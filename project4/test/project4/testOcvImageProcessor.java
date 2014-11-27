package project4;

import static org.junit.Assert.*;

import org.junit.Test;

public class testOcvImageProcessor {

	@Test
	public void testOcvImageProcessor_1() {

		OcvImageProcessor ocvImgProc = new OcvImageProcessor("testimages/2 3.png");
		ocvImgProc.findRawObjects();
		assertTrue(true);
	}

}
