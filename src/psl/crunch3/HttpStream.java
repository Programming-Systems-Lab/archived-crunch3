/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import java.sql.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

/*
 * TODO implement chunked transfer encoding, keepalive, and pipelining
 */

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class HttpStream extends Thread {
	private static final char CR = '\r';
	private static final char LF = '\n';
	private static final String CRLF = "\r\n";
	private static final int BUFFER_SIZE = 16384;
	

	private static final String[] STATUS_STRINGS = { "0. Initializing", //0
		"1. Reading Client First Line", //1
		"2. Reading Client Metadata", //2
		"3. Connecting to Server", //3
		"4. Sending Server Metadata", //4
		"5. Reading Server First Line", //5
		"6. Reading Server Metadata", //6
		"7. Streaming Data To File", //7
		"8. Filtering", //8
		"9. Sending Client Metadata", //9
		"10. Streaming Data To Client" //10
	};

	private int soTimeout = Crunch3.settings.getSocketTimeout();
	
	private boolean isCrunchPage = false;
	
	Socket clientSocket;
	HttpMetadata clientMetadata = new HttpMetadata(HttpMetadata.CLIENT);

	Socket serverSocket;
	HttpMetadata serverMetadata = new HttpMetadata(HttpMetadata.SERVER);

	File contentFile;

	private String status;

	private Connection con;
	
	
	public HttpStream(final Socket socket) {
		super("HttpStreamThread");
		if (socket == null)
			throw new NullPointerException("HttpStream Error: socket is null.");

		setStatus(STATUS_STRINGS[0]);

		clientSocket = socket;

		if (Crunch3.settings.isVerbose()) {
			System.out.println(
				"\nNew connection from "
					+ clientSocket.getInetAddress()
					+ ":"
					+ clientSocket.getPort()
					+ " on "
					+ clientSocket.getLocalAddress()
					+ ":"
					+ clientSocket.getLocalPort()
					+ "\n");
		}

		
		//get associated username with client InetAddress
		if(Crunch3.settings.RUN_ON_SERVER){
			getUsernameFromDB();
		}
		this.start();
	}

	public void run() {
		// display new connection on gui
		if(Crunch3.mainWindow != null) Crunch3.mainWindow.addNewConnection(this);

		try {
			clientSocket.setSoTimeout(soTimeout);
		} catch (SocketException e1) {
			if (Crunch3.settings.isVerbose()) {
				System.out.println("HttpStream Warning: Error setting socket timeout of " + soTimeout);
				e1.printStackTrace();
			}
		}

		try {
			readClientFirstLine();
			if(!Crunch3.settings.isGUISet())Crunch3.mainControl.printStatus();
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		} finally {
			shutdown();

			// remove connection from gui
			if(Crunch3.mainWindow != null) Crunch3.mainWindow.removeConnection(this);
		}
	}

	/**
	 * Reads the first line from the client and then calls the read client metadata method.
	 */
	private void readClientFirstLine() {
		// update status
		setStatus(STATUS_STRINGS[1]);

		// try to read the first line from the client
		try {
			String get = readLine(clientSocket.getInputStream());
			clientMetadata.setFirstLine(get);
			
			if(get.contains("rector.psl.cs.columbia.edu/crunch")){
				//Crunch3.mainControl.loadFile("config"+ File.separator +"level13.ini");
				if(get.contains("rector.psl.cs.columbia.edu/crunch/register.htm") || get.contains("rector.psl.cs.columbia.edu/crunch/slogin.html")
						|| get.contains("rector.psl.cs.columbia.edu/crunch/Register.jsp") || get.contains("rector.psl.cs.columbia.edu/crunch/login.htm")){
				int start = get.lastIndexOf("HTTP");
				get = get.substring(0,start-1) + "?ip=" + clientSocket.getInetAddress() + " " + get.substring(start);
			
				clientMetadata.setFirstLine(get);
				}
				isCrunchPage = true;
			}
			else{
				isCrunchPage = false;
			}
		} catch (IOException ioe) {
			// shutdown on an error
			if (Crunch3.settings.isVerbose())
				ioe.printStackTrace();
			shutdown();
			return;
		}

		//go on to the next step (reading the client
		// metadata)
		readClientMetadata();
	}

	/**
	 * Reads the client metadata and then calls the connect to server method.
	 *  
	 */
	private void readClientMetadata() {
		setStatus(STATUS_STRINGS[2]);
		try {
			readMetadata(clientSocket.getInputStream(), clientMetadata);
		} catch (IOException ioe) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream: Error reading client metadata - shutting down.");
			shutdown();
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
			shutdown();
			return;
		}

		connectToServer();
	}

	/**
	 * Connects to the server specified in the html header.
	 */
	private void connectToServer() {
		setStatus(STATUS_STRINGS[3]);
		//figure out where to connect to
		URI serverURI;

		if (Crunch3.settings.getUseExternalProxy())
			try {
				serverURI = new URI(Crunch3.settings.getExternalProxyAddress() + ":" + Crunch3.settings.getExternalProxyPort());
			} catch (URISyntaxException e1) {
				if (Crunch3.settings.isVerbose()) {
					System.err.println("Error generating external proxy URI:");
					e1.printStackTrace();
				}
			}

		serverURI = clientMetadata.getURI();
		if (serverURI == null || serverURI.getHost() == null) {
			shutdown();
			return;
		}

		String serverName = serverURI.getHost();

		// resolve the internet address
		InetAddress address = null;
		try {
			address = InetAddress.getByName(serverName);
		} catch (UnknownHostException uhe) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream Error: Unknown host (" + serverName + ")");
			shutdownWithHttpError(HttpResponses.RESPONSE_500);
			return;
		} catch (SecurityException se) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream: Security Error.  This VM doesn't have permission to resolve IPs.");
			shutdown();
			return;
		}

		// get the port to connect to from the server uri
		int port = serverURI.getPort();

		//if the port was not specified then use the
		// default http port of 80
		if (port == -1) {
			port = 80;
		}

		// try to connect to the server and
		try {
			serverSocket = new Socket(address, port);
		} catch (IOException e) {
			if (Crunch3.settings.isVerbose()) {
				System.out.println("HttpStream: Error connecting to server: " + serverName + ":" + port);
				e.printStackTrace();
			}
			shutdown();
			return;
		}
		modifyClientMetadata();
		sendServerMetadata();
	}

	/**
	 * Removes some tags that were sent to the proxy and meant for the proxy rather than the server.
	 */
	private void modifyClientMetadata() {
		// TODO implement modify client metadata
		clientMetadata.reformFirstLine();
		clientMetadata.remove("Proxy-Connection");
		//clientMetadata.remove("Connection");
		//clientMetadata.remove("Pragma");
		//clientMetadata.remove("Accept-Language");
		//clientMetadata.remove("Accept");
		//clientMetadata.remove("Accept-Charset");
		clientMetadata.remove("Accept-Encoding");
		clientMetadata.remove("If-Modified-Since");
		clientMetadata.remove("If-None-Match");
		//clientMetadata.remove("User-Agent");
		//clientMetadata.remove("Host");
		if (Crunch3.settings.isVerbose()) {
			System.out.println(clientMetadata.toString());
		}
	}

	private void sendServerMetadata() {
		setStatus(STATUS_STRINGS[4]);
		try {
			sendString(serverSocket.getOutputStream(), clientMetadata.toString());
		} catch (IOException e) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream: Error sending server client metadata.");
			shutdown();
			return;
		}

		try {
			streamData(clientSocket.getInputStream(), serverSocket.getOutputStream(), clientMetadata.getContentLength());
		} catch (IOException e1) {
			if (Crunch3.settings.isVerbose())
				e1.printStackTrace();
			shutdown();
			return;
		}

		readServerFirstLine();
	}

	private void readServerFirstLine() {
		setStatus(STATUS_STRINGS[5]);
		try {
			serverMetadata.setFirstLine(readLine(serverSocket.getInputStream()));
		} catch (IOException ioe) {
			if (Crunch3.settings.isVerbose())
				ioe.printStackTrace();
			shutdown();
		}

		readServerMetadata();
	}

	private void readServerMetadata() {
		setStatus(STATUS_STRINGS[6]);
		try {
			readMetadata(serverSocket.getInputStream(), serverMetadata);
		} catch (IOException ioe) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream: Error reading server metadata - shutting down.");
			shutdown();
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
			shutdown();
		}

		decideFiltering();
	}

	private void decideFiltering() {
		// TODO make decide filtering
		boolean shouldFilter = true;
		
		// only filter if filtering enabled
		shouldFilter = shouldFilter && Crunch3.settings.isFilterContent();
		
		// only filter if content-type is correct
		String contentType = serverMetadata.get("content-type").toLowerCase();
		shouldFilter = shouldFilter && Crunch3.settings.isFilterType(contentType);
		
		// only filter if not homepage or filter homepages turned on
		shouldFilter = shouldFilter && (Crunch3.settings.isFilterHomepages() || !isHomepage());
		
		//only filter if not crunch page
		shouldFilter = shouldFilter && !isCrunchPage;
		if (shouldFilter)
			contentFile = filter();
		
		modifyServerMetadata();
	}
	
	private boolean isHomepage() {
		URI uri = clientMetadata.getFirstLineURI();
		if (uri == null)
			return false;

		String url = uri.getPath();
		
		if (url == null)
			return false;

		boolean endsWithSlash = url.lastIndexOf('/') == url.length() - 1;
		return endsWithSlash;
	}

	private File filter() {
		if (Crunch3.settings.isVerbose())
			System.out.println("HttpStream Filtering");

		try {
			// stream content from server to file
			setStatus(STATUS_STRINGS[7]);
			contentFile = File.createTempFile("temp-", ".html");
			FileOutputStream fos = new FileOutputStream(contentFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			long contentLength = serverMetadata.getContentLength();
			if (contentLength > 0) {
				streamData(serverSocket.getInputStream(), bos, contentLength);
			} else {
				streamData(serverSocket.getInputStream(), bos);
			}
			bos.close();

			// perform filtering
			setStatus(STATUS_STRINGS[8]);
			File oldContentFile = contentFile;
			contentFile = new PluginFilterRunner(clientMetadata, serverMetadata).process(contentFile);

			if (oldContentFile != null && !oldContentFile.equals(contentFile))
				oldContentFile.delete();

		} catch (IOException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}

		if (Crunch3.settings.isVerbose())
			System.out.println("HttpStream Filtering Completed");

		return contentFile;
	}

	private void modifyServerMetadata() {
		if (contentFile != null) {
			serverMetadata.set("Content-Length: " + contentFile.length() + CRLF);
		}
		sendClientMetadata();
	}

	private void sendClientMetadata() {
		setStatus(STATUS_STRINGS[9]);
		try {
			if (Crunch3.settings.isVerbose())
				System.out.println(serverMetadata.toString());
			sendString(clientSocket.getOutputStream(), serverMetadata.toString());
		} catch (IOException e) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpStream: Error sending client server metadata.");
			shutdown();
			return;
		}
		sendClientData();
	}

	private void sendClientData() {
		try {
			setStatus(STATUS_STRINGS[10]);
			if (contentFile == null) {
				long contentLength = serverMetadata.getContentLength();
				if (contentLength > 0) {
					streamData(serverSocket.getInputStream(), clientSocket.getOutputStream(), contentLength);
				} else {
					streamData(serverSocket.getInputStream(), clientSocket.getOutputStream());
				}
			} else {
				streamData(new FileInputStream(contentFile), clientSocket.getOutputStream(), contentFile.length());
				contentFile.delete();
			}
		} catch (IOException e1) {
			if (Crunch3.settings.isVerbose())
				e1.printStackTrace();
			shutdown();
			return;
		}
		shutdown();
	}

	private void streamData(final InputStream input, final OutputStream output, final long amount) throws IOException {
		if (amount < 1)
			return;

		byte[] buffer = new byte[BUFFER_SIZE];

		int amountInBuffer = 0;
		int amountWrittenInBuffer = 0;
		long amountToRead = 0;
		long amountWritten = 0;

		while (amountWritten < amount) {
			amountToRead = amount - amountWritten;
			if (amountToRead > buffer.length)
				amountToRead = buffer.length;

			amountInBuffer = input.read(buffer, 0, (int) amountToRead);
			if (amountInBuffer == -1) {
				// TODO deal with nothing left and content length not reached
				return;
			}

			output.write(buffer, amountWrittenInBuffer, amountInBuffer);
			amountWritten += amountInBuffer;
		}

	}

	private void streamData(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int amountInBuffer = 0;
		int amountWrittenInBuffer = 0;
		long amountToRead = 0;
		long amountWritten = 0;

		while (true) {
			amountToRead = buffer.length;

			amountInBuffer = input.read(buffer, 0, (int) amountToRead);
			if (amountInBuffer == -1) {
				// TODO deal with nothing left and content length not reached
				return;
			}

			output.write(buffer, amountWrittenInBuffer, amountInBuffer);
			amountWritten += amountInBuffer;
		}

	}

	private void sendString(final OutputStream output, final String string) throws IOException {
		byte[] bytes = string.getBytes();
		output.write(bytes);
		output.flush();
	}

	/**
	 * @param input
	 *            the input stream to read the metadata from
	 * @param metadata
	 *            the metadata container to store the metadata in
	 * @throws IOException
	 *             if error reading from the inputstream
	 */
	private void readMetadata(final InputStream input, final HttpMetadata metadata) throws IOException {
		StringBuffer buffer = new StringBuffer();
		int c = 0;

		// read in the metadata until a blank line is
		// detected
		while (true) {
			// keep reading bytes until no more
			while (!lineEnded(buffer)) {
				c = input.read();
				if (c == -1) {
					break;
				} else {
					buffer.append((char) c);
				}
			}

			// if there is no more input, break
			// (any properly terminated lines would have
			// been dealt with)
			if (c == -1)
				break;

			// convert the stringbuffer to a line
			String line = buffer.toString();

			//create a new stringbuffer for the next line
			buffer.delete(0, buffer.length());

			// either return (blank line) or read the line
			// into the metadata
			if (CRLF.equals(line))
				return;
			else {
				metadata.set(line);
			}

		}
	}

	/**
	 * @param input
	 *            input stream to read the line from.
	 * @return the line
	 * @throws IOException
	 *             when there was an error reading the line
	 */
	private String readLine(final InputStream input) throws IOException {
		StringBuffer buffer = new StringBuffer();
		int c = 0;

		while (!lineEnded(buffer)) {
			c = input.read();
			if (c == -1)
				throw new IOException("Connection disconnected unexpectedly while reading line.");
			else
				buffer.append((char) c);
		}

		return buffer.toString();
	}

	/**
	 * Returns true when a line is terminated by a CR LF
	 * 
	 * @param line
	 *            StringBuffer containing a line
	 * @return whether or not the line is terminated by a CR LF
	 */
	private boolean lineEnded(final StringBuffer line) {
		int length = line.length();

		// if the line length is less than 2 then it can't
		// possibly have
		// a CR LF at the end
		if (length < 2)
			return false;

		// if the line has a CR LF at the end then it is
		// properly terminated
		// otherwise it is not
		if (line.charAt(length - 2) == CR && line.charAt(length - 1) == LF)
			return true;
		else
			return false;
	}

	private void shutdownWithHttpError(final String htmlMessage) {
		if (Crunch3.settings.isVerbose())
			System.out.println(htmlMessage);
		try {
			sendString(clientSocket.getOutputStream(), htmlMessage);
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose()) {
				System.out.println("HttpStream Error: Error seding shutdown message to client.");
				e.printStackTrace();
			}
		}
		shutdown();
	}

	/**
	 * Shutdown this http connection set. Closes both connections (the client connection and the connection to the http server).
	 */
	private void shutdown() {
		if (serverSocket != null) {
			if (!serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					if (Crunch3.settings.isVerbose())
						e.printStackTrace();
				}
			}
		}

		if (clientSocket != null) {
			if (!clientSocket.isClosed()) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					if (Crunch3.settings.isVerbose())
						e.printStackTrace();
				}
			}
		}

		if (contentFile != null) {
			try {
				contentFile.delete();
			} catch (Exception e) {
				if (Crunch3.settings.isVerbose())
					e.printStackTrace();
			}
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	
	/**
	 * connects to the database and fetches the username that is associated with the IP address
	 * of the client
	 */
	private void getUsernameFromDB(){
		con = Crunch3.proxy.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		if (Crunch3.settings.isVerbose()){
    		System.out.println("changing settings");
    		System.out.println();
    	}
		
		
		if(con != null){
			try{
				
				stmt = con.createStatement();
	            
	            stmt.execute("select user from connected where ip='" + clientSocket.getInetAddress() + "'");
	            
	            System.out.println(clientSocket.getInetAddress());
	            rs= stmt.getResultSet();
	            if(!rs.next()){
	            	//load default settings
	            	System.out.println("problem \n");
	            }
	            else{
	            	String username=rs.getString(1);
	            	Crunch3.mainControl.loadFile("users" +File.separator+ username+".ini");
	            	if (Crunch3.settings.isVerbose()){
	            		System.out.println("settings file loaded for user " + username);
	            	}
	            }
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
		}
	}

}
