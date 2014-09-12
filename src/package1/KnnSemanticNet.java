package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class KnnSemanticNet 
{
	
	public HashMap<FIGURE_LABEL,Node> nodes = null;
	public HashMap<FIGURE_LABEL,Node> candidate_nodes = null;
	public String rpmType = null;
	
	public KnnSemanticNet(String _rpmType)
	{
		nodes = new HashMap<FIGURE_LABEL,Node>();
		candidate_nodes = new HashMap<FIGURE_LABEL,Node>();
		rpmType = _rpmType;
		
		switch (rpmType) 
		{
			case "2x1":
				FIGURE_LABEL[] labels_2x1 = {FIGURE_LABEL.A, FIGURE_LABEL.B, FIGURE_LABEL.C};
				createNodes(labels_2x1);

				// link nodes
				nodes.get(FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(FIGURE_LABEL.B));

				break;
			case "2x2":
/*				
 				FIGURE_LABEL[] labels_2x2 = 
			    {
						FIGURE_LABEL.A, 
						FIGURE_LABEL.B, 
						FIGURE_LABEL.C
				};
				
				createNodes(labels_2x2);
				
				// link nodes
				nodes.get(FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(FIGURE_LABEL.B));
				nodes.get(FIGURE_LABEL.A).setNextVerticalNode(nodes.get(FIGURE_LABEL.C));
*/				
				throw new IllegalArgumentException(
						"2x2 RPM problems not implemented yet");		
				//break; uncomment when implemented
			case "3x3":
/*
				KnnGlobals.FIGURE_LABEL[] labels_3x3 = 
			    {
						FIGURE_LABEL.A,
						FIGURE_LABEL.B, 
						FIGURE_LABEL.C,
						FIGURE_LABEL.D,
						FIGURE_LABEL.E,
						FIGURE_LABEL.F,
						FIGURE_LABEL.G,
						FIGURE_LABEL.H
				};
				
				
 				createNodes(labels_3x3);
				
				// link nodes
				nodes.get(FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(FIGURE_LABEL.B));
				nodes.get(FIGURE_LABEL.B).setNextHorizontalNode(nodes.get(FIGURE_LABEL.C));
				nodes.get(FIGURE_LABEL.D).setNextHorizontalNode(nodes.get(FIGURE_LABEL.E));
				nodes.get(FIGURE_LABEL.E).setNextHorizontalNode(nodes.get(FIGURE_LABEL.F));
				nodes.get(FIGURE_LABEL.G).setNextHorizontalNode(nodes.get(FIGURE_LABEL.H));

				nodes.get(FIGURE_LABEL.A).setNextVerticalNode(nodes.get(FIGURE_LABEL.D));
				nodes.get(FIGURE_LABEL.D).setNextVerticalNode(nodes.get(FIGURE_LABEL.G));
				nodes.get(FIGURE_LABEL.B).setNextVerticalNode(nodes.get(FIGURE_LABEL.E));
				nodes.get(FIGURE_LABEL.E).setNextVerticalNode(nodes.get(FIGURE_LABEL.H));
				nodes.get(FIGURE_LABEL.C).setNextVerticalNode(nodes.get(FIGURE_LABEL.F));
*/
				throw new IllegalArgumentException(
						"3x3 RPM problems not implemented yet");			
				//break; uncomment when implemented
			default:
				throw new IllegalArgumentException(
						"Please specify rpmType as; 2x1, 2x2 or 3x3");
		}
		

		
		
	}
	
	private void createNodes(FIGURE_LABEL[] _labels)
	{
		for (FIGURE_LABEL label : _labels)
		{
			Node node = new Node(label);
			nodes.put(label,node);
		}
		
	}
	
	public void addFrameToNode(Frame f, FIGURE_LABEL nodeLabel)
	{
		Node n = null;
		if (f != null) {
			// is it an answer node i.e. 1 to 6 ?
			if (Util.isNumeric(nodeLabel.getValue()))
			{
				// add to set of candidate nodes
				n = candidate_nodes.get(nodeLabel);
			} else
			{
				// add to the set of nodes
				n = nodes.get(nodeLabel);
				
			}
			if (n != null) n.addFrame(f);
		}
		
	}
}
	
	
	
	

