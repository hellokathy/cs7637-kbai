package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class SemanticNet 
{
	
	public HashMap<String,Node> nodes = null;
	public HashMap<String,Node> candidateNodes = null;
	public String rpmType = null;
	
	public SemanticNet(String _rpmType)
	{
		nodes = new HashMap<String,Node>();
		candidateNodes = new HashMap<String,Node>();
		rpmType = _rpmType;
		
		String[] labels_candidate = {"1","2","3","4","5","6"};
		createCandidateNodes(labels_candidate);
		
		switch (rpmType) 
		{
			case "2x1":
				String[] labels_2x1 = {"A", "B", "C"};
				createNodes(labels_2x1);

				// link nodes
				nodes.get("A").setNextHorizontalNode(nodes.get("B"));

				break;
			case "2x2":
/*				
 				String[] labels_2x2 = 
			    {
						"A", 
						"B", 
						"C"
				};
				
				createNodes(labels_2x2);
				
				// link nodes
				nodes.get("A").setNextHorizontalNode(nodes.get("B"));
				nodes.get("A").setNextVerticalNode(nodes.get("C"));
*/				
				throw new IllegalArgumentException(
						"2x2 RPM problems not implemented yet");		
				//break; uncomment when implemented
			case "3x3":
/*
				KnnGlobals.String[] labels_3x3 = 
			    {
						"A",
						"B", 
						"C",
						"D",
						"E",
						"F",
						"G",
						"H"
				};
				
				
 				createNodes(labels_3x3);
				
				// link nodes
				nodes.get("A").setNextHorizontalNode(nodes.get("B"));
				nodes.get("B").setNextHorizontalNode(nodes.get("C"));
				nodes.get("D").setNextHorizontalNode(nodes.get("E"));
				nodes.get("E").setNextHorizontalNode(nodes.get("F"));
				nodes.get("G").setNextHorizontalNode(nodes.get("H"));

				nodes.get("A").setNextVerticalNode(nodes.get("D"));
				nodes.get("D").setNextVerticalNode(nodes.get("G"));
				nodes.get("B").setNextVerticalNode(nodes.get("E"));
				nodes.get("E").setNextVerticalNode(nodes.get("H"));
				nodes.get("C").setNextVerticalNode(nodes.get("F"));
*/
				throw new IllegalArgumentException(
						"3x3 RPM problems not implemented yet");			
				//break; uncomment when implemented
			default:
				throw new IllegalArgumentException(
						"Please specify rpmType as; 2x1, 2x2 or 3x3");
		}
		

		
		
	}
	
	private void createNodes(String[] _labels)
	{
		// these nodes are created and linked before traversing the 
		// Ravens problem
		for (String label : _labels)
		{
			Node node = new Node(label);
			nodes.put(label,node);
		}
		
	}
	
	private void createCandidateNodes(String[] _labels)
	{
		// these nodes are created and linked before traversing the 
		// Ravens problem
		for (String label : _labels)
		{
			Node node = new Node(label);
			candidateNodes.put(label,node);
		}
		
	}
	
	public void addFrameToNode(Frame f, String nodeLabel)
	{
		Node n = null;
		if (f != null) {
			// is it an answer node i.e. 1 to 6 ?
			if (Util.isNumeric(nodeLabel))
			{
				n = candidateNodes.get(nodeLabel);
			} else
			{
				// add to the set of nodes
				n = nodes.get(nodeLabel);
				
			}
			n.addFrame(f);
		}
		
	}
	
	public void debugPrintNetwork()
	{
		
		System.out.println("*******************************************");
		System.out.println("Network dump:");
		System.out.println("*******************************************");
		System.out.println("Candidate Nodes:");
		
		for (Entry<String,Node> nodeEntry: this.candidateNodes.entrySet())
		{
			System.out.println("\nNode "+ nodeEntry.getKey());
			
			Node node = nodeEntry.getValue();
			for (Frame f : node.frames){
				System.out.println("\nframe:");
				for (Entry<String,Integer> frameEntry : f.slots.entrySet()) 
				{
					System.out.println(frameEntry.getKey() + " -> " + frameEntry.getValue());
				}
			}
			
			
		}

		System.out.println("\nFixed Nodes:");
		
		for (Entry<String,Node> nodeEntry: this.nodes.entrySet())
		{
			System.out.println("\nNode "+ nodeEntry.getKey());
			
			Node node = nodeEntry.getValue();
			for (Frame f : node.frames){
				System.out.println("\nframe:");
				for (Entry<String,Integer> frameEntry : f.slots.entrySet()) 
				{
					System.out.println(frameEntry.getKey() + " -> " + frameEntry.getValue());
				}
			}
			
			
		}
	}
}
	
	
	
	

