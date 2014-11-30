package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;




/*
 *  takes an OcvImageProcessor and returns an array of TextRavensObjects
 */
public class ShapeAnalyser {
	
	OcvImageProcessor ocvImgProc = null;
	private ArrayList<TextRavensObject> trObjects = null;
	List<MatOfPoint> contoursCopy = null;
	List<MatOfPoint2f> objectsCopy = null;
	List<ObjectRec> objTable = null; // temporary table for collating data

	public ShapeAnalyser(OcvImageProcessor ocvImgProc) {
		this.ocvImgProc = ocvImgProc;
		this.trObjects = new ArrayList<TextRavensObject>();
		
		extractData();
	}

	private void extractData()
	{
		
		contoursCopy = new ArrayList<MatOfPoint>();
		contoursCopy.addAll(this.ocvImgProc.getContours());
		
		objectsCopy = new ArrayList<MatOfPoint2f>();
		objectsCopy.addAll(this.ocvImgProc.getRawObjects());
		
		// first remove first contour rawObject from the set - this 
		// is the image file boundary
		contoursCopy.remove(0);
		objectsCopy.remove(0);

		objTable = new ArrayList<ObjectRec>();
		
		for (MatOfPoint contour : contoursCopy)
		{
			ObjectRec objRec = new ObjectRec();
			objTable.add(objRec);
		}
		
		// next check for an inner edge contour - for each contour at index 1,
		// if an inner edge is found at index i+1
		// we assume that contour i has no fill otherwise it has fill = yes
		// (very primitive fill detection - needs to be improved later on )
		
		List<Point> outerAsPoints = null;
		List<Point> innerAsPoints = null;
		List<Point> objectAsPoints = null;
		
		for (int i=0; i<contoursCopy.size() ; i++)
		{	
			outerAsPoints = new ArrayList<Point>();
			
			Converters.Mat_to_vector_Point(contoursCopy.get(i), outerAsPoints);
						

			// get mid pt on upper surface of this shape
			Point outerMidPtUpperSurf = ocvImgProc.computeMidPointOfUpperSurf(contoursCopy.get(i));
			
			System.out.println("contour "+i+" midPtUppSurf: "+outerMidPtUpperSurf.toString());
			
			if (objTable.get(i).isKeep())
			{
				if (i<contoursCopy.size()-1)
				{
					innerAsPoints = new ArrayList<Point>();
					
					Converters.Mat_to_vector_Point(contoursCopy.get(i+1), innerAsPoints);
					// get first point of this shape
					Point innerMidPtUpperSurf = this.ocvImgProc.computeMidPointOfUpperSurf(contoursCopy.get(i+1));
					
					System.out.println("contour "+(i+1)+" midPtUppSurf: "+innerMidPtUpperSurf.toString());

					if (this.ocvImgProc.hasCloseProximity(outerMidPtUpperSurf,innerMidPtUpperSurf, i, i+1))
					{
						// check whether there is a fill between them
						if (this.ocvImgProc.isFilledBetween(outerMidPtUpperSurf, innerMidPtUpperSurf))
						{
							// mark inner edge for removal
							objTable.get(i+1).setKeep(false);
						}
						objTable.get(i).setFill(Const.Fill.no.toString());
					}
					else
					{
						// check pixel color values of a slightly contracted contour based on this one
						// to determine whether there is a fill or not (for nested figures with a mix of 
						// fill and no fill)
						System.out.println("set yes chkpt 1");				
						Point testForFillPt = new Point(outerMidPtUpperSurf.x, outerMidPtUpperSurf.y + 4.0);
												
						if (this.ocvImgProc.isFilledBetween(outerMidPtUpperSurf, testForFillPt))
						{
							objTable.get(i).setFill(Const.Fill.yes.toString());	
						}
						else
						{
							objTable.get(i).setFill(Const.Fill.no.toString());	
						}
						
					}
				}
				else
				{
					// this is the last legitimate contour
					System.out.println("last contour is "+i);
					System.out.println("set yes chkpt 2");

					objTable.get(i).setFill(Const.Fill.yes.toString());
					//System.out.println("test");
				}

			}
			
	    	//System.out.println("Contour "+i +"\n"+contoursCopy.get(i).dump()+"\n");
	    	//System.out.println("raw objects "+i +"\n"+rawObjectsCopy.get(i).dump()+"\n");
		}
		
		// before removing unwanted contours
		System.out.println("before removing unwanted contours");
		this.dumpObjTable();
		System.out.println("\n");
		
		// create temporary lists to store elements we need to keep
		List<MatOfPoint> contoursToKeep = new ArrayList<MatOfPoint>();
		List<MatOfPoint2f> objectsToKeep = new ArrayList<MatOfPoint2f>();
		
		// iterate through objTable, if isKeep = false 
		// remove contours as well as objects at index i
		for (int i=0; i<objTable.size() ; i++)
		{	
			if(objTable.get(i).isKeep())
			{
				contoursToKeep.add(contoursCopy.get(i));
				objectsToKeep.add(objectsCopy.get(i));
			}
		}
		
		this.contoursCopy = contoursToKeep;
		this.objectsCopy = objectsToKeep;
				
		// now remove unwanted objects from objTable itself
		ListIterator<ObjectRec> iter = objTable.listIterator();
		while (iter.hasNext())
		{
			if(!iter.next().isKeep()) iter.remove();
		}
		
		String shape;
		boolean closed = true;
		List<MatOfPoint2f> contoursCopy2f = null;
		
		objectAsPoints = new ArrayList<Point>();
		
		// identify shape, area and spatial relationships
		for (int i=0; i<objTable.size() ; i++)
		{	
			contoursCopy2f = this.convertToMatOfPoint2f(contoursCopy);
		
			Converters.Mat_to_vector_Point(objectsCopy.get(i), objectAsPoints);
			Converters.Mat_to_vector_Point(contoursCopy.get(i), outerAsPoints); // reusing outerAsPoints ptr here
			

			ObjectRec objRec = objTable.get(i);

			// build list of objects excluding this one - used to derive spatial relationships
			List<MatOfPoint2f> otherObjects = new ArrayList<MatOfPoint2f>();
			otherObjects.addAll(objectsCopy);
			otherObjects.remove(i);
			
			objRec.setObjAsPoints(objectAsPoints);
			objRec.setContourAsPoints(outerAsPoints);
			objRec.setContourArea(Math.abs(Imgproc.contourArea(this.contoursCopy.get(i))) );
			objRec.setObjectArea(Imgproc.contourArea(this.objectsCopy.get(i)));
			objRec.setObjectArcLength(Imgproc.arcLength(objectsCopy.get(i), closed));
			objRec.setContourArcLength(Imgproc.arcLength(contoursCopy2f.get(i), closed));
			objRec.setNumObjectVertices((int)this.objectsCopy.get(i).total());
			RotatedRect minAreaRec = Imgproc.minAreaRect(contoursCopy2f.get(i));
			Rect rect = Imgproc.boundingRect(contoursCopy.get(i));
			objRec.setBoundingRect(rect);
			objRec.setMinAreaRect(minAreaRec);
			objRec.setShape(identifyShape(i));
			objRec.setCenter(ocvImgProc.computeCenterPoint(contoursCopy.get(i)));
			objRec.setContourArea(Imgproc.contourArea(this.contoursCopy.get(i), true));
			objRec.setAngle(Util.roundToNearestMultipleOfN(computeAngleOfRotation(i),45) % 360);
			objRec.setSize(this.approximateSize(i));
			computeLowsAndHighs(objRec);
		}
		// iterate through object table again to set spatial attributes - dependent on computeLowsAndHighs(objRec);

		for (int i=0; i<objTable.size() ; i++)
		{
			setSpatialAttributes(i);

		}

		// iterate through object table again to debug print angles
		for (int i=0; i<objTable.size() ; i++)
		{
			ObjectRec objRec = this.objTable.get(i);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			int sum = 0;
			for (Point pt : objectAsPoints)
			{
				Double d = ocvImgProc.angle(objRec.getCenter(), pt);
				
				sum+=d.intValue();
				System.out.println(d.intValue());
			}
			System.out.println("---");
			System.out.println(sum);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				
		}
		System.out.println("final snapshot of object table");
		this.dumpObjTable();
		
//		TextRavensObject tro = null;
//		TextRavensAttribute tra = null;
//		tro = new TextRavensObject(String.valueOf(i));
//
//		String shape = identifyShape(contoursCopy, );
//		tra = new TextRavensAttribute()
//		
//		
//		tra = new TextRavensAttribute("fill","no");
//		tra = new TextRavensAttribute("fill","yes");
//
//		tro.getAttributes().add(tra);

	}

