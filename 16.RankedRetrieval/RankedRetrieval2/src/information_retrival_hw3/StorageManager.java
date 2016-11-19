package information_retrival_hw3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StorageManager {
	private Map<String, Integer> stemsMap;
	private Map<String, Integer> lemmaMap;
	private static Map<String, DocumentInfo> docProperties = new HashMap<>(); //String is Doc.Name
	private static Set<String> stopwords;

	public StorageManager(Set<String> stopwords) {
		this.stemsMap = new HashMap<>();
		this.lemmaMap = new HashMap<>();
		StorageManager.stopwords = stopwords;
	}

	@Override
	public String toString() {
		return "StorageManager [stemsMap=" + stemsMap + ", lemmaMap="
				+ lemmaMap + "]";
	}

	public Map<String, Integer> getStemsMap() {
		return this.stemsMap;
	}

	public Map<String, Integer> getLemmaMap() {
		return this.lemmaMap;
	}

	public static Map<String, DocumentInfo> getDocProperties() {
		return StorageManager.docProperties;
	}

	public boolean store(String word, List<String> lemma, String fileName,
			String headLine) throws IOException {
		//create properties
		String doc = fileName.replaceAll("[^\\d]", "");
		if (!docProperties.containsKey(doc)) {
			docProperties.put(doc, new DocumentInfo());
		}

		//set headline
		docProperties.get(doc).setHeadline(headLine);

		if (!StorageManager.stopwords.contains(word)) {
			int count = 0;

			for (String string : lemma) {
				count = this.lemmaMap.containsKey(string) ? this.lemmaMap
						.get(string) : 0;
				this.lemmaMap.put(string, count + 1);

				docProperties.get(doc).wordSet.add(string);
			}

			if (docProperties.get(doc).getMaxFreq() < count + 1) {
				docProperties.get(doc).setMaxFreq(count + 1);
			}
			
			int len = docProperties.get(doc).getDoclen();
			docProperties.get(doc).setDoclen(len + 1);
			return true;
		} 
		return false;//is stop words
	}
}
