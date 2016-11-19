package information_retrival_hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Main {
	private static InfoUtil characteristics;
	
	public static void main(String[] args) throws IOException{
		final long start = System.currentTimeMillis();
		characteristics = new InfoUtil();
		
		// Get path of Cranfield and stopword
		Scanner filePath = new Scanner(System.in);
		System.out.println("Please input Cranfield directory path: ");
		String cranfieldDictoryPath = filePath.nextLine();///Users/wanghongyi/Desktop/IR_project/Cranfield
		System.out.println("Please input stopwords file path: ");
		String stopwordPath = filePath.nextLine();///Users/wanghongyi/Desktop/IR_project/stopwords
		filePath.close();	
//		String cranfieldDictoryPath = "/Users/wanghongyi/Desktop/IR_project/Cranfield";
//		String stopwordPath = "/Users/wanghongyi/Desktop/IR_project/stopwords";
		
		
		// Call readFile
		final File folder = new File(cranfieldDictoryPath);
		final File stopwords = new File(stopwordPath);
		//mt.readFiles("/Users/wanghongyi/Documents/workspace/Tokenization/src/Cranfield");
		final FileParser parser = new FileParser(stopwords);
		parser.parse(folder);

		// show index_v1 uncompressed file info
		String fileName = "index_v1.uncompressed";
		Map<String, WordInfo> dictionary = parser.getDictionary().getLemmaDictionary();
		System.out.println("*******************************************************************************");
		showIndexResults(fileName, dictionary);
		
		// show Index 1 compressed results
		File file = new File("index_v1.compressed");
		File file2 = new File("index_v1.compressed_dictionary");
		long startCompressTime = System.currentTimeMillis();
		CompressUtil.blockCompress(dictionary, file, file2);
		long endcompressedTime = System.currentTimeMillis();
		long elapsedTime = endcompressedTime - startCompressTime;
		showCompressionResults(elapsedTime, file, file2);
			
		// show Index 2 uncompressed results
		fileName = "index_v2_uncompressed";
		dictionary = parser.getDictionary().getStemsDictionary();
		showIndexResults(fileName, dictionary);
		
		// show Index 2 compressed results
		file = new File("index_v2_compressed");
		file2 = new File("index_v2_compressed_dictionary");
		startCompressTime = System.currentTimeMillis();
		CompressUtil.frontCodingCompress(dictionary, file, file2);
		endcompressedTime = System.currentTimeMillis();
		elapsedTime = endcompressedTime - startCompressTime;
		showCompressionResults(elapsedTime, file, file2);
		System.out.println("*******************************************************************************");
		
		// show term freq and dictionary freq
		showInfoUtil(parser);

		// show largest and smallest
		dictionary = parser.getDictionary().getLemmaDictionary();
		System.out.println("*******************************************************************************");
		System.out.println("Now it is index 1");
		showPeakTerms(dictionary);

		dictionary = parser.getDictionary().getStemsDictionary();
		System.out.println("Now it is index 2");
		showPeakTerms(dictionary);

		// Get documents with largest max_tf and doclen
		System.out.println("\nDocuments with largest max_tf: " + StringUtils.join(characteristics.getDocsWithLargestMaxTF(), " "));
		System.out.println("Documents with largest doclen: " + StringUtils.join(characteristics.getDocsWithLargestDoclen(), " "));
		System.out.println("*******************************************************************************");
		System.out.println("\nTotal running time: " + (System.currentTimeMillis() - start) + " milliseconds");
	}
	
	private static void showIndexResults(final String fileName, final Map<String, WordInfo> dictionary)
			throws FileNotFoundException,
			UnsupportedEncodingException {
		final NewPrintWriter writer = new NewPrintWriter();

		// record time start
		final long startTime = System.currentTimeMillis();
		final File index1Uncompressed = writer.write(dictionary, fileName);
		final long endTime = System.currentTimeMillis();

		final OutputFormatter formatter = new OutputFormatter();
		formatter.addRow(fileName, index1Uncompressed.length() + " bytes");
		formatter.addRow("Creation time for " + fileName, endTime - startTime + " ms");

		formatter.addRow("Number of inverted lists in " + fileName, String.valueOf(dictionary.size()));
		System.out.println(formatter);
	}
	
	private static void showCompressionResults(final long time, final File file, final File file2)
			throws FileNotFoundException,
			IOException {
		// Print the results
		final OutputFormatter formatter = new OutputFormatter();
		formatter.addRow(file.getName(), String.valueOf(file.length() + file2.length()) + " bytes");
		formatter.addRow("Creation time for " + file.getName(), time + " ms");
		System.out.println(formatter);
	}

	static void showInfoUtil(final FileParser parser) throws NumberFormatException, UnsupportedEncodingException {
		final Set<String> terms = new HashSet<>();
		terms.add("reynolds");
		terms.add("nasa");
		terms.add("pressure");
		terms.add("boundary");
		terms.add("shock");
		terms.add("prandtl");
		terms.add("flow");


		final StanfordLemmatizer lemmatizer = FileParser.lemmatizer;
		final Set<String> lemmaSet = new HashSet<>();
		for (final String string : terms) {
			lemmaSet.addAll(lemmatizer.lemmatize(string));
		}

		final Set<String> stemSet = new HashSet<>();
		for (final String string : terms) {
			stemSet.add(parser.stem(string));
		}

		System.out.println("*******************************************************************************");
		System.out.println("LEMMATIZATION TOKENS\n");
		OutputFormatter formatter = characteristics.getTermInfo(lemmaSet, parser.getDictionary().getLemmaDictionary());
		System.out.println(formatter);

		System.out.println("STEMMING TOKENS\n");
		formatter = characteristics.getTermInfo(stemSet, parser.getDictionary().getStemsDictionary());
		System.out.println(formatter);
		System.out.println("*******************************************************************************");
	}
	
	static void showPeakTerms(final Map<String, WordInfo> dictionary) {
		List<String> terms = characteristics.getTermsWithLargestDf(dictionary);

		OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("Term(max DF)", "Dictionary Freq");
		for (final String string : terms) {
			formatter.addRow(string, String.valueOf(dictionary.get(string).getDocFreq()));
		}

		System.out.println(formatter);

		terms = characteristics.getTermsWithSmallestDf(dictionary);
		formatter = new OutputFormatter();
		formatter.addRow("Term(min DF)", "Dictionary Freq");
		for (final String string : terms) {
			formatter.addRow(string, String.valueOf(dictionary.get(string).getDocFreq()));
		}

		System.out.println(formatter);
	}
}












