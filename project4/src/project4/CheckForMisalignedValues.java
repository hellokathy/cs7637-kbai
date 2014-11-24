package project4;

import java.util.ArrayList;
import java.util.List;

public class CheckForMisalignedValues {

	// check to retrieve values from matrices that have possibly been misaligned
	
	private List<Integer> list2 = new ArrayList<Integer>();
	private List<Integer> listOf2 = new ArrayList<Integer>();
	
	private int a,b,c,d,e,f,g,h = 0;
	
	public CheckForMisalignedValues (List<Integer> list1, List<Integer> list2, List<Integer> listOf2)
	{
		if (list1.size() != 3 || list2.size() != 3 || listOf2.size() != 2)
		{
			throw new RuntimeException ("Lists 1 and 2 must contain 3 values each. List of 2 must contain 2 values.");
		}
		
		this.listOf2 = listOf2;
		this.list2 = list2;
		
		a = list1.get(0);
		b = list1.get(1);
		c = list1.get(2);
		
		d = list2.get(0);
		e = list2.get(1);
		f = list2.get(2);
		
		g = listOf2.get(0);
		h = listOf2.get(1);
	}
	
	public boolean isPossibleMisalignedValues()
	{		
		
		//return (g == h && g == d && g == e && g == a && g == b && g != c && c == f )
		//	|| (a == d && a == g && b == c && b == e && b == f && b == h && a != b );
		return (g == h && g == d && g == e && a == b && c == f)
			|| (g == h && g == a && g == b && d == e && c == f);
		
	}
	
	
	public int getMissingValue()
	{

		
		if (isPossibleMisalignedValues())
		{
			// find missing value from list2
			
			return list2.get(2);
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
	
}
