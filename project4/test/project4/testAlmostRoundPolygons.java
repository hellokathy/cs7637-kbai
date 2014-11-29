package project4;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;

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

public class testAlmostRoundPolygons {

	@Test
	public void testTextRavensProblemCreator_1() {
		
		TextRavensFigureCreator trfCreator = null;
		TextRavensFigure trf = null;
		
		// redirect all output to System.out to logfile instead
		try {
			System.setOut(new PrintStream("test.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			System.out.println("------hexagon---------------------------");

			for (int x=0 ; x<=315 ; x+=45)
			{
				System.out.println("------angle "+x+"---------------------------");		

			
				trfCreator = new TextRavensFigureCreator("testimages/hexagon/ang"+x+".png");
				trf = trfCreator.getTextRavensFigure();
			}
		
		assertTrue(true);
	}

}
