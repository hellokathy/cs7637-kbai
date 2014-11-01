package project3;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class Test2 {

	@Test
	public void test_VectorExt_1() {
		
		VectorExt<Double> vector1 = new VectorExt<Double>();
		vector1.add( (double) 0.4);

		
		VectorExt<Double> vector2 = new VectorExt<Double>();
		vector2.add( (double) 1.0);
	
	
		Double cosDiff = VectorExt.cosineSimilarity(vector1, vector2);
		
		assertEquals (  Double.valueOf(cosDiff), Double.valueOf(1.0) );
	}
}
