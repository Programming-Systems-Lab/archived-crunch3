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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import psl.crunch3.Crunch3;
import psl.crunch3.HttpMetadata;
import psl.crunch3.HttpResponses;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ImageSizeModifier {
	private SizeModifierSettings settings = SizeModifierSettings.getInstance();
	private double imageRescaleFactor = settings.getImageRescaleFactor();
	
	public ImageData[] filter(ImageData[] imageArray) {
		for(int i=0;i<imageArray.length;i++)
			imageArray[i] = filter(imageArray[i]);
		return imageArray;
	}
	
	private ImageData filter(ImageData imagedata) {
		ImageData newimage = null;
		if(imagedata.height == 0) return imagedata;
		if(imagedata.width == 0) return imagedata;
		try {

			int sizeX = (int) (imagedata.width * imageRescaleFactor + 0.5d);
			if(sizeX == 0) sizeX = 1;
			int sizeY = (int) (imagedata.height * imageRescaleFactor + 0.5d);
			if(sizeY == 0) sizeY = 1;
			System.out.println("Scaling from ("+imagedata.x+","+imagedata.y+") to ("+sizeX+","+sizeY+")");
			newimage = imagedata.scaledTo(sizeX, sizeY);
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
		if(newimage == null)
			return imagedata;
		else
			return newimage;
	}
	
	public File filter(File inFile, HttpMetadata metadata) throws FileNotFoundException, IOException {
		ImageLoader loader = new ImageLoader();
		
		FileInputStream fin = new FileInputStream(inFile);
		loader.data = loader.load(fin);		
		fin.close();

		filter(loader.data);
		
		int type = SWT.IMAGE_PNG;
		type = loader.data[0].type;
		
		File outFile = File.createTempFile("temp-", ".image");
		FileOutputStream fos = new FileOutputStream(outFile);
		loader.save(fos, type);
		fos.close();
		
		switch(type) {
			case SWT.IMAGE_BMP:
				metadata.set("Content-Type: image/bmp"+HttpResponses.CRLF);
				break;
	 		case SWT.IMAGE_GIF:
				metadata.set("Content-Type: image/gif"+HttpResponses.CRLF);
				break;
			case SWT.IMAGE_ICO:
				metadata.set("Content-Type: image/ico"+HttpResponses.CRLF);
				break;
			case SWT.IMAGE_JPEG:
				metadata.set("Content-Type: image/jpeg"+HttpResponses.CRLF);
				break;
			case SWT.IMAGE_PNG:
				metadata.set("Content-Type: image/png"+HttpResponses.CRLF);
				break;
		}
		return outFile;
	}
}
