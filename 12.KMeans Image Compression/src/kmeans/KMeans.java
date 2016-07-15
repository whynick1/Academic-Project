package kmeans;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
 
public class KMeans {

	private final static int ITERATIONS = 20;
	
	public static void main(String [] args)
	{
		if (args.length != 1)
		{
			System.out.println("Input format should be: Java -jar Kmeans.jar <K>");
			return;
		}
		String inputImageFilePath1 = "Koala.jpg";
		String inputImageFilePath2 = "Penguins.jpg";
		String K = args[0];
		String outputImageFilePath1 = "K=" + K + "_Koala.jpg";
		String outputImageFilePath2 = "K=" + K + "_Penguins.jpg";
		
		try{
			BufferedImage originalImage1 = ImageIO.read(new File(inputImageFilePath1));
			int k=Integer.parseInt(K);
			BufferedImage kmeansJpg1 = useKMeans(originalImage1,k);
			ImageIO.write(kmeansJpg1, "jpg", new File(outputImageFilePath1));
			
			BufferedImage originalImage2 = ImageIO.read(new File(inputImageFilePath2));
			k=Integer.parseInt(K);
			BufferedImage kmeansJpg2 = useKMeans(originalImage2,k);
			ImageIO.write(kmeansJpg2, "jpg", new File(outputImageFilePath2));
	
			System.out.println("Done!");
			
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
    }
    
    private static BufferedImage useKMeans(BufferedImage originalImage, int k)
	{
		int w=originalImage.getWidth();
		int h=originalImage.getHeight();
		BufferedImage kmeansImage = new BufferedImage(w, h, originalImage.getType());
		Graphics2D g = kmeansImage.createGraphics();
		
		g.drawImage(originalImage, 0, 0, w, h, null);
		
		int[] rgb = new int[w*h];
		int count=0;
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				rgb[count] = kmeansImage.getRGB(i,j);
				count++;
			}
		}
		
		//update the rgb values
		kmeans(rgb,k);

		//write the new rgb values back to the image
		count=0;
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				kmeansImage.setRGB(i, j, rgb[count++]); 
			}
		}
		return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb
    private static void kmeans(int[] rgb, int k)
    {
    	int len = rgb.length;
    	int[] r = new int[len];
    	int[] g = new int[len];
    	int[] b = new int[len];
    	
		for(int i=0; i<len; i++) {
			r[i] = (rgb[i]>>16) & 0xff;	//(R)：rgb(255,0,0)
			g[i] = (rgb[i]>>8) & 0xff;	//(G)：rgb(0,255,0)
			b[i] = (rgb[i]) & 0xff;		//(B)：rgb(0,0,255)
		}
		
		int[] init = initClusterCenter(k, len);
		int[][] K_Center = new int[k][3];//R, G, B channel
		for(int j=0; j<k; j++) {
			K_Center[j][0] = r[init[j]];
			K_Center[j][1] = g[init[j]];
			K_Center[j][2] = b[init[j]];
		}

		// Repeat until convergence
		int[] dis= new int[len]; //distance
		int[] ndx = new int[len]; //index of cluster class of a Pixel
		int tmpDis;

		for(int m=0; m<ITERATIONS; m++)
		{
			//set the distance for each pixel to cluster class k = 0
			for(int j=0; j<len; j++) {
				dis[j]=(int)Math.abs(K_Center[0][0]-r[j]) + Math.abs(K_Center[0][1]-g[j]) + Math.abs(K_Center[0][2]-b[j]);
				ndx[j] = 0;
			}
			//update index for each pixel (find new minimum distance)
			for(int i=1; i<k; i++) {
				for(int j=0; j<len; j++) {
					tmpDis = (int)Math.abs(K_Center[i][0]-r[j]) + Math.abs(K_Center[i][1]-g[j]) + Math.abs(K_Center[i][2]-b[j]);
					if(tmpDis < dis[j]) {
						dis[j] = tmpDis;	
						ndx[j] = i;		
					}
				}
			}
			
			//find new cluster center
			int[][] ValueSUM = new int[k][3]; 
			int[] NumSUM = new int[k]; 
	
			for(int j=0; j<len; j++) {
				ValueSUM[ndx[j]][0] += r[j];	
				ValueSUM[ndx[j]][1] += g[j];	
				ValueSUM[ndx[j]][2] += b[j];	
				NumSUM[ndx[j]] += 1;
			}
			for(int i=1; i<k; i++) {
				K_Center[i][0] = (int) Math.rint( (float) ValueSUM[i][0] / NumSUM[i]);
				K_Center[i][1] = (int) Math.rint( (float) ValueSUM[i][1] / NumSUM[i]);
				K_Center[i][2] = (int) Math.rint( (float) ValueSUM[i][2] / NumSUM[i]);
			}
		}
		
		//update r, g, b array
		for(int j=0; j<len; j++) {
			r[j] = K_Center[ndx[j]][0];
			g[j] = K_Center[ndx[j]][1];
			b[j] = K_Center[ndx[j]][2];
		}
		for(int i=0; i<len; i++) {
			rgb[i] = ((r[i]&0x0ff)<<16)|((g[i]&0x0ff)<<8)|(b[i]&0x0ff);
		}
    }

    //initiate cluster center
    public static int[] initClusterCenter(int k, int length)
    {
		int[] K_Center = new int[k];
		for(int i=0; i<k; i++)
		{
			Random rand = new Random();
			int min = 0;
			int max = length;
			K_Center[i] = rand.nextInt(max - min + 1) + min;
		}
    	return K_Center;
    }
}