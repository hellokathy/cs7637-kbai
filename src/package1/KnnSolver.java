package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class KnnSolver {
/* Class uses KNN distance algorithm to calculate distances between nodes 
 * in semantic network passed to constructor
 * 
 */
	
	private SemanticNet semanticNet = null;
	private Node horizontalTestOriginNode = null;  // horizontal node to be tested against solution candidates
	private Node verticalTestOriginNode = null; // vertical node to be tested against solution candidates
	private Node startNode = null;
	
	private ArrayList<Double> horizontalCaseMemory = null;
	private ArrayList<Double> verticalCaseMemory = null;
	
	private HashMap<String,ArrayList<Double>> horizontalTestResults = null;
	private HashMap<String,ArrayList<Double>> verticalTestResults = null;
	
	// constructor
	public KnnSolver(SemanticNet _semanticNet)
	{
		this.semanticNet = _semanticNet;
		
		switch (semanticNet.rpmType)
		{
			case "2x1":
				horizontalTestOriginNode = semanticNet.nodes.get("C");
				verticalTestOriginNode = null;
				break;
			case "2x2":
				horizontalTestOriginNode = semanticNet.nodes.get("C");
				verticalTestOriginNode = semanticNet.nodes.get("B");	
				break;
			case "3x3":
				horizontalTestOriginNode = semanticNet.nodes.get("H");
				verticalTestOriginNode = semanticNet.nodes.get("F");
				// TODO: there are definitely other test origin nodes in a 3x3 RPM problem.
				// just have not thought them through yet. Not required for project 1
				break;
			default:
				throw new Error("Invalid Raven's problem type specified. Please ensure type is 1x2, 2x2 or 3x3");
		}
		
		startNode = semanticNet.nodes.get("A");
		
		horizontalCaseMemory = null;
		verticalCaseMemory = null;
		horizontalTestResults = new HashMap<String,ArrayList<Double>>();
		verticalTestResults = new HashMap<String,ArrayList<Double>>();
	
	}
	
	public String computeSolution()
	{
		
		Entry<String, ArrayList<Double>> testResultSelected = null;
		
		// traverse network - populate case memory
		// TODO: populate a list of nodes to start traversing from
		Node currNode = startNode;
		
		// store case values for horizontal transformations
	
		while (currNode.getNextHorizontalNode() != null) {
			
			this.horizontalCaseMemory = this.computeKnnDelta(currNode,currNode.getNextHorizontalNode());
			
			System.out.println("\nadded to case memory for T("+currNode.getFigureLabel()+","+currNode.getNextHorizontalNode().getFigureLabel()+") :"+this.horizontalCaseMemory+"\n" );
			currNode = currNode.getNextHorizontalNode();
		}
		
		/* TODO: mirror above case storage for vertical transformations
		 * more relevant for 2x2 and 3x3 problems
		 */ 
		
		// test each candidate solution against the horizontal test origin node
		
		for (Node candidateNode : semanticNet.candidateNodes.values())
		{
			ArrayList<Double> testCaseVector = null;
			testCaseVector = this.computeKnnDelta(horizontalTestOriginNode, candidateNode);
			this.horizontalTestResults.put(candidateNode.getFigureLabel(),testCaseVector);
			System.out.println("calc test case value for T("+horizontalTestOriginNode.getFigureLabel()+","+candidateNode.getFigureLabel()+") :"+testCaseVector );

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
	
	public ArrayList<Double> computeKnnDelta(Node node1, Node node2)
	{
		/* Computes K-nearest neighbor delta between two nodes
		 * 
		 */
		ArrayList<Double> deltaVector = new ArrayList<Double>();
		
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
				sum +=  sqr( getSimilarityWeight(frame1,key) - getSimilarityWeight(frame2,key)) ;
			}
			delta = sum;
			deltaVector.add(delta);
			
		}
			
		return deltaVector;
	}
	
	private Integer getSimilarityWeight(Frame f, String key)
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
	
	private Entry<String, ArrayList<Double>> compareTestResultsToCaseMemory ()
	{
		/* compare a map of test results to values recorded in case memory
		 * return test result entry (containing candidate solution node label and KNN delta)
		 */
		Entry<String, ArrayList<Double>> solutionTestResult = null;  // return value
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
		
		maxLength = this.horizontalCaseMemory.size();
		
		for (Entry<String, ArrayList<Double>> testResultVectorEntry : this.horizontalTestResults.entrySet())
			if (testResultVectorEntry.getValue().size() > maxLength )
				maxLength = testResultVectorEntry.getValue().size();
		
		// check case memory
		int diff = this.horizontalCaseMemory.size() - maxLength;
		
		if (diff<0)
			for (int i=0 ; i<diff*-1 ; i++)
				this.horizontalCaseMemory.add(0.0);
		
		// check test result vectors
		
		for (Entry<String, ArrayList<Double>> testResultVectorEntry : this.horizontalTestResults.entrySet())
		{
			diff = maxLength - testResultVectorEntry.getValue().size();
			
			if (diff>0)
				for (int i=0 ; i<diff ; i++)
					testResultVectorEntry.getValue().add(0.0);
		}
		
		


		double minDiff = Const.POSITIVE_INFINITY;
		
		// iterate through set of test result vectors and compute delta to avg case vector
		// keep track of which one has the smallest delta
		for (Entry<String, ArrayList<Double>> testResultVectorEntry : this.horizontalTestResults.entrySet())
		{
			ArrayList<Double> diffVector = new ArrayList<Double>(); // diff between memory vector and test vector
			int diffVectorSum = 0;
			
			for (int i = 0 ; i < this.horizontalCaseMemory.size() ; i++)
			{
				// work out diffVector
				diffVector.add( Math.abs( this.horizontalCaseMemory.get(i) - (double)(testResultVectorEntry.getValue().get(i)) ));
			}
			
			// sum elements of diff vector
			for (int j=0 ; j < diffVector.size() ; j++)
			{
				diffVectorSum += diffVector.get(j);
			}
				
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
