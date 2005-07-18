/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Scale;

import psl.crunch3.ButtonGroup;
import psl.crunch3.Crunch3;
import psl.crunch3.TypedProperties;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContentExtractorDescriptionGUI {
	
	private ContentExtractorDescription control=null;
	
	private Composite parentComposite = null;
	private Composite mainComposite = null;
	private Sash ContentPluginSeparator1;
	private Group presetsGroup;
	private Group settingsGroup;
	private Group userSettingsGroup;
	private Button autoButton = null;
	private Button newsButton = null;
	private Button shoppingButton = null;
	private Button governmentButton = null;
	private Button educationButton = null;
	private Button textHeavyButton = null;
	private Button linkHeavyButton = null;
	private Button normalButton = null;
	private Button pdaButton = null;
	private Button impairedButton = null;
	private Button infoButton = null;
	private Sash ContentPluginSeparator2;
	private Group automaticGroup;
	private Label specificImageLabel = null;
	private Label classificationLabel = null;
	private Label genericImageLabel = null;
	private Group referrerGroup;
	private Label referrerLabel = null;
	private Text referrerText = null;
	private Label applicationLabel = null;
	private Text applicationText = null;
	private Group customGroup;
	private Button customButton = null;
	private Combo engineCombo = null;
	private Scale relax;
	private Label relaxLabel = null;
	private Label toughenLabel = null; 
	private Button frontPageCheck = null;
	private Button nextPageCheck = null;
	
	/**
	 * @param c
	 */
	public ContentExtractorDescriptionGUI(Composite c, ContentExtractorDescription d) {
		parentComposite = c;
		
		control = d; 
		
		if (Crunch3.settings.isVerbose())
			System.out.println("Creating ContentExtractorDescriptionGUI");
		
		createControls();
		
		c.redraw();
	}
	
	/**
	 * @return
	 */
	public Composite getComposite() {
		if (mainComposite == null || !mainComposite.isDisposed())
			return mainComposite;
		else {
			mainComposite = null;
			createControls();
			return mainComposite;
		}
	}
	
	private void createControls(){
		// START VISUALS_INITIALIZATION
		// init visuals
		mainComposite = new Composite(parentComposite, SWT.NONE);
		ContentPluginSeparator1 = new Sash(mainComposite, SWT.HORIZONTAL | SWT.BORDER);
		settingsGroup = new Group(mainComposite, SWT.NULL);
		
		presetsGroup = new Group(settingsGroup, SWT.NONE);
		userSettingsGroup = new Group(settingsGroup, SWT.NULL);
		
		newsButton = new Button(presetsGroup, SWT.RADIO);
		shoppingButton = new Button(presetsGroup, SWT.RADIO);
		governmentButton = new Button(presetsGroup, SWT.RADIO);
		educationButton = new Button(presetsGroup, SWT.RADIO);
		textHeavyButton = new Button(presetsGroup, SWT.RADIO);
		linkHeavyButton = new Button(presetsGroup, SWT.RADIO);
		customButton = new Button(presetsGroup, SWT.RADIO);
		
		
		autoButton = new Button(presetsGroup, SWT.RADIO);
		engineCombo = new Combo(presetsGroup , SWT.DROP_DOWN);
		engineCombo.setText("5");
		for (int i = 1; i < 8; i++){
		   engineCombo.add(i+" ");
		 }
		
		
		normalButton = new Button(userSettingsGroup, SWT.RADIO);
		impairedButton = new Button(userSettingsGroup, SWT.RADIO);
		infoButton = new Button(userSettingsGroup, SWT.RADIO);
		pdaButton = new Button(userSettingsGroup, SWT.RADIO);
		frontPageCheck = new Button(userSettingsGroup, SWT.CHECK);
		nextPageCheck = new Button(userSettingsGroup, SWT.CHECK);
		
		ContentPluginSeparator2 = new Sash(mainComposite, SWT.HORIZONTAL | SWT.BORDER);
		automaticGroup = new Group(mainComposite, SWT.NULL);
		specificImageLabel = new Label(automaticGroup, SWT.BORDER);
		classificationLabel = new Label(automaticGroup, SWT.NULL);
		genericImageLabel = new Label(automaticGroup, SWT.BORDER);
		referrerGroup = new Group(mainComposite, SWT.NULL);
		referrerLabel = new Label(referrerGroup, SWT.NULL);
		referrerText = new Text(referrerGroup, SWT.BORDER);
		applicationLabel = new Label(referrerGroup, SWT.NULL);
		applicationText = new Text(referrerGroup, SWT.BORDER);
		customGroup = new Group(mainComposite, SWT.NULL);
		toughenLabel = new Label(customGroup, SWT.NULL);
		relax = new Scale(customGroup, SWT.NULL);
		relaxLabel = new Label(customGroup, SWT.NULL);
		
				
		
		
		// init nonvisuals
		GridData mainCompositeGridData = new GridData();
		GridLayout mainCompositeGridLayout = new GridLayout();
		ButtonGroup modeSelectionGroup = new ButtonGroup();
		ButtonGroup userSelectionGroup = new ButtonGroup();
		GridData ContentSeparator1 = new GridData();
		GridData presetsGroupGrid = new GridData();
		GridLayout presetsGridLayout = new GridLayout();
		GridData userSettingsGroupGrid = new GridData();
		GridLayout userSettingsGroupLayout = new GridLayout();
		GridData autoButtonGridData = new GridData();
		GridData newsButtonGridData = new GridData();
		GridData shoppingButtonGridData = new GridData();
		GridData governmentButtonGridData = new GridData();
		GridData educationButtonGridData = new GridData();
		GridData textHeavyButtonGridData = new GridData();
		GridData linkHeavyButtonGridData = new GridData();
		GridData normalButtonGridData = new GridData();
		GridData pdaButtonGridData = new GridData();
		GridData imparedButtonGridData = new GridData();
		GridData infoButtonGridData = new  GridData();
		GridData automaticGroupGrid = new GridData();	
		GridLayout automaticGridLayout = new GridLayout();
		GridData customButtonGridData = new GridData();
		GridData ContentSeparator2 = new GridData();
		GridData specificImageLabelGridData = new GridData();
		GridData classificationLabelGridData = new GridData();
		GridData genericImageLabelGridData = new GridData();
		GridData referrerGroupGrid = new GridData();	
		GridLayout referrerGroupGridLayout = new GridLayout();
		GridData referrerLabelGridData  = new GridData();
		GridData referrerTextGridData  = new GridData();
		GridData applicationLabelGridData  = new GridData();
		GridData applicationTextGridData  = new GridData();
		GridData customGroupGrid = new GridData();	
		GridLayout customGroupGridLayout = new GridLayout();
		GridData comboGridData = new GridData();
		GridData settingsGroupGridData = new GridData();
		FillLayout settingsGroupGridLayout = new FillLayout(SWT.HORIZONTAL);
		
		
		// set fields
		mainCompositeGridLayout.numColumns = 1;
		presetsGridLayout.numColumns = 1;
		presetsGroupGrid.grabExcessHorizontalSpace = true;
		presetsGroupGrid.horizontalAlignment = GridData.FILL;
		ContentSeparator1.horizontalSpan = 1;
		ContentSeparator1.grabExcessHorizontalSpace = true;
		ContentSeparator1.horizontalAlignment = GridData.FILL;
		ContentSeparator1.heightHint = 0;
		ContentSeparator2.horizontalSpan = 1;
		ContentSeparator2.grabExcessHorizontalSpace = true;
		ContentSeparator2.horizontalAlignment = GridData.FILL;
		ContentSeparator2.heightHint = 0;
		userSettingsGroupGrid.grabExcessHorizontalSpace = true;
		userSettingsGroupGrid.horizontalAlignment = GridData.FILL;
		automaticGridLayout.numColumns = 3;
		automaticGroupGrid.grabExcessHorizontalSpace = true;
		automaticGroupGrid.horizontalAlignment = GridData.FILL;
		specificImageLabelGridData.heightHint = 105;
		specificImageLabelGridData.widthHint = 100;
		genericImageLabelGridData.heightHint = 105;
		genericImageLabelGridData.widthHint = 100;
		referrerGroupGridLayout.numColumns = 2;
		referrerGroupGrid.grabExcessHorizontalSpace = true;
		referrerGroupGrid.horizontalAlignment = GridData.FILL;
		referrerTextGridData.grabExcessHorizontalSpace = true;
		referrerTextGridData.horizontalAlignment = GridData.FILL;
		applicationTextGridData.grabExcessHorizontalSpace = true;
		applicationTextGridData.horizontalAlignment = GridData.FILL;
		customGroupGridLayout.numColumns = 3;
		customGroupGrid.grabExcessHorizontalSpace = true;
		customGroupGrid.horizontalAlignment = GridData.FILL;
		settingsGroupGridData.grabExcessHorizontalSpace = true;
		settingsGroupGridData.horizontalAlignment = GridData.FILL;
		settingsGroup.setLayoutData(settingsGroupGridData);
		settingsGroup.setLayout(settingsGroupGridLayout);
		
		
		// set properties
		mainComposite.setLayoutData(mainCompositeGridData);
		mainComposite.setLayout(mainCompositeGridLayout);
		ContentPluginSeparator1.setLayoutData(ContentSeparator1);
		presetsGroup.setLayoutData(presetsGroupGrid);
		presetsGroup.setLayout(presetsGridLayout);
		presetsGroup.setText("Presets");
		modeSelectionGroup.add(newsButton);
		modeSelectionGroup.add(shoppingButton);
		modeSelectionGroup.add(governmentButton);
		modeSelectionGroup.add(educationButton);
		modeSelectionGroup.add(textHeavyButton);
		modeSelectionGroup.add(linkHeavyButton);
		modeSelectionGroup.add(autoButton);
		modeSelectionGroup.add(customButton);
		newsButton.setLayoutData(newsButtonGridData);
		newsButton.setText("News");
		shoppingButton.setLayoutData(shoppingButtonGridData);
		shoppingButton.setText("Shopping");
		governmentButton.setLayoutData(governmentButtonGridData);
		governmentButton.setText("Government");
		educationButton.setLayoutData(educationButtonGridData);
		educationButton.setText("Education");
		textHeavyButton.setLayoutData(textHeavyButtonGridData);
		textHeavyButton.setText("Text Heavy");
		linkHeavyButton.setLayoutData(linkHeavyButtonGridData);
		linkHeavyButton.setText("Link Heavy");
		
		customButton.setLayoutData(customButtonGridData);
		customButton.setText("Custom");
		automaticGroup.setLayoutData(automaticGroupGrid);
		automaticGroup.setLayout(automaticGridLayout);
		automaticGroup.setText("Automatic Detection");
		
		userSettingsGroup.setLayoutData(userSettingsGroupGrid);
		userSettingsGroup.setLayout(userSettingsGroupLayout);
		userSettingsGroup.setText("User Options");
		userSelectionGroup.add(normalButton);
		userSelectionGroup.add(infoButton);
		userSelectionGroup.add(pdaButton);
		userSelectionGroup.add(impairedButton);
		normalButton.setLayoutData(normalButtonGridData);
		normalButton.setText("Normal");
		pdaButton.setLayoutData(pdaButtonGridData);
		pdaButton.setText("PDA");
		infoButton.setLayoutData(infoButtonGridData);
		infoButton.setText("Information Retrieval");
		impairedButton.setLayoutData(imparedButtonGridData);
		impairedButton.setText("Visually Impaired");
		frontPageCheck.setText("Detect Front Page");
		frontPageCheck.setSelection(true);
		nextPageCheck.setText("Append Next Page");
		nextPageCheck.setSelection(true);
		
		
		ContentPluginSeparator2.setLayoutData(ContentSeparator2);
		specificImageLabel.setLayoutData(specificImageLabelGridData);
		specificImageLabel.setSize(100, 105);
		classificationLabel.setLayoutData(classificationLabelGridData);
		classificationLabel.setText("classification label"); // FIXME change text properly
		classificationLabel.setAlignment(SWT.CENTER);
		genericImageLabel.setLayoutData(genericImageLabelGridData);
		genericImageLabel.setSize(100, 105);
		referrerGroup.setText("Referrer Data");
		referrerGroup.setLayout(referrerGroupGridLayout);
		referrerGroup.setLayoutData(referrerGroupGrid);
		referrerLabel.setLayoutData(referrerLabelGridData);
		referrerLabel.setText("Referrer");
		referrerText.setLayoutData(referrerTextGridData);
		referrerText.setText("                                  ");
		referrerText.setEditable(false);
		applicationLabel.setLayoutData(applicationLabelGridData);
		applicationLabel.setText("Application");
		applicationText.setLayoutData(applicationTextGridData);
		applicationText.setText("                                  ");
		applicationText.setEditable(false);
		customGroup.setText("Custom Settings");
		customGroup.setLayout(customGroupGridLayout);
		customGroup.setLayoutData(customGroupGrid);
		engineCombo.setData(comboGridData);
		autoButton.setLayoutData(autoButtonGridData);
		autoButton.setText("Automatic");
		relax.setMaximum(12);
		relax.setMinimum(1);
		relax.setPageIncrement(1);
		relaxLabel.setText("Relax");
		toughenLabel.setText("Toughen");
		
		
		// END VISUALS_INITIALIZATION

		// START EVENT_INITIALIZATION
		newsButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				newsButton_widgetSelected(e);
			}
		});
		shoppingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				shoppingButton_widgetSelected(e);
			}
		});
		governmentButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				governmentButton_widgetSelected(e);
			}
		});
		educationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
		 		control.checkFrontPage(true);
				educationButton_widgetSelected(e);
			}
		});
		textHeavyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				textHeavyButton_widgetSelected(e);
			}
		});
		linkHeavyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				linkHeavyButton_widgetSelected(e);
			}
		});
		autoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				frontPageCheck.setSelection(true);
				control.checkFrontPage(true);
				auto_widgetSelected(e);
			}
		});
		customButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				customButton_widgetSelected(e);
			}
		});
		
		normalButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				normalButton_widgetSelected(e);
			}
		});
		
		pdaButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				pdaButton_widgetSelected(e);
			}
		});
		
		infoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				infoButton_widgetSelected(e);
			}
		});
		
		impairedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				impairedButton_widgetSelected(e);
			}
		});
		
		engineCombo.addSelectionListener(new SelectionAdapter() {
		    public void widgetSelected(SelectionEvent e){
		      Character c = new Character((engineCombo.getText()).charAt(0));
		      control.setEngineNumber(Integer.parseInt(c.toString()));
		    }
		});
		
		relax.addSelectionListener(
		 new SelectionAdapter(){
		 	public void widgetSelected(SelectionEvent e)
		 	{
		 		autoButton.setSelection(false);
		 		selectCustom();
		 		control.setAutomatic(false);
		 		frontPageCheck.setSelection(false);
		 		control.checkFrontPage(false);
				control.commitSettings("config" + File.separator + "level" + relax.getSelection() + ".ini", relax.getSelection());    
		 	}
			}
		);
		
		frontPageCheck.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent e){
				if (frontPageCheck.getSelection() == true){
					control.checkFrontPage(true);
				}
				else{
					control.checkFrontPage(false);
				}
			}	
		});
		
		nextPageCheck.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent e){
				if (nextPageCheck.getSelection() == true){
					control.checkNextPage(true);
				}
				else{
					control.checkNextPage(false);
				}
			}	
		});
		
		// TODO 
		if (ContentExtractor.customLast){
			customButton.setSelection(true);
			control.setSettingsLabel("custom");
		}
		else{
			newsButton.setSelection(true);
			control.setSettingsLabel("news");
			relax.setSelection(2);
		}
		
		normalButton.setSelection(true);
		
		if(!(Crunch3.settings.isGUISet())){
			control.commitSettings(Crunch3.settings.getSettings(), 2);
			control.setSettingsLabel("news");
		}
	}
	
	private void newsButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF, 2);
		control.setSettingsLabel("news");
		relax.setSelection(2);
		control.setAutomatic(false);
	}
	
	protected void shoppingButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL7_SETTINGS_FILE_DEF , 7);
		control.setSettingsLabel("shopping");
		relax.setSelection(7);
		control.setAutomatic(false);
	}
	
	protected void governmentButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL5_SETTINGS_FILE_DEF , 5);
		control.setSettingsLabel("government");
		relax.setSelection(5);
		control.setAutomatic(false);
	}
	
	protected void educationButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL6_SETTINGS_FILE_DEF , 6);
		control.setSettingsLabel("education");
		relax.setSelection(6);
		control.setAutomatic(false);
	}
	
	protected void textHeavyButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL2_SETTINGS_FILE_DEF , 2);
		control.setSettingsLabel("text heavy");
		relax.setSelection(2);
		control.setAutomatic(false);
	}
	
	protected void linkHeavyButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.LEVEL10_SETTINGS_FILE_DEF, 10);
		control.setSettingsLabel("link heavy");
		relax.setSelection(10);
		control.setAutomatic(false);
	}
	
	protected void normalButton_widgetSelected(SelectionEvent e) {
		
	}
	
	protected void pdaButton_widgetSelected(SelectionEvent e) {
		
	}
	
	protected void infoButton_widgetSelected(SelectionEvent e) {
		
	}
	
	protected void impairedButton_widgetSelected(SelectionEvent e) {
		
	}
	
	/**
	 * load automatic settings from file
	 * @param e
	 */
	protected void auto_widgetSelected(SelectionEvent e) {
		control.setAutomatic(true);
		control.setSettingsLabel("automatic");
		
		
		control.storeClustersInfo();
	}
	
	protected void customButton_widgetSelected(SelectionEvent e) {
		control.commitSettings(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF , 0);
		control.setAutomatic(false);
		control.setSettingsLabel("custom");
	}
	
	
	public static void main(String[] args){
		
	}
	
	public void updateSpecificImage(final String imageName){
		
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(specificImageLabel != null && !specificImageLabel.isDisposed()){
					Image img = null;
					InputStream imageData = getImageResourceAsStream("autoscreenshots/" + imageName);
					if (imageData != null)
						img = new Image(Crunch3.Display_1, imageData);
					else if (new java.io.File("autoscreenshots/" + imageName).canRead())
						img = new Image(Crunch3.Display_1, ("autoscreenshots/" + imageName));
					else if (Crunch3.settings.isVerbose())
						System.out.println("MainWindow Warning: Could not find autoscreenshots/" + imageName);
					//if(img != null)
					specificImageLabel.setImage(img);
					
					
				}
			}
		});
		
	}
	
	
	public void updateGenericImage(final String imageName){
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(genericImageLabel != null && !genericImageLabel.isDisposed()){
					Image img = null;
					InputStream imageData = getImageResourceAsStream("boxedscreenshots/" + imageName);
					if (imageData != null)
						img = new Image(Crunch3.Display_1, imageData);
					else if (new java.io.File("boxedscreenshots/" + imageName).canRead())
						img = new Image(Crunch3.Display_1, ("boxedscreenshots/" + imageName));
					else if (Crunch3.settings.isVerbose())
						System.out.println("MainWindow Warning: Could not find autoscreenshots/" + imageName);
					//if(img != null)
					genericImageLabel.setImage(img);
				}
			}
		});
	}
	
	private InputStream getImageResourceAsStream(final String name) { // enable image
		// loading from
		// jars
		return getClass().getResourceAsStream("/" + name);
	}
	
	
	public void updateReferrerText(final String text){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(referrerText != null && !referrerText.isDisposed()){
					if(text.equals("")){
						String currentURL = Crunch3.mainControl.getCurrentURL();
						if (currentURL !="") referrerText.setText(currentURL);
						else referrerText.setText("");
					}
					else referrerText.setText(text);
				}
			}
		});
	}
	public void updateApplicationText(final String text){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(applicationText != null && !applicationText.isDisposed())
					applicationText.setText(text);
			}
		});
	}
	
	public void updateClassificationLabel(final String text){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(classificationLabel != null && !classificationLabel.isDisposed())
					classificationLabel.setText(text);
			}
		});
	}
	
	 public void updateSettingsLevel(){
	 	final int level = control.getSettingsLevel();
	 	control.setSettingsLevel(level);
    	
    }
	
	 public void setSettingsLevel(final int level){
	 	Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(relax != null && !relax.isDisposed())
					relax.setSelection(level);
			}
		});
    }
	 

	 
	 
	 public void update(){
		 Crunch3.Display_1.syncExec(new Runnable(){
				public void run(){
					if(relax != null && !relax.isDisposed())
						relax.setSelection(control.getSettingsLevel());
					
					if(classificationLabel != null && !classificationLabel.isDisposed())
						classificationLabel.setText(control.getSettingsLabel());
					
					
					
				}
			});
	 }
	
	//selects the custom button
	public void selectCustom(){
		customButton.setSelection(true);
		newsButton.setSelection(false);
		shoppingButton.setSelection(false);
		governmentButton.setSelection(false);
		educationButton.setSelection(false);
		textHeavyButton.setSelection(false);
		linkHeavyButton.setSelection(false);
		autoButton.setSelection(false);
	}
    
    
}
