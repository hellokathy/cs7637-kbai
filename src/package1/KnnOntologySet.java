package package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

class Ontology {
	/* <K,V> list of pairs where K is an attribute value and V is a similarity value
	 * 
	 */
	private HashMap<String,Integer> ontology; // 

	public Ontology() {
		this.ontology = new HashMap<String,Integer>();
	}
	
	public void addMapping(String attributeValue, int similarityValue){
		this.ontology.put(attributeValue, similarityValue);
	}
	
	public int getMapping(String attributeValue){
		return this.ontology.get(attributeValue);
	}
}

public class KnnOntologySet {

	/* An list of ontologies keyed by attribute name
	 * 
	 */
	private HashMap<String,Ontology> ontologySet;

	// constructor
	public KnnOntologySet() throws Exception {
		this.ontologySet = new HashMap<String,Ontology>();
		loadOntologies();
	}
	
	// getters and setters
	public void add(String attributeName, String attributeValue, Integer similarityValue){
		/* check ontologyList to see if ontology already exists for given attributeName
		 * if not create new ontology and add to set
		 */
		Ontology o = ontologySet.get(attributeName);
		
		if (o == null) {
			// create new ontology and add to set
			o = new Ontology();
			ontologySet.put(attributeName, o);
		}
			
		o.addMapping(attributeValue, similarityValue);	
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
    				add(contents[0], contents[1], Integer.parseInt(contents[2]));
    			}
    		}
    	} catch (Exception e){
    		System.out.println("Please provide ontologies.txt file in program directory and ensure file is readable.");
    		// rethrow error
    		throw e;
    	}
    	
    	return true;
    }
}
