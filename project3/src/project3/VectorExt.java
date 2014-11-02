package project3;

import java.util.Vector;

public class VectorExt<E> extends Vector<E> {

	public static double cosineSimilarity(Vector<Double> vectorA, Vector<Double> vectorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < vectorA.size(); i++) {
	        dotProduct += vectorA.get(i) * vectorB.get(i);
	        normA += Math.pow(vectorA.get(i), 2);
	        normB += Math.pow(vectorB.get(i), 2);
	    }   
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	public double getSum()
	{
		double sum = 0.0;

		try 
		{
			for (int i = 0 ; i < this.size() ; i++)
			{
				sum = sum + (double)this.get(i);
			}
			
			if (Double.isNaN(sum)) sum = 0;
			return sum;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
