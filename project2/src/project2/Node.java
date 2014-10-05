package project2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Node {
	/* contains Map of frames related to a single figure in an RPM
	 */
	
	private String figureLabel;
	public RavensFigureObjectIndex objIndex = null;
	private TreeMap<String,Frame> frames = null;
	private Node nextHorizontalNode = null;
	private Node nextVerticalNode = null;
	
	// constructor
	public Node(String _figureLabel)
	{
		figureLabel = _figureLabel;
		frames = new TreeMap<String,Frame>();
	}
	
	// getters and setters
	public String getFigureLabel() 
	{
		return figureLabel;
	}

	public void addFrame( Frame frame)
	{
		if (frame != null)
		{
			frames.put(frame.getObjectLabel(), frame);
		}
	}
	
	public Frame getFrame(String objectLabel)
	{
		if (!frames.containsKey(objectLabel))
		{
			// return an empty frame - ensure that attempts
			// to get values from this empty frame always return 0
			return (new Frame(objectLabel));
		} else 
		{
			return frames.get(objectLabel);
		}
		
	}	
	
	public Collection<Frame> getFrameListValues()
	{
		return frames.values();
	}

	public Set<String> getFrameListKeys()
	{
		return frames.keySet();
	}

	public Set<Entry<String,Frame>> getFrameListEntrySet()
	{
		return frames.entrySet();
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