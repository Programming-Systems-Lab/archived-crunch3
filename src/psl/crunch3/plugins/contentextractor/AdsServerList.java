/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.contentextractor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import psl.crunch3.Crunch3;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class AdsServerList {
	public static final String AD_FILE = "serverlist.txt";

	private static HashSet mAdServers; //hashtable of ad servers

	/**
	 * Loads the ad file into a hashtable
	 */
	public static void loadAdsServerList() {
		mAdServers = new HashSet();

		try {
			InputStream is = null;
			File adServerList = new File(AD_FILE);
			if (!adServerList.exists() || !adServerList.canRead())
				is = getResourceAsStream(AD_FILE);
			else
				is = new FileInputStream(adServerList);
			
			BufferedInputStream bis = new BufferedInputStream(is);
			InputStreamReader isr = new InputStreamReader(bis);
			BufferedReader in = new BufferedReader(isr);
			String line = in.readLine();

			while (line != null) {
				mAdServers.remove(line);
				mAdServers.add(line);
				line = in.readLine();
			} //while
			
			if (Crunch3.settings.isVerbose())
				System.out.println("AdServerList Loaded: "+mAdServers.size()+" servers in list.");
			
		} catch (FileNotFoundException e) {
			//if the ad file is not there, don't do anything, just print
			//that the file isn't there
			System.out.println(
				"AdServerList: WARNING: Server list for ad remover not found.");
		} catch (NullPointerException e) {
			System.out.println(
				"AdServerList: WARNING: Server list for ad remover not found.");
		} catch (IOException e) {
			if("Stream closed".equalsIgnoreCase(e.getMessage())) {
				System.out.println("AdServerList: WARNING: Server list for ad remover not found.");
			} else {
				e.printStackTrace();
			}
		}
	} //loadAdsServerList

	public static boolean isAdsServer(final String servername) {
		if (mAdServers == null)
			loadAdsServerList();
		return mAdServers.contains(servername);
	}

	private static InputStream getResourceAsStream(final String name) {
		// enable loading from jars
		return new Object().getClass().getResourceAsStream("/" + name);
	}
}
