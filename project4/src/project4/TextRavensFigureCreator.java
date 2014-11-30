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
		
		List<ObjectRec> objTable = sa.getObtTable();
		
		this.trf = this.buildTextRavensFigure(this.vrf.getName(), objTable);
	}
	
	public TextRavensFigure getTextRavensFigure()
	{
		return this.trf;
	}
	
	public TextRavensFigure buildTextRavensFigure(String name, List<ObjectRec> objTable)
	{
		TextRavensFigure trf = new TextRavensFigure(name);
		
		// create TextRavensObject for each object in table
		for (int i=0; i< objTable.size() ; i++)
		{
			ObjectRec objRec = objTable.get(i);
		
			TextRavensObject tro = new TextRavensObject(objRec.getObjName());
			
			// create a TextRavensAttribute for each attribute
			TextRavensAttribute traShape = new TextRavensAttribute(Const.Attr.shape.toString(), objRec.getShape());
			tro.addAttrib(traShape);
			TextRavensAttribute traSize = new TextRavensAttribute(Const.Attr.size.toString(), objRec.getSize());
			tro.addAttrib(traSize);		
			TextRavensAttribute traFill = new TextRavensAttribute(Const.Attr.fill.toString(), objRec.getFill());
			tro.addAttrib(traFill);
			TextRavensAttribute traAngle = new TextRavensAttribute(Const.Attr.angle.toString(), String.valueOf(objRec.getAngle()) );
			tro.addAttrib(traAngle);
			if (!(objRec.getAbove().isEmpty() ) )
			{
				TextRavensAttribute traAbove = new TextRavensAttribute(Const.Attr.above.toString(), objRec.getAbove() );
				tro.addAttrib(traAbove);
			}
			if (!(objRec.getBelow().isEmpty()) )
			{
				TextRavensAttribute traBelow = new TextRavensAttribute(Const.Attr.below.toString(), objRec.getBelow() );
				tro.addAttrib(traBelow);
			}
			if (!(objRec.getLeftOf().isEmpty() ) )
			{ 
				TextRavensAttribute traLeftOf = new TextRavensAttribute(Const.Attr.left_of.toString(), objRec.getLeftOf() );
				tro.addAttrib(traLeftOf);
			}
			if (!(objRec.getRightOf().isEmpty() ) )
			{
				TextRavensAttribute traRightOf = new TextRavensAttribute(Const.Attr.right_of.toString(), objRec.getRightOf() );
				tro.addAttrib(traRightOf);
			}
			if (!(objRec.getOverlaps().isEmpty() ) )
			{
				TextRavensAttribute traOverlaps = new TextRavensAttribute(Const.Attr.overlaps.toString(), objRec.getOverlaps() );
				tro.addAttrib(traOverlaps);
			}
			if (!(objRec.getInside().isEmpty() ) )
			{			
				TextRavensAttribute traInside= new TextRavensAttribute(Const.Attr.inside.toString(), objRec.getInside() );
				tro.addAttrib(traInside);
			}
			// add object to figure
			trf.addObject(tro, objRec.getObjName());

		}
		
		return trf;
		
	}
}

