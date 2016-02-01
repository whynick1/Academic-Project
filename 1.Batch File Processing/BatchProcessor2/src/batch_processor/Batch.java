package batch_processor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Batch
{
	private Map<String, Command> commands;
	
	/*private static Batch batch_instance = null;
	public static Batch getInstance() 
	{
		if(batch_instance == null)
			batch_instance = new Batch();
		return batch_instance;
	}*/	
	
	private static class BatchInstance 
	{ 
		private static final Batch instance = new Batch(); 
	} 

	public static Batch getInstance() { 
		return BatchInstance.instance; 
	}
	
	private Batch()	// Notice "private"
	{	
		//commands = new HashMap<String, Command>();
		commands = new LinkedHashMap<String, Command>(); //LinkedHashMap can be output by order
	}
	
	public void addCommand(Command command)
	{
		commands.put(command.id, command);
	}/**/

	public Map<String, Command> getCommands()
	{
		return commands;
	}
}