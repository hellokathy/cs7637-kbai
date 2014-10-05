package project2;

public class NameValuePair 
{

	public String getName() {
		return name;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String _value) {
		this.value = _value;
		
		if (Util.isNumeric(_value))
		{
			valueInt = Integer.parseInt(_value);
		} else 
		{
			valueInt = Const.NEGATIVE_INFINITY;
		}
	}

	public int getValueInt() {
		return valueInt;
	}

	public void setValueInt(int _valueInt) {
		this.valueInt = _valueInt;
		
		value = String.valueOf(_valueInt);
	}

	private String name;
	private String value;
	private int valueInt;
	public String debug;
	
	public NameValuePair(String _name, String _value)
	{
		this.name = _name;
		this.value = _value;
		this.debug = "";
		
		if (Util.isNumeric(_value))
		{
			valueInt = Integer.parseInt(_value);
		} else 
		{
			valueInt = Const.NEGATIVE_INFINITY;
		}
			
	}

	public NameValuePair(String _name, Integer _value)
	{
		this.name = _name;
		this.valueInt = _value;
		
		this.value = String.valueOf(_value);
			
	}
	
	@Override
	public String toString()
	{
		return this.name+this.value;
	}
	
}
