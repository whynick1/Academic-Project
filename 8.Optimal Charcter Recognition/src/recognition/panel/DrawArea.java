package recognition.panel;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import recognition.WritingFrame;
import recognition.service.FeatureService;

public class DrawArea extends JPanel{
	private int width, height;        
	private boolean flagStartWriting;   
	private WritingFrame writingFrame;  
	private DrawArea drawArea = this;
	
	private FeatureService featureService;  
	private Vector<Point> vector;  
	private int fontSize = 20;
	int counts;                             
	
	//Constructor
	public DrawArea(WritingFrame writingFrame, int width, int height){
		super();
		this.width = width;
		this.height = height;
		counts = 0;
		vector = new Vector<Point>();
		this.writingFrame = writingFrame;
		featureService = new FeatureService(this);
		
		this.setSize(width, height);
		this.addAllListener();
		this.setBackground(Color.WHITE);
	}
	
	public int[] getImageRGBs() throws IOException{
		int[] rgbs = new int[width * height];
		try{
			Robot robot = new Robot();
			Rectangle rec = new Rectangle(this.getLocationOnScreen().x, this.getLocationOnScreen().y, width, height);
			BufferedImage image = robot.createScreenCapture(rec);
			rgbs = image.getRGB(0, 0, width, height, rgbs, 0, width);  
		}catch(AWTException e){
			e.printStackTrace();
		}
		return rgbs;
	}
	
