package project4;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageDiffAnalyser 
{

	BufferedImage img1 = null;
	BufferedImage img2 = null;

	int width1 = 0;
	int width2 = 0;
	int height1 = 0;
	int height2 = 0;
	int length = 0;

	
	public ImageDiffAnalyser(File url1, File url2) 
	{
    
		try 
		{
			this.img1 = convertToBW(ImageIO.read(url1));
			this.img2 = convertToBW(ImageIO.read(url2));
			
			this.width1 = img1.getWidth(null);
			this.width2 = img2.getWidth(null);
			this.height1 = img1.getHeight(null);
			this.height2 = img2.getHeight(null);
			
			if ((this.width1 != this.width2) || (this.height1 != this.height2)) 
			{
				System.err.println("Warning: Images dimensions mismatch");
			}
			
			if ((this.width1 == 0)) 
			{
				System.err.println("Warning: Image1 has zero width");
			}

			if ((this.width2 == 0)) 
			{
				System.err.println("Warning: Image2 has zero width");
			}
			
			this.length = this.height1 * this.width1;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public double getRGBPercDiff()
	// returns difference between two images in terms of % of RGB values
	{

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
	
	public double getCosDiff()
	// returns the difference between two images in terms of Cosine Difference (treating images as vectors)
	{
		VectorExt<Double> v1 = new VectorExt<Double>();
		VectorExt<Double> v2 = new VectorExt<Double>();
		
		for (int i = 0 ; i<this.height1 ; i++)
		{
			for (int j = 0; j<this.width1; j++) 
			{
				v1.add((double) this.img1.getRGB(i,j));
				v2.add((double) this.img2.getRGB(i,j));
			}
		}
		
		return VectorExt.getCosineSimilarity(v1, v2);
	}
	
	public static BufferedImage convertToGrayscale(BufferedImage source) 
	{ 
	     BufferedImageOp op = new ColorConvertOp(
	       ColorSpace.getInstance(ColorSpace.CS_GRAY), null); 
	     return op.filter(source, null);
	}
	
	public static BufferedImage convertToBW(BufferedImage source) 
	{ 
		 BufferedImage blackAndWhiteImage =
				              new BufferedImage(source.getWidth(null),
				  source.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
		 return blackAndWhiteImage;
	}
}
