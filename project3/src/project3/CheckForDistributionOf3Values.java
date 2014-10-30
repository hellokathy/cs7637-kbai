package project3;

import java.util.ArrayList;
import java.util.Collections;

public class CheckForDistributionOf3Values 
{

	private ArrayList<Integer> list1Sorted = new ArrayList<Integer>();
	private ArrayList<Integer> list2Sorted = new ArrayList<Integer>();
	
	public CheckForDistributionOf3Values(ArrayList<Integer> list1, ArrayList<Integer> list2)
	{
		if (list1.size() != 3 || list2.size() != 3)
		{
			throw new RuntimeException ("Lists must contain 3 values each");
		}
		
		this.list1Sorted = new ArrayList<Integer>(list1); 
		this.list2Sorted = new ArrayList<Integer>(list2);
		
	}
	
	public boolean isDistributionOf3Values()
	{
		// sort list 1 and list 2 then compare them for equivalence and 
		// check that item1 != item2 and item1 != item3
		
		Collections.sort(list1Sorted);
		Collections.sort(list2Sorted);
		
		return list1Sorted.equals(list2Sorted) && list1Sorted.get(0) != list1Sorted.get(1) && list1Sorted.get(0) != list1Sorted.get(2);
	}
	
	public boolean isPartOfDistribution(ArrayList<Integer> listOf2)
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}		
		
		if (listOf2.get(0) != listOf2.get(1))
		{
			if (this.isDistributionOf3Values())
			{	
				// check whether list of 2 is contained within list1Sorted
				return this.list1Sorted.containsAll(listOf2);
			} 
			else 
			{
				return false;
			}
		} 
		else 
		{
			return false;
		}
	}
	
	public int getMissingValue(ArrayList<Integer> listOf2)
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}
		
		if (isPartOfDistribution(listOf2))
		{
			// find missing value from listOf2
			ArrayList<Integer> listOf1 = new ArrayList<Integer>(this.list1Sorted);
			listOf1.removeAll(listOf2);
			
			return listOf1.get(0);
		}
		else
		{
			return -999;
		}
	}
}
