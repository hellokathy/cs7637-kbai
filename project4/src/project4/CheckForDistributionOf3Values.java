package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckForDistributionOf3Values 
{
	
	private int a,b,c,d,e,f,g,h = 0;
	private List<Integer> list1Sorted = new ArrayList<Integer>();
	private List<Integer> listOf2 = new ArrayList<Integer>();

	public CheckForDistributionOf3Values(List<Integer> list1, List<Integer> list2, List<Integer> listOf2)
	{
		if (list1.size() != 3 || list2.size() != 3 || listOf2.size() != 2 )
		{
			throw new RuntimeException ("Lists must contain 3 values each");
		}
		
		this.list1Sorted = new ArrayList<Integer>(list1); 
		this.listOf2 = listOf2;
		Collections.sort(list1Sorted);
		
		a = list1.get(0);
		b = list1.get(1);
		c = list1.get(2);
		
		d = list2.get(0);
		e = list2.get(1);
		f = list2.get(2);
		
		g = listOf2.get(0);
		h = listOf2.get(1);
		
	}
	
	public boolean isDistributionOf3Values()
	{
		// sort list 1 and list 2 then compare them for equivalence and 
		// check that item1 != item2 and item1 != item3
		
		
		
		return (a == f && a == h && b == d && c == e && c == g && a != b && b != c && a != c )
			|| (a == e && b == f && b == g && c == d && c == h && a != b && b != c && a != c );
	}
	
	
	public int getMissingValue()
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}
		
		if (isDistributionOf3Values())
		{
			// find missing value from listOf2
			ArrayList<Integer> listOf1 = new ArrayList<Integer>(this.list1Sorted);
			listOf1.removeAll(listOf2);
			return listOf1.get(0);
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
}
