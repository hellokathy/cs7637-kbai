package project4;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class ImageDiffAnalyser 
{

	BufferedImage img1 = null;
	BufferedImage img2 = null;
	URL url1 = null;
	URL url2 = null;
	
	public ImageDiffAnalyser(URL url1, URL url2) 
	{
    
		try 
		{
			this.url1 = url1;
			this.url2 = url2;
			this.img1 = ImageIO.read(url1);
			this.img2 = ImageIO.read(url2);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public double getDiffPerc()
	{
		int width1 = img1.getWidth(null);
		int width2 = img2.getWidth(null);
		int height1 = img1.getHeight(null);
		int height2 = img2.getHeight(null);
	
		if ((width1 != width2) || (height1 != height2)) 
		{
			System.err.println("Error: Images dimensions mismatch");
			System.exit(1);
		}
	
		long diff = 0;
	
		for (int i = 0; i < height1; i++) 
		{
			for (int j = 0; j < width1; j++) 
			{
				int rgb1 = img1.getRGB(i, j);
				int rgb2 = img2.getRGB(i, j);
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >>  8) & 0xff;
				int b1 = (rgb1      ) & 0xff;
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >>  8) & 0xff;
				int b2 = (rgb2      ) & 0xff;
				diff += Math.abs(r1 - r2);
				diff += Math.abs(g1 - g2);
				diff += Math.abs(b1 - b2);
			}	
		}
		double n = width1 * height1 * 3;
		double p = diff / n / 255.0;
		return (p * 100.0);
	  
	}
}
