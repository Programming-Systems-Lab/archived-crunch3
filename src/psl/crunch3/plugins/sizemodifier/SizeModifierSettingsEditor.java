/*
 * Copyright (c) 2003: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.sizemodifier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import psl.crunch3.Crunch3;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class SizeModifierSettingsEditor {

	private static SizeModifierSettingsEditor self = null;

	private SizeModifierSettings settings;

	private Shell mainShell = null;

	private Composite settingsComposite;
	private Button fontSizeCheck;
	private Combo fontSizeCombo;
	private Button rescaleImagesCheck;
	private Combo rescaleImagesCombo;

	private Composite bottomButtonComposite;
	private Button commitButton;
	private Button revertButton;
	private Button okButton;
	private Button cancelButton;

	/**
	 * Singleton
	 * 
	 * @return a SizeModifierSettingsEditor
	 */
	public static SizeModifierSettingsEditor getInstance() {
		if (self == null)
			self = new SizeModifierSettingsEditor();
		return self;
	}

	/**
	 * Creates new form SizeModifierSettingsEditor
	 */
	private SizeModifierSettingsEditor() {
		initComponents();
		settings = SizeModifierSettings.getInstance();
		loadSettings();
		mainShell.pack();
		mainShell.setBounds(
			Crunch3.mainWindow.getShell().getBounds().x + Crunch3.mainWindow.getShell().getBounds().width / 2 - mainShell.getBounds().width / 2,
			Crunch3.mainWindow.getShell().getBounds().y + Crunch3.mainWindow.getShell().getBounds().height / 2 - mainShell.getBounds().height / 2,
			mainShell.getBounds().width,
			mainShell.getBounds().height);
		mainShell.open();
	}

	private void initComponents() {
		// init visuals
		mainShell = new Shell(Crunch3.mainWindow.getShell(), SWT.DIALOG_TRIM);

		settingsComposite = new Composite(mainShell, SWT.BORDER);
		fontSizeCheck = new Button(settingsComposite, SWT.CHECK);
		fontSizeCombo = new Combo(settingsComposite, SWT.READ_ONLY);
		rescaleImagesCheck = new Button(settingsComposite, SWT.CHECK);
		rescaleImagesCombo = new Combo(settingsComposite, SWT.READ_ONLY);

		bottomButtonComposite = new Composite(mainShell, SWT.NULL);
		commitButton = new Button(bottomButtonComposite, SWT.PUSH);
		revertButton = new Button(bottomButtonComposite, SWT.PUSH);
		okButton = new Button(bottomButtonComposite, SWT.PUSH);
		cancelButton = new Button(bottomButtonComposite, SWT.PUSH);

		// init non visuals
		GridLayout mainShellGridLayout = new GridLayout();

		GridData settingsGridData = new GridData();
		GridLayout settingsGridLayout = new GridLayout();
		GridData fontSizeCheckGridData = new GridData();
		GridData fontSizeComboGridData = new GridData();
		GridData rescaleImagesCheckGridData = new GridData();
		GridData rescaleImagesComboGridData = new GridData();

		GridData bottomButtonGridData = new GridData();
		GridLayout bottomButtonGridLayout = new GridLayout();
		GridData commitButtonGridData = new GridData();
		GridData revertButtonGridData = new GridData();
		GridData okButtonGridData = new GridData();
		GridData cancelButtonGridData = new GridData();

		// init fields
		settingsGridLayout.numColumns = 2;
		fontSizeComboGridData.grabExcessHorizontalSpace = true;
		fontSizeComboGridData.horizontalAlignment = GridData.FILL;
		rescaleImagesComboGridData.grabExcessHorizontalSpace = true;
		rescaleImagesComboGridData.horizontalAlignment = GridData.FILL;

		bottomButtonGridData.grabExcessHorizontalSpace = true;
		bottomButtonGridData.horizontalAlignment = GridData.FILL;
		bottomButtonGridLayout.numColumns = 4;
		commitButtonGridData.horizontalAlignment = GridData.BEGINNING;
		revertButtonGridData.horizontalAlignment = GridData.BEGINNING;
		revertButtonGridData.grabExcessHorizontalSpace = true;
		okButtonGridData.horizontalAlignment = GridData.END;
		cancelButtonGridData.horizontalAlignment = GridData.END;

		// set properties
		mainShell.setDefaultButton(okButton);
		mainShell.setImage(Crunch3.mainWindow.crunchIcon);
		mainShell.setLayout(mainShellGridLayout);
		mainShell.setText("Crunch 2.0: Size Modifier Settings");

		settingsComposite.setLayoutData(settingsGridData);
		settingsComposite.setLayout(settingsGridLayout);
		fontSizeCheck.setLayoutData(fontSizeCheckGridData);
		fontSizeCheck.setText("Relative &Font Size");
		fontSizeCombo.setItems(SizeModifierSettings.FONT_SIZES);
		fontSizeCombo.setLayoutData(fontSizeComboGridData);
		rescaleImagesCheck.setLayoutData(rescaleImagesCheckGridData);
		rescaleImagesCheck.setText("&Image Scaling Factor");
		rescaleImagesCombo.setItems(SizeModifierSettings.IMAGE_SCALING_FACTORS);
		rescaleImagesCombo.setLayoutData(rescaleImagesComboGridData);

		bottomButtonComposite.setLayoutData(bottomButtonGridData);
		bottomButtonComposite.setLayout(bottomButtonGridLayout);
		commitButton.setLayoutData(commitButtonGridData);
		commitButton.setText("&Commit");
		revertButton.setLayoutData(revertButtonGridData);
		revertButton.setText("&Revert");
		okButton.setLayoutData(okButtonGridData);
		okButton.setText("Ok");
		cancelButton.setLayoutData(cancelButtonGridData);
		cancelButton.setText("Cancel");

		mainShell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent evt) {
				mainShell_shellClosed(evt);
			}
		});
		fontSizeCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				fontSizeCheck_widgetSelected(evt);
			}
		});
		rescaleImagesCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				rescaleImagesCheck_widgetSelected(evt);
			}
		});
		commitButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				commitButton_widgetSelected(evt);
			}
		});
		revertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				revertButton_widgetSelected(evt);
			}
		});
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				okButton_widgetSelected(evt);
			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent evt) {
				cancelButton_widgetSelected(evt);
			}
		});

	}

	/**
	 * @param evt
	 */
	protected void mainShell_shellClosed(ShellEvent evt) {
		mainShell.dispose();
		self = null;
	}

	/**
	 * @param evt
	 */
	protected void fontSizeCheck_widgetSelected(final SelectionEvent evt) {
		fontSizeCombo.setEnabled(fontSizeCheck.getSelection());
	}

	/**
	 * @param evt
	 */
	protected void rescaleImagesCheck_widgetSelected(final SelectionEvent evt) {
		rescaleImagesCombo.setEnabled(rescaleImagesCheck.getSelection());
	}

	/**
	 * @param evt
	 */
	protected void commitButton_widgetSelected(final SelectionEvent evt) {
		commitSettings();
	}

	/**
	 * @param evt
	 */
	protected void revertButton_widgetSelected(final SelectionEvent evt) {
		loadSettings();
	}

	/**
	 * @param evt
	 */
	protected void okButton_widgetSelected(final SelectionEvent evt) {
		commitSettings();

		mainShell.dispose();
		self = null;
	}

	/**
	 * @param evt
	 */
	protected void cancelButton_widgetSelected(final SelectionEvent evt) {
		mainShell.dispose();
		self = null;
	}

	private void loadSettings() {
		// TODO implement loadsettings
		fontSizeCheck.setSelection(settings.resizeFonts);
		fontSizeCombo.select(settings.resizeFontsIndex);
		fontSizeCombo.setEnabled(fontSizeCheck.getSelection());
		rescaleImagesCheck.setSelection(settings.rescaleImages);
		rescaleImagesCombo.select(settings.rescaleImagesIndex);
		rescaleImagesCombo.setEnabled(rescaleImagesCheck.getSelection());
	}

	private void commitSettings() {
		settings.resizeFonts = fontSizeCheck.getSelection();
		settings.resizeFontsIndex = fontSizeCombo.getSelectionIndex();
		settings.rescaleImages = rescaleImagesCheck.getSelection();
		settings.rescaleImagesIndex = rescaleImagesCombo.getSelectionIndex();
		settings.saveSettings();
	}
}
