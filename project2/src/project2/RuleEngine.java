package project2;

public class RuleEngine
{
	public void engage(RuleSet ruleSet, Context context)
	{
		int ruleCount;
		
		do 
		{
			ruleCount = 0;
			
			for (Rule rule : ruleSet.getRules())
			{
				boolean fired = rule.fire(context);
				
				if (fired)
				{
					ruleCount++;
				}
			}
		}
		while (ruleCount > 0);

	}
}
