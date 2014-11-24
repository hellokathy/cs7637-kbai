package project4;

import java.util.ArrayList;
import java.util.List;

public class CheckForGeometricSeries 
{

	private int a,b,c = 0;
	private List<Integer> list = new ArrayList<Integer>();
	
	public CheckForGeometricSeries(List<Integer> list)
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

	public boolean isAnalogousTo(CheckForGeometricSeries chk2)
	{
		return (this.getConstant() == chk2.getConstant() && this.getConstant() != -999 );	
	}
	
	public int getMissingValue(List<Integer> listOf2)
	{
		if (listOf2.size() != 2 )
		{
			throw new RuntimeException ("List must contain 2 values");
		}
		
		Double constant = this.getConstant();
		
		if (Double.compare(listOf2.get(1) - listOf2.get(0), constant) == 0 )
		{
			// find missing value from listOf2
			Double retVal = listOf2.get(1) * constant;
			return retVal.intValue();
		}
		else
		{
			return Const.NEGATIVE_INFINITY;
		}
	}
	
}