	public void computeLowsAndHighs(ObjectRec objRec)
	{
    	double highX = Const.NEGATIVE_INFINITY;   	
    	double lowX = Const.POSITIVE_INFINITY;
    	double highY = Const.NEGATIVE_INFINITY;
    	double lowY = Const.POSITIVE_INFINITY;
    	
    	List<Point> contourAsPts = objRec.getContourAsPoints();
    	for (Point p : contourAsPts) 
    	{ 
    		if (p.x < lowX) lowX = p.x;
    		if (p.x > highX) highX = p.x;
    		if (p.y < lowY) lowY = p.y;
    		if (p.y > highY) highY = p.y;  		
    	}   	
    	
    	objRec.setLowX(lowX);
    	objRec.setHighX(highX);
    	objRec.setLowY(lowY);
    	objRec.setHighY(highY);

	}
	
	private void setSpatialAttributes(int i)
	{ 
		 ObjectRec objRec = this.objTable.get(i);
		 
		for (int j=0 ; j<this.objTable.size() ; j++)
		{
			if ( j != i)
			{
				String otherObjName = "O"+j;
				
				// want to compare object i against all other objects in the table
				ObjectRec otherObject = this.objTable.get(j);
				
				boolean left = false; 
				boolean right = false;
				boolean below =false;
				boolean above = false;
				boolean  inside = false;
				
				System.out.println("\n objRec O"+i+"  ,otherObject O"+j);
				System.out.println("\n objRec low x:"+objRec.getLowX()+"  ,otherObject low x"+otherObject.getLowX());
				System.out.println(" objRec high x:"+objRec.getHighX()+"  ,otherObject high x"+otherObject.getHighX());
				System.out.println(" objRec low y:"+objRec.getLowY()+"  ,otherObject low y"+otherObject.getLowY());
				System.out.println(" objRec high y:"+objRec.getHighY()+"  ,otherObject high y"+otherObject.getHighY());
				
				if (objRec.getLowX() > otherObject.getLowX() && objRec.getHighX() < otherObject.getHighX() && objRec.getLowY() > otherObject.getLowY() && objRec.getHighY()< otherObject.getHighY())
				{
					objRec.setInside(otherObjName);
					inside = true;
				} 
				
				if (!inside)
				{
					if (objRec.getHighX() <= otherObject.getLowX())
					{
						objRec.setLeftOf(otherObjName);
						left = true;                                           
					} 
					else if (objRec.getLowX() >= otherObject.getHighX())
					{
						objRec.setRightOf(otherObjName);
						right = true;                                                        
					}
		
					if (objRec.getHighY() <= otherObject.getLowY())
					{
						objRec.setBelow(otherObjName);
						below = true;
					}
					else if (objRec.getLowY() >= otherObject.getHighY())
					{
						objRec.setAbove(otherObjName);
						above = true;
		
					}
		
					if ( !left && !right && !above && !below && !inside )
					{
						objRec.setOverlaps(otherObjName);
					}
				}
			}
		}

	}
	private List<Point> findConvexityDefects(int i )
	{
		// find convexity defects
		
		MatOfPoint contour = this.contoursCopy.get(i);
		MatOfInt hull = new MatOfInt();

		Imgproc.convexHull(contour, hull);
		
		List<Point> furthestPoints = new ArrayList<Point>();
		
		MatOfInt4 defects = new MatOfInt4();
		
		Imgproc.convexityDefects(contour, hull, defects);
		
		int[] indexs = defects.toArray();
		Point p = new Point();
		for (int j = 0; j * 4 < indexs.length; j++)
		{ 
			if (indexs[j*4 + 3] > this.objTable.get(i).getContourArea() / 10)
			{ 
				p = contour.toArray()[indexs[j*4+2]];
				// System.out.println("x: " + String.format("%.2f", p.x) + " y: " + String.format("%.2f", p.y)); vro.farthestNonConvexPoints.add(p);
				furthestPoints.add(p);
			}
		}			
		
		System.out.println("farthest pt: "+furthestPoints.toString());
		
		return furthestPoints;
		
	}
	private int computeAngleOfRotation(int i)
	{
		int angleOfRotation = 0;
		ObjectRec objRec = this.objTable.get(i);
		
		int tolerance = 10; // error tolerance for matching angles
		
		if ( objRec.getShape().compareTo(Const.Shape.Pac_Man.toString()) == 0 )
		{
			// should have a single convexity defect
			List<Point> cdPts = this.findConvexityDefects(i);
			System.out.println("conv defects : "+cdPts);
			if (cdPts.size() != 1 )
			{
				System.out.println("ERROR: shape incorrectly identified.");
				angleOfRotation = 0;
			}
			else
			{
				Point cdPt = cdPts.get(0);
				angleOfRotation = Double.valueOf(ocvImgProc.angle(objRec.getCenter(), cdPt)).intValue();
			}		
		}
		
		if ( objRec.getShape().compareTo(Const.Shape.square.toString()) == 0 )
		{
			int sumAngles = this.sumOfAngles(objRec);
			
			if (Math.abs( sumAngles - 721) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 896) <= tolerance ) angleOfRotation = 45;

		}

