package project2;

public class ThenSet implements Consequence
{
	private final int value;
	private final String consequenceExpr;
	
	public ThenSet(String consequenceExpr, int value)
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
