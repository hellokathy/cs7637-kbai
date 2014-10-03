package project2;

public class RuleConstants 
{
	public static final String RULE_FIRED_PREFIX = "fired-"; 
	
	public enum var {
		_rotationalSymmetryOrder,
		_angle,
		_leftOf,
		_rightOf,
		_above,
		_below,		
		_horizontalSymmetry,
		_verticalSymmetry,
		_finalAngle,
		_horizontalFlip,
		_verticalFlip,
		_shape;
		
		public String str()
		{
			return this.toString();
		}
		
	}
}
