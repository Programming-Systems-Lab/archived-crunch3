/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;

import psl.crunch3.ButtonGroup;
import psl.crunch3.Crunch3;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContentExtractorDescriptionGUI {
	private Composite parentComposite = null;
	private Composite mainComposite = null;
	private Sash ContentPluginSeparator1;
	private Group presetsGroup;
	private Button autoButton = null;
	private Button newsButton = null;
	private Button shoppingButton = null;
	private Button governmentButton = null;
	private Button educationButton = null;
	private Button textHeavyButton = null;
	private Button linkHeavyButton = null;
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

	/**
	 * @param c
	 */
	public ContentExtractorDescriptionGUI(Composite c) {
		parentComposite = c;
		
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
		presetsGroup = new Group(mainComposite, SWT.NONE);
		newsButton = new Button(presetsGroup, SWT.RADIO);
		shoppingButton = new Button(presetsGroup, SWT.RADIO);
		governmentButton = new Button(presetsGroup, SWT.RADIO);
		educationButton = new Button(presetsGroup, SWT.RADIO);
		textHeavyButton = new Button(presetsGroup, SWT.RADIO);
		linkHeavyButton = new Button(presetsGroup, SWT.RADIO);
		autoButton = new Button(presetsGroup, SWT.RADIO);
		customButton = new Button(presetsGroup, SWT.RADIO);
		ContentPluginSeparator2 = new Sash(mainComposite, SWT.HORIZONTAL | SWT.BORDER);
		automaticGroup = new Group(mainComposite, SWT.NULL);
		specificImageLabel = new Label(automaticGroup, SWT.BORDER);
		classificationLabel = new Label(automaticGroup, SWT.NONE);
		genericImageLabel = new Label(automaticGroup, SWT.BORDER);
		referrerGroup = new Group(mainComposite, SWT.NULL);
		referrerLabel = new Label(referrerGroup, SWT.NULL);
		referrerText = new Text(referrerGroup, SWT.BORDER);
		applicationLabel = new Label(referrerGroup, SWT.NULL);
		applicationText = new Text(referrerGroup, SWT.BORDER);
		customGroup = new Group(mainComposite, SWT.NULL);
		
		
		// init nonvisuals
		GridData mainCompositeGridData = new GridData();
		GridLayout mainCompositeGridLayout = new GridLayout();
		ButtonGroup modeSelectionGroup = new ButtonGroup();
		GridData ContentSeparator1 = new GridData();
		GridData presetsGroupGrid = new GridData();	
		GridLayout presetsGridLayout = new GridLayout();
		GridData autoButtonGridData = new GridData();
		GridData newsButtonGridData = new GridData();
		GridData shoppingButtonGridData = new GridData();
		GridData governmentButtonGridData = new GridData();
		GridData educationButtonGridData = new GridData();
		GridData textHeavyButtonGridData = new GridData();
		GridData linkHeavyButtonGridData = new GridData();
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
		automaticGridLayout.numColumns = 3;
		automaticGroupGrid.grabExcessHorizontalSpace = true;
		automaticGroupGrid.horizontalAlignment = GridData.FILL;
		specificImageLabelGridData.heightHint = 105;
		specificImageLabelGridData.widthHint = 100;
		genericImageLabelGridData.heightHint = 105;
		genericImageLabelGridData.widthHint = 100;
		customGroupGridLayout.numColumns = 3;
		referrerGroupGridLayout.numColumns = 2;
		referrerGroupGrid.grabExcessHorizontalSpace = true;
		referrerGroupGrid.horizontalAlignment = GridData.FILL;
		referrerTextGridData.grabExcessHorizontalSpace = true;
		referrerTextGridData.horizontalAlignment = GridData.FILL;
		applicationTextGridData.grabExcessHorizontalSpace = true;
		applicationTextGridData.horizontalAlignment = GridData.FILL;
		customGroupGridLayout.numColumns = 2;
		customGroupGrid.grabExcessHorizontalSpace = true;
		customGroupGrid.horizontalAlignment = GridData.FILL;
		
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
		autoButton.setLayoutData(autoButtonGridData);
		autoButton.setText("Automatic");
		customButton.setLayoutData(customButtonGridData);
		customButton.setText("Custom");
		automaticGroup.setLayoutData(automaticGroupGrid);
		automaticGroup.setLayout(automaticGridLayout);
		automaticGroup.setText("Automatic Detection");
		ContentPluginSeparator2.setLayoutData(ContentSeparator2);
		specificImageLabel.setLayoutData(specificImageLabelGridData);
		specificImageLabel.setSize(100, 105);
		classificationLabel.setLayoutData(classificationLabelGridData);
		classificationLabel.setText("classification label"); // FIXME change text properly
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
		
		// END VISUALS_INITIALIZATION

		// START EVENT_INITIALIZATION
		newsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				newsButton_widgetSelected(e);
			}
		});
		shoppingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				shoppingButton_widgetSelected(e);
			}
		});
		governmentButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				governmentButton_widgetSelected(e);
			}
		});
		educationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				educationButton_widgetSelected(e);
			}
		});
		textHeavyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				textHeavyButton_widgetSelected(e);
			}
		});
		linkHeavyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				linkHeavyButton_widgetSelected(e);
			}
		});
		autoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				auto_widgetSelected(e);
			}
		});
		customButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				customButton_widgetSelected(e);
			}
		});
		
		// TODO change this to what the last selection was
		if (ContentExtractor.customLast)
			customButton.setSelection(true);
		newsButton.setSelection(true);
	}
	
	private void newsButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.NEWS_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void shoppingButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.SHOPPING_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void governmentButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.GOVERNMENT_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void educationButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.EDUCATION_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void textHeavyButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.TEXT_HEAVY_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void linkHeavyButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.LINK_HEAVY_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void auto_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.AUTOMATIC_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = false;
	}
	protected void customButton_widgetSelected(SelectionEvent e) {
		if (ContentExtractor.customLast)
			copy(new File(ContentExtractor.SETTINGS_FILE_DEF), new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF));
		copy(new File(ContentExtractor.CUSTOM_SETTINGS_FILE_DEF), new File(ContentExtractor.SETTINGS_FILE_DEF));
		ContentExtractor.customLast = true;
	}

	public static void main(String[] args){
		
	}
	
	public void updateSpecificImage(final Image image){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(specificImageLabel != null && !specificImageLabel.isDisposed())
				specificImageLabel.setImage(image);
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
	public void updateGenericImage(final Image image){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(genericImageLabel != null && !genericImageLabel.isDisposed())
					genericImageLabel.setImage(image);
			}
		});
	}
	public void updateReferrerText(final String text){
		// FIXME method stub
		Crunch3.Display_1.syncExec(new Runnable(){
			public void run(){
				if(referrerText != null && !referrerText.isDisposed())
					referrerText.setText(text);
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

    /**
     * Copies src file to dst file.  If the dst file does not exist, it is created.
     **/
    private void copy(File src, File dst) {
    	try {
    		InputStream in = new FileInputStream(src);
    		OutputStream out = new FileOutputStream(dst);
    
    		// Transfer bytes from in to out
    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0) {
    			out.write(buf, 0, len);
    		}
    		in.close();
    		out.close();
    	}
    	catch(Exception e){
    		if (Crunch3.settings.isVerbose()){
    			System.out.println("Error copying files: " + "source file:" + src + " destination file:" + dst);
    			e.printStackTrace();
    		}
    	}
    }
}
