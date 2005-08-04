/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;

import psl.crunch3.plugins.ProxyFilter;

/*
 *  
 */

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class Proxy implements Runnable {
	private int listenPort;
	private boolean initialized = false;
	private Thread transferThread;
	private ServerSocket serverSocket;
	//private LinkedList transfers;
	private Vector<ProxyFilter> plugins = new Vector<ProxyFilter>();

	private Connection con = null;
	
	public Proxy(int port) {
		//ensure the port given is in the proper range
		if (port < 0 || port > 65536) {
			System.out.println("Error: port number out of range: " + port);
		}
		listenPort = port;

		if (Crunch3.settings.isVerbose())
			System.out.println("Initializing Proxy...");
		initialized = initialize();

		if (initialized) {
			transferThread = new Thread(this, "TransferThread");
			transferThread.setDaemon(true);
			transferThread.start();
		} else {
			System.out.println("Error initializing proxy.");
		}
	}

	public void run() {
		if (Crunch3.settings.isVerbose()) {
			System.out.println("TransferThread Started...");
			System.out.println("Listening on port " + listenPort);
		}
		mainLoop();
	}

	/**
	 * Initializes the transfer thread.
	 * 
	 * @return true if the transfer thread has been initialized properly
	 */
	private synchronized boolean initialize() {
		// set up the server socket
		try {
			serverSocket = new ServerSocket();
		} catch (IOException ioe) {
			System.out.println("An error occurred creating the server socket.");
			ioe.printStackTrace();
			return false;
		}

		// set up the server socket timeout
		try {
			serverSocket.setSoTimeout(Crunch3.settings.getServerSocketTimeout());
		} catch (SocketException se) {
			System.out.println("Error settings the server socket timeout (nonfatal)");
			se.printStackTrace();
		}

		// bind the server socket to a port
		try {
			serverSocket.bind(new InetSocketAddress(listenPort));
		} catch (IOException ioe) {
			System.out.println("An error occurred binding the server socket" + " (maybe the socket is already in use by another application)");
			ioe.printStackTrace();
			return false;
		}

		if(Crunch3.mainWindow !=null)
		Crunch3.mainWindow.setListeningOn(serverSocket.getInetAddress().toString(), listenPort);

		return true;
	}

	/**
	 * Proxy listen loop. Endlessly loops listening for connections on the server socket. Creates a new HttpStream for each connection.
	 *  
	 */
	private void mainLoop() {
		if(Crunch3.mainWindow !=null)
			Crunch3.mainWindow.setProxyStatus("Listening for requests.");
		while (true) {
			boolean exception = false;

			//accept a new connection from the server socket
			ServerSocket currentServerSocket = serverSocket;
			try {
				Socket clientSocket = currentServerSocket.accept();
				new HttpStream(clientSocket);
			} catch (SocketTimeoutException ste) {
				// not a big deal if the server socket times out
			} catch (IOException e) {
				if (currentServerSocket != serverSocket); // indicates the socket was replaced
				else
					exception = true;
			} catch (Exception e) {
				if (Crunch3.settings.isVerbose())
					e.printStackTrace();
				exception = true;
			}

			if (exception) {
				if (!serverSocket.isBound()) {
					if (Crunch3.settings.isVerbose())
						System.out.println("Proxy: ServerSocket isn't bound.");
				} else if (serverSocket.isClosed()) {
					if (Crunch3.settings.isVerbose())
						System.out.println("Proxy: ServerSocket was closed.");
				}
				
				while(currentServerSocket == serverSocket) {
					Crunch3.mainWindow.setProxyStatus("ServerSocket Error.");
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// don't need to do anything on this exception
					}
				}
				
			}
		}
	}

	/**
	 * @param i
	 *            the timeout used by the server socket when initiating a connection
	 */
	public void setServerSocketTimeout(final int i) {
		try {
			if (serverSocket != null)
				serverSocket.setSoTimeout(i);
		} catch (SocketException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
	}

	public void setListenPort(final int i) {
		if (listenPort == i)
			return;
		
		ServerSocket oldServerSocket = serverSocket;
		listenPort = i;
		initialize();
		if (oldServerSocket != null)
			try {
				oldServerSocket.close();
			} catch (IOException e) {
				if (Crunch3.settings.isVerbose())
					e.printStackTrace();
			}
	}

	/**
	 * Registers a new plugin with the proxy.
	 * 
	 * @param plugin
	 *            the plugin to register
	 */
	public void registerPlugin(final ProxyFilter plugin) {
		plugins.add(plugin);
		if(Crunch3.mainWindow != null) Crunch3.mainWindow.addPlugin(plugin);
	}

	public ProxyFilter[] getPlugins() {
		Object[] oArray = plugins.toArray();
		ProxyFilter[] pfArray = new ProxyFilter[oArray.length];
		for (int i = 0; i < oArray.length; i++)
			pfArray[i] = (ProxyFilter) oArray[i];

		return pfArray;
	}
	
	/**
	 * returns a Connection object that connnects to the crunch database
	 */
	public Connection getConnection(){
		
		if(con == null){
			//connect to DB
			 try {
					
				 
				 //Register the JDBC driver for MySQL.
			
				
				 Class.forName("org.gjt.mm.mysql.Driver");
				 
			     String url = "jdbc:mysql://localhost:3306/crunch";

				 System.out.println("point 2");

		        con = DriverManager.getConnection(url, "admin", "test"); 
		           
		     } 
			 catch (Exception ex) {
				
				 	con=null;
				 	ex.printStackTrace();
		     }
			 
			
		}
		return con;
	}
	
}
