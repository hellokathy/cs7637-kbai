package project2;

import java.util.ArrayList;
import java.util.List;

public class AntecedentAngle implements Antecedent
{
	public List<Integer> angles = new ArrayList<Integer>();

	public AntecedentAngle(int[] angles)
	{
		for (int angle : angles)
		{
			this.angles.add(angle);
		}
	}

	public AntecedentAngle(List<Integer> angles)
	{
		this.angles.addAll(angles);
	}

	@Override
	public boolean applies(Context context)
	{
		final Object angle = context.get("x:angle");

		if (angle == null)
		{
			return false;
		}

		// allow integers as strings for the lazy people
		if (angle instanceof String)
		{
			int value = Integer.parseInt((String)angle);
			return angles.contains(value);
		}

		// add other type support here

		// do not bother with any other type you do not expect
		if (!(angle instanceof Integer))
		{
			return false;
		}

		return angles.contains(angle);
	}
}
