package psl.crunch3.plugins.contentextractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import psl.crunch3.TypedProperties;


public class ContentExtractorDescription {

	private ContentExtractorSettings newFilter = ContentExtractorSettings.getInstance();
	private boolean isAuto = false;
	private int [][] frequencies;
	private Vector<String> keywords;
	private Vector<String> names;
	private Hashtable<String,ClusterInfo> clusters;
	private int engineNumber = 5;
	private int settingsLevel = 0;
	private boolean frontPage = true;
	private boolean nextPage = true;
	private String settingsLabel = "";
	private String currentURL = null;
	
	public String getCurrentURL(){
		return currentURL;
	}
	
	public void setCurrentURL(String url){
		currentURL = url;
	}
	
	public void checkFrontPage(boolean check){
		frontPage = check;
	}
	
	public boolean getCheckFrontPage(){
		return frontPage;
	}
	

	public void checkNextPage(boolean check){
		nextPage = check;
	}
	
	public boolean getCheckNextPage(){
		return nextPage;
	}
	
	public void setEngineNumber(int num){
		
		engineNumber = num;
		
	}
	
	public int getEngineNumber(){
		return engineNumber;
	}
	
	public void setAutomatic(boolean auto){
		isAuto = auto;
	}
	
	public boolean getAutomatic(){
		return isAuto;
	}
	
	public void setSettingsLabel(String label){
		settingsLabel = label;
	}
	
	public String getSettingsLabel(){
		return settingsLabel;
	}
	
	public void setSettingsLevel(int level){
		settingsLevel = level;
	}
	
