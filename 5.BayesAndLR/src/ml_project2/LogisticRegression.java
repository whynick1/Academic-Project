package ml_project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ml_project2.Instance;

public class LogisticRegression {
	/** the learning rate */
    final double RATE = 0.01;
    
    /** the penalty rate */
    final double PENALTY = 0.01; 

    /** the weight to learn */
    private double[] weights;

    /** the number of iterations */
    private int ITERATIONS = 500;
    
    private HashMap<String, Integer> hm;
    float[] wordsFreq;
    List<Instance> instances;

    public LogisticRegression(int hmSize, HashMap<String, Integer> hm, List<Instance> trainInstances) {
    	weights = new double[hmSize];
        this.hm = hm; 
        this.wordsFreq = new float[hmSize];
    	getWordsFreqLR(this.wordsFreq, hm, trainInstances);
        this.instances = trainInstances;
    }
    
    public static void getWordsFreqLR(float[] wordsFreqLR, HashMap<String, Integer> hm, List<Instance> table)
	{
		for (int i = 0; i < table.size(); i++)
		{
			HashSet<String> hs = new HashSet<String>();
			ArrayList<String> value = table.get(i).getX();
			for (int j = 0; j < value.size(); j++)
			{
				if (!hs.contains(value.get(j)))
				{
					int index = hm.get(value.get(j));
					wordsFreqLR[index]++;
					hs.add(value.get(j));
				}
			}
		}
		for (int i = 0; i < wordsFreqLR.length; i++)
		{
			wordsFreqLR[i] /= table.size();
		}
	}

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    public void train(List<Instance> instances) {
        for (int n = 0; n < ITERATIONS; n++) {
            for (int i=0; i<instances.size(); i++) {
            	Instance inst = instances.get(i);
                double predicted = classify(inst);
                int label = instances.get(i).getLabel();
                for (int j = 0; j < inst.getX().size(); j++) {
                	int index = hm.get(inst.getX().get(j));
                    weights[index] = weights[index] + RATE * (label - predicted) * wordsFreq[index] - RATE * PENALTY * weights[index];
//                    System.out.println("key = " + inst.getX().get(j) + ", w = " + weights[index] + ", Xi = " + wordsFreq[index]);
                }
            }
        }
    }

    public double classify(Instance inst) {
        double logit = .0;
        ArrayList<String> document = inst.getX();
        for (int i = 0; i < document.size(); i++)  
        {
        	String tempStr = document.get(i);
			if (hm.containsKey(tempStr))
			{
	        	int index = hm.get(document.get(i));
	            logit += weights[index] * wordsFreq[index];	            
//	            System.out.println("///////weight = " + weights[index] + " wordsFreq = " + wordsFreq[index]);
//	            System.out.println("logit = " + logit);
			}
        }
        return sigmoid(logit);
    }
    
    public double getAccuracy(List<Instance> testingInstances)
    {
		int CorrectCount = 0;
		int ErrorCount = 0;
		int NaN = 0;
		System.out.println("\n==============Logistic Regression============");
    	for (int i = 0; i < testingInstances.size(); i++) 
    	{
            double predicted = classify(testingInstances.get(i));
//            System.out.println("predicted = " + predicted);
      
            int label = testingInstances.get(i).label;
            
            if(label == 1 && predicted < 0.5)
            	ErrorCount++;
            else if(label == 1 && predicted >= 0.5)
            	CorrectCount++;
            else if(label == 0 && predicted >= 0.5)
            	ErrorCount++;
            else if(label == 0 && predicted < 0.5)
            	CorrectCount++;
            else
            	NaN++;
        }
        System.out.println("CorrectCount = " + CorrectCount);
        System.out.println("ErrorCount = " + ErrorCount);
        System.out.println("NaNCount = " + NaN);
        System.out.println("Correct Accuracy = " + (double)CorrectCount/testingInstances.size());
        
    return (double)CorrectCount/testingInstances.size();
    }
}
