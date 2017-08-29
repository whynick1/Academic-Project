package davidBase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public abstract class Command {
	public static String schema = "";
	public abstract void execute() throws ProcessException;
	public boolean EOF(long fileSize, int currentPointer)
	{
		if (fileSize <= currentPointer)
		{
			System.out.println("[EOF]");
			return true;
		}
		else
			return false;
	}
}

//done!!
class CreateSchemaCmd extends Command
{
	String schema_name;
	public CreateSchemaCmd(String schema_name) {
		this.schema_name = schema_name;
	}
	@Override
	public void execute()
	{
		RandomAccessFile schemataTableFile;
		try {
			if (SystemTables.existInSCHEMATA(schema_name))//check existence
			{
				System.out.println("Schema \"" + schema_name + "\" already exists.");
				return;
			}
			schemataTableFile = new RandomAccessFile("information_schema/information_schema.schemata.tbl", "rw");
			schemataTableFile.seek(schemataTableFile.length());//move pointer to the end
			schemataTableFile.writeByte(schema_name.length());
			schemataTableFile.writeBytes(schema_name);
			schemataTableFile.close();
			
			if (!SystemTables.IsDicitionaryExist(schema_name))//create dictionary if not exist
				System.out.println("schema:" + schema_name + " has been created.");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}

//done!!
class ShowSchemasCmd extends Command
{	
	@Override
	public void execute()
	{
		try {
			int indexFileLocation = 0;
			String tableName = "information_schema/information_schema.schemata.tbl";
			RandomAccessFile schemataTableFile = new RandomAccessFile(tableName, "rw");

			while(true) {
				if (EOF(schemataTableFile.length(), indexFileLocation))
					break;
				
				schemataTableFile.seek(indexFileLocation);
				Byte varcharLength = schemataTableFile.readByte();
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < varcharLength; i++)
				{
					sb.append((char)schemataTableFile.readByte());
				}
				System.out.println(sb.toString());
				indexFileLocation += 1;
				indexFileLocation = indexFileLocation + varcharLength.intValue();
			}
			schemataTableFile.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

//done!!
class UseCmd extends Command
{	
	String schema_name;
	public UseCmd(String schema_name) {
		this.schema_name = schema_name;
	}
	
	@Override
	public void execute()
	{
		try {
			if (SystemTables.existInSCHEMATA(schema_name))//check existence
			{
				schema = schema_name;
				System.out.println("Database changes to: " + schema);
			}
			else
				System.out.println(schema_name + ": is not a valid schema name.");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

//done!!
class ShowTablesCmd extends Command//done!
{	
	@Override
	public void execute()
	{
		try {
			int indexFileLocation = 0;
			String tableName = "information_schema/information_schema.table.tbl";
			RandomAccessFile tableTableFile = new RandomAccessFile(tableName, "rw");

			while(true) {
				if (EOF(tableTableFile.length(), indexFileLocation))
					break;
				
				tableTableFile.seek(indexFileLocation);
				Byte varcharLength1 = tableTableFile.readByte();
				StringBuilder sb1 = new StringBuilder();
				for(int i = 0; i < varcharLength1; i++)
				{
					sb1.append((char)tableTableFile.readByte());
				}
				
				Byte varcharLength2 = tableTableFile.readByte();
				StringBuilder sb2 = new StringBuilder();
				for(int i = 0; i < varcharLength2; i++)
				{
					sb2.append((char)tableTableFile.readByte());
				}
				Long table_row = tableTableFile.readLong();
				
				indexFileLocation += 10;//1 byte + 1 byte + 8 long = 10
				indexFileLocation = indexFileLocation + varcharLength1.intValue() + varcharLength2.intValue();
				
				//show a tuple of system table
				//if table_name matches "XXXXX..." means the table has been delete
				if (Pattern.matches("[Z]+", sb2.toString()))
					continue;
				System.out.print(sb1.toString() + "  \t");
				System.out.print(sb2.toString() + "  \t");
				System.out.println(table_row);
			}
			tableTableFile.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

class CreateTableCmd extends Command
{	
	String[] insertTuples;
	String table_name;
	public CreateTableCmd(String table_name, String[] insertTuples) {
		this.table_name = table_name;
		this.insertTuples = insertTuples;
	}
	@Override
	public void execute()
	{
		if (schema.equals(""))//missing: use xxx beforehand
			System.out.println("error: you must choose schema first.");
		else//normal state
		{
			//System.out.println(schema);
			//System.out.println(table_name);
			List<String> column_name = new ArrayList<String>();
			List<String> column_type = new ArrayList<String>();
			List<String> is_nullable = new ArrayList<String>();
			List<String> column_key = new ArrayList<String>();
			for (int i = 0; i < insertTuples.length; i++)
			{
				String[] temp = insertTuples[i].trim().split("\\s");
				column_name.add(temp[0]);
				column_type.add(temp[1]);
				if (temp.length <= 2)//if column is empty
				{
					is_nullable.add("YES");
					column_key.add("");
				}
				else if (Pattern.matches("(?i)primary", temp[2]))
				{
					is_nullable.add("NO");
					column_key.add("PRI");
				}
				else if (Pattern.matches("(?i)not", temp[2]))
				{
					is_nullable.add("NO");
					column_key.add("");
				}
				else
				{
					is_nullable.add("YES");
					column_key.add("");
				}
			}
			//check if any column have the same name (violate rules)
			for (int i = 0; i < column_name.size() - 1; i++)
			{
				if (column_name.get(i).equals(column_name.get(i + 1)))
				{
					System.out.println("Error: Duplicate column name found!");
					return;
				}
			}
			SystemTables.updateSystemTABLES(schema, table_name);
			SystemTables.updateSystemCOLUMNS(schema, table_name, column_name, column_type, is_nullable, column_key); 
		}
	}
}

class InsertCmd extends Command
{	
	String[] insertTuples;
	String table_name;
	public InsertCmd(String table_name, String[] insertTuples) {
		this.table_name = table_name;
		this.insertTuples = insertTuples;
	}
	@Override
	public void execute()
	{
		if (schema.equals(""))//missing: use xxx beforehand
			System.out.println("error: you must choose schema first.");
		
		else if (!SystemTables.existInTABLES(schema, table_name))//can not insert if table not exist
		{
			System.out.println("Error: Table \"" + table_name + "\" not exists.");
		}
		
		else
		{
			//get each column's value
			List<String> temp = new ArrayList<String>();
			for (int i = 0; i < insertTuples.length; i++)
				temp.add(insertTuples[i].trim());
			
			//check column type validation
			if (SystemTables.InsertTypeCheck(schema, table_name, temp))
			{
				System.out.println("insert check pass!!!");
				
				//updateSystemTABLE_ROW() + 1
				SystemTables.incrementSystemTABLE_ROW(schema, table_name);//done!
				
				//write to each index file and table file	
				SystemTables.insertIntoTable(schema, table_name, temp);
			}
		}
	}
}

class SelectFromCmd extends Command
{	
	String[] columns;
	String table_name;
	String condition;
	public SelectFromCmd(String[] columns, String table_name, String condition)
	{
		this.columns = columns;
		this.table_name = table_name;
		this.condition = condition;
	}
	@Override
	public void execute()
	{
		try {
			SystemTables.SelectFromTable(schema, table_name, condition);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

class DropTableCmd extends Command
{	
	String table_name;
	public DropTableCmd(String table_name)
	{
		this.table_name = table_name;
	}
	@Override
	public void execute()
	{
		String deleteTableName = schema + "/" + schema + "." + table_name + "tbl";
		if (SystemTables.dropTable(schema, table_name))//found corresponding table
		{
		    File f = new File(deleteTableName);
		    f.delete();
		    System.out.println("Drop successful.");	
		}
		else
			System.out.println("Error: Table \'" + table_name + "\' does not exist.");
	}
}










