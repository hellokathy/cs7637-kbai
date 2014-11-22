package project4;

import java.awt.Graphics2D;
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

	
	public ImageDiffAnalyser(File file1, File file2) 
	{
    
		try 
		{
			this.img1 = ImageIO.read(file1);
			this.img2 = ImageIO.read(file2);
			
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
		catch (Exception e) 
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
		return p*100;
		
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
		
		return (1 - VectorExt.getCosineSimilarity(v1, v2))*100;
	}
	
	public double getEuclidianDiff()
	// returns difference between two images in terms of Euclidean Difference
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
		
		return VectorExt.getEuclidianDist(v1, v2);
	}
	
	public static BufferedImage convertToGrayscale(BufferedImage source) 
	{ 
	     BufferedImageOp op = new ColorConvertOp(
	       ColorSpace.getInstance(ColorSpace.CS_GRAY), null); 
	     return op.filter(source, null);
	}
	
	public static BufferedImage convertToBW(File source) 
	{ 
        try
        {
            //colored image path
            BufferedImage image = ImageIO.read(source);
            
        	//getting width and height of image
            double image_width = image.getWidth();
            double image_height = image.getHeight();

            BufferedImage bimg = null;
            BufferedImage img = image;

            //drawing a new image      
            bimg = new BufferedImage((int)image_width, (int)image_height, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D gg = bimg.createGraphics();
            gg.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

            //saving black and white image onto drive
            String temp = "_bw";
            File fi = new File(source.getPath()+temp);
            ImageIO.write(bimg, "png", fi);
            
            BufferedImage retimg = ImageIO.read(fi);
            return retimg;
        }
        catch (Exception e)
        {
        	System.out.println(e);
        	return null;
        }
	}
}
