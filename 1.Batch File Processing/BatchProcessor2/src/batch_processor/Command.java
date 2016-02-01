package batch_processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import batch_processor.ProcessException; 
import batch_processor.Batch;

public abstract class Command
{	
	public String id, path;
	
	public List<String> cmdArgs; //for pipe CmdCommand
	public String inID;
	public String outID;
	public List<String> cmdArgs2; //for pipe command
	public String id1,id2;
	
	public abstract void describe();
	public abstract void parse(Element elem)throws ProcessException;
	public abstract void execute(String workingDir) throws ProcessException;
}

class WDCommand extends Command
{	
	@Override
	public void describe()
	{System.out.println("The working directory will be set to work");}

	@Override
	public void parse(Element elem)
	{
		id = elem.getAttribute("id");
		path = elem.getAttribute("path");
	}
	@Override
	public void execute(String workingDir)
	{describe();}
}

class FileCommand extends Command
{
	@Override
	public void describe()
	{System.out.println("File Command on file: "+path);}
	@Override
	public void parse(Element elem) throws ProcessException
	{
		id = elem.getAttribute("id");
		path = elem.getAttribute("path");
	}
	@Override
	public void execute(String workingDir)
	{describe();}	
}

class CmdCommand extends Command
{
	@Override
	public void describe()
	{System.out.println("Command " + id + " start to excute");}

	@Override
	public void parse(Element elem)
	{
		try {
			parseCmd(elem);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void parseCmd(Element elem) throws ProcessException
	{
		id = elem.getAttribute("id");	
		path = elem.getAttribute("path");
		
		cmdArgs = new ArrayList<String>(); 
		cmdArgs.add(path);
		
		String arg = elem.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}
		inID = elem.getAttribute("in");
		outID = elem.getAttribute("out");
	}
	
	@Override
	public void execute(String workingDir)throws ProcessException
	{
		describe();
		List<String> command = new ArrayList<String>();	
		command = cmdArgs; //first step: to input other parts
		
		//=======through Batch Instance visit Map=======
		Batch bat = Batch.getInstance();
		Map<String, Command> map = bat.getCommands();
		//////////////////////////
		//delete if statement!
		// if(map.get(inID)!=null)
		// 	command.add(map.get(inID).path);
		//////////////////////////

		if (!inID.equals("") && map.get(inID)==null) throw new ProcessException("Cannot Find The ID  " + inID );
		if (!outID.equals("") && map.get(outID)==null) throw new ProcessException("Cannot Find The ID  " + outID );
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(new File(workingDir));	//set the running directory of the process
		File wd = builder.directory();				//obtain the current directory
		
		//////////////////////////
		//add new if statement!
		File redirct = new File(wd, map.get(inID).path);
		if (map.get(inID) != null) 
		{
			builder.redirectInput (redirct);
		}
		//////////////////////////

		String outfile = map.get(outID).path;
		File outFile1 = new File(wd, outfile);
		builder.redirectOutput(outFile1);	//output to the outFile
			
		try {	
			Process process = builder.start();
			process.waitFor();
			System.out.println("Command " + id + " has been executed");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			//throw new ProcessException("error executing cmd " + path + e.getMessage());			
		}
	}	
}

class PipeCommand extends Command
{
	@Override
	public void describe()
	{{System.out.println("Pipe command");}}

	@Override
	public void parse(Element elem)throws ProcessException
	{
		id = elem.getAttribute("id"); // add id of pipe which is "pipe1"
//		check();
		NodeList nodes = elem.getChildNodes(); 
		cmdArgs = new ArrayList<String>(); 
		
		int count = 0;
		//nodes.getLength()
		for(int idx = 0; idx < nodes.getLength(); idx++) 
		{
			Node node = nodes.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element elem1 = (Element) node;
				//System.out.println(node);			
				path = elem1.getAttribute("path");				
				count++;
				if(count==1)
				{		
					id1 = elem1.getAttribute("id");
					cmdArgs = new ArrayList<String>();
					cmdArgs.add(path);				
					String arg = elem1.getAttribute("args");
					if (!(arg == null || arg.isEmpty())) {
						StringTokenizer st = new StringTokenizer(arg);
						while (st.hasMoreTokens()) {
							String tok = st.nextToken();
							cmdArgs.add(tok);
						}
					}
					inID = elem1.getAttribute("in"); 
				}
				else {
					id2 = elem1.getAttribute("id");
					cmdArgs2 = new ArrayList<String>();
					cmdArgs2.add(path);			
					String arg = elem1.getAttribute("args");
					if (!(arg == null || arg.isEmpty())) {
						StringTokenizer st = new StringTokenizer(arg);
						while (st.hasMoreTokens()) {
							String tok = st.nextToken();
							cmdArgs2.add(tok);
						}
					}
					outID = elem1.getAttribute("out");	
				}			
			}
		}
	}
	
	@Override
	public void execute(String workingDir)
	{	
		try{
		describe();
		//======================Process1 : command1======================
		File temp = File.createTempFile("temp", ".txt"); 
		
		System.out.println("Waiting for command '" + id1 + "' to exit");
		List<String> command = new ArrayList<String>();	
		command = cmdArgs;
		Batch bat = Batch.getInstance();
		Map<String, Command> map = bat.getCommands();
		if (!inID.equals("") && map.get(inID)==null) throw new ProcessException("Cannot Find The ID  " + inID );
		command.add(map.get(inID).path); 
		
		ProcessBuilder builder1 = new ProcessBuilder(command);
		builder1.directory(new File(workingDir));
		final Process process1 = builder1.start();
		
		FileOutputStream fos = new FileOutputStream(temp); //
		InputStream is = process1.getInputStream();
		copyStreams(is, fos);
		System.out.println(id1 + "has exited");
		
		//======================Process2 : command2======================
		if (!outID.equals("") && map.get(outID)==null) throw new ProcessException("Cannot Find The ID  " + outID );
		File output = new File(workingDir, map.get(outID).path);
		System.out.println("Waiting for command '" + id2 + "' to exit");
		command.clear();
		command = cmdArgs2;

		ProcessBuilder builder2 = new ProcessBuilder(command);
		builder2.directory(new File(workingDir));
		builder2.redirectInput(temp);
		builder2.redirectOutput(output);
		builder2.start();
		System.out.println(id2 + "has exited");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			//throw new ProcessException("error executing cmd " + path + e.getMessage());			
		}
	}
	
	/*Copy the contents of the input stream to the output stream in separate thread.
	 *  The thread ends when an EOF is read from the input stream.*/
	static void copyStreams(final InputStream is, final OutputStream os) 
	{
		Runnable copyThread = (new Runnable() {
			public void run()
			{
				try {
					int achar;
					while ((achar = is.read()) != -1) {
						os.write(achar);
					}
					os.close();
				}
				catch (IOException ex) {
					throw new RuntimeException(ex.getMessage(), ex);
				}
			}
		});
		new Thread(copyThread).start();
	}
	/**/
}/**/


