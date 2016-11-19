package information_retrival_hw3;

import java.util.HashMap;
import java.util.Map;

public class TermWeightCalculater {
	private Map<String, WordInfo> termIndexing;
	public Map<String, Double> w1, w2;
	public Map<String, Map<String, Double>> Doc_W1, Doc_W2;
	private double avgDocLen;

	public TermWeightCalculater(Map<String, WordInfo> termIndexing, double avgdoclen) {
		this.termIndexing = termIndexing;
		this.w1 = new HashMap<>();
		this.w2 = new HashMap<>();
		this.Doc_W1 = new HashMap<>();
		this.Doc_W2 = new HashMap<>();
		this.avgDocLen = avgdoclen;
	}

	/**
	 * This function is used to calculate the w1 & w2 of query
	 * @param: Dictionary query
	 */
	public void process2(Dictionary query) {
		int collectionSize = this.termIndexing.size();
		for (String term : this.termIndexing.keySet()) {
			WordInfo properties = this.termIndexing.get(term);
			int df = properties.getDocFreq();
			Map<String, DocumentInfo> postingFile = properties.getPostingFile();

			for (String docID : postingFile.keySet()) {
				int maxtf = 1;
				int doclen = query.dictionaryLemma.size();
				int tf = 1;
//				System.out.println("[query]DocId: " + docID + " Check doclen (within process()): " + doclen + " " + this.avgdoclen);
				
				//calculate w1
				double w1 = this.getW1(tf, maxtf, df, collectionSize);
				double weight = this.w1.containsKey(term) ? this.w1.get(term) : 0.0;
				this.w1.put(term, weight + w1);

				//calculate w2
				double w2 = this.getW2(tf, doclen, this.avgDocLen, df, collectionSize);
				weight = this.w2.containsKey(term) ? this.w2.get(term) : 0.0;
				this.w2.put(term, weight + w2);
			}
		}
	}

	/**
	 * This function is used to calculate the w1 & w2 of document base on query
	 * @param: Dictionary query
	 */
	public void process1(Dictionary query) {
		int collectionSize = this.termIndexing.size();
		for (String term : query.getLemmaDictionary().keySet()) {
			WordInfo properties = this.termIndexing.get(term);
			if (properties == null) {
				continue;
			}

			int df = properties.getDocFreq();
			Map<String, DocumentInfo> postingFile = properties.getPostingFile();
			
			for (String docID : postingFile.keySet()) {
				int maxtf = postingFile.get(docID).getMaxFreq();
				int doclen = postingFile.get(docID).getDoclen();
//				System.out.println("[document]Check doclen (within process()): " + doclen + " " + this.avgdoclen);
				
				int tf = properties.getTermFreq().get(docID);
				this.updateWeights(collectionSize, term, df, docID, maxtf, doclen, tf);
			}
		}
	}

	private void updateWeights(int collectionSize, String term, int df, String docID, int maxtf, int doclen, int tf) {
		double w1 = this.getW1(tf, maxtf, df, collectionSize);
		double weight = this.w1.containsKey(docID) ? this.w1.get(docID) : 0.0;
		this.w1.put(docID, weight + w1);

		if (this.Doc_W1.get(docID) == null) {
			this.Doc_W1.put(docID, new HashMap<String, Double>());
		}

		weight = this.Doc_W1.get(docID).containsKey(term) ? this.Doc_W1.get(docID).get(term) : 0.0;
		this.Doc_W1.get(docID).put(term, weight + w1);

		double w2 = this.getW2(tf, doclen, this.avgDocLen, df, collectionSize);
		weight = this.w2.containsKey(docID) ? this.w2.get(docID) : 0.0;
		this.w2.put(docID, weight + w2);

		if (this.Doc_W2.get(docID) == null) {
			this.Doc_W2.put(docID, new HashMap<String, Double>());
		}

		weight = this.Doc_W2.get(docID).containsKey(term) ? this.Doc_W2.get(docID).get(term) : 0.0;
		this.Doc_W2.get(docID).put(term, weight + w2);
	}

	public double getW1(int tf, int maxtf, int df, int collectionSize) {
		double temp = 0;
		try {
			temp = (0.4 + 0.6 * Math.log(tf + 0.5) / Math.log(maxtf + 1.0)) * (Math.log(collectionSize / (double) df) / Math.log(collectionSize));
		} catch (Exception e) {
			temp = 0;
		}

		return temp;
	}

	public double getW2(int tf, int doclen, double avgdoclen, int df, int collectionSize) {
		double temp = 0;
		try {
			temp = 0.4 + 0.6 * (tf / (tf + 0.5 + 1.5 * (doclen / avgdoclen))) * Math.log(collectionSize / (double) df) / Math.log(collectionSize);
		} catch (Exception e) {
			temp = 0;
		}

		return temp;
	}
}