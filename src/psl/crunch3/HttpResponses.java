/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;

/**
 * A bunch of stock http error responses.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class HttpResponses {
	/**
	 * HTTP newline
	 */
	public static final String CRLF = "\r\n";

	private static final String RESPONSE_500_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.0 500 Internal Error</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.0 500 Internal Error" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_500 =
		"HTTP/1.0 500 Internal Error" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_500_CONTENT.length() + CRLF + CRLF +
		RESPONSE_500_CONTENT;
	
	private static final String RESPONSE_501_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.0 501 Not Implemented</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.0 501 Not Implemented" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_501 =
		"HTTP/1.0 501 Not Implemented" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_501_CONTENT.length() + CRLF + CRLF +
		RESPONSE_501_CONTENT;

	private static final String RESPONSE_502_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.0 502 Bad Gateway</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.0 502 Bad Gateway" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_502 =
		"HTTP/1.0 502 Bad Gateway" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_502_CONTENT.length() + CRLF + CRLF +
		RESPONSE_502_CONTENT;

	private static final String RESPONSE_503_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.0 503 Service Unavailable</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.0 503 Service Unavailable" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_503 =
		"HTTP/1.0 503 Service Unavailable" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_503_CONTENT.length() + CRLF + CRLF +
		RESPONSE_503_CONTENT;

	private static final String RESPONSE_504_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.1 504 Gateway Timeout</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.1 504 Gateway Timeout" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_504 =
		"HTTP/1.1 504 Gateway Timeout" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_504_CONTENT.length() + CRLF + CRLF +
		RESPONSE_504_CONTENT;

	private static final String RESPONSE_505_CONTENT = 
	"<HTML>" + CRLF +
	"<HEAD><TITLE>HTTP/1.1 505 Http Version Not Supported</TITLE></HEAD>" + CRLF +
	"<BODY>" + CRLF +
	"HTTP/1.1 505 Http Version Not Supported" + CRLF +
	"</BODY>" + CRLF +
	"</HTML>" + CRLF;
	public static final String RESPONSE_505 =
		"HTTP/1.1 505 Http Version Not Supported" + CRLF +
		"Content-Type: text/html" + CRLF +
		"Content-Length: " + RESPONSE_505_CONTENT.length() + CRLF + CRLF +
		RESPONSE_505_CONTENT;

	/**
	 * Prevents instantiation of this class
	 *  
	 */
	private HttpResponses() {
		//prevent instantiation
	}
}
