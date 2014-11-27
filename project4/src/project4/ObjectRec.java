package project4;

import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

/*
 * Temporary record for storing fill values and which contours will be marked for 
 * deletion while iterating through list of contours
 */
public class ObjectRec
{
	private boolean keep = true;
	private String fill = "";
	private String shape = "";
	private double angle = 0.0;
	private String size = "";
	private String right_of = "";
	private String above = "";
	private double contourArea = 0.0;
	private double objectArea = 0.0;
	private double objectArcLength = 0.0;
	private double contourArcLength = 0.0;
	private double isopQuotient = 0.0;
	private int numObjectVertices = 0; 
	private int numContourVertices = 0;
	private RotatedRect minAreaRect = null;
	private String left_of = "";
	private String below = "";
	
	@Override
	public String toString()
	{
		return (" shape: "+shape+" ,keep:"+keep+" ,numObjVertices: "+numObjectVertices+" ,fill: "+fill+" ,angle: "+angle+" ,size: "+size+" ,left_of: "+left_of+" ,right_of: "+right_of+" ,above: "+above+" ,below: "+below+" ,contourArea: "+contourArea+" ,objectArea: "+objectArea+" ,objectArcLength: "+objectArcLength+" ,isopQ: "+isopQuotient);

	}
	public ObjectRec() 
	{
	}
		
	public RotatedRect getMinAreaRect() {
		return minAreaRect;
	}
	public void setMinAreaRect(RotatedRect minAreaRect) {
		this.minAreaRect = minAreaRect;
		this.angle = this.minAreaRect.angle;

	}
	public String getLeft_of() {
		return left_of;
	}
	public void setLeft_of(String left_of) {
		if (this.left_of.length() > 0)
		{
			this.left_of = this.left_of + "," + left_of;
		}
		else
		{
			this.left_of = left_of;
		}
	}
	public String getBelow() {
		return below;
	}
	public void setBelow(String below) {
		if (this.below.length() > 0)
		{
			this.below = this.below + "," + below;
		}
		else
		{
			this.below = below;
		}
	}
	public int getNumObjectVertices() {
		return numObjectVertices;
	}
	public void setNumObjectVertices(int numObjectVertices) {
		this.numObjectVertices = numObjectVertices;
	}
	public int getNumContourVertices() {
		return numContourVertices;
	}
	public void setNumContourVertices(int numContourVertices) {
		this.numContourVertices = numContourVertices;
	}
	
	private void setIsoperimetricQuotient()
	{
		this.isopQuotient = this.isoperimetricQuotient(this.contourArea, this.objectArcLength);
	}
	
	public double getIsoperimetricQuotient() {
		
		return isopQuotient;
	}
	
	public double getObjectArcLength() {
		return objectArcLength;
	}

	public void setObjectArcLength(double objectArcLength) {
		this.objectArcLength = objectArcLength;
		if (this.objectArcLength>0.0 && this.contourArea > 0.0) this.setIsoperimetricQuotient();

	}

	public double getContourArcLength() {
		return contourArcLength;
	}

	public void setContourArcLength(double contourtArcLength) {
		this.contourArcLength = contourArcLength;

	}
	
	public double getContourArea() {
		return contourArea;
	}

	public void setContourArea(double contourArea) {
		this.contourArea = contourArea;
		if (this.objectArcLength>0.0 && this.contourArea > 0.0) this.setIsoperimetricQuotient();

	}

	public double getObjectArea() {
		return objectArea;
	}

	public void setObjectArea(double objectArea) {
		this.objectArea = objectArea;

	}
	
	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public double getAngle() {

		return this.angle;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRight_of() {
		return right_of;
	}

	public void setRight_of(String right_of) {
		if (this.right_of.length() > 0)
		{
			this.right_of = this.right_of + "," + right_of;
		}
		else
		{
			this.right_of = right_of;
		}
	}

	public String getAbove() {
		return above;
	}

	public void setAbove(String above) {
		if (this.above.length() > 0)
		{
			this.above = this.above + "," + above;
		}
		else
		{
			this.above = above;
		}
			
	}
	
	public boolean isKeep() {
		return keep;
	}

	public void setKeep(boolean keep) {
		this.keep = keep;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	private double isoperimetricQuotient(double area, double length)
	{
		//The "compactness" or "isoperimetric quotient" Q is a shape metric, equal
		//to  (4*pi*A)/(L*L).
		//     http://en.wikipedia.org/wiki/Isoperimetric_quotient
		//Circles are the most compact shape, with a Q of 1. Everything else has a lower compactness.		
		
		return (4* Math.PI * area)/(length*length);

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
    
 
}

