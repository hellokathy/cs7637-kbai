package project3;

import java.util.ArrayList;

public class CheckForGeometricSeries 
{

	private int a,b,c = 0;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	
	public CheckForGeometricSeries(ArrayList<Integer> list)
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
	
	public boolean isGeometricSeries()
	{
		// check whether b/a = c/b
		
		try 
		{
			return ( Double.compare( b/a, c/b ) == 0  && (b != a) );
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public double getConstant()
	{
		if (this.isGeometricSeries())
		{
			try
			{
				return b*1.0/a;
			}
			catch (Exception e)
			{
				return -999;
			}
		}
		
		return -999;
	}

	public boolean isProportionalTo(CheckForGeometricSeries chk2)
	{
		return (this.getConstant() == chk2.getConstant() && this.getConstant() != -999 );	
	}
}
