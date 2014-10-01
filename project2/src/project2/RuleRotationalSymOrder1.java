package project2;

public class RuleRotationalSymOrder1 extends Rule
{
	public RuleRotationalSymOrder1()
	{
		super("rule-rotational-sym-order-1", new AndAntecedent(new AntecedentExpr("x:rotational-symmetry-order",4),new AntecedentExpr("x:angle",4)  ), new ConsequenceExpr());
	}
}
