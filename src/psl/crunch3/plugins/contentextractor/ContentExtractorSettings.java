/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;
import java.io.*;

import psl.crunch3.Crunch3;
import psl.crunch3.TypedProperties;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ContentExtractorSettings implements ContentExtractorConstants {
	private static ContentExtractorSettings self;

	private String mSettingsFile; //the settings file path
	private TypedProperties mSettings; //the settings properties file

	//Settings variables
	boolean ignoreTextLinks;
	boolean ignoreImages;
	boolean ignoreScripts;
	boolean ignoreExternalStylesheets;
	boolean ignoreStyles;
	boolean ignoreForms;
	boolean ignoreMeta;
	//private int minTextLength;

	//================================================================
	//All the settings for link lists - or link cells
	boolean ignoreLinkCells;

	//LC stands for Link Cells
	boolean ignoreLCImageLinks;
	boolean ignoreLCTextLinks;
	boolean ignoreLCOnlyLinksAndText;
	//End of settings for link lists - or link cells
	//=================================================================

	boolean ignoreImageLinks;
	boolean ignoreInputTags;
	boolean ignoreButtonTags;
	boolean ignoreSelectTags;
	boolean ignoreNoscriptTags;
	boolean ignoreCellWidth;
	boolean ignoreAds;
	boolean onlyText;
	boolean ignoreDivStyles;
	boolean ignoreStyleAttributes;
	boolean ignoreIFrameTags;
	boolean displayImageAlts;
	boolean displayImageLinkAlts;

	////////////////////////// Empty Table Settings
	// ////////////////////////////
	boolean removeEmptyTables;
	boolean substanceImage;
	boolean substanceLinks;
	boolean substanceIFrame;
	boolean substanceInput;
	boolean substanceButton;
	boolean substanceTextarea;
	boolean substanceSelect;
	boolean substanceForm;
	int substanceMinTextLength;
	////////////////////////////////////////////////////////////////////////////

	boolean limitLinebreaks;
	int maxLinebreaks;
	boolean addLinksToBottom;
	boolean ignoreEmbedTags;
	boolean ignoreFlash;

	/*
	 * Link/Text ratio is determined by the amount of text words to single links A word is considered 4 letters long.
	 */
	double linkTextRatio;
	
	// FIXME fix getInstance()
	public static ContentExtractorSettings getInstance() {
		if (self == null)
			self = new ContentExtractorSettings();
		return self;
	}

	private ContentExtractorSettings() {
		this(ContentExtractor.SETTINGS_FILE_DEF);
	}
	private ContentExtractorSettings(String iSettings) {
		mSettingsFile = iSettings;
		mSettings = new TypedProperties();
		loadSettingsProperties();
		loadSettings();
	}

	/**
	 * Returns a setting based on the final variables
	 * 
	 * @param iSetting
	 *            the name of the setting based on the final variables
	 * @return the setting as a string. For boolean values, "true" and "false" will be returned. Null will be returned if the setting doesn't exist
	 */
	public String getSetting(final String iSetting) {
		if (iSetting.equalsIgnoreCase(IGNORE_ADS))
			return Boolean.toString(ignoreAds);
		else if (iSetting.equalsIgnoreCase(IGNORE_BUTTON_TAGS))
			return Boolean.toString(ignoreButtonTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_CELL_WIDTH))
			return Boolean.toString(ignoreCellWidth);
		else if (iSetting.equalsIgnoreCase(IGNORE_DIV_STYLES))
			return Boolean.toString(ignoreDivStyles);
		else if (iSetting.equalsIgnoreCase(IGNORE_FORMS))
			return Boolean.toString(ignoreForms);
		else if (iSetting.equalsIgnoreCase(IGNORE_IFRAME_TAGS))
			return Boolean.toString(ignoreIFrameTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_IMAGE_LINKS))
			return Boolean.toString(ignoreImageLinks);
		else if (iSetting.equalsIgnoreCase(IGNORE_IMAGES))
			return Boolean.toString(ignoreImages);
		else if (iSetting.equalsIgnoreCase(IGNORE_INPUT_TAGS))
			return Boolean.toString(ignoreInputTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_LINK_CELLS))
			return Boolean.toString(ignoreLinkCells);
		else if (iSetting.equalsIgnoreCase(IGNORE_META))
			return Boolean.toString(ignoreMeta);
		else if (iSetting.equalsIgnoreCase(IGNORE_NOSCRIPT_TAGS))
			return Boolean.toString(ignoreNoscriptTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_SCRIPTS))
			return Boolean.toString(ignoreScripts);
		else if (iSetting.equalsIgnoreCase(IGNORE_SELECT_TAGS))
			return Boolean.toString(ignoreSelectTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_EXTERNAL_STYLESHEETS))
			return Boolean.toString(ignoreExternalStylesheets);
		else if (iSetting.equalsIgnoreCase(IGNORE_STYLES))
			return Boolean.toString(ignoreStyles);
		else if (iSetting.equalsIgnoreCase(IGNORE_STYLE_ATTRIBUTES))
			return Boolean.toString(ignoreStyleAttributes);
		else if (iSetting.equalsIgnoreCase(IGNORE_TEXT_LINKS))
			return Boolean.toString(ignoreTextLinks);
		else if (iSetting.equalsIgnoreCase(LC_IGNORE_IMAGE_LINKS))
			return Boolean.toString(ignoreLCImageLinks);
		else if (iSetting.equalsIgnoreCase(LC_IGNORE_TEXT_LINKS))
			return Boolean.toString(ignoreLCTextLinks);
		else if (iSetting.equalsIgnoreCase(LINK_TEXT_REMOVAL_RATIO))
			return Double.toString(linkTextRatio);
		else if (iSetting.equalsIgnoreCase(ONLY_TEXT))
			return Boolean.toString(onlyText);
		else if (iSetting.equalsIgnoreCase(LC_ONLY_LINKS_AND_TEXT))
			return Boolean.toString(ignoreLCOnlyLinksAndText);
		else if (iSetting.equalsIgnoreCase(DISPLAY_IMAGE_ALTS))
			return Boolean.toString(displayImageAlts);
		else if (iSetting.equalsIgnoreCase(DISPLAY_IMAGE_LINK_ALTS))
			return Boolean.toString(displayImageLinkAlts);
		else if (iSetting.equalsIgnoreCase(REMOVE_EMPTY_TABLES))
			return Boolean.toString(removeEmptyTables);
		else if (iSetting.equalsIgnoreCase(LIMIT_LINEBREAKS))
			return Boolean.toString(limitLinebreaks);
		else if (iSetting.equalsIgnoreCase(MAX_LINEBREAKS))
			return Integer.toString(maxLinebreaks);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_BUTTON))
			return Boolean.toString(substanceButton);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_FORM))
			return Boolean.toString(substanceForm);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_IFRAME))
			return Boolean.toString(substanceIFrame);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_IMAGE))
			return Boolean.toString(substanceImage);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_INPUT))
			return Boolean.toString(substanceInput);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_LINKS))
			return Boolean.toString(substanceLinks);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_MIN_TEXT_LENGTH))
			return Integer.toString(substanceMinTextLength);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_SELECT))
			return Boolean.toString(substanceSelect);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_TEXTAREA))
			return Boolean.toString(substanceTextarea);
		else if (iSetting.equalsIgnoreCase(ADD_LINKS_TO_BOTTOM))
			return Boolean.toString(addLinksToBottom);
		else if (iSetting.equalsIgnoreCase(IGNORE_EMBED_TAGS))
			return Boolean.toString(ignoreEmbedTags);
		else if (iSetting.equalsIgnoreCase(IGNORE_FLASH))
			return Boolean.toString(ignoreFlash);
		return null;

	} //getSettings

	/**
	 * Sets a setting based on the final variables
	 * 
	 * @param iSetting
	 *            the name of the setting based on the final variables
	 * @param iValue
	 *            the desired value of the setting. For boolean values, "true" and "false" should be used
	 */

	public void changeSetting(final String iSetting, final String iValue) {
		if (iSetting.equalsIgnoreCase(IGNORE_ADS))
			ignoreAds = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_BUTTON_TAGS))
			ignoreButtonTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_CELL_WIDTH))
			ignoreCellWidth = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_DIV_STYLES))
			ignoreDivStyles = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_FORMS))
			ignoreForms = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_IFRAME_TAGS))
			ignoreIFrameTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_IMAGE_LINKS))
			ignoreImageLinks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_IMAGES))
			ignoreImages = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_INPUT_TAGS))
			ignoreInputTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_LINK_CELLS))
			ignoreLinkCells = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_META))
			ignoreMeta = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_NOSCRIPT_TAGS))
			ignoreNoscriptTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_SCRIPTS))
			ignoreScripts = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_SELECT_TAGS))
			ignoreSelectTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_EXTERNAL_STYLESHEETS))
			ignoreExternalStylesheets = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_STYLES))
			ignoreStyles = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_STYLE_ATTRIBUTES))
			ignoreStyleAttributes = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_TEXT_LINKS))
			ignoreTextLinks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(LC_IGNORE_IMAGE_LINKS))
			ignoreLCImageLinks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(LC_IGNORE_TEXT_LINKS))
			ignoreLCTextLinks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(LINK_TEXT_REMOVAL_RATIO))
			linkTextRatio = Double.parseDouble(iValue);
		else if (iSetting.equalsIgnoreCase(ONLY_TEXT))
			onlyText = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(LC_ONLY_LINKS_AND_TEXT))
			ignoreLCOnlyLinksAndText = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(DISPLAY_IMAGE_ALTS))
			displayImageAlts = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(DISPLAY_IMAGE_LINK_ALTS))
			displayImageLinkAlts = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(REMOVE_EMPTY_TABLES))
			removeEmptyTables = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(LIMIT_LINEBREAKS))
			limitLinebreaks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(MAX_LINEBREAKS))
			maxLinebreaks = Integer.parseInt(iValue);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_BUTTON))
			substanceButton = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_FORM))
			substanceForm = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_IFRAME))
			substanceIFrame = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_IMAGE))
			substanceImage = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_INPUT))
			substanceInput = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_LINKS))
			substanceLinks = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_MIN_TEXT_LENGTH))
			substanceMinTextLength = Integer.parseInt(iValue);
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_SELECT))
			substanceSelect = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(SUBSTANCE_TEXTAREA))
			substanceTextarea = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(ADD_LINKS_TO_BOTTOM))
			addLinksToBottom = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_EMBED_TAGS))
			ignoreEmbedTags = iValue.equals("true");
		else if (iSetting.equalsIgnoreCase(IGNORE_FLASH))
			ignoreFlash = iValue.equals("true");
		else if (Crunch3.settings.isVerbose())
			System.out.println("ContentExtractorSettings changeSetting: Unrecognized setting: " + iSetting);
	} //changeSetting

	/**
	 * Loads the settings into the property file
	 */
	private void loadSettingsProperties() {
		try {
			mSettings.load(new FileInputStream(mSettingsFile));
		} catch (FileNotFoundException e) {
			//Don't load the settings if the file doesn't exist
			System.out.println("ContentExtractor: WARNING: Settings file not found: " + mSettingsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Loads the settings file into the boolean values
	 *  
	 */
	public void loadSettings() {
		ignoreTextLinks = mSettings.getProperty(IGNORE_TEXT_LINKS, IGNORE_TEXT_LINKS_DEF);
		ignoreImageLinks = mSettings.getProperty(IGNORE_IMAGE_LINKS, IGNORE_IMAGE_LINKS_DEF);
		ignoreImages = mSettings.getProperty(IGNORE_IMAGES, IGNORE_IMAGES_DEF);
		ignoreScripts = mSettings.getProperty(IGNORE_SCRIPTS, IGNORE_SCRIPTS_DEF);
		ignoreExternalStylesheets = mSettings.getProperty(IGNORE_EXTERNAL_STYLESHEETS, IGNORE_EXTERNAL_STYLESHEETS_DEF);
		ignoreStyles = mSettings.getProperty(IGNORE_STYLES, IGNORE_SCRIPTS_DEF);
		ignoreStyleAttributes = mSettings.getProperty(IGNORE_STYLE_ATTRIBUTES, IGNORE_STYLE_ATTRIBUTES_DEF);
		ignoreForms = mSettings.getProperty(IGNORE_FORMS, IGNORE_FORMS_DEF);
		ignoreMeta = mSettings.getProperty(IGNORE_META, IGNORE_META_DEF);
		ignoreLinkCells = mSettings.getProperty(IGNORE_LINK_CELLS, IGNORE_LINK_CELLS_DEF);
		ignoreLCImageLinks = mSettings.getProperty(LC_IGNORE_IMAGE_LINKS, LC_IGNORE_IMAGE_LINKS_DEF);
		ignoreLCTextLinks = mSettings.getProperty(LC_IGNORE_TEXT_LINKS, LC_IGNORE_TEXT_LINKS_DEF);
		linkTextRatio = mSettings.getProperty(LINK_TEXT_REMOVAL_RATIO, LINK_TEXT_REMOVAL_RATIO_DEF);
		ignoreButtonTags = mSettings.getProperty(IGNORE_BUTTON_TAGS, IGNORE_BUTTON_TAGS_DEF);
		ignoreInputTags = mSettings.getProperty(IGNORE_INPUT_TAGS, IGNORE_INPUT_TAGS_DEF);
		ignoreSelectTags = mSettings.getProperty(IGNORE_SELECT_TAGS, IGNORE_SELECT_TAGS_DEF);
		ignoreNoscriptTags = mSettings.getProperty(IGNORE_NOSCRIPT_TAGS, IGNORE_NOSCRIPT_TAGS_DEF);
		ignoreCellWidth = mSettings.getProperty(IGNORE_CELL_WIDTH, IGNORE_CELL_WIDTH_DEF);
		ignoreAds = mSettings.getProperty(IGNORE_ADS, IGNORE_ADS_DEF);
		onlyText = mSettings.getProperty(ONLY_TEXT, ONLY_TEXT_DEF);
		ignoreIFrameTags = mSettings.getProperty(IGNORE_IFRAME_TAGS, IGNORE_IFRAME_TAGS_DEF);
		ignoreDivStyles = mSettings.getProperty(IGNORE_DIV_STYLES, IGNORE_DIV_STYLES_DEF);
		ignoreLCOnlyLinksAndText = mSettings.getProperty(LC_ONLY_LINKS_AND_TEXT, LC_ONLY_LINKS_AND_TEXT_DEF);
		displayImageAlts = mSettings.getProperty(DISPLAY_IMAGE_ALTS, DISPLAY_IMAGE_ALTS_DEF);
		displayImageLinkAlts = mSettings.getProperty(DISPLAY_IMAGE_LINK_ALTS, DISPLAY_IMAGE_LINK_ALTS_DEF);
		removeEmptyTables = mSettings.getProperty(REMOVE_EMPTY_TABLES, REMOVE_EMPTY_TABLES_DEF);
		limitLinebreaks = mSettings.getProperty(LIMIT_LINEBREAKS, LIMIT_LINEBREAKS_DEF);
		maxLinebreaks = mSettings.getProperty(MAX_LINEBREAKS, MAX_LINEBREAKS_DEF);
		substanceButton = mSettings.getProperty(SUBSTANCE_BUTTON, SUBSTANCE_BUTTON_DEF);
		substanceForm = mSettings.getProperty(SUBSTANCE_FORM, SUBSTANCE_FORM_DEF);
		substanceIFrame = mSettings.getProperty(SUBSTANCE_IFRAME, SUBSTANCE_IFRAME_DEF);
		substanceImage = mSettings.getProperty(SUBSTANCE_IMAGE, SUBSTANCE_IMAGE_DEF);
		substanceInput = mSettings.getProperty(SUBSTANCE_INPUT, SUBSTANCE_INPUT_DEF);
		substanceLinks = mSettings.getProperty(SUBSTANCE_LINKS, SUBSTANCE_LINKS_DEF);
		substanceMinTextLength = mSettings.getProperty(SUBSTANCE_MIN_TEXT_LENGTH, SUBSTANCE_MIN_TEXT_LENGTH_DEF);
		substanceSelect = mSettings.getProperty(SUBSTANCE_SELECT, SUBSTANCE_SELECT_DEF);
		substanceTextarea = mSettings.getProperty(SUBSTANCE_TEXTAREA, SUBSTANCE_TEXTAREA_DEF);
		addLinksToBottom = mSettings.getProperty(ADD_LINKS_TO_BOTTOM, ADD_LINKS_TO_BOTTOM_DEF);
		ignoreEmbedTags = mSettings.getProperty(IGNORE_EMBED_TAGS, IGNORE_EMBED_TAGS_DEF);
		ignoreFlash = mSettings.getProperty(IGNORE_FLASH, IGNORE_FLASH_DEF);
	} //loadSettings

	private void saveProperties() {
		mSettings.setProperty(IGNORE_TEXT_LINKS, ignoreTextLinks);
		mSettings.setProperty(IGNORE_IMAGE_LINKS, ignoreImageLinks);
		mSettings.setProperty(IGNORE_IMAGES, ignoreImages);
		mSettings.setProperty(IGNORE_SCRIPTS, ignoreScripts);
		mSettings.setProperty(IGNORE_EXTERNAL_STYLESHEETS, ignoreExternalStylesheets);
		mSettings.setProperty(IGNORE_STYLES, ignoreStyles);
		mSettings.setProperty(IGNORE_STYLE_ATTRIBUTES, ignoreStyleAttributes);
		mSettings.setProperty(IGNORE_FORMS, ignoreForms);
		mSettings.setProperty(IGNORE_META, ignoreMeta);
		mSettings.setProperty(IGNORE_LINK_CELLS, ignoreLinkCells);
		mSettings.setProperty(LC_IGNORE_IMAGE_LINKS, ignoreLCImageLinks);
		mSettings.setProperty(LC_IGNORE_TEXT_LINKS, ignoreLCTextLinks);
		mSettings.setProperty(LINK_TEXT_REMOVAL_RATIO, linkTextRatio);
		mSettings.setProperty(IGNORE_BUTTON_TAGS, ignoreButtonTags);
		mSettings.setProperty(IGNORE_INPUT_TAGS, ignoreInputTags);
		mSettings.setProperty(IGNORE_SELECT_TAGS, ignoreSelectTags);
		mSettings.setProperty(IGNORE_NOSCRIPT_TAGS, ignoreNoscriptTags);
		mSettings.setProperty(IGNORE_CELL_WIDTH, ignoreCellWidth);
		mSettings.setProperty(IGNORE_ADS, ignoreAds);
		mSettings.setProperty(ONLY_TEXT, onlyText);
		mSettings.setProperty(IGNORE_IFRAME_TAGS, ignoreIFrameTags);
		mSettings.setProperty(IGNORE_DIV_STYLES, ignoreDivStyles);
		mSettings.setProperty(LC_ONLY_LINKS_AND_TEXT, ignoreLCOnlyLinksAndText);
		mSettings.setProperty(DISPLAY_IMAGE_ALTS, displayImageAlts);
		mSettings.setProperty(DISPLAY_IMAGE_LINK_ALTS, displayImageLinkAlts);
		mSettings.setProperty(REMOVE_EMPTY_TABLES, removeEmptyTables);
		mSettings.setProperty(LIMIT_LINEBREAKS, limitLinebreaks);
		mSettings.setProperty(MAX_LINEBREAKS, maxLinebreaks);
		mSettings.setProperty(SUBSTANCE_BUTTON, substanceButton);
		mSettings.setProperty(SUBSTANCE_FORM, Boolean.toString(substanceForm));
		mSettings.setProperty(SUBSTANCE_IFRAME, substanceIFrame);
		mSettings.setProperty(SUBSTANCE_IMAGE, substanceImage);
		mSettings.setProperty(SUBSTANCE_INPUT, substanceInput);
		mSettings.setProperty(SUBSTANCE_LINKS, substanceLinks);
		mSettings.setProperty(SUBSTANCE_MIN_TEXT_LENGTH, substanceMinTextLength);
		mSettings.setProperty(SUBSTANCE_SELECT, substanceSelect);
		mSettings.setProperty(SUBSTANCE_TEXTAREA, substanceTextarea);
		mSettings.setProperty(ADD_LINKS_TO_BOTTOM, addLinksToBottom);
		mSettings.setProperty(IGNORE_EMBED_TAGS, ignoreEmbedTags);
		mSettings.setProperty(IGNORE_FLASH, ignoreFlash);
	}
	
	/**
	 * Save the settings file
	 */
	public void saveSettings() {
		saveProperties();

		try {
			mSettings.store(new FileOutputStream(new File(mSettingsFile)), "Content Extractor Settings File");
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
	}
	
	/**
	 * Saves the settings to the custom settings file & default settings file
	 */
	public void saveToCustom() {
		saveProperties();

		try {
			
			mSettings.store(new FileOutputStream(new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF)), "Content Extractor Settings File");
			mSettings.store(new FileOutputStream(new File(mSettingsFile)), "Content Extractor Settings File");
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
		
	}
	
	
}
