package information_retrival_hw3;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static InfoUtil infoUtil;
	
	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		infoUtil = new InfoUtil();
	
		//get path of Cranfield and stopword
		Scanner filePath = new Scanner(System.in);
		
		System.out.println("Please input Cranfield directory path: ");
		String cranfieldDictoryPath = filePath.nextLine();///Users/wanghongyi/Desktop/IR_project/Cranfield
		
		System.out.println("Please input stopwords file path: ");
		String stopwordPath = filePath.nextLine();///Users/wanghongyi/Desktop/IR_project/stopwords
		
		System.out.println("Please input hw3.queries file path: ");
		String queriesPath = filePath.nextLine();///Users/wanghongyi/Desktop/IR_project/hw3.queries
			
		filePath.close();	
		
//		String cranfieldDictoryPath = "/Users/wanghongyi/Desktop/IR_project/Cranfield";
//		String stopwordPath = "/Users/wanghongyi/Desktop/IR_project/stopwords";
//		String queriesPath = "/Users/wanghongyi/Desktop/IR_project/hw3.queries";
		
		
		// parse Cranfield doc
		File folder = new File(cranfieldDictoryPath);
		File stopwords = new File(stopwordPath);
		File queries = new File(queriesPath);
		
		FileParser parser = new FileParser(stopwords);
		parser.parse(folder);
		
		// parse query
		QueryParser queryParser = new QueryParser(parser.stopwords);
		queryParser.readFile(queries);
		
		// get average doc length
		double avgDocLen = parser.docLenSum / (double)parser.docCount;
		System.out.println("avg doc length = " + avgDocLen);
		
		// get average query length
		double avgQueryLen = queryParser.queryLenSum / (double)queryParser.queryCount;
		System.out.println("avg query length = " + avgQueryLen);
		
		// calculate term weight and display results
		retrieveRank(parser, queryParser, avgDocLen, avgQueryLen);
		
		// print time use
		System.out.println("\nTotal time use: " + (System.currentTimeMillis() - start) + " millis");
	}
	
	private static void retrieveRank(FileParser parser,
			QueryParser queryParser, double avgDocLen, double avgQueryLen) {
		Map<String, WordInfo> lemmaDictionary = parser.getDictionary().getLemmaDictionary();
		int num = 1;
		for (Dictionary dictionary : queryParser.dictionaries) {
			TermWeightCalculater docProcessor = new TermWeightCalculater(lemmaDictionary, avgDocLen);
			docProcessor.process1(dictionary);

			System.out.println("*********************************************************************************");
			System.out.println("For Query " + num + "\n");
			
			TermWeightCalculater queryProcessor = new TermWeightCalculater(dictionary.getLemmaDictionary(), avgQueryLen);
			queryProcessor.process2(dictionary);

			//For W1
			System.out.println("Vector representation for query:");
			System.out.println("W1:");
			System.out.println(infoUtil.getQueryRepresentation(queryProcessor.w1));
			
			System.out.println("\nDisplay rank, score, external document identifier, and headline of top 5 documents:\n");
			System.out.println("W1:");
			System.out.println(infoUtil.getTopFiveInfo1(docProcessor.w1));
			
			System.out.println("\nVector representation of Top 5 documents for the query");
			System.out.println(infoUtil.getTopFiveInfo2(docProcessor.w1, docProcessor.Doc_W1));

			//For W2
			System.out.println("W2:");
			System.out.println(infoUtil.getQueryRepresentation(queryProcessor.w2));
			
			System.out.println("\nDisplay rank, score, external document identifier, and headline of top 5 documents:\n");
			System.out.println("W2:");
			
			System.out.println("\nDisplay rank, score, external document identifier, and headline of top 5 documents:\n");
			System.out.println(infoUtil.getTopFiveInfo1(docProcessor.w2));
			
			System.out.println("\nVector representation of Top 5 documents for the query");
			System.out.println(infoUtil.getTopFiveInfo2(docProcessor.w2, docProcessor.Doc_W2));
			num++;
		}

		System.out.println("*********************************************************************************");
	}
}












