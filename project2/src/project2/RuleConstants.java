package project2;

public class RuleConstants 
{
	public static final String RULE_FIRED_PREFIX = "fired-"; 
	
	public enum var {
		x_rotationalSymmetryOrder,
		y_rotationalSymmetryOrder,
		x_angle,
		y_angle,
		x_leftOf,
		y_leftOf,
		x_rightOf,
		y_rightOf,
		x_above,
		y_above,
		x_below,
		y_below,		
		x_horizontalSymmetry,
		x_finalAngle,
		x_horizontalFlip,
		y_horizontalFlip,
		x_verticalFlip,
		y_verticalFlip,
		x_shape;
		
		public String str()
		{
			return this.toString();
		}
		
	}
}
