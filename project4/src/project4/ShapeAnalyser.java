package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
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
		
		for (int i=0; i<contoursCopy.size() ; i++)
		{	
			outerAsPoints = new ArrayList<Point>();
			
			Converters.Mat_to_vector_Point(contoursCopy.get(i), outerAsPoints);
						
			System.out.println(outerAsPoints);

			// get mid pt on upper surface of this shape
			Point outerMidPtUpperSurf = ocvImgProc.computeMidPointOfUpperSurf(outerAsPoints);
			
			System.out.println("contour "+i+" midPtUppSurf: "+outerMidPtUpperSurf.toString());
			
			if (objTable.get(i).isKeep())
			{
				if (i<contoursCopy.size()-1)
				{
					innerAsPoints = new ArrayList<Point>();
					
					Converters.Mat_to_vector_Point(contoursCopy.get(i+1), innerAsPoints);
					// get first point of this shape
					Point innerMidPtUpperSurf = this.ocvImgProc.computeMidPointOfUpperSurf(innerAsPoints);
					
					if (this.ocvImgProc.hasCloseProximity(outerMidPtUpperSurf,innerMidPtUpperSurf, i, i+1))
					{
						// check whether there is a fill between them
						if (this.ocvImgProc.isFilledBetween(outerMidPtUpperSurf, innerMidPtUpperSurf, i, i+1))
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
						Point testForFillPt = new Point(outerMidPtUpperSurf.x, outerMidPtUpperSurf.y + 4);
												
						if (this.ocvImgProc.isFilledBetween(outerMidPtUpperSurf, testForFillPt, i, i*100))
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

		// identify shape, area and spatial relationships
		for (int i=0; i<objTable.size() ; i++)
		{	
			contoursCopy2f = this.convertToMatOfPoint2f(contoursCopy);

			ObjectRec objRec = objTable.get(i);
			objRec.setContourArea(Imgproc.contourArea(this.contoursCopy.get(i)));
			objRec.setObjectArea(Imgproc.contourArea(this.objectsCopy.get(i)));
			objRec.setObjectArcLength(Imgproc.arcLength(objectsCopy.get(i), closed));
			objRec.setContourArcLength(Imgproc.arcLength(contoursCopy2f.get(i), closed));
			objRec.setNumObjectVertices((int)this.objectsCopy.get(i).total());
			RotatedRect minAreaRec = Imgproc.minAreaRect(contoursCopy2f.get(i));
			objRec.setMinAreaRect(minAreaRec);
			objRec.setShape(identifyShape(i));
			//shape = identifyShape(i);
		}
		
		System.out.println("after removing unwanted contours");
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
	
	private String identifyShape(int i)
	{
		MatOfPoint2f object = this.objectsCopy.get(i);
		int numVertices = (int)object.total();
		MatOfPoint contour = this.contoursCopy.get(i);
		ObjectRec objRec = this.objTable.get(i);
		
		double iQ = objRec.getIsoperimetricQuotient();
		
		if (iQ >= 0.985)
		{
			return "circle";
		}
		else if (iQ >= 0.75 && iQ <= 0.97)
		{
			switch (numVertices)
			{
				case  9 :
					return "nonogon";
				case 8 :
					return "ocotogon";
				case 7 :
					return "septagon";
				case 6 :
					return "hexagon";
				case 5 :
					return "pentagon";
				case 4 :
					return "square";
				default :
					return "unknown_polygon";
			}
		}
		
		if (iQ > 0.65 && numVertices > 5)
		{
			return "Pac-man";
		}
		
		switch (numVertices)
		{
			case 12 :
				return "plus";
			case 7 :
				return "arrow";
			case 5 :
				return "half_arrow";
			case 4 :
				return "rectangle";
			case 3 :
				return "triangle";
		}

		return "unknown_polygon";
		
		
		
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
