package information_retrival_hw3;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Dictionary {
	private Map<String, WordInfo> dictionaryStem;
	public Map<String, WordInfo> dictionaryLemma;

	public Dictionary() {
		this.dictionaryStem = new HashMap<>();
		this.dictionaryLemma = new HashMap<>();
	}

	public void add(StorageManager manager, File file) {
		String doc = file.getName().replaceAll("[^\\d]", "");
		this.addToDictionary(this.dictionaryStem, manager.getStemsMap(), StorageManager.getDocProperties(), doc);
		this.addToDictionary(this.dictionaryLemma, manager.getLemmaMap(), StorageManager.getDocProperties(), doc);
	}

	private void addToDictionary(Map<String, WordInfo> appendTo,
			Map<String, Integer> appendFrom,
			Map<String, DocumentInfo> docProperties,
			String file) {
		for (Entry<String, Integer> entry : appendFrom.entrySet()) {
			WordInfo temp;
			if (appendTo.containsKey(entry.getKey())) {
				temp = appendTo.get(entry.getKey());
				temp.setDocFreq(temp.getDocFreq() + 1);

				Map<String, Integer> freq = temp.getTermFreq();
				freq.put(file, entry.getValue());
				temp.setTermFreq(freq);
			} else {
				temp = new WordInfo();
				temp.setDocFreq(1);
				temp.getTermFreq().put(file, entry.getValue());
			}

			DocumentInfo property = docProperties.get(file);
			temp.getPostingFile().put(file, property);
			appendTo.put(entry.getKey(), temp);
		}
	}

	public Map<String, WordInfo> getStemsDictionary() {
		return dictionaryStem;
	}

	public Map<String, WordInfo> getLemmaDictionary() {
		return dictionaryLemma;
	}
}