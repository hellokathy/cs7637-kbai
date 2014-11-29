package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.utils.Converters;

public class OcvImageProcessor {
	
	public Mat source = null;
	public Mat grayImage = null;
	public Mat bwImage = null;
	public Mat hierarchy = null;
	
	// hierarchy - Optional output vector, containing information about the image topology. 
	//It has as many elements as the number of contours. For each i-th contour contours[i], 
	//the elements hierarchy[i][0], hiearchy[i][1], hiearchy[i][2], and hiearchy[i][3] are set to 
	//0-based indices in contours of the next and previous contours at the same hierarchical level, 
	//the first child contour and the parent contour, respectively. 
	//If for the contour i there are no next, previous, parent, or nested contours, 
	//the corresponding elements of hierarchy[i] will be negative.
	
	List<MatOfPoint> contours = null;
	List<MatOfPoint2f> rawObjects = null;

	boolean foundContours = false;
	boolean foundRawObjects = false;
	
	public OcvImageProcessor(String imagePath) 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // this is required to workaround a known bug
		this.source = Highgui.imread(imagePath);
		
		grayImage = new Mat();
		bwImage = new Mat();
		hierarchy = new Mat();
		
		Imgproc.cvtColor(source, grayImage, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.threshold(grayImage, bwImage, 155, 255, Imgproc.THRESH_BINARY);
        
	}
	
 
	
	public Mat getGrayScale()
	{
        return this.grayImage;
	}

	public Mat getBwThreshold()
	{ 
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // this is required to workaround a known bug

        return this.bwImage;
	}
	
	private void findContours()
	{
		Mat copyOfBwImage = this.bwImage.clone(); // required since openCV findContours method modifies the original image
		
		this.contours = new ArrayList<MatOfPoint>();
	    Imgproc.findContours(copyOfBwImage, contours, hierarchy, Imgproc.RETR_TREE , Imgproc.CHAIN_APPROX_SIMPLE);
	    this.foundContours = true;
	}
	
	public Mat getHierarchy()
	{
		return this.hierarchy;
	}
	
	public List<MatOfPoint> getContours()
	{
		return this.contours;
	}
	
	public void findRawObjects()
	{
		if (!this.foundContours) this.findContours();
		
		this.rawObjects = new ArrayList<MatOfPoint2f>();
		
		for (MatOfPoint contour : contours)
		{
			MatOfPoint2f rawObject = new MatOfPoint2f();
	    	MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
	    	
	    	double arcLength = Imgproc.arcLength(contour2f, true);
	    	Imgproc.approxPolyDP(contour2f, rawObject, 0.025*arcLength, true);

	    	this.rawObjects.add(rawObject);
	    			
		}
		
		this.foundRawObjects = this.rawObjects.size() > 0;
		
		for (int i=0; i<rawObjects.size() ; i++)
		{
	    	//System.out.println("Contour "+i +"\n"+contours.get(i).dump()+"\n");
		}
    	//System.out.println("Hierarchy \n"+hierarchy.dump()+"\n\n");				

		
	}
	
	public List<MatOfPoint2f> getRawObjects()
	{
		return this.rawObjects;
	}
	
	public boolean hasCloseProximity (Point outer, Point inner, int o, int i)
	{
		boolean retVal = false;
		double tolerance = 9.0;
		
		Point diff = this.getDiff(outer, inner);
		
		if ( (diff.x <= tolerance) && (diff.y <= tolerance ) )
		{
				retVal = true;
		}
		
		System.out.println("diff.x "+ String.valueOf(diff.x) + " <= tolerance "+String.valueOf(tolerance));
		System.out.println("diff.y "+ String.valueOf(diff.y) + " <= tolerance "+String.valueOf(tolerance));

		System.out.println("hasCloseProximity is "+retVal+"\n");
		return retVal;

	}
	
