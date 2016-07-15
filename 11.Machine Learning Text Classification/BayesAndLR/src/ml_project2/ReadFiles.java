package ml_project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReadFiles {
	private int[] countNegPos = {0, 0}; 
	String[] stopwords = {"a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours	","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"};
	Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	private List<Instance> table = new ArrayList<Instance>();
	
	public boolean isStopword(String word) {
		if(word.length() < 2) return true;
		if(stopWordSet.contains(word)) return true;
		else return false;
	}
	
	public void loadData(String filePath, int label, boolean removeStopWords)
	{
		String path = filePath;
		File file=new File(path);
		File[] tempList = file.listFiles();
		try {
			//iteratively read each File with for loop
			for (int i = 0; i < tempList.length; i++) {
				//can use if(tempList[i].isFile()) or if (tempList[i].isDirectory()) to check if tempList[i] is file or directory
				ArrayList<String> list = new ArrayList<String>(); 
				BufferedReader br = new BufferedReader(new FileReader(tempList[i]));
				Scanner sc = new Scanner(br);
				while (sc.hasNext())
				{
					String[] str = sc.nextLine().split("\\b");
					for (int j = 0; j < str.length; j++)
					{
						if (removeStopWords)
						{
							if (str[j].matches("\\b[a-zA-Z].*\\b") && !isStopword(str[j]))
							{
								list.add(str[j]);
								countNegPos[label]++;//number of words increment
							}
						}
						else
						{
							if (str[j].matches("\\b[a-zA-Z].*\\b"))
							{
								list.add(str[j]);
								countNegPos[label]++;//number of words increment
							}
						}
					}
				}
				Instance document = new Instance(label, list);
				table.add(document);
				sc.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		//check if words have been extracted from document
//		for (int i = 0; i < table.get(1).getX().size(); i++)
//			System.out.println("the " + i + " word is: " + table.get(1).getX().get(i));
	}
	
	public List<Instance> getTable()
	{
		return table;
	}
	
	public int countNegWords()
	{
		return countNegPos[0];
	}
	
	public int countPosWords()
	{
		return countNegPos[1];
	}
}
