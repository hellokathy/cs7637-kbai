package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class KnnSemanticNet 
{
	
	public HashMap<KnnGlobals.FIGURE_LABEL,Node> nodes = null;
	public HashMap<KnnGlobals.FIGURE_LABEL,Node> candidate_nodes = null;
	public String rpmType = null;
	
	public KnnSemanticNet(String _rpmType)
	{
		nodes = new HashMap<KnnGlobals.FIGURE_LABEL,Node>();
		candidate_nodes = new HashMap<KnnGlobals.FIGURE_LABEL,Node>();
		rpmType = _rpmType;
		
		switch (rpmType) 
		{
			case "2x1":
				KnnGlobals.FIGURE_LABEL[] labels_2x1 = {KnnGlobals.FIGURE_LABEL.A, KnnGlobals.FIGURE_LABEL.B, KnnGlobals.FIGURE_LABEL.C};
				createNodes(labels_2x1);
				
				// link nodes
				nodes.get(KnnGlobals.FIGURE_LABEL.A).setNextHorizontalNode(nodes.get(KnnGlobals.FIGURE_LABEL.B));

				break;
			case "2x2":
/*				
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
*/				
				throw new IllegalArgumentException(
						"2x2 RPM problems not implemented yet");		
				//break; uncomment when implemented
			case "3x3":
/*
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
*/
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
	
	
	
	

