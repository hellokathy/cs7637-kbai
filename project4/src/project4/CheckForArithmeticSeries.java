package project4;

import java.util.ArrayList;
import java.util.List;

public class CheckForArithmeticSeries 
{
	
	private int a,b,c = 0;
	private List<Integer> list = new ArrayList<Integer>();
	
	public CheckForArithmeticSeries(List<Integer> list)
	{
		if (list.size() != 3 )
		{
			throw new RuntimeException ("Lists must contain 3 values each");
		}
		
		this.list = list; 
		
		a = list.get(0);
		b = list.get(1);
		c = list.get(2);
	}
	
	public boolean isArithmeticSeries()
	{
		// check whether b-a = c-b
		
		return ( (b - a) == ( c - b ) && (b != a) );
	}
	
	public int getConstant()
	{
		if (this.isArithmeticSeries())
		{
			return b - a;		
		}
		return Const.NEGATIVE_INFINITY;
	}
	
	public boolean isAnalogousTo(CheckForArithmeticSeries chk2)
	{
		return (this.getConstant() == chk2.getConstant() && this.getConstant() != Const.NEGATIVE_INFINITY );	
	}
	
	public int getMissingValue(List<Integer> listOf2)
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}
		
		int constant = this.getConstant();
		
		if (listOf2.get(1) - listOf2.get(0) == constant)
		{
			// find missing value from listOf2
			return listOf2.get(1) + constant;
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
}
