/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ContentExtractorErrorHandler implements ErrorHandler {

	public ContentExtractorErrorHandler() {
		super();
	}

	public void error(SAXParseException arg0) throws SAXException {
		System.out.println("XML Error:");
		arg0.printStackTrace();
	}

	public void fatalError(SAXParseException arg0) throws SAXException {
		System.out.println("XML Fatal Error:");
		arg0.printStackTrace();
	}

	public void warning(SAXParseException arg0) throws SAXException {
		System.out.println("XML Warning:");
		arg0.printStackTrace();
	}

}
