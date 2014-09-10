package package1;

import java.util.ArrayList;
import java.util.HashMap;


class Frame {
	/* stores mapping from an attribute value to similarityWeight
	 * for a single object in a figure
	 */

	private HashMap<String, Integer> frame = null;
	
	public Frame(){
		frame = new HashMap<String, Integer>();
	}
	
	public void addSlot(String attributeName, Integer similarityWeight){
		/* add new slot and filler to the frame
		 * 
		 */
		if (attributeName.length()>0){
			frame.put(attributeName, similarityWeight);
		}
		
	}
	
	public Integer getSlot(String attributeName){
		/* get slot using attributeName as a key 
		 * 
		 */
		Integer similarityValue = frame.get(attributeName);
		if ( similarityValue != null) {
			return similarityValue;
		} else {
			return 0;
			
		}
	}
}

class Node {
	/* a list of frames related to a single figure in an RPM
	 * i.e. A,B,C,B,D1,D2, etc
	 * each list is keyed by a FIGURE_LABEL
	 */

	public HashMap<KnnGlobals.FIGURE_LABEL, ArrayList<Frame>> node = null;
	
	public Node(){
		node = new HashMap<KnnGlobals.FIGURE_LABEL, ArrayList<Frame>>();
	}
	

	 
}

public class KnnSemanticNet {
	
	
	
	
	
	
	
}
