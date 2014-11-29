package project4;

import static org.junit.Assert.*;

import org.junit.Test;
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

public class testTextRavensFigureCreator {

	@Test
	public void testTextRavensProblemCreator_1() {
		
		TextRavensFigureCreator trfCreator = null;
		TextRavensFigure trf = null;
		
		for (int x=0 ; x<=315 ; x+=45)
		{
			System.out.println("------angle "+x+"---------------------------");
		
			trfCreator = new TextRavensFigureCreator("testimages/rtri/ang"+x+".png");
			trf = trfCreator.getTextRavensFigure();
		}

		
		assertTrue(true);
	}

}
