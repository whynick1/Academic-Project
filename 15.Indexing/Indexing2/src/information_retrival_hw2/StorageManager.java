package information_retrival_hw2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StorageManager {
	private  Map<String, Integer>	stemsMap;
	private  Map<String, Integer>	lemmaMap;
	private static Map<String, DocumentInfo> docProperties	= new HashMap<>();;
	private static Set<String>	stopwords;

	public StorageManager( Set<String> stopwords) {
		this.stemsMap = new HashMap<>();
		this.lemmaMap = new HashMap<>();
		StorageManager.stopwords = stopwords;
	}
	
	@Override
	public String toString() {
		return "StorageManager [stemsMap=" + stemsMap + ", lemmaMap="
				+ lemmaMap + "]";
	}

	public  Map<String, Integer> getStemsMap() {
		return this.stemsMap;
	}

	public  Map<String, Integer> getLemmaMap() {
		return this.lemmaMap;
	}

	public static Map<String, DocumentInfo> getDocProperties() {
		return StorageManager.docProperties;
	}
	
	public void store( String word,  List<String> lemma,  String stem, String fileName) {
		 String doc = fileName.replaceAll("[^\\d]", "");
		if (!StorageManager.docProperties.containsKey(doc)) {
			docProperties.put(doc, new DocumentInfo());
		}

		if (!StorageManager.stopwords.contains(word)) {
			int count = this.stemsMap.containsKey(stem) ? this.stemsMap.get(stem) : 0;
			this.stemsMap.put(stem, count + 1);

			for ( String string : lemma) {
				count = this.lemmaMap.containsKey(string) ? this.lemmaMap.get(string) : 0;
				this.lemmaMap.put(string, count + 1);
			}

			if (StorageManager.docProperties.get(doc).getMaxFreq() < count + 1) {
				StorageManager.docProperties.get(doc).setMaxFreq(count + 1);
			}
		}
		 int len = StorageManager.docProperties.get(doc).getDoclen();
		StorageManager.docProperties.get(doc).setDoclen(len + 1);
	}
}
