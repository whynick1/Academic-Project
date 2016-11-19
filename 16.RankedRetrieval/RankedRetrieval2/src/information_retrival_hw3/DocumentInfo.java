package information_retrival_hw3;

import java.util.HashSet;
import java.util.Set;

public class DocumentInfo {
	private int	doclen;
	private int	maxFreq;
	private String	headline;
	public Set<String> wordSet;

	public DocumentInfo() {
		this.wordSet = new HashSet<>();
	}
	
	public int getDoclen() {
		return this.doclen;
	}

	public void setDoclen(int doclen) {
		this.doclen = doclen;
	}

	public int getMaxFreq() {
		return this.maxFreq;
	}

	public void setMaxFreq(int maxFreq) {
		this.maxFreq = maxFreq;
	}

	@Override
	public String toString() {
		return "DocumentInfo [doclen=" + doclen + ", maxFreq=" + maxFreq + "]";
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public String getHeadline() {
		return this.headline;
	}
}