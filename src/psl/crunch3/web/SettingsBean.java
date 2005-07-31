package psl.crunch3.web;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import psl.crunch3.Crunch3;
import psl.crunch3.plugins.contentextractor.ContentExtractorConstants;
import psl.crunch3.plugins.contentextractor.ContentExtractorSettings;

public class SettingsBean implements Serializable {

	
	private boolean ignoreAds;
	private boolean ignoreScripts;
	private boolean ignoreNoscript;
	private boolean	ignoreExternalStylesheets;
	private boolean ignoreStyles;
	private boolean ignoreStyleAttributes;
	private boolean ignoreStyleInDiv;
	private boolean ignoreImages;
	private boolean displayAltTags;
	private boolean ignoreImageLinks;
	private boolean displayImageLinkAlts;
	private boolean ignoreTextLinks;
	private boolean ignoreForms;
	private boolean ignoreInput;
	private boolean ignoreButton;
	private boolean ignoreSelect;
	private boolean ignoreMeta;
	private boolean ignoreIframe;
	private boolean ignoreTableCellWidths;
	private boolean ignoreEmbed;
	private boolean ignoreFlash;

	private boolean	ignoreLinkLists;
	private boolean ignoreLLTextLinks;
	private boolean ignoreLLImageLinks;
	private boolean ignoreOnlyTextAndLinks;
	private double linkTextRatio;
	
	private boolean	removeEmptyTables;
	private boolean substanceImage;
	private boolean	substanceTextarea;
	private boolean substanceLinks;
	private boolean substanceButton;
	private boolean substanceInput;
	private boolean substanceForm;
	private boolean substanceSelect;
	private boolean substanceIFrame;
	private int minimumTextLength;
	
	private boolean htmlOutput;
	private boolean textOutput;
	private boolean appendLinks;
	private boolean limitLineBreaks;
	private int	maxLineBreaks;
	
	
	private ContentExtractorSettings mFilter;
	
	public SettingsBean(){
	
		
	}
	
	
	
	
	public void setAppendLinks(String appendLinks) {
		if(appendLinks !=null)
			this.appendLinks = true;
		else this.appendLinks = false;
	}




	public void setDisplayAltTags(String displayAltTags) {
		if(displayAltTags !=null)
			this.displayAltTags = true;
		else this.displayAltTags = false;
	}




	public void setDisplayImageLinkAlts(String displayImageLinkAlts) {
		if(displayImageLinkAlts !=null)
			this.displayImageLinkAlts = true;
		else this.displayImageLinkAlts = false;
	}




	public void setHtmlOutput(String htmlOutput) {
		if(htmlOutput !=null)
			this.htmlOutput = true;
		else this.htmlOutput = false;
	}




	public void setIgnoreAds(String ignoreAds) {
		if(ignoreAds !=null)
			this.ignoreAds = true;
		else this.ignoreAds = false;
	}




	public void setIgnoreButton(String ignoreButton) {
		if(ignoreButton !=null)
			this.ignoreButton = true;
		else this.ignoreButton = false;
	}




	public void setIgnoreEmbed(String ignoreEmbed) {
		if( ignoreEmbed !=null)
			this.ignoreEmbed = true;
		else this.ignoreEmbed = false;
	}




	public void setIgnoreExternalStylesheets(String ignoreExternalStylesheets) {
		if( ignoreExternalStylesheets !=null)
			this.ignoreExternalStylesheets = true;
		else this.ignoreExternalStylesheets = false;
	}




	public void setIgnoreFlash(String ignoreFlash) {
		if( ignoreFlash !=null)
			this.ignoreFlash = true;
		else this.ignoreFlash = false;
	}




	public void setIgnoreForms(String ignoreForms) {
		if( ignoreForms !=null)
			this.ignoreForms = true;
		else this.ignoreForms = false;
	}




	public void setIgnoreIframe(String ignoreIframe) {
		if( ignoreIframe !=null)
			this.ignoreIframe = true;
		else this.ignoreIframe = false;
	}




	public void setIgnoreImageLinks(String ignoreImageLinks) {
		if( ignoreImageLinks !=null)
			this.ignoreImageLinks = true;
		else this.ignoreImageLinks = false;
	}




	public void setIgnoreImages(String ignoreImages) {
		if( ignoreImages !=null)
			this.ignoreImages = true;
		else this.ignoreImages = false;
	}




	public void setIgnoreInput(String ignoreInput) {
		if( ignoreInput !=null)
			this.ignoreInput = true;
		else this.ignoreInput = false;
	}




	public void setIgnoreLinkLists(String ignoreLinkLists) {
		if( ignoreLinkLists !=null)
			this.ignoreLinkLists = true;
		else this.ignoreLinkLists = false;
	}