	public boolean isFilledBetween(Point outer, Point inner)
	{
		double x = outer.x;
		double y1 = outer.y;
		double y2 = inner.y;
		
		double threshold = 0.55 ; // threshold % used to determine whether there is a fill or not 
		
		double ctr = 0;
		double countOfBlackPix = 0;
		
		//System.out.println("bwImage : "+this.bwImage.dump());

		for (double yi=y1; yi<=y2 ; yi=yi+1.0 )
		{
			ctr++;
			if ( getPixelValAtPoint(new Point(x,yi)) == 0 ) 
			{
				countOfBlackPix++;

			}
			
		}
		
		System.out.println("sum of black pix "+countOfBlackPix);
		System.out.println("total count of pix "+ctr);

		double perc = countOfBlackPix / ctr;
		System.out.println("perc : "+perc);
		
		boolean filled = false;
		
		if (perc >= threshold)
		{
			filled = true;
		}
		else if (perc >= 0.5)
		{
			// check middle pixel
			filled =  ( getPixelValAtPoint (new Point(x, y1+((y2-y1)/2) ) ) == 0  ) ;
		}
		else
		{
			filled = false;
		}
		
		System.out.println("fill between "+outer+" and "+inner+" = "+filled);

		return filled;
	}
	
	private Point getDiff(Point outer, Point inner)
	{
		Point diff = new Point();
		
		diff.x =  Math.abs(inner.x - outer.x); // x value
		diff.y =  Math.abs(inner.y - outer.y);  // y value
		
		return diff;
		
		
	}
	
	
	public double getPixelValAtPoint(Point p)
	{
		int xi = Double.valueOf(p.x).intValue();
		int yi = Double.valueOf(p.y).intValue();
		
		
		double retVal =  this.bwImage.get(yi, xi)[0]; // why on earth did openCV reverse the X,Y order for the get method ????!!!!!!
		
		System.out.println("pixel val at "+p.toString()+" is "+retVal);
		return retVal;

	}


    
	public static Point computeMidPointOfUpperSurf(MatOfPoint contour)
	{
		
//		double highX = Const.NEGATIVE_INFINITY;
//		double lowX = Const.POSITIVE_INFINITY;
//		double highY = Const.NEGATIVE_INFINITY;
		
		List<Point> contourAsPtsSrc = new ArrayList<Point>();
		Converters.Mat_to_vector_Point(contour, contourAsPtsSrc);
		
		System.out.println("extracted : "+contourAsPtsSrc);

		// use interpolation to increase resolution of contour
		List<Point> contourAsPts = new ArrayList<Point>();
		contourAsPts = enhanceContourRes(contourAsPtsSrc);

		System.out.println("res enh : "+ contourAsPts);
		
		//List<Point> contourAsPts = new ArrayList<Point>();
		//Converters.Mat_to_vector_Point(contour, contourAsPts);
		
		double midX = computeCenterPoint(contour).x;
		
		// aim is to find  points in the contour with x values closest to midX
		// then we take the lowest y value from this set to approximate y value 
		// at mid pt on upper surface
		
		// make copy of list
		List<Point> contourAsPtsCopy = new ArrayList<Point>();
		contourAsPtsCopy.addAll(contourAsPts);
		
		// initialize list for points with same X value as mid
		List<Point> closestPoints = new ArrayList<Point>();
		
		for (Point p : contourAsPtsCopy)
		{
			if ( Math.abs( p.x - midX ) <= 1.0  ) closestPoints.add(p);
		}
		
		System.out.println("take smallest y from " +closestPoints.toString());
		double lowestY = Const.POSITIVE_INFINITY;
		for (Point p : closestPoints)
			if (p.y < lowestY) lowestY = p.y;
		
		Point targetPt = new Point(midX,lowestY);
		return targetPt;
	}
	
  
//    
//    public static Point computeCenterPoint(MatOfPoint contour) 
//    { 
//    	double highX = Const.NEGATIVE_INFINITY;
//    	
//    	double lowX = Const.POSITIVE_INFINITY;
//    	double highY = Const.NEGATIVE_INFINITY;
//    	double lowY = Const.POSITIVE_INFINITY;
//    	
//    	List<Point> contourAsPts = new ArrayList<Point>();
//    	Converters.Mat_to_vector_Point(contour, contourAsPts);
//    	
//    	for (Point p : contourAsPts) 
//    	{ 
//    		if (p.x < lowX) lowX = p.x;
//    		if (p.x > highX) highX = p.x;
//    		if (p.y < lowY) lowY = p.y;
//    		if (p.y > highY) highY = p.y;  		
//    	} 
//    	Point approxCenter = new Point(); 
//    	
//    	approxCenter.x = lowX + (highX - lowX)/2; 
//    	approxCenter.y = lowY + (highY - lowY)/2; 
//    	
//    	System.out.println("center : " +approxCenter);
//    	return approxCenter;
//    } 
    
