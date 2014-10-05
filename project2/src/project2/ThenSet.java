package project2;

public class ThenSet implements Consequence
{
	private int value;
	private String consequenceKey1 = null;
	private String consequenceKey2 = null;
		
	public ThenSet(String consequenceKey, int value)
	{
		this.value = value;
		this.consequenceKey1 = consequenceKey;
	}

	public ThenSet(String consequenceKey1, String consequenceKey2)
	{
		this.consequenceKey1 = consequenceKey1;
		this.consequenceKey2 = consequenceKey2;
	}
	
	@Override
	public void execute(Context context)
	{

		if (this.consequenceKey2 != null)	
			this.value = (Integer)context.get(this.consequenceKey2);
				
		context.set(consequenceKey1, value);
	
	}		

}
