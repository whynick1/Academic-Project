package information_retrival_hw3;

import java.util.HashMap;
import java.util.Map;

public class WordInfo {
	public int docFreq;
	private Map<String, DocumentInfo> postingFile = new HashMap<>();
	private Map<String, Integer> termFreq = new HashMap<>();

	public Map<String, Integer> getTermFreq() {
		return this.termFreq;
	}

	public void setTermFreq(Map<String, Integer> termFreq) {
		this.termFreq = termFreq;
	}

	public void setDocFreq(int docFreq) {
		this.docFreq = docFreq;
	}

	public int getDocFreq() {
		return this.docFreq;
	}

	public Map<String, DocumentInfo> getPostingFile() {
		return this.postingFile;
	}

	public void setPostingFile(final Map<String, DocumentInfo> postingFile) {
		this.postingFile = postingFile;
	}

	@Override
	public String toString() {
		return "WordInfo [docFreq=" + docFreq + ", postingFile=" + postingFile
				+ ", termFreq=" + termFreq + "]";
	}

}