/*
 * Copyright (c) 2003: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.sizemodifier;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import psl.crunch3.plugins.ProxyFilter;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class SizeModifier extends ProxyFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#process(java.io.File)
	 */
	public File process(final File in) throws IOException {
		// TODO Implement process(File f)
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getSettingsGUI()
	 */
	public void getSettingsGUI() {
		// TODO Implement settings GUI
		SizeModifierSettingsEditor.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#hasSettingsGUI()
	 */
	public boolean hasSettingsGUI() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getName()
	 */
	public String getName() {
		return "Size Modifier";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getDescription()
	 */
	public String getDescription() {
		// TODO make a better discription
		return "Modifies sizes in a webpage.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#getContentType()
	 */
	public String getContentType() {
		// TODO deal with the content type
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psl.crunch3.plugins.ProxyFilter#process(org.w3c.dom.Document,
	 *      org.w3c.dom.Document, org.w3c.dom.Document)
	 */
	public Document process(final Document originalDocument,
			final Document previousDocument, final Document currentDocument) {
		new SizeModifier(currentDocument).internalProcess();
		return currentDocument;
	}

	Document document;

	SizeModifierSettings settings;

	double imageScaleFactor;

	public SizeModifier() {
		//init code here
	}

	private SizeModifier(final Document document) {
		this.document = document;
		settings = SizeModifierSettings.getInstance();
		imageScaleFactor = settings.getImageRescaleFactor();
	}

	private void internalProcess() {
		if (settings.rescaleImages)
			resizeImageNodes(this.document);
		if (settings.resizeFonts)
			wrapTextNodes(this.document);
	}

	private void wrapTextNodes(final Node n) {
		if (n.getNodeType() == Node.TEXT_NODE) {
			wrapTextNode(n);
			return;
		} else {
			if ("TITLE".equalsIgnoreCase(n.getNodeName()))
				return;
			NodeList nodes = n.getChildNodes();
			Node[] nodeArray = new Node[nodes.getLength()];
			for (int i = 0; i < nodeArray.length; i++)
				nodeArray[i] = nodes.item(i);
			nodes = null;
			for (int i = 0; i < nodeArray.length; i++)
				wrapTextNodes(nodeArray[i]);
		}
	}

	private void wrapTextNode(final Node n) {
		Node parent = n.getParentNode();
		Node wrapperNode = generateWrapperNode();
		parent.replaceChild(wrapperNode, n);
		wrapperNode.appendChild(n);
	}

	private Node generateWrapperNode() {
		Element n = document.createElement("FONT");
		n.setAttribute("size", settings.getFontSize());
		return n;
	}

	private void resizeImageNodes(final Node n) {
		if (n.getNodeType() == Node.TEXT_NODE)
			return;

		String nodeName = n.getNodeName().toUpperCase();

		if (n.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) n;
			if ("IMG".equals(nodeName)) {
				//esizeImageElement(e, scaleFactor);
				resizeFixedOnly(e, imageScaleFactor);
				return;
			} else if ("TABLE".equals(nodeName) || "TR".equals(nodeName)
					|| "TD".equals(nodeName) || "TH".equals(nodeName)) {
				resizeFixedOnly(e, imageScaleFactor);
			}
		}

		NodeList nodes = n.getChildNodes();
		Node[] nodeArray = new Node[nodes.getLength()];
		for (int i = 0; i < nodeArray.length; i++)
			nodeArray[i] = nodes.item(i);
		nodes = null;
		for (int i = 0; i < nodeArray.length; i++)
			resizeImageNodes(nodeArray[i]);
	}

	private void resizeFixedOnly(final Element e, final double scaleFactor) {
		// deal with image widths
		if (e.hasAttribute("width")) {
			e.setAttribute("width", resizeFixedString(e.getAttribute("width"),
					scaleFactor));
		} else if (e.hasAttribute("style")
				&& e.getAttribute("style").indexOf("width:") > -1) {
			resizeStyleString("width:", e.getAttribute("style"), scaleFactor);
		}

		// deal with image heights
		if (e.hasAttribute("height")) {
			e.setAttribute("height", resizeFixedString(
					e.getAttribute("height"), scaleFactor));
		} else if (e.hasAttribute("style")
				&& e.getAttribute("style").indexOf("height:") > -1) {
			resizeFixedStyleString("height:", e.getAttribute("style"),
					scaleFactor);
		}

		//cellspacing cellpadding border
	}

	private String resizeFixedStyleString(final String resizeToken,
			final String s, final double scaleFactor) {
		if (s.indexOf('%') < 0)
			return resizeStyleString(resizeToken, s, scaleFactor);
		else
			return s;
	}

	private String resizeFixedString(final String s, final double scaleFactor) {
		if (s.indexOf('%') < 0)
			return resizeString(s, scaleFactor);
		else
			return s;
	}

	private void resizeImageElement(final Element n, final double scaleFactor) {
		//TODO relative resizing

		// deal with image widths
		if (n.hasAttribute("width")) {
			n.setAttribute("width", resizeString(n.getAttribute("width"),
					scaleFactor));
		} else if (n.hasAttribute("style")
				&& n.getAttribute("style").indexOf("width:") > -1) {
			resizeFixedStyleString("width:", n.getAttribute("style"),
					scaleFactor);
		} else {
			//n.setAttribute("width", resizeFixedString("100%", scaleFactor));
		}

		// deal with image heights
		if (n.hasAttribute("height")) {
			n.setAttribute("height", resizeFixedString(
					n.getAttribute("height"), scaleFactor));
		} else if (n.hasAttribute("style")
				&& n.getAttribute("style").indexOf("height:") > -1) {
			resizeFixedStyleString("height:", n.getAttribute("style"),
					scaleFactor);
		} else {
			//n.setAttribute("height", resizeString("100%", scaleFactor));
		}

	}

	/**
	 * Multiplies the first contiguous chunk of numbers by the scale factor.
	 * Then it rounds the number to the nearest unit and reinserts the number.
	 * 
	 * @param s
	 *            the string containing the number to resize
	 * @param scaleFactor
	 *            the scaling factor (2 for instance doubles the number)
	 * @return the string with the rescaled number
	 */
	private String resizeString(final String s, final double scaleFactor) {
		if (s == null || s.length() == 0)
			return s;

		//find where the number starts and stops
		int numberStart = 0;
		while (numberStart < s.length()
				&& (s.charAt(numberStart) < '0' || s.charAt(numberStart) > '9'))
			numberStart++;

		if (numberStart == s.length())
			return s;

		int numberStop = numberStart;
		while (numberStop < s.length()
				&& (s.charAt(numberStop) >= '0' && s.charAt(numberStop) <= '9'))
			numberStop++;

		//gather prefix, number, postfix
		String prefix = s.substring(0, numberStart);
		String number = s.substring(numberStart, numberStop);
		String postfix = s.substring(numberStop, s.length());

		//scale the number
		double d = Double.parseDouble(number);
		number = String.valueOf((long) (d * scaleFactor + 0.5));

		//put it all back together
		return prefix + number + postfix;
	}

	private String resizeStyleString(final String resizeToken, String s,
			final double scaleFactor) {
		int index = 0;
		String prefix;
		String suffix;

		s = s.toLowerCase();
		index = s.indexOf(resizeToken);
		if (index >= 0) {
			prefix = s.substring(0, index);
			suffix = s.substring(index, s.length());
			s = prefix + resizeString(suffix, scaleFactor);
		}

		return s;
	}
}