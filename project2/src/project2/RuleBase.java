package project2;

import project2.RuleConstants.var;

public class RuleBase 
{

}

class RuleRotationalSymmetryOrder1 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {0,90,180,270} then x:final-angle = 0
	public RuleRotationalSymmetryOrder1()
	{
			super("rule-rotational-symmetry-order-1", new OpAnd(new IfEqual(var.x_rotationalSymmetryOrder.str(),4),new IfEqual(var.x_angle.str(),new int[] {1,3,5,7} )), new ThenSet(var.x_finalAngle.str(),1) );
	}
}

class RuleRotationalSymmetryOrder2 extends Rule
{
	// if x:rotational-symmetry-order = 4 and x:angle in {45,135,225,315} then x:final-angle = 45
	public RuleRotationalSymmetryOrder2()
	{
			super("rule-rotational-sym-order-2", new OpAnd(new IfEqual(var.x_rotationalSymmetryOrder.str(),4),new IfEqual(var.x_angle.str(),new int[] {1,3,5,7} )), new ThenSet(var.x_finalAngle.str(),0) );
	}
}

class RuleRotationalSymmetryOrder3  extends Rule
{
	// if x:rotational-symmetry-order = 2 and x:angle in {0,180} then x:final-angle = 0
	public RuleRotationalSymmetryOrder3()
	{
			super("rule-rotational-sym-order-3", new OpAnd(new IfEqual(var.x_rotationalSymmetryOrder.str(),2),new IfEqual(var.x_angle.str(),new int[] {1,3,5,7} )), new ThenSet(var.x_finalAngle.str(),0) );
	}
}

class Rul extends Rule
{
// if x:rotational-symmetry-order = 2 and x:angle in {90,270} then x:final-angle = 90
// if x:rotational-symmetry-order = 2 and x:angle in {45,225} then x:final-angle = 45
// if x:rotational-symmetry-order = 2 and x:angle in {135,315} then x:final-angle = 135
// if x:rotational-symmetry-order = 8 and x:angle in {0,90,180,270,45,135,225,315} then x:final-angle = 0

// if x:rotational-symmetry-order = 999 then x:final-angle = y:angle
// if x:rotational-symmetry-order not in {4,2,8,999} then x:final-angle = x:angle

// if x:shape=right-triangle and x:final-angle = 90 and y:vertical-flip = 1 then x:vertical-flip = 1
// if x:shape=right-triangle and x:final-angle = 270 and y:horizontal-flip = 1 then x:horizontal-flip = 1
  
// if x:right-of = y then y:left-of = x
// if x:left-of = y then y:right-of = x

// if x:above = y then y:below = x
// if x:below = y then y:above = x

// if x:horizontal-symmetry = 1 and x:final-angle = 180 and y:horizontal-flip = 1 then x:horizontal-flip = 1
// if x:vertical-symmetry = 1 and x:final-angle = 180 and y:vertical-flip = 1 then x:vertical-flip = 1