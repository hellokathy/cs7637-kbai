package project4;

import java.util.List;

import org.opencv.core.Point;
import org.opencv.core.Rect;
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
	private double angleOfRect = 0.0;
	private int angle = 0;
	private String size = "";
	private double contourArea = 0.0;
	private double objectArea = 0.0;
	private double objectArcLength = 0.0;
	private double contourArcLength = 0.0;
	private double isopQuotient = 0.0;
	private int numObjectVertices = 0; 
	private int numContourVertices = 0;
	private RotatedRect minAreaRect = null;
	private Rect boundingRect = new Rect();
	private String rightOf = "";
	private String leftOf = "";
	private String above = "";
	private String below = "";
	private String overlaps = "";
	private String inside = "";
	private Point center = null;
	private List<Point> objAsPoints = null;
	private List<Point> contourAsPoints = null;
	private double highX = Const.NEGATIVE_INFINITY;   	
	private double lowX = Const.POSITIVE_INFINITY;
	private double highY = Const.NEGATIVE_INFINITY;
	private double lowY = Const.POSITIVE_INFINITY;

	@Override
	public String toString()
	{
		return (" shape: "+shape+" ,keep:"+keep+" ,numObjVertices: "+numObjectVertices+" ,fill: "+fill+" ,angle: "+angle+" ,size: "+size+" ,left_of: "+leftOf+" ,right_of: "+rightOf+" ,above: "+above+" ,below: "+below+" ,inside: "+inside+" , overlaps: "+overlaps+" ,contourArea: "+contourArea+" ,objectArea: "+objectArea+" ,bRectArea:"+String.valueOf(boundingRect.area())+" ,objectArcLength: "+objectArcLength+" ,isopQ: "+isopQuotient);

	}
	public ObjectRec() 
	{
	}
	
	public double getHighX() {
		return highX;
	}

	public void setHighX(double highX) {
		this.highX = highX;
	}

	public double getLowX() {
		return lowX;
	}

	public void setLowX(double lowX) {
		this.lowX = lowX;
	}

	public double getHighY() {
		return highY;
	}

	public void setHighY(double highY) {
		this.highY = highY;
	}

	public double getLowY() {
		return lowY;
	}

	public void setLowY(double lowY) {
		this.lowY = lowY;
	}
	
	public List<Point> getContourAsPoints()
	{
		return this.objAsPoints;
	}

	public void setContourAsPoints(List<Point> contourjAsPoints)
	{
		this.contourAsPoints = contourAsPoints;
	}
	
	public List<Point> getObjAsPoints()
	{
		return this.objAsPoints;
	}

	public void setObjAsPoints(List<Point> objAsPoints)
	{
		this.objAsPoints = objAsPoints;
	}
	

	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}

	public double getAngleOfRect() {
		return this.angleOfRect;
	}
	
	public RotatedRect getMinAreaRect() {
		return minAreaRect;
	}
	public void setMinAreaRect(RotatedRect minAreaRect) {
		this.minAreaRect = minAreaRect;
		this.angleOfRect = this.minAreaRect.angle;

	}
	
	public Rect getBoundingRect() {
		return boundingRect;
	}
	
	public void setBoundingRect(Rect rect) {
		this.boundingRect = rect;

	}
	
	public double getAngle() {

		return this.angle;
	}
	
	public void setAngle(int angle) {

		this.angle = angle;
	}
	
	public String getLeftOf() {
		return leftOf;
	}
	
	public void setLeftOf(String leftOf) {
		if (this.leftOf.length() > 0)
		{
			this.leftOf = this.leftOf + "," + leftOf;
		}
		else
		{
			this.leftOf = leftOf;
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

	public double getMinAreaRectArea() {
		return 0.0;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRightOf() {
		return rightOf;
	}

	public void setRightOf(String rightOf) {
		if (this.rightOf.length() > 0)
		{
			this.rightOf = this.rightOf + "," + rightOf;
		}
		else
		{
			this.rightOf = rightOf;
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

	public void setInside(String inside) {
		if (this.inside.length() > 0)
		{
			this.inside = this.inside + "," + inside;
		}
		else
		{
			this.inside = inside;
		}
			
	}

	public void setOverlaps(String overlaps) {
		if (this.overlaps.length() > 0)
		{
			this.overlaps = this.overlaps + "," + overlaps;
		}
		else
		{
			this.overlaps = overlaps;
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
		//The "compactness" or "isoperimetric quotient" IQ is a shape metric, equal
		//to  (4*pi*A)/(L*L).
		//     http://en.wikipedia.org/wiki/Isoperimetric_quotient
		//Circles are the most compact shape, with  IQ of 1. Everything else has a lower compactness.		
		
		return (4* Math.PI * area)/(length*length);

	}
	

    
 
}

