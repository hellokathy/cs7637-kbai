package project2;

import java.util.ArrayList;
import java.util.List;

public class ThenSetMulti implements Consequence
{
	
	private List<Consequence>  consequences = null;
	
	
	public ThenSetMulti(Consequence[] consequences)
	{
		this.consequences = new ArrayList<Consequence>();
		
		for (Consequence c : consequences)
			this.consequences.add(c);
	}
	
	@Override
	public void execute(Context context)
	{
		for (Consequence c : this.consequences)
		{
			c.execute(context);
		}
	
	}		

}