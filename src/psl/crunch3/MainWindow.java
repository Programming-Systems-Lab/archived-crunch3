/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import psl.crunch3.plugins.EnhancedProxyFilter;
import psl.crunch3.plugins.ProxyFilter;
import psl.crunch3.plugins.contentextractor.ContentExtractorDescriptionGUI;

/**
 * Generates the main window for Crunch 3.0.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class MainWindow extends Thread {
	
	private String currentURL="";
	private boolean isInitialized = false;
	private boolean listenPortChanged = false;
	private Vector connections = new Vector();
	private Thread updateThread = null;
	private Thread trayShutdownHook = null;

	// START VISUALS_DECLARATION
	public Image crunchIcon = null;
	public Image crunchIconActive = null;
	public Image crunchIconOff = null;
	public Image crunchIconOffActive = null;	

	private Tray tray = null;
	TrayItem mainTrayItem = null;
	
	private Menu trayMenu = null;
	private MenuItem trayOpenMenuItem = null;
	private MenuItem traySeparator1MenuItem = null;
	private MenuItem trayFilterMenuItem = null;
	private MenuItem traySeparator2MenuItem = null;
	private MenuItem trayShoppingModeMenuItem = null;
	private MenuItem trayGeneralPurposeMenuItem = null;
	private MenuItem trayMaximumFilteringMenuItem = null;
	private MenuItem trayCustomFilteringMenuItem = null;
	private MenuItem traySeparator3MenuItem = null;
	private MenuItem trayExitMenuItem = null;
	
	private Shell mainShell = null;
	private Menu mainMenu = null;
	private MenuItem fileMenuItem = null;
	private Menu fileMenu = null;
	private MenuItem exitMenuItem = null;
	private MenuItem helpMenuItem = null;
	private Menu helpMenu = null;
	private MenuItem aboutMenuItem = null;
	private TabFolder mainTabFolder = null;

	private TabItem statusTabItem = null;
	private Composite statusComposite = null;
	private Label statusLabel = null;
	private Text statusText = null;
	private Label listenIPLabel = null;
	private Text listenIPText = null;
	private Group connectionGroup = null;
	private Label connectionCountLabel = null;
	private Text connectionCountText = null;
	private SashForm connectionSashForm = null;
	private Group connectionAddressGroup = null;
	private List connectionAddressList = null;
	private Group connectionStatusGroup = null;
	private List connectionStatusList = null;

	private TabItem proxySettingsTabItem = null;
	private Composite proxySettingsComposite = null;
	private Group generalSettingsGroup = null;
	private Label verboseLabel = null;
	private Button verboseCheck = null;
	private Group networkSettingsGroup = null;
	private Label listenPortLabel = null;
	private Text listenPortText = null;
	private Label serverSocketTimeoutLabel = null;
	private Text serverSocketTimeoutText = null;
	private Label socketTimeoutLabel = null;
	private Text socketTimeoutText = null;
	private Label useProxyLabel = null;
	private Button useProxyCheck = null;
	private Label proxyServerAddressLabel = null;
	private Text proxyServerAddressText = null;
	private Label proxyServerPortLabel = null;
	private Text proxyServerPortText = null;
	private Group filterSettingsGroup = null;
	private Label filteringCheckLabel = null;
	private Button filterCheck = null;
	private Label filterTypesLabel = null;
	private Text filterTypesText = null;
	private Label filterHomepagesLabel = null;
	private Button filterHomepagesCheck = null;
	private Composite proxySettingsButtonComposite = null;
	private Button proxySettingsCommitButton = null;
	private Button proxySettingsRevertButton = null;
	
	/*
	private TabItem browsingModeTabItem = null;
	private Composite browsingModeComposite = null;
	private ButtonGroup browsingModeCompositeButtonGroup = null;
	private Button shoppingRadio = null;
	private Button generalPurposeRadio = null;
	private Button maximumFilteringRadio = null;
	private Button customFilteringRadio = null;
	*/
	
	private TabItem pluginsTabItem = null;
	private Composite pluginsComposite = null;
	private SashForm pluginsSashForm = null;
	private Group pluginGroup = null;
	private Table pluginTable = null;
	private Group descriptionGroup = null;
	private ScrolledComposite descriptionScrolledComposite = null;
	private Composite descriptionComposite = null;
	private Label pluginNameLabel = null;
	private StyledText pluginDescriptionText = null;
	private Composite pluginDescriptionGUIComposite = null;
	private Button enablePluginCheck = null;
	private Button configurePluginButton = null;

	private Color tableItemEnabledColor = null;
	private Color tableItemDisabledColor = null;
	// END VISUALS_DECLARATION

	private void createControls() {
		// START VISUALS_INITIALIZATION
		// init visuals
		if (Crunch3.Display_1 == null)
			Crunch3.Display_1 = Display.getDefault();
		
		if (tray == null)
			tray = Crunch3.Display_1.getSystemTray();
		
		if (mainTrayItem == null) {
			mainTrayItem = new TrayItem(tray, SWT.NONE);
			trayShutdownHook = new TrayShutdownHook();
			Runtime.getRuntime().addShutdownHook(trayShutdownHook);
		}
		
		mainShell = new Shell(Crunch3.Display_1, SWT.SHELL_TRIM);
		
		
		
		trayMenu = new Menu(mainShell, SWT.POP_UP);
		trayOpenMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		traySeparator1MenuItem = new MenuItem(trayMenu, SWT.SEPARATOR);
		trayFilterMenuItem = new MenuItem(trayMenu, SWT.CHECK);
		traySeparator2MenuItem = new MenuItem(trayMenu, SWT.SEPARATOR);
		trayShoppingModeMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		trayGeneralPurposeMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		trayMaximumFilteringMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		trayCustomFilteringMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		traySeparator3MenuItem = new MenuItem(trayMenu, SWT.SEPARATOR);
		trayExitMenuItem = new MenuItem(trayMenu, SWT.PUSH);
		
		mainMenu = new Menu(mainShell, SWT.BAR);
		fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
		fileMenu = new Menu(fileMenuItem);
		exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
		helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
		helpMenu = new Menu(helpMenuItem);
		aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
		mainTabFolder = new TabFolder(mainShell, SWT.NULL);

		statusTabItem = new TabItem(mainTabFolder, SWT.NULL);
		statusComposite = new Composite(mainTabFolder, SWT.NULL);
		statusLabel = new Label(statusComposite, SWT.CENTER);
		statusText = new Text(statusComposite, SWT.BORDER);
		listenIPLabel = new Label(statusComposite, SWT.CENTER);
		listenIPText = new Text(statusComposite, SWT.BORDER);
		connectionGroup = new Group(statusComposite, SWT.NULL);
		connectionCountLabel = new Label(connectionGroup, SWT.CENTER);
		connectionCountText = new Text(connectionGroup, SWT.BORDER);
		connectionSashForm = new SashForm(connectionGroup, SWT.HORIZONTAL);
		connectionAddressGroup = new Group(connectionSashForm, SWT.NULL);
		connectionAddressList = new List(connectionAddressGroup, SWT.SINGLE | SWT.BORDER);
		connectionStatusGroup = new Group(connectionSashForm, SWT.NULL);
		connectionStatusList = new List(connectionStatusGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);

		proxySettingsTabItem = new TabItem(mainTabFolder, SWT.NULL);
		proxySettingsComposite = new Composite(mainTabFolder, SWT.NULL);
		generalSettingsGroup = new Group(proxySettingsComposite, SWT.NULL);
		verboseLabel = new Label(generalSettingsGroup, SWT.NULL);
		verboseCheck = new Button(generalSettingsGroup, SWT.CHECK);
		networkSettingsGroup = new Group(proxySettingsComposite, SWT.NULL);
		listenPortLabel = new Label(networkSettingsGroup, SWT.NULL);
		listenPortText = new Text(networkSettingsGroup, SWT.BORDER);
		serverSocketTimeoutLabel = new Label(networkSettingsGroup, SWT.NULL);
		serverSocketTimeoutText = new Text(networkSettingsGroup, SWT.BORDER);
		socketTimeoutLabel = new Label(networkSettingsGroup, SWT.NULL);
		socketTimeoutText = new Text(networkSettingsGroup, SWT.BORDER);
		useProxyLabel = new Label(networkSettingsGroup, SWT.NONE);
		useProxyCheck = new Button(networkSettingsGroup, SWT.CHECK);
		proxyServerAddressLabel = new Label(networkSettingsGroup, SWT.NONE);
		proxyServerAddressText = new Text(networkSettingsGroup, SWT.BORDER);
		proxyServerPortLabel = new Label(networkSettingsGroup, SWT.NONE);
		proxyServerPortText = new Text(networkSettingsGroup, SWT.BORDER);
		filterSettingsGroup = new Group(proxySettingsComposite, SWT.NULL);
		filteringCheckLabel = new Label(filterSettingsGroup, SWT.NULL);
		filterCheck = new Button(filterSettingsGroup, SWT.CHECK);
		filterTypesLabel = new Label(filterSettingsGroup, SWT.NULL);
		filterTypesText = new Text(filterSettingsGroup, SWT.BORDER);
		filterHomepagesLabel = new Label(filterSettingsGroup, SWT.NULL);
		filterHomepagesCheck = new Button(filterSettingsGroup, SWT.CHECK);
		proxySettingsButtonComposite = new Composite(proxySettingsComposite, SWT.NULL);
		proxySettingsCommitButton = new Button(proxySettingsButtonComposite, SWT.PUSH);
		proxySettingsRevertButton = new Button(proxySettingsButtonComposite, SWT.PUSH);

		/*
		browsingModeTabItem = new TabItem(mainTabFolder, SWT.NULL);
		browsingModeComposite = new Composite(mainTabFolder, SWT.NULL);
		browsingModeCompositeButtonGroup = new ButtonGroup();
		shoppingRadio = new Button(browsingModeComposite, SWT.RADIO);
		generalPurposeRadio = new Button(browsingModeComposite, SWT.RADIO);
		maximumFilteringRadio = new Button(browsingModeComposite, SWT.RADIO);
		customFilteringRadio = new Button(browsingModeComposite, SWT.RADIO);
		*/
		
		pluginsTabItem = new TabItem(mainTabFolder, SWT.NULL);
		pluginsComposite = new Composite(mainTabFolder, SWT.NULL);
		pluginsSashForm = new SashForm(pluginsComposite, SWT.HORIZONTAL);
		pluginGroup = new Group(pluginsSashForm, SWT.NULL);
		pluginTable = new Table(pluginGroup, SWT.BORDER | SWT.V_SCROLL);
		descriptionGroup = new Group(pluginsSashForm, SWT.NULL);
		pluginNameLabel = new Label(descriptionGroup, SWT.NULL);
		pluginDescriptionText = new StyledText(descriptionGroup, SWT.FULL_SELECTION | SWT.WRAP);
		descriptionScrolledComposite = new ScrolledComposite(descriptionGroup, SWT.V_SCROLL);
		descriptionComposite = new Composite(descriptionScrolledComposite, SWT.NULL);
		enablePluginCheck = new Button(descriptionGroup, SWT.CHECK);
		configurePluginButton = new Button(descriptionGroup, SWT.PUSH);

		InputStream imageData = getImageResourceAsStream("images/crunch2_icon.gif");
		if (imageData != null)
			crunchIcon = new Image(Crunch3.Display_1, imageData);
		else if (new java.io.File("images/crunch2_icon.gif").canRead())
			crunchIcon = new Image(Crunch3.Display_1, "images/crunch2_icon.gif");
		else if (Crunch3.settings.isVerbose())
			System.out.println("MainWindow Warning: Could not find images/crunch2_icon.gif");
		
		imageData = getImageResourceAsStream("images/crunch2_icon_active.gif");
		if (imageData != null)
			crunchIconActive = new Image(Crunch3.Display_1, imageData);
		else if (new java.io.File("images/crunch2_icon_active.gif").canRead())
			crunchIconActive = new Image(Crunch3.Display_1, "images/crunch2_icon_active.gif");
		else if (Crunch3.settings.isVerbose())
			System.out.println("MainWindow Warning: Could not find images/crunch2_icon_active.gif");
		
		imageData = getImageResourceAsStream("images/crunch2_icon_off.gif");
		if (imageData != null)
			crunchIconOff = new Image(Crunch3.Display_1, imageData);
		else if (new java.io.File("images/crunch2_icon_off.gif").canRead())
			crunchIconOff = new Image(Crunch3.Display_1, "images/crunch2_icon_off.gif");
		else if (Crunch3.settings.isVerbose())
			System.out.println("MainWindow Warning: Could not find images/crunch2_icon_off.gif");
		
		imageData = getImageResourceAsStream("images/crunch2_icon.gif");
		if (imageData != null)
			crunchIconOffActive = new Image(Crunch3.Display_1, imageData);
		else if (new java.io.File("images/crunch2_icon_off_active.gif").canRead())
			crunchIconOffActive = new Image(Crunch3.Display_1, "images/crunch2_icon_off_active.gif");
		else if (Crunch3.settings.isVerbose())
			System.out.println("MainWindow Warning: Could not find images/crunch2_icon_off_active.gif");
		
		// init nonvisuals
		FormData statusLabelFormData = new FormData();
		FormData statusTextFormData = new FormData();
		FormData listenIPLabelFormData = new FormData();
		FormData listenIPTextFormData = new FormData();
		GridData connectionCountTextGridData = new GridData();
		GridData connectionSashFormGridData = new GridData();
		FormData connectionGroupFormData = new FormData();
		GridLayout connectionGroupGridLayout = new GridLayout();
		FormLayout statusCompositeFormLayout = new FormLayout();
		GridData socketTimeoutTextGridData = new GridData();
		GridData filterTypesTextGridData = new GridData();
		GridData proxySettingsButtonCompositeGridData = new GridData();
		GridLayout proxySetttingsCompositeGridLayout = new GridLayout();
		GridData generalSettingsGroupGridData = new GridData();
		GridLayout generalSettingsGroupGridLayout = new GridLayout();
		GridData networkSettingsGroupGridData = new GridData();
		GridLayout networkSettingsGroupGridLayout = new GridLayout();
		GridData useProxyLabelGridData = new GridData();
		GridData useProxyCheckGridData = new GridData();
		GridData proxyServerAddressLabelGridData = new GridData();
		GridData proxyServerAddressTextGridData = new GridData();
		GridData proxyServerPortLabelGridData = new GridData();
		GridData proxyServerPortTextGridData = new GridData();
		
		/*
		GridLayout browsingModeCompositeGridLayout = new GridLayout();
		GridData shoppingRadioGridData = new GridData();
		GridData generalPurposeRadioGridData = new GridData();
		GridData maximumFilteringRadioGridData = new GridData();
		GridData customFilteringRadioGridData = new GridData();
		*/
		
		GridData filterSettingsGroupGridData = new GridData();
		GridLayout filterSettingsGroupGridLayout = new GridLayout();
		GridData pluginNameLabelGridData = new GridData();
		GridData pluginDescriptionTextGridData = new GridData();
		GridData configurePluginButtonGridData = new GridData();
		GridLayout descriptionGroupGridLayout = new GridLayout();
		GridData descriptionScrolledCompositeGridData = new GridData();
		GridLayout descriptionCompositeGridLayout = new GridLayout();

		// set fields
		statusLabelFormData.top = new FormAttachment(0, 0);
		statusLabelFormData.left = new FormAttachment(0, 0);
		statusTextFormData.right = new FormAttachment(100, 0);
		statusTextFormData.top = new FormAttachment(statusLabel, 0, 16777216);
		statusTextFormData.left = new FormAttachment(statusLabel, 0, 131072);
		listenIPLabelFormData.top = new FormAttachment(statusLabel, 10, 0);
		listenIPTextFormData.right = new FormAttachment(100, 0);
		listenIPTextFormData.top = new FormAttachment(statusText, 3, 0);
		listenIPTextFormData.left = new FormAttachment(listenIPLabel, 0, 131072);
		connectionCountTextGridData.horizontalAlignment = GridData.FILL;
		connectionCountTextGridData.grabExcessHorizontalSpace = true;
		connectionSashFormGridData.horizontalAlignment = GridData.FILL;
		connectionSashFormGridData.verticalAlignment = GridData.FILL;
		connectionSashFormGridData.grabExcessVerticalSpace = true;
		connectionSashFormGridData.grabExcessHorizontalSpace = true;
		connectionSashFormGridData.horizontalSpan = 2;
		connectionGroupFormData.right = new FormAttachment(100, 0);
		connectionGroupFormData.top = new FormAttachment(listenIPText, 0, 0);
		connectionGroupFormData.left = new FormAttachment(0, 0);
		connectionGroupFormData.bottom = new FormAttachment(100, 0);
		connectionGroupGridLayout.numColumns = 2;
		statusCompositeFormLayout.marginHeight = 10;
		statusCompositeFormLayout.marginWidth = 10;
		generalSettingsGroupGridData.horizontalSpan = 2;
		generalSettingsGroupGridData.horizontalAlignment = GridData.FILL;
		generalSettingsGroupGridData.grabExcessHorizontalSpace = true;
		generalSettingsGroupGridLayout.numColumns = 2;
		networkSettingsGroupGridData.horizontalAlignment = GridData.FILL;
		networkSettingsGroupGridData.horizontalSpan = 2;
		networkSettingsGroupGridLayout.numColumns = 2;
		proxyServerAddressTextGridData.horizontalAlignment = GridData.FILL;
		proxyServerAddressTextGridData.grabExcessHorizontalSpace = true;
		filterSettingsGroupGridData.grabExcessVerticalSpace = true;
		filterSettingsGroupGridData.horizontalAlignment = GridData.FILL;
		filterSettingsGroupGridData.horizontalSpan = 2;
		filterSettingsGroupGridData.verticalAlignment = GridData.FILL;
		filterSettingsGroupGridLayout.numColumns = 2;
		filterTypesTextGridData.horizontalAlignment = GridData.FILL;
		filterTypesTextGridData.grabExcessHorizontalSpace = true;
		proxySettingsButtonCompositeGridData.horizontalAlignment = GridData.END;
		proxySettingsButtonCompositeGridData.verticalAlignment = GridData.END;
		proxySettingsButtonCompositeGridData.grabExcessVerticalSpace = false;
		proxySettingsButtonCompositeGridData.horizontalSpan = 2;
		proxySettingsButtonCompositeGridData.grabExcessHorizontalSpace = true;
		proxySetttingsCompositeGridLayout.numColumns = 2;
		
		/*
		browsingModeCompositeGridLayout.numColumns = 1;
		shoppingRadioGridData.grabExcessHorizontalSpace = true;
		generalPurposeRadioGridData.grabExcessHorizontalSpace = true;
		maximumFilteringRadioGridData.grabExcessHorizontalSpace = true;
		customFilteringRadioGridData.grabExcessHorizontalSpace = true;
		*/
		
		pluginNameLabelGridData.horizontalSpan = 2;
		pluginNameLabelGridData.horizontalAlignment = GridData.FILL;
		pluginNameLabelGridData.grabExcessHorizontalSpace = true;
		pluginDescriptionTextGridData.verticalSpan = 1;
		pluginDescriptionTextGridData.horizontalAlignment = GridData.FILL;
		pluginDescriptionTextGridData.verticalAlignment = GridData.FILL;
		pluginDescriptionTextGridData.heightHint = 3*pluginDescriptionText.getLineHeight();
		//pluginDescriptionTextGridData.grabExcessVerticalSpace = true;
		pluginDescriptionTextGridData.horizontalSpan = 2;
		pluginDescriptionTextGridData.grabExcessHorizontalSpace = true;
		configurePluginButtonGridData.horizontalAlignment = GridData.END;
		descriptionGroupGridLayout.numColumns = 2;
		descriptionScrolledCompositeGridData.horizontalSpan = 2;
		descriptionScrolledCompositeGridData.grabExcessHorizontalSpace = true;
		descriptionScrolledCompositeGridData.grabExcessVerticalSpace = true;
		descriptionScrolledCompositeGridData.horizontalAlignment = GridData.FILL;
		descriptionScrolledCompositeGridData.verticalAlignment = GridData.FILL;
		descriptionCompositeGridLayout.numColumns = 2;

		// set properties
		mainTrayItem.setImage(crunchIcon);
		mainTrayItem.setToolTipText("Crunch 3.0");
		trayMenu.setDefaultItem(trayOpenMenuItem);
		trayOpenMenuItem.setText("Open Crunch Window");
		trayFilterMenuItem.setText("Filter Content");
		trayFilterMenuItem.setSelection(Crunch3.settings.isFilterContent());
		trayShoppingModeMenuItem.setText("Shopping");
		trayShoppingModeMenuItem.setEnabled(false);
		trayGeneralPurposeMenuItem.setText("General Purpose");
		trayGeneralPurposeMenuItem.setEnabled(false);
		trayMaximumFilteringMenuItem.setText("Maximum Filtering");
		trayMaximumFilteringMenuItem.setEnabled(false);
		trayCustomFilteringMenuItem.setText("Custom Filtering");
		trayCustomFilteringMenuItem.setEnabled(false);
		trayExitMenuItem.setText("Exit");
		mainShell.setImage(crunchIcon);
		mainShell.setMenuBar(mainMenu);
		mainShell.setText("Crunch 3.0");
		fileMenuItem.setText("&File");
		fileMenuItem.setAccelerator(SWT.ALT | 'f');
		exitMenuItem.setText("E&xit\tAlt+F4");
		exitMenuItem.setAccelerator(SWT.ALT | SWT.F4);
		helpMenuItem.setText("&Help");
		helpMenuItem.setAccelerator(SWT.ALT | 'h');
		aboutMenuItem.setText("&About");
		statusTabItem.setText("Status");
		statusTabItem.setControl(statusComposite);
		statusLabel.setLayoutData(statusLabelFormData);
		statusLabel.setText("Current Status: ");
		statusLabel.setToolTipText("The current status of the proxy.");
		statusText.setEditable(false);
		statusText.setLayoutData(statusTextFormData);
		statusText.setText("Not Initialized");
		listenIPLabel.setLayoutData(listenIPLabelFormData);
		listenIPLabel.setText("Listening on: ");
		listenIPLabel.setToolTipText("The port and address the proxy is currently listening on.");
		listenIPText.setEditable(false);
		listenIPText.setLayoutData(listenIPTextFormData);
		listenIPText.setText("              ");
		connectionGroup.setLayoutData(connectionGroupFormData);
		connectionGroup.setText("Connections");
		connectionCountLabel.setLayoutData(new GridData());
		connectionCountLabel.setText("Connection Count:");
		connectionCountLabel.setToolTipText("The number of connections the proxy is currently handling.");
		connectionCountText.setEditable(false);
		connectionCountText.setLayoutData(connectionCountTextGridData);
		connectionCountText.setText("0");
		connectionSashForm.setLayoutData(connectionSashFormGridData);
		connectionAddressGroup.setText("Address");
		connectionAddressList.setBackground(connectionAddressGroup.getBackground());
		connectionAddressList.setEnabled(true);
		connectionStatusGroup.setText("Status");
		connectionStatusList.setBackground(connectionStatusGroup.getBackground());
		proxySettingsTabItem.setText("Proxy Settings");
		proxySettingsTabItem.setControl(proxySettingsComposite);
		generalSettingsGroup.setLayout(generalSettingsGroupGridLayout);
		generalSettingsGroup.setLayoutData(generalSettingsGroupGridData);
		generalSettingsGroup.setText("General Settings");
		verboseLabel.setLayoutData(new GridData());
		verboseLabel.setText("&Verbose:");
		verboseLabel.setToolTipText("Enables verbose console debugging messages.");
		verboseCheck.setLayoutData(new GridData());
		networkSettingsGroup.setLayout(networkSettingsGroupGridLayout);
		networkSettingsGroup.setLayoutData(networkSettingsGroupGridData);
		networkSettingsGroup.setText("Network Settings");
		listenPortLabel.setLayoutData(new GridData());
		listenPortLabel.setText("&Listen Port:");
		listenPortLabel.setToolTipText("Changes the port the proxy will listen on.");
		listenPortText.addFocusListener(new TextIntegerListener(0, 65535));
		listenPortText.setTextLimit(5);
		listenPortText.setLayoutData(new GridData());
		listenPortText.setText(String.valueOf(Crunch3.settings.getListenPort()));
		serverSocketTimeoutLabel.setLayoutData(new GridData());
		serverSocketTimeoutLabel.setText("&Server Socket Timeout (ms):");
		serverSocketTimeoutLabel.setToolTipText("The amount of time to wait for an incoming connection to be established with the proxy");
		serverSocketTimeoutText.addFocusListener(new TextIntegerListener(0, Integer.MAX_VALUE));
		serverSocketTimeoutText.setText(String.valueOf(Crunch3.settings.getServerSocketTimeout()));
		socketTimeoutLabel.setLayoutData(new GridData());
		socketTimeoutLabel.setText("Socket &Timeout (ms):");
		socketTimeoutLabel.setToolTipText("The amount of time to wait for a network operation to complete.");
		socketTimeoutText.addFocusListener(new TextIntegerListener(0, Integer.MAX_VALUE));
		socketTimeoutText.setLayoutData(socketTimeoutTextGridData);
		socketTimeoutText.setText(String.valueOf(Crunch3.settings.getSocketTimeout()));
		useProxyLabel.setLayoutData(useProxyLabelGridData);
		useProxyLabel.setText("Use a proxy:");
		useProxyLabel.setToolTipText("Whether this proxy should connect directly to the internet or to another proxy.");
		useProxyCheck.setLayoutData(useProxyCheckGridData);
		proxyServerAddressLabel.setLayoutData(proxyServerAddressLabelGridData);
		proxyServerAddressLabel.setText("Proxy Address:");
		proxyServerAddressLabel.setToolTipText("The address of the proxy server Crunch should connect to.");
		proxyServerAddressText.setLayoutData(proxyServerAddressTextGridData);
		//proxyServerAddressText.setText("localhost"); // TODO change this to the default settings when they exist for this
		proxyServerAddressText.setText(Crunch3.settings.getExternalProxyAddress());//TODO check this
		proxyServerPortLabel.setLayoutData(proxyServerPortLabelGridData);
		proxyServerPortLabel.setText("Proxy Port:");
		proxyServerPortLabel.setToolTipText("The port of the proxy server Crunch should connect to.");
		proxyServerPortText.addFocusListener(new TextIntegerListener(0, 65535));
		proxyServerPortText.setLayoutData(proxyServerPortTextGridData);
		proxyServerPortText.setText(String.valueOf(Crunch3.settings.getListenPort()));
		proxyServerPortText.setTextLimit(5);
		filterSettingsGroup.setLayout(filterSettingsGroupGridLayout);
		filterSettingsGroup.setLayoutData(filterSettingsGroupGridData);
		filterSettingsGroup.setText("Filter Settings");
		filteringCheckLabel.setLayoutData(new GridData());
		filteringCheckLabel.setText("&Filter Content:");
		filteringCheckLabel.setToolTipText("Whether or not to filter content that passes through the proxy.");
		filterCheck.setLayoutData(new GridData());
		filterTypesLabel.setLayoutData(new GridData());
		filterTypesLabel.setText("Filter T&ypes:");
		filterTypesLabel.setToolTipText("The types of content to pass through filters.  Additional types are separated by spaces.");
		filterTypesText.setLayoutData(filterTypesTextGridData);
		filterTypesText.setText("text/html");
		filterHomepagesLabel.setLayoutData(new GridData());
		filterHomepagesLabel.setText("Filter &Homepages:");
		filterHomepagesLabel.setToolTipText("Whether or not to filter pages with URLs ending in /");
		filterHomepagesCheck.setLayoutData(new GridData());
		proxySettingsButtonComposite.setLayoutData(proxySettingsButtonCompositeGridData);
		proxySettingsCommitButton.setText("&Commit");
		proxySettingsCommitButton.setToolTipText("Causes changes made to take effect.");
		proxySettingsRevertButton.setSelection(true);
		proxySettingsRevertButton.setText("&Revert");
		proxySettingsRevertButton.setToolTipText("Reverts settings displayed to what they actually are.");
		
		/*
		browsingModeTabItem.setText("Browsing Mode");
		browsingModeTabItem.setControl(browsingModeComposite);
		browsingModeComposite.setLayout(browsingModeCompositeGridLayout);
		browsingModeCompositeButtonGroup.add(shoppingRadio);
		browsingModeCompositeButtonGroup.add(generalPurposeRadio);
		browsingModeCompositeButtonGroup.add(maximumFilteringRadio);
		browsingModeCompositeButtonGroup.add(customFilteringRadio);
		shoppingRadio.setLayoutData(shoppingRadioGridData);
		shoppingRadio.setText("Shopping");
		generalPurposeRadio.setLayoutData(generalPurposeRadioGridData);
		generalPurposeRadio.setText("General Purpose");
		maximumFilteringRadio.setLayoutData(maximumFilteringRadioGridData);
		maximumFilteringRadio.setText("Maximum Filtering");
		customFilteringRadio.setLayoutData(customFilteringRadioGridData);
		customFilteringRadio.setText("Custom Filtering");
		*/
		
		pluginsTabItem.setText("Plugins");
		pluginsTabItem.setControl(pluginsComposite);
		pluginsSashForm.setWeights(new int[]{100, 300});
		pluginGroup.setText("&Plugins");
		descriptionGroup.setText("Description");
		pluginNameLabel.setFont(
			new Font(
				Crunch3.Display_1,
				pluginNameLabel.getFont().getFontData()[0].getName(),
				pluginNameLabel.getFont().getFontData()[0].getHeight(),
				SWT.BOLD));
		pluginNameLabel.setLayoutData(pluginNameLabelGridData);
		pluginNameLabel.setText(" ");
		pluginDescriptionText.setEditable(false);
		pluginDescriptionText.setBackground(descriptionGroup.getBackground());
		pluginDescriptionText.setLayoutData(pluginDescriptionTextGridData);
		pluginDescriptionText.setWordWrap(true);
		enablePluginCheck.setSelection(true);
		enablePluginCheck.setLayoutData(new GridData());
		enablePluginCheck.setText("&Enable");
		enablePluginCheck.setToolTipText("Allows content to be passed through plugin during filtering.");
		configurePluginButton.setLayoutData(configurePluginButtonGridData);
		configurePluginButton.setText("&Configure...");
		configurePluginButton.setToolTipText("Changes plugin settings.");
		descriptionGroup.setLayout(descriptionGroupGridLayout);
		descriptionScrolledComposite.setContent(descriptionComposite);
		descriptionScrolledComposite.setExpandHorizontal(true);
		descriptionScrolledComposite.setLayoutData(descriptionScrolledCompositeGridData);
		descriptionComposite.setLayout(descriptionCompositeGridLayout);
		pluginGroup.setLayout(new FillLayout());
		pluginsComposite.setLayout(new FillLayout());
		proxySettingsButtonComposite.setLayout(new FillLayout());
		proxySettingsComposite.setLayout(proxySetttingsCompositeGridLayout);
		connectionStatusGroup.setLayout(new FillLayout());
		connectionAddressGroup.setLayout(new FillLayout());
		connectionGroup.setLayout(connectionGroupGridLayout);
		statusComposite.setLayout(statusCompositeFormLayout);
		helpMenuItem.setMenu(helpMenu);
		fileMenuItem.setMenu(fileMenu);
		mainShell.setLayout(new FillLayout());
		// END VISUALS_INITIALIZATION

		// START EVENT_INITIALIZATION
		mainTrayItem.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e){
				mainTrayItem_defaultSelection(e);
			}
		});
		mainTrayItem.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event e) {
				mainTrayItem_menuDetect(e);
			}
		});
		trayOpenMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e){
				trayOpenMenuItem_widgetSelected(e);
			}
		});
		trayFilterMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e){
				trayFilterMenuItem_widgetSelected(e);
			}
		});
		//trayShoppingModeMenuItem;
		//trayGeneralPurposeMenuItem;
		//trayMaximumFilteringMenuItem;
		//trayCustomFilteringMenuItem;
		trayExitMenuItem.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(final SelectionEvent e){
				trayExitMenuItem_widgetSelected(e);
			}
		});
		mainShell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
				mainShell_shellClosed(e);
			}
		});
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				exitMenuItem_widgetSelected(e);
			}
		});
		aboutMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				aboutMenuItem_widgetSelected(e);
			}
		});
		useProxyCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				useProxyCheck_widgetSelected(e);
			}
		});
		proxySettingsCommitButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				proxySettingsCommitButton_widgetSelected(e);
			}
		});
		proxySettingsRevertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				proxySettingsRevertButton_widgetSelected(e);
			}
		});
		pluginTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				pluginTable_widgetSelected(e);
			}
		});
		enablePluginCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				enablePluginCheck_widgetSelected(e);
			}
		});
		configurePluginButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				configurePluginButton_widgetSelected(e);
			}
		});
		// END EVENT_INITIALIZATION
	}


	// START EVENT_HANDLING
	private InputStream getImageResourceAsStream(final String name) { // enable image
		// loading from
		// jars
		return getClass().getResourceAsStream("/" + name);
	}
	private void mainTrayItem_defaultSelection(final Event e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("mainTrayItem_defaultSelection: " + e);
		
			mainShell.setVisible(true);
			mainShell.setActive();
		
	}
	private void mainTrayItem_menuDetect(final Event e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("mainTrayItem_menuDetect: " + e);
		
		trayFilterMenuItem.setSelection(Crunch3.settings.isFilterContent());
		trayMenu.setVisible(true);
	}
	private void trayOpenMenuItem_widgetSelected(final SelectionEvent e){
		if (Crunch3.settings.isVerbose())
			System.out.println("trayOpenMenuItem_widgetSelected: " + e);
		
		mainShell.setVisible(true);
		mainShell.setActive();
	}
	private void trayFilterMenuItem_widgetSelected(final SelectionEvent e){
		if (Crunch3.settings.isVerbose())
			System.out.println("trayFilterMenuItem_widgetSelected: " + e);
		
		Object o = e.getSource();
		if(o == trayFilterMenuItem)
			Crunch3.settings.setFilterContent(trayFilterMenuItem.getSelection());
		else if (Crunch3.settings.isVerbose())
			System.out.println("Wrong widget used to fire event: " + o);
		
		revertSettings();
	}
	private void trayExitMenuItem_widgetSelected(final SelectionEvent e){
		if (Crunch3.settings.isVerbose()){
			System.out.println("trayExitMenuItem_widgetSelected: " + e);
		}
		
		new Thread("CrunchShutdownThread"){
			public void run() {
				System.exit(0);
			}
		}.start();	
	}
	private void mainShell_shellClosed(final ShellEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("mainShell_shellClosed: " + e);
		
		e.doit = false;
		mainShell.setVisible(false);
		
		if(mainTrayItem == null)
			new Thread("CrunchShutdownThread"){
				public void run() {
					System.exit(0);
				}
			}.start();
	}
	private void exitMenuItem_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("exitMenuItem_widgetSelected: " + e);
		new Thread("CrunchShutdownThread"){
			public void run() {
				System.exit(0);
			}
		}.start();
	}
	private void aboutMenuItem_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("aboutMenuItem_widgetSelected: " + e);
		AboutShell.showAboutShell();
	}
	private void useProxyCheck_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("useProxyCheck_widgetSelected: " + e);

		proxyServerAddressText.setEnabled(useProxyCheck.getSelection());
		proxyServerPortText.setEnabled(useProxyCheck.getSelection());
	}
	private void proxySettingsCommitButton_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("proxySettingsCommitButton_widgetSelected: " + e);

		commitSettings();
		revertSettings();
	}

	private void proxySettingsRevertButton_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("proxySettingsRevertButton_widgetSelected: " + e);

		revertSettings();
	}

	private void pluginTable_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose()) {
			System.out.println("pluginTable_widgetSelected: " + e);
			System.out.println(" selected: " + pluginTable.getSelectionIndex());
		}
		boolean redraw = false;
		int selectionIndex = pluginTable.getSelectionIndex();
		if (selectionIndex == -1)
			return;

		
		TableItem selectedItem = pluginTable.getItem(selectionIndex);
		
		ProxyFilter plugin = (ProxyFilter) selectedItem.getData();
		
		pluginNameLabel.setText(plugin.getName());
		pluginDescriptionText.setText(plugin.getDescription());
		
		if (pluginDescriptionGUIComposite != null && !pluginDescriptionGUIComposite.isDisposed()) {
			pluginDescriptionGUIComposite.dispose();
			redraw = true;
		}
		if (plugin instanceof EnhancedProxyFilter){
			EnhancedProxyFilter enhancedPlugin = (EnhancedProxyFilter)plugin;
			pluginDescriptionGUIComposite = enhancedPlugin.getDescriptionGUI(descriptionComposite);
			redraw = true;
		}
		enablePluginCheck.setSelection(plugin.isEnabled());
		configurePluginButton.setEnabled(plugin.hasSettingsGUI());
		
		if (redraw) {
			descriptionGroup.layout();
			descriptionComposite.setSize(descriptionComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			descriptionComposite.layout();
			descriptionComposite.redraw();
			descriptionScrolledComposite.setAlwaysShowScrollBars(true);
		}
	}
	private void enablePluginCheck_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("enablePlugin_widgetSelected: " + e);

		// get selected item index
		int selectionIndex = pluginTable.getSelectionIndex();
		if (selectionIndex == -1)
			return;

		// get selected item and update enabled status
		TableItem selectedItem = pluginTable.getItem(selectionIndex);
		ProxyFilter plugin = (ProxyFilter) selectedItem.getData();
		plugin.setEnabled(enablePluginCheck.getSelection());
		if (plugin.isEnabled())
			selectedItem.setForeground(tableItemEnabledColor);
		else
			selectedItem.setForeground(tableItemDisabledColor);
	}
	private void configurePluginButton_widgetSelected(final SelectionEvent e) {
		if (Crunch3.settings.isVerbose())
			System.out.println("configurePluginButton_widgetSelected: " + e);

		int selectionIndex = pluginTable.getSelectionIndex();
		if (selectionIndex == -1)
			return;

		TableItem selectedItem = pluginTable.getItem(selectionIndex);
		ProxyFilter plugin = (ProxyFilter) selectedItem.getData();
		plugin.getSettingsGUI();
	}
	// END EVENT_HANDLING
	
	public MainWindow() {
		super("MainWindowDisplayThread");
		this.start();

		//don't release control until the gui is initialize
		while (!isInitialized && this.isAlive()) {
			yield();
		}

		this.updateThread = new MainWindow.UpdateThread();
	}

	public void run() {
		try {
			//int windowHeight = 509;
			int windowHeight = 760;
			//int windowWidth = 447;
			int windowWidth = 515;
			
			createControls();
			revertSettings();
			mainShell.setBounds(0, 0, windowWidth, windowHeight);
			mainShell.setBounds(
				Crunch3.Display_1.getBounds().width / 2 - mainShell.getBounds().width / 2,
				Crunch3.Display_1.getBounds().height / 2 - mainShell.getBounds().height / 2,
				windowWidth,
				windowHeight);
			if(Crunch3.settings.isGUISet())
			mainShell.open();
			//this opens crunch without GUI and loads one settings file for the whole session
			else{
				processNoGUI();
			}
			isInitialized = true;
			

			while (!mainShell.isDisposed()) {
				try {
					if (!Crunch3.Display_1.readAndDispatch())
						Crunch3.Display_1.sleep();
				} catch (Exception e) {
					if (Crunch3.settings.isVerbose())
						e.printStackTrace();
				} catch (Error e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		} catch (Throwable t) {
			System.out.println(t);
		} finally {
			Crunch3.Display_1.dispose();
			new Thread("CrunchShutdownThread"){
				public void run() {
					System.exit(0);
				}
			}.start();
		}
	}

	/**
	 * Set the listening address display.
	 * 
	 * @param s
	 *            the ip address
	 * @param port
	 *            the port
	 */
	public void setListeningOn(final String s, final int port) {
		Crunch3.Display_1.asyncExec(new Runnable() {
			public void run() {
				listenIPText.setText(s + ":" + port);
			}
		});
	}

	/**
	 * Sets the general proxy status as displayed in the gui.
	 * 
	 * @param s
	 *            The status to be displayed.
	 */
	public void setProxyStatus(final String s) {
		Crunch3.Display_1.asyncExec(new Runnable() {
			public void run() {
				if(statusText != null && !statusText.isDisposed())
					statusText.setText(s);
			}
		});
	}

	/**
	 * Registers a new connection in the connections status lists.
	 * 
	 * @param hs
	 */
	public void addNewConnection(final HttpStream hs) {
		synchronized (connections) {
			connections.add(hs);
		}
		updateConnections();
	}

	/**
	 * Removes a connection from the connection status list. This is usually because the httpstream is done working.
	 * 
	 * @param hs
	 *            HttpStream to remove.
	 */
	public void removeConnection(final HttpStream hs) {
		
		synchronized (connections) {
			connections.remove(hs);
		}
		updateConnections();
	}
	
	
	/**
	 * Gets a list of the current connections and sends them to the status lists.
	 *  
	 */
	private void updateConnections() {
		Object[] connectionStreamObjects;
		synchronized (connections) {
			connectionStreamObjects = connections.toArray();
		}
		String[] addresses = new String[connectionStreamObjects.length];
		String[] statuses = new String[connectionStreamObjects.length];
		for (int i = 0; i < connectionStreamObjects.length; i++) {
			HttpStream connection = (HttpStream) connectionStreamObjects[i];
			addresses[i] = connection.clientSocket.getRemoteSocketAddress().toString();
			statuses[i] = connection.getStatus();
		}
		updateConnections_2(addresses, statuses);
	}

	private void updateConnections_2(final String[] addresses, final String[] statuses) {
		if (addresses.length == 0) {
			setProxyStatus("Listening for requests");
		} else {
			setProxyStatus("Processing requests");
		}
		Crunch3.Display_1.asyncExec(new Runnable() {
			public void run() {
				connectionAddressList.setItems(addresses);
				connectionStatusList.setItems(statuses);
				connectionCountText.setText(String.valueOf(addresses.length));
				
				if(mainTrayItem != null) {
					if(addresses.length == 0){
						if (Crunch3.settings.isFilterContent())
							mainTrayItem.setImage(crunchIcon);
						else
							mainTrayItem.setImage(crunchIconOff);
					} else {
						if (Crunch3.settings.isFilterContent())
							mainTrayItem.setImage(crunchIconActive);
						else
							mainTrayItem.setImage(crunchIconOffActive);
						
					}
				}
			}
		});
	}
	
	
	public void updateStatus() {
		// TODO implement update status
	}

	/**
	 * Adds a plugin to the plugins table.
	 * 
	 * @param plug
	 *            the plugin to add to the table
	 */
	public void addPlugin(final ProxyFilter plug) {
		Crunch3.Display_1.syncExec(new Runnable() {
			public void run() {
				TableItem item = new TableItem(pluginTable, SWT.NONE);
				item.setData(plug);
				item.setText(plug.getName());
				if (tableItemEnabledColor == null)
					tableItemEnabledColor = item.getForeground();
				if (tableItemDisabledColor == null)
					tableItemDisabledColor = Crunch3.Display_1.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
				if (plug.isEnabled())
					item.setForeground(tableItemEnabledColor);
				else
					item.setForeground(tableItemDisabledColor);
			}
		});
	}

	class UpdateThread extends Thread {
		public static final int UPDATE_DELAY = 1000;

		public UpdateThread() {
			super("MainWindowUpdateThread");
			this.setDaemon(true);
			this.start();
		}
		public void run() {
			while (true) {
				updateStatus();
				updateConnections();
				try {
					Thread.sleep(UPDATE_DELAY);
				} catch (InterruptedException e) {
					// don't need to do anything on this kind of exception
				}
			}
		}
	}

	public String getURL(){
		// TODO test 
		return currentURL.trim();
	}
	
	public void setURL(String url){
		currentURL = url;
	}
	
	public String parseURL(String original, String host){
		
		try{
		//original should be in the form GET /... HTTP/1.1
		int length = original.length()-9;
		return "http://" + host.trim() + original.substring(4,length).trim();
		
		}
		catch(Exception e){
			if (Crunch3.settings.isVerbose())
	    		System.out.println("error parsing the url");
		}
		return "";
	}
	
	
	public void setCustom(){
		int selectionIndex = pluginTable.getSelectionIndex();
		if (selectionIndex == 0){  //ContentExtractor is selected
			TableItem selectedItem = pluginTable.getItem(selectionIndex);
			EnhancedProxyFilter enhancedPlugin = (EnhancedProxyFilter)(selectedItem.getData());
			enhancedPlugin.selectCustom();
		}
	}
	
	/**
	 * @return the main shell
	 */
	public Shell getShell() {
		return mainShell;
	}
	private void commitSettings() {
		Crunch3.settings.setVerbose(verboseCheck.getSelection());
		Crunch3.settings.setListenPort(Integer.parseInt(listenPortText.getText()));
		Crunch3.settings.setServerSocketTimeout(Integer.parseInt(serverSocketTimeoutText.getText()));
		Crunch3.settings.setSocketTimeout(Integer.parseInt(socketTimeoutText.getText()));
		Crunch3.settings.setUseExternalProxy(useProxyCheck.getSelection());
		Crunch3.settings.setExternalProxyAddress(proxyServerAddressText.getText());
		Crunch3.settings.setExternalProxyPort(Integer.parseInt(proxyServerPortText.getText()));
		Crunch3.settings.setFilterContent(filterCheck.getSelection());
		Crunch3.settings.setFilterTypes(
		//filterTypesText.getText().split("\\s+"));
		split(filterTypesText.getText(), " "));
		Crunch3.settings.setFilterHomepages(filterHomepagesCheck.getSelection());
	}
	private void revertSettings() {
		verboseCheck.setSelection(Crunch3.settings.isVerbose());
		listenPortText.setText(String.valueOf(Crunch3.settings.getListenPort()));
		serverSocketTimeoutText.setText(String.valueOf(Crunch3.settings.getServerSocketTimeout()));
		socketTimeoutText.setText(String.valueOf(Crunch3.settings.getSocketTimeout()));
		useProxyCheck.setSelection(Crunch3.settings.getUseExternalProxy());
		proxyServerAddressText.setText(Crunch3.settings.getExternalProxyAddress());
		proxyServerAddressText.setEnabled(useProxyCheck.getSelection());
		proxyServerPortText.setText(String.valueOf(Crunch3.settings.getExternalProxyPort()));
		proxyServerPortText.setEnabled(useProxyCheck.getSelection());
		filterCheck.setSelection(Crunch3.settings.isFilterContent());
		filterTypesText.setText(Crunch3.settings.getFilterTypes());
		filterHomepagesCheck.setSelection(Crunch3.settings.isFilterHomepages());
	}

	private void processNoGUI(){
		ContentExtractorDescriptionGUI dg = new ContentExtractorDescriptionGUI(mainShell);
	}
	
	private String[] split(final String text, final String delimiters) {
		StringTokenizer tokens = new StringTokenizer(text, delimiters);
		String[] strings = new String[tokens.countTokens()];
		for (int i = 0; i < strings.length; i++)
			strings[i] = tokens.nextToken();
		return strings;
	}
}
