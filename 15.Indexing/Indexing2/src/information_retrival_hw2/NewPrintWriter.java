package information_retrival_hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class NewPrintWriter {
	public NewPrintWriter() {
		super();
	}

	public File write(final Map<String, WordInfo> dictionary, final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		final File file = new File(fileName);
		try (final PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			for (final Entry<String, WordInfo> entry : dictionary.entrySet()) {
				writer.print(entry.getKey() + ":" + entry.getValue().getDocFreq() + " <");

				final List<String> pairs = new ArrayList<>();
				for (final Entry<String, DocumentInfo> list : entry.getValue().getPostingFile().entrySet()) {
					pairs.add(list.getKey() + ":" + entry.getValue().getTermFreq().get(list.getKey()) + " " + list.getValue().getMaxFreq() + " " +
							list.getValue().getDoclen());
				}
				writer.print(StringUtils.join(pairs, " "));
				writer.println(">\n");
			}
		}

		return file;
	}
}