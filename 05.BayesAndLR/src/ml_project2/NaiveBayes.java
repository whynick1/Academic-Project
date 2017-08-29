package ml_project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NaiveBayes {
	
	public static void testNB(float[] prior, float[][] condPorb, List<Instance> testingTable, HashMap<String, Integer> hm)
	{
		int[] predicted = new int[testingTable.size()];
		System.out.println("==============Mutinominal Naive Bayes============");
		for (int h = 0; h < testingTable.size(); h++)//for each instance
		{
			ArrayList<String> value = testingTable.get(h).getX();
			double score[] = new double[2];
			for (int i = 0; i < 2; i++)//for each class
			{
				score[i] = Math.log(prior[i]) / Math.log(2);
				for (int j = 0; j < value.size(); j++)//for each word(allow repeat) within instance
				{
					String tempStr = value.get(j);
					if (hm.containsKey(tempStr))
					{
						int wordIndex = hm.get(tempStr);
						score[i] += Math.log(condPorb[wordIndex][i]) / Math.log(2.0);
					}
				}
			}
			predicted[h] = (score[0] > score[1])? 1: 0;
		}
		calculateAccuracy(testingTable, predicted);
	}
	
	public static void calculateAccuracy(List<Instance> testingTable, int[] predicted)
	{
		int right = 0;
		int wrong = 0;
		float accuracy;
		for (int i = 0; i < predicted.length; i++)
		{
			if (testingTable.get(i).getLabel() == predicted[i])
				right++;
			else
				wrong++;
		}
		accuracy = (float)right / (right + wrong);
        System.out.println("CorrectCount = " + right);
        System.out.println("ErrorCount = " + wrong);
        System.out.println("Correct Accuracy = " + accuracy);
	}
	
	public static float[][] getCondprob(HashMap<String, Integer> hm, int V, int[][] wordsFreq, 
			int Nc1, int Nc0)
	{
		int[] Nc = new int[2];
		Nc[0] = Nc1;
		Nc[1] = Nc0;
		float condPorb[][] = new float[wordsFreq.length][2];
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < wordsFreq.length; j++)//wordsFreq.length == hm.size()
			{
				//problem here!!!the index1 of wordsFreq[index1][index2] is base on the sequence of value in HashMap
				condPorb[j][i] = (wordsFreq[j][i] + 1) / (float)(Nc[i] + V);			
//				System.out.println("tmpProb = " + condPorb[j][i] + ", tempCount = " + wordsFreq[j][i] + ", TotalCount = " + Nc[i] + ","
//						+ " V.size = " + V);
			}
		}
		return condPorb;
	}
	
	public static void getWordsFreq(int[][] wordsFreq, HashMap<String, Integer> hm, List<Instance> table)
	{
		for (int i = 0; i < table.size(); i++)
		{
			ArrayList<String> value = table.get(i).getX();
			for (int j = 0; j < value.size(); j++)
			{
				if (table.get(i).getLabel() == 1)
				{
					int index = hm.get(value.get(j));
					wordsFreq[index][0]++;
//					System.out.println("index: " + index);
//					System.out.println(value.get(j) + "++ = " + wordsFreq[index][0] + ", pos");
				}
				else
				{
					int index = hm.get(value.get(j));
					wordsFreq[index][1]++;
//					System.out.println("index: " + index);
//					System.out.println(value.get(j) + "++ = " + wordsFreq[index][1] + ", neg");
				}
			}
		}
	}
}
