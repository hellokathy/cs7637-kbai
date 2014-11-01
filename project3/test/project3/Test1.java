package project3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class Test1 {

	@Test
	public void test_CheckForArithmeticSeries_1() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(45);
		list.add(90);
		CheckForArithmeticSeries chkArithSeries = new CheckForArithmeticSeries(list);
		assertEquals (chkArithSeries.getConstant(),45);
	}

	@Test
	public void test_CheckForArithmeticSeries_2() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(1);		
		CheckForArithmeticSeries chkArithSeries = new CheckForArithmeticSeries(list);
		assertFalse (chkArithSeries.isArithmeticSeries());
	}

	@Test
	public void test_CheckForArithmeticSeries_3() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(45);
		list.add(90);	
		List<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(270);
		listOf2.add(315);
		CheckForArithmeticSeries chkArithSeries = new CheckForArithmeticSeries(list);
		assertEquals(chkArithSeries.getMissingValue(listOf2),360);
	}

	@Test
	public void test_CheckForArithmeticSeries_4() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(45);
		list.add(90);	
		List<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(270);
		listOf2.add(315);
		CheckForArithmeticSeries chkArithSeries = new CheckForArithmeticSeries(list);
		assertEquals(chkArithSeries.getMissingValue(listOf2),Const.NEGATIVE_INFINITY);
	}

	@Test
	public void test_CheckForBinaryOp_1() {
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(4);
		list1.add(5);
		list1.add(9);	
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(10);
		list2.add(20);
		list2.add(30);

		CheckForBinaryOp chk1 = new CheckForBinaryOp(list1);
		CheckForBinaryOp chk2 = new CheckForBinaryOp(list2);
		
		assertTrue(chk1.isBinaryOpAddition() && chk2.isBinaryOpAddition());
	}

	@Test
	public void test_CheckForBinaryOp_2() {
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(4);
		list1.add(2);
		list1.add(8);	

		CheckForBinaryOp chk1 = new CheckForBinaryOp(list1);
		
		assertTrue(chk1.isBinaryOpMultiplication());
	}
	
	@Test
	public void test_CheckForGeometricSeries_1() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(45);
		list.add(90);		
		CheckForGeometricSeries chkGeomSeries = new CheckForGeometricSeries(list);
		assertFalse (chkGeomSeries.isGeometricSeries());
	}

	@Test
	public void test_CheckForGeometricSeries_2() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(4);
		list.add(8);			
		CheckForGeometricSeries chkGeomSeries = new CheckForGeometricSeries(list);
		assertTrue (Double.compare(chkGeomSeries.getConstant(), 2) == 0 );
	}
	
	@Test
	public void test_CheckForGeometricSeries_3() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(8);
		list.add(4);
		list.add(2);			
		CheckForGeometricSeries chkGeomSeries = new CheckForGeometricSeries(list);
		System.out.println("Returned "+chkGeomSeries.getConstant());
		assertTrue (Double.compare(chkGeomSeries.getConstant(), 0.5) == 0 );
	}
	
	@Test
	public void test_CheckForDistributionOf3Values_1() {
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(4);
		list1.add(6);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(4);
		list2.add(6);
		list2.add(1);
		
		ArrayList<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(6);
		listOf2.add(1);
		
		CheckForDistributionOf3Values chk = new CheckForDistributionOf3Values(list1, list2, listOf2);
		System.out.println("Returned "+chk.getMissingValue());
		assertEquals (chk.getMissingValue(), 4);
	}
	
	@Test
	public void test_CheckForDistributionOf3Values_2() {
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(4);
		list1.add(6);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(5);
		list2.add(1);
		list2.add(6);
		
		ArrayList<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(6);
		listOf2.add(1);
		
		CheckForDistributionOf3Values chk = new CheckForDistributionOf3Values(list1, list2, listOf2);
		System.out.println("Returned "+chk.getMissingValue());
		assertEquals (chk.getMissingValue(), Const.NEGATIVE_INFINITY);
	}
	
	@Test
	public void test_CheckForDistributionOf2Values_1() {
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(4);
		list1.add(4);
		list1.add(6);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(4);
		list2.add(6);
		list2.add(4);
		
		ArrayList<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(6);
		listOf2.add(4);
		
		CheckForDistributionOf2Values chk = new CheckForDistributionOf2Values(list1, list2, listOf2);
		System.out.println("Returned "+chk.getMissingValue());
		assertEquals (chk.getMissingValue(), 4);
	}

	@Test
	public void test_CheckForDistributionOf2Values_2() {
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(6);
		list1.add(4);
		list1.add(4);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(4);
		list2.add(6);
		list2.add(4);
		
		ArrayList<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(4);
		listOf2.add(4);
		
		CheckForDistributionOf2Values chk = new CheckForDistributionOf2Values(list1, list2, listOf2);
		System.out.println("Returned "+chk.getMissingValue());
		assertEquals (chk.getMissingValue(), 6);
	}
	
	@Test
	public void test_CheckForDistributionOf2Values_3() {
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(4);
		list1.add(4);
		list1.add(6);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(6);
		list2.add(4);
		list2.add(4);
		
		ArrayList<Integer> listOf2 = new ArrayList<Integer>();
		listOf2.add(6);
		listOf2.add(4);
		
		CheckForDistributionOf2Values chk = new CheckForDistributionOf2Values(list1, list2, listOf2);
		System.out.println("Returned "+chk.getMissingValue());
		assertEquals (chk.getMissingValue(), Const.NEGATIVE_INFINITY);
	}
	
}

