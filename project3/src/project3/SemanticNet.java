package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class SemanticNet 
{
	
	public TreeMap<String,Node> nodes = null;
	public TreeMap<String,Node> candidateNodes = null;
	public String rpmType = null;
	private Node horizontalTestOriginNode = null;  // horizontal node to be tested against solution candidates
	private Node verticalTestOriginNode = null; // vertical node to be tested against solution candidates
	private Node startNode = null;

	
	public Node getHorizontalTestOriginNode() {
		return horizontalTestOriginNode;
	}
	public void setHorizontalTestOriginNode(Node horizontalTestOriginNode) {
		this.horizontalTestOriginNode = horizontalTestOriginNode;
	}

	public Node getVerticalTestOriginNode() {
		return verticalTestOriginNode;
	}
	public void setVerticalTestOriginNode(Node verticalTestOriginNode) {
		this.verticalTestOriginNode = verticalTestOriginNode;
	}

	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	public SemanticNet(String _rpmType)
	{
		nodes = new TreeMap<String,Node>();
		candidateNodes = new TreeMap<String,Node>();
		rpmType = _rpmType;
		
		String[] labels_candidate = {"1","2","3","4","5","6"};
		createCandidateNodes(labels_candidate);
		
		switch (rpmType) 
		{
			case "2x1":
				String[] labels_2x1 = {"A","B","C"};
				createNodes(labels_2x1);

				// link nodes
				this.nodes.get("A").setNextHorizontalNode(this.nodes.get("B"));
				
				// set test origin nodes
				this.setHorizontalTestOriginNode(this.nodes.get("C"));
				this.setVerticalTestOriginNode(null);
				
				break;
			case "2x2":
				
 				String[] labels_2x2 = {"A","B","C"};
				
				createNodes(labels_2x2);
				
				// link nodes
				this.nodes.get("A").setNextHorizontalNode(this.nodes.get("B"));
				this.nodes.get("A").setNextVerticalNode(this.nodes.get("C"));
				
				// set test origin nodes
				this.setHorizontalTestOriginNode(this.nodes.get("C"));
				this.setVerticalTestOriginNode (this.nodes.get("B"));	
				
				break; 
			case "3x3":

				String[] labels_3x3 = {"A","B","C","D","E","F","G","H"};
				
 				createNodes(labels_3x3);
				
				// link nodes
 				this.nodes.get("A").setNextHorizontalNode(this.nodes.get("B"));
 				this.nodes.get("B").setNextHorizontalNode(this.nodes.get("C"));
 				this.nodes.get("D").setNextHorizontalNode(this.nodes.get("E"));
 				this.nodes.get("E").setNextHorizontalNode(this.nodes.get("F"));
 				this.nodes.get("G").setNextHorizontalNode(this.nodes.get("H"));

 				this.nodes.get("A").setNextVerticalNode(this.nodes.get("D"));
 				this.nodes.get("D").setNextVerticalNode(this.nodes.get("G"));
 				this.nodes.get("B").setNextVerticalNode(this.nodes.get("E"));
 				this.nodes.get("E").setNextVerticalNode(this.nodes.get("H"));
 				this.nodes.get("C").setNextVerticalNode(this.nodes.get("F"));

				this.setHorizontalTestOriginNode(this.nodes.get("H"));
				this.setVerticalTestOriginNode(this.nodes.get("F"));
				
				break; 
			default:
				throw new IllegalArgumentException(
						"Please specify rpmType as; 2x1, 2x2 or 3x3");
		}
		
		this.setStartNode(this.nodes.get("A"));
		
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
	
	public Node getNode(String nodeLabel)
	{
		if (nodeLabel != null) {
			// is it an answer node i.e. 1 to 6 ?
			if (Util.isNumeric(nodeLabel))
			{
				return candidateNodes.get(nodeLabel);
			} else
			{
				// add to the set of nodes
			    return  nodes.get(nodeLabel);
				
			}
		}	
		return null;

	}
	
	public ArrayList<String> getAllSlots()
	{
		// returns a list of all the slot names across all the frames contained within "nodes"
		ArrayList<String> slotNames = new ArrayList<String>();
		LinkedHashSet<String> setWithoutDupes = new LinkedHashSet<String>();
		
		for (Entry<String,Node> nodeEntry: this.nodes.entrySet())
		{			
			Node node = nodeEntry.getValue();
			for (Frame f : node.getFrameListValues())
			{
				setWithoutDupes.addAll(f.slots.keySet() );
			}
			
			
		}
		slotNames.addAll(setWithoutDupes);
		
		return slotNames;
	}

	public ArrayList<String> getAllFrameLabels()
	{
		// returns a list of all the frame labels across all the frames contained within "nodes"
		ArrayList<String> frameLabels = new ArrayList<String>();
		LinkedHashSet<String> setWithoutDupes = new LinkedHashSet<String>();
		
		for (Entry<String,Node> nodeEntry: this.nodes.entrySet())
		{			
			Node node = nodeEntry.getValue();
			for (Frame f : node.getFrameListValues())
			{
				setWithoutDupes.add(f.getFrameLabel());
			}
			
			
		}
		frameLabels.addAll(setWithoutDupes);
		
		return frameLabels;
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
			for (Frame f : node.getFrameListValues()){
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
			for (Frame f : node.getFrameListValues()){
				System.out.println("\nframe:");
				for (Entry<String,Integer> frameEntry : f.slots.entrySet()) 
				{
					System.out.println(frameEntry.getKey() + " -> " + frameEntry.getValue());
				}
			}
			
			
		}
	}
}
	
	
	
	

