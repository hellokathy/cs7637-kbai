package project4;
/*
 *  Creates a TextRavensProblem from a VisualRavensProblem
 */
public class TextRavensProblemCreator {
	
	private VisualRavensProblem vrp;
	private TextRavensProblem rp = null;
	
	public TextRavensProblemCreator(VisualRavensProblem vrp) {
		this.vrp = vrp;
		this.rp = new TextRavensProblem(vrp.getName(),vrp.getProblemType());
		
	}

	public TextRavensProblem getTextRavensProblem()
	{
		
		return null;
	}
}
