package information_retrival_hw3;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OutputFormatter {
	private List<String[]> rows = new LinkedList<String[]>();
	
	public OutputFormatter() {
		super();
	}

	public void addRow(String... cols) {
		this.rows.add(cols);
	}

	private int[] getColWidths() {
		int cols = -1;
		for (String[] row : this.rows) {
			cols = Math.max(cols, row.length);
		}

		int[] widths = new int[cols];
		for (String[] row : this.rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				widths[colNum] = Math.max(widths[colNum], StringUtils.length(row[colNum]));
			}
		}

		return widths;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		int[] colWidths = this.getColWidths();

		for (String[] row : this.rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				buf.append(StringUtils.rightPad(StringUtils.defaultString(row[colNum]), colWidths[colNum]));
				buf.append('\t');
			}

			buf.append('\n');
		}
		return buf.toString();
	}
}