	//overload
	public int[] getImageRGBs(int x, int y, int boxWidth, int boxHeight) throws IOException{
		int[] rgbs;
		int[] compressRGBs = new int[128];
		int complement;
		try{
			Robot robot = new Robot();
			Rectangle rec;
			//wrap character with a box
			if (boxWidth * 2 > boxHeight)//too fat
			{
				complement = (boxWidth * 2 - boxHeight) / 2;
				boxHeight += complement * 2;
				rec = new Rectangle(this.getLocationOnScreen().x + x, 
						this.getLocationOnScreen().y + y - complement, boxWidth, boxHeight);
				BufferedImage image = robot.createScreenCapture(rec);
				rgbs = new int[boxWidth * boxHeight];
				rgbs = image.getRGB(0, 0, boxWidth, boxHeight, rgbs, 0, boxWidth);
				
				//output image file
				OutputStream output = new FileOutputStream(new File("displayImage.jpg"));
		        ImageIO.write(image, "jpeg", output);   
			}
			
			else//too slim
			{
				complement = (boxHeight - boxWidth * 2) / 4;
				boxWidth += complement * 2;
				rec = new Rectangle(this.getLocationOnScreen().x + x - complement, 
						this.getLocationOnScreen().y + y, boxWidth, boxHeight);
				BufferedImage image = robot.createScreenCapture(rec);
				rgbs = new int[boxWidth * boxHeight];
				rgbs = image.getRGB(0, 0, boxWidth, boxHeight, rgbs, 0, boxWidth);
				
				//output image file
				OutputStream output = new FileOutputStream(new File("displayImage.jpg"));
		        ImageIO.write(image, "jpeg", output);   
			}
			
			//compress image to 8*16
			float grid = boxWidth / 8;
			int cnt = 0;
			for (int i = 0; i < 16; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					compressRGBs[cnt++] = rgbs[(int)(boxWidth * (int)(grid / 2) + boxWidth * grid * i + grid * j + grid / 2)]; 
				}
			}
		}catch(AWTException e){
			e.printStackTrace();
		}
		return compressRGBs;
	}
	
	public int[] getPixels(int[] rgb)
	{
		int len = rgb.length;
		int[] r = new int[len];
		int[] g = new int[len];
		int[] b = new int[len];
		int[] pixels = new int[len];

		for (int i = 0; i < len; i++) 
		{
			r[i] = (rgb[i] >> 16) & 0xff; // (R)：rgb(255,0,0)
			g[i] = (rgb[i] >> 8) & 0xff; // (G)：rgb(0,255,0)
			b[i] = (rgb[i]) & 0xff; // (B)：rgb(0,0,255)
		}	
		
		for (int i = 0; i < len; i++) 
		{
			if (r[i] == 0 && g[i] == 0 && b[i] == 0)
				pixels[i] = 1;
			else
				pixels[i] = 0;
		}
		return pixels;
	}
	
	//display Matrix
    public void displayImage(int[] pixels, String filedir) throws IOException{
    	int[][] matrix = new int[16][8];
    	int cnt = 0;
    	for (int i = 0; i < 16; i++)
    	{
    		for (int j = 0; j < 8; j++)
    		{
    			matrix[i][j] = pixels[cnt++];
    		}
    	}
        int cx = matrix.length;
        int cy = matrix[0].length;
        int cz = 10;//size for each pixel box
        int width = cy * cz;
        int height = cx * cz;
        
        OutputStream output = new FileOutputStream(new File(filedir));
        BufferedImage bufImg = new BufferedImage(width, height,    BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = bufImg.createGraphics();
        gs.setBackground(Color.WHITE);
        gs.clearRect(0, 0, width, height);
 
        gs.setColor(Color.BLACK);
        for (int i = 0; i < cx; i++) {
            for (int j = 0; j < cy; j++) {
              //draw black pixels
              if(matrix[i][j]==1){
                  gs.drawRect(j*cz, i*cz, cz, cz);
                  gs.fillRect(j*cz, i*cz, cz, cz);
              }
            }
        }
        gs.dispose();
        bufImg.flush();
        ImageIO.write(bufImg, "jpeg", output);   
    }
	
	private void addAllListener(){
		MouseAdapter adapter = new MouseAdapter(){
			
			public void mousePressed(MouseEvent e){
				if(flagStartWriting == false){
					
					flagStartWriting = true;
					counts = 0;
					
					DrawArea[] drawAreas = writingFrame.getDrawAreas();
					DrawArea anotherDrawArea = null;
					
					if(drawAreas[0] == drawArea){
						drawAreas[0].cleanScreen();
						anotherDrawArea = drawAreas[1];
					}else {
						drawAreas[1].cleanScreen();
						anotherDrawArea = drawAreas[0];
					}
					
					drawArea.setBackground(Color.PINK);
					anotherDrawArea.endWriting();
					anotherDrawArea.setBackground(Color.WHITE);
				}
			}
			
			public void mouseDragged(MouseEvent e){
				vector.add(e.getPoint());
				drawArea.repaint();
			}
			
			public void mouseReleased(MouseEvent e){
				if(vector.isEmpty()) 
					return;
				if (vector.get(vector.size() - 1) != null)
				{
					vector.add(null);
					counts++;
				}	
			}
		};
		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
	}
	
	public boolean redo(){
		if(vector.isEmpty()) return false;
		
		int i = vector.size() - 1;
		vector.remove(i--);  
		
		while(i >= 0 && vector.get(i) != null) 
			vector.remove(i--);
		
		this.repaint();  
		if(counts > 0) counts--;
		return true;
	}
	
	public void cleanScreen(){
		vector.clear(); 
		counts = 0;
		this.repaint();
	}
	
	public void sizeUp(){
		fontSize += 5;
	}
	
	public void sizeDown(){
		fontSize -= 5;
	}
	
	//repaint() will automatically call paintComponent, so need override
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		
		float [] dash = {5, 5};
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER
				, 10.0f, dash, 0.0f));
		g2d.setColor(Color.GRAY);
		
		g2d.drawLine(0, width / 2, height, width / 2);
		g2d.drawLine(width / 2, 0, width / 2, height);
		
		//the size of pen
		g2d.setStroke(new BasicStroke(fontSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(Color.BLACK);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(vector.size() != 0){
			Point p1 = vector.get(0), p2;
			for(int i = 1; i < vector.size(); i++){
				p2 = vector.get(i);
				if(null == p2 && null != p1){
					if(i + 2 < vector.size()){
						p1 = vector.get(i + 1);
						p2 = vector.get(i + 2);
						i += 2;			
						g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
						p1 = p2;
					}else return;
				}else {
					g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
					p1 = p2;
				}
			}
		}
	}

	public boolean isBlank(){
		if(vector.isEmpty()) return true;
		else return false;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getFeatures(){
		return featureService.getFeatures();
	}
	
	public int getLeftBorder(){
		return featureService.border_left;
	}
	
	public int getUpBorder(){
		return featureService.border_up;
	}
	
	public int getGridWidth(){
		return featureService.gridWidth;
	}
	
	public int getGridHeight(){
		return featureService.gridHeight;
	}

	public int[] getFeatures2(){
		return featureService.getFeatures2();
	}
	
	public List<Integer> getLeftBorder2(){
		return featureService.char_left;
	}
	
	public List<Integer> getUpBorder2(){
		return featureService.char_up;
	}
	
	public List<Integer> getGridWidth2(){
		return featureService.charWidth;
	}
	
	public List<Integer> getGridHeight2(){
		return featureService.charHeight;
	}

	public boolean getStatus(){
		return flagStartWriting;
	}
	
	public void endWriting(){
		flagStartWriting = false;
	}
}
