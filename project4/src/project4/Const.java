package project4;

public class Const {

	public static int NEGATIVE_INFINITY = -999999999;
	public static int POSITIVE_INFINITY = 999999999;
	public static int MIN_SIMILARITY_WEIGHT = 1;

	public static boolean DEBUG_OPENCV = false;
	
	public static String PERMANENT_CASE_FILENAME = "permanentmemory.db";
	
	public static String RPM_TYPE_3x3 = "3x3 (Image)";
	public static String RPM_TYPE_2x1 = "2x1 (Image)";
	public static String RPM_TYPE_2x2 = "2x2 (Image)";

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
		  polygon,
		  plus,
		  quadrilateral;
	}

	public static enum Size 
	{
		very_small,
		small,
		medium,
		large,
		very_large;
	}	
	
	public static enum Attr
	{
		 shape,
		 size,
		 fill,
		 angle,
		 left_of,
		 right_of,
		 above,
		 below,
		 overlaps,
		 inside,
		 shape_count;
		 
	}
}

