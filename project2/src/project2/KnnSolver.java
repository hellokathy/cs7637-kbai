package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class KnnSolver {
/* Class uses KNN distance algorithm to calculate distances between nodes 
 * in semantic network passed to constructor
 * 
 */
	
	private SemanticNet semanticNet = null;
	
	private Vector horizontalCaseMemory = null;
	private Vector verticalCaseMemory = null;
	
	private TreeMap<String,Vector> horizontalTestResults = null;
	private TreeMap<String,Vector> verticalTestResults = null;
	
	// constructor
	public KnnSolver(SemanticNet _semanticNet)
	{
		this.semanticNet = _semanticNet;

		horizontalCaseMemory = null;
		verticalCaseMemory = null;
		horizontalTestResults = new TreeMap<String,Vector>();
		verticalTestResults = new TreeMap<String,Vector>();
	
	}
	
	public String computeSolution()
	{
		
		Entry<String, Vector> testResultSelected = null;
		
		// traverse network - populate case memory
		// TODO: populate a list of nodes to start traversing from
		Node currNode = this.semanticNet.getStartNode();
		
		// store case values for horizontal transformations
	
		while (currNode.getNextHorizontalNode() != null) {
			
			// needs to be extended for multiple cases for 3x3 i.e. A : B , B : C 
			
			this.horizontalCaseMemory = this.calcKnnDelta(currNode,currNode.getNextHorizontalNode());
			
			System.out.println("\nadded to case memory for T("+currNode.getFigureLabel()+","+currNode.getNextHorizontalNode().getFigureLabel()+") :"+this.horizontalCaseMemory+"\n" );
			currNode = currNode.getNextHorizontalNode();
		}
		
		/* TODO: mirror above case storage for vertical transformations
		 * more relevant for 2x2 and 3x3 problems
		 */ 
		
		// test each candidate solution against the horizontal test origin node
		
		for (Node candidateNode : semanticNet.candidateNodes.values())
		{
			Vector testCandidateVector = null;
			testCandidateVector = this.calcKnnDelta(this.semanticNet.getHorizontalTestOriginNode(), candidateNode);
			
			this.horizontalTestResults.put(candidateNode.getFigureLabel(),testCandidateVector);
			System.out.println("calc test case value for T("+this.semanticNet.getHorizontalTestOriginNode().getFigureLabel()+","+candidateNode.getFigureLabel()+") :"+testCandidateVector );

		}
		
		/* TODO: mirror above test case storage for vertical test origin node
		 * more relevant for 2x2 and 3x3 problems
		 */ 
		
		// compare test results against case memory to see which
		// test result is the closest match
		/* TODO: the following code will only work for 1x2. Need to 
		 * come up with a scalable approach that will also work for 2x2 and 3x3
		 */
		
		testResultSelected = compareTestResultsToCaseMemory();
		
		return testResultSelected.getKey();
	}
	
	public Vector calcKnnDelta(Node node1, Node node2)
	{
		/* Computes nearest neighbor delta between two nodes
		 * 
		 */
		
		Vector deltaVector = new Vector();
		
		for (String objectInFrame1 : node1.getFrameListKeys())
		{
			
			Frame frame1 = node1.getFrame(objectInFrame1);
						
			int sum = 0;
			double delta = 0;
			
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

				for (String key : normalizedFrame1.slots.keySet())
					if (!combinedKeys.contains(key)) combinedKeys.add(key);
				
				for (String key : normalizedFrame2.slots.keySet())
					if (!combinedKeys.contains(key)) combinedKeys.add(key);
				
				System.out.println("\n   obj:"+objectInFrame1+" -> "+normalizedFrame1);
				System.out.println("   obj:"+objectInFrame2+" -> "+normalizedFrame2+"\n");
				
				for (String key : combinedKeys)
				{
					sum +=  sqr( getSimilarityValue(normalizedFrame1,key) - getSimilarityValue(normalizedFrame2,key)) ;
				}
				
			}
			delta += sum;
			deltaVector.add(delta);
			
		}
			
		return deltaVector;
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
	
	private int sqr(int i)
	{
		return i * i;
	}
	
	private Entry<String, Vector> compareTestResultsToCaseMemory ()
	{
		/* compare a map of test results to values recorded in case memory
		 * return test result entry (containing candidate solution node label and KNN delta)
		 */
		Entry<String, Vector> solutionTestResult = null;  // return value
		
		// make memory and test vectors all the same length, zero pad where necessary
		int maxLength = Const.NEGATIVE_INFINITY;
		int maxTestVectorLength = Const.NEGATIVE_INFINITY;
		double diffVectorSum = 0;
		maxLength = this.horizontalCaseMemory.size();
		
		double minDiff = Const.POSITIVE_INFINITY;
		
		// iterate through set of test result vectors and compute delta to avg case vector
		// keep track of which one has the smallest delta
		for (Entry<String, Vector> testResultVector : this.horizontalTestResults.entrySet())
		{

			
				Vector diffVector = new Vector(); // diff between memory vector and test vector
				
				//TODO: This is where we would put
				// conflict-resolution logic if we find that 
				// more than one test result appears to be correct.
				
				diffVector = this.horizontalCaseMemory.getDiff(testResultVector.getValue());
				
				diffVectorSum = diffVector.getSum();
					
				if ( diffVectorSum < minDiff )
				{
				
						minDiff = diffVectorSum;
						solutionTestResult = testResultVector;
					
				}
			
			
		}
		
		System.out.println("Min diff calculated : " +minDiff);
		System.out.println("Answer calculated : " +solutionTestResult.getKey());
		return solutionTestResult;
	}
	
}
