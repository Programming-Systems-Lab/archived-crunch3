/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Container class for storing HTTP headers.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class HttpMetadata {
	/**
	 * Tells the metadata during construction that it will be working with
	 * client metadata.
	 */
	public static final int CLIENT = 1;
	/**
	 * Tells the metadata during construction that it will be working with
	 * server metadata.
	 */
	public static final int SERVER = 2;
	/**
	 * HTTP newline
	 */
	private static final String CRLF = "\r\n";
	/**
	 * Stores the key value pairs for easy value lookups
	 */
	private Hashtable pairs = new Hashtable();
	/**
	 * Stores the original lines for easy lookup key based lookups
	 */
	private Hashtable originalLines = new Hashtable();
	/**
	 * Stores the original metadata lines in the original order
	 */
	private Vector originalVector = new Vector();
	private URI firstLineURI = null;
	private String firstLine = CRLF;
	private int type = 0;

	/**
	 * Creates a new http metadata storage structure.
	 * 
	 * @param type
	 *            the type of metadata to store (Client or Server).
	 * @see #CLIENT
	 * @see #SERVER
	 */
	public HttpMetadata(int type) {
		this.type = type;
	}

	/**
	 * @param line
	 *            the first line of an http response/request.
	 */
	public void setFirstLine(String line) {
		if (Crunch3.settings.isVerbose())
			System.out.println("metadata firstline set: " + line.trim());
		firstLine = line;
	}

	/**
	 * returns the first line of an http response/request.
	 * 
	 * @return the first line of the http response/request
	 */
	public String getFirstLine() {
		return firstLine;
	}
	/**
	 * @return the URI from the first line (the second token)
	 */
	public URI getFirstLineURI() {
		StringTokenizer st = new StringTokenizer(firstLine);

		if (st.countTokens() < 2)
			return null;

		st.nextToken();

		try {
			return new URI(st.nextToken());
		} catch (URISyntaxException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
			return null;
		}
	}

	/**
	 * Set a new property or replace an old one using an unparsed http header
	 * line. For example "content-type: text/plain"
	 * 
	 * @param line
	 *            the line containing the property
	 */
	public void set(final String line) {
		if (Crunch3.settings.isVerbose())
			System.out.println("metadata set: " + line.trim());
		String key = null;
		String value = null;
		int colonIndex = -1;

		// null lines are invalid
		if (line == null) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpMetadata Error: Null set string");
			return;
		}

		// get the index of the first colon in the line
		colonIndex = line.indexOf(':');

		// lines that don't have colons are invalid
		if (colonIndex == -1) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpMetadata Error: No colon found in line: " + line);
			return;
		}

		// set the key to everything before the colon
		String keyOriginalCase = line.substring(0, colonIndex);
		key = keyOriginalCase.trim().toLowerCase();

		// if the key is 0 length, it is invalid
		if (key.length() < 1) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpMetadata Error: Key too small: " + line);
			return;
		}

		// set the value to everything after the colon
		value = line.substring(colonIndex + 1, line.length()).trim();

		// remove old key pair
		int oldLocation = remove_1(key);

		// add new key pair
		pairs.put(key, value);
		originalLines.put(key, line);
		if (oldLocation != -1 && oldLocation < originalVector.size())
			originalVector.add(oldLocation, line);
		else
			originalVector.add(line);
	}

	/**
	 * Removes the metadata specified by the given key.
	 * 
	 * @param key
	 *            the key of the metadata to be removed.
	 */
	public void remove(final String key) {
		if (Crunch3.settings.isVerbose())
			System.out.println("metadata remove: " + key.trim());
		remove_1(key);
	}

	private int remove_1(String key) {
		key = key.trim().toLowerCase();
		pairs.remove(key);
		int oldLocation = originalVector.indexOf(originalLines.remove(key));
		if (oldLocation != -1)
			originalVector.remove(oldLocation);
		return oldLocation;
	}

	/**
	 * Gets a property, given its name. If the property does not exist, returns
	 * an empty string.
	 * 
	 * @param key
	 *            the name of the property
	 * @return the value of the property
	 */
	public String get(String key) {

		// ensure the key is lowercase and trimmed
		key = key.trim().toLowerCase();

		// if there is a value for the key, return it
		// otherwise return a zero length string.
		if (pairs.containsKey(key))
			return (String) pairs.get(key);
		else
			return "";
	}

	/**
	 * Gets the content length. Returns 0 if the content length not specified
	 * and returns -1 if there was an error parsing the value as a long.
	 * 
	 * @return the content length (the length of the message body).
	 */
	public long getContentLength() {
		String lengthString = this.get("content-length");
		if ("".equals(lengthString))
			return 0;

		try {
			long length = Long.parseLong(lengthString);
			return length;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Gets the URI location of the document. First it tries the host metadata,
	 * next it tries the location metadata, and finally it tries to read it
	 * from the first line. If none of those exist, it returns null.
	 * 
	 * @return the URI
	 */
	public URI getURI() {
		URI serverURI = null;

		// attempt to get the uri from the host element
		try {
			serverURI = new URI(this.get("Host"));
		} catch (Exception e) {
			// if serverURI is null, problem will be dealt with
		}

		// attempt to get the uri from the location element if that didn't work
		if (serverURI == null || serverURI.getHost() == null) {
			try {
				serverURI = new URI(this.get("Location"));
			} catch (Exception e) {
				// if server uri is still null problem will be dealt with
			}
		}

		// attempt to get the uri from the first line if that didn't work
		if (serverURI == null || serverURI.getHost() == null) {
			serverURI = this.getFirstLineURI();
			firstLineURI = serverURI;
		}

		// if none of the above worked, return null
		if (serverURI == null || serverURI.getHost() == null) {
			if (Crunch3.settings.isVerbose())
				System.out.println("Error: could not find suitable host URI in header.");
			return null;
		}

		return serverURI;
	}

	/**
	 * Converts the metadata back into an http property set.
	 * 
	 * @return http property set (header)
	 */
	public String toString() {
		StringBuffer output = new StringBuffer();

		output.append(firstLine);

		Enumeration lines = originalVector.elements();

		for (; lines.hasMoreElements();) {
			String currentLine = (String) lines.nextElement();
			output.append(currentLine);
		}

		output.append(CRLF);

		return output.toString();
	}

	/**
	 * Converts the first line's a url to just a path. Certain servers, such as
	 * www.slashdot.org return 501 errors unless this is done.
	 */
	public void reformFirstLine() {
		StringTokenizer tokens = new StringTokenizer(this.firstLine);
		StringBuffer newLine = new StringBuffer();

		// initial error checking
		if (firstLine == null) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpMetadata: reformFirstLine(): null first line.");
			return;
		}
		if (tokens.countTokens() < 2) {
			if (Crunch3.settings.isVerbose())
				System.out.println("HttpMetadata: reformFirstLine(): " + "not enough tokens in the first line: " + firstLine.trim());
			return;
		}

		// first token should not have to be reformed
		newLine.append(tokens.nextToken());

		// second token needs to be converted into a stand-alone path
		String stringURI = tokens.nextToken();
		URI uri = null;
		try {
			uri = new URI(stringURI);
		} catch (URISyntaxException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
			return;
		}

		try {
			uri = new URI(null, null, uri.getPath(), uri.getQuery(), uri.getFragment());
		} catch (URISyntaxException e1) {
			if (Crunch3.settings.isVerbose()) {
				System.out.println("HttpMetadata: reformFirstLine: can't reform first line uri.");
				e1.printStackTrace();
			}
			return;
		}

		newLine.append(" ").append(uri.toString());

		if (tokens.hasMoreTokens()) {

			String httpVersion = tokens.nextToken();

			if (httpVersion.startsWith("HTTP/"))
				newLine.append(" ").append("HTTP/1.0");
			else {
				newLine.append(" ").append(httpVersion);
				if (Crunch3.settings.isVerbose())
					System.out.println("HttpMetadata: reformFirstLine: unrecognized http version: " + httpVersion);
			}
		}

		while (tokens.hasMoreTokens()) {
			newLine.append(" ").append(tokens.nextToken());
		}

		newLine.append(CRLF);
		firstLine = newLine.toString();
	}
}
