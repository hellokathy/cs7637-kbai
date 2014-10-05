package project2;

import static project2.RuleConstants.RULE_FIRED_PREFIX;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Context
{
	private final Map<String, Object> map = new TreeMap<String, Object>();
	
	public Context()
	{
	}
	
	public Context(Map<String, Object> map)
	{
		this.map.putAll(map);
	}
	
	public Object get(String key)
	{
		return map.get(key);
	}
	
	public void onRuleFired(String ruleId)
	{
		map.put(RULE_FIRED_PREFIX + ruleId, Boolean.TRUE);
	}
	
	public void set(String key, Object value)
	{
		map.put(key, value);		
	}

	public void printOn(PrintWriter pw)
	{
		for (Entry<String, Object> entry : map.entrySet())
		{
			pw.println(entry.getKey() + "=" + entry.getValue().toString());
		}		
	}
	
	public Set<Entry<String, Object>> getEntrySet()
	{
		return map.entrySet();
	}
	
	@Override
	public String toString()
	{
		return map.toString();
	}

	public boolean hasFired(String ruleId)
	{
		return map.containsKey(RULE_FIRED_PREFIX + ruleId);
	}
}
