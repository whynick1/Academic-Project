package infomation_retrieval;

import java.util.Iterator;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		MyTokenizer mt = new MyTokenizer();
		System.out.println("Please input Cranfield directory path: ");
		Scanner filePath = new Scanner(System.in);
		mt.readFiles(filePath.nextLine());
		//mt.readFiles("/Users/wanghongyi/Documents/workspace/Tokenization/src/Cranfield");
		filePath.close();
		
		//iterator through all words in HashMap to get top 30 frequent words
		//meanwhile count number of tokens that occur only once
		Iterator<String> it = MyTokenizer.hm.keySet().iterator();
		while (it.hasNext()) {
			String str = it.next();
			int fq = MyTokenizer.hm.get(str);
			if (fq == 1)
				MyTokenizer.LonelyTokens++;
			if (MyTokenizer.pq.size() < 30)
				MyTokenizer.pq.offer(new Token(str, fq));
			else if (MyTokenizer.pq.peek().getFreq() < fq) {
				MyTokenizer.pq.poll();
				MyTokenizer.pq.offer(new Token(str, fq));
			}	
		}
		
		//iterator through all stem in HashMap to get top 30 frequent stems
		//meanwhile count number of stem that occur only once
		Iterator<String> it2 = MyTokenizer.hm_stem.keySet().iterator();
		while (it2.hasNext()) {
			String str = it2.next();
			int fq = MyTokenizer.hm_stem.get(str);
			if (fq == 1)
				MyTokenizer.LonelyStems++;
			if (MyTokenizer.pq_stem.size() < 30)
				MyTokenizer.pq_stem.offer(new Token(str, fq));
			else if (MyTokenizer.pq_stem.peek().getFreq() < fq) {
				MyTokenizer.pq_stem.poll();
				MyTokenizer.pq_stem.offer(new Token(str, fq));
			}	
		}
		
		System.out.println("**********************************************************");
		//time took by acquiring all tokens
		System.out.println("1.Acquire text characters take: " + (MyTokenizer.endTime - MyTokenizer.startTime) + " milliseconds");
		//number of tokens in all
		System.out.println("2.Number of tokens in text collection is: " + MyTokenizer.allToken);
		//number of unique tokens
		System.out.println("3.Number of unique tokens is: " + MyTokenizer.hm.size());
		//number of tokens that occur only once
		System.out.println("4.Number of tokens that occur only once is: " + MyTokenizer.LonelyTokens);
		//30 most frequent word tokens
		System.out.println("5.30 most frequent tokens are: ");
		//iterator of top 30 frequent words
		Iterator<Token> it_pq = MyTokenizer.pq.iterator();
		while (it_pq.hasNext()) {
			Token token = it_pq.next();
			System.out.println(token.getWord() + ": " + token.getFreq());
		}
		//The average number of word tokens per document
		int sum = 0;
		for (int count: MyTokenizer.tokensEachdocument)
			sum += count;
		System.out.print("6.The average number of tokens per document is: " + sum / MyTokenizer.tokensEachdocument.size());
		
		
		System.out.println("\n\n**********************************************************");
		//number of unique stems
		System.out.println("1.Number of unique stem is: " + MyTokenizer.hm_stem.size());
		//number of stems that occur only once
		System.out.println("2.Number of stem that occur only once is: " + MyTokenizer.LonelyStems);
		//30 most frequent word stems
		System.out.println("3.30 most frequent stems are: ");
		//iterator of top 30 frequent stems
		Iterator<Token> it_pq_stem = MyTokenizer.pq_stem.iterator();
		while (it_pq_stem.hasNext()) {
			Token token = it_pq_stem.next();
			System.out.println(token.getWord() + ": " + token.getFreq());
		}
		//The average number of word tokens per document
		int sum2 = 0;
		for (int count: MyTokenizer.stemsEachdocument)
			sum2 += count;
		System.out.print("4.The average number of stems per document is: " + sum2 / MyTokenizer.stemsEachdocument.size());
	}
}
