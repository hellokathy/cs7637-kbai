package package1;

import java.util.HashMap;

class Ontology {
	/* <K,V> list of pairs where K is an attribute value and V is a similarity value
	 * 
	 */
	private HashMap<String,Integer> ontology; // 

	public Ontology() {
		this.ontology = new HashMap<String,Integer>();
	}
	
	public int addMapping(String attributeValue, int similarityValue){
		return this.ontology.put(attributeValue, similarityValue);
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

	public KnnOntologySet() {
		this.ontologySet = new HashMap<String,Ontology>();
	}

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
	
}
