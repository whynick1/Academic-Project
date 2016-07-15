
package perceptron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.omg.CORBA.FREE_MEM;


public class Perceptron {
	final int ATTRIBUTE_NUM = 57;
    double[] weights;
    double threshold;
    float[][] attributeFreq;
    
    public void getAttributeFreq(List<ArrayList<Integer>> inputs)
	{
    	attributeFreq = new float[ATTRIBUTE_NUM][4];
		for (int i = 0; i < inputs.size(); i++)
		{
			for (int j = 0; j < ATTRIBUTE_NUM; j++)
			{
				attributeFreq[j][inputs.get(i).get(j)]++;
			}
		}
		for (int i = 0; i < attributeFreq.length; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				attributeFreq[i][j] /= 71;
				//System.out.println("attribuiteFreq[" + i + "][" + j + "] = " + attributeFreq[i][j]);
			}
		}
	}
    
    public void Train(List<ArrayList<Integer>> inputs, ArrayList<Integer> outputs, double threshold, double lrate, int epoch)
    {
        this.threshold = threshold;
        weights = new double[ATTRIBUTE_NUM];
        
        //initialize weights
        for(int i = 0; i < ATTRIBUTE_NUM; i++)
        {
            weights[i] = 0;
        }
        
        //get attribute frequency
        getAttributeFreq(inputs);

        for (int i = 0; i < epoch; i++)
        {
            int totalError = 0;
            for(int j = 0; j < outputs.size(); j++)// line j
            {
                int output = Output(inputs.get(j));
                int error = outputs.get(j) - output;
                totalError +=error;
               
                for(int k = 0; k < ATTRIBUTE_NUM; k++)
                {
                	weights[k] += lrate * attributeFreq[k][inputs.get(j).get(k)] * error; 
//                    System.out.println("i = " + i + "j = " + j + "k = " + k);
//                    System.out.println("delta = lrate * " + attributeFreq[k][inputs.get(j).get(k)] + " * " + error);
                    
                }  
            }
            if(totalError == 0)
                break;
        }
    }

    public int Output(ArrayList<Integer> attriValues)
    {
        double sum = 0.0;
        for(int i = 0; i < ATTRIBUTE_NUM; i++)
        {
            sum += weights[i] * attributeFreq[i][attriValues.get(i)];
        }

        if(sum > threshold)
            return 1;
        else
            return 0;
    }
    
    
    public double getAccuracy(List<ArrayList<Integer>> inputs, ArrayList<Integer> outputs)
    {
    	double accuracy = 0.0;
    	double count = 0;
    	for (int i = 0; i < inputs.size(); i++)
    	{
    		if (Output(inputs.get(i)) == outputs.get(i))
    			count++;
    		System.out.println(Output(inputs.get(i)) + " " + outputs.get(i));
    	}
    	accuracy = count / inputs.size(); 
    	return accuracy;
    }
}