	public void setIgnoreLLImageLinks(String ignoreLLImageLinks) {
		if( ignoreLLImageLinks !=null)
			this.ignoreLLImageLinks = true;
		else this.ignoreLLImageLinks = false;
	}




	public void setIgnoreLLTextLinks(String ignoreLLTextLinks) {
		if( ignoreLLTextLinks !=null)
			this.ignoreLLTextLinks = true;
		else this.ignoreLLTextLinks = false;
	}




	public void setIgnoreMeta(String ignoreMeta) {
		if( ignoreMeta !=null)
			this.ignoreMeta = true;
		else this.ignoreMeta = false;
	}




	public void setIgnoreNoscript(String ignoreNoscript) {
		if( ignoreNoscript !=null)
			this.ignoreNoscript = true;
		else this.ignoreNoscript = false;
	}




	public void setIgnoreOnlyTextAndLinks(String ignoreOnlyTextAndLinks) {
		if( ignoreOnlyTextAndLinks !=null)
			this.ignoreOnlyTextAndLinks = true;
		else this.ignoreOnlyTextAndLinks = false;
	}




	public void setIgnoreScripts(String ignoreScripts) {
		if( ignoreScripts !=null)
			this.ignoreScripts = true;
		else this.ignoreScripts = false;
	}




	public void setIgnoreSelect(String ignoreSelect) {
		if( ignoreSelect !=null)
			this.ignoreSelect = true;
		else this.ignoreSelect = false;
	}




	public void setIgnoreStyleAttributes(String ignoreStyleAttributes) {
		if( ignoreStyleAttributes !=null)
			this.ignoreStyleAttributes = true;
		else this.ignoreStyleAttributes = false;
	}




	public void setIgnoreStyleInDiv(String ignoreStyleInDiv) {
		if( ignoreStyleInDiv !=null)
			this.ignoreStyleInDiv = true;
		else this.ignoreStyleInDiv = false;
	}




	public void setIgnoreStyles(String ignoreStyles) {
		if( ignoreStyles !=null)
			this.ignoreStyles = true;
		else this.ignoreStyles = false;
	}




	public void setIgnoreTableCellWidths(String ignoreTableCellWidths) {
		if( ignoreTableCellWidths !=null)
			this.ignoreTableCellWidths = true;
		else this.ignoreTableCellWidths = false;
	}




	public void setIgnoreTextLinks(String ignoreTextLinks) {
		if( ignoreTextLinks !=null)
			this.ignoreTextLinks = true;
		else this.ignoreTextLinks = false;
	}




	public void setLimitLineBreaks(String limitLineBreaks) {
		if( limitLineBreaks !=null)
			this.limitLineBreaks = true;
		else this.limitLineBreaks = false;
	}




	public void setLinkTextRatio(String linkTextRatio) {
		if(linkTextRatio !=null)
		this.linkTextRatio = Double.parseDouble(linkTextRatio);
	}




	public void setMaxLineBreaks(String maxLineBreaks) {
		if( maxLineBreaks !=null)
			this.maxLineBreaks = Integer.parseInt(maxLineBreaks);
		
	}




	public void setMinimumTextLength(String minimumTextLength) {
		if( minimumTextLength !=null)
			this.minimumTextLength = Integer.parseInt(minimumTextLength);
		
	}




	public void setRemoveEmptyTables(String removeEmptyTables) {
		if( removeEmptyTables !=null)
			this.removeEmptyTables = true;
		else this.removeEmptyTables = false;
	}




	public void setSubstanceButton(String substanceButton) {
		if( substanceButton !=null)
			this.substanceButton = true;
		else this.substanceButton = false;
	}




	public void setSubstanceForm(String substanceForm) {
		if( substanceForm !=null)
			this.substanceForm = true;
		else this.substanceForm = false;
	}




	public void setSubstanceIFrame(String substanceIFrame) {
		if( substanceIFrame !=null)
			this.substanceIFrame = true;
		else this.substanceIFrame = false;
	}




	public void setSubstanceImage(String substanceImage) {
		if( substanceImage !=null)
			this.substanceImage = true;
		else this.substanceImage = false;
	}




	public void setSubstanceInput(String substanceInput) {
		if( substanceInput !=null)
			this.substanceInput = true;
		else this.substanceInput = false;
	}




	public void setSubstanceLinks(String substanceLinks) {
		if( substanceLinks !=null)
			this.substanceLinks = true;
		else this.substanceLinks = false;
	}




	public void setSubstanceSelect(String substanceSelect) {
		if( substanceSelect !=null)
			this.substanceSelect = true;
		else this.substanceSelect = false;
	}




	public void setSubstanceTextarea(String substanceTextarea) {
		if( substanceTextarea !=null)
			this.substanceTextarea = true;
		else this.substanceTextarea = false;
	}


