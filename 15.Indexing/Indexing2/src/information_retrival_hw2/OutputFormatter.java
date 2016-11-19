package information_retrival_hw2;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OutputFormatter {
	private final List<String[]> rows = new LinkedList<String[]>();
	
	public OutputFormatter() {
		super();
	}

	public void addRow(final String... cols) {
		this.rows.add(cols);
	}

	private int[] getColWidths() {
		int cols = -1;
		for (final String[] row : this.rows) {
			cols = Math.max(cols, row.length);
		}

		final int[] widths = new int[cols];
		for (final String[] row : this.rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				widths[colNum] = Math.max(widths[colNum], StringUtils.length(row[colNum]));
			}
		}

		return widths;
	}

	@Override
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		final int[] colWidths = this.getColWidths();

		for (final String[] row : this.rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				buf.append(StringUtils.rightPad(StringUtils.defaultString(row[colNum]), colWidths[colNum]));
				buf.append('\t');
			}

			buf.append('\n');
		}
		return buf.toString();
	}
}