package information_retrival_hw2;


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
	private final Set<String> stopwords;
	private final Dictionary dictionary;
	
	public static StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
	
	public FileParser(final File file) throws FileNotFoundException, IOException {
		this.stopwords = new HashSet<>();
		this.dictionary = new Dictionary();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			for (String line; (line = reader.readLine()) != null;) {
				this.stopwords.add(line.trim());
			}
		}
	}
	
	public void parse(final File rootFile) throws IOException {
		// Go through every entry in the root path
		for (final File file : rootFile.listFiles()) {
			// If entry is a directory, recursively parse it
			if (file.isDirectory()) {
				this.parse(file);
			} else {
				// Else, read this file
				this.readFile(file);
			}
		}
	}


	private void readFile(final File file) throws IOException {
		// Validate input
		if (file == null || !file.exists() || file.isDirectory()) {
			return;
		}

		// Read while file into a 'file String'
		String fileStr = fileToString(file);
		final StorageManager storageManager = new StorageManager(this.stopwords);
		
		// Tokenize and store words
		tokenize(fileStr, file.getName(), storageManager);

		// Append the parsed tokens to stems and lemma dictionary
		this.dictionary.add(storageManager, file);
	}

	public void tokenize(final String fileStr, String fileName, final StorageManager storageManager) {
		// Get and read each token
		Scanner sc = new Scanner(fileStr);
		while (sc.hasNext()) {	
			String token = sc.next();
			// Skip if token is empty
			if (token == null || token.length() < 1) {
				continue;
			}
			// Stem and lemmatize		
			final String stem = stem(token);
			final List<String> lemma = lemmatizer.lemmatize(token);

			storageManager.store(token, lemma, stem, fileName);
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
		message = message.replaceAll("\\'s", "");
		message = message.replaceAll("\\.", "");
		message = message.replaceAll("[^\\w\\s]", " ");
		message = message.replaceAll("\\d", "");
		message.toLowerCase();
		
		return message;
	}
	
	public final Dictionary getDictionary() {
		return this.dictionary;
	}
}
