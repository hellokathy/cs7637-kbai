package project3;

import java.util.ArrayList;

public class CheckForArithmeticSeries 
{
	
	private int a,b,c = 0;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	
	public CheckForArithmeticSeries(ArrayList<Integer> list)
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
		return -999;
	}
	
	public boolean isProportionalTo(CheckForArithmeticSeries chk2)
	{
		return (this.getConstant() == chk2.getConstant() && this.getConstant() != -999 );	
	}
}
