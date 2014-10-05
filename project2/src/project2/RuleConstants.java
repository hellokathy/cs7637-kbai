package project2;

public class RuleConstants 
{
	public static final String RULE_FIRED_PREFIX = "fired-"; 
	
	public enum SLOT {
		rotationalSymmetryOrder,
		angle,
		leftOf,
		rightOf,
		above,
		below,
		overlaps,
		numSides,
		size,
		horizontalSymmetry,
		verticalSymmetry,
		finalAngle,
		horizontalFlip,
		verticalFlip,
		fill,
		shape,
		aboveChanged,
		belowChanged,
		shapeChanged;
		
		public String str()
		{
			return this.toString();
		}
	
	}
}
