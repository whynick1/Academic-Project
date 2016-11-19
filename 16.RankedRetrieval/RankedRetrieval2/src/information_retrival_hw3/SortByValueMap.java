package information_retrival_hw3;

import java.util.Comparator;
import java.util.Map;

public class SortByValueMap implements Comparator<String> {
	private Map<String, Double> map;

	public SortByValueMap(Map<String, Double> queryMap) {
		this.map = queryMap;
	}

	@Override
	public int compare(String s1, String s2) {
		if (this.map.get(s1) > this.map.get(s2))
			return 1;
		else if (this.map.get(s1) < this.map.get(s2))
			return -1;
		return 0;
	}
}
