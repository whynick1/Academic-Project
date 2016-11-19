package information_retrival_hw3;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;

public class InfoUtil {
	public InfoUtil() {
		super();
	}

	//get term frequency, document frequency, size of inverted list for the terms
	public OutputFormatter getTermInfo(Set<String> terms, Map<String, WordInfo> dictionary)
			throws NumberFormatException,
			UnsupportedEncodingException {

		OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("TERM", "TF", "DF", "Size of inverted list");

		for (String term : terms) {
			if (dictionary.containsKey(term)) {
				Map<String, DocumentInfo> postingFile = dictionary.get(term).getPostingFile();
				int tf = 0, lists = 0;
				for (Entry<String, DocumentInfo> entry : postingFile.entrySet()) {
					tf += dictionary.get(term).getTermFreq().get(entry.getKey());
					lists += Integer.SIZE / 8 * 4;
				}
				formatter.addRow(term, String.valueOf(tf), String.valueOf(dictionary.get(term).getDocFreq()), lists + " bytes");
			}
		}
		return formatter;
	}

	public OutputFormatter getFirstThree(WordInfo properties) {
		List<String> docs = new ArrayList<>();
		docs.addAll(properties.getTermFreq().keySet());
		Collections.sort(docs);
		docs = docs.subList(0, 3);

		OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("Doc_ID", "Tf", "Max_tf", "Doc_Len");
		for (String string : docs) {
			int tf = properties.getTermFreq().get(string);
			int max_tf = properties.getPostingFile().get(string).getMaxFreq();
			int doclen = properties.getPostingFile().get(string).getDoclen();
			formatter.addRow("Cranfield: " + string, String.valueOf(tf), String.valueOf(max_tf), String.valueOf(doclen));
		}
		return formatter;
	}

	public List<String> getTermsWithLargestDf(Map<String, WordInfo> dictionary) {
		List<String> terms = new ArrayList<>();

		int max = 0;
		for (Entry<String, WordInfo> entry : dictionary.entrySet()) {
			if (max < entry.getValue().getDocFreq()) {
				terms.clear();
				max = entry.getValue().getDocFreq();
				terms.add(entry.getKey());
			} else if (max == entry.getValue().getDocFreq()) {
				terms.add(entry.getKey());
			}
		}

		return terms;
	}


	public List<String> getDocsWithLargestMaxTF() {
		List<String> docs = new ArrayList<>();

		int max = 0;
		for (Entry<String, DocumentInfo> entry : StorageManager.getDocProperties().entrySet()) {
			if (max < entry.getValue().getMaxFreq()) {
				docs.clear();
				max = entry.getValue().getMaxFreq();
				docs.add("Cranfield: " + entry.getKey() + " tf = " + max);
			} else if (max == entry.getValue().getMaxFreq()) {
				docs.add("Cranfield: " + entry.getKey() + " tf = " + max);
			}
		}

		return docs;
	}


	public List<String> getDocsWithLargestDoclen() {
		List<String> docs = new ArrayList<>();

		int max = 0;
		for (Entry<String, DocumentInfo> entry : StorageManager.getDocProperties().entrySet()) {
			if (max < entry.getValue().getDoclen()) {
				docs.clear();
				max = entry.getValue().getDoclen();
				docs.add("cranfield" + entry.getKey() + " Doc_len = " + max);
			} else if (max == entry.getValue().getDoclen()) {
				docs.add("cranfield" + entry.getKey() + " Doc_len = " + max);
			}
		}

		return docs;
	}

	public List<String> getTermsWithSmallestDf(Map<String, WordInfo> dictionary) {
		List<String> terms = new ArrayList<>();

		int min = Integer.MAX_VALUE;
		for (Entry<String, WordInfo> entry : dictionary.entrySet()) {
			if (min > entry.getValue().getDocFreq()) {
				terms.clear();
				min = entry.getValue().getDocFreq();
				terms.add(entry.getKey());
			} else if (min == entry.getValue().getDocFreq()) {
				terms.add(entry.getKey());
			}
		}

		return terms;
	}

	public double getAverageDocumentLen(Map<String, WordInfo> dictionaryLemma) {
		double sum = 0;
		for (String term : dictionaryLemma.keySet()) {
			WordInfo properties = dictionaryLemma.get(term);
			sum += properties.getDocFreq();
		}

		return sum / dictionaryLemma.size();
	}
	
	public String getQueryRepresentation(Map<String, Double> dictionary) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Double> term : dictionary.entrySet()) {
			builder.append(term.getKey() + ":" + term.getValue() + " ");
		}

		return builder.toString().trim();
	}
	
	/**
	 * gets vector representation of top 5 documents
	 * @param queryMap
	 * @param docMap
	 * @return result for print
	 */
	public String getTopFiveInfo2(Map<String, Double> queryMap, Map<String, Map<String, Double>> docMap) {
		StringBuilder builder = new StringBuilder();

		SortByValueMap sortableMap = new SortByValueMap(queryMap);
		TreeMap<String, Double> sortedMap = new TreeMap<>(sortableMap);
		sortedMap.putAll(queryMap);

		int count = 0;
		for (String key : sortedMap.descendingKeySet()) {
			if (++count > 5) {
				break;
			}
			
			Set<String> words = StorageManager.getDocProperties().get(key).wordSet;
			Map<String, Double> map = docMap.get(key);

			builder.append("\nDoc: cranfield" + key + "\n");
			for (String string : words) {
				if (map.containsKey(string)) {
					builder.append(string + " : " + map.get(string) + ", ");
				} else {
					builder.append(string + " : 0.0, ");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}
	
	/**
	 * get the rank, score, external document identifier, and headline, for top 5 documents based on query
	 * @param queryMap
	 * @return
	 */
	public OutputFormatter getTopFiveInfo1(Map<String, Double> queryMap) {
		OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("RANK", "SCORE", "EXTERNAL DOCUMENT IDENTIFIER", "HEADLINE");

		SortByValueMap sortableMap = new SortByValueMap(queryMap);
		TreeMap<String, Double> sortedMap = new TreeMap<>(sortableMap);
		sortedMap.putAll(queryMap);

		int count = 0;
		for (String key : sortedMap.descendingKeySet()) {
			if (++count > 5) {
				break;
			}

			String headline = StorageManager.getDocProperties().get(key).getHeadline();
			String score = String.valueOf(queryMap.get(key));

			formatter.addRow(String.valueOf(count), score, "Cranfield" + key, headline);
		}
		return formatter;
	}

}