package dbtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReadFiles {
	////////////////////////////////////////
	// load csv file
	////////////////////////////////////////
	final static int BOOKS_COLLUMN_NUM = 7;
	public static List<ArrayList<String>> loadSet(String fileName, int containTitle) 
	{
		List<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(fileName));
			String stemp;
			while ((stemp = br.readLine()) != null) {
			list.add(stemp);
		}
		int listSize = list.size();

		//get the number of columns
		StringTokenizer tempSt = new StringTokenizer(getRow(0, list), "\t|,");
		int collumnNum = tempSt.countTokens();
		
		//i = 0 mean do not contain title, load start from first line; i = 1 means need to start from second line
		for (int i = containTitle; i < listSize; i++)
		{
			StringTokenizer st = new StringTokenizer(getRow(i, list), "\t|,");
			ArrayList<String> tempList = new ArrayList<String>();
			for (int j = 0; j < collumnNum; j++)
			{
				tempList.add(st.nextToken());
			}
			table.add(tempList);
		}
		System.out.println("read .csv file sucessed!");
		br.close();
		
		}catch (Exception e){
		e.printStackTrace();
		}
		return table;
	}
	
	public static List<ArrayList<String>> loadBooks(String fileName) 
	{
		List<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(fileName));
			String stemp;
			while ((stemp = br.readLine()) != null) {
			list.add(stemp);
		}
		int listSize = list.size();
		
		//i = 1 mean exclude the first line
		for (int i = 1; i < listSize; i++)
		{
			StringTokenizer st = new StringTokenizer(getRow(i, list), "\t");
			ArrayList<String> tempList = new ArrayList<String>();
			if (st.countTokens() == 7)//mean book info complete
			{
				for (int j = 0; j < 7; j++)
				{
					tempList.add(st.nextToken());
				}
			}
			else//book info incomplete, so only keep ISBN and Title info
			{
				for (int j = 0; j < 7; j++)
				{
					if (j <= 2)
						tempList.add(st.nextToken());
					else
					{	
						st.nextToken();
						tempList.add(st.nextToken());
					}
				}
			}
			table.add(tempList);//add a tuple to the Books table
		}
		System.out.println("down!");
		br.close();
		
		}catch (Exception e){
		e.printStackTrace();
		}
		return table;
	}

	static public String getRow(int index, List<String> list) {
		if (list.size() != 0) 
	        return (String) list.get(index); 
	    else
	    	return null;
	}
}


