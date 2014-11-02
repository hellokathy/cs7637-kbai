package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class RecordedCaseRPMSolver {
	
	private String testHash;
	private  HashMap<String,String> memory = null;
	private SemanticNet semanticNet = null;
	
	public RecordedCaseRPMSolver(SemanticNet semanticNet, HashMap<String,String> memory)
	{
		this.semanticNet = semanticNet;
		this.testHash = this.rpmProblemHash(semanticNet);
		this.memory = memory;
	}

	public String computeSolution()
	{
		// iterate through candidate notes
		
		ArrayList<String> possibleMatches = new ArrayList<String>();
				
		//try to find cases that are close to the testString .. iterate through memory
		for (String possibleKeyMatch : memory.keySet())
		{
			DamerauLevenshtein dl = new DamerauLevenshtein(testHash, possibleKeyMatch);
			if (dl.getSimilarity()==0) 
			{
				System.out.println("\npossible match in permanent memory: delta to current problem = "+dl.getSimilarity());
				possibleMatches.add(possibleKeyMatch);
			}
		}
			
		int minDiff = Const.POSITIVE_INFINITY;
		String possibleSoln = ""; 
		
		for (Node n : semanticNet.candidateNodes.values())
		{
			for (String possibleMatch : possibleMatches)
			{
				
				DamerauLevenshtein dl = new DamerauLevenshtein(this.FigureHash(n), this.memory.get(possibleMatch));
				int dlDiff = dl.getSimilarity();
				if (dlDiff < minDiff)
				{
					
					minDiff = dl.getSimilarity();
					possibleSoln = n.getFigureLabel().trim();
				}
			}
				
		}
		if (minDiff != 0)
		{
			System.out.println("no matches found in permanent case memory. (smallest delta is "+minDiff+ ") falling back to generate and test");
			// discard case and fallback to alternative solver
			return null;
		} else 
		{
			System.out.println("possible match found. Solution Delta = "+minDiff);
			return possibleSoln;
		}
			
		
	}
	
	public static String FigureHash(Node n)
	{
		
		String hashStr = "";
		
		for (Entry<String,Frame> e : n.getFrameListEntrySet() )
		{
			hashStr = hashStr + e.getKey().toString().trim();
			
			Frame f = e.getValue();
			for (Entry<String,Integer> s : f.slots.entrySet())
			{
				hashStr = hashStr + s.getKey().toString().trim() + s.getValue().toString().trim();
			}
		}
		
		return hashStr;
	}
	
	public static String rpmProblemHash(SemanticNet semanticNet)
	{
    	String problemHash = "";
    	// writes RPM problem to permanent memory
    	for (Node n : semanticNet.nodes.values())
    	{
    		problemHash += RecordedCaseRPMSolver.FigureHash(n);
    	}
    	return problemHash;
	}
}
