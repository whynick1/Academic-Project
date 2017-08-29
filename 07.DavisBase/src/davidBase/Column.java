package davidBase;

public class Column {
	String column_na;
	String column_type;
	String column_key;
	String is_nullable;//primary key/ NOT NULL
	public Column(String columnInfo) //column info will be a string like "Age INT(10) NOT NULL"
	{
		String[] temp = columnInfo.split("\\s");
		this.column_na = temp[0];
		this.column_type = temp[1];
		if (temp.length == 2)
		{
			this.column_key = "";
			this.is_nullable = "YES";
		}
		else if (temp.length >= 2)
		{
			if (temp[2].equals("primary"))
			{
				this.column_key = "PRI";
				this.is_nullable = "NO";
			}
			if (temp[2].equals("not"))
			{
				this.column_key = "";
				this.is_nullable = "NO";
			}
			else
			{
				System.out.println("Input wrong format for column!");
			}
		}
	}
}
