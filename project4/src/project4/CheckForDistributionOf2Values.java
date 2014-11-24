package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckForDistributionOf2Values 
{

	private List<Integer> list1Sorted = new ArrayList<Integer>();
	private List<Integer> listOf2 = new ArrayList<Integer>();
	
	private int a,b,c,d,e,f,g,h = 0;
	
	public CheckForDistributionOf2Values(List<Integer> list1, List<Integer> list2, List<Integer> listOf2)
	{
		if (list1.size() != 3 || list2.size() != 3 || listOf2.size() != 2)
		{
			throw new RuntimeException ("Lists 1 and 2 must contain 3 values each. List of 2 must contain 2 values.");
		}
		
		this.list1Sorted = new ArrayList<Integer>(list1); // new array created since this array will be modified
		Collections.sort(list1Sorted);
		this.listOf2 = listOf2;
		
		a = list1.get(0);
		b = list1.get(1);
		c = list1.get(2);
		
		d = list2.get(0);
		e = list2.get(1);
		f = list2.get(2);
		
		g = listOf2.get(0);
		h = listOf2.get(1);
	}
	
	public boolean isDistributionOf2Values()
	{
		
		
		return (a == c && a == e && a == f && a == g && a == h && a != b && b == d )
			|| (a == c && a == d && a == e && a == h && a != b && b == f && b == g )
			|| (b == c && b == d && b == f && b == g && b == h && a != b && a == e )
			|| (b == c && b == d && b == e && b == g && a != b && a == f && a == h )
			|| (a == b && a == e && a == f && a == g && a != c && c == d && c == h )
			|| (a == b && a == d && a == f && a == h && a != c && c == e && c == g );
	}
	
	
	public int getMissingValue()
	{

		
		if (isDistributionOf2Values())
		{
			// find missing value from listOf2
			ArrayList<Integer> listOf1 = new ArrayList<Integer>(this.list1Sorted);
			if (listOf2.get(0) == listOf2.get(1))
			{
				listOf1.removeAll(listOf2);
			}
			else
			{
				listOf1.remove(listOf2);
			}
			
			return listOf1.get(0);
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
}
