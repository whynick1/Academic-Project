package id3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;



public class ID3 {

	final static int ATTRIBUTE_NUMBER = 20;
	static Node root;
	static String[] attributeList = {"XB", "XC", "XD", "XE", "XF", "XG", "XH", "XI", "XJ", "XK", 
			"XL", "XM", "XN", "XO", "XP", "XQ", "XR", "XS", "XT", "XU"};
	public static void main(String args[])
	{
		//look up table for training_set_csv
		List<ArrayList<Boolean>> lookUpTable = new ArrayList<ArrayList<Boolean>>();
		//look up table for test_set.csv
		List<ArrayList<Boolean>> testLookUpTable = new ArrayList<ArrayList<Boolean>>();
		
		lookUpTable = loadSet("training_set.csv", lookUpTable);
		testLookUpTable = loadSet("test_set.csv", testLookUpTable);
		
		ID3 id3 = new ID3();
		//Machine learning process, build DTree
		//heuristic-> 0:Information Gain Heuristic
		//heuristic-> 1:Variance impurity Heuristic
		id3.learn(lookUpTable, 0);
		//produce predicted result of test_set.csv
		List<Boolean> predictions = id3.classify(testLookUpTable);
		//print accuracy
		System.out.println("############ Print decision tree ############");
		PrintTree(root, 0);
		System.out.println("Acurracy with ID3 algorithm is: "
				+ id3.computeAccuracy(predictions, testLookUpTable));
	}


	public void learn(List<ArrayList<Boolean>> lookUpTable, int heuristic) 
	{
		ID3.root = generate(lookUpTable, heuristic);
	}

	public Node generate(List<ArrayList<Boolean>> lookUpTable, int heuristic)
	{
		List<Integer> remainAttributes = new ArrayList<Integer>();
		for (int i = 0; i < ATTRIBUTE_NUMBER; i++)
			remainAttributes.add(i);
		Node root = new Node(null, lookUpTable, remainAttributes, -1);
		expand(root, 0, heuristic);
		return root;
	}

	//compute Gain() 
	public double ComputeGain(List<ArrayList<Boolean>> lookUpTable, int attributeIndex, int heuristic)
	{  
	    //get entropy before classified
	    double parent_entropy = ComputeEntropy(lookUpTable, heuristic); 
	    double children_entropy = 0;
	    //get entropy after classification
	    ArrayList<ArrayList<Boolean>> posLookUpTable = new ArrayList<ArrayList<Boolean>>();
	    ArrayList<ArrayList<Boolean>> negLookUpTable = new ArrayList<ArrayList<Boolean>>();
	    for (int i = 0; i < lookUpTable.size(); i++)
	    {
	    	//positive attribute set
	    	if (lookUpTable.get(i).get(attributeIndex))
	    	{
	    		posLookUpTable.add(lookUpTable.get(i));
	    	}
	    	//negative attribute set
	    	else
	    	{
	    		negLookUpTable.add(lookUpTable.get(i));
	    	}
	    }
	    
	    children_entropy -= 1.0 * (posLookUpTable.size())
	            / (lookUpTable.size())
	            * ComputeEntropy(posLookUpTable, heuristic);
	    children_entropy -= 1.0 * (negLookUpTable.size())
	    		/ (lookUpTable.size())
                * ComputeEntropy(negLookUpTable, heuristic);
        return (parent_entropy + children_entropy);
	}
	    
	//computeEntropy
	public double ComputeEntropy(List<ArrayList<Boolean>> examples, int heuristic)
	{  
		int countYes = 0;
		int countNo = 0;
	    for (int i = 0; i < examples.size(); i++)
        {
	    	if (examples.get(i).get(ATTRIBUTE_NUMBER))
	        	countYes++;
	        else	
	       		countNo++;
	    }

	    if(countYes == 0 || countNo == 0 ) return 0;
	    //calculate entropy
	    double sum = countYes + countNo;  
	    double entropy = 0;
	    if (heuristic == 0)
	    	entropy = -countYes/sum*Math.log(countYes/sum)/Math.log(2.0) - countNo/sum*Math.log(countNo/sum)/Math.log(2.0); 
	    else if (heuristic == 1)
	    	entropy = (countYes / sum) * (countNo / sum) ;
		else
			try {
				throw new InvalidHeuristicException("" + heuristic);
			} catch (InvalidHeuristicException e) {
				e.printStackTrace();
			}
	    return entropy;
	} 
		
	//judge is all the labels are the same
	public boolean AllTheSameLabel(List<ArrayList<Boolean>> examples, Boolean label){
	    int count = 0;  
	    for(int i = 0; i < examples.size(); i++){  
	    	if(examples.get(i).get(ATTRIBUTE_NUMBER).equals(label)) 
	    		count++;
	    	else
	    		break;
	    }  
	    if(count >= examples.size()) 
	    	return true;
	    else 
	    	return false;
	}
	
