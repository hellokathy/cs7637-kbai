package project3;

import java.util.ArrayList;
import java.util.List;

public class CheckForConstantAcrossRowOrCol 
{
	
	private int a,b,c = 0;
	private List<Integer> list = new ArrayList<Integer>();
	
	public CheckForConstantAcrossRowOrCol(List<Integer> list)
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
	

}