		if ( objRec.getShape().compareTo(Const.Shape.rectangle.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			int height = Double.valueOf( this.ocvImgProc.dist(objPts.get(0), objPts.get(1)) ).intValue();
			int width = Double.valueOf( this.ocvImgProc.dist(objPts.get(1), objPts.get(2)) ).intValue();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if ( (Math.abs( sumAngles - 716) < tolerance ) && (height > width) ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 537) < tolerance) angleOfRotation = 45;
			if ( (Math.abs( sumAngles - 720) < tolerance ) && (width > height) ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 899) < tolerance ) angleOfRotation = 135;

		}

		if ( objRec.getShape().compareTo(Const.Shape.right_triangle.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 402) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 536) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 670) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 451) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 587) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 718) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 492) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 630) <= tolerance ) angleOfRotation = 315;


		}

		if ( objRec.getShape().compareTo(Const.Shape.triangle.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 449) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 584) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 357) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 492) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 630) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 405) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 540) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 674) <= tolerance ) angleOfRotation = 315;


		}

		if ( objRec.getShape().compareTo(Const.Shape.hexagon.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 1255) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 1180) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 1074) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 991) <= tolerance ) angleOfRotation = 135;
			//if (Math.abs( sumAngles - 1255) <= tolerance ) angleOfRotation = 180;
			//if (Math.abs( sumAngles - 1180) <= tolerance ) angleOfRotation = 225;
			//if (Math.abs( sumAngles - 1074) <= tolerance ) angleOfRotation = 270;
			//if (Math.abs( sumAngles - 991) <= tolerance ) angleOfRotation = 315;


		}
		
		if ( objRec.getShape().compareTo(Const.Shape.pentagon.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 987) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 851) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 1079) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 934) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 806) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 1041) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 899) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 755) <= tolerance ) angleOfRotation = 315;


		}
		
		if ( objRec.getShape().compareTo(Const.Shape.septagon.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 1168) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 1128) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 1072) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 1391) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 1348) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 1309) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 1250) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 1208) <= tolerance ) angleOfRotation = 315;


		}
		
		if ( objRec.getShape().compareTo(Const.Shape.arrow.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 1437) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 1392) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 986) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 1298) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 1257) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 1215) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 1527) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 1119) <= tolerance ) angleOfRotation = 315;


		}		

		if ( objRec.getShape().compareTo(Const.Shape.half_arrow.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 937) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 1168) <= tolerance ) angleOfRotation = 45;
			if (Math.abs( sumAngles - 666) <= tolerance ) angleOfRotation = 90;
			if (Math.abs( sumAngles - 894) <= tolerance ) angleOfRotation = 135;
			if (Math.abs( sumAngles - 752) <= tolerance ) angleOfRotation = 180;
			if (Math.abs( sumAngles - 620) <= tolerance ) angleOfRotation = 225;
			if (Math.abs( sumAngles - 843) <= tolerance ) angleOfRotation = 270;
			if (Math.abs( sumAngles - 1074) <= tolerance ) angleOfRotation = 315;


		}
		if ( objRec.getShape().compareTo(Const.Shape.plus.toString()) == 0 )
		{
			
			List<Point> objPts = objRec.getObjAsPoints();
			
			int sumAngles = this.sumOfAngles(objRec);
						
			if (Math.abs( sumAngles - 2158) <= tolerance ) angleOfRotation = 0;
			if (Math.abs( sumAngles - 2324) <= tolerance ) angleOfRotation = 45;


		}		
		return angleOfRotation;
	}
	
	private int sumOfAngles(ObjectRec objRec)
	{
		List<Point> pts = objRec.getObjAsPoints();
		int sum = 0;
		for (Point p : pts)
		{
			sum += Double.valueOf(ocvImgProc.angle(objRec.getCenter(), p)).intValue();
		}
		return sum;
	}
	
	private String approximateSize(int i)
	{
		ObjectRec objRec = this.objTable.get(i);
		
		int area = Double.valueOf(Math.abs(objRec.getBoundingRect().area())).intValue();
		
		if (area <= 4200 )
		{
			return Const.Size.very_small.toString();
		} 
		else if (area <= 8000)
		{
			return Const.Size.small.toString();
		}
		else if (area <= 13800)
		{
			return Const.Size.medium.toString();
		}
		else if (area <= 21000) 	
		{
			return Const.Size.large.toString();
		}
		else
		{
			return Const.Size.very_large.toString();
		}
		
		
	}
	
	private String identifyShape(int i)
	{
		MatOfPoint2f object = this.objectsCopy.get(i);
		int numVertices = (int)object.total();
		MatOfPoint contour = this.contoursCopy.get(i);
		ObjectRec objRec = this.objTable.get(i);
		
		List<Point> objPts = objRec.getObjAsPoints();

		double iQ = objRec.getIsoperimetricQuotient();
		
		if (iQ >= 0.985)
		{
			return Const.Shape.circle.toString();
		}
		else if (iQ >= 0.75 && iQ <= 0.97)
		{
			switch (numVertices)
			{
				case  9 :
					return Const.Shape.nonogon.toString();
				case 8 :
					return Const.Shape.octagon.toString();
				case 7 :
					return Const.Shape.septagon.toString();
				case 6 :
					return Const.Shape.hexagon.toString();
				case 5 :
					return Const.Shape.pentagon.toString();
				case 4 :
					// check for square vs rectangle;
					int height = Double.valueOf( this.ocvImgProc.dist(objPts.get(0), objPts.get(1)) ).intValue();
					int width = Double.valueOf( this.ocvImgProc.dist(objPts.get(1), objPts.get(2)) ).intValue();
					
					int errTolerance = 3; // allowed diff between height and width
					
					System.out.println("rec angle :"+objRec.getMinAreaRect().angle);
					System.out.println("height : "+ height);
					System.out.println("width : "+ width);
					
					if (Math.abs(height - width) <= errTolerance)
					{
						return Const.Shape.square.toString();
					}
					else
					{
						
						return Const.Shape.rectangle.toString();
					}
				default :
					
					return Const.Shape.polygon.toString();
			}
		}
		
		if (iQ > 0.65 && numVertices > 5)
		{
			return Const.Shape.Pac_Man.toString();
		}
		
		switch (numVertices)
		{
			case 12 :
				return Const.Shape.plus.toString();
			case 7 :
				return Const.Shape.arrow.toString();
			case 5 :
				return Const.Shape.half_arrow.toString();
			case 4 :
				return Const.Shape.quadrilateral.toString();
			case 3 :
				
				return this.identifyTriangleType(objPts);
		}

		return Const.Shape.polygon.toString();
		
		
		
	}
	private String identifyTriangleType(List<Point> pts)
	{
		double side1, side2, side3 = 0.0;
		double errTol = 150.0;

		if (pts.size() == 3)
		{
			// find which side is longest
			side1 = ocvImgProc.dist(pts.get(0), pts.get(1));
			side2 = ocvImgProc.dist(pts.get(1), pts.get(2));
			side3 = ocvImgProc.dist(pts.get(2), pts.get(0));
			
			System.out.println("side1 "+side1+" side2 "+side2+" side3 "+side3);
			
			if ( ( (side1 > side2 && side1 > side3 && Math.abs( side1*side1 - (side2*side2 + side3*side3) ) <= errTol )  || 
				 (  side2 > side3 && side2 > side1 && Math.abs( side2*side2 - (side1*side1 + side3*side3) ) <= errTol )  ||
				 (  side3 > side2 && side3 > side1 && Math.abs( side3*side3 - (side1*side1 + side2*side2) ) <= errTol ) ))
			{
	            return Const.Shape.right_triangle.toString();
	        }
	        else
	        {
	        	return Const.Shape.triangle.toString();
	        }
		}
		else
		{
			System.out.println("ERROR: shape incorrectly identified.");
			return Const.Shape.polygon.toString();
		}
	}
	
	public void dumpObjTable()
	{
		for(int i=0; i<this.objTable.size();i++)
		{
			ObjectRec objRec = objTable.get(i);
			System.out.println("index "+i+" "+objRec.toString());
		}
	}
	
	public List<MatOfPoint2f> convertToMatOfPoint2f (List<MatOfPoint> contours)
	{
		List<MatOfPoint2f> contoursCopy2f = new ArrayList<MatOfPoint2f>();
		
		for(int i=0; i < contours.size(); i++){
		    //Convert contours(i) from MatOfPoint to MatOfPoint2f
			MatOfPoint2f mMOP2f1 = new MatOfPoint2f();
		    contours.get(i).convertTo(mMOP2f1, CvType.CV_32FC2);
		    contoursCopy2f.add(mMOP2f1);
		}
		return contoursCopy2f;	
		
	}



}