	public void setTextOutput(String textOutput){
		if( textOutput !=null)
			this.textOutput = true;
		else this.textOutput = false;
	}


	/***********************************************************
	 * GET methods
	 *************************************************************/

	public String getAppendLinks() {
		return Boolean.toString(appendLinks);
	}

	public String getDisplayAltTags() {
		return Boolean.toString(displayAltTags);
	}

	public String getDisplayImageLinkAlts() {
		return Boolean.toString(displayImageLinkAlts);
	}

	public String getHtmlOutput() {
		return Boolean.toString(htmlOutput);
	}

	public String getIgnoreAds() {
		 return Boolean.toString(ignoreAds);
	}

	public String getIgnoreButton() {
		 return Boolean.toString(ignoreButton);
	}

	public String getIgnoreEmbed() {
		return Boolean.toString(ignoreEmbed);
	}

	public String getIgnoreExternalStylesheets() {
		 return Boolean.toString(ignoreExternalStylesheets);
	}

	public String getIgnoreFlash() {
		 return Boolean.toString(ignoreFlash);
	}

	public String getIgnoreForms() {
		 return Boolean.toString(ignoreForms);
	}

	public String getIgnoreIframe() {
		 return Boolean.toString(ignoreIframe);
	}

	public String getIgnoreImageLinks() {
		 return Boolean.toString(ignoreImageLinks);
	}

	public String getIgnoreImages() {
		 return Boolean.toString(ignoreImages);
	}

	public String getIgnoreInput() {
		return Boolean.toString(ignoreInput);
	}

	public String getIgnoreLinkLists() {
		return Boolean.toString(ignoreLinkLists);
	}

	public String getIgnoreLLImageLinks() {
		return Boolean.toString(ignoreLLImageLinks);
	}

	public String getIgnoreLLTextLinks() {
		return Boolean.toString(ignoreLLTextLinks);
	}

	public String getIgnoreMeta() {
		return Boolean.toString(ignoreMeta);
	}

	public String getIgnoreNoscript() {
		return Boolean.toString(ignoreNoscript);
	}

	public String getIgnoreOnlyTextAndLinks() {
		return Boolean.toString(ignoreOnlyTextAndLinks);
	}

	public String getIgnoreScripts() {
		return Boolean.toString(ignoreScripts);
	}

	public String getIgnoreSelect() {
		return Boolean.toString(ignoreSelect);
	}

	public String getIgnoreStyleAttributes() {
		return Boolean.toString(ignoreStyleAttributes);
	}

	public String getIgnoreStyleInDiv() {
		return Boolean.toString(ignoreStyleInDiv);
	}

	public String getIgnoreStyles() {
		return Boolean.toString(ignoreStyles);
	}

	public String getIgnoreTableCellWidths() {
		return Boolean.toString(ignoreTableCellWidths);
	}

	public String getIgnoreTextLinks() {
		return Boolean.toString(ignoreTextLinks);
	}

	public String getLimitLineBreaks() {
		return Boolean.toString(limitLineBreaks);
	}

	public String getLinkTextRatio() {
		return Double.toString(linkTextRatio);
	}

	public String getMaxLineBreaks() {
		return Integer.toString(maxLineBreaks);
	}

	public String getMinimumTextLength() {
		return Integer.toString(minimumTextLength);
	}

	public String getRemoveEmptyTables() {
		return Boolean.toString(removeEmptyTables);
	}

	public String getSubstanceButton() {
		return Boolean.toString(substanceButton);
	}

	public String getSubstanceForm() {
		return Boolean.toString(substanceForm);
	}

	public String getSubstanceIFrame() {
		return Boolean.toString(substanceIFrame);
	}

	public String getSubstanceImage() {
		return Boolean.toString(substanceImage);
	}

	public String getSubstanceInput() {
		return Boolean.toString(substanceInput);
	}

	public String getSubstanceLinks() {
		return Boolean.toString(substanceLinks);
	}

	public String getSubstanceSelect() {
		return Boolean.toString(substanceSelect);
	}

	public String getSubstanceTextarea() {
		return Boolean.toString(substanceTextarea);
	}
	
	public String getTextOutput(){
		return Boolean.toString(textOutput);
	}
	
