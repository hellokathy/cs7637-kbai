package project2;

import java.util.ArrayList;

public class Vector {

	
	private ArrayList<Double> elements = null;

	public Vector() 
	{
		this.elements = new ArrayList<Double>();
	}
	
	@Override 
	public String toString()
	{
		return elements.toString();
	}
	
	/**
	 * @return the list
	 */
	public ArrayList<Double> getElements() {
		return elements;
	}

	/**
	 * @param list the list to set
	 */
	public void setElements(ArrayList<Double> elements) {
		this.elements = elements;
	}
	
	public void add(Double d)
	{
		elements.add(d);
	}

	public Double get(int i)
	{
		return elements.get(i);
	}
	
	public int size()
	{
		return elements.size();
	}
	
	public Vector getDiff(Vector v)
	{
		Vector diffVector = new Vector();
		
		// find out which vector is longer
		int maxSize = v.size();
		int diff = Math.abs(v.size() - this.size());
		if (this.size() > maxSize) maxSize = this.size();
		
		if (v.size() < maxSize)
			for (int i = 0 ; i < diff ; i++)
				v.add(0.0);
		
		if (this.size() < maxSize)
			for (int i = 0 ; i < diff ; i++)
				this.add(0.0);

		for (int i = 0 ; i < this.size() ; i++)
		{
			// work out diffVector
			diffVector.add( Math.abs( this.get(i) - v.get(i)) );
		}
		
		return diffVector;
	}
	
	public double getSum()
	{
		double sum = 0;
		
		for (int i = 0 ; i<this.size() ; i++ )
		{
			sum+=this.get(i);
		}
		return sum;
	}
			
}
