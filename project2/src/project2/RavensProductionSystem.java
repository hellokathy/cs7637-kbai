package project2;

import java.util.ArrayList;
import java.util.List;

import project2.RuleConstants.SLOT;

public class RavensProductionSystem
{
	public RuleSet ruleset = new RuleSet();

	
	public RavensProductionSystem(Frame f1, Frame f2)
	{
		this.ruleset.add(new RuleFinalAngle1(f1,"1"));
		this.ruleset.add(new RuleFinalAngle1(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle2(f1,"1"));
		this.ruleset.add(new RuleFinalAngle2(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle3(f1,"1"));
		this.ruleset.add(new RuleFinalAngle3(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle4(f1,"1"));
		this.ruleset.add(new RuleFinalAngle4(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle5(f1,"1"));
		this.ruleset.add(new RuleFinalAngle5(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle6(f1,"1"));
		this.ruleset.add(new RuleFinalAngle6(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle7(f1,"1"));
		this.ruleset.add(new RuleFinalAngle7(f2,"2"));
	
		this.ruleset.add(new RuleFinalAngle8(f1,"1"));
		this.ruleset.add(new RuleFinalAngle8(f2,"2"));
		
		this.ruleset.add(new RuleFinalAngle9(f1,f2,"1","2"));
	
		this.ruleset.add(new RuleFinalAngle10(f2,f1,"2","1"));
		this.ruleset.add(new RuleFinalAngle10(f1,f2,"1","2"));
		
		this.ruleset.add(new RuleVerticalFlip1(f1,f2,"1","2"));
		this.ruleset.add(new RuleVerticalFlip1(f2,f1,"2","1"));
		
		this.ruleset.add(new RuleVerticalFlip2(f1,f2,"1","2"));
		this.ruleset.add(new RuleVerticalFlip2(f2,f1,"2","1"));

		//this.ruleset.add(new RuleShapeChange1(f1, f2, "1", "2"));
		this.ruleset.add(new RuleShapeChange2(f1, f2, "1", "2"));
		
		
//		this.ruleset.add(new RuleRightOf(f1.getObjectLabel(),f2.getObjectLabel()));
//		this.ruleset.add(new RuleRightOf(f2.getObjectLabel(),f1.getObjectLabel()));
//
//		this.ruleset.add(new RuleLeftOf(f1.getObjectLabel(),f2.getObjectLabel()));
//		this.ruleset.add(new RuleLeftOf(f2.getObjectLabel(),f1.getObjectLabel()));
//
//		this.ruleset.add(new RuleAbove(f1.getObjectLabel(),f2.getObjectLabel()));
//		this.ruleset.add(new RuleAbove(f2.getObjectLabel(),f1.getObjectLabel()));
//
//		this.ruleset.add(new RuleBelow(f1.getObjectLabel(),f2.getObjectLabel()));
//		this.ruleset.add(new RuleBelow(f2.getObjectLabel(),f1.getObjectLabel()));

	}
}

class RuleFinalAngle1 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {0,90,180,270} then x:final-angle = 0
	public RuleFinalAngle1(Frame f, String x)
	{
			super(x+"_RuleFinalAngle1", new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(), 4),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {0,2,4,6} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),0) );
	}
}

class RuleFinalAngle2 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {45,135,225,315} then x:final-angle = 45
	public RuleFinalAngle2(Frame f, String x)
	{
			super(x+"_RuleFinalAngle2",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),4),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {1,3,5,7} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),1) );
	}
}

class RuleFinalAngle3 extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {0,180} then x:final-angle = 0
	public RuleFinalAngle3(Frame f, String x)
	{
			super(x+"_RuleFinalAngle3",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),2),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {0,4} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),0) );
	}
}

class RuleFinalAngle4 extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {90,270} then x:final-angle = 90
	public RuleFinalAngle4(Frame f, String x)
	{
		super(x+"_RuleFinalAngle4",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),2),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {2,6} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),2) );
	}
}

class RuleFinalAngle5 extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {45,225} then x:final-angle = 45
	public RuleFinalAngle5(Frame f, String x)
	{
		super(x+"_RuleFinalAngle5",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),2),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {1,5} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),1) );
	}
}

