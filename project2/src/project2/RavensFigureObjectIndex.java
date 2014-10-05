package project2;

import java.util.ArrayList;
import java.util.HashMap;

public class RavensFigureObjectIndex 
{
	private HashMap<String,String> index = new HashMap<String,String>();

	public RavensFigureObjectIndex(RavensFigure rf) 
	{
		ArrayList<RavensObject> objects = rf.getObjects();
		for ( int i = 0; i < objects.size(); i++)
		{
			RavensObject ro = objects.get(i);
			index.put(ro.getName().trim(), String.valueOf(i*2 + 1));
		}	
	}
	
	public String get(String objectLabel)
	{
		return index.get(objectLabel);
	}

}
