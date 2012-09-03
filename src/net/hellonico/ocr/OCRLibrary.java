/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package net.hellonico.ocr;

import static java.lang.System.out;

import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.sourceforge.javaocr.gui.meanSquareOCR.TrainingImageSpec;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader;
import processing.core.PApplet;

/**
 * This is a template class and can be used to start a new processing library or
 * tool. Make sure you rename this class as well as the name of the example
 * package 'template' to your own library or tool naming convention.
 * 
 * @example Hello
 * 
 *          (the tag @example followed by the name of an example included in
 *          folder 'examples' will automatically include the example in the
 *          javadoc.)
 * 
 */

public class OCRLibrary {

	PApplet myParent;
	public final static String VERSION = "##library.prettyVersion##";
	private OCRScanner ocr;

	public OCRLibrary(PApplet theParent) {
		myParent = theParent;
		init();
	}
	
	private HashMap<Character, ArrayList<TrainingImage>> getTrainingImageHashMap(
			ArrayList<TrainingImageSpec> imgs) {
		TrainingImageLoader loader = new TrainingImageLoader();
		HashMap<Character, ArrayList<TrainingImage>> trainingImages = new HashMap<Character, ArrayList<TrainingImage>>();
		Frame frame = new Frame();
		
		for (int i = 0; i < imgs.size(); i++) {
			try {
				String loc = imgs.get(i).getFileLocation();
				Image img = ImageIO.read(new URL(loc));
			loader.load(frame, img , imgs.get(i)
					.getCharRange(), trainingImages, loc);
			} catch(Exception e) {
				out.println("Could not process:"+imgs.get(i)+" ["+e.getMessage()+"]");
			}
		}

		return trainingImages;
	}
	
	public void init(String pathToImage) {
		ocr = new OCRScanner();
		ArrayList<TrainingImageSpec> imgs = new ArrayList<TrainingImageSpec>();
		TrainingImageSpec trainImage = new TrainingImageSpec();
		String loc = String.class
				.getResource(pathToImage)
				.toString();
		//System.out.println(loc);
		trainImage.setFileLocation(loc);
		// http://en.wikipedia.org/wiki/ASCII
		trainImage.setCharRange(new CharacterRange(32, 126));
		imgs.add(trainImage);
		HashMap<Character, ArrayList<TrainingImage>> trainingImages = getTrainingImageHashMap(imgs);
		ocr.addTrainingImages(trainingImages);
	}
	
	public void init() {
		init("/data/samples/TrainingImages/hpljPica.jpg");
	}
	
	public String scan(URL img) {
		try {
			Image bi = ImageIO.read(img);
			return ocr.scan(bi, 0, 0, 0, 0, null);
		} catch (Exception e) {
			out.print(e);
			return "";
		}
	}
	public String scan(String file) {
		try {
		Image bi = ImageIO.read(new File(file));
		return ocr.scan(bi, 0, 0, 0, 0, null);
		} catch (Exception e) {
			out.print(e);
			return "";
		} 
	}
	public String scanResource(String rsc) {
		URL resource = this.getClass().getResource(rsc);
		return scan(resource);
	}

	public static String version() {
		return VERSION;
	}

	public static void main(String[] args) throws Exception {
		OCRLibrary ocr = new OCRLibrary(null);
		//out.println("->"+ocr.scanResource("/samples/asciiSentence.png"));
		out.println("->"+ocr.scanResource("/samples/hpljPicaSample.jpg"));
	}

}
