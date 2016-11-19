package infomation_retrieval;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MyTokenizer {
	//global variables
	public static int allToken;
	
	public static List<Integer> tokensEachdocument;
	public static int LonelyTokens;
	public static Map<String, Integer> hm;
	public static PriorityQueue<Token> pq;
	
	public static List<Integer> stemsEachdocument;
	public static int LonelyStems;
	public static Map<String, Integer> hm_stem;
	public static PriorityQueue<Token> pq_stem;	
	
	public static long startTime;
	public static long endTime;
	
	//constructor
	public MyTokenizer(){
		allToken = 0;
		LonelyTokens = 0;
		LonelyStems = 0;
		tokensEachdocument = new ArrayList<Integer>();
		stemsEachdocument = new ArrayList<Integer>();
		hm = new HashMap<String, Integer>();
		hm_stem = new HashMap<String, Integer>();
		pq = new PriorityQueue<Token>(30);
		pq_stem = new PriorityQueue<Token>(30);
	}
	
	//read document
	public void readFiles(String dirctoryPath) {
		startTime = System.currentTimeMillis();
		File directory = new File(dirctoryPath);
		if (!directory.isDirectory()) 
			throw new IllegalArgumentException("Fail to open the directory !");
		
		String[] fileList = directory.list(); 
		for(int i = 0; i < fileList.length; i++) {
			String filePath = directory.getPath() + File.separator + fileList[i];
			String parsedFile = processFile(filePath); 
			storeAndCount(parsedFile);	
		}	
		endTime = System.currentTimeMillis();
		for(int i = 0; i < fileList.length; i++) {
			String filePath = directory.getPath() + File.separator + fileList[i];
			String parsedFile = processFile(filePath); 
			storeAndCountStemming(parsedFile);
		}		
//		String filePath = directory.getPath() + File.separator + fileList[0];
//		storeAndCount(filePath); 
	}

	private void storeAndCountStemming(String parsedFile) {
		int stemCount = 0;
		Stemmer s = new Stemmer();
		Scanner sc = new Scanner(parsedFile);
		while (sc.hasNext()) {
			stemCount++;
			String token = sc.next();
			s.add(token.toCharArray(), token.length());
			s.stem();
			String stem = s.toString();
			if (hm_stem.containsKey(stem)) {
				hm_stem.put(stem, hm_stem.get(stem) + 1);
			} else {
				hm_stem.put(stem, 1);
			}
		}
		stemsEachdocument.add(stemCount);
		sc.close();
	}

	public void storeAndCount(String parsedFile) {
		int tokenCount = 0;
		Scanner sc = new Scanner(parsedFile);
		while (sc.hasNext()) {
			allToken++;
			tokenCount++;
			String token = sc.next();
			if (hm.containsKey(token)) {
				hm.put(token, hm.get(token) + 1);
			} else {
				hm.put(token, 1);
			}
		}
		tokensEachdocument.add(tokenCount);		
		sc.close();	
	}
	
	public String processFile (String filePath) {
		File file = new File(filePath);
		String parsedFile = FileParser.parse(file);
		//deal with "-", "_", ","s", and Upper letter
		parsedFile = parsedFile.replaceAll("\\'s", "");
		parsedFile = parsedFile.replaceAll("\\.", "");
		parsedFile = parsedFile.replaceAll("[^\\w\\s]", " ");
		parsedFile = parsedFile.replaceAll("\\d", "");
		
		parsedFile.toLowerCase();
		//System.out.println(parsedFile);
		return parsedFile;
	}
	
}
