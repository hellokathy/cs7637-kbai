package package1;

public class NameValuePair 
{

	public String name;
	public String value;
	public int valueInt;
	
	public NameValuePair(String _name, String _value)
	{
		this.name = _name;
		this.value = _value;
		
		if (Util.isNumeric(_value))
		{
			valueInt = Integer.parseInt(_value);
		} else 
		{
			valueInt = -999999;
		}
			
	}

	public NameValuePair(String _name, Integer _value)
	{
		this.name = _name;
		this.valueInt = _value;
		
		this.value = String.valueOf(_value);
			
	}
	
}