    public static  Point computeCenterPoint(MatOfPoint contour)
    {
    	// uses hu moments to compute mass center 
    	Moments m = new Moments();

    	m = Imgproc.moments(contour, false);
        
        int x = (int) (m.get_m10() / m.get_m00());
        int y = (int) (m.get_m01() / m.get_m00());
        
        return new Point(x, y);
    }
    
    public static double angle(Point center, Point p) {
        double angle = Math.atan((center.y - p.y) / (p.x - center.x));
        if (angle < 0) {
            angle += Math.PI;
        }
        if (Math.abs(center.y - p.y) > Math.abs(center.x - p.x)) {
            if (center.y < p.y) {
                angle += Math.PI;
            }
        } else if ((center.x < p.x && angle > Math.PI / 2) || (center.x > p.x && angle < Math.PI / 2)) {
            angle += Math.PI;
        }
        return 360 - angle / Math.PI * 180;
    }

    static double dist(Point p1, Point p2) {
        return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }

    static double cos(Point p1, Point p2, Point p3) {
        return ((p3.x - p1.x) * (p2.x - p1.x) + (p3.y - p1.y) * (p2.y - p1.y))
                / dist(p3, p1) / dist(p2, p1);
    }
    
    private static List<Point> enhanceContourRes(List<Point> contourAsPts)
    {
    	// enhance contour resolution .. we assume its a closed polygon for now and 
    	// interpolate the line across the last and first points
    	// so we add the first point to the end of the list
    	
    	contourAsPts.add(contourAsPts.get(0));
    	
    	List<Point> newContourAsPts = new ArrayList<Point>();
    	Point newPt = null;
    	
    	for (int i=0 ; i < contourAsPts.size()-1 ; i++)
    	{
    		Point p1 = contourAsPts.get(i);
    		Point p2 = contourAsPts.get(i+1);
    		double resolution = 2.0;
    		
    		newContourAsPts.add(p1);

    		if (Math.abs(p2.x - p1.x) > resolution )
    		{
    			Point first = null;
    			Point second = null;
    			boolean ascend = true;
    			
    			if (p1.x <= p2.x)
    			{
    				first = p1;
    				second = p2;
    			}
    			else
    			{
    				first = p2;
    				second = p1;
    				ascend = false;
    			}
    			
    			List<Point> newPts = new ArrayList<Point>();
    			for (double xi=first.x ; xi<second.x ; xi+=resolution)
    			{
    				newPt = interpolate(first, second, xi);
    	    		newPts.add(newPt);
    			}
    			
    			if (!ascend) Collections.reverse(newPts);
    			
    			newContourAsPts.addAll(newPts);
    		}
    		    		

    	}
    	System.out.println(contourAsPts.size());
    	newContourAsPts.add(contourAsPts.get(contourAsPts.size()-1));
    	

    	return newContourAsPts;
    }
    
    private static Point interpolate(Point p1, Point p2, double xi) 
    {
    	 //Pre conditions
    	assert p1.x<xi;
    	assert xi<p2.x;
    	    	
    	//Calculate y position of x
    	double yi = p1.y + ( (xi - p1.x)*(p2.y - p1.y)/(p2.x - p1.x) );
    	//create new point
    	 	
    	return new Point(xi,yi);
    }
    
}
