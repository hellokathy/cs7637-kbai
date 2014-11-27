package project4;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class TextRavensFigureCreator {

	private TextRavensFigure trf = null;
	private VisualRavensFigure vrf = null;
	private OcvImageProcessor ocvImgProc = null;
	
	public TextRavensFigureCreator(VisualRavensFigure vrf)
	{
		this.vrf = vrf;
		this.trf = new TextRavensFigure(vrf.getName());
		createTextRavensFigure(vrf.getPath());
	}

	public TextRavensFigureCreator(String imagePath)
	{
		createTextRavensFigure(imagePath);
	}
	
	private void createTextRavensFigure(String imagePath)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		this.ocvImgProc = new OcvImageProcessor(imagePath);
		this.ocvImgProc.findRawObjects();
		
		// for each vertex
		// 		identify shape, fill, size, angle and spatial relationships 
		

		
		ShapeAnalyser sa = new ShapeAnalyser(ocvImgProc);
		//this.trf.getObjects().addAll(sa.getTextRavensObjects);
		
	}
	
	public TextRavensFigure getTextRavensFigure()
	{
		return this.trf;
	}
	

}

