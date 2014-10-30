package project3;

import java.util.ArrayList;

public class CheckForBinaryOp 
{

	private int a,b,c = 0;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	
	public CheckForBinaryOp(ArrayList<Integer> list)
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
	
	public boolean isBinaryOpAddition()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, a + b ) == 0  && (b != a) );
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isBinaryOpSubtraction()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, a - b ) == 0  && (b != a) );
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isBinaryOpDivision()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, a / b ) == 0  && (b != a) );
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isBinaryOpMultiplication()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, a * b ) == 0  && (b != a) );
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isProportionalTo(CheckForBinaryOp chk2)
	{
		return ( (this.isBinaryOpAddition() && chk2.isBinaryOpAddition() )
			   ||(this.isBinaryOpSubtraction() && chk2.isBinaryOpSubtraction() ) 
			   ||(this.isBinaryOpMultiplication() && chk2.isBinaryOpMultiplication() ) 
			   ||(this.isBinaryOpDivision() && chk2.isBinaryOpDivision() ));	
	}	
}
