package project2;

public class ConsequenceExpr implements Consequence
{
	private final int value;
	private final String consequenceExpr;
	
	public ConsequenceExpr(String consequenceExpr, int value)
	{
		this.value = value;
		this.consequenceExpr = consequenceExpr;
	}
	
	@Override
	public void execute(Context context)
	{
		context.set(consequenceExpr, value);
	}		

}
