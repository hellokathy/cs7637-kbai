package project4;

import static org.junit.Assert.*;

import org.junit.Test;

public class testRoundToNearestMultiple {

	@Test
	public void test1() {
		
		int N = 45;
		int input = 3;
		
		int result = Util.roundToNearestMultipleOfN(input, N);
		
		assertEquals((long)result, (long)0) ;
	}

	@Test
	public void test2() {
		
		int N = 45;
		int input = 5;
		
		int result = Util.roundToNearestMultipleOfN(input, N);
		
		assertEquals((long)result, (long)0) ;
	}
	
	@Test
	public void test3() {
		
		int N = 45;
		int input = 42;
		
		int result = Util.roundToNearestMultipleOfN(input, N);
		
		assertEquals((long)result, (long)45) ;
	}
	
	@Test
	public void test4() {
		
		int N = 45;
		int input = 40;
		
		int result = Util.roundToNearestMultipleOfN(input, N);
		
		assertEquals((long)result, (long)45) ;
	}
	
	@Test
	public void test5() {
		
		int N = 45;
		int input = 47;
		
		int result = Util.roundToNearestMultipleOfN(input, N);
		
		assertEquals((long)result, (long)45) ;
	}
	
}
