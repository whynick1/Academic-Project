package information_retrival_hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QueryParser {
	private Set<String> stopwords;
	public List<Dictionary> dictionaries;
	public static StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
	public long queryLenSum = 0;
	public int queryCount = 0;
	
	public QueryParser(Set<String> stopwords) {
		this.stopwords = stopwords;
		this.dictionaries = new ArrayList<>();
	}

	
	public void readFile(File file) throws IOException {
		// Validate input
		if (file == null || !file.exists() || file.isDirectory()) {
			return;
		}
		
		//file to string
		String fileStr = fileToString(file);
		
		//tokenize and store words
		List<String> queries = tokenize(fileStr);

		//put tokens of query into dictionaries
		for (String query : queries) {
			StorageManager storageManager = new StorageManager(this.stopwords);
			buildStorageMananger(file, query, storageManager);

			Dictionary dictionary = new Dictionary();
			dictionary.add(storageManager, file);
			
			//System.out.println("dictionary size: " + dictionary.dictionaryLemma.size());
			this.dictionaries.add(dictionary);
		}
	}
	
	public void buildStorageMananger(final File file, String query, StorageManager storageManager) throws IOException {
		// Get and read each token
		String[] words = query.split("\\s+");
		for (String word : words) {
			if (word == null || word.length() < 1) {
				continue;
			}

			// Lemmatize
			List<String> lemma = lemmatizer.lemmatize(word);
			String headLine = FileParser.getHeadLine(file);
			
			if (storageManager.store(word, lemma, file.getName(), headLine))
				queryLenSum++;
		}
	}
	
	public List<String> tokenize(String fileStr) {
		// Get and read each token
		List<String> res = new ArrayList<String>();
		String[] strArr = fileStr.split("\\n");
		for (String str: strArr) {
			str.trim();
			if (str.length() > 0) {
				queryCount++;
				res.add(str);
			}
		}
		return res;
	}

	/*
	 * Transform file to String and remove special signs
	 * @param: File file
	 * @return: String fileAsString
	 */
	public String fileToString(File file) throws IOException {
		BufferedReader buf = new BufferedReader(new FileReader(file)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder(); 
		
		while(line != null){
			if (line.charAt(0) == '.')
				sb.append("\n"); 
			else 
				sb.append(line).append(" ");
			line = buf.readLine(); 
		}
		String fileAsString = sb.toString();
		fileAsString = fileAsString.replaceAll("\\'s", "");
		fileAsString = fileAsString.replaceAll("\\.", "");
		fileAsString = fileAsString.replaceAll("[^\\w\\s]", " ");
		fileAsString = fileAsString.replaceAll("\\d", "");
		fileAsString.toLowerCase();
		buf.close();
		return fileAsString;
	}
	
	public String stem(String token) {
		Stemmer s = new Stemmer();
		s.add(token.toCharArray(), token.length());
		s.stem();
		return s.toString();
	}
}
