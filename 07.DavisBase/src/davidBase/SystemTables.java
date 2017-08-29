package davidBase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SystemTables {
	private static boolean EOF(long fileSize, long currentPointer)
	{
		if (fileSize <= currentPointer)
		{
			//System.out.println("[EOF]");
			return true;
		}
		else
			return false;
	}
	
	public static List<String> getSystemColumnTABLE_SCHEMA()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				StringBuilder sb1 = new StringBuilder();
				for(int i = 0; i < varcharLength1; i++)
				{
					sb1.append((char)columnsTableFile.readByte());
				}
				lst.add(sb1.toString());
				
				Byte varcharLength2 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength2);//skip TABLE_NAME
				
				Byte varcharLength3 = columnsTableFile.readByte();//skip COLUMNS NAME
				columnsTableFile.skipBytes(varcharLength3 + 4);//skip ORDINAL_POSITION
					
				Byte varcharLength4 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength4);//skip COLUMNS TYPE
					
				Byte varcharLength5 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength5);//skip IS_NULLABLE
				
				Byte varcharLength6 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength6);//skip COLLUMN KEY
			}
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<String> getSystemColumnTABLE_NAME()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength1);//skip TABLE_SCHEMA

				Byte varcharLength2 = columnsTableFile.readByte();
				StringBuilder sb2 = new StringBuilder();
				for(int i = 0; i < varcharLength2; i++)
				{
					sb2.append((char)columnsTableFile.readByte());
				}
				lst.add(sb2.toString());

				Byte varcharLength3 = columnsTableFile.readByte();//skip COLUMNS NAME
				columnsTableFile.skipBytes(varcharLength3 + 4);//skip ORDINAL_POSITION
					
				Byte varcharLength4 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength4);//skip COLUMNS TYPE
					
				Byte varcharLength5 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength5);//skip IS_NULLABLE
				
				Byte varcharLength6 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength6);//skip COLLUMN KEY
			}
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<String> getSystemColumnCOLUMN_NAME()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength1);//skip TABLE_SCHEMA

				Byte varcharLength2 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength2);//skip TABLE_NAME
				
				Byte varcharLength3 = columnsTableFile.readByte();
				StringBuilder sb2 = new StringBuilder();
				for(int i = 0; i < varcharLength3; i++)
				{
					sb2.append((char)columnsTableFile.readByte());
				}
				lst.add(sb2.toString());

				columnsTableFile.skipBytes(4);//skip ORDINAL_POSITION
					
				Byte varcharLength4 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength4);//skip COLUMNS TYPE
					
				Byte varcharLength5 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength5);//skip IS_NULLABLE
				
				Byte varcharLength6 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength6);//skip COLLUMN KEY
			}
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
		
	public static List<String> getSystemColumnCOLUMN_TYPE()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength1);//skip TABLE_SCHEMA

				Byte varcharLength2 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength2);//skip TABLE_NAME
				
				Byte varcharLength3 = columnsTableFile.readByte();//skip COLUMN_NAME
				columnsTableFile.skipBytes(varcharLength3 + 4);//skip ORDINAL_POSITION
				
				Byte varcharLength4 = columnsTableFile.readByte();
				StringBuilder sb4 = new StringBuilder();
				for (int i = 0; i < varcharLength4; i++)
				{
					sb4.append((char)columnsTableFile.readByte());
				}
				lst.add(sb4.toString());
					
				Byte varcharLength5 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength5);//skip IS_NULLABLE
				
				Byte varcharLength6 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength6);//skip COLLUMN KEY
			}
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<String> getSystemColumnIS_NULLABLE()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength1);//skip TABLE_SCHEMA

				Byte varcharLength2 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength2);//skip TABLE_NAME
				
				Byte varcharLength3 = columnsTableFile.readByte();//skip COLUMN_NAME
				columnsTableFile.skipBytes(varcharLength3 + 4);//skip ORDINAL_POSITION
				
				Byte varcharLength4 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength4);//skip COLUMN_TYPE
				
				Byte varcharLength5 = columnsTableFile.readByte();
				StringBuilder sb5 = new StringBuilder();
				for (int i = 0; i < varcharLength5; i++)
				{
					sb5.append((char)columnsTableFile.readByte());
				}
				lst.add(sb5.toString());
				
				Byte varcharLength6 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength6);//skip COLLUMN KEY
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<String> getSystemColumnCOLUMN_KEY()
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read system COLUMNS file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				//read schema_name and table_name
				Byte varcharLength1 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength1);//skip TABLE_SCHEMA

				Byte varcharLength2 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength2);//skip TABLE_NAME
				
				Byte varcharLength3 = columnsTableFile.readByte();//skip COLUMN_NAME
				columnsTableFile.skipBytes(varcharLength3 + 4);//skip ORDINAL_POSITION
				
				Byte varcharLength4 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength4);//skip COLUMN_TYPE
				
				Byte varcharLength5 = columnsTableFile.readByte();
				columnsTableFile.skipBytes(varcharLength5);//skip IS_NULLABLE
				
				Byte varcharLength6 = columnsTableFile.readByte();
				StringBuilder sb6 = new StringBuilder();
				for (int i = 0; i < varcharLength6; i++)
				{
					sb6.append((char)columnsTableFile.readByte());
				}
				lst.add(sb6.toString());
			}
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<Byte> ReadByteIndexFile(String schema_name, String table_name, String column_name)//To be check
	{
		List<Byte> lst = new ArrayList<Byte>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				lst.add(columnsTableFile.readByte());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<Short> ReadShortIndexFile(String schema_name, String table_name, String column_name)//To be check
	{
		List<Short> lst = new ArrayList<Short>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				lst.add(columnsTableFile.readShort());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<Integer> ReadIntIndexFile(String schema_name, String table_name, String column_name)//To be check
	{
		List<Integer> lst = new ArrayList<Integer>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				lst.add(columnsTableFile.readInt());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<Long> ReadLongIndexFile(String schema_name, String table_name, String column_name)//To be check
	{
		List<Long> lst = new ArrayList<Long>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				lst.add(columnsTableFile.readLong());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<String> ReadCharsIndexFile(String schema_name, String table_name, String column_name, int length)//To be check
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;
				
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < length; i++)
				{
					sb.append((char)columnsTableFile.readByte());
				}
				lst.add(sb.toString());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static List<String> ReadStringIndexFile(String schema_name, String table_name, String column_name)//To be check
	{
		List<String> lst = new ArrayList<String>();
		try {
			String file_name = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			//read index file
			while(true) {
				if (EOF(columnsTableFile.length(), columnsTableFile.getFilePointer()))
					break;

				Byte varcharLength = columnsTableFile.readByte();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < varcharLength; i++)
				{
					sb.append((char)columnsTableFile.readByte());
				}
				lst.add(sb.toString());
				columnsTableFile.skipBytes(8);//skip long pointer
			}
//			for (int i = 0; i < lst.size(); i++)
//				System.out.println(lst.get(i));
			columnsTableFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public static boolean InsertTypeCheck(String schema_name, String table_name, List<String> typesToBeCheck)//To be check
	{
		try {
			List<String> tab_schema = getSystemColumnTABLE_SCHEMA();
			List<String> tab_name = getSystemColumnTABLE_NAME();
			List<String> col_name = getSystemColumnCOLUMN_NAME();
			List<String> col_type = getSystemColumnCOLUMN_TYPE();
			List<String> is_nullalbe = getSystemColumnIS_NULLABLE();
			List<String> col_key = getSystemColumnCOLUMN_KEY();
			
			//check if Column count match value count
			int count = 0;
			for (int i = 0; i < tab_name.size(); i++)
				if (tab_schema.get(i).equals(schema_name) && tab_name.get(i).equals(table_name))
					count++;
			if (count != typesToBeCheck.size())
			{
				System.out.println("Error: Column count doesn't match value count.");
				return false;
			}
			
			//check if Column types are all correct
			count = -1;
			for (int i = 0; i < tab_name.size(); i++)
			{
				//only scan related columns in specific schema and table
				if (tab_schema.get(i).equals(schema_name) && tab_name.get(i).equals(table_name))
				{
					count++;
					//check if NULL allowed
					if (Pattern.matches("(?i)YES", is_nullalbe.get(i)) && Pattern.matches("(?i)NULL", typesToBeCheck.get(count)))
						continue;//pass checking
					//check data type
					switch (getDataSize(col_type.get(i)))
					{
						case 1://BYTE
						{
							if (Long.parseLong(typesToBeCheck.get(count)) < -128 ||  Long.parseLong(typesToBeCheck.get(count)) > 127)
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'BYTE\'");
								return false;
							}
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<Byte> list =  ReadByteIndexFile(schema_name, table_name, col_name.get(i));
								for (int j = 0; j < list.size(); j++)
								{
									if (Long.parseLong(typesToBeCheck.get(count)) == list.get(j))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
						case 2://SHORT INT or SHORT
						{
							if (!Pattern.matches("(?i)[0-9]+", typesToBeCheck.get(count)))
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'SHORT\'");
								return false;
							}
							if (Long.parseLong(typesToBeCheck.get(count)) < -32768 ||  Long.parseLong(typesToBeCheck.get(count)) > 32767)
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'SHORT\'");
								return false;
							}
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<Short> list =  ReadShortIndexFile(schema_name, table_name, col_name.get(i));
								for (int j = 0; j < list.size(); j++)
								{
									if (Long.parseLong(typesToBeCheck.get(count)) == list.get(j))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
						case 4://INT or FLOAT
						{
							if (!Pattern.matches("(?i)[0-9]+", typesToBeCheck.get(count)))
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'SHORT\'");
								return false;
							}
							if (Long.parseLong(typesToBeCheck.get(count)) < -2147483648 ||  Long.parseLong(typesToBeCheck.get(count)) > 2147483647)
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'INT\'");
								return false;
							}
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<Integer> list =  ReadIntIndexFile(schema_name, table_name, col_name.get(i));
								for (int j = 0; j < list.size(); j++)
								{
									if (Long.parseLong(typesToBeCheck.get(count)) == list.get(j))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
						case 8://LONG INT or LONG or DOUBLE	or DATETIME or DATE
						{
							if (!Pattern.matches("(?i)[0-9]+", typesToBeCheck.get(count)))
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'SHORT\'");
								return false;
							}
							if (Long.parseLong(typesToBeCheck.get(count)) < -2147483648 ||  Long.parseLong(typesToBeCheck.get(count)) > 2147483647)
							{
								System.out.println("Error: Unknown column \'" + typesToBeCheck.get(count) + "\' for type \'INT\'");
								return false;
							}
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<Long> list =  ReadLongIndexFile(schema_name, table_name, col_name.get(i));
								for (int j = 0; j < list.size(); j++)
								{
									if (Long.parseLong(typesToBeCheck.get(count)) == list.get(j))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
						case 0://VARCHAR(n)	
						{
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<String> list =  ReadStringIndexFile(schema_name, table_name, col_name.get(i));
								for (int j = 0; j < list.size(); j++)
								{
									if (typesToBeCheck.get(count).equals(list.get(j)))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
						case -1://invalid type
						{
							System.out.println("Error: Invalid type.");
							return false;
						}
						default://CHAR(n)
						{
							//check if it is Primary key
							if (Pattern.matches("(?i)PRI", col_key.get(i)))
							{
								List<String> list =  ReadCharsIndexFile(schema_name, table_name, col_name.get(i), getDataSize(col_type.get(i)));
								for (int j = 0; j < list.size(); j++)
								{
									if (typesToBeCheck.get(count).equals(list.get(j)))
									{
										System.out.println("Error: Violate Primary Key.");
										return false;
									}
								}	
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private static int getDataSize(String data_type)
	{
		//BYTE									1
		//SHORT INT		SHORT					2
		//INT		FLOAT						4
		//LONG INT		LONG	DOUBLE			8
		//DATETIME		DATE					8
		//CHAR(n)								n
		//VARCHAR(n)							0
		if (Pattern.matches("(?i)BYTE", data_type))//down!
			return	1;
		else if (Pattern.matches("(?i)SHORT\\s+INT", data_type))//down!
			return	2;
		else if (Pattern.matches("(?i)SHORT", data_type))//down!
			return	2;
		else if (Pattern.matches("(?i)INT", data_type))//down!
			return	4;
		else if (Pattern.matches("(?i)FLOAT", data_type))//down!
			return	4;
		else if (Pattern.matches("(?i)LONG\\s+INT", data_type))//down!
			return	8;
		else if (Pattern.matches("(?i)LONG", data_type))//down!
			return	8;
		else if (Pattern.matches("(?i)DOUBLE", data_type))//down!
			return	8;
		else if (Pattern.matches("(?i)DATETIME", data_type))//down!
			return	8;
		else if (Pattern.matches("(?i)DATE", data_type))//down!
			return	8;
		else if (Pattern.matches("(?i)CHAR\\([0-9]\\)", data_type))//down!
		{
			String temp = data_type.replaceFirst("(?i)CHAR\\(\\s*", "");
			temp = temp.replaceFirst("\\s*\\)", "");
			return Integer.parseInt(temp);
		}
		else if (Pattern.matches("(?i)VARCHAR\\([0-9]+\\)", data_type))//down!
			return 0;//means string type size wait to be decided
		else
		{
			System.out.println("Invalid Data Type Found!");
			return -1;
		}
	}
	
	public static boolean IsDicitionaryExist(String dictionaryName)//done!!
	{
		File f = new File(dictionaryName);
		if (f.exists())
			return true;
		else
		{
			f.mkdirs();
			return false;
		}	
	}
	
	public static void init() throws IOException//done!!
	{
		if (!IsDicitionaryExist("information_schema"))//create dictionary if not exist
			System.out.println("information_schema has been created.");
		File f = new File("information_schema/information_schema.schemata.tbl");
		if (!f.exists())
		{
			initSystemTables();//insert tuples into SCHEMATA table
			System.out.println("information_schema has been setup!");
		}
		else
			System.out.println("information_schema has been setup long time ago!");
	}
	
	public static void initSystemTables() //done!!
	{
		try 
		{	
			////////////////////////////////
			//Create the SCHEMATA table file.
			////////////////////////////////
			RandomAccessFile schemataTableFile = new RandomAccessFile("information_schema/information_schema.schemata.tbl", "rw");
			// ROW 1: information_schema.schemata.tbl	
			schemataTableFile.writeByte("information_schema".length());
			schemataTableFile.writeBytes("information_schema");
			schemataTableFile.close();
			
			////////////////////////////////
			//Create the TABLES table file.
			////////////////////////////////
			RandomAccessFile tablesTableFile = new RandomAccessFile("information_schema/information_schema.table.tbl", "rw");
			// ROW 1: information_schema.tables.tbl			
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			tablesTableFile.writeBytes("SCHEMATA");
			tablesTableFile.writeLong(1); // TABLE_ROWS

			// ROW 2: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("TABLES".length()); // TABLE_NAME
			tablesTableFile.writeBytes("TABLES");
			tablesTableFile.writeLong(3); // TABLE_ROWS
			
			// ROW 3: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			tablesTableFile.writeBytes("COLUMNS");
			tablesTableFile.writeLong(7); // TABLE_ROWS
			tablesTableFile.close();
			
			////////////////////////////////
			//Create the COLUMNS table file.
			////////////////////////////////
			RandomAccessFile columnsTableFile = new RandomAccessFile("information_schema/information_schema.columns.tbl", "rw");
			
			// ROW 1: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			columnsTableFile.writeBytes("SCHEMATA");
			columnsTableFile.writeByte("SCHEMA_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("SCHEMA_NAME");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 2: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 3: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 4: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_ROWS".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_ROWS");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("long int".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("long int");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 5: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 6: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 7: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_NAME");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 8: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("ORDINAL_POSITION".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("ORDINAL_POSITION");
			columnsTableFile.writeInt(4); // ORDINAL_POSITION
			columnsTableFile.writeByte("int".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("int");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 9: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_TYPE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_TYPE");
			columnsTableFile.writeInt(5); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 10: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("IS_NULLABLE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("IS_NULLABLE");
			columnsTableFile.writeInt(6); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 11: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_KEY".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_KEY");
			columnsTableFile.writeInt(7); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");
			
			columnsTableFile.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	//update system TABLES when create a new Table
	public static void updateSystemTABLES(String schema_name, String table_name)//done!!
	{
		try {
			String file_name = "information_schema/information_schema.table.tbl";
			RandomAccessFile tablesTableFile = new RandomAccessFile(file_name, "rw");
			
			//check if table already exist (same schema, not allowed same table name)
			if (existInTABLES(schema_name, table_name))//return true/false
			{
				System.out.println("Table \"" + table_name + "\" already exists.");
			}
			else
			{
				tablesTableFile.seek(tablesTableFile.length());//move pointer to the end of file
				//write tablesTableFile
				tablesTableFile.writeByte(schema_name.length()); // TABLE_SCHEMA
				tablesTableFile.writeBytes(schema_name);
				tablesTableFile.writeByte(table_name.length()); // TABLE_NAME
				tablesTableFile.writeBytes(table_name);
				tablesTableFile.writeLong(0); // TABLE_ROWS
			}
			tablesTableFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//update system COLUMNS when create a new Table
	public static void updateSystemCOLUMNS(String schema_name, String table_name, 
			List<String> column_name, List<String> column_type, List<String> is_nullable, List<String> column_key)//done!!
	{
		try {
			String file_name = "information_schema/information_schema.columns.tbl";
			RandomAccessFile columnsTableFile = new RandomAccessFile(file_name, "rw");
			
			//start to write system table: COLUMNS
			columnsTableFile.seek(columnsTableFile.length());//move pointer to the end of file
			for (int j = 0; j < column_name.size(); j++)
			{
				columnsTableFile.writeByte(schema_name.length());
				columnsTableFile.writeBytes(schema_name);
				columnsTableFile.writeByte(table_name.length());
				columnsTableFile.writeBytes(table_name);
				columnsTableFile.writeByte(column_name.get(j).length());
				columnsTableFile.writeBytes(column_name.get(j));
				columnsTableFile.writeInt(j + 1);//write ORDINAL_POSITION
				columnsTableFile.writeByte(column_type.get(j).length());
				columnsTableFile.writeBytes(column_type.get(j));
				columnsTableFile.writeByte(is_nullable.get(j).length());
				columnsTableFile.writeBytes(is_nullable.get(j));
				columnsTableFile.writeByte(column_key.get(j).length());
				columnsTableFile.writeBytes(column_key.get(j));
			}
			columnsTableFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//call method when insert into table
	public static void incrementSystemTABLE_ROW(String schema_name, String table_name) {
		try {
			String file_name = "information_schema/information_schema.table.tbl";
			RandomAccessFile tablesTableFile = new RandomAccessFile(file_name, "rw");
			
			//check if table already exist (same schema, not allowed same table name)
			if (!existInTABLES(schema_name, table_name))//return true/false
			{
				System.out.println("Table \"" + table_name + "\" not exists.");
			}
			else
			{
				//read system TABLES file
				while(true) {
					if (EOF(tablesTableFile.length(), tablesTableFile.getFilePointer()))
						break;
					//System.out.println("file length: " + tablesTableFile.length() + " FilePointer: " + tablesTableFile.getFilePointer());
					//find corresponding tuple
					Byte varcharLength1 = tablesTableFile.readByte();
					StringBuilder sb1 = new StringBuilder();
					for(int i = 0; i < varcharLength1; i++)
					{
						sb1.append((char)tablesTableFile.readByte());
					}
						
					Byte varcharLength2 = tablesTableFile.readByte();
					StringBuilder sb2 = new StringBuilder();
					for(int i = 0; i < varcharLength2; i++)
					{
						sb2.append((char)tablesTableFile.readByte());
					}
					
					if (sb1.toString().equals(schema_name) && sb2.toString().equals(table_name))
					{
						Long pointer = tablesTableFile.getFilePointer();
						Long row = tablesTableFile.readLong();
						tablesTableFile.seek(pointer);
						tablesTableFile.writeLong(row + 1);
						break;
					}
					tablesTableFile.skipBytes(8);
						
				}
			}
			tablesTableFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//call method when insert into table
	public static void insertIntoTable(String schema_name, String table_name,
			List<String> temp) {
		try {
			List<String> tab_schema = getSystemColumnTABLE_SCHEMA();
			List<String> tab_name = getSystemColumnTABLE_NAME();
			List<String> col_name = getSystemColumnCOLUMN_NAME();
			List<String> col_type = getSystemColumnCOLUMN_TYPE();
			//get insert_column_name
			List<String> insert_column_name = new ArrayList<String>();
			List<String> insert_column_type = new ArrayList<String>();
			for (int i = 0; i < tab_name.size(); i++)
			{
				//System.out.println(col_type.get(i));
				//only scan related columns in specific schema and table
				if (tab_schema.get(i).equals(schema_name) && tab_name.get(i).equals(table_name))
				{
					insert_column_name.add(col_name.get(i));
					insert_column_type.add(col_type.get(i));
				}
			}
			
			//if insert null value
			for (int i = 0; i < insert_column_type.size(); i++)
			{
				if (Pattern.matches("(?i)null", temp.get(i)))
				{
					System.out.println("Insert \'null\' allowed, but I don \'t know how");
					return;
				}
			}
			
			//insert into table and index file
			String TableFileName = schema_name + "/" + schema_name + "." + table_name + ".tbl";
			for (int i = 0; i < insert_column_type.size(); i++)
			{
				String indexFileName = schema_name + "/" + schema_name + "." + table_name + "." + insert_column_name.get(i) + ".ndx";
				RandomAccessFile TableFile = new RandomAccessFile(TableFileName, "rw");
				RandomAccessFile IndexFile = new RandomAccessFile(indexFileName, "rw");
				IndexFile.seek(IndexFile.length());
				
				//System.out.println(insert_column_type.get(i));
				switch (getDataSize(insert_column_type.get(i)))
				{
					case 1://BYTE
						IndexFile.writeByte(Byte.parseByte(temp.get(i)));
						System.out.println("writing Byte...");
						break;
					case 2://SHORT INT or SHORT
						IndexFile.writeShort(Short.parseShort(temp.get(i)));
						System.out.println("writing Short...");
						break;
					case 4://INT or FLOAT
						IndexFile.writeInt(Integer.parseInt(temp.get(i)));
						System.out.println("writing Int...");
						break;
					case 8://LONG INT or LONG or DOUBLE	or DATETIME or DATE
						IndexFile.writeLong(Long.parseLong(temp.get(i)));
						System.out.println("writing Long...");
						break;
					case 0://VARCHAR(n)	
						IndexFile.writeByte(temp.get(i).length());
						IndexFile.writeBytes(temp.get(i));
						System.out.println("writing Varchar...");
						break;
					case -1://invalid type
						System.out.println("Error: Insert Failed1, invalid Type");
						break;
					default://CHAR(n)
						IndexFile.writeBytes(temp.get(i));
						System.out.println("writing Char()...");
						break;
				}
				TableFile.seek(TableFile.length());
				IndexFile.writeLong(TableFile.getFilePointer());
				TableFile.close();
				IndexFile.close();
			}
			for (int i = 0; i < insert_column_name.size(); i++)
			{
				RandomAccessFile TableFile = new RandomAccessFile(TableFileName, "rw");
				TableFile.seek(TableFile.length());
				switch (getDataSize(insert_column_type.get(i)))
				{
					case 1://BYTE
						TableFile.writeByte(Byte.parseByte(temp.get(i)));
						break;
					case 2://SHORT INT or SHORT
						TableFile.writeShort(Short.parseShort(temp.get(i)));
						break;
					case 4://INT or FLOAT
						TableFile.writeInt(Integer.parseInt(temp.get(i)));
						break;
					case 8://LONG INT or LONG or DOUBLE	or DATETIME or DATE
						TableFile.writeLong(Long.parseLong(temp.get(i)));
						break;
					case 0://VARCHAR(n)	
						TableFile.writeByte(temp.get(i).length());
						TableFile.writeBytes(temp.get(i));
						break;
					case -1://invalid type
						System.out.println("Error: Insert Failed2, invalid Type");
						break;
					default://CHAR(n)
						TableFile.writeBytes(temp.get(i));
						break;
				}
				TableFile.close();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//call method when select from table
	public static void SelectFromTable(String schema_name, String table_name, String condition)
	{
		try {
			//parse condition string
			String [] condi = condition.trim().split("\\s+");
			if (condi.length != 3)
			{
				System.out.println("Error: invalid condition format.");
				return;
			}
			//condi[0]=attribute name; condi[1]=operator; condi[2]=value
			String column_name = condi[0];
			String operator = condi[1];
			String value = condi[2];
			//get data type before read
			List<String> tab_schema = getSystemColumnTABLE_SCHEMA();
			List<String> tab_name = getSystemColumnTABLE_NAME();
			List<String> col_name = getSystemColumnCOLUMN_NAME();
			List<String> col_type = getSystemColumnCOLUMN_TYPE();
			String column_type = "";
			for (int i = 0; i < tab_name.size(); i++)
			{
				//System.out.println(col_type.get(i));
				//only scan related columns in specific schema and table
				if (tab_schema.get(i).equals(schema_name) && tab_name.get(i).equals(table_name)
						&& col_name.get(i).equals(column_name))
				{
					column_type = col_type.get(i);		
				}
			} 
			
			//get address of tuple satisfy the condition
			ArrayList<Long> address = getTupleAddress(schema_name, table_name, column_name, column_type, operator, value);
			
			//print selected table
			printSelectTuples(schema_name, table_name, address);
		}
		catch(Exception e) {
			System.out.println(e);
		}
				
	}
	
	//return the address of tuples in related table
	public static ArrayList<Long> getTupleAddress(String schema_name, String table_name, String column_name
			, String column_type, String operater, String compareItem)
	{
		String indexName = schema_name + "/" + schema_name + "." + table_name + "." + column_name + ".ndx";
		try {
			ArrayList<Long> al = new ArrayList<Long>();
			RandomAccessFile IndexFile = new RandomAccessFile(indexName, "rw");
			int column_size = getDataSize(column_type);
			
			switch (column_size)
			{
				case 1://BYTE
				{
					List<Byte> list = ReadByteIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i) == Byte.parseByte(compareItem))
							{
								IndexFile.skipBytes(1);
								al.add(IndexFile.readLong());
							}
						}
						else if (operater.equals("<"))
						{
							if (list.get(i) < Byte.parseByte(compareItem))
							{
								IndexFile.skipBytes(1);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							if (list.get(i) > Byte.parseByte(compareItem))
							{
								IndexFile.skipBytes(1);
								al.add(IndexFile.readLong());
							}
						}
					}
					break;
				}
				case 2://SHORT INT or SHORT
				{
					List<Short> list = ReadShortIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i) == Short.parseShort(compareItem))
							{
								IndexFile.skipBytes(2);
								al.add(IndexFile.readLong());
							}
						}
						else if (operater.equals("<"))
						{
							if (list.get(i) < Short.parseShort(compareItem))
							{
								IndexFile.skipBytes(2);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							if (list.get(i) > Short.parseShort(compareItem))
							{
								IndexFile.skipBytes(2);
								al.add(IndexFile.readLong());
							}
						}
					}
					break;
				}
				case 4://INT or FLOAT
				{
					List<Integer> list = ReadIntIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i) == Integer.parseInt(compareItem))
							{
								IndexFile.skipBytes(4);
								al.add(IndexFile.readLong());
							}
						}
						else if (operater.equals("<"))
						{
							if (list.get(i) < Integer.parseInt(compareItem))
							{
								IndexFile.skipBytes(4);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							if (list.get(i) > Integer.parseInt(compareItem))
							{
								IndexFile.skipBytes(4);
								al.add(IndexFile.readLong());
							}
						}
					}
					break;
				}
				case 8://LONG INT or LONG or DOUBLE	or DATETIME or DATE
				{
					List<Long> list = ReadLongIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i) == Long.parseLong(compareItem))
							{
								IndexFile.skipBytes(8);
								al.add(IndexFile.readLong());
							}
						}
						else if (operater.equals("<"))
						{
							if (list.get(i) < Long.parseLong(compareItem))
							{
								IndexFile.skipBytes(8);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							if (list.get(i) > Long.parseLong(compareItem))
							{
								IndexFile.skipBytes(8);
								al.add(IndexFile.readLong());
							}
						}
					}
					break;
				}
				case 0://VARCHAR(n)	
				{
					List<String> list = ReadStringIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i).equals(compareItem))
							{
								IndexFile.skipBytes(8);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							System.out.println("Error: Invalid operator for type \'String\'");
							return al;
						}
					}
					break;
				}
				case -1://invalid type
				{
					System.out.println("Error: Invalid type.");
					return al;
				}
				default://CHAR(n)
				{
					List<String> list = ReadStringIndexFile(schema_name, table_name, column_name);
					for (int i = 0; i < list.size(); i++)
					{
						if (operater.equals("="))
						{
							if (list.get(i).equals(compareItem))
							{
								IndexFile.skipBytes(column_size);
								al.add(IndexFile.readLong());
							}
						}
						else
						{
							System.out.println("Error: Invalid operator for type \'String\'");
							return al;
						}
					}
					break;
				}
			}
			IndexFile.close();
			return al;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//print selected tuples by address
	public static void printSelectTuples(String schema_name, String table_name, ArrayList<Long> addr)
	{
		String file_name = schema_name + "/" + schema_name + "." + table_name + ".tbl";
		try {
			//get data type before read
			List<String> tab_schema = getSystemColumnTABLE_SCHEMA();
			List<String> tab_name = getSystemColumnTABLE_NAME();
			List<String> col_type = getSystemColumnCOLUMN_TYPE();
			List<String> column_type = new ArrayList<String>();
			for (int i = 0; i < tab_name.size(); i++)
			{
				//System.out.println(col_type.get(i));
				//only scan related columns in specific schema and table
				if (tab_schema.get(i).equals(schema_name) && tab_name.get(i).equals(table_name))
				{
					column_type.add(col_type.get(i));		
				}
			} 
			RandomAccessFile tableFile = new RandomAccessFile(file_name, "rw");
			//for each tuple to be printed
			System.out.println("addr.size() should be 1: " + addr.size()); 
			System.out.println("column_type.size() should be 2: " + column_type.size()); 
			for (int j = 0; j < addr.size(); j++)
			{
				tableFile.seek(addr.get(j));
				//for each attribute in a tuple
				for (int k = 0; k < column_type.size(); k++)
				{
					switch (getDataSize(column_type.get(k))) 
					{
						case 1://BYTE
						{
							System.out.print(tableFile.readByte() + "  \t");
							break;
						}
						case 2://SHORT INT or SHORT
						{
							System.out.print(tableFile.readShort() + "  \t");
							break;
						}
						case 4://INT or FLOAT
						{
							System.out.print(tableFile.readInt() + "  \t");
							break;
						}
						case 8://LONG INT or LONG or DOUBLE	or DATETIME or DATE
						{
							System.out.print(tableFile.readLong() + "  \t");
							break;
						}
						case 0://VARCHAR(n)	
						{
							Byte varcharLength = tableFile.readByte();
							StringBuilder sb = new StringBuilder();
							for(int i = 0; i < varcharLength; i++)
							{
								sb.append((char)tableFile.readByte());
							}
							System.out.print(sb.toString() + "  \t");
							break;
						}
						case -1://invalid type
						{
							System.out.println("Error: Invalid type.");
							return;
						}
						default://CHAR(n)
						{
							int varcharLength = getDataSize(column_type.get(j));
							StringBuilder sb = new StringBuilder();
							for(int i = 0; i < varcharLength; i++)
							{
								sb.append((char)tableFile.readByte());
							}
							System.out.print(sb.toString() + "  \t");
							break;
						}
					}
				}
				System.out.println("");
			}
			tableFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	//check if the schema_name already exist in system table
	public static boolean existInSCHEMATA(String compareItem)
	{
		try {
			RandomAccessFile IndexFile = new RandomAccessFile("information_schema/information_schema.schemata.tbl", "rw");
			int indexFileLocation = 0;
			
			//read IndexFile
			while(true) {
				if (EOF(IndexFile.length(), indexFileLocation))
					break;
				
				IndexFile.seek(indexFileLocation);
				Byte varcharLength = IndexFile.readByte();
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < varcharLength; i++)
				{
					sb.append((char)IndexFile.readByte());
				}
				System.out.println("schema_name:" + compareItem + " search_result:" + sb.toString());
				if (compareItem.equals(sb.toString()))
				{
					IndexFile.close();
					return true;
				}
				indexFileLocation += 1;
				indexFileLocation = indexFileLocation + varcharLength.intValue();
			}
			IndexFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//check if the table_name already exist in system table
	
	public static boolean existInTABLES(String schema_name, String table_name)
	{
		try {
			RandomAccessFile IndexFile = new RandomAccessFile("information_schema/information_schema.table.tbl", "rw");
			int indexFileLocation = 0;
			
			//read IndexFile
			while(true) {
				if (EOF(IndexFile.length(), indexFileLocation))
					break;
				
				IndexFile.seek(indexFileLocation);
				Byte varcharLength1 = IndexFile.readByte();
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < varcharLength1; i++)
				{
					sb.append((char)IndexFile.readByte());
				}
				String column1 = sb.toString();//column1 is: schema_name read from TABLES
				
				Byte varcharLength2 = IndexFile.readByte();
				sb = new StringBuilder();
				for(int i = 0; i < varcharLength2; i++)
				{
					sb.append((char)IndexFile.readByte());
				}
				String column2 = sb.toString();//column2 is: table_name read from TABLES
				
				if (schema_name.equals(column1) && table_name.equals(column2))
				{
					IndexFile.close();
					return true;
				}
				
				indexFileLocation += 10;//1 byte + 1 byte + 8 long:pointer + 8 long:column3 = 18
				indexFileLocation = indexFileLocation + varcharLength1.intValue() + varcharLength2.intValue();
			}
			IndexFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;	
	}

	
	public static boolean dropTable(String schema_name, String table_name) 
	{
		try {
			int indexFileLocation = 0;
			String systemTableName = "information_schema/information_schema.table.tbl";
			
			RandomAccessFile tableTableFile = new RandomAccessFile(systemTableName, "rw");

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
				
				Long deletePointer = tableTableFile.getFilePointer();//for easy to mark table with ZZZZ
				
				Byte varcharLength2 = tableTableFile.readByte();
				StringBuilder sb2 = new StringBuilder();
				for(int i = 0; i < varcharLength2; i++)
				{
					sb2.append((char)tableTableFile.readByte());
				}
				Long table_row = tableTableFile.readLong();
				
				//if the delete table has been found, mark ZZZZ...
				if (schema_name.equals(sb1.toString()) && table_name.equals(sb2.toString()))
				{
					tableTableFile.seek(deletePointer);
					tableTableFile.skipBytes(1);
					for (int i = 0; i < varcharLength2; i++)//the length of original table_name
					{
						tableTableFile.writeBytes("Z");//#######
					}
					return true;
				}
				indexFileLocation += 10;//1 byte + 1 byte + 8 long:pointer + 8 long:column3 = 18
				indexFileLocation = indexFileLocation + varcharLength1.intValue() + varcharLength2.intValue();
			}
			tableTableFile.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
}










