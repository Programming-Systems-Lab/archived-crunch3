/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.xml.serialize.HTMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XHTMLSerializer;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import psl.crunch3.plugins.ProxyFilter;
import psl.crunch3.plugins.SiteDependentFilter;
import psl.crunch3.plugins.contentextractor.ContentExtractorErrorHandler;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class PluginFilterRunner {
	private DOMParser htmlParser;
	private HttpMetadata clientData;
	private HttpMetadata serverData;
	private boolean documentCopyable = true;

	ProxyFilter[] plugins;

	public PluginFilterRunner() {
		plugins = Crunch3.proxy.getPlugins();
	}
	
	public PluginFilterRunner(HttpMetadata clientData, HttpMetadata serverData) {
		plugins = Crunch3.proxy.getPlugins();
		this.clientData = clientData;
		this.serverData = serverData;
	}

	public File process(final File f) {
		// generate xml document from file
		Document originalDocument = getXML(f);
		Document previousDocument = null;
		Document currentDocument = null;

		currentDocument = copyDocument(originalDocument);

		for (int i = 0; i < plugins.length; i++) {
			ProxyFilter plugin = plugins[i];
			if (!plugin.isEnabled())
				continue;
			
			if (plugin instanceof SiteDependentFilter && clientData != null && serverData != null) {
				SiteDependentFilter sdf = (SiteDependentFilter)plugin;
				sdf.reportHost(clientData.get("Host"));
				String referer = clientData.get("Referer");
				if (referer != ""){ 
					Crunch3.mainWindow.setURL(parseURL(clientData.getFirstLine().trim(), clientData.get("Host"))); 
					sdf.reportReferer(referer);}
				else {
					sdf.reportReferer(Crunch3.mainWindow.getURL()); 
					Crunch3.mainWindow.setURL(parseURL(clientData.getFirstLine().trim(), clientData.get("Host"))); 
					}
				sdf.reportApplication(clientData.get("User-Agent"));
			}

			if (currentDocument != null)
				previousDocument = currentDocument;

			if (previousDocument != null)
				currentDocument = copyDocument(previousDocument);

			// ALL THE WORK GETS DONE HERE
			currentDocument = plugin.process(originalDocument, previousDocument, currentDocument);
		}

		if (currentDocument == null)
			currentDocument = previousDocument;
		if (currentDocument == null)
			currentDocument = originalDocument;

		return xMLtoFile(currentDocument);
	}

	public String parseURL(String original, String host){
		
		try{
		//original should be in the form GET /... HTTP/1.1
		int length = original.length()-9;
		return "http://" + host.trim() + original.substring(4,length).trim();
		
		}
		catch(Exception e){
			if (Crunch3.settings.isVerbose())
	    		System.out.println("error parsing the url");
		}
		return "";
	}
	
	/**
	 * @param currentDocument
	 * @return the file the document was written to.
	 */
	private File xMLtoFile(Document currentDocument) {
		File contentFile;
		try {
			contentFile = File.createTempFile("temp-", ".html");
		} catch (IOException e1) {
			if (Crunch3.settings.isVerbose())
				e1.printStackTrace();
			return null;
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(contentFile);
		} catch (FileNotFoundException e2) {
			if (Crunch3.settings.isVerbose())
				e2.printStackTrace();
			return null;
		}
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		OutputFormat format = new OutputFormat(currentDocument, "ISO-8859-1", false);

		//XHTMLSerializer printer = new XHTMLSerializer(bos, format);
		HTMLSerializer printer = new HTMLSerializer(bos, format);
		
		format.setOmitDocumentType(true);

		try {
			printer.serialize(currentDocument);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				contentFile.delete();
			} catch (Exception e1) {
				// not much to do here if can't delete the
				// file
			}
			return null;
		} //catch

		return contentFile;
	}

	private Document getXML(final File f) {
		if (htmlParser == null) {
			htmlParser = new DOMParser();
			if (Crunch3.settings.isVerbose())
				htmlParser.setErrorHandler(new ContentExtractorErrorHandler());
		}

		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
			return null;
		}

		InputSource is = new InputSource(bis);

		try {
			htmlParser.parse(is);
		} catch (SAXException e1) {
			if (Crunch3.settings.isVerbose())
				e1.printStackTrace();
		} catch (IOException e1) {
			if (Crunch3.settings.isVerbose())
				e1.printStackTrace();
		}

		return htmlParser.getDocument();
	}

	private Document getXML(final String s) {
		if (htmlParser == null) {
			htmlParser = new DOMParser();
			if (Crunch3.settings.isVerbose())
				htmlParser.setErrorHandler(new ContentExtractorErrorHandler());
		}

		StringReader stringReader = new StringReader(s);
		try {
			htmlParser.parse(new InputSource(stringReader));

		} catch (SAXException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		} catch (IOException e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		} finally {
			stringReader.close();
		}

		return htmlParser.getDocument();
	}
	/**
	 * Copies the document
	 * 
	 * @param d
	 *            the document to copy
	 * @return a copy of d
	 */
	private Document copyDocument(final Document d) {
		Document clone = null;

		if (documentCopyable)
			try {
				//clone = (Document) d.cloneNode(true);
				clone = (Document) d.cloneNode(true);
			} catch (DOMException dome) {
				if (Crunch3.settings.isVerbose())
					dome.printStackTrace();
				documentCopyable = false;
			}

		if (!documentCopyable) {
			StringWriter stringWriter = new StringWriter();
			XHTMLSerializer xHTMLSerializer = new XHTMLSerializer();
			OutputFormat outputFormat = new OutputFormat(d, "ISO-8859-1", false);
			xHTMLSerializer.setOutputFormat(outputFormat);
			xHTMLSerializer.setOutputCharStream(stringWriter);
			try {
				xHTMLSerializer.serialize(d);
			} catch (IOException e) {
				if (Crunch3.settings.isVerbose())
					e.printStackTrace();
			}
			String outputString = stringWriter.toString();
			try {
				stringWriter.close();
			} catch (IOException e1) {
				if (Crunch3.settings.isVerbose())
					e1.printStackTrace();
			}
			clone = getXML(outputString);
		}

		return clone;
	}
}
