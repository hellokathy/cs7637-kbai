package project4;

public class Const {

	public static int NEGATIVE_INFINITY = -999999999;
	public static int POSITIVE_INFINITY = 999999999;
	public static int MIN_SIMILARITY_WEIGHT = 1;

	public static String PERMANENT_CASE_FILENAME = "permanentmemory.db";
	
	public static enum Fill 
	{
		no, yes;
	}
	
	public static enum Shape 
	{
		  circle,
		  Pac_Man,
		  triangle,
		  right_triangle,
		  square,
		  rectangle,
		  pentagon,
		  hexagon,
		  septagon,
		  heptagon,
		  octagon,
		  nonogon,
		  decagon,
		  dodecagon,
		  arrow,
		  half_arrow,
		  plus;
	}

	public static enum Size 
	{
		very_small,
		small,
		medium,
		large,
		very_large,
	}	
}

