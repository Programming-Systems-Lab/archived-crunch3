/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class AboutShell {

	private static AboutShell self = null;

	public static void showAboutShell() {
		if (self != null)
			return;
		else
			new AboutShell();
	}

	private AboutShell() {
		self = this;
		createControls();
		mainShell.pack();
		mainShell.setBounds(
			Crunch3.mainWindow.getShell().getBounds().x + Crunch3.mainWindow.getShell().getBounds().width / 2 - mainShell.getBounds().width / 2,
			Crunch3.mainWindow.getShell().getBounds().y + Crunch3.mainWindow.getShell().getBounds().height / 2 - mainShell.getBounds().height / 2,
			mainShell.getBounds().width,
			mainShell.getBounds().height);
		mainShell.open();
	}

	private Shell mainShell;
	private Label imageLabel;
	private Label titleLabel;
	private Label authorsLabel;
	private Label acknowledgementLabel;
	private Button okButton;
	private Sash divider;
	private Label copyrightLabel;

	private void createControls() {
		// START VISUALS_INITIALIZATION
		// init visuals
		mainShell = new Shell(Crunch3.mainWindow.getShell(), SWT.DIALOG_TRIM);
		imageLabel = new Label(mainShell, SWT.BORDER);
		titleLabel = new Label(mainShell, SWT.NONE);
		authorsLabel = new Label(mainShell, SWT.NONE);
		acknowledgementLabel = new Label(mainShell, SWT.NONE);
		divider = new Sash(mainShell, SWT.BORDER | SWT.HORIZONTAL);
		copyrightLabel = new Label(mainShell, SWT.NONE);
		okButton = new Button(mainShell, SWT.PUSH);

		// init nonviusuals
		GridLayout mainShellGridLayout = new GridLayout();
		GridData imageLabelGridData = new GridData();
		GridData titleLabelGridData = new GridData();
		GridData authorsLabelGridData = new GridData();
		GridData acknowledgementLabelGridData = new GridData();
		GridData okButtonGridData = new GridData();
		GridData dividerGridData = new GridData();
		GridData copyrightLabelGridData = new GridData();

		// set fields
		mainShellGridLayout.makeColumnsEqualWidth = false;
		mainShellGridLayout.numColumns = 2;
		imageLabelGridData.verticalSpan = 3;
		titleLabelGridData.grabExcessHorizontalSpace = true;
		titleLabelGridData.horizontalAlignment = GridData.FILL;
		authorsLabelGridData.grabExcessHorizontalSpace = true;
		authorsLabelGridData.horizontalAlignment = GridData.FILL;
		acknowledgementLabelGridData.grabExcessHorizontalSpace = true;
		acknowledgementLabelGridData.horizontalAlignment = GridData.FILL;
		dividerGridData.grabExcessHorizontalSpace = true;
		dividerGridData.horizontalAlignment = GridData.FILL;
		dividerGridData.horizontalSpan = 2;
		dividerGridData.heightHint = 0;
		copyrightLabelGridData.grabExcessHorizontalSpace = true;
		copyrightLabelGridData.horizontalAlignment = GridData.FILL;
		copyrightLabelGridData.horizontalSpan = 2;
		okButtonGridData.grabExcessHorizontalSpace = true;
		okButtonGridData.horizontalAlignment = GridData.CENTER;
		okButtonGridData.horizontalSpan = 2;

		// set properties
		mainShell.setImage(Crunch3.mainWindow.crunchIcon);
		mainShell.setLayout(mainShellGridLayout);
		mainShell.setText("Crunch 3.0: About");
		imageLabel.setImage(Crunch3.mainWindow.crunchIcon);
		imageLabel.setLayoutData(imageLabelGridData);
		titleLabel.getFont().getFontData()[0].setStyle(SWT.BOLD);
		titleLabel.setLayoutData(titleLabelGridData);
		titleLabel.setText("Crunch 3.0");
		authorsLabel.setLayoutData(authorsLabelGridData);
		authorsLabel.setText("Suhit Gupta\n" + "Dr. Gail Kaiser\n" + "Peter Grimm\n" + "David Neistadt");
		acknowledgementLabel.setLayoutData(acknowledgementLabelGridData);
		acknowledgementLabel.setText(
			"This product includes software developed by Andy Clark.\n"
				+ "This product includes software developed by the Apache\nSoftware Foundation (http://www.apache.org/).");
		divider.setEnabled(false);
		divider.setLayoutData(dividerGridData);
		copyrightLabel.setLayoutData(copyrightLabelGridData);
		copyrightLabel.setText("Copyright (c) 2004: The Trustees of Columbia University in the City\nof New York. All Rights Reserved.");
		okButton.setLayoutData(okButtonGridData);
		okButton.setText("    OK    ");
		// END VISUALS_INITIALIZATION

		// START EVENT_INITIALIZATION
		mainShell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
				mainShell_shellClosed(e);
			}
		});
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				okButton_widgetSelected(e);
			}
		});
		// END EVENT_INITIALIZATION
	}
	// START EVENT_HANDLING

	private void mainShell_shellClosed(final ShellEvent e) {
		mainShell.dispose();
		self = null;
	}
	private void okButton_widgetSelected(final SelectionEvent e) {
		mainShell.dispose();
		self = null;
	}
	// END EVENT_HANDLING

}
