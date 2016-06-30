package term_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFunc {
	private BPNeuralNetwork bp;
	private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
			 "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	public void initBP() throws Exception
	{
		int lenOfSamples = 52152; 
		double[][] inputData = new double[lenOfSamples][128]; // 128 means 8*16 image length
		double[][] target= new double[lenOfSamples][26];	// 26 means the 26 English letters
		
		readFiles(inputData, target);
		System.out.println("Data was sucessfully read from local file!");
		
		/** inputSize = 128, hiddenSize = 40, outputSize = 26, eta = 00.1 and momentum = 0.0001 */
		bp = new BPNeuralNetwork(128, 40, 26, 0.04, 0.0001);

		/** ################## Training Neural Network ################## */
		int Epoch = 5; // 50, 200, 300
		for (int i = 0; i < Epoch; i++) {
			for (int j = 0; j < lenOfSamples; j++) {
				bp.train(inputData[j], target[j]);
			}
			System.out.println("Epoch = "+i);
		}
		System.out.println("Training done!!!");
	}
	
	/** ################## Testing Neural Network ################## */
	public String recognize(double[] pixels) 
	{
		double[] result = bp.test(pixels);
		double max = result[0];
		double pre_max = result[0];//pre_max is the second most possible number
		int idx_result = 0;
		for (int j = 1; j < result.length; j++) {
			if (result[j] > max) {
				pre_max = max;
				max = result[j];
				idx_result = j;
			}
		}
		System.out.println("recognization result = " + alphabet[idx_result]);
		return alphabet[idx_result];
	}
	
	/** Read data to two-dimensional array "train" and "target"
	 *  Every line of read data is 6 parameters followed by 8*16 size image data
	 * 	Website address: http://ai.stanford.edu/~btaskar/ocr/
	 *  The length of samples is 52152.
	 */
	public static void readFiles(double[][] train, double[][] target) throws Exception
	{
		String letters = "abcdefghijklmnopqrstuvwxyz";
		
		File f = new File("letter.data");
		FileInputStream fip = new FileInputStream(f);
		InputStreamReader reader = new InputStreamReader(fip);
		StringBuffer sb = new StringBuffer();
		
		int idx = 0;
		while (reader.ready())
		{
			char tmp = (char) reader.read();
			if(tmp == '\n') {
				String str[] = sb.toString().split("\t");
				for(int i = 6; i < str.length; i++) {
					train[idx][i-6] = Double.parseDouble(str[i]);
				}
				
				int idxtmp = letters.indexOf(str[1]);
				
				for(int i = 0; i < 26; i++) {
					if(i == idxtmp)
						target[idx][i] = 1d;
					else
						target[idx][i] = 0d;
				}
				idx++;
				sb.delete(0,sb.length());	// clear StringBuffer
				//if(idx>=5) break;	//test
			}
			else
				sb.append(tmp);
		}
		reader.close(); 
		fip.close(); 	
	}
}
