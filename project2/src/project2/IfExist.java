package project2;

import java.util.ArrayList;
import java.util.List;

public class IfExist implements Antecedent 
{
	private List<Integer> values = new ArrayList<Integer>();
	private String antecedentKey;
	
	public IfExist(String antecedentKey)
	{
		this.antecedentKey = antecedentKey;
	}
	
	@Override
	public boolean applies(Context context)
	{
		final Object value = context.get(this.antecedentKey);

		if (value == null)
		{
			return false;
		}

		// allow integers as strings 
		if (value instanceof String)
		{
			int valueInt = Integer.parseInt((String)value);
			return values.contains(valueInt);
		}

		// add other type support here

		// do not bother with any other type you do not expect
		if (!(value instanceof Integer))
		{
			return false;
		}

		return values.contains(value);
	}
}

