package batch_processor;

import java.util.Map;

public class BatchProcessor
{
	public static void main(String[] args) throws Exception
	{
		String filename = null;
		if(args.length > 0) {
			filename = args[0];
		}
		else {			
//			filename = "work/batch1.dos.xml";
			//filename = "work/batch2.dos.xml";
//			filename = "work/batch3.xml";
			filename = "work/batch4.xml";
//			filename = "work/batch5.broken.xml";
		}

		Batchparser bp = new Batchparser(filename);
		executeBatch(bp.buildBatch());
	}
	
	public static void executeBatch(Batch bat) throws Exception
	{
		Map<String, Command> map = bat.getCommands();
		
		String workPath;
		if(map.get("swd1")!=null) //chech directory 
			workPath = map.get("swd1").path; //if id=sw1, get directory 
		else return;/**/
		
		/*for (int i=0; i<6; i++) 
		{
			//cmd.execute(batch.getWorkingDir());
		}*/
		
		for (String key : map.keySet()) // output by order with "LinkedHashMap" class
		{
			map.get(key).execute(workPath);
	    }		

		System.out.println("Program terminated!");
	}
}
