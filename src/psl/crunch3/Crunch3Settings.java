/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;


import java.util.HashSet;
import java.util.Vector;

/**
 * Handles all the arguments and settings for Crunch3.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class Crunch3Settings {
	public static final boolean VERBOSE_DEF = false;
	public static final boolean GUI_DEF = false;
	public static final int LISTEN_PORT_DEF = 4000;
	public static final int SERVER_SOCKET_TIMEOUT_DEF = 1000;
	public static final int SOCKET_TIMEOUT_DEF = 5000;
	public static final boolean USE_EXTERNAL_PROXY_DEF = true;
	public static final String EXTERNAL_PROXY_ADDRESS_DEF = "localhost";
	public static final int EXTERNAL_PROXY_PORT_DEF = 8080;
	public static final boolean FILTER_CONTENT_DEF = true;
	public static final String[] FILTER_TYPES_DEF = { "text/html" };
	public static final boolean FILTER_HOMEPAGES_DEF = true;
	public static final boolean PROXY_MODE_DEF = true;
	public static String SETTINGS_FILE = "config/content extractor settings.ini";
	public static final boolean CHECK_HOMEPAGE_DEF = false;
	public static final boolean RUN_ON_SERVER = false;

	//internal variables
	private String[] arguments;
	private Vector additionalArgs;

	// settings variables
	private boolean verbose;
	private boolean verboseSet = false;

	private boolean gui;
	private boolean guiSet = false;
	
	private int listenPort;
	private boolean listenPortSet = false;

	private int serverSocketTimeout;
	private boolean serverSocketTimeoutSet = false;

	private int socketTimeout;
	private boolean socketTimeoutSet = false;
	
	private boolean useExternalProxy;
	private boolean useExternalProxySet = false;
	
	private String externalProxyAddress;
	private boolean externalProxyAddressSet = false;
	
	private int externalProxyPort;
	private boolean externalProxyPortSet = false;

	private boolean filterContent;
	private boolean filterContentSet = false;

	private HashSet filterTypesHashSet;
	private String[] filterTypes;
	private boolean filterTypesSet = false;
	
	private boolean filterHomepages;
	private boolean filterHomepagesSet = false;

	private boolean proxyMode = true;
	private boolean proxyModeSet = false;
	
	private boolean settingsFile;
	private boolean settingsFileSet = false;
	
	private boolean checkHomePage;
	private boolean checkHomePageSet = false;

	
	
	/**
	 * Constructs a new Crunch3Settings using a given set of arguments. Usually
	 * the arguments given will be from the main function.
	 * 
	 * @param args
	 *            Command line arguments to be parsed.
	 */
	public Crunch3Settings(String[] args) {
		arguments = args;
		initialize();
	}

	/**
	 * Reads in all command line arguments.
	 *  
	 */
	private void initialize() {
		loadSettings();

		//create additional args vector
		additionalArgs = new Vector();

		//for all the arguments
		for (int argNumber = 0; argNumber < arguments.length;) {

			//store the old argument number
			int oldArgNumber = argNumber;

			//process the current command line argument
			//and increment the current argument
			argNumber += processArgument(argNumber);

			//ensure that the arguments are being processed (no hangs)
			if (argNumber <= oldArgNumber) {
				System.out.println(
					"Error with command line argument number "
						+ argNumber
						+ ": "
						+ arguments[argNumber]);
				System.out.println(
					"  Handling function does not iterate argument.");
				argNumber++;
			} //if
		} //for
	} //initialize()

	/**
	 *  
	 */
	private void loadSettings() {
		// TODO Implement loadSettings

	}

	/**
	 * Takes a given argument represented by its respective number in the array
	 * of arguments and figures out what to do with it. The number of arguments
	 * parsed including the current one is returned so the loop can restart at
	 * the next intelligent argument.
	 * 
	 * @param argNumber
	 *            The number of the argument to be processed with respect to
	 *            the argument array.
	 * @return the number of arguments that were processed
	 */
	private int processArgument(int argNumber) {
		//ensure that the argument number is valid
		if (argNumber < 0 || argNumber >= arguments.length) {
			System.out.println(
				"Error: Argument Number is out of bounds: " + argNumber);
			return 1;
		}

		//grab the respective argument
		String argument = arguments[argNumber];

		if (null == argument) { //if null, print an error
			System.out.println("Error: argument " + argNumber + " is null.");
		} else if ("-v".equals(argument) || "--verbose".equals(argument)) {
			setVerbose(true);
		} else if ("-p".equals(argument) || "--port".equals(argument)) {
			processPortArgument(argNumber + 1);
			return 2;
		} else if (
			"-sst".equals(argument)
				|| "--server-socket-timeout".equals(argument)) {
			processServerSocketTimeout(argNumber + 1);
			return 2;
		} else if (
			"-st".equals(argument) || "--socket-timeout".equals(argument)) {
			processSocketTimeout(argNumber + 1);
			return 2;
		} else if ("-g".equals(argument) || "--gui".equals(argument)) {
			setGUI(true);
		} else if ("-f".equals(argument) || "--file".equals(argument)){
			setSettingsFile(arguments[argNumber+1]);
		} else if ("-h".equals(argument) || "--homepage".equals(argument)){
			setHomePageCheck(true);
		}
		
		else {
			additionalArgs.add(argument);
		}

		//the default is to process only one argument
		return 1;
	}

	/**
	 * @param argNumber
	 *            the command line argument that contains the socket timeout
	 */
	private void processSocketTimeout(int argNumber) {
		//ensure the argument number is valid
		if (argNumber < 0 || argNumber >= arguments.length) {
			System.out.println(
				"ArgumentParser Error: "
					+ "no argument for Server Socket Timeout was given.");
			return;
		}

		//grab the socket timeout string
		String socketTimeout = arguments[argNumber];

		int socketTimeoutNumber = 0;

		//parse the socket timeout string
		try {
			socketTimeoutNumber = Integer.parseInt(socketTimeout);
		} catch (Exception e) {
			System.out.println(
				"Invalid Socket Timeout: " + serverSocketTimeout);
			return;
		}

		//ensure the socket timeout specified is in the valid range of numbers
		if (socketTimeoutNumber < 0 || socketTimeoutNumber > 65535) {
			System.out.println(
				"Socket Timeout is out of range: " + socketTimeoutNumber);
			return;
		}

		//set the socket timeout
		setSocketTimeout(socketTimeoutNumber);
	}

	/**
	 * @param argNumber
	 *            the command line argument that contains the server socket
	 *            timeout
	 */
	private void processServerSocketTimeout(int argNumber) {
		//ensure the argument number is valid
		if (argNumber < 0 || argNumber >= arguments.length) {
			System.out.println(
				"ArgumentParser Error: "
					+ "no argument for Server Socket Timeout was given.");
			return;
		}

		//grab the server socket timeout string
		String serverSocketTimeout = arguments[argNumber];

		int serverSocketTimeoutNumber = 0;

		//parse the server socket timeout string
		try {
			serverSocketTimeoutNumber = Integer.parseInt(serverSocketTimeout);
		} catch (Exception e) {
			System.out.println(
				"Invalid Server Socket Timeout: " + serverSocketTimeout);
			return;
		}

		//ensure the server socket timeout specified is in the valid range of
		// numbers
		if (serverSocketTimeoutNumber < 0
			|| serverSocketTimeoutNumber > 65535) {
			System.out.println(
				"Server Socket Timeout is out of range: "
					+ serverSocketTimeoutNumber);
			return;
		}

		//set the server socket timeout
		setServerSocketTimeout(serverSocketTimeoutNumber);
	}

	/**
	 * Processes a port argument. Prints an error if the port was invalid or
	 * nonexistant.
	 * 
	 * @param argNumber
	 *            the argument that contains the number of the port.
	 */
	private void processPortArgument(int argNumber) {
		//ensure the argument number is valid
		if (argNumber < 0 || argNumber >= arguments.length) {
			System.out.println("Error: no argument for port number was given.");
			return;
		}

		//grab the port string
		String port = arguments[argNumber];

		int portNumber = 0;

		//parse the port string
		try {
			portNumber = Integer.parseInt(port);
		} catch (Exception e) {
			System.out.println("Invalid port number: " + port);
			return;
		}

		//ensure the port specified is in the valid range of port numbers
		if (portNumber < 0 || portNumber > 65535) {
			System.out.println("Port is out of range: " + port);
			return;
		}

		//set the port
		setListenPort(portNumber);
	}

	/**
	 * @return the port the proxy should listen on
	 */
	public int getListenPort() {
		if (listenPortSet)
			return listenPort;
		else
			return LISTEN_PORT_DEF;
	}

	/**
	 * @return whether or not to display verbose debugging statements
	 */
	public boolean isVerbose() {
		if (verboseSet)
			return verbose;
		else
			return VERBOSE_DEF;
	}

	/**
	 * @return whether or not Crunch is running in proxy mode or standalone
	 *         file mode
	 */
	public boolean isProxyMode() {
		if (proxyModeSet)
			return proxyMode;
		else
			return PROXY_MODE_DEF;
	}

	public boolean isGUISet(){
		if(guiSet){
			return gui;
		}
		else return GUI_DEF;
	}
	
	public String getSettings(){
		return SETTINGS_FILE;
	}
	/**
	 * Sets the port to listen on in proxy mode. Valid ports are in the range
	 * from 0 to 65535.
	 * 
	 * @param i
	 *            the port to listen on in proxy mode
	 */
	public void setListenPort(int i) {
		// TODO improve set listen port so it actually changes the listen port
		if (i >= 0 && i <= 65535) {
			listenPort = i;
			listenPortSet = true;
			if(Crunch3.proxy != null)
				Crunch3.proxy.setListenPort(i);
		}
	}
	/**
	 * @param b
	 *            whether or not to run in proxy mode
	 */
	public void setProxyMode(boolean b) {
		proxyMode = b;
		proxyModeSet = true;
	}

	/**
	 * @param b
	 *            whether or not to display verbose debugging messages
	 */
	public void setVerbose(boolean b) {
		verbose = b;
		verboseSet = true;
	}
	
	public void setGUI(boolean b){
		gui = b;
		guiSet = true;
	}

	public void setHomePageCheck(boolean b){
		if(!settingsFileSet){
			checkHomePage = true;
			checkHomePageSet = true;
		}
		
	}
	
	/**
	 * @return the time the server socket should wait for data before timing
	 *         out
	 */
	public int getServerSocketTimeout() {
		if (!serverSocketTimeoutSet)
			return SERVER_SOCKET_TIMEOUT_DEF;
		else
			return serverSocketTimeout;
	}
	/**
	 * @param i
	 *            the amount of time the server socket should use to wait for
	 *            connections to be established.
	 */
	public void setServerSocketTimeout(int i) {
		if (i >= 0) {
			serverSocketTimeout = i;
			serverSocketTimeoutSet = true;
			if (Crunch3.proxy != null)
				Crunch3.proxy.setServerSocketTimeout(i);
		}
	}
	/**
	 * @return the time ordinary sockets should wait before timing out
	 */
	public int getSocketTimeout() {
		if (socketTimeoutSet)
			return socketTimeout;
		else
			return SOCKET_TIMEOUT_DEF;
	}
	/**
	 * @param i
	 *            the amount of time sockets should wait when performing io
	 *            operations.
	 */
	public void setSocketTimeout(int i) {
		if (i >= 0) {
			socketTimeout = i;
			socketTimeoutSet = true;
		}
	}
	
	/**
	 * @return
	 */
	public boolean getUseExternalProxy() {
		if (useExternalProxySet)
			return useExternalProxy;
		else
			return USE_EXTERNAL_PROXY_DEF;
	}
	
	public void setUseExternalProxy(boolean b) {
		useExternalProxy = b;
		useExternalProxySet = true;
	}
	
	public String getExternalProxyAddress() {
		if (externalProxyAddressSet)
			return externalProxyAddress;
		else
			return EXTERNAL_PROXY_ADDRESS_DEF;
	}
	
	public void setExternalProxyAddress(String s) {
		if (s != null) {
			externalProxyAddress = s;
			externalProxyAddressSet = true;
		}
	}
	
	public int getExternalProxyPort() {
		if(externalProxyPortSet)
			return externalProxyPort;
		else
			return EXTERNAL_PROXY_PORT_DEF;
	}
	
	public void setExternalProxyPort(int i) {
		if (i >= 0 && i <= 65535) {
			externalProxyPort = i;
			externalProxyPortSet = true;
		}
	}
	
	public void setSettingsFile(String s){
		SETTINGS_FILE = s;
		settingsFileSet = true;
		if(checkHomePageSet == true){
			checkHomePage = false;
		}
	}
	
	public String getSettingsFile(){
		return SETTINGS_FILE;
	}
	
	/**
	 * @param b whether or not to filter content.
	 */
	public void setFilterContent(boolean b) {
		filterContent = b;
		filterContentSet = true;
	}
	/**
	 * @return a string containing the filter types separated by spaces.
	 */
	public String getFilterTypes() {
		StringBuffer typesString = new StringBuffer();
		if (filterTypesSet) {
			String[] types = filterTypes;
			for(int i = 0; i < types.length; i++)
				typesString.append(types[i]).append(" ");
		} else {
			for(int i = 0; i < FILTER_TYPES_DEF.length; i++)
				typesString.append(FILTER_TYPES_DEF[i]).append(" ");
		}
		return typesString.toString().trim();
	}
	public boolean isFilterType(final String type) {
		if (filterTypesHashSet == null) {
			setFilterTypes(FILTER_TYPES_DEF);
		}
		
		if(filterTypesHashSet.contains(type.toLowerCase())) {
			return true;
		} else if (type.indexOf(";") > type.indexOf("/")) {
			return filterTypesHashSet.contains(type.substring(0, type.indexOf(";")).toLowerCase());
		}
		return false;
	}
	/**
	 * @param strings
	 */
	public void setFilterTypes(final String[] strings) {
		HashSet hs = new HashSet();
		for (int i = 0; i < strings.length; i++)
			hs.add(strings[i].toLowerCase());
		filterTypesHashSet = hs;
		filterTypes = strings;
		filterTypesSet = true;
	}
	/**
	 * @return whether or not to filter content.
	 */
	public boolean isFilterContent() {
		if (filterContentSet)
			return filterContent;
		else
			return FILTER_CONTENT_DEF;
	}
	/**
	 * @return whether or not to filter homepages.
	 */
	public boolean isFilterHomepages() {
		if (filterHomepagesSet)
			return filterHomepages;
		else
			return FILTER_HOMEPAGES_DEF;
	}
	/**
	 * @param b
	 */
	public void setFilterHomepages(boolean b) {
		filterHomepages = b;
		filterHomepagesSet = true;
	}
	
	public boolean isHomePageCheck(){
		return checkHomePage;
		
	}

}
