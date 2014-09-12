package package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

class Ontology {
	/* Class that stores the lexicon of the RPM problem set. Object characteristics such
	 * as shape, fill, size etc are stored along with their possible values and a similarity 
	 * weight that reflects how similar each value is to adjacent ones.
	 */
	private HashMap<String,Integer> ontology; // 
	
	public Ontology() {
		this.ontology = new HashMap<String,Integer>();
	}
	
	public void addValueMap(String _attributeValue, Integer _similarityValue)
	{
		this.ontology.put(_attributeValue, _similarityValue);
	}
	
	public int getValueMap(String _attributeValue)
	{
		return this.ontology.get(_attributeValue);
	}
	
}

public class KnnOntologySet {

	/* An list of ontologies keyed by attribute name
	 * 
	 */
	private HashMap<String,Ontology> ontologySet;
	private ArrayList<NameValuePair> keyMap;
	
	// constructor
	public KnnOntologySet() throws Exception {
		this.ontologySet = new HashMap<String,Ontology>();
		this.keyMap = new ArrayList<NameValuePair>();

		loadOntologies();
	}
	
	// getters and setters
	public void addSimilarityWeight(String _ontologyKeyName, String _attributeValue, Integer _similarityValue){
		/* check ontologyList to see if ontology already exists for given attributeName
		 * if not create new ontology and add to set
		 */
		Ontology o = this.ontologySet.get(_ontologyKeyName);
		
		if (o == null) {
			// create new ontology and add to set
			o = new Ontology();
			ontologySet.put(_ontologyKeyName, o);
		}
			
		o.addValueMap(_attributeValue, _similarityValue);	
	}
	
	public Integer getSimilarityWeight(String _ontologyKeyName, String _attributeValue)
	{
		/* get similarityWeight mapped to _attributeValue in ontology keyed by _ontologyKeyName
		 * 
		 */
		
		Ontology o = this.ontologySet.get(_ontologyKeyName);
		
		if (o != null)
		{
			return o.getValueMap(_attributeValue);
		} else 
		{
			return 0;
		}
		
		
	}
	
    private boolean loadOntologies() throws Exception{
    	// load from file
    	String fileName = "ontologies.txt";
    	String line = "";
    	String splitChar = ",";
    	BufferedReader br = null;
    	
    	try {
    		br = new BufferedReader(new FileReader(fileName));
    		
    		// populate ontologyset
    		while((line = br.readLine()) != null) {
    			String[] contents = line.split(splitChar);
    			if (line.length()>0) 
    			{
    				// if contents contains 2 elements then it is a record of type
    				// (attribute key name, ontology key name) 
    				// e.g. fill | fill-h
    				if (contents.length == 2)
    				{
    					this.addKeyMap(contents[0], contents[1]);
    				} else
    				{
    					// if contents contains 3 elements then it is a record of type
    					// attribute or ontology key name | attribute value | similiarity value
    					// e.g. (shape, circle, 2)
    					// e.g. (fill-h,bottom-left,3)
    					if (contents.length == 3)
    					{
    	    				this.addSimilarityWeight(contents[0], contents[1], Integer.parseInt(contents[2]));
	
    					}
    				}
    			}
    		}
    	} catch (Exception e){
    		System.out.println("Please provide ontologies.txt file in program directory and ensure file is readable.");
    		// rethrow error
    		throw e;
    	}
    	
    	return true;
    }
    
	public void addKeyMap(String _attributeKeyName, String _ontologyKeyName)
	{
		this.keyMap.add(new NameValuePair(_attributeKeyName, _ontologyKeyName));
	}
	
	public ArrayList<NameValuePair> getKeyMaps(String _attributeKeyName)
	{
		/*  Returns a list of Key maps for which _attributeKeyName
		 *  has a corresponding ontologyKeyName e.g. _attributeKeyName fill
		 *  can map to ontology key names fill-v and fill-h
		 */ 
		
		ArrayList<NameValuePair> retKeyMap = new ArrayList<NameValuePair>();
		
		for (NameValuePair k : this.keyMap) 
		{
			if (k.name.equals(_attributeKeyName)) retKeyMap.add(k);
		}
		
		return retKeyMap;
	}  
	
    public ArrayList<NameValuePair> getSlots(RavensAttribute ra)
    {
    	/* returns a list of ontology key names and similarity weights for a given Raven's attribute
    	 * e.g. fill ->  fill-h | 3
    	 *               fill-v | 4
    	 */       
    	
    	// first check to see whether ravens attribute name has any entries in keyMap
    	Boolean bFound = false;
    	ArrayList<NameValuePair> slots = new ArrayList<NameValuePair>();
    	
    	for (NameValuePair k : keyMap) {
    		if (k.name.equals(ra.getName())) 
    		{
    			// attribute name found in keyMap, get corresponding ontology key name
    			bFound = true;
    			slots.add(new NameValuePair(k.value, this.getSimilarityWeight(k.value, ra.getValue())));
    		}
    	}
    	
    	if (!bFound)
    	{
    		// use the ravens attribute name as the ontology key name
    		slots.add(new NameValuePair(ra.getName(),this.getSimilarityWeight(ra.getName(), ra.getValue())));
    	}
    	
    	return slots;
    }
    

}
