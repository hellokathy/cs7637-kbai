package project2;

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
		// if _attributeValue is not present in Ontology then add it		
		
		
		if (!ontology.containsKey(_attributeValue))
		{
			// _attributeValue is not present in Ontology so add it
			// get next similarityWeight in the ontology map, increment current max by MIN_SIMILARITY_WEIGHT
			Integer nextSimilarityWeight = 0;		
			nextSimilarityWeight = findMaxValue() + Const.MIN_SIMILARITY_WEIGHT;
			addValueMap(_attributeValue, nextSimilarityWeight);
			
		}
		return this.ontology.get(_attributeValue);
		
	}
	
	public HashMap<String,Integer> getOntology()
	{
		return ontology;
	}
	
    private int findMaxValue()
    {
    	/* iterates through an Ontology and returns the maximum value
    	 * 
    	 */
    	int max = 0;
   		for (int i : ontology.values())
   			if(i > max) max = i;
    	
    	return max;
    }
}

public class OntologySet {

	/* An list of ontologies keyed by attribute name
	 * 
	 */
	
	private HashMap<String,Ontology> ontologySet;
	private ArrayList<NameValuePair> keyMap;
	
	// constructor
	public OntologySet() throws Exception 
	{
		this.ontologySet = new HashMap<String,Ontology>();
		this.keyMap = new ArrayList<NameValuePair>();

		loadOntologies();
	}

	private boolean isSpatialAttribute(String attributeName)
	{
		ArrayList<String> spatials = new ArrayList<String>();
		spatials.add("left-of");
		spatials.add("right-of");
		spatials.add("above");
		spatials.add("below");
		spatials.add("inside");
		spatials.add("overlaps");
		
		return spatials.contains(attributeName.trim());
	}
	
	// getters and setters
	public void addSimilarityValue(String _ontologyKeyName, String _attributeValue, Integer _similarityValue)
	{
		/* check ontologyList to see if ontology already exists for given attributeName
		 * if not create new ontology and add to set
		 */
		Ontology o = this.ontologySet.get(_ontologyKeyName);
		
		if (o == null) 
			// create new ontology and add to set
			o = createNewOntologyInSet(_ontologyKeyName);
			
		o.addValueMap(_attributeValue, _similarityValue);	
	}
	
	public Integer getSimilarityValue(String _ontologyKeyName, String _attributeValue)
	{
		/* get similarityWeight mapped to _attributeValue in ontology keyed by _ontologyKeyName
		 * 
		 */
		
		Ontology o = this.ontologySet.get(_ontologyKeyName);
		
		if (o == null) {
			// Ontology not present for _ontologyKeyName. Create one and add _attributeValue
			// create new ontology and add to set
			o = createNewOntologyInSet(_ontologyKeyName);
			o.addValueMap(_attributeValue, Const.MIN_SIMILARITY_WEIGHT);
		}

		return o.getValueMap(_attributeValue); 
		
	}
	
	private Ontology createNewOntologyInSet(String _ontologyKeyName)
	{
		Ontology o = new Ontology();
		this.ontologySet.put(_ontologyKeyName, o);
		return o;
	}
	
    private boolean loadOntologies() throws Exception
    {
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
    	    				this.addSimilarityValue(contents[0], contents[1], Integer.parseInt(contents[2]));
	
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
			if (k.getName().equals(_attributeKeyName)) retKeyMap.add(k);
		}
		
		return retKeyMap;
	}  
	
    public ArrayList<NameValuePair> getFrameDataSet(RavensAttribute ra, RavensFigureObjectIndex objIdx)
    {
    	/* returns a list of ontology key names and similarity values for a given Raven's attribute
    	 * e.g. fill ->  fill-h | 3
    	 *               fill-v | 4
    	 */       
    	
    	ArrayList<String> attributeValueList = new ArrayList<String>(); // this is the list of attributes extracted 
    																	// from ra.value
    																	// In most cases this array will only
    																	// have one element in it but for some attributes 
    																	// which can have several values on the same line
    																	// e.g. fill, inside etc, each value will be placed
    																	// in a separate element
    	String raName = ra.getName().trim();
    	boolean isSpatial = this.isSpatialAttribute(raName);
    	
    	// first check whether this attribute needs to be split into multiple entries
    	// fill attributes can have multiple comma separated values so need to separate them out
    	// e.g. fill:top-right,bottom-right,bottom-left
    	String[] contents = ra.getValue().split(",");
    	
    	// works for both single values and comma separated values
    	for (String s : contents) 
    		attributeValueList.add(s.trim());
    	
    	// first check to see whether ravens attribute name has any entries in keyMap
    	Boolean bFoundInKeyMap = false;
    	ArrayList<NameValuePair> frameData = new ArrayList<NameValuePair>();
    	
    	for (NameValuePair keyMapPair : keyMap)
    	{
    		if (keyMapPair.getName().equals(raName) )
    		{
    			// attribute name found in keyMap, so use corresponding ontology key name from keyMapPair.value
    			// if mapping is found in keyMap then this attribute should already in the ontologySet
    			bFoundInKeyMap = true;
    			for (String attrVal : attributeValueList)
    			{
    				if (isSpatial) 
    				{ 
    					addToFrameDataSet(frameData, keyMapPair.getValue(), objIdx.get(attrVal) , false);
    				}
    				else 
    				{
    					addToFrameDataSet(frameData, keyMapPair.getValue(), attrVal, true);
    				}
    			}

    		}
    	}
    	
    	if (!bFoundInKeyMap)
    	{
    		// use the ravens attribute name as the ontology key name
    		for (String attrVal : attributeValueList) 
    		{
    			if (isSpatial)
    			{
    				addToFrameDataSet(frameData, raName, objIdx.get(attrVal) , false);
    			} else
    			{
    				addToFrameDataSet(frameData,raName, attrVal ,true );
    			}
    		}
    			
    	}
    	
    	return frameData;
    }
 
    
    private void addToFrameDataSet(ArrayList<NameValuePair> slots, String slotName, String slotFiller, boolean useOntology)
    {	
    	/* Used by getFrameDataSet to build the list of slot names and fillers to be returned
    	 * 
    	 */
    		boolean bSlotAlreadyExists = false;
    		Integer valueOfSlotFiller = 0;
    		
    		if (useOntology) 
    		{
    			valueOfSlotFiller = this.getSimilarityValue(slotName, slotFiller);
    		} else
    		{
    			valueOfSlotFiller = Integer.parseInt(slotFiller);
    		}
    			
    		if (slots.isEmpty())
    		{
    			slots.add(new NameValuePair(slotName, valueOfSlotFiller ));
    	
    		} else 
    		{
				for(NameValuePair p : slots) 
				{
					if (slotName.equals(p.getName()))
					{
						// add similarityValue to existing slot
						bSlotAlreadyExists = true;
						p.setValueInt(p.getValueInt()+ valueOfSlotFiller) ;
					}
				}	
				if (!bSlotAlreadyExists)
					slots.add(new NameValuePair(slotName, valueOfSlotFiller ));
    		}
    		

    }
    

}
