package project4;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.opencv.core.Core; 
import org.opencv.core.CvType; 
import org.opencv.core.Mat; 
import org.opencv.core.MatOfFloat; 
import org.opencv.core.MatOfInt; 
import org.opencv.core.MatOfInt4; 
import org.opencv.core.MatOfPoint; 
import org.opencv.core.MatOfPoint2f; 
import org.opencv.core.Point; 
import org.opencv.core.Rect; 
import org.opencv.core.Scalar; 
import org.opencv.highgui.Highgui; 
import org.opencv.imgproc.Imgproc;

import org.junit.Test;

public class testShapeDetectorJcv {

	@Test
	public void testShapDetector_1()
	{
		
		ShapeDetector sd = new ShapeDetector();
		
		Mat source = Highgui.imread("testimages/4.png");
		
		//sd.findObjects(source);
		
		assertTrue(true);
		
	
	}

}
