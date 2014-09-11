package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import package1.KnnGlobals.FIGURE_LABEL;


class Frame 
{
	/* stores mapping from an attribute value to similarityWeight
	 * for a single object in a figure
	 */

	private HashMap<String, Integer> frame = null;
	
	public Frame()
	{
		frame = new HashMap<String, Integer>();
	}
	
	public void addSlot(String attributeName, Integer similarityWeight)
	{
		/* add new slot and filler to the frame
		 * 
		 */
		if (attributeName.length()>0)
		{
			frame.put(attributeName, similarityWeight);
		}
		
	}
	
	public Integer getSlot(String attributeName)
	{
		/* get slot using attributeName as a key 
		 * 
		 */
		Integer similarityValue = frame.get(attributeName);
		if ( similarityValue != null) 
		{
			return similarityValue;
		} else 
		{
			return 0;
			
		}
	}
}

class Node {
	/* list of frames related to a single figure in an RPM
	 */
	
	private KnnGlobals.FIGURE_LABEL figureLabel;
	private ArrayList<Frame> frames = null;
	private Node nextHorizontalNode = null;
	private Node nextVerticalNode = null;
	
	// constructor
	public Node(KnnGlobals.FIGURE_LABEL _figureLabel)
	{
		figureLabel = _figureLabel;
		frames = new ArrayList<Frame>();
	}
	
	// getters and setters
	public KnnGlobals.FIGURE_LABEL getFigureLabel() 
	{
		return figureLabel;
	}
	
	public void addFrame(Frame frame)
	{
		if (frame != null)
		{
			frames.add(frame);
		}
	}
	
	public Frame getFrame(int index){
		if (index >= 0 && index <= frames.size()-1)
		{
			return frames.get(index);
		} else {
			return null;
		}
	}
	
	public int getFrameListSize()
	{
		return frames.size();
	}
	
	public Node getNextHorizontalNode() {
		return nextHorizontalNode;
	}

	public void setNextHorizontalNode(Node _nextHorizontalNode) {
		this.nextHorizontalNode = _nextHorizontalNode;
	}

	public Node getNextVerticalNode() {
		return nextVerticalNode;
	}

	public void setNextVerticalNode(Node _nextVerticalNode) {
		this.nextVerticalNode = _nextVerticalNode;
	}
}



public class KnnSemanticNet 
{
	
	public HashMap<KnnGlobals.FIGURE_LABEL,Node> nodes = null;
	public HashMap<KnnGlobals.FIGURE_LABEL,Node> candidate_nodes = null;

	public KnnSemanticNet(String rpmType)
	{
		
		switch (rpmType) 
		{
			case "2x1":
				KnnGlobals.FIGURE_LABEL[] labels_2x1 = {KnnGlobals.FIGURE_LABEL.A, KnnGlobals.FIGURE_LABEL.B, KnnGlobals.FIGURE_LABEL.C};
				createNodes(labels_2x1);
				
				// link nodes
				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.B));

				break;
			case "2x2":
				KnnGlobals.FIGURE_LABEL[] labels_2x2 = 
			    {
						KnnGlobals.FIGURE_LABEL.A, 
						KnnGlobals.FIGURE_LABEL.B, 
						KnnGlobals.FIGURE_LABEL.C
				};
				
				createNodes(labels_2x2);
				
				// link nodes
				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.B));
				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.C));
				
				throw new IllegalArgumentException(
						"2x2 RPM problems not implemented yet");		
				//break; uncomment when implemented
			case "3x3":
				KnnGlobals.FIGURE_LABEL[] labels_3x3 = 
			    {
						KnnGlobals.FIGURE_LABEL.A,
						KnnGlobals.FIGURE_LABEL.B, 
						KnnGlobals.FIGURE_LABEL.C,
						KnnGlobals.FIGURE_LABEL.D,
						KnnGlobals.FIGURE_LABEL.E,
						KnnGlobals.FIGURE_LABEL.F,
						KnnGlobals.FIGURE_LABEL.G,
						KnnGlobals.FIGURE_LABEL.H
				};
				
				createNodes(labels_3x3);
				
				// link nodes
				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.B));
				nodes.get(KnnGlobals.FIGURE_LABEL.B).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.C));
				nodes.get(KnnGlobals.FIGURE_LABEL.D).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.E));
				nodes.get(KnnGlobals.FIGURE_LABEL.E).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.F));
				nodes.get(KnnGlobals.FIGURE_LABEL.G).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.H));

				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.D));
				nodes.get(KnnGlobals.FIGURE_LABEL.D).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.G));
				nodes.get(KnnGlobals.FIGURE_LABEL.B).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.E));
				nodes.get(KnnGlobals.FIGURE_LABEL.E).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.H));
				nodes.get(KnnGlobals.FIGURE_LABEL.C).setNextVerticalNode(nodes.get(KnnGlobals.FIGURE_LABEL.F));

				throw new IllegalArgumentException(
						"3x3 RPM problems not implemented yet");			
				//break; uncomment when implemented
			default:
				throw new IllegalArgumentException(
						"Please specify rpmType as; 2x1, 2x2 or 3x3");
		}
		

		
		
	}
	
	private void createNodes(KnnGlobals.FIGURE_LABEL[] labels)
	{
		for (KnnGlobals.FIGURE_LABEL label : labels)
		{
			Node node = new Node(label);
			nodes.put(node.getFigureLabel(), node);
		}
	}
}
	
	
	
	

