package project2;

import java.util.ArrayList;
import java.util.List;


public class OpAnd implements Antecedent
{
	private final List<Antecedent>  antecedents = new ArrayList<Antecedent>();

	public OpAnd(Antecedent a1, Antecedent a2)
	{
		this.antecedents.add(a1);
		this.antecedents.add(a2);
	}

	public OpAnd(Antecedent[] antecedents)
	{
		for (Antecedent a : antecedents)
			this.antecedents.add(a);
	}

	@Override
	public boolean applies(Context context)
	{
		for (Antecedent a : antecedents)
		{
			if (!a.applies(context))
			{
				return false;
			}
		}

		return true;
	}
}
