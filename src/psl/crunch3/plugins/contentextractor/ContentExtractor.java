/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;
//import java.io.*;
//import java.net.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Iterator;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.apache.xml.serialize.*;
import org.eclipse.swt.widgets.Composite;

import psl.crunch3.Crunch3;
import psl.crunch3.plugins.EnhancedProxyFilter;
import psl.crunch3.plugins.ProxyFilterSettings;
import psl.crunch3.plugins.SiteDependentFilter;
import psl.crunch3.util.WordCount;


/**
 * This class uses a settings file to determine portions of a web site to remove, thus extracting the true content of a site based on the user's preferences.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ContentExtractor extends EnhancedProxyFilter implements SiteDependentFilter {
	//Normal final variables not associated with settings
	public static final int ALL = 0;
	public static final int TEXT = 1;
	public static final int IMAGE = 2;
	public static final int LETTERS_PER_WORD = 5;
	public static final String SETTINGS_FILE_DEF = "config" + File.separator + "content extractor settings.ini";
	public static final String NEWS_SETTINGS_FILE_DEF = "config" + File.separator + "news.ini";
	public static final String NEWS_HP_SETTINGS_FILE_DEF = "config" + File.separator + "news_hp.ini";
	public static final String SHOPPING_SETTINGS_FILE_DEF = "config" + File.separator + "shopping.ini";
	public static final String GOVERNMENT_SETTINGS_FILE_DEF = "config" + File.separator + "government.ini";
	public static final String EDUCATION_SETTINGS_FILE_DEF = "config" + File.separator + "education.ini";
	public static final String TEXT_HEAVY_SETTINGS_FILE_DEF = "config" + File.separator + "text heavy.ini";
	public static final String LINK_HEAVY_SETTINGS_FILE_DEF = "config" + File.separator + "link heavy.ini";
	public static final String AUTOMATIC_SETTINGS_FILE_DEF = "config" + File.separator + "automatic.ini";
	public static final String CUSTOM_SETTINGS_FILE_DEF = "config" + File.separator + "custom.ini";
	
	public static final String LEVEL1_SETTINGS_FILE_DEF = "config" + File.separator + "level1.ini";
	public static final String LEVEL2_SETTINGS_FILE_DEF = "config" + File.separator + "level2.ini";
	public static final String LEVEL3_SETTINGS_FILE_DEF = "config" + File.separator + "level3.ini";
	public static final String LEVEL4_SETTINGS_FILE_DEF = "config" + File.separator + "level4.ini";
	public static final String LEVEL5_SETTINGS_FILE_DEF = "config" + File.separator + "level5.ini";
	public static final String LEVEL6_SETTINGS_FILE_DEF = "config" + File.separator + "level6.ini";
	public static final String LEVEL7_SETTINGS_FILE_DEF = "config" + File.separator + "level7.ini";
	public static final String LEVEL8_SETTINGS_FILE_DEF = "config" + File.separator + "level8.ini";
	public static final String LEVEL9_SETTINGS_FILE_DEF = "config" + File.separator + "level9.ini";
	public static final String LEVEL10_SETTINGS_FILE_DEF = "config" + File.separator + "level10.ini";
	public static final String LEVEL11_SETTINGS_FILE_DEF = "config" + File.separator + "level11.ini";
	public static final String LEVEL12_SETTINGS_FILE_DEF = "config" + File.separator + "level12.ini";
	
	public static final String CONTENT_TEXT = "text/plain";
	public static final String CONTENT_HTML = "text/html";

	//Instance variables
	static boolean customLast = false; // if the custom profile was the last selected profile
	private boolean child = false;
	private InputStream mIn; //the inputstream to filter
	private Document mTree; //the DOM tree for HTML
	private LinkedList mLinksSource;
	//hashtable of all the removed link sources
	private LinkedList mLinksText; //hashtable of all the removed link texts
	private LinkedList mLinksSourceAll; //hashtable of ALL link sources
	private LinkedList mLinksTextAll; //hashtable of ALL link texts
	private LinkedList mImagesSource; //hashtable of ALL image sources
	private ProxyFilterSettings mSettingsGUI; //the settings JPanel
	private boolean mCheckChildren;
	//boolean to see if children nodes should be checked
	private String textPrintBuffer; //the line to print when text printing
	private int numberBlankLines; //the number of consecutive blank lines
	private int lengthForTableRemover;
	//the cumulative length of text in a table
	private Node mBodyNode; //the BODY tag node for the link enqueuer

	ContentExtractorSettings settings; // the settings
	ContentExtractorDescriptionGUI descriptionGUI; // the description GUI
	private Vector visitedClusters;
	private boolean detectRandomSurfing = false;
	private String linkToAppend = null;
	private String currentAddress = null;
	private int counter=1; //counts the number of pages to append
	
	/**
	 * Creates a new instance without any input stream and the default settings file.
	 */
	public ContentExtractor() {
		this(null);
		child = false;
	}

	/**
	 * Creates a new instance of ContentExtractor with the default settings file
	 * 
	 * @param iIn
	 *            the input stream of the HTML file
	 */
	public ContentExtractor(final InputStream iIn) {
		// TODO load settings
		child = true;
		settings = ContentExtractorSettings.getInstance();
		mIn = iIn;

		//mSettingsGUI = SettingsEditor.getInstance();
		textPrintBuffer = "";
		numberBlankLines = 0;
		mLinksSource = new LinkedList();
		mLinksText = new LinkedList();
		mLinksSourceAll = new LinkedList();
		mLinksTextAll = new LinkedList();
		mImagesSource = new LinkedList();
		
		visitedClusters = new Vector();
	}

	
	/**
	 * Extracts the content of the html page based on the settings
	 */
	public void extractContent() {
		//HTMLParser parser = new HTMLParser();
		org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();

		if (Crunch3.settings.isVerbose())
			parser.setErrorHandler(new ContentExtractorErrorHandler());

		
		try {

			//Create the input source using the ISO-8859-1 character set
			InputStreamReader reader = new InputStreamReader(mIn, "ISO-8859-1");
			parser.parse(new InputSource(reader));
			mTree = parser.getDocument();
			extractContent(mTree);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void extractContent(final Node iNode) {
		if (child) {
			if (mTree == null)
				mTree = (Document) iNode;
			counter = counter + 1; 
			extract(iNode, mTree);
			
			org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();
			String address = null;
			URL site =null; 
			Document newTree;
			InputStream in;
			
			
			try{
				while((linkToAppend !=null) && (linkToAppend != address) ){
					
					
					System.out.println("*** " + linkToAppend);
					address = linkToAppend;
					linkToAppend = null;
					try{
						site = new URL(address);
					}
					catch(MalformedURLException ex){
						//maybe it's a relative url
						try{
							
							if(currentAddress == null)
								currentAddress = Crunch3.mainWindow.getURL();
								String temp = currentAddress.substring(7);
								
								int index = temp.indexOf("/");
								
								String first = currentAddress.substring(0,index+7);
								
								if(!(first.endsWith("/")) && !(address.startsWith("/"))) first = first + "/";
								site = new URL (first + address);
								
							
						}
						
						catch(Exception e){
							e.printStackTrace();
							//break;
						}
					}
				
					in = site.openStream();
					InputStreamReader reader = new InputStreamReader(in, "ISO-8859-1");
					parser.parse(new InputSource(reader));
					newTree = parser.getDocument();
					linkToAppend = null;
					
					counter++;
					extract(newTree,newTree);
					System.out.println(linkToAppend + ",,,,,,,,,,");
					if(linkToAppend != null) System.out.println("***## " + linkToAppend);
					
					//prettyPrint(newTree, System.out);					
					System.out.println((mTree.getFirstChild()).getNodeName() + "*************");
					appendDocument(newTree, mTree);
					
					
				}
			}
			catch(FileNotFoundException fe){
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			//Appends the links to the bottom of the page
			if (settings.addLinksToBottom)
				addEnqueuedLinks();
		} else {
			new ContentExtractor(null).extractContent(iNode);
		}
	}

	/**
	 * Extracts content and returns text only without changing settings
	 */
	public void extractContentAsText() {
		String lastSetting = settings.getSetting(ContentExtractorConstants.ONLY_TEXT);
		extractContent();
		settings.changeSetting(ContentExtractorConstants.ONLY_TEXT, lastSetting);
	}

	/**
	 * A recursive algorithm that checks through a node's children and filters out what it wants
	 * 
	 * @param iNode
	 *            the node to start checking
	 */
	private void extract(final Node iNode, Document doc) {
		NodeList children = iNode.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				filterNode(children.item(i), doc);
			}
		}
	}

	/**
	 * Examines a node and determines if it should be included in the extracted DOM tree
	 * 
	 * @param iNode
	 *            the node to filter
	 */
	private void filterNode(final Node iNode, Document doc) {
		//Boolean that determines if the the children of the node should be
		// filtered
		mCheckChildren = true;

		//Put the node through the sequence of filters
		passThroughFilters(iNode, doc);

		if (mCheckChildren)
			filterChildren(iNode, doc);
	} //filterNode

	/**
	 * Passes a node through a set of filters
	 * 
	 * @param iNode
	 *            the node to filter
	 */
	private void passThroughFilters(final Node iNode, Document doc) {
		//Check to see if the node is a Text node or an element node and
		//act accordingly
		int type = iNode.getNodeType();

		Node parent = iNode.getParentNode();

		////Get the attributes of the node
		//NamedNodeMap attr = iNode.getAttributes();

		//Element node
		if (type == Node.ELEMENT_NODE) {

			String name = iNode.getNodeName();
						
			//================================================================
			// Set of conditions that just check the nodes without editing or
			// deleting them
			//================================================================

			//Any type of link is encountered
			if (isLink(iNode))
				recordLink(iNode);
			
			if (isImage(iNode))
				recordImage(iNode);

			//================================================================
			// Set of conditions that edit the nodes but don't delete them
			//================================================================

			//<TD|TABLE width=*> removes widths
			if (settings.ignoreCellWidth && (name.equals("TD") || name.equals("TABLE")) ) {
				if (hasAttribute(iNode, "width"))
					removeAttribute(iNode, "width");
			} //if
			
			// removes generic style attributes
			if (settings.ignoreStyleAttributes && hasAttribute(iNode, "style")){
				removeAttribute(iNode, "style");
			}
			
			//<DIV style=*> removes style
			else if (settings.ignoreDivStyles && name.equals("DIV") ) {
				if (hasAttribute(iNode, "style"))
					removeAttribute(iNode, "style");
			} //if

			//================================================================
			//Set of conditionals determining what to ignore and not to ignore
			// (Conditions that DELETE nodes from the DOM tree)
			//================================================================

			if (settings.ignoreAds && isAdLink(iNode) ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<TD> with Link/Text Ratio higher than threshold
			else if (settings.ignoreLinkCells && name.equals("TD") ) {
				testRemoveCell(iNode);
			}

			//<A HREF> with no Images
			else if (settings.ignoreTextLinks && isTextLink(iNode) ) {
				//before you remove, check if has a child with text "next"
				//to append to href link to current document
				
				linkToAppend = getNextLink(iNode);
				
				parent.removeChild(iNode);
				if (settings.addLinksToBottom)
					enqueueLink(iNode);
				mCheckChildren = false;
			}

			//<A HREF> with Images
			else if (settings.ignoreImageLinks && isImageLink(iNode) ) {
				if (settings.displayImageLinkAlts) {
					Node alt = null;
					boolean image = isImage(iNode);

					//Make sure the image link is the image
					if (image)
						alt = createImageLinkAltNode(iNode, doc);
					if (alt != null) {
						parent.getParentNode().insertBefore(alt, iNode.getParentNode());
					} //if

					//Remove the image and the link
					if (image) {
						parent.removeChild(iNode);

						//Only remove the link if there are no more children
						//to prevent NullPointerExceptions
						if (!parent.hasChildNodes())
							parent.getParentNode().removeChild(parent);
					}
				} //if
				else
					parent.removeChild(iNode);
			}

			//<IMG*>
			else if (settings.ignoreImages && name.equals("IMG") && !isImageLink(iNode)) {
				if (settings.displayImageAlts) {
					Node alt = createAltNode(iNode, doc);
					if (alt != null) {
						//Node replaced = parent.insertBefore(alt, iNode);
						parent.insertBefore(alt, iNode);
					} //if
				} //if
				parent.removeChild(iNode);
			}

			//<SCRIPT>
			else if (settings.ignoreScripts && name.equals("SCRIPT") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<NOSCRIPT>
			else if (settings.ignoreNoscriptTags && name.equals("NOSCRIPT") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<NOSCRIPT> removal and save children
			else if (settings.ignoreScripts && name.equals("NOSCRIPT") ) {
				if (iNode.hasChildNodes()) {
					Node current = iNode.getFirstChild();
					while (current != null) {
						Node next = current.getNextSibling();
						//reinsert child before NOSCRIPT node
						parent.insertBefore(current, iNode);
						current = next;
					} //while
				} //if
				parent.removeChild(iNode);
			} //else if
				
			//<LINK> (Ignore External Stylesheets)
			else if (settings.ignoreExternalStylesheets && name.equals("LINK") && getAttribute(iNode, "rel").equalsIgnoreCase("stylesheet")) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}
			
			//<STYLE>
			else if (settings.ignoreStyles && name.equals("STYLE") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<META>
			else if (settings.ignoreMeta && name.equals("META") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<FORM>
			else if (settings.ignoreForms && name.equals("FORM") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<INPUT>
			else if (settings.ignoreInputTags && name.equals("INPUT") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<BUTTON>
			else if (settings.ignoreButtonTags && name.equals("BUTTON") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<SELECT>
			else if (settings.ignoreSelectTags && name.equals("SELECT") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<IFRAME>
			else if (settings.ignoreIFrameTags && name.equals("IFRAME") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}

			//<TABLE>
			else if (settings.removeEmptyTables && name.equals("TABLE") ) {
				//Call method that removes empty tables
				removeEmptyTables(iNode, doc);
				mCheckChildren = false;
			} //else if

			//<EMBED>
			else if (settings.ignoreEmbedTags && name.equals("EMBED") ) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			} //else if
			
			//<BODY>
			else if (name.equals("BODY"))
				mBodyNode = iNode;
			
			//FLASH
			if (settings.ignoreFlash && isFlash(name, iNode)) {
				parent.removeChild(iNode);
				mCheckChildren = false;
			}
			
		} //if (type == Node.ELEMENT_NODE)

		//Text node
		else if (type == Node.TEXT_NODE) {
			
			
			//================================================================
			//Set of conditions determining what text to ignore
			//================================================================

			//none so far

		} //else if

	}

	/**
	 * Filter child nodes
	 * 
	 * @param iNode
	 *            the node to filter the children
	 */
	private void filterChildren(final Node iNode, Document doc) {
		if (iNode.hasChildNodes()) {
			Node next = iNode.getFirstChild();
			while (next != null) {
				Node current = next;
				next = current.getNextSibling();
				filterNode(current, doc);
			}
		}
	} //filterChildren

	/**
	 * Removes empty tables
	 * 
	 * @param iNode
	 *            the table node to examine
	 */
	private void removeEmptyTables(final Node iNode, Document doc) {
		//First filter the children but check for
		//undeleted nodes
		if (iNode.hasChildNodes()) {
			Node next = iNode.getFirstChild();

			while (next != null) {
				Node current = next;
				next = current.getNextSibling();
				filterNode(current,doc);
			} //while
		} //if

		//Check to see if the table is actually empty
		//but reset length recognizer
		lengthForTableRemover = 0;
		boolean empty = processEmptyTable(iNode);

		if (empty)
			iNode.getParentNode().removeChild(iNode);

	} //removeEmptyTables

	/**
	 * Recursively check children nodes to see if the table is empty
	 * 
	 * @param iNode
	 *            the node to recursively check.
	 * @return true if the nodes are empty, false if they are not
	 */
	private boolean processEmptyTable(final Node iNode) {
		//The variable that determines if the table is empty
		boolean empty = true;

		//Determine the type of the node
		int type = iNode.getNodeType();
		String name = iNode.getNodeName();

		//If it is an element
		if (type == Node.ELEMENT_NODE) {
			//Check to make sure if there are any elements that have
			//substance according to what settings are set
			if (name.equalsIgnoreCase("IMG") && settings.substanceImage)
				empty = false;
			else if (name.equalsIgnoreCase("A") && settings.substanceLinks)
				empty = false;
			else if (name.equalsIgnoreCase("BUTTON") && settings.substanceButton)
				empty = false;
			else if (name.equalsIgnoreCase("FORM") && settings.substanceForm)
				empty = false;
			else if (name.equalsIgnoreCase("IFRAME") && settings.substanceIFrame)
				empty = false;
			else if (name.equalsIgnoreCase("INPUT") && settings.substanceInput)
				empty = false;
			else if (name.equalsIgnoreCase("SELECT") && settings.substanceSelect)
				empty = false;
			else if (name.equalsIgnoreCase("TEXTAREA") && settings.substanceTextarea)
				empty = false;
		} //if

		else if (type == Node.TEXT_NODE) {
			//Trim the text and make sure there is no more substance
			lengthForTableRemover += iNode.getNodeValue().trim().length();
			if (lengthForTableRemover >= settings.substanceMinTextLength)
				empty = false;
		} //else if

		//Process the children
		if (iNode.hasChildNodes()) {
			Node next = iNode.getFirstChild();

			while (next != null && empty) {
				Node current = next;
				next = current.getNextSibling();
				empty = processEmptyTable(current);
			} //while
		} //if

		return empty;

	} //processEmptyTable

	/**
	 * Creates a new node from an image link node that creates a link to the image and the target of the image link.
	 * 
	 * @param iNode
	 *            the <IMG>node that is within the <A>tag
	 * @return the new node or null if something went wrong
	 */
	private Node createImageLinkAltNode(final Node iNode, Document doc) {
		boolean imageMap = false;

		//Make sure it is an image link and an image
		if (!isImage(iNode))
			return null;
		if (!isImageLink(iNode))
			return null;

		//Determine if there is an ALT tag
		String altTag = "";
		Node attr = iNode.getAttributes().getNamedItem("alt");
		if (attr == null || attr.getNodeValue().trim() == "")
			altTag = "-Link-";
		else
			altTag = attr.getNodeValue();

		//Determine the source of the image
		String imageSource = "";
		Node attrSource = iNode.getAttributes().getNamedItem("src");
		if (attrSource == null)
			return null;
		else if (attrSource.getNodeValue().trim() == "")
			return null;
		else
			imageSource = attrSource.getNodeValue();

		//Determine the href of the link
		String linkHref = "";
		Node link = iNode.getParentNode();
		Node hrefAttribute = link.getAttributes().getNamedItem("href");
		if (hrefAttribute != null)
			linkHref = hrefAttribute.getNodeValue();
		else {
			//TODO Handle image maps
			linkHref = "#";
			imageMap = true;
		}

		if (linkHref == null)
			return null;
		else if (linkHref.trim() == "")
			return null;

		//CONSTRUCT REPLACEMENT NODE
		Element parent = doc.createElement("B");
		Element italic = doc.createElement("I");
		Element imageLink = doc.createElement("A");
		imageLink.setAttribute("href", imageSource);
		Element altLink = doc.createElement("A");
		altLink.setAttribute("href", linkHref);
		Node openBracket = doc.createTextNode("[");
		Node closeBracket = doc.createTextNode("]");
		Node seperator = doc.createTextNode(" | ");
		Node imageLinkText;
		if (imageMap)
			imageLinkText = doc.createTextNode("Image Map");
		else
			imageLinkText = doc.createTextNode("Image");
		Node altLinkText = doc.createTextNode(altTag);

		//Link together nodes
		parent.appendChild(openBracket);
		parent.appendChild(imageLink);
		imageLink.appendChild(imageLinkText);
		parent.appendChild(seperator);
		parent.appendChild(italic);
		italic.appendChild(altLink);
		altLink.appendChild(altLinkText);
		parent.appendChild(closeBracket);

		//Return node
		return parent;
	} //createImageLinkAltNode

	/**
	 * Creates a new node that creates a link to an image node using ALT text
	 * 
	 * @param iNode
	 *            the image node
	 * @return the node to add to the DOM tree or null if the node isn't an image or doesn't have an ALT attribute.
	 */
	private Node createAltNode(final Node iNode, Document mTree) {
		if (!isImage(iNode))
			return null;

		//Determine if there is an ALT tag
		Node attr = iNode.getAttributes().getNamedItem("alt");
		if (attr == null)
			return null;
		if (attr.getNodeValue().trim() == "")
			return null;

		//Determine if there is a src
		Node attrLink = iNode.getAttributes().getNamedItem("src");
		if (attrLink == null)
			return null;

		//Create new link node
		Element altNode = mTree.createElement("A");

		//Add text
		altNode.setAttribute("href", attrLink.getNodeValue());

		//Bold Element
		Node bold = mTree.createElement("B");
		Node textNode = mTree.createTextNode("[" + attr.getNodeValue() + "]");
		bold.appendChild(textNode);
		altNode.appendChild(bold);
		try {
			altNode.setNodeValue("");
		} catch (DOMException e) {
			e.printStackTrace();
		}

		return altNode;
	} //getAltNode

	/**
	 * Determines if a node has a link to an ad
	 * 
	 * @param iNode
	 *            the node to check for ads
	 * @return true if the node is a link to an ad, or false if it isn't
	 */
	private boolean isAdLink(final Node iNode) {
		String attr = null;

		if (hasAttribute(iNode, "href"))
			attr = "href";
		else if (hasAttribute(iNode, "src"))
			attr = "src";
		else
			// Doesn't had the required attributes
			return false;

		//Get the address of the potential ad
		Node attrNode = iNode.getAttributes().getNamedItem(attr);
		String address = attrNode.getNodeValue();

		try {
			URL addressURL = new URL(address);
			String host = addressURL.getHost();

			return AdsServerList.isAdsServer(host);

		} catch (Exception e) {
			//Don't do anything because if the URL is malformed, it
			//probably doesn't point towards an advertisement domain
		} //catch

		return false;

	} //isAdLink

	/**
	 * Removes an attribute if the attrbiute exists from an Element node
	 * 
	 * @param iNode
	 *            the node
	 * @param iAttr
	 *            the name of the attribute
	 */
	private void removeAttribute(final Node iNode, final String iAttr) {
		iNode.getAttributes().removeNamedItem(iAttr);
	} //removeAttribute

	/**
	 * Adds an attribute to an Element node
	 * 
	 * @param iNode
	 *            the node
	 * @param iName
	 *            the name of the attribute
	 * @param iValue
	 *            the value of the attribute
	 */
	private void addAttribute(final Node iNode, final String iName, final String iValue) {
		Attr attr = mTree.createAttribute(iName);
		attr.setValue(iValue);
		iNode.getAttributes().setNamedItem(attr);
	} //addAttribute

	/**
	 * Checks to see if an attribute exists in an Element node
	 * 
	 * @param iNode
	 *            the node
	 * @param iAttr
	 *            the name of the attribute to check for
	 * @return true if the attribute exists, false if it doesn't
	 */
	private boolean hasAttribute(final Node iNode, final String iAttr) {
		Node attr = iNode.getAttributes().getNamedItem(iAttr);
		if (attr == null)
			return false;
		else
			return true;
	} //hasAttribute
	
	/**
	 * Gets the attribute if it exists, otherwise, it returns an empty string.
	 * 
	 * @param iNode the node
	 * @param iAttr the name of the attribute the get
	 * @return the value of the attribute, "" otherwise
	 */
	private String getAttribute(final Node iNode, final String iAttr) {
		Node attr = iNode.getAttributes().getNamedItem(iAttr);
		if (attr == null)
			return "";
		
		String value = attr.getNodeValue();
		
		if (value == null)
			return "";
		
		return value;
	}
	
	/**
	 * Removes a table cell if the link ratio is appropriate
	 * 
	 * @param iNode
	 *            the table cell node
	 */
	public void testRemoveCell(final Node iNode) {
		//Ignore if the cell has no children
		if (!iNode.hasChildNodes())
			return;

		double links;
		double words;
		int type = ALL;

		if (settings.ignoreLCImageLinks && settings.ignoreLCTextLinks)
			type = ALL;
		else if (settings.ignoreLCImageLinks)
			type = IMAGE;
		else if (settings.ignoreLCTextLinks)
			type = TEXT;

		//Count up links and words
		links = getNumLinks(iNode, type);
		words = getNumWords(iNode);

		//Compute the ratio and check for divide by 0
		double ratio = 0;
		if (words == 0)
			ratio = settings.linkTextRatio + 1;
		else
			ratio = links / words;

		if (ratio > settings.linkTextRatio) {
			Node next = iNode.getFirstChild();
			while (next != null) {
				Node current = next;
				next = current.getNextSibling();

				//Check to see if only text and link nodes should be removed
				if (settings.ignoreLCOnlyLinksAndText) {
					removeLinksAndText(current, type);
				} else {
					Node next2 = iNode.getFirstChild();
					while (next2 != null) {
						Node current2 = next;
						next2 = current2.getNextSibling();
						removeAll(current2);
					} //while

					//Don't check the children because they are all removed
					mCheckChildren = false;
				} //else
			}
		}
	} //testRemoveCell

	/**
	 * Recursive function that removes everything
	 * 
	 * @param iNode
	 *            the node to start removing children from
	 */
	private void removeAll(final Node iNode) {
		if(isTextLink(iNode)) linkToAppend = getNextLink(iNode);
		if (isTextLink(iNode) && settings.addLinksToBottom) {
			enqueueLink(iNode);
		} else {
			Node next = iNode.getFirstChild();
			while (next != null) {
				Node current = next;
				next = current.getNextSibling();
				removeAll(current);
			} //while
		} //while

		iNode.getParentNode().removeChild(iNode);
	} //removeChild

	/**
	 * Recursive function that removes links and text nodes
	 * 
	 * @param iNode
	 *            the node to edit
	 * @param iType
	 *            the type of links to remove
	 */
	private void removeLinksAndText(final Node iNode, final int iType) {
		if(isTextLink(iNode)) linkToAppend = getNextLink(iNode);
		if (isLink(iNode) || iNode.getNodeType() == Node.TEXT_NODE) {
			if (iType == ALL)
				iNode.getParentNode().removeChild(iNode);
			else if (iType == IMAGE && isImageLink(iNode))
				iNode.getParentNode().removeChild(iNode);
			else if (iType == TEXT && !isImageLink(iNode))
				iNode.getParentNode().removeChild(iNode);
			else if (isTextLink(iNode) && settings.addLinksToBottom)
				enqueueLink(iNode);
		} else {
			Node next = iNode.getFirstChild();
			while (next != null) {
				Node current = next;
				next = current.getNextSibling();
				removeLinksAndText(current, iType);
			} //while
		} //else
	} //isDeeperLink

	/**
	 * Determines if a domain is an ad domain
	 * 
	 * @param iDomain
	 *            the the domain to check
	 * @return true if the domain is an ad domain, false if it is not.
	 */
	private boolean isAdDomain(final String iDomain) {
		return AdsServerList.isAdsServer(iDomain);
	} //isAdDomain

	
	private String getNextLink(final Node iNode){
		Node temp = iNode.getFirstChild(); 
		if((temp != null) && (temp.getNodeType() == Node.TEXT_NODE)){
			String num = Integer.toString(counter);
			String text = ((temp.getNodeValue()).trim()).toLowerCase();
			if ((text.startsWith("next")) || (text.trim()).startsWith(num)){
				String s = ((Element)iNode).getAttribute("href");
				return s;
			}
		}
		if(linkToAppend == null) return null;
		else return linkToAppend;
	}
	
	
	/**
	 * Determines if a given node contains a given attribute
	 * 
	 * @param iNode
	 *            the node to check for the attribute
	 * @param attribute
	 *            the attribute the check for in the node
	 * @return true if the node contains the attribute
	 */
	private boolean nodeContainsAttribute(final Node iNode, final String attribute) {
		if (iNode == null)
			return false;

		NamedNodeMap map = iNode.getAttributes();
		
		if (map == null)
			return false;

		int length = map.getLength();
		for (int i = 0; i < length; i++) {
			if (attribute.equalsIgnoreCase(map.item(i).getNodeName()))
				return true;
		}
		return false;
	}

	/**
	 * Counts the number of links from one node downward
	 * 
	 * @param iNode
	 *            the node to start counting from
	 * @param iType
	 *            the type of links to count.
	 * @return the number of links
	 */
	private double getNumLinks(final Node iNode, final int iType) {
		double links = 0;

		if (iNode.hasChildNodes()) {
			Node next = iNode.getFirstChild();

			while (next != null) {
				Node current = next;
				next = current.getNextSibling();
				links += getNumLinks(current, iType);
			}
		}

		switch (iType) {
			case ALL :
				if (isLink(iNode))
					links++;
				break;
			case TEXT :
				if (isTextLink(iNode))
					links++;
				break;
			case IMAGE :
				if (isImageLink(iNode))
					links++;
				break;
		} //switch

		return links;
	} //getNumLinks

	/**
	 * Checks to see if a node is a link
	 * 
	 * @param iNode
	 *            the node to check
	 * @return true if the node is a link, false if it is not
	 */
	private boolean isLink(final Node iNode) {
		//Check to see if the node is a Text node or an element node
		int type = iNode.getNodeType();

		//Element node
		if (type == Node.ELEMENT_NODE) {
			String name = iNode.getNodeName();

			//Check to see if it is a link
			if ( name.equals("A") )
				return ((Element)iNode).hasAttribute("href");
			
		} //if

		return false;
	}

	/**
	 * Checks to see if a node is a link with an image as the link or if the node is an image, it checks if it is a link
	 * 
	 * @param iNode
	 *            the node to check
	 * @return true if the node is a link with an image, false if it is not
	 */
	private boolean isImageLink(final Node iNode) {
		boolean imageLink = false;

		//Check to see if the node is a link
		if (isLink(iNode)) {

			//Check to see if the children have an image in it
			if (iNode.hasChildNodes()) {
				Node next = iNode.getFirstChild();

				while (next != null && !imageLink) {
					Node current = next;
					next = current.getNextSibling();
					if (isImage(current))
						//imageLink = true;
						return true;

				} //while
			} //if
		} //if
		//If the node is an image, check if its parent is a link
		else if (isImage(iNode)) {
			if (isLink(iNode.getParentNode()))
				//imageLink = true;
				return true;
			else {
				// check for image maps
				if (nodeContainsAttribute(iNode, "usemap"))
					//imageLink = true;
					return true;
			}

		} //else if

		return imageLink;
	} //isImageLink

	/**
	 * Checks to see if a node is an image
	 * 
	 * @param iNode
	 *            the node to check
	 * @return true if the node is an image, false if it is not
	 */
	private boolean isImage(final Node iNode) {
		boolean image = false;

		//Check to see if the node is an image
		int type = iNode.getNodeType();
		if (type == Node.ELEMENT_NODE) {
			if (iNode.getNodeName().equals("IMG"))
				image = true;
		} //if

		return image;
	}

	/**
	 * Determines if a link is a text link
	 * 
	 * @param iNode
	 *            the node to analyze
	 * @return true if the node is a text link and false if it is not.
	 */
	private boolean isTextLink(final Node iNode) {
		return !isImageLink(iNode) && isLink(iNode);
	} //isTextLink

	/**
	 * Counts the number of links from one node downward
	 * 
	 * @param iNode
	 *            the node to start counting from
	 * @return the number of links
	 */
	private double getNumWords(final Node iNode) {
		double words = 0;

		if (iNode.hasChildNodes()) {
			Node next = iNode.getFirstChild();

			while (next != null) {
				Node current = next;
				next = current.getNextSibling();

				//If it is a link, don't go any deeper into it
				if (!isLink(current))
					words += getNumWords(current);
			}
		}

		//Check to see if the node is a Text node or an element node
		int type = iNode.getNodeType();

		//Text node
		if (type == Node.TEXT_NODE) {
			String content = iNode.getNodeValue();
			words += ((double) content.length()) / LETTERS_PER_WORD;
		} //if

		return words;
	} //getNumWords

	/**
	 * Prepares a link node to be added to the bottom of the page by adding it to the Hashtable
	 * 
	 * @param iLinkNode
	 *            the link node to add o the bottom of the page
	 */
	private void enqueueLink(final Node iLinkNode) {
		//Make sure the node is a link
		if (!isTextLink(iLinkNode))
			return;

		//Get the source of the text link
		Node hrefAttribute = iLinkNode.getAttributes().getNamedItem("href");
		String source = "";
		if (hrefAttribute != null)
			source = hrefAttribute.getNodeValue();
		else
			// TODO implement image map link grabbing
			source = "IMAGE_MAP";

		String text = iLinkNode.getFirstChild().getNodeValue();

		if (source != null && text != null) {
			mLinksSource.add(source);
			mLinksText.add(text);
		}
	} //enqueueLink

	/**
	 * Records the presence of a link
	 * 
	 * @param iLinkNode
	 *            the link node
	 */
	private void recordLink(final Node iLinkNode) {
		//Make sure the node is a link
		if (!isLink(iLinkNode))
			return;
		
		//Get the source of the text link
		Node sourceNode = iLinkNode.getAttributes().getNamedItem("href");
		Node textNode = iLinkNode.getFirstChild();

		String source = null;
		String text = null;

		if (sourceNode != null)
			sourceNode.getNodeValue();

		if (textNode != null)
			text = textNode.getNodeValue();

		if (source != null && text != null) {
			mLinksSource.add(source);
			mLinksText.add(text);
		}
	} //recordLink

	/**
	 * Records the presence of an image
	 * 
	 * @param iImageNode
	 *            the image node
	 */
	private void recordImage(final Node iImageNode) {
		//Make sure the node is an image
		if (!isImage(iImageNode))
			return;

		//get source of the image
		String source = null;

		Node sourceNode = iImageNode.getAttributes().getNamedItem("src");

		if (sourceNode != null)
			source = sourceNode.getNodeValue();

		if (source != null) {
			mImagesSource.add(source);
		}
	}

	/**
	 * Returns a linked list containing all the image sources
	 * 
	 * @return a linked list containing all the image sources
	 */
	public LinkedList getImageSources() {
		return mImagesSource;
	}

	/**
	 * Returns a linked list containing all the link sources
	 * 
	 * @return a linked list containing all the link sources
	 */
	public LinkedList getLinkSources() {
		return mLinksSourceAll;
	}

	/**
	 * Returns a linked list containing all the link text
	 * 
	 * @return a linked list containing all the link text
	 */
	public LinkedList getLinkText() {
		return mLinksTextAll;
	}
	
	/**
	 * Returns true if the tag is associated with a flash movie
	 * @param name the name of the node
	 * @param iNode the node
	 * @return whether or not the node is associated with a flash movie
	 */
	public boolean isFlash(final String name, final Node iNode) {
		if (name.equals("OBJECT")) {
			if(hasAttribute(iNode, "classid")){
				String classid = getAttribute(iNode, "classid");
				if(classid.trim().equalsIgnoreCase("clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"))
					return true;
			}
		}
		else if (name.equals("PARAM")) {
			if(hasAttribute(iNode, "value")){
				String value = getAttribute(iNode, "value");
				if(value.trim().toLowerCase().endsWith(".swf"))
					return true;
			}
		}
		else if (name.equals("EMBED")) {
			if(hasAttribute(iNode, "src")) {
				String src = getAttribute(iNode, "src");
				if(src.trim().toLowerCase().endsWith(".swf"))
					return true;
			}
		}
		return false;
	}
	
	
	private void appendDocument(Document from, Document to){
	
		Node fromBody = ((from.getFirstChild()).getFirstChild());
		
		System.out.println(from.getNodeName());
		
		while (!((fromBody.getNodeName()).equals("BODY")) && !(fromBody == null)){
			fromBody = fromBody.getNextSibling();
		}
		
		
		from.removeChild(from.getFirstChild());
		
		Node toBody = ((to.getDocumentElement()).getFirstChild()); 
		
		
		while (!((toBody.getNodeName()).equals("BODY")) && !(toBody == null)){
			toBody = toBody.getNextSibling();
		}
		
		append(to,fromBody, toBody);
		
		
	}
	
	
	
	private void append(Document doc, Node n, Node to){
		
		NodeList children = n.getChildNodes();
		Node newNode;
		for (int i=0; i<children.getLength();i++){
			try{
				newNode = doc.importNode(children.item(i),false);
				to.appendChild(newNode);
				append(doc,children.item(i), newNode);
			}
			catch(Exception e){
				System.out.println(children.item(i));
			}
			
			
		}
	}
	
	
	/**
	 * Add enqueued links to bottom of page
	 */
	private void addEnqueuedLinks() {
		//Make sure the body node isn't null
		if (mBodyNode == null)
			return;

		//Make sure there are links enqueued
		if (mLinksSource.size() == 0)
			return;
		if (mLinksText.size() == 0)
			return;

		//Start adding formating
		Element center = mTree.createElement("CENTER");
		Element table = mTree.createElement("TABLE");
		table.setAttribute("cellpadding", "5");
		table.setAttribute("width", "100%");
		Element tablerow = mTree.createElement("TR");
		Element tablecell = mTree.createElement("TD");
		tablecell.setAttribute("bgcolor", "white");
		Element headerTag = mTree.createElement("H3");
		Node header = mTree.createTextNode("Removed Links:");

		//Append them
		mBodyNode.appendChild(table);
		table.appendChild(tablerow);
		tablerow.appendChild(tablecell);
		tablecell.appendChild(center);
		center.appendChild(headerTag);
		headerTag.appendChild(header);

		Iterator itrSource = mLinksSource.listIterator(0);
		Iterator itrText = mLinksText.listIterator(0);

		//Add links
		while (itrSource.hasNext() && itrText.hasNext()) {
			String source = (String) itrSource.next();
			String text = (String) itrText.next();

			Element link = mTree.createElement("A");
			link.setAttribute("href", source);
			link.setAttribute("style", "color: blue");
			link.appendChild(mTree.createTextNode(text));
			tablecell.appendChild(link);
			tablecell.appendChild(mTree.createElement("BR"));
		} //while

		//Purge the enqueued Links
		mLinksSource.clear();
		mLinksText.clear();
	} //addEnqueuedLinks

	/**
	 * Returns the Document object
	 * 
	 * @return the Document object of the DOM tree representing the HTML file
	 */
	public Document getTree() {
		return mTree;
	}

	/**
	 * 
	 * Prints only the text without any of the tags of the DOM tree
	 * 
	 * @param iOutputStream
	 *            the output stream
	 *  
	 */
	public void textPrint(final OutputStream iOutputStream) {
		PrintWriter output = new PrintWriter(iOutputStream);
		textPrint(mTree, output);
		output.close();
	} //textPrint

	/**
	 * Prints only the text without any of the tags of the DOM tree
	 * 
	 * @param iDOMTree
	 *            the DOM Document module to print without any tags
	 * @param iWriter
	 *            the PrintWriter
	 */
	private void textPrint(final Node iDOMTree, final PrintWriter iWriter) {
		//Print child nodes first
		if (iDOMTree.hasChildNodes()) {
			Node next = iDOMTree.getFirstChild();

			while (next != null) {
				Node current = next;
				next = current.getNextSibling();

				//=====Filter out what is not really text=====//
				String name = current.getNodeName();
				boolean valid = true;

				//Styles should not be treated as text
				if (name.equalsIgnoreCase("STYLE"))
					valid = false;

				//Scripts should not be treated as text either
				else if (name.equalsIgnoreCase("SCRIPT"))
					valid = false;

				//============================================//

				//Perform recursive function
				if (valid)
					textPrint(current, iWriter);
			} //while
		} //if

		//Check to see if the node is a Text node or an element node
		int type = iDOMTree.getNodeType();

		//Element node
		if (type == Node.ELEMENT_NODE) {
			//if the node is <BR>, then print a line break
			if (iDOMTree.getNodeName().equalsIgnoreCase("BR")) {
				flush(iWriter);
			}
		} //else if

		//Text node
		else if (type == Node.TEXT_NODE) {
			//Print the text nodes to the output stream.
			if (!(iDOMTree.getNodeValue().trim().equals(""))) {
				textPrintBuffer += iDOMTree.getNodeValue();
			}
		} //if
	} //textPrint

	/**
	 * Flushs the buffered line and prints it out depending on the number of consecutive blank lines. This method also keeps track of the number of consecutive
	 * blank lines.
	 * 
	 * @param iWriter
	 *            the PrintWriter to flush the buffer to
	 */
	private void flush(final PrintWriter iWriter) {
		boolean blank = false;

		//Check to see if the buffered line is blank
		if (textPrintBuffer.trim().length() == 0)
			blank = true;

		//Make sure there are not too many consecutive blank lines if
		// necessary
		if (settings.limitLinebreaks) {
			if (blank && numberBlankLines < settings.maxLinebreaks) {
				iWriter.println(textPrintBuffer);
				numberBlankLines++;
			} //if
			else if (!blank)
				iWriter.println(textPrintBuffer);
		} //if

		else
			iWriter.println(textPrintBuffer);

		//Reset the numberBlankLines if the line is not blank
		if (!blank)
			numberBlankLines = 0;
		textPrintBuffer = "";
	} //flush

	/**
	 * Pretty prints the HTML to an OutputStream
	 * 
	 * @param iNode
	 *            the Document to start printing from
	 * @param iOut
	 *            the output stream to print to.
	 */
	public void prettyPrint(final Document iNode, final OutputStream iOut) {
		//Create formating that will indent and print with the proper
		//method specified by the Document object.
		OutputFormat format = new OutputFormat(iNode, "ISO-8859-1", true);

		//Get the printer
		//HTMLSerializer printer = new HTMLSerializer(iOut, format);
		XHTMLSerializer printer = new XHTMLSerializer(iOut, format);

		try {
			printer.serialize(iNode);
			iOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} //catch
	} //prettyPrint
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ContentExtractor [input file] [output file]");
			return;
		}

		FileInputStream streamIn;
		try {
			streamIn = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("ContentExtractor: Input File Not Found");
			return;
		} catch (SecurityException e) {
			System.out.println("ContentExtractor: Read access denied to Input File");
			return;
		}

		ContentExtractor ce;
		ce = new ContentExtractor(streamIn);
		ce.extractContent();

		try {
			File output = new File(args[1]);
			output.createNewFile();
			ce.processNoOverwrite(new File(args[0]), output);
		} catch (IOException e) {
			System.out.println("ContentExtractor: IO Exception");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Returns what the content type of the file is.
	 * 
	 * @return the content type
	 */
	public String getContentType() {
		if (settings.onlyText)
			return CONTENT_TEXT;
		else
			return CONTENT_HTML;
	} //getContentType

	/**
	 * This method processes a File and returns the processed file for the proxy to use.
	 * 
	 * @return the processed file
	 */
	public File process(final File in) throws IOException {
		
		FileInputStream streamIn = new FileInputStream(in);
		mIn = streamIn;
		extractContent();
		streamIn.close();
		FileOutputStream streamOut = new FileOutputStream(in);
		if (!settings.onlyText) {
			prettyPrint(mTree, streamOut);
		} else {
			textPrint(streamOut);
		}
		streamOut.close();
		return in;
	}

	/**
	 * This method processes a File and returns a new file for the proxy to use. Note: the file is not overwritten
	 * 
	 * @param in
	 *            the file to process
	 * @param out
	 *            the output file
	 */
	public File processNoOverwrite(final File in, final File out) throws IOException {
		FileInputStream streamIn = new FileInputStream(in);
		mIn = streamIn;
		extractContent();
		streamIn.close();
		FileOutputStream streamOut = new FileOutputStream(out);
		if (!settings.onlyText) {
			prettyPrint(mTree, streamOut);
		} else {
			textPrint(streamOut);
		}
		streamOut.close();
		return out;
	}

	public boolean hasSettingsGUI() {
		return true;
	}

	public void getSettingsGUI() {
		SettingsEditor.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getName()
	 */
	public String getName() {
		return "Content Extractor";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getDescription()
	 */
	public String getDescription() {
		return "This plugin simplifies web pages by removing excess clutter.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#process(org.w3c.dom.Document, org.w3c.dom.Document, org.w3c.dom.Document)
	 */
	public Document process(Document originalDocument, Document previousDocument, Document currentDocument) {
		extractContent(currentDocument);
		if (settings.onlyText);
		//TODO implement text only output
		return currentDocument;
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.EnhancedProxyFilter#getDescriptionGUI()
	 */
	public Composite getDescriptionGUI(Composite c) {
		// FIXME Auto-generated method stub
		if (descriptionGUI == null)
			descriptionGUI = new ContentExtractorDescriptionGUI(c);
		return descriptionGUI.getComposite();
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.SiteDependentFilter#reportURL(java.lang.String)
	 * updates the generic image and specific image labels
	 */
	public void reportHost(String host) {
		// TODO 
		int index = -1;
		if ((descriptionGUI != null) && (host !=null) )
			index = host.indexOf('.');
			if (index != -1){
				if (host.substring(0,index).equals("www") || host.substring(0,index).equals("www1")) host = host.substring(index+1);
				index = host.lastIndexOf('.');
				if(index != -1) 
					host = host.substring(0,index);
					String beg, end;
					//replace all '.' with _ 
					while (host.indexOf('.') != -1){
						index = host.indexOf('.');
						beg = host.substring(0, index);
						end = host.substring(index+1);
						host = beg + "_" + end;
					}
				
				host = host.toLowerCase().trim().concat(".jpg");
				descriptionGUI.updateSpecificImage(host);
				
			
				//randomly choose a generic image
				int rand = (int)(Math.random()*5.0);
				switch(rand){
					case 0: descriptionGUI.updateGenericImage("basic.jpg");
							break;
					case 1: descriptionGUI.updateGenericImage("multibodywithadvertisement.jpg");
							break;
					case 2: descriptionGUI.updateGenericImage("multisidemenu.jpg");
							break;
					case 3: descriptionGUI.updateGenericImage("singlebody.jpg");
							break;
					case 4: descriptionGUI.updateGenericImage("threecolumn.jpg");
							break;
				    default: descriptionGUI.updateGenericImage("");
				
				}
				
			}	
	}
	
	
	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.SiteDependentFilter#reportReferrer(java.lang.String)
	 */
	public void reportReferer(String referrer) {
		// TODO Auto-generated method stub
		if (descriptionGUI != null)
			descriptionGUI.updateReferrerText(referrer);
		
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.SiteDependentFilter#reportApplication(java.lang.String)
	 */
	public void reportApplication(String application) {
		// TODO Auto-generated method stub
		if (descriptionGUI != null)
			descriptionGUI.updateApplicationText(application);
	}
	

	/**
	 * computes extraction settings by caluculating the distance of the current URL
	 * to the clusters generated by the preprocessor WordCount.java
	 * also checks whether a page is a homepage using the heuristics in HomePageTestser.java
	 */
	public void reportURL(String URL){
		
		currentAddress = URL;
		
		//if (!Crunch3.settings.isGUISet()) return;
		psl.crunch3.util.HomePageTester hpt = new psl.crunch3.util.HomePageTester(Crunch3.mainWindow.getURL().trim());
		if (Crunch3.settings.isVerbose()){
			if (hpt.isHomePage())
				System.out.println("This is A Homepage!");
			else System.out.println("This is not a Homepage");
		}
		
		//handles frontpage detection for GUI-less crunch
		if(descriptionGUI == null) {
			 
			if(Crunch3.settings.isHomePageCheck()){
			if( hpt.isHomePage()){
			 	 Crunch3.mainWindow.setGUIHomepage();
			 }
			 else {
			 	 Crunch3.mainWindow.setGUINoHomepage();
			 }
			}
			return;
		}
		
		//compute closes cluster to current URL and apply appropriate filter settings
		if (descriptionGUI.isAuto()){
			
			int cluster = 0;
			
			System.out.println("******************************* "+ (WordCount.parseURL(URL.substring(7),true)));
			
			//check if the site is already clustered.
			if((cluster = descriptionGUI.getCluster(WordCount.parseURL(URL.substring(7),true))) != 0){
				System.out.println(URL +" is already clustered");
			}
			else{
				//create a new wordcount object that is used to generate the word-frequency map for the current site
				WordCount wc = new WordCount(URL.substring(7), descriptionGUI.getFrequencies(),
						descriptionGUI.getKeys(), descriptionGUI.getSites(), descriptionGUI.getEngineNumber());
				
				//get the site closest to URL from preclustered list
				String closest = wc.getClosestSite();
				if(closest !=null)
					cluster = descriptionGUI.getCluster(closest);
				else cluster = 0;
			}
			
			//keep track of visited clusters for random surfing detection
		    visitedClusters.addElement(new Integer(cluster));
		    if(visitedClusters.size()>3){
		    	visitedClusters.removeElementAt(0);
		    }
		    
		    //apply filter settings based on the cluster number
		    applySettings(cluster);

			
			//if the page is a news home page relax the settings
			if(hpt.isHomePage()){
				int level = descriptionGUI.getSettingLevel();
				if(level ==2){
					level+=4;
					System.out.println("Setting level " + level);
					descriptionGUI.commitSettings("config" + File.separator + "level" + level + ".ini", level);
				}
			}
			
			if((visitedClusters.size()==3) && detectRandomSurfing){
			//if surfing is random then relax the setting
				if(isRandomSurfing()){
					System.out.println("Random Surfing Detected");
					relax(); 
				}
			}
			
			
		}
		
		//if settings aren't automatic, just apply regular front-page detection proc
		else{
			if(descriptionGUI.checkFrontPage() && hpt.isHomePage()){
				
				if((descriptionGUI.getSettingsLabel()).equals("news")){
					descriptionGUI.commitSettings("config" + File.separator + "level6.ini", 6);
					descriptionGUI.setSettingsLevel(6);
				}
				
				
			}
			else if(descriptionGUI.checkFrontPage() && !hpt.isHomePage()){
				if((descriptionGUI.getSettingsLabel()).equals("news")){
					descriptionGUI.commitSettings("config" + File.separator + "level2.ini", 2);
					descriptionGUI.setSettingsLevel(2);
				}
			}
		}
	}
	
	
	private void relax(){
		descriptionGUI.commitSettings("config" + File.separator + "level" + 9 + ".ini", 9);
	}
	
	
	/**
	 * Selects the custom button and deselects any other options in the description GUI
	 */
	public void selectCustom(){
		descriptionGUI.selectCustom();
	}
	
	
	/**
	 * Apply the correct filter settings determined manually, given the cluster number. 
	 * @param cluster
	 */
	private void applySettings(int cluster){
		switch(cluster){
		
			case 1: descriptionGUI.commitSettings(ContentExtractor.LEVEL10_SETTINGS_FILE_DEF, 10);
					descriptionGUI.updateClassificationLabel("news");
					break;
			case 2: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					break;
			case 3: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("world news");
					break;
			case 4: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("world news");
					break;
			case 5: descriptionGUI.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF , 2);
					descriptionGUI.updateClassificationLabel("news");
					break;
			case 6: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("shopping");
					break;
			case 7: descriptionGUI.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF , 2);
					descriptionGUI.updateClassificationLabel("news");
					break;
			case 8: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("shopping");
					break;
			case 9: descriptionGUI.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF , 2);
					descriptionGUI.updateClassificationLabel("news");
					break;
			case 10: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10); 
					descriptionGUI.updateClassificationLabel("space");
				 	 break;
			case 11: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10); 
					descriptionGUI.updateClassificationLabel("tech news");
					 break;
			case 12: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("gossip");
					 break;
			case 13: descriptionGUI.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 10);
					descriptionGUI.updateClassificationLabel("blogs");
					break;
			case 14: descriptionGUI.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF , 2);
					descriptionGUI.updateClassificationLabel("news");
					 break;
			default: descriptionGUI.commitSettings(ContentExtractor.LEVEL12_SETTINGS_FILE_DEF , 12); 
						descriptionGUI.updateClassificationLabel("misc");
					break;
		
		}
		descriptionGUI.updateSettingsLevel();
	}
	
	
	/**
	 * If the last three sites visited are different from each other, 
	 * switch to randon surfing mode
	 * @return
	 */
	private boolean isRandomSurfing(){
		int current = ((Integer)(visitedClusters.elementAt(2))).intValue();
		int prev1 = ((Integer)(visitedClusters.elementAt(1))).intValue();
		int prev2 = ((Integer)(visitedClusters.elementAt(0))).intValue();
		System.out.println(current + "  " + prev1 + "  "+ prev2);
		if((current != prev1) && (current != prev2)) return true;
		else return false;
	}
	
	
} //ContentExtractor
