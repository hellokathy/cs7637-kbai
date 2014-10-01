package project2;

import java.util.ArrayList;
import java.util.List;


public class AndAntecedent implements Antecedent
{
	private final List<Antecedent>  antecedents = new ArrayList<Antecedent>();

	public AndAntecedent(Antecedent e1, Antecedent e2)
	{
		this.antecedents.add(e1);
		this.antecedents.add(e2);
	}

	public AndAntecedent(List<Antecedent> antecedents)
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
