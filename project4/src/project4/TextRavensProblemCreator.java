package project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



/*
 *  Creates a TextRavensProblem from a VisualRavensProblem
 */
public class TextRavensProblemCreator {
	
	private VisualRavensProblem vrp;
	private TextRavensProblem trp = null;
	
	public TextRavensProblemCreator(VisualRavensProblem vrp) {
		this.vrp = vrp;
		createTextRavensProblem();
	}
	
	private void createTextRavensProblem()
	{
        
        ArrayList<TextRavensFigure> figures=new ArrayList<>();
        TextRavensFigure currentFigure=null;
        TextRavensObject currentObject=null;
	
        this.trp = new TextRavensProblem(vrp.getName(),vrp.getProblemType());

        for (String figureLabel : this.vrp.getFigures().keySet())
        {
        	
        	TextRavensFigureCreator trfCreator = new TextRavensFigureCreator(this.vrp.getFigures().get(figureLabel));
        	TextRavensFigure trf = trfCreator.getTextRavensFigure();
        	
            this.trp.getFigures().put(figureLabel,trf);
    
        }
	}
	
	public TextRavensProblem getTextRavensProblem()
	{
		return this.trp;
	}
	
}

