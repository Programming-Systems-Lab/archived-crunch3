/*
 * Copyright (c) 2003: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.sizemodifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import psl.crunch3.TypedProperties;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class SizeModifierSettings {
	private static final String SETTINGS_FILE_DEF =
		"config" + File.separator + "size modifier settings.ini";

	private static SizeModifierSettings self = null;

	private String settingsFile;
	private TypedProperties settings;

	// settings
	boolean resizeFonts;
	public static final String RESIZE_FONTS = "Resize Fonts";
	public static final boolean RESIZE_FONTS_DEF = true;

	int resizeFontsIndex;
	public static final String RESIZE_FONTS_INDEX = "Resize Fonts Index";
	public static final int RESIZE_FONTS_INDEX_DEF = 7;
	public static final String[] FONT_SIZES =
		{ "-5", "-4", "-3", "-2", "-1", "+0", "+1", "+2", "+3", "+4", "+5" };

	boolean rescaleImages;
	public static final String RESCALE_IMAGES = "Rescale Images";
	public static final boolean RESCALE_IMAGES_DEF = true;
	
	int rescaleImagesIndex;
	public static final String RESCALE_IMAGES_INDEX = "Rescale Images Index";
	public static final int RESCALE_IMAGES_INDEX_DEF = 7;
	public static final String[] IMAGE_SCALING_FACTORS =
		{
			"0.176777",
			"0.25",
			"0.353554",
			"0.5",
			"0.707106",
			"1",
			"1.414214",
			"2",
			"2.828427",
			"4",
			"5.656854" };

	public static SizeModifierSettings getInstance() {
		if (self == null)
			self = new SizeModifierSettings();
		return self;
	}

	private SizeModifierSettings() {
		this(SizeModifierSettings.SETTINGS_FILE_DEF);
	}

	public SizeModifierSettings(final String settingsFile) {
		this.settingsFile = settingsFile;
		settings = new TypedProperties();
		loadSettingsProperties();
		loadSettings();
	}

	private void loadSettings() {
		resizeFonts = settings.getProperty(RESIZE_FONTS, RESIZE_FONTS_DEF);
		resizeFontsIndex = settings.getProperty(RESIZE_FONTS_INDEX, RESIZE_FONTS_INDEX_DEF);
		rescaleImages = settings.getProperty(RESCALE_IMAGES, RESCALE_IMAGES_DEF);
		rescaleImagesIndex = settings.getProperty(RESCALE_IMAGES_INDEX, RESCALE_IMAGES_INDEX_DEF);
	}

	/**
	 * Loads the settings into the property file
	 */
	private void loadSettingsProperties() {
		try {
			settings.load(new FileInputStream(settingsFile));
		} catch (FileNotFoundException e) {
			//Don't load the settings if the file doesn't exist
			System.out.println(
				"SizeModifier: WARNING: Settings file not found: "
					+ settingsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save the settings file
	 */
	public void saveSettings() {
		saveProperties();

		try {
			settings.store(
				new FileOutputStream(new File(settingsFile)),
				"Size Modifier Settings File");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  
	 */
	private void saveProperties() {
		settings.setProperty(RESIZE_FONTS, resizeFonts);
		settings.setProperty(RESIZE_FONTS_INDEX, resizeFontsIndex);
		settings.setProperty(RESCALE_IMAGES, rescaleImages);
		settings.setProperty(RESCALE_IMAGES_INDEX, rescaleImagesIndex);
	}

	/**
	 * @return the font size.
	 */
	public String getFontSize() {
		return FONT_SIZES[resizeFontsIndex];
	}

	/**
	 * @return the image scaling factor.
	 */
	public double getImageRescaleFactor() {
		return Double.parseDouble(IMAGE_SCALING_FACTORS[rescaleImagesIndex]);
	}
}