	public int getSettingsLevel(){
		return settingsLevel;
	}
	
	
	public void storeClustersInfo(){
//		store cluster information 
		try{
			
			String part1;
			String word = null;
			int index1, index2;
			clusters = new Hashtable<String,ClusterInfo>();
			BufferedReader in = new BufferedReader(new FileReader(new File("clusters.txt")));
			while((word =in.readLine())!=null){
				index1 = word.indexOf(" ");
				part1 = word.substring(index1+1);
				index2 = part1.indexOf(" ");
				clusters.put(word.substring(0,index1), 
						new ClusterInfo((Integer.parseInt(part1.substring(0,index2))),
								(Integer.parseInt(part1.substring(index2+1))))
						);
			}
			in.close();
			
			
			BufferedReader inSites = new BufferedReader(new FileReader(new File("keyinfo.txt")));
			String site = null;
			
			//the file should be in the same format as written by the WordCount
			int numSites = Integer.parseInt(inSites.readLine());
			int keySize = Integer.parseInt(inSites.readLine());
			frequencies = new int[numSites+1][keySize];
		
			//read in the list of sites
			StringTokenizer st;
			names = new Vector<String>();
			for(int i=1;(site = inSites.readLine()) !=null;i++){
				st = new StringTokenizer(site);
				word = st.nextToken();
				if(clusters.get(word) !=null){
					names.addElement(word);
					for(int j=0;st.hasMoreElements();j++){
						frequencies[i][j] = Integer.parseInt(st.nextToken());
					}
				}
			}
			
			inSites.close();
		
			in = new BufferedReader(new FileReader(new File("key.txt")));
			
			keywords = new Vector<String>();
			while((word =in.readLine())!=null){
				keywords.addElement(word);
			}
			in.close();
			
			
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	
	/**
     * Changes the filter settings to new settings read from a file.
     * @param fileName the file containing the new filter settings. 
     */
    public void commitSettings(String fileName , int level) {
    	File nSettingsFile = new File(fileName);
    	TypedProperties nSettings = new TypedProperties();
    	try {
			nSettings.load(new FileInputStream(nSettingsFile));
		} catch (FileNotFoundException e) {
			//Don't load the settings if the file doesn't exist
			System.out.println("ContentExtractor: WARNING: Settings file not found: " + nSettingsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		settingsLevel = level;
		
		
    	newFilter.changeSetting(ContentExtractorConstants.ONLY_TEXT, Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.ONLY_TEXT, ContentExtractorConstants.ONLY_TEXT_DEF)));
    	newFilter.changeSetting(ContentExtractorConstants.IGNORE_ADS,Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.IGNORE_ADS, ContentExtractorConstants.IGNORE_ADS_DEF)));
    	newFilter.changeSetting(ContentExtractorConstants.IGNORE_BUTTON_TAGS, Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.IGNORE_BUTTON_TAGS, ContentExtractorConstants.IGNORE_BUTTON_TAGS_DEF)));
    	newFilter.changeSetting(ContentExtractorConstants.IGNORE_FORMS, Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.IGNORE_FORMS, ContentExtractorConstants.IGNORE_FORMS_DEF)));
    	newFilter.changeSetting(ContentExtractorConstants.IGNORE_IFRAME_TAGS, Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.IGNORE_IFRAME_TAGS, ContentExtractorConstants.IGNORE_IFRAME_TAGS_DEF)));
    	newFilter.changeSetting(ContentExtractorConstants.IGNORE_IMAGE_LINKS, Boolean.toString
    			(nSettings.getProperty(ContentExtractorConstants.IGNORE_IMAGE_LINKS, ContentExtractorConstants.IGNORE_IMAGE_LINKS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.DISPLAY_IMAGE_LINK_ALTS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.DISPLAY_IMAGE_LINK_ALTS, ContentExtractorConstants.DISPLAY_IMAGE_ALTS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_TEXT_LINKS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_TEXT_LINKS, ContentExtractorConstants.IGNORE_TEXT_LINKS_DEF)));

		newFilter.changeSetting(ContentExtractorConstants.DISPLAY_IMAGE_ALTS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.DISPLAY_IMAGE_ALTS, ContentExtractorConstants.DISPLAY_IMAGE_ALTS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_IMAGES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_IMAGES, ContentExtractorConstants.IGNORE_IMAGES_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_INPUT_TAGS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_INPUT_TAGS, ContentExtractorConstants.IGNORE_INPUT_TAGS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_LINK_CELLS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_LINK_CELLS, ContentExtractorConstants.IGNORE_LINK_CELLS_DEF)));

		
		newFilter.changeSetting(ContentExtractorConstants.LC_IGNORE_IMAGE_LINKS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.LC_IGNORE_IMAGE_LINKS, ContentExtractorConstants.LC_IGNORE_IMAGE_LINKS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.LC_IGNORE_TEXT_LINKS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.LC_IGNORE_TEXT_LINKS, ContentExtractorConstants.LC_IGNORE_TEXT_LINKS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.LINK_TEXT_REMOVAL_RATIO, Double.toString
				(nSettings.getProperty(ContentExtractorConstants.LINK_TEXT_REMOVAL_RATIO, ContentExtractorConstants.LINK_TEXT_REMOVAL_RATIO_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.LC_ONLY_LINKS_AND_TEXT, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.LC_ONLY_LINKS_AND_TEXT, ContentExtractorConstants.LC_ONLY_LINKS_AND_TEXT_DEF)));

		newFilter.changeSetting(ContentExtractorConstants.IGNORE_META, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_META, ContentExtractorConstants.IGNORE_META_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_SCRIPTS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_SCRIPTS, ContentExtractorConstants.IGNORE_SCRIPTS_DEF)));

		newFilter.changeSetting(ContentExtractorConstants.IGNORE_NOSCRIPT_TAGS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_NOSCRIPT_TAGS, ContentExtractorConstants.IGNORE_NOSCRIPT_TAGS_DEF)));

		newFilter.changeSetting(ContentExtractorConstants.IGNORE_SELECT_TAGS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_SELECT_TAGS, ContentExtractorConstants.IGNORE_SELECT_TAGS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_EXTERNAL_STYLESHEETS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_EXTERNAL_STYLESHEETS, ContentExtractorConstants.IGNORE_EXTERNAL_STYLESHEETS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_DIV_STYLES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_DIV_STYLES, ContentExtractorConstants.IGNORE_DIV_STYLES_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_STYLE_ATTRIBUTES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_STYLE_ATTRIBUTES, ContentExtractorConstants.IGNORE_STYLE_ATTRIBUTES_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_STYLES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_STYLES, ContentExtractorConstants.IGNORE_STYLES_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_CELL_WIDTH, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_CELL_WIDTH, ContentExtractorConstants.IGNORE_CELL_WIDTH_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.REMOVE_EMPTY_TABLES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.REMOVE_EMPTY_TABLES, ContentExtractorConstants.REMOVE_EMPTY_TABLES_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_BUTTON, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_BUTTON, ContentExtractorConstants.SUBSTANCE_BUTTON_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_FORM, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_FORM, ContentExtractorConstants.SUBSTANCE_FORM_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_IFRAME, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_IFRAME, ContentExtractorConstants.SUBSTANCE_IFRAME_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_IMAGE, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_IMAGE, ContentExtractorConstants.SUBSTANCE_IMAGE_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_INPUT, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_INPUT, ContentExtractorConstants.SUBSTANCE_INPUT_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_LINKS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_LINKS, ContentExtractorConstants.SUBSTANCE_LINKS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_SELECT, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_SELECT, ContentExtractorConstants.SUBSTANCE_SELECT_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_TEXTAREA, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_TEXTAREA, ContentExtractorConstants.SUBSTANCE_TEXTAREA_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.SUBSTANCE_MIN_TEXT_LENGTH, Integer.toString
				(nSettings.getProperty(ContentExtractorConstants.SUBSTANCE_MIN_TEXT_LENGTH, ContentExtractorConstants.SUBSTANCE_MIN_TEXT_LENGTH_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.REMOVE_EMPTY_TABLES, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.REMOVE_EMPTY_TABLES, ContentExtractorConstants.REMOVE_EMPTY_TABLES_DEF)));

		newFilter.changeSetting(ContentExtractorConstants.IGNORE_EMBED_TAGS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_EMBED_TAGS, ContentExtractorConstants.IGNORE_EMBED_TAGS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.IGNORE_FLASH, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.IGNORE_FLASH, ContentExtractorConstants.IGNORE_FLASH_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.ADD_LINKS_TO_BOTTOM, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.ADD_LINKS_TO_BOTTOM, ContentExtractorConstants.ADD_LINKS_TO_BOTTOM_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.LIMIT_LINEBREAKS, Boolean.toString
				(nSettings.getProperty(ContentExtractorConstants.LIMIT_LINEBREAKS, ContentExtractorConstants.LIMIT_LINEBREAKS_DEF)));
		newFilter.changeSetting(ContentExtractorConstants.MAX_LINEBREAKS, Integer.toString
				(nSettings.getProperty(ContentExtractorConstants.MAX_LINEBREAKS, ContentExtractorConstants.MAX_LINEBREAKS_DEF)));

		newFilter.saveSettings();
		
    }
    
    
    public int[][] getFrequencies(){
    	return frequencies;
    }
    
    
    public Vector getKeys(){
    	return keywords;
    }
    
    public Vector getSites(){
    	return names;
    }
    
    
    public int getCluster(String closest){
    	ClusterInfo temp = (ClusterInfo)clusters.get(closest);
    	if (temp != null)
    		return temp.clusterNum;
    	else return 0;
    }
    
    
    
    class ClusterInfo{
    	private int clusterNum;
    	private int level;
    	
    	ClusterInfo(int num, int lev){
    		clusterNum = num;
    		level = lev;
    	}
    	
    }
}
