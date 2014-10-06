package project2;

import static org.junit.Assert.*;
import org.junit.Test;

import project2.Math2;

public class testMath2 {

	@Test
	public void test1() {
		assertEquals (Math2.isOdd(3),true);
	}

	@Test
	public void test2() {
		assertEquals (Math2.isOdd(0),false);
	}
	
	@Test
	public void test3() {
		assertEquals (Math2.isOdd(1),true);
	}
	
	@Test
	public void test4() {
		assertEquals (Math2.isOdd(2),false);
	}

	@Test
	public void test5() {
		assertEquals (Math2.nextOddInt(0),1);
	}

	@Test
	public void test6() {
		assertEquals (Math2.nextOddInt(1),3);
	}
	
	@Test
	public void test7() {
		assertEquals (Math2.nextOddInt(2),true);
	}
	
	@Test
	public void test8() {
		assertEquals (Math2.isOdd(11),true);
	}
	
	@Test
	public void test9() {
		assertEquals (Math2.isOdd(11),true);
	}
}