	//recursively build decision tree 
    public void expand(Node node, int depth, int heuristic)
    {
    	//if all the labels are the true
    	if (AllTheSameLabel(node.lookUpTable, true))
    	{
    		node.predictedLabel = 1;
    		return;
    	}
    	//if all the labels are the false
    	if (AllTheSameLabel(node.lookUpTable, false))
    	{
    		node.predictedLabel = 0;
    		return;
    	}
    	//if remainAttribute.size() = 0
    	if (node.remainAttributes.size() <= 0)
    	{
    		node.predictedLabel = node.calculateLable();
    		return;
    	}
    	
    	//find the max Gain attribute
    	double tempGain = -9999;
    	double maxGain = -9999;
    	int maxIndex = 0;
    	for (int i = 0; i < node.remainAttributes.size(); i++)
    	{
    		tempGain = ComputeGain(node.lookUpTable, node.remainAttributes.get(i), heuristic);
    		if(tempGain > maxGain) 
    		{  
    			//attention: maxIndex is just index, not the attributeName;
	            maxGain = tempGain;
	            maxIndex = i;
	        }
    	}
 
    	node.attributeName = node.remainAttributes.get(maxIndex);
        if( node.attributeName == -1)
	    	System.out.println("can't find the of attribute");
    			
    	//removes the best attribute in every "satisfied" step of DFS
        List<Integer> newRemainAttribute = new ArrayList<Integer>();
        for (int i = 0; i < node.remainAttributes.size(); i++)
        {
        	newRemainAttribute.add(node.remainAttributes.get(i));
        }
    	newRemainAttribute.remove(maxIndex);
    	
    	boolean truthTable[] = {true, false};
    	for (int i = 0; i < 2; i++)//only positive and negative
    	{
    		List<ArrayList<Boolean>> subLookUpTable = new ArrayList<ArrayList<Boolean>>();
    		for (int j = 0; j < node.lookUpTable.size(); j++)
    		{
    			//be careful in the following clause
    			if (!node.lookUpTable.get(j).get(node.attributeName).equals(truthTable[i]))
    				subLookUpTable.add(node.lookUpTable.get(j));	
    		}
    		node.children[i] = new Node(node, subLookUpTable, newRemainAttribute, -1);
    		expand(node.children[i], depth + 1, heuristic);
    	}
	}
    
    public static void PrintTree(Node node, int depth)
	{
		if(node.predictedLabel != -1) return; //means node is leaf-node
		
	    for (int i = 0; i < 2; i++)
	    {
	    	for (int j = 0; j < depth; j++)	//DFS
		    	System.out.print("| "); 
	    	
	    	if(node.children[i].predictedLabel != -1) //mean node.child is leaf-node
	    		System.out.println(attributeList[node.attributeName] + " = " + i + " : " + node.children[i].predictedLabel);
	    	else
	    		System.out.println(attributeList[node.attributeName] + " = " + i + " : ");
	    	
	        PrintTree(node.children[i], depth + 1); //打印下一颗子树
	    }
	    return;
	} 
    
	////////////////////////////////////////
	// load csv file
	////////////////////////////////////////
	private static List<ArrayList<Boolean>> loadSet(String fileName, List<ArrayList<Boolean>> lookUpTable) 
	{
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(fileName));
			String stemp;
			while ((stemp = br.readLine()) != null) {
			list.add(stemp);
			}
			
			//i = 1 mean neglect the first line
			for (int i = 1; i < getRowNum(list); i++)
			{
				StringTokenizer st = new StringTokenizer(getRow(i, list), ",");
				ArrayList<Boolean> tempList = new ArrayList<Boolean>();
				boolean flag = false;
				for (int j = 0; j < ATTRIBUTE_NUMBER + 1; j++)
				{
					flag = (st.nextToken().equals("1"))? true: false;
			    	tempList.add(flag);
				}
			    lookUpTable.add(tempList);
			}
			br.close();
			
	    }catch (Exception e){
	        e.printStackTrace();
	    }
		return lookUpTable;
	}
	
	//classify the test set
	public List<Boolean> classify(List<ArrayList<Boolean>> testInstances) {
        ArrayList<Boolean> predictions = new ArrayList<Boolean>();
        for (ArrayList<Boolean> t : testInstances) 
        {
            Boolean predictedCategory = root.classify(t);
            predictions.add(predictedCategory);
        }
        return predictions;
    }
	
	//calculate accuracy
	public double computeAccuracy(List<Boolean> predictions,
            List<ArrayList<Boolean>> testInstances) {
		if (predictions.size() != testInstances.size())
        {
            return 0;
        } 
        else 
        {
            int right = 0, wrong = 0;
            for (int i = 0; i < predictions.size(); i++) {
                if (predictions.get(i) == null) {
                    wrong++;
                } else if (predictions.get(i) == testInstances.get(i).get(ATTRIBUTE_NUMBER)) {
                    right++;
                } else {
                    wrong++;
                }
            }
            return right * 1.0 / (right + wrong);
        }
    }
	
    static public int getRowNum(List<String> list) {
            return list.size();
    }
    
    static public String getRow(int index, List<String> list) {
    	if (list.size() != 0) 
            return (String) list.get(index); 
        else
        	return null;
    }
}

