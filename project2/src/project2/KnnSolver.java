package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class KnnSolver {
/* Class uses KNN distance algorithm to calculate distances between nodes 
 * in semantic network passed to constructor
 * 
 */
	
	private SemanticNet semanticNet = null;
	
	private Vector horizontalCaseMemory = null;
	private Vector verticalCaseMemory = null;
	
	private HashMap<String,Vector> horizontalTestResults = null;
	private HashMap<String,Vector> verticalTestResults = null;
	
	// constructor
	public KnnSolver(SemanticNet _semanticNet)
	{
		this.semanticNet = _semanticNet;

		horizontalCaseMemory = null;
		verticalCaseMemory = null;
		horizontalTestResults = new HashMap<String,Vector>();
		verticalTestResults = new HashMap<String,Vector>();
	
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
		
		// first pad nodes with zero frames if necessary to make sure they
		// each contain the same number of frames
		int diff = 0;
		
		diff = node1.getFrameListSize() - node2.getFrameListSize();
		
		if (diff>0)
			for (int i=0 ; i<diff ; i++)
				node2.addFrame(new Frame());
		
		if (diff<0)
			for (int i=0 ; i<diff*-1 ; i++)
				node1.addFrame(new Frame());
		
		// TODO: adjust similarityWeights in frames to cater for
		// known idioms such as; angle of a circle is comparable to
		// any other angle for any other shape since the circle looks
		// exactly the same after rotation
		
		
		
		for (int i=0; i<node1.getFrameListSize(); i++)
		{
			
			Frame frame1 = node1.frames.get(i);
			Frame frame2 = node2.frames.get(i);
			
			int sum = 0;
			double delta = 0;
			
			// need to get a combination of the keys in both frames since frame1 can 
			// contain keys that are not in frame2 and vice-versa.
			ArrayList<String> combinedKeys = new ArrayList<String>();
				
			for (String key : frame1.slots.keySet())
				if (!combinedKeys.contains(key)) combinedKeys.add(key);
			
			
			for (String key : frame2.slots.keySet())
				if (!combinedKeys.contains(key)) combinedKeys.add(key);
			
			for (String key : combinedKeys)
			{
				sum +=  sqr( getSimilarityValue(frame1,key) - getSimilarityValue(frame2,key)) ;
			}
			delta = sum;
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
		//ArrayList<Double> avgCaseVector = new ArrayList<Double>();
		
				
//		for (int i = 0 ; i < vectorSize ; i++)
//		{
//			int sum = 0;
//			for (ArrayList<Integer> caseVector : caseMemory)
//			{
//				sum += caseVector.get(i);
//			}
//			avgCaseVector.add((double)sum / caseMemory.size()); // this is the case value we are going 
//            													// to compare the test results to
//		}
		
		// make memory and test vectors all the same length, zero pad where necessary
		int maxLength = Const.NEGATIVE_INFINITY;
		int maxTestVectorLength = Const.NEGATIVE_INFINITY;
		double diffVectorSum = 0;
		maxLength = this.horizontalCaseMemory.size();
		
		double minDiff = Const.POSITIVE_INFINITY;
		
		// iterate through set of test result vectors and compute delta to avg case vector
		// keep track of which one has the smallest delta
		for (Entry<String, Vector> testResultVectorEntry : this.horizontalTestResults.entrySet())
		{
			Vector diffVector = new Vector(); // diff between memory vector and test vector
			
			//TODO: This is where we would put
			// conflict-resolution logic if we find that 
			// more than one test result appears to be correct.
			
			diffVector = this.horizontalCaseMemory.getDiff(testResultVectorEntry.getValue());
			
			diffVectorSum = diffVector.getSum();
				
			if ( diffVectorSum < minDiff )
			{
				minDiff = diffVectorSum;
				solutionTestResult = testResultVectorEntry;		
			}
	
		}
		
		System.out.println("Min diff calculated : " +minDiff);
		System.out.println("Answer calculated : " +solutionTestResult.getKey());
		return solutionTestResult;
	}
	
}
