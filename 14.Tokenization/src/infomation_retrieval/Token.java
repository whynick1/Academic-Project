package infomation_retrieval;

public class Token implements Comparable<Token>{
	//global variables
	private int freq;
	private String word;

	//constructor
	public Token(String word, int freq) {
		this.word = word;
		this.freq = freq;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getFreq() {
		return freq;
	}
	
	public void setFreq(int newFreq) {
		freq = newFreq;
	}

	@Override
	public int compareTo(Token o) {
		// TODO Auto-generated method stub
		return this.freq - o.freq;
	}
}
