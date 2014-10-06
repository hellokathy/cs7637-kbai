package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class DLSolver {
/* Class uses Damerau Levenshtein distance algorithm to calculate distances between nodes 
 * in semantic network passed to constructor
 * 
 */
	
	private SemanticNet semanticNet = null;
	
	private String horizontalCaseMemory = null;
	private String verticalCaseMemory = null;
	
	private TreeMap<String,String> horizontalTestResults = null;
	private TreeMap<String,String> verticalTestResults = null;
	
	// constructor
	public DLSolver(SemanticNet _semanticNet)
	{
		this.semanticNet = _semanticNet;

		horizontalCaseMemory = null;
		verticalCaseMemory = null;
		horizontalTestResults = new TreeMap<String,String>();
		verticalTestResults = new TreeMap<String,String>();
	
	}
	
	public String computeSolution()
	{
		
		
		// traverse network - populate case memory
		// TODO: populate a list of nodes to start traversing from
		Node currNodeH = this.semanticNet.getStartNode();
		Node currNodeV = this.semanticNet.getStartNode();

		// store case values for horizontal transformations
	
		while (currNodeH.getNextHorizontalNode() != null) {
			
			// needs to be extended for multiple cases for 3x3 i.e. A : B , B : C 
			
			this.horizontalCaseMemory = this.generateDeltaString(currNodeH,currNodeH.getNextHorizontalNode());
			
			System.out.println("\nadded to case memory for T("+currNodeH.getFigureLabel()+","+currNodeH.getNextHorizontalNode().getFigureLabel()+") :"+this.horizontalCaseMemory+"\n" );
			currNodeH = currNodeH.getNextHorizontalNode();
		}
		
		/* TODO: mirror above case storage for vertical transformations
		 * more relevant for 2x2 and 3x3 problems
		 */ 
		
		// store cases for vertical transformations
		while (currNodeV.getNextVerticalNode() != null) {
						
			this.verticalCaseMemory = this.generateDeltaString(currNodeV,currNodeV.getNextVerticalNode());
			
			System.out.println("\nadded to case memory for T("+currNodeV.getFigureLabel()+","+currNodeV.getNextVerticalNode().getFigureLabel()+") :"+this.verticalCaseMemory+"\n" );
			currNodeV = currNodeV.getNextVerticalNode();
		}		
		
		
		// test each candidate solution against the horizontal test origin node
		
		for (Node candidateNode : semanticNet.candidateNodes.values())
		{
			// horizontal
			String testCandidateString = null;
			testCandidateString = this.generateDeltaString(this.semanticNet.getHorizontalTestOriginNode(), candidateNode);
			
			this.horizontalTestResults.put(candidateNode.getFigureLabel(),testCandidateString);
			System.out.println("calc test case value for T("+this.semanticNet.getHorizontalTestOriginNode().getFigureLabel()+","+candidateNode.getFigureLabel()+") :"+testCandidateString );
			
			if (this.semanticNet.getVerticalTestOriginNode() != null)
			{
				// vertical
				String testCandidateString2 = null;
				testCandidateString2 = this.generateDeltaString(this.semanticNet.getVerticalTestOriginNode(), candidateNode);
			
				this.verticalTestResults.put(candidateNode.getFigureLabel(),testCandidateString2);
				System.out.println("calc test case value for T("+this.semanticNet.getVerticalTestOriginNode().getFigureLabel()+","+candidateNode.getFigureLabel()+") :"+testCandidateString2 );
			}
			
		}
		
		/* TODO: mirror above test case storage for vertical test origin node
		 * more relevant for 2x2 and 3x3 problems
		 */ 
		
		// compare test results against case memory to see which
		// test result is the closest match
		/* TODO: the following code will only work for 1x2. Need to 
		 * come up with a scalable approach that will also work for 2x2 and 3x3
		 */
		
		return  compareTestResultsToCaseMemory();
		
		 
	}
	
	public String generateDeltaString(Node node1, Node node2)
	{
		/* Generates delta string between two nodes
		 * 
		 */
		
		String deltaString = "x";
		
		for (String objectInFrame1 : node1.getFrameListKeys())
		{
			
			Frame frame1 = node1.getFrame(objectInFrame1);
						
			int diff = 0;
			//double delta = 0;
			
			for (String objectInFrame2 : node2.getFrameListKeys())  // iterate across all frames in node 2
			{
				
				Frame frame2 = node2.getFrame(objectInFrame2);
					
				// need to get a combination of the keys in both frames since frame1 can 
				// contain keys that are not in frame2 and vice-versa.
				TreeSet<String> combinedKeys = new TreeSet<String>();
				
				// produce normalized frames
				Normalizer normalizer = new Normalizer(frame1,frame2);
				Frame normalizedFrame1 = normalizer.get(0);
				Frame normalizedFrame2 = normalizer.get(1);
				
				System.out.println("\n   obj:"+objectInFrame1+" -> "+normalizedFrame1);
				System.out.println("   obj:"+objectInFrame2+" -> "+normalizedFrame2+"\n");
				
				for (String key : normalizedFrame1.slots.keySet())
					if (!combinedKeys.contains(key)) combinedKeys.add(key);
				
				for (String key : normalizedFrame2.slots.keySet())
					if (!combinedKeys.contains(key)) combinedKeys.add(key);
				
				for (String key : combinedKeys)
				{
					diff =  getSimilarityValue(normalizedFrame2,key) - getSimilarityValue(normalizedFrame1,key) ;					
					deltaString = deltaString + String.valueOf(diff);
				}
				
			}
			//delta += sum;
			//deltaVector.add(delta);
			
		}
			
		return deltaString;
	}
	
	private Integer getSimilarityValue(Frame f, String key)
	{
		Integer weight = null;
		
		weight = f.slots.get(key);
		if ( weight == null)
		{
			return 0;
		} else
		{
			return weight;
		}
	}

	
	private String compareTestResultsToCaseMemory ()
	{
		/* compare a map of test results to values recorded in case memory
		 * return test result entry (containing candidate solution node label and KNN delta)
		 */
		String solutionTestResult = null;  // return value
		
		// make memory and test vectors all the same length, zero pad where necessary
		
		int diffDL = 0;

		
		int minDiff = Const.POSITIVE_INFINITY;
		
		// iterate through set of test result vectors and compute delta to avg case vector
		// keep track of which one has the smallest delta
		for (String candidateNodeLabel : this.horizontalTestResults.keySet())
		{
						
			String horizontalCandidate = this.horizontalTestResults.get(candidateNodeLabel);
			String verticalCandidate = this.verticalTestResults.get(candidateNodeLabel);

			String memory = this.horizontalCaseMemory;
			String candidate = horizontalCandidate;
			
			//TODO: This is where we would put
			// conflict-resolution logic if we find that 
			// more than one test result appears to be correct.
				
			DamerauLevenshtein dl = new DamerauLevenshtein(memory, candidate);
			diffDL = dl.getSimilarity();
			
			if ( diffDL < minDiff )
			{
				minDiff = diffDL;
				solutionTestResult = candidateNodeLabel;				
			}

		}
		
		System.out.println("Min diff calculated : " +minDiff);
		System.out.println("Answer calculated : " +solutionTestResult);
		return solutionTestResult;
	}
	
}
