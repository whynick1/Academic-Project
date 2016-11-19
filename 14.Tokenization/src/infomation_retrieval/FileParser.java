package infomation_retrieval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FileParser {
	public static String parse(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		String message = null;
		try {
			//transfer XML into String
			@SuppressWarnings("resource")
			String xml = new Scanner(file).useDelimiter("\\Z").next();
			//eliminate XML tags
			db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			try {
				Document doc = db.parse(is);
				message = doc.getDocumentElement().getTextContent();
				//System.out.println("The message is: " + message);

			} catch (SAXException e) {
				// handle SAXException
			} catch (IOException e) {
				// handle IOException
			}
		} catch (ParserConfigurationException e1) {
			// handle ParserConfigurationException
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return message;
	}
}
