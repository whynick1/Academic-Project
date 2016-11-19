package information_retrival_hw2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class CompressUtil {
	public CompressUtil() {
		super();
	}

	public static byte[] StringtoBytes(final String string) throws UnsupportedEncodingException {
		final BitSet bitSet = new BitSet(string.length());
		int index = 0;
		while (index < string.length()) {
			if (string.charAt(index) == '1') {
				bitSet.set(index);
			}
			index++;
		}

		final byte[] btob = new byte[(bitSet.length() + 7) / 8];
		int i = 0;
		while (i < bitSet.length()) {
			if (bitSet.get(i)) {
				btob[btob.length - i / 8 - 1] |= 1 << i % 8;
			}

			i++;
		}
		return btob;
	}

	public static byte[] gamma(final int number) throws UnsupportedEncodingException {
		final String unary = Integer.toBinaryString(number);
		String compressed = new String();
		int i = 1;
		while (i < unary.length()) {
			compressed = compressed + "1";
			i++;
		}

		compressed = compressed + "0" + unary.substring(1);
		final byte[] bytes = StringtoBytes(compressed);
		return bytes;
	}


	public static byte[] delta(final int number) throws UnsupportedEncodingException {
		final String unary = Integer.toBinaryString(number);
		final int len = unary.length();
		final String lenUnary = Integer.toBinaryString(len);
		String compressed = new String();
		int i = 1;
		while (i < lenUnary.length()) {
			compressed = compressed + "1";
			i++;
		}

		compressed = compressed + "0" + lenUnary.substring(1);
		compressed = compressed + unary.substring(1);
		final byte[] bytes = StringtoBytes(compressed);
		return bytes;
	}
	
	
	public static void blockCompress(final Map<String, WordInfo> dictionary, final File file, final File file2) throws FileNotFoundException, IOException {
		try (final RandomAccessFile accessFile = new RandomAccessFile(file, "rw"); PrintWriter writer = new PrintWriter(file2)) {
			List<String> words = new ArrayList<String>();
			int count = 0;
			for (final Entry<String, WordInfo> entry : dictionary.entrySet()) {
				if (count == 0) {
					final String compressed = StringUtils.join(words.toArray());
					words = new ArrayList<String>();

					writer.write(compressed + compressed.length());
				}
				if (count < 8) {
					words.add(entry.getKey());
					final byte[] df = gamma(entry.getValue().getDocFreq());
					accessFile.write(df);

					int prev = 0;
					final Map<String, DocumentInfo> tempPostingFile = entry.getValue().getPostingFile();
					for (final Entry<String, DocumentInfo> list : tempPostingFile.entrySet()) {
						final byte[] gap = gamma(Integer.parseInt(list.getKey()) - prev);
						accessFile.write(gap);
						prev = Integer.parseInt(list.getKey());

						final byte[] tf = gamma(entry.getValue().getTermFreq().get(list.getKey()));
						accessFile.write(tf);

						final byte[] doclen = gamma(list.getValue().getDoclen());
						accessFile.write(doclen);

						final byte[] max = gamma(list.getValue().getMaxFreq());
						accessFile.write(max);
					}

					count++;
				}
				if (count == 8) {
					count = 0;
				}
			}
		}
	}

	public static void frontCodingCompress(final Map<String, WordInfo> dictionary, final File file, final File file2)
			throws FileNotFoundException,
			IOException {
		final List<String> sortedList = new ArrayList<>();
		sortedList.addAll(dictionary.keySet());
		Collections.sort(sortedList);

		int minLen = Integer.MAX_VALUE;
		for (final String string : sortedList) {
			minLen = Math.min(minLen, string.length());
		}

		int prefixLen = 0;
		boolean breakFlag = false;
		final StringBuilder frontCodeString = new StringBuilder();

		try (final RandomAccessFile accessFile = new RandomAccessFile(file, "rw"); PrintWriter writer = new PrintWriter(file2)) {
			while (prefixLen < minLen) {
				final char cur = sortedList.get(0).charAt(prefixLen);
				for (int i = 1; i < sortedList.size(); i++) {
					if (!(sortedList.get(i).charAt(prefixLen) == cur)) {
						breakFlag = true;
						break;
					}
				}

				if (breakFlag) {
					break;
				}

				prefixLen++;
			}
			
			if (prefixLen >= 1) {
				frontCodeString.append(Integer.toString(sortedList.get(0).length())
						+ sortedList.get(0).substring(0, prefixLen) + "*"
						+ sortedList.get(0).substring(prefixLen));
				for (int i = 1; i < sortedList.size(); i++) {
					frontCodeString.append(Integer.toString(sortedList.get(i).length()
							- prefixLen)
							+ "|" + sortedList.get(i).substring(prefixLen));
				}
			} else {
				for (int i = 0; i < sortedList.size(); i++) {
					frontCodeString.append(Integer.toString(sortedList.get(i).length())
							+ sortedList.get(i));
				}
			}

			for (final String string : sortedList) {
				int prev = 0;
				final Map<String, DocumentInfo> tempPostingFile = dictionary.get(string).getPostingFile();
				for (final Entry<String, DocumentInfo> list : tempPostingFile.entrySet()) {
					final byte[] gap = delta(Integer.parseInt(list.getKey()) - prev);
					accessFile.write(gap);
					prev = Integer.parseInt(list.getKey());

					final byte[] tf = delta(dictionary.get(string).getTermFreq().get(list.getKey()));
					accessFile.write(tf);

					final byte[] doclen = delta(list.getValue().getDoclen());
					accessFile.write(doclen);

					final byte[] max = delta(list.getValue().getMaxFreq());
					accessFile.write(max);
				}
			}

			writer.write(frontCodeString.toString());
		}
	}
}
