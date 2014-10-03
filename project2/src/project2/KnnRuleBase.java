package project2;

import java.util.ArrayList;
import java.util.List;

import project2.RuleConstants.var;

class RuleFinalAngle1 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {0,90,180,270} then x:final-angle = 0
	public RuleFinalAngle1(String x)
	{
			super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(), 4),new IfEqual(x+var._angle.str(),new int[] {0,2,4,6} )), new ThenSet(x+var._finalAngle.str(),0) );
	}
}

class RuleFinalAngle2 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {45,135,225,315} then x:final-angle = 45
	public RuleFinalAngle2(String x)
	{
			super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),4),new IfEqual(x+var._angle.str(),new int[] {1,3,5,7} )), new ThenSet(x+var._finalAngle.str(),1) );
	}
}

class RuleFinalAngle3  extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {0,180} then x:final-angle = 0
	public RuleFinalAngle3(String x)
	{
			super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),2),new IfEqual(x+var._angle.str(),new int[] {0,4} )), new ThenSet(x+var._finalAngle.str(),0) );
	}
}

class RuleFinalAngle4  extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {90,270} then x:final-angle = 90
	public RuleFinalAngle4(String x)
	{
		super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),2),new IfEqual(x+var._angle.str(),new int[] {2,6} )), new ThenSet(x+var._finalAngle.str(),2) );
	}
}

class RuleFinalAngle5  extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {45,225} then x:final-angle = 45
	public RuleFinalAngle5(String x)
	{
		super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),2),new IfEqual(x+var._angle.str(),new int[] {1,5} )), new ThenSet(x+var._finalAngle.str(),1) );
	}
}

class RuleFinalAngle6  extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {135,315} then x:final-angle = 135
	public RuleFinalAngle6(String x)
	{
		super( new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),2),new IfEqual(x+var._angle.str(),new int[] {3,7} )), new ThenSet(x+var._finalAngle.str(),3) );
	}
}

class RuleFinalAngle7  extends Rule
{
	// if x:rotational-symmetry-order = 8 and x:angle in {0,90,180,270,45,135,225,315} then x:final-angle = 0
	public RuleFinalAngle7(String x)
	{
		super(new OpAnd(new IfEqual(x+var._rotationalSymmetryOrder.str(),8),new IfEqual(x+var._angle.str(),new int[] {0,1,2,3,4,5,6,7} )), new ThenSet(x+var._finalAngle.str(),0) );
	}
}

class RuleFinalAngle8  extends Rule
{
	// catch all rule
	//if x:rotational-symmetry-order in {1,7,9,10} then x:final-angle = x:angle
	public RuleFinalAngle8(String x)
	{
		super(new IfEqual(x+var._rotationalSymmetryOrder.str(), new int[] {1,7,9,10}), new ThenSet(x+var._finalAngle.str(), x+var._angle.str()) );
	}
}

class RuleFinalAngle9  extends Rule
{
	// if x:shape=circle and y:shape=circle then x:final-angle = 0, y:final-angle = 0
	public RuleFinalAngle9(String x, String y)
	{
		super( new OpAnd (new IfEqual(x+var._shape.str(),6), new IfEqual(y+var._shape.str(),6) ), new ThenSetMulti( new Consequence[] 
				                     																	{new ThenSet(x+var._finalAngle.str(),0),new ThenSet(y+var._finalAngle.str(),0)} ));
	}
}

class RuleFinalAngle10  extends Rule
{
	// if x:shape=circle and y:shape!=circle then x:final-angle = y:final-angle
	public RuleFinalAngle10(String x, String y)
	{
		super( new OpAnd (new IfEqual(x+var._shape.str(),6), new IfNotEqual(y+var._shape.str(),6) ), new ThenSet(x+var._finalAngle.str(),y+var._finalAngle.str()));
																												
	}
}

class RuleVerticalFlip1  extends Rule
{	

	// if x:shape=right-triangle and x:final-angle = 90 and y:vertical-flip = 1 then x:vertical-flip = 1
	public RuleVerticalFlip1(String x,String y)
	{

		super( new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+ var._shape.str(),5),new IfEqual(x+var._finalAngle.str(),2), new IfEqual(y+var._verticalFlip,1) } 
				), new ThenSet(x+var._verticalFlip.str(),1));
	}
}

class RuleVerticalFlip2  extends Rule
{
	// if x:vertical-symmetry = 1 and x:final-angle = 180 and y:vertical-flip = 1 then x:vertical-flip = 1
	public RuleVerticalFlip2(String x,String y)
	{

		super( new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+ var._verticalSymmetry.str(),1),new IfEqual(x+var._finalAngle.str(),4), new IfEqual(y+var._horizontalFlip,1) } 
				), new ThenSet(x+var._horizontalFlip.str(),1));		
	}
}

class RuleHorizontalFlip1  extends Rule
{	
	// if x:shape=right-triangle and x:final-angle = 270 and y:horizontal-flip = 1 then x:horizontal-flip = 1
	public RuleHorizontalFlip1(String x,String y)
	{

		super( new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+ var._shape.str(),5),new IfEqual(x+var._finalAngle.str(),7), new IfEqual(y+var._horizontalFlip,1) } 
				), new ThenSet(x+var._horizontalFlip.str(),1));		
	}
}

class RuleHorizontalFlip2  extends Rule
{
	//if x:horizontal-symmetry = 1 and x:final-angle = 180 and y:horizontal-flip = 1 then x:horizontal-flip = 1
	public RuleHorizontalFlip2(String x,String y)
	{

		super( new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+ var._horizontalSymmetry.str(),1),new IfEqual(x+var._finalAngle.str(),4), new IfEqual(y+var._horizontalFlip,1) } 
				), new ThenSet(x+var._horizontalFlip.str(),1));		
	}
}

class RuleRightOf extends Rule
{
	// if x:right-of = y then y:left-of = x
	public RuleRightOf(String x,String y)
	{
		super(new IfEqual(x+var._rightOf.str(),Integer.parseInt(y)), new ThenSet(y+var._leftOf.str(),Integer.parseInt(x)) );
	}
}

class RuleLeftOf extends Rule
{  
	// if x:left-of = y then y:right-of = x
	public RuleLeftOf(String x,String y)
	{
		super(new IfEqual(x+var._leftOf.str(),Integer.parseInt(y)), new ThenSet(y+var._rightOf.str(),Integer.parseInt(x)) );
	}
}

class RuleAbove extends Rule
{
	// if x:above = y then y:below = x
	public RuleAbove(String x,String y)
	{
		super(new IfEqual(x+var._above.str(),Integer.parseInt(y)), new ThenSet(y+var._below.str(),Integer.parseInt(x)) );
	}
}

class RuleBelow extends Rule
{  
	// if x:below = y then y:above = x
	public RuleBelow(String x,String y)
	{
		super(new IfEqual(x+var._below.str(),Integer.parseInt(y)), new ThenSet(y+var._above.str(),Integer.parseInt(x)) );
	}
}