class RuleFinalAngle6 extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {135,315} then x:final-angle = 135
	public RuleFinalAngle6(Frame f, String x)
	{
		super(x+"_RuleFinalAngle6",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),2),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {3,7} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),3) );
	}
}

class RuleFinalAngle7 extends Rule
{
	// if x:rotational-symmetry-order = 8 and x:angle in {0,90,180,270,45,135,225,315} then x:final-angle = 0
	public RuleFinalAngle7(Frame f, String x)
	{
		super(x+"_RuleFinalAngle7",new OpAnd(new IfEqual(x+"_"+SLOT.rotationalSymmetryOrder.str(),8),new IfEqual(x+"_"+SLOT.angle.str(),new int[] {0,1,2,3,4,5,6,7} )), new ThenSet(x+"_"+SLOT.finalAngle.str(),0) );
	}
}

class RuleFinalAngle8 extends Rule
{
	// catch all rule
	//if x:rotational-symmetry-order in {1,7,9,10} then x:final-angle = x:angle
	public RuleFinalAngle8(Frame f, String x)
	{
		super(f.getObjectLabel()+"_RuleFinalAngle8",new IfEqual(f.getObjectLabel()+"_"+SLOT.rotationalSymmetryOrder.str(), new int[] {1,7,9,10}), new ThenSet(f.getObjectLabel()+"_"+SLOT.finalAngle.str(), f.getObjectLabel()+"_"+SLOT.angle.str()) );
	}
}

class RuleFinalAngle9 extends Rule
{
	// if x:shape=circle and y:shape=circle then x:final-angle = 0, y:final-angle = 0
	public RuleFinalAngle9(Frame f1, Frame f2, String x, String y)
	{
		super(x+"_RuleFinalAngle9", new OpAnd (new IfEqual(x+"_"+SLOT.shape.str(),1), new IfEqual(y+"_"+SLOT.shape.str(),1) ), new ThenSetMulti( new Consequence[] 
				                     																	{new ThenSet(x+"_"+SLOT.finalAngle.str(),0),new ThenSet(y+"_"+SLOT.finalAngle.str(),0)} ));
	}
}

class RuleFinalAngle10 extends Rule
{
	// if x:shape=circle and y:shape!=circle then x:final-angle = y:final-angle
	public RuleFinalAngle10(Frame f1, Frame f2, String x, String y)
	{
		super(x+"_"+y+"_RuleFinalAngle10", new OpAnd (new IfEqual(x+"_"+SLOT.shape.str(),1), new IfNotEqual(y+"_"+SLOT.shape.str(),1) ), new ThenSet(x+"_"+SLOT.finalAngle.str(), y+"_"+SLOT.angle.str()));
																												
	}
}

class RuleVerticalFlip1 extends Rule
{	

	// if x:shape=right-triangle and x:final-angle = 90 and y:vertical-flip = 1 then x:vertical-flip = 1
	public RuleVerticalFlip1(Frame f1, Frame f2, String x , String y)
	{

		super(x+"_"+y+"_RuleVerticalFlip1", new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+"_"+SLOT.shape.str(),5),new IfEqual(x+"_"+SLOT.finalAngle.str(),2), new IfEqual(y+"_"+SLOT.verticalFlip,1) } 
				), new ThenSet(x+"_"+SLOT.verticalFlip.str(),1));
	}
}

class RuleVerticalFlip2 extends Rule
{
	// if x:vertical-symmetry = 1 and x:final-angle = 180 and y:vertical-flip = 1 then x:vertical-flip = 1
	public RuleVerticalFlip2(Frame f1, Frame f2, String x, String y)
	{

		super(x+"_"+y+"_RuleVerticalFlip2", new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+"_"+SLOT.verticalSymmetry.str(),1),new IfEqual(x+"_"+SLOT.finalAngle.str(),4), new IfEqual(y+"_"+SLOT.verticalFlip,1) } 
				), new ThenSet(x+"_"+SLOT.verticalFlip.str(),1));		
	}
}

