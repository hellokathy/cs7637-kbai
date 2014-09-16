package package1;

import java.util.ArrayList;
import java.util.HashMap;

public class Frame 
{
	/* stores mapping from an attribute value to similarityWeight
	 * for a single object in a figure
	 */

	public HashMap<String, Integer> slots = null;
	
	public Frame()
	{
		slots = new HashMap<String, Integer>();
	}
	
	//public void addSlots(String attributeName, Integer similarityWeight)
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
	
	public Integer getSlot(String attributeName)
	{
		/* get slot using attributeName as a key 
		 * 
		 */
		Integer similarityValue = slots.get(attributeName);
		if ( similarityValue != null) 
		{
			return similarityValue;
		} else 
		{
			return 0;
			
		}
	}
}