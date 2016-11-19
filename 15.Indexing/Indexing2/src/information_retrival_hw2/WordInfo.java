package information_retrival_hw2;


import java.util.HashMap;
import java.util.Map;

public class WordInfo {
	private int	docFreq;
	private Map<String, DocumentInfo> postingFile = new HashMap<>();
	private Map<String, Integer> termFreq = new HashMap<>();

	public final Map<String, Integer> getTermFreq() {
		return this.termFreq;
	}

	public final void setTermFreq(final Map<String, Integer> termFreq) {
		this.termFreq = termFreq;
	}

	public final void setDocFreq(final int docFreq) {
		this.docFreq = docFreq;
	}

	public final int getDocFreq() {
		return this.docFreq;
	}
	
	public final Map<String, DocumentInfo> getPostingFile() {
		return this.postingFile;
	}

	public final void setPostingFile(final Map<String, DocumentInfo> postingFile) {
		this.postingFile = postingFile;
	}

	@Override
	public String toString() {
		return "WordInfo [docFreq=" + docFreq + ", postingFile=" + postingFile
				+ ", termFreq=" + termFreq + "]";
	}

}