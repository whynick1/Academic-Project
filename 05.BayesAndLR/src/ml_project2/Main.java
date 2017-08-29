package ml_project2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
	public static void main(String[] args) 
	{
		List<Instance> trainingTable= new ArrayList<Instance>();
		List<Instance> testingTable= new ArrayList<Instance>();
		String trainingPathPos="train/ham";
		String trainingPathNeg="train/spam";
		String testingPathPos="test/ham";
		String testingPathNeg="test/spam";
		
		//read training set data
		ReadFiles readTrain = new ReadFiles();
		//when removeStopWords = true; remove Stop Words
		boolean removeStopWords = true;
		readTrain.loadData( trainingPathPos, 1, removeStopWords);//Nc (c=1)
		readTrain.loadData( trainingPathNeg, 0, removeStopWords);//Nc (c=0)
		trainingTable = readTrain.getTable();
		int numWordsPos = readTrain.countPosWords();
		int numWordsNeg = readTrain.countNegWords();
		float numAllWords = numWordsNeg + numWordsPos;//N
		float[] prior = new float[2];//prior
		prior[0] = numWordsPos / numAllWords;
		prior[1] = numWordsNeg / numAllWords;
		
		//read testing set data
		ReadFiles readTest = new ReadFiles();
		readTest.loadData( testingPathPos, 1, false);
		readTest.loadData( testingPathNeg, 0, false);
		testingTable = readTest.getTable();
		
		//build hash map for training data
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		buildHash(hm, trainingTable);
		int numDifferentWord = hm.size();//V
		int[][] wordsFreq = new int[numDifferentWord][2];
		
		//implement Naive Bayes algorithm
		NaiveBayes.getWordsFreq(wordsFreq, hm, trainingTable);
		float[][] condPorb = NaiveBayes.getCondprob(hm, numDifferentWord, wordsFreq, numWordsPos, numWordsNeg);
		NaiveBayes.testNB(prior, condPorb, testingTable, hm);
		
		//implement Logistic Regression algorithm
 		LogisticRegression lr = new LogisticRegression(numDifferentWord, hm, trainingTable);
		lr.train(lr.instances);
		lr.getAccuracy(testingTable);
	}
	
	public static void buildHash(HashMap<String, Integer> hm, List<Instance> table)
	{
		int keyCount = 0;
		for (int i = 0; i < table.size(); i++)
		{
			ArrayList<String> value = table.get(i).getX();
			for (int j = 0; j < value.size(); j++)
			{
				if (!hm.containsKey(value.get(j)))
				{
					hm.put(value.get(j), keyCount++);
				}
			}
		}
	}
}



