package project4;

public class Util {

	public static boolean isNumeric(String s) 
	{  
	    return s.matches("\\d+"); 
	}
	
	public static int roundToNearestMultipleOfN(int input, int n)
	{
		//int roundVal =  n*(Math.round(inputNum/n));
		//int roundVal = Double.valueOf(n*(Math.ceil(Math.abs(inputNum/n)))).intValue();
		int roundVal = (input % n) > (n/2) ? input + n - input%n : input - input%n ;
		
		return roundVal;
	}
}