	/**
	 * Commits the settings so that the ContentExtractor reflects the user's specifications.
	 */
	public void commitSettings() {
		
		mFilter = ContentExtractorSettings.getInstance();
		
		mFilter.changeSetting(ContentExtractorConstants.ONLY_TEXT, Boolean.toString(textOutput));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_ADS, Boolean.toString(ignoreAds));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_BUTTON_TAGS, Boolean.toString(ignoreButton));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_FORMS, Boolean.toString(ignoreForms));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_IFRAME_TAGS, Boolean.toString(ignoreIframe));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_IMAGE_LINKS, Boolean.toString(ignoreImageLinks));
		if (ignoreImageLinks)
			mFilter.changeSetting(ContentExtractorConstants.DISPLAY_IMAGE_LINK_ALTS, Boolean.toString(displayImageLinkAlts));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_TEXT_LINKS, Boolean.toString(ignoreTextLinks));

		if (ignoreImages) {
			mFilter.changeSetting(ContentExtractorConstants.DISPLAY_IMAGE_ALTS, Boolean.toString(displayAltTags));
			mFilter.changeSetting(ContentExtractorConstants.IGNORE_IMAGES, Boolean.toString(ignoreImages));
		} else
			mFilter.changeSetting(ContentExtractorConstants.IGNORE_IMAGES, Boolean.toString(ignoreImages));

		mFilter.changeSetting(ContentExtractorConstants.IGNORE_INPUT_TAGS, Boolean.toString(ignoreInput));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_LINK_CELLS, Boolean.toString(ignoreLinkLists));

		if (ignoreLinkLists) {
			mFilter.changeSetting(ContentExtractorConstants.LC_IGNORE_IMAGE_LINKS, Boolean.toString(ignoreLLImageLinks));
			mFilter.changeSetting(ContentExtractorConstants.LC_IGNORE_TEXT_LINKS, Boolean.toString(ignoreLLTextLinks));
			mFilter.changeSetting(ContentExtractorConstants.LINK_TEXT_REMOVAL_RATIO, Double.toString(linkTextRatio));
			mFilter.changeSetting(ContentExtractorConstants.LC_ONLY_LINKS_AND_TEXT, Boolean.toString(ignoreOnlyTextAndLinks));
		}

		mFilter.changeSetting(ContentExtractorConstants.IGNORE_META, Boolean.toString(ignoreMeta));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_SCRIPTS, Boolean.toString(ignoreScripts));

		if (ignoreNoscript)
			mFilter.changeSetting(ContentExtractorConstants.IGNORE_NOSCRIPT_TAGS, Boolean.toString(ignoreNoscript));

		mFilter.changeSetting(ContentExtractorConstants.IGNORE_SELECT_TAGS, Boolean.toString(ignoreSelect));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_EXTERNAL_STYLESHEETS, Boolean.toString(ignoreExternalStylesheets));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_DIV_STYLES, Boolean.toString(ignoreStyleInDiv));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_STYLE_ATTRIBUTES, Boolean.toString(ignoreStyleAttributes));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_STYLES, Boolean.toString(ignoreStyles));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_CELL_WIDTH, Boolean.toString(ignoreTableCellWidths));

		if (removeEmptyTables) {
			mFilter.changeSetting(ContentExtractorConstants.REMOVE_EMPTY_TABLES, Boolean.toString(removeEmptyTables));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_BUTTON, Boolean.toString(substanceButton));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_FORM, Boolean.toString(substanceForm));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_IFRAME, Boolean.toString(substanceIFrame));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_IMAGE, Boolean.toString(substanceImage));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_INPUT, Boolean.toString(substanceInput));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_LINKS, Boolean.toString(substanceLinks));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_SELECT, Boolean.toString(substanceSelect));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_TEXTAREA, Boolean.toString(substanceTextarea));
			mFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_MIN_TEXT_LENGTH, Integer.toString(minimumTextLength));
		} //if
		else
			mFilter.changeSetting(ContentExtractorConstants.REMOVE_EMPTY_TABLES, Boolean.toString(removeEmptyTables));

		mFilter.changeSetting(ContentExtractorConstants.IGNORE_EMBED_TAGS, Boolean.toString(ignoreEmbed));
		mFilter.changeSetting(ContentExtractorConstants.IGNORE_FLASH, Boolean.toString(ignoreFlash));
		mFilter.changeSetting(ContentExtractorConstants.ADD_LINKS_TO_BOTTOM, Boolean.toString(appendLinks));
		mFilter.changeSetting(ContentExtractorConstants.LIMIT_LINEBREAKS, Boolean.toString(limitLineBreaks));
		if (limitLineBreaks)
			mFilter.changeSetting(ContentExtractorConstants.MAX_LINEBREAKS, Integer.toString(maxLineBreaks));

			
			
		 mFilter.save("/eclipse/workspace/crunch3/config/content.ini");
		 if(Crunch3.mainControl != null)
		 Crunch3.mainControl.loadFile("/eclipse/workspace/crunch3/config/content.ini");
		//return mFilter.getSetting(ContentExtractorConstants.LINK_TEXT_REMOVAL_RATIO);
		
	}
	
}
