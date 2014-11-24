package project4;

import java.util.ArrayList;
import java.util.List;

public class CheckFor2x2Pattern 
{

	private int a,b,c = 0;
	private List<Integer> list = new ArrayList<Integer>();

	
	public CheckFor2x2Pattern(List<Integer> list)
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

	public boolean isPossibleAdditionPattern()
	{
		try
		{
			return (a != b && b != c && a != c && a == 0)
				|| (a != b && b != c && a != c && a != 0 && b > a);
		}
		catch (Exception e)
		{
			return false;
		}

	}
	
	
	public int getMissingValueFromAdditionPattern()
	{
		if (isPossibleAdditionPattern()) 
		{
			return c + (b - a);
		}
		return Const.NEGATIVE_INFINITY;

	}
	
	public boolean isPattern0101()
	{
		return a == c && a != b;
	}
	
	
	public int getMissingValueFromPattern0101()
	{
		if (isPattern0101()) 
		{
			return b;
		}
		return Const.NEGATIVE_INFINITY;

	}

	public boolean isPattern1111()
	{
		return a == b && a == c;
	}
	
	public int getMissingValueFromPattern1111()
	{
		if (isPattern1111()) 
		{
			return c;
		}
		
		return Const.NEGATIVE_INFINITY;

	}

	public boolean isPattern0011()
	{
		return a == b && a != c;
	}
	
	public int getMissingValueFromPattern0011()
	{
		if (isPattern0011()) 
		{
			return c;
		}
		
		return Const.NEGATIVE_INFINITY;

	}

	public boolean isPattern0110()
	{
		return b == c && a !=b;
	}
	
	public int getMissingValueFromPattern0110()
	{
		if (isPattern0110()) 
		{
			return a;
		}
		
		return Const.NEGATIVE_INFINITY;

	}
	
//	public boolean isPattern0111()
//	{
//		return b == c && a != b;
//	}
//	
//	public int getMissingValueFromPattern0111()
//	{
//		if (isPattern0111()) 
//		{
//			return c;
//		}
//		
//		return Const.NEGATIVE_INFINITY;
//
//	}

	
	
}