class RuleHorizontalFlip1 extends Rule
{	
	// if x:shape=right-triangle and x:final-angle = 270 and y:horizontal-flip = 1 then x:horizontal-flip = 1
	public RuleHorizontalFlip1(Frame f1, Frame f2, String x, String y)
	{

		super(x+"_"+y+"_RuleHorizontalFlip1", new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+"_"+SLOT.shape.str(),5),new IfEqual(x+"_"+SLOT.finalAngle.str(),7), new IfEqual(y+"_"+SLOT.horizontalFlip,1) } 
				), new ThenSet(x+"_"+SLOT.horizontalFlip.str(),1));		
	}
}

class RuleHorizontalFlip2 extends Rule
{
	//if x:horizontal-symmetry = 1 and x:final-angle = 180 and y:horizontal-flip = 1 then x:horizontal-flip = 1
	public RuleHorizontalFlip2(Frame f1, Frame f2, String x, String y)
	{

		super(x+"_"+y+"_RuleHorizontalFlip2", new OpAnd ( 
				new Antecedent[] 
				{ new IfEqual(x+"_"+SLOT.horizontalSymmetry.str(),1),new IfEqual(x+"_"+SLOT.finalAngle.str(),4), new IfEqual(y +"_"+SLOT.horizontalFlip,1) } 
				), new ThenSet(x +"_"+SLOT.horizontalFlip.str(),1));		
	}
}

class RuleShapeChange1 extends Rule
{
	//if x:shape = n and y:shape = n then x:shapeChanged = 0, y:shapeChanged = 0
	public RuleShapeChange1(Frame f1, Frame f2, String x, String y)
	{

		super(x+"_"+y+"_ShapeChange1", new OpAnd ( 
				new Antecedent[] 
				{new IfEqual (x+"_shape" , f1.slots.get("shape")), new IfEqual(y+"_shape" , f1.slots.get("shape")) } 
				), new ThenSetMulti ( new Consequence[] { new ThenSet (x+"_shapeChanged",0) , new ThenSet (y+"_shapeChanged",0)}) );
	}
}

class RuleShapeChange2 extends Rule
{
	//if x:shape != y:shape then x:shapeChanged = 0, y:shapeChanged = 1
	public RuleShapeChange2(Frame f1, Frame f2, String x, String y)
	{

		super(x+"_"+y+"_ShapeChange2",  new OpNot ( new OpAnd ( new Antecedent[] 
				{new IfEqual (x+"_shape" , f1.slots.get("shape")), new IfEqual(y+"_shape" , f1.slots.get("shape")) } 
				) ), new ThenSetMulti ( new Consequence[] { new ThenSet (x+"_shapeChanged",0) , new ThenSet (y+"_shapeChanged",1)}) );
	}
}


//class RuleRightOf extends Rule
//{
//	// if x:right-of = y then y:left-of = x
//	public RuleRightOf(String x,String y)
//	{
//		super(x+"_"+y+"_RuleRightOf",new IfEqual(x+"_"+SLOT.rightOf.str(),Integer.parseInt(y)), new ThenSet(y+"_"+SLOT.leftOf.str(),Integer.parseInt(x)) );
//	}
//}
//
//class RuleLeftOf extends Rule
//{  
//	// if x:left-of = y then y:right-of = x
//	public RuleLeftOf(String x,String y)
//	{
//		super(x+"_"+y+"_RuleLeftOf", new IfEqual(x+"_"+SLOT.leftOf.str(),Integer.parseInt(y)), new ThenSet(y+"_"+SLOT.rightOf.str(),Integer.parseInt(x)) );
//	}
//}
//
//class RuleAbove extends Rule
//{
//	// if x:above = y then y:below = x
//	public RuleAbove(String x,String y)
//	{
//		super(x+"_"+y+"_RuleAbove", new IfEqual(x+"_"+SLOT.above.str(),Integer.parseInt(y)), new ThenSet(y+"_"+SLOT.below.str(),Integer.parseInt(x)) );
//	}
//}
//
//class RuleBelow extends Rule
//{  
//	// if x:below = y then y:above = x
//	public RuleBelow(String x,String y)
//	{
//		super(x+"_"+y+"_RuleBelow",new IfEqual(x+"_"+SLOT.below.str(),Integer.parseInt(y)), new ThenSet(y+"_"+SLOT.above.str(),Integer.parseInt(x)) );
//	}
//}