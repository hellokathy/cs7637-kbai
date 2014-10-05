package project2;

import java.util.ArrayList;
import java.util.List;


public class OpNot implements Antecedent
{
	Antecedent  antecedent = null;

	public OpNot(Antecedent a)
	{
		this.antecedent = a;
	}

	@Override
	public boolean applies(Context context)
	{
		if (this.antecedent.applies(context) )
		{
			return false;
		} else
		{
			return true;
		}
		
	}
}
