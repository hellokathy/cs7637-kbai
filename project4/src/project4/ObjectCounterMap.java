package project4;

import java.util.HashMap;

public class ObjectCounterMap {

	private HashMap<String,Tuple> map = new HashMap<String,Tuple>();
	
	public ObjectCounterMap()
	{
		// constructor
		
	}
	
	public Tuple addObjectHash(String objectHash, int index)
	{
		Tuple tp = null;
		
		Integer count = 0;
		tp = map.get(objectHash);
		
		if (tp == null)
		{
			tp = new Tuple();
			count = 1;
			tp.count = count;
			tp.index = index;
		}
		else
		{
			tp.count++;
		}
		
		map.put(objectHash, tp);
		
		return tp;
		
	}
}
