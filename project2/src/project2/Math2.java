package project2;

public class Math2 {

	public static boolean isOdd(int i)
	{
		return !(i % 2 == 0);
	}
	
	public static int nextOddInt(int i)
	{
		if (Math2.isOdd(i))
		{
			return i;
		} else
		{
			return Math2.nextOddInt(i+1);
		}
	}
	
}
