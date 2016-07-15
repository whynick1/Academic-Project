package perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReadFile {
	
	final int ATTRIBUTE_NUM = 57;
	List<ArrayList<Integer>> table;
	ArrayList<Integer> labelTable;

	public void loadSet(String fileName) 
	{
		table = new ArrayList<ArrayList<Integer>>();
		labelTable = new ArrayList<Integer>();
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(fileName));
			String stemp;
			while ((stemp = br.readLine()) != null) {
			list.add(stemp);
		}
		int listSize = list.size();
		
		//for each row in .new file
		for (int i = 0; i < listSize; i++)
		{
			StringTokenizer st = new StringTokenizer(getRow(i, list), " ");
			labelTable.add(Integer.parseInt(st.nextToken()));
			
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			for (int j = 1; j < ATTRIBUTE_NUM + 1; j++)
			{
				tempList.add(Integer.parseInt(st.nextToken().replaceFirst("[0-9]+:", "")));
			}
			table.add(tempList);
		}
		br.close();
		
		}catch (Exception e){
		e.printStackTrace();
		}
	}

//	//for checking
//	public void PrintTables(){
//		for (int i = 0; i < table.size(); i++)
//		{
//			System.out.print(labelTable.get(i) + " ");
//			for (int j = 0; j < table.get(0).size(); j++)
//			{
//				System.out.print(j + ":" + table.get(i).get(j) + " ");
//			}
//			System.out.println("");
//		}
//	}
	
	public String getRow(int index, List<String> list) {
		if (list.size() != 0) 
	        return (String) list.get(index); 
	    else
	    	return null;
	}
}


