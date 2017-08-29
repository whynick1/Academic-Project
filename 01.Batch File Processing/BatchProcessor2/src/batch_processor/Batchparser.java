package batch_processor;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document; 
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Batchparser
{	
	private String argument; 
	public Batchparser(String args)	//Constructor
	{argument = args;}	
	
	public Batch buildBatch()
	{
		Batch bat = Batch.getInstance(); //important: obtain instance of Batch
		
		Command cmd = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			File f = new File(argument); 				
			FileInputStream fis = new FileInputStream(f);
			Document doc = dBuilder.parse(fis);			// use java. IO to read XML
	
			Element pnode = doc.getDocumentElement(); 	// get the element of document 
			NodeList nodes = pnode.getChildNodes();		
			for (int idx = 0; idx < nodes.getLength(); idx++) 
			{
				Node node = nodes.item(idx);			
				if (node.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element elem = (Element) node;
					//System.out.println(node);
					//===============important£¡===============
					cmd = buildCommand(elem);	
					bat.addCommand(cmd);	
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return bat;
	}
	
	public Command buildCommand(Element elem) throws ProcessException
	{	
		Command cmd = null;
		String cmdName = elem.getNodeName(); //get the name of node
		
		if (cmdName == null) {
			throw new ProcessException("unable to parse command from " + elem.getTextContent());
		}
		else if ("wd".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing wd");
			cmd = new WDCommand(); 
			cmd.parse(elem);
		}
		else if ("file".equalsIgnoreCase(cmdName)) {	
			System.out.println("Parsing file");
			cmd = new FileCommand();
			cmd.parse(elem);
		}
		else if ("cmd".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing cmd");
			cmd = new CmdCommand();
			cmd.parse(elem);
		}
		else if ("pipe".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing pipe");
			cmd = new PipeCommand();
			cmd.parse(elem);
		}
		else {
			throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
		}
		return cmd;
	}
}