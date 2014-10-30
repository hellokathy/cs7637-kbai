package project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Frame 
{
	private String frameLabel;
	
	/* stores mapping from an attribute value to similarityWeight
	 * for a single object in a figure
	 */

	public TreeMap<String, Integer> slots = null;
	
	public Frame()
	{
		slots = new TreeMap<String, Integer>();
	}

	public Frame(String frameLabel)
	{
		this.frameLabel = frameLabel;
		slots = new TreeMap<String, Integer>();
	}
	
	public void setFrameLabel(String frameLabel)
	{
		this.frameLabel = frameLabel;
	}
	
	public String getFrameLabel()
	{
		return this.frameLabel;
	}

	public String getObjectHash()
	{
		// allows the agent to track the cardinality of this object in a figure i.e. how many are there?
		return this.getSlot("shape")+":"+this.getSlot("size")+":"+this.getSlot("fill")+this.getSlot("angle");
	}
	
	public void addSlots(ArrayList<NameValuePair> pairs)
	{
		/* add each new slot and filler to the frame
		 * 
		 */
		for (NameValuePair s : pairs)
			if (s.getName().length()>0)
			{
				slots.put(s.getName(), s.getValueInt());
			}
		
	}
	
	public void addSlot(NameValuePair pair)
	{
		if (pair.getName().length()>0)
			slots.put(pair.getName(), pair.getValueInt());
	}
	
	public Integer getSlot(String attributeName)
	{
		/* get slot using attributeName as a key 
		 * 
		 */
		Integer slotValue = slots.get(attributeName);
		if ( slotValue != null) 
		{
			return slotValue;
		} else 
		{
			return 0;
			
		}
	}
	
	@Override
	public String toString()
	{
		return slots.toString();
	}
}