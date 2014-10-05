package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class RecordedCaseSolver {
	
	private String testString;
	private  HashMap<String,String> memory = null;
	private SemanticNet semNet = null;
	
	public RecordedCaseSolver(String testString, HashMap<String,String> memory, SemanticNet semNet)
	{
		this.testString = testString;
		this.memory = memory;
		this.semNet = semNet;
	}
	
	public String computeSolution()
	{
		// iterate through candidate notes
		
		ArrayList<String> possibleMatches = new ArrayList<String>();
				
		//try to find cases that are close to the testString .. iterate through memory
		for (String possibleKeyMatch : memory.keySet())
		{
			DamerauLevenshtein dl = new DamerauLevenshtein(testString, possibleKeyMatch);
			if (dl.getSimilarity()<=4) possibleMatches.add(possibleKeyMatch);
		}
			
		int minDiff = Const.POSITIVE_INFINITY;
		String possibleSoln = ""; 
		
		for (Node n : semNet.candidateNodes.values())
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
		if (minDiff > 3)
		{
			// discard case and fallback to alternative solver
			return null;
		} else 
		{
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
				hashStr = hashStr + s.getValue().toString().trim();
			}
		}
		
		return hashStr;
	}
}
