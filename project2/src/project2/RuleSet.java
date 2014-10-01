package project2;

import java.util.ArrayList;
import java.util.List;

public class RuleSet
{
	private final List<Rule> rules = new ArrayList<Rule>();

	public RuleSet(Rule... rules)
	{
		for (Rule rule : rules)
		{
			this.rules.add(rule);
		}
	}
	
	public RuleSet(List<Rule> rules)
	{
		this.rules.addAll(rules);
	}
	
	public RuleSet(Rule rules)
	{
		this.rules.add(rules);
	}
	
	public Iterable<Rule> getRules()
	{
		return rules;
	}
}
