package project4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckForAlternatingSeries {


	private List<Integer> listOf2 = new ArrayList<Integer>();
	
	private int a,b,c,d,e,f,g,h = 0;
	
	public CheckForAlternatingSeries(List<Integer> list1, List<Integer> list2, List<Integer> listOf2)
	{
		if (list1.size() != 3 || list2.size() != 3 || listOf2.size() != 2)
		{
			throw new RuntimeException ("Lists 1 and 2 must contain 3 values each. List of 2 must contain 2 values.");
		}
		
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
	
	public boolean isAlternatingSeries1()
	{		
		
		
		return (a == c && d == f && a != b && d != e && g != h );
		
	}
	
	
	public int getMissingValue()
	{

		
		if (isAlternatingSeries1())
		{
			// find missing value from listOf2
			
			return listOf2.get(0);
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
	
}
