package davidBase;

import java.util.regex.Pattern;

public class ParseCmd {
	public static void run(String str) throws ProcessException
	{
		if (Pattern.matches("(?i)CREATE\\s+SCHEMA\\s+[a-zA-Z0-9]+", str))//down!
		{
			String[] command = str.split("\\s+|;");
			Command cmd = new CreateSchemaCmd(command[2]);
			cmd.execute();
		}
		
		else if (Pattern.matches("(?i)SHOW\\s+SCHEMAS", str))//done!
		{
			Command cmd = new ShowSchemasCmd();
			cmd.execute();
		}
		
		else if (Pattern.matches("(?i)USE\\s+[a-zA-Z0-9]+", str))//done!
		{
			String[] command = str.split("\\s+|;");
			Command cmd = new UseCmd(command[1]);
			cmd.execute();
		}
		
		else if (Pattern.matches("(?i)SHOW\\s+TABLES", str))//done!
		{
			Command cmd = new ShowTablesCmd();
			cmd.execute();
		}
		
		else if (Pattern.matches("(?i)CREATE\\s+TABLE\\s+[a-zA-Z0-9]+\\s*\\(\\s*.+\\s*\\)", str))
    	{
    		String table_name = str.split("\\s")[2];
    		String temp = str.replaceFirst("(?i)CREATE\\s+TABLE\\s+[a-zA-Z0-9]+\\s*\\(\\s*", "");
    		if (Pattern.matches("(?i).+\\)\\s*\\)", temp))
    			temp = temp.replaceAll("\\)\\s*\\)", ")");
    		String[] insertTuples = temp.split(",");
    		Command cmd = new CreateTableCmd(table_name, insertTuples);
			cmd.execute();
    	}
		
		else if (Pattern.matches("(?i)INSERT\\s+INTO\\s+TABLE\\s+[a-zA-Z0-9]+\\s+VALUES\\s+\\(\\s*.+\\s*\\)", str))
		{
			String table_name = str.split("\\s")[3];
    		String temp = str.replaceFirst("(?i)INSERT\\s+INTO\\s+TABLE\\s+[a-zA-Z0-9]+\\s+VALUES\\s+\\(\\s*", "");
    		temp = temp.replaceAll("\\s*\\)", "");
    		temp = temp.replaceAll("\\s", "");
    		String[] insertTuples = temp.split(",");
			Command cmd = new InsertCmd(table_name, insertTuples);
			cmd.execute();
		}

		else if (Pattern.matches("(?i)SELECT\\s+.+\\s+FROM\\s+[a-zA-Z0-9]+\\s+WHERE\\s+.+", str))
    	{
    		String temp1 = str.replaceFirst("(?i).+FROM\\s+", "");
    		String table_name = temp1.split("\\s")[0];
    		temp1 = temp1.replaceFirst("(?i).+WHERE\\s+", "");
    		String condition = temp1.split(";")[0];
    		
    		String temp2 = str.replaceFirst("(?i)\\s+FROM.+", "");
    		temp2 = temp2.replaceFirst("(?i)SELECT\\s+", "");
    		temp2 = temp2.replaceFirst("\\s", "");
    		String[] columns = temp2.split(",");
    		Command cmd = new SelectFromCmd(columns, table_name, condition);
			cmd.execute();
    	}
		
		else if (Pattern.matches("(?i)DROP\\s+TABLE\\s+.+", str))
		{
			String table_name = str.replaceFirst("(?i)DROP\\s+TABLE\\s+", "");
			Command cmd = new DropTableCmd(table_name);
			cmd.execute();
		}
		
		else if (Pattern.matches("(?i)\\s*exit\\s*", str))
			System.out.println("Exiting...");
		else	
			System.out.println("You have an error in your SQL syntax;");
	}
}









