package information_retrival_hw3;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class FileParser {
	public Set<String> stopwords;
	private Dictionary dictionary;
	public long docLenSum = 0;
	public int docCount = 0;
	
	public static StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
	
	public FileParser(File file) throws FileNotFoundException, IOException {
		this.stopwords = new HashSet<>();
		this.dictionary = new Dictionary();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			for (String line; (line = reader.readLine()) != null;) {
				this.stopwords.add(line.trim());
			}
		}
	}
	
	public void parse(File rootFile) throws IOException {
		// Go through every entry in the root path
		for (File file : rootFile.listFiles()) {
			// If entry is a directory, recursively parse it
			if (file.isDirectory()) {
				this.parse(file);
			} else {
				docCount++;
				this.readFile(file);
			}
		}
	}


	private void readFile(File file) throws IOException {
		// Validate input
		if (file == null || !file.exists() || file.isDirectory()) {
			return;
		}

		// Read while file into a 'file String'
		String fileStr = fileToString(file);
		StorageManager storageManager = new StorageManager(this.stopwords);
		
		// Tokenize and store words
		String headLine = getHeadLine(file);
		tokenize(fileStr, file.getName(), storageManager, headLine);
		
		// Append the parsed tokens to stems and lemma dictionary
		this.dictionary.add(storageManager, file);
	}

	public static String getHeadLine(File file) throws IOException {
		BufferedReader buf = new BufferedReader(new FileReader(file));
		Scanner sc = new Scanner(buf);
		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			if (str.trim().equals("<TITLE>")) {
				return "<TITLE> " + sc.nextLine();
			}
		}
		sc.close();
		return "NO TITLE FOUND!";
	}
	
	public void tokenize(String fileStr, String fileName, StorageManager storageManager, String headLine) throws IOException {
		// Get and read each token
		Scanner sc = new Scanner(fileStr);
		while (sc.hasNext()) {	
			String token = sc.next();
			// Skip if token is empty
			if (token == null || token.length() < 1) {
				continue;
			}
			// Stem and lemmatize		
//			String stem = stem(token);
			List<String> lemma = lemmatizer.lemmatize(token);
			if (storageManager.store(token, lemma, fileName, headLine))
				docLenSum++;
		}
		sc.close();
	}
	
	public String stem(String token) {
		Stemmer s = new Stemmer();
		s.add(token.toCharArray(), token.length());
		s.stem();
		return s.toString();
	}

	public static String fileToString(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		String message = null;
		try {
			//transfer XML into String
			@SuppressWarnings("resource")
			String xml = new Scanner(file).useDelimiter("\\Z").next();
			//eliminate XML tags
			db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			try {
				Document doc = db.parse(is);
				message = doc.getDocumentElement().getTextContent();
				//System.out.println("The message is: " + message);

			} catch (SAXException e) {
				// handle SAXException
			} catch (IOException e) {
				// handle IOException
			}
		} catch (ParserConfigurationException e1) {
			// handle ParserConfigurationException
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (message == null)
			System.out.println(file.getName() + "'s message is NULL");
		message = message.replaceAll("\\'s", "");
		message = message.replaceAll("\\.", "");
		message = message.replaceAll("[^\\w\\s]", " ");
		message = message.replaceAll("\\d", "");
		message.toLowerCase();
		
		return message;
	}
	
	public Dictionary getDictionary() {
		return this.dictionary;
	}
}
