package package1;

import java.util.ArrayList;

public class Node {
	/* list of frames related to a single figure in an RPM
	 */
	
	private String figureLabel;
	public ArrayList<Frame> frames = null;
	private Node nextHorizontalNode = null;
	private Node nextVerticalNode = null;
	
	// constructor
	public Node(String _figureLabel)
	{
		figureLabel = _figureLabel;
		frames = new ArrayList<Frame>();
	}
	
	// getters and setters
	public String getFigureLabel() 
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