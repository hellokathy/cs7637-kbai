package project3;

import java.util.ArrayList;

public class CheckForConstantAcrossRowOrCol 
{
	
	private int a,b,c = 0;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	
	public CheckForConstantAcrossRowOrCol(ArrayList<Integer> list)
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
	
	public boolean isConstantAcrossRowOrCol()
	{		
		return ( (b  == c ) && (b == a) && (c == a ) );
	}
	
	public int getConstant()
	{
		if (this.isConstantAcrossRowOrCol())
		{
			return a;		
		}
		return -999;
	}
}
