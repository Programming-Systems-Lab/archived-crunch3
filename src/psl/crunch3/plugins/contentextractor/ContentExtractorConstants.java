/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public interface ContentExtractorConstants {
	//Settings variables
	public static final String IGNORE_TEXT_LINKS = "Ignore Text Links";

	public static final boolean IGNORE_TEXT_LINKS_DEF = true;

	public static final String IGNORE_IMAGES = "Ignore Images";

	public static final boolean IGNORE_IMAGES_DEF = true;

	public static final String IGNORE_SCRIPTS = "Ignore Scripts";

	public static final boolean IGNORE_SCRIPTS_DEF = true;

	public static final String IGNORE_EXTERNAL_STYLESHEETS = "Ignore External Stylesheets";
	
	public static final boolean IGNORE_EXTERNAL_STYLESHEETS_DEF = false;
	
	public static final String IGNORE_DIV_STYLES =
		"Ignore Style Attribute in <DIV> Tags";

	public static final boolean IGNORE_DIV_STYLES_DEF = false;

	public static final String IGNORE_STYLE_ATTRIBUTES = "Ignore Style Attributes";
	
	public static final boolean IGNORE_STYLE_ATTRIBUTES_DEF = false;
	
	public static final String IGNORE_STYLES = "Ignore Styles";

	public static final boolean IGNORE_STYLES_DEF = false;

	public static final String IGNORE_FORMS = "Ignore Forms";

	public static final boolean IGNORE_FORMS_DEF = true;

	public static final String IGNORE_META = "Ignore Meta Tags";

	public static final boolean IGNORE_META_DEF = true;

	public static final String MINIMUM_TEXT_LENGTH = "Minimum Text Length";

	public static final int MINIMUM_TEXT_LENGTH_DEF = 0;

	//================================================================
	//All the settings for link lists - or link cells
	public static final String IGNORE_LINK_CELLS = "Ignore Link Lists";

	public static final boolean IGNORE_LINK_CELLS_DEF = true;

	//LC stands for Link Cells
	public static final String LC_IGNORE_IMAGE_LINKS =
		"Ignore Image Links in Link Lists";

	public static final boolean LC_IGNORE_IMAGE_LINKS_DEF = true;

	public static final String LC_IGNORE_TEXT_LINKS =
		"Ignore Text Links in Link Lists";

	public static final boolean LC_IGNORE_TEXT_LINKS_DEF = true;

	public static final String LC_ONLY_LINKS_AND_TEXT =
		"Ignore Only Links and Text in Link Lists";

	public static final boolean LC_ONLY_LINKS_AND_TEXT_DEF = true;

	//End of settings for link lists - or link cells
	//=================================================================

	public static final String IGNORE_IMAGE_LINKS = "Ignore Image Links";

	public static final boolean IGNORE_IMAGE_LINKS_DEF = true;

	public static final String IGNORE_INPUT_TAGS = "Ignore "+String.valueOf('<')+"INPUT> Tags";

	public static final boolean IGNORE_INPUT_TAGS_DEF = true;

	public static final String IGNORE_BUTTON_TAGS = "Ignore "+String.valueOf('<')+"BUTTON> Tags";

	public static final boolean IGNORE_BUTTON_TAGS_DEF = true;

	public static final String IGNORE_SELECT_TAGS = "Ignore "+String.valueOf('<')+"SELECT> Tags";

	public static final boolean IGNORE_SELECT_TAGS_DEF = true;

	public static final String IGNORE_NOSCRIPT_TAGS = "Ignore "+String.valueOf('<')+"NOSCRIPT> Tags";

	public static final boolean IGNORE_NOSCRIPT_TAGS_DEF = true;

	public static final String IGNORE_CELL_WIDTH = "Ignore Table Cell Widths";

	public static final boolean IGNORE_CELL_WIDTH_DEF = false;

	public static final String IGNORE_ADS = "Ignore All Advertisements";

	public static final boolean IGNORE_ADS_DEF = true;

	public static final String ONLY_TEXT = "Print Only Text";

	public static final boolean ONLY_TEXT_DEF = false;

	public static final String IGNORE_IFRAME_TAGS = "Ignore "+String.valueOf('<')+"IFRAME> Tags";

	public static final boolean IGNORE_IFRAME_TAGS_DEF = false;

	public static final String DISPLAY_IMAGE_ALTS = "Display Image ALTs";

	public static final boolean DISPLAY_IMAGE_ALTS_DEF = false;

	public static final String DISPLAY_IMAGE_LINK_ALTS =
		"Display Image Link ALTs";

	public static final boolean DISPLAY_IMAGE_LINK_ALTS_DEF = false;

	////////////////////////// Empty Table Settings ////////////////////////////
	public static final String REMOVE_EMPTY_TABLES = "Remove Empty Tables";

	public static final boolean REMOVE_EMPTY_TABLES_DEF = true;

	public static final String SUBSTANCE_IMAGE = ""+String.valueOf('<')+"IMG> tags are substance";

	public static final boolean SUBSTANCE_IMAGE_DEF = true;

	public static final String SUBSTANCE_LINKS = ""+String.valueOf('<')+"A> tags are substance";

	public static final boolean SUBSTANCE_LINKS_DEF = true;

	public static final String SUBSTANCE_IFRAME = String.valueOf('<')+"IFRAME> tags are substance";

	public static final boolean SUBSTANCE_IFRAME_DEF = true;

	public static final String SUBSTANCE_INPUT = String.valueOf('<')+"INPUT> tags are substance";

	public static final boolean SUBSTANCE_INPUT_DEF = true;

	public static final String SUBSTANCE_BUTTON = String.valueOf('<')+"BUTTON> tags are substance";

	public static final boolean SUBSTANCE_BUTTON_DEF = true;

	public static final String SUBSTANCE_TEXTAREA =
		String.valueOf('<')+"TEXTAREA> tags are substance";

	public static final boolean SUBSTANCE_TEXTAREA_DEF = true;

	public static final String SUBSTANCE_SELECT = String.valueOf('<')+"SELECT> tags are substance";

	public static final boolean SUBSTANCE_SELECT_DEF = true;

	public static final String SUBSTANCE_FORM = String.valueOf('<')+"FORM> tags are substance";

	public static final boolean SUBSTANCE_FORM_DEF = false;

	public static final String SUBSTANCE_MIN_TEXT_LENGTH =
		"Minimum text length as substance";

	public static final int SUBSTANCE_MIN_TEXT_LENGTH_DEF = 1;

	////////////////////////////////////////////////////////////////////////////

	public static final String LIMIT_LINEBREAKS = "Limit Number of Line Breaks";

	public static final boolean LIMIT_LINEBREAKS_DEF = true;

	public static final String MAX_LINEBREAKS = "Maximum Number of Line Breaks";

	public static final int MAX_LINEBREAKS_DEF = 2;

	public static final String ADD_LINKS_TO_BOTTOM =
		"Add removed links to bottom of the page";

	public static final boolean ADD_LINKS_TO_BOTTOM_DEF = false;

	public static final String IGNORE_EMBED_TAGS = "Ignore <EMBED> tags";

	public static final boolean IGNORE_EMBED_TAGS_DEF = false;
	
	public static final String IGNORE_FLASH = "Ignore Flash";
	
	public static final boolean IGNORE_FLASH_DEF = false;

	/*
	 * Link/Text ratio is determined by the amount of text words to single links
	 * A word is considered 4 letters long.
	 */
	public final static String LINK_TEXT_REMOVAL_RATIO =
		"Link/Text Removal Ratio";

	public static final double LINK_TEXT_REMOVAL_RATIO_DEF = 0.25;
}