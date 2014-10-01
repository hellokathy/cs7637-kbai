package project2;

import java.util.ArrayList;
import java.util.List;

public class OpOr implements Antecedent
{
	private final List<Antecedent> antecedents = new ArrayList<Antecedent>();

	public OpOr(Antecedent e1, Antecedent e2)
	{
		this.antecedents.add(e1);
		this.antecedents.add(e2);		
	}
	
	public OpOr(List<Antecedent> antecedents)
	{
		this.antecedents.addAll(antecedents);
	}

	@Override
	public boolean applies(Context context)
	{
		for (Antecedent e : antecedents)
		{
			if (e.applies(context))
			{
				return true;
			}
		}
		
		return false;
	}
}
