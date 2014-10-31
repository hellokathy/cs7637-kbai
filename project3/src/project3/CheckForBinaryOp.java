package project3;

import java.util.ArrayList;
import java.util.List;

public class CheckForBinaryOp 
{

	private int a,b,c = 0;
	private List<Integer> list = new ArrayList<Integer>();
	
	public CheckForBinaryOp(List<Integer> list)
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
			return ( Double.compare( c, a + b ) == 0  );
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
			return ( Double.compare( c, a - b ) == 0  );
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isBinaryOpSubtraction2()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, b - a ) == 0  );
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
			return ( Double.compare( c, a / b ) == 0 && a != 0 && b != 0  );
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isBinaryOpDivision2()
	{
		// check whether a op b = c
		
		try 
		{
			return ( Double.compare( c, b / a ) == 0 && a != 0 && b != 0 );
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
			return ( Double.compare( c, a * b ) == 0  );
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public int getMissingValue(List<Integer> listOf2)
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}
		
		if (this.isBinaryOpAddition())
		{
			return listOf2.get(0) + listOf2.get(1);
		}
		
		if (this.isBinaryOpDivision())
		{	
			System.out.println(listOf2.get(0) + "/" + listOf2.get(1));
			return listOf2.get(0) / listOf2.get(1);
		}

		if (this.isBinaryOpDivision2())
		{
			return listOf2.get(1) / listOf2.get(0);
		}

		if (this.isBinaryOpMultiplication())
		{		
			return listOf2.get(0) * listOf2.get(1);
		}

		if (this.isBinaryOpSubtraction())
		{		
			return listOf2.get(0) - listOf2.get(1);
		}

		if (this.isBinaryOpSubtraction2())
		{		
			return listOf2.get(1) - listOf2.get(0);
		}
		
		return Const.NEGATIVE_INFINITY;
	}
	
	public boolean isAnalogousTo(CheckForBinaryOp chk)
	{
		 return (this.isBinaryOpAddition() && chk.isBinaryOpAddition() )
		     ||	(this.isBinaryOpDivision() && chk.isBinaryOpDivision() )
		     || (this.isBinaryOpDivision2() && chk.isBinaryOpDivision2() )
		     || (this.isBinaryOpMultiplication() && chk.isBinaryOpMultiplication() )
		     || (this.isBinaryOpSubtraction() && chk.isBinaryOpSubtraction() )
		     || (this.isBinaryOpSubtraction2() && chk.isBinaryOpSubtraction2() );
	}

}