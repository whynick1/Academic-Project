package information_retrival_hw2;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class InfoUtil {
	public InfoUtil() {
		super();
	}

	//get term frequency, document frequency, size of inverted list for the terms
	public OutputFormatter getTermInfo(final Set<String> terms, final Map<String, WordInfo> dictionary)
			throws NumberFormatException,
			UnsupportedEncodingException {

		final OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("TERM", "TF", "DF", "Size of inverted list");

		for (final String term : terms) {
			if (dictionary.containsKey(term)) {
				final Map<String, DocumentInfo> postingFile = dictionary.get(term).getPostingFile();
				int tf = 0, lists = 0;
				for (final Entry<String, DocumentInfo> entry : postingFile.entrySet()) {
					tf += dictionary.get(term).getTermFreq().get(entry.getKey());
					lists += Integer.SIZE / 8 * 4;
				}
				formatter.addRow(term, String.valueOf(tf), String.valueOf(dictionary.get(term).getDocFreq()), lists + " bytes");
			}
		}
		return formatter;
	}

	public OutputFormatter getFirstThree(final WordInfo properties) {
		List<String> docs = new ArrayList<>();
		docs.addAll(properties.getTermFreq().keySet());
		Collections.sort(docs);
		docs = docs.subList(0, 3);

		final OutputFormatter formatter = new OutputFormatter();
		formatter.addRow("Doc_ID", "Tf", "Max_tf", "Doc_Len");
		for (final String string : docs) {
			final int tf = properties.getTermFreq().get(string);
			final int max_tf = properties.getPostingFile().get(string).getMaxFreq();
			final int doclen = properties.getPostingFile().get(string).getDoclen();
			formatter.addRow("Cranfield: " + string, String.valueOf(tf), String.valueOf(max_tf), String.valueOf(doclen));
		}
		return formatter;
	}

	public List<String> getTermsWithLargestDf(final Map<String, WordInfo> dictionary) {
		final List<String> terms = new ArrayList<>();

		int max = 0;
		for (final Entry<String, WordInfo> entry : dictionary.entrySet()) {
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
		final List<String> docs = new ArrayList<>();

		int max = 0;
		for (final Entry<String, DocumentInfo> entry : StorageManager.getDocProperties().entrySet()) {
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
		final List<String> docs = new ArrayList<>();

		int max = 0;
		for (final Entry<String, DocumentInfo> entry : StorageManager.getDocProperties().entrySet()) {
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

	public List<String> getTermsWithSmallestDf(final Map<String, WordInfo> dictionary) {
		final List<String> terms = new ArrayList<>();

		int min = Integer.MAX_VALUE;
		for (final Entry<String, WordInfo> entry : dictionary.entrySet()) {
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

}