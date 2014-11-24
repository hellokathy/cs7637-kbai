package project4;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

public class ShapeDetector {


	
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat source = Highgui.imread("testimages/4.png");
		
		Mat grayImage = new Mat();
		Mat bwImage = new Mat();
		
		// hierarchy - Optional output vector, containing information about the image topology. 
		//It has as many elements as the number of contours. For each i-th contour contours[i], 
		//the elements hierarchy[i][0], hiearchy[i][1], hiearchy[i][2], and hiearchy[i][3] are set to 
		//0-based indices in contours of the next and previous contours at the same hierarchical level, 
		//the first child contour and the parent contour, respectively. 
		//If for the contour i there are no next, previous, parent, or nested contours, 
		//the corresponding elements of hierarchy[i] will be negative.
		Mat hierarchy = new Mat();

        // Let's find some contours! but first some thresholding...
		Imgproc.cvtColor(source, grayImage, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.threshold(grayImage, bwImage, 127, 255, Imgproc.THRESH_BINARY);
       
        
       // cvFindContours(grayImage, storage, contour, Loader.sizeof(CvContour.class),CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);
       List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
       
       Imgproc.findContours(bwImage, contours, hierarchy, Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE);
       int i = 0;
       
       MatOfPoint2f vertices = new MatOfPoint2f();
       for (MatOfPoint contour : contours)
       {
    	   MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
    	   System.out.println("Contour "+i +"\n"+contour.dump()+"\n");
    	   Imgproc.approxPolyDP(contour2f, vertices, 4, true);
    	   System.out.println("Vertices "+i +"\n"+vertices.dump()+"\n");
    	   System.out.println("Hierarchy "+i++ +"\n"+hierarchy.dump()+"\n\n");

       }
        
	}

}
