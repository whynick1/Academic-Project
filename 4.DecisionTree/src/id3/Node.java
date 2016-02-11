package id3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {
	Node parent;
    Node children[];
    List<ArrayList<Boolean>> lookUpTable = new ArrayList<ArrayList<Boolean>>();
    List<Integer> remainAttributes = new LinkedList<Integer>();

    int attributeName;
    int predictedLabel = -1;

    Node(Node parent, List<ArrayList<Boolean>> lookUpTable, List<Integer> remainAttributes, int predictedLable) 
    {
        this.parent = parent;
        children = new Node[2];
        this.lookUpTable = lookUpTable;
        this.predictedLabel = predictedLable;//-1 means non-leaf node
        this.attributeName = -1; //-1 means the attribute index has not been set yet
        this.remainAttributes = remainAttributes;
    }
    
    public boolean classify(List<Boolean> t) 
    {
        if (predictedLabel == 1) 
            return true;
        else if (predictedLabel == 0)
        	return false;
        else 
        {
            if (t.get(attributeName)) 
            {
                return children[1].classify(t);
            } 
            else 
            {
            	return children[0].classify(t);
            }
        }
    }
    
    public int calculateLable()
    {
    	//means no more attributes
    	int count[] = new int[2];
    	if(remainAttributes.size() <= 1)
    	{
    		for (ArrayList<Boolean> lst: lookUpTable)
    		{
    			if (lst.get(20))
    				count[0]++;
    			else
    				count[1]++;
    		}
    		if (count[0] > count[1])
    			return 1;
    		else
    			return 0;
    	}
    	//means purity is 1
    	else
    	{
    		if(lookUpTable.get(0).get(20))
    			return 1;
    		else
    			return 0;
    	}
    }
    
    
}
