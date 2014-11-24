package project4;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;





import project4.ImageDiffAnalyser;

public class testImageDiffAnalyser {


	
	@Test
	public void testImageDiffAnalyser_1()
	{
		
		File url1 = null;
		File url2 = null;
		try 
		{
			url1 = new File("testimages/cir_fill_no.png");
			url2 = new File("testimages/cir_fill_nocopy.png");
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ImageDiffAnalyser analyser = new ImageDiffAnalyser(url1, url2);
		System.out.println(url1.getPath());
		System.out.println(url2.getPath());
		
		System.out.println("% diff : "+analyser.getRGBPercDiff());
		System.out.println("Cos diff : "+analyser.getCosDiff());
		System.out.println("Ecl diff : "+analyser.getEuclidianDiff());
		assertTrue(analyser.getCosDiff() > 90);
		
	
	}

}

