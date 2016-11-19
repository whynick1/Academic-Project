package information_retrival_hw2;

public class DocumentInfo {
	private int	doclen;
	private int	maxFreq;

	public final int getDoclen() {
		return this.doclen;
	}

	public final void setDoclen(final int doclen) {
		this.doclen = doclen;
	}

	public final int getMaxFreq() {
		return this.maxFreq;
	}

	public final void setMaxFreq(final int maxFreq) {
		this.maxFreq = maxFreq;
	}

	@Override
	public String toString() {
		return "DocumentInfo [doclen=" + doclen + ", maxFreq=" + maxFreq + "]";
	}
}