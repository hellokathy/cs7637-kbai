package project2;

import java.util.ArrayList;
import java.util.List;


public class OpAnd implements Antecedent
{
	private final List<Antecedent>  antecedents = new ArrayList<Antecedent>();

	public OpAnd(Antecedent e1, Antecedent e2)
	{
		this.antecedents.add(e1);
		this.antecedents.add(e2);
	}

	public OpAnd(List<Antecedent> antecedents)
	{
		this.antecedents.addAll( antecedents);
	}

	@Override
	public boolean applies(Context context)
	{
		for (Antecedent e : antecedents)
		{
			if (!e.applies(context))
			{
				return false;
			}
		}

		return true;
	}
}
