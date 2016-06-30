package recognition.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import recognition.panel.DrawArea;

public class FeatureService {
	private DrawArea drawArea;          
	private boolean[] isBlackPixel;     
	private int cntWidth = 2;      
	private int cntHeight = 4;    
	
	public int gridWidth, gridHeight;  
	public int border_up, border_down, border_left, border_right;  
	public List<Integer> charWidth, charHeight;
	public List<Integer> char_up, char_down, char_left, char_right;
	
	private int[] features;    
	
	public FeatureService(DrawArea drawArea){
		this.drawArea = drawArea;
		isBlackPixel = new boolean[drawArea.getWidth() * drawArea.getHeight()];
		features = new int[cntWidth * cntHeight + 6];
	}
	
	private boolean computeBorder() throws IOException{
		int[] rgbs = drawArea.getImageRGBs();
		int i, j, k;
		int width = drawArea.getWidth();
		int height = drawArea.getHeight();
		boolean[] tmp1 = new boolean[width], tmp2 = new boolean[height];
		
		for(i = 0; i < height; i++){
			for(j = 0; j < width; j++){
				k = i * width + j;
				
				if(0 == rgbs[k] + 16777216){
					isBlackPixel[k] = true;
				}else isBlackPixel[k] = false;
			}
		}
		
		Arrays.fill(tmp1, false);
		for(j = 0; j < width; j++){
			for(i = 0; i < height; i++){
				k = i * width + j;
				tmp1[j] |= isBlackPixel[k];
			}
		}
		
		j = 0;
		while(j < width && false == tmp1[j]) j++;
		border_left = j;
		
		j = width - 1;
		while(j >= 0 && false == tmp1[j]) j--;
		border_right = j;
		
		if(border_left >= border_right) return false;
		
		Arrays.fill(tmp2, false);
		for(i = 0; i < height; i++){
			for(j = 0; j < width; j++){
				k = i * width + j;
				tmp2[i] |= isBlackPixel[k];
			}
		}
		
		i = 0;
		while(i < height && false == tmp2[i]) i++;
		border_up = i;
		
		i = height - 1;
		while(i >= 0 && false == tmp2[i]) i--;
		border_down = i;
		
		if(border_up >= border_down) return false;
		
		gridWidth = (border_right - border_left);
		gridHeight = (border_down - border_up);

		System.out.println("border_left = " + border_left);
		System.out.println("border_right = " + border_right);
		System.out.println("gridWidth = " + gridWidth);
		System.out.println("border_up = " + border_up);
		System.out.println("border_down = " + border_down);
		System.out.println("gridHeight = " + gridHeight);
		return true;
	}
	
	private boolean computeBorder2() throws IOException{
		int[] rgbs = drawArea.getImageRGBs();
		int i, j, k;
		int width = drawArea.getWidth();
		int height = drawArea.getHeight();
		boolean[] tmp1 = new boolean[width], tmp2 = new boolean[height];//TBD
		char_left = new ArrayList<Integer>();
		char_right = new ArrayList<Integer>();
		char_up = new ArrayList<Integer>();
		char_down = new ArrayList<Integer>();
		charWidth = new ArrayList<Integer>();
		charHeight = new ArrayList<Integer>();
		
		for(i = 0; i < height; i++){
			for(j = 0; j < width; j++){
				k = i * width + j;
				
				if(0 == rgbs[k] + 16777216){
					isBlackPixel[k] = true;
				}else isBlackPixel[k] = false;
			}
		}
		
		Arrays.fill(tmp1, false);
		for(j = 0; j < width; j++){
			for(i = 0; i < height; i++){
				k = i * width + j;
				tmp1[j] |= isBlackPixel[k];//tmp1 is used to test left and right edge: 00011100011100 means two characters
			}
		}
		
		j = 0;
		boolean lastPixel = false;
		while(j < width) //do while until reach a 1
		{
			if (lastPixel != tmp1[j] && tmp1[j] == true)//find -> 1 border
			{
				System.out.println("flag1!");
				char_left.add(j);
				lastPixel = true;
			}
			else if (lastPixel != tmp1[j] && tmp1[j] == false)//find -> 0 border
			{
				char_right.add(j);
				lastPixel = false;
			}
			j++;
		}
		System.out.println("char_left size is: " + char_left.size());
		
		for (int n = 0; n < char_left.size(); n++)
		{
			Arrays.fill(tmp2, false);
			for(i = 0; i < height; i++){
				for(j = char_left.get(n); j < char_right.get(n); j++){
					k = i * width + j;
					tmp2[i] |= isBlackPixel[k];
				}
			}
			i = 0;
			while(i < height && false == tmp2[i]) i++;//find 1
			char_up.add(i);
			
			i = height - 1;
			while(i >= 0 && false == tmp2[i]) i--;//find 0
			char_down.add(i);
		}
		
		for (int n = 0; n < char_left.size(); n++)
		{
			charWidth.add(char_right.get(n) - char_left.get(n));
			charHeight.add(char_down.get(n) - char_up.get(n));
		}
		
		System.out.println("char_left = " + char_left);
		System.out.println("char_right = " + char_right);
		System.out.println("charWidth = " + charWidth);
		System.out.println("char_up = " + char_up);
		System.out.println("char_down = " + char_down);
		System.out.println("charHeight = " + charHeight);
		return true;
	}
	

	public int[] getFeatures(){
		computeAllFeatures();
		return features;
	}
	
	public int[] getFeatures2(){
		computeAllFeatures2();
		return features;
	}
	
	private boolean computeAllFeatures(){
		try {
			if(!this.computeBorder()){  
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean computeAllFeatures2(){
		try {
			if(!this.computeBorder2()){  
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
