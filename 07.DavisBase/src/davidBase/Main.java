package davidBase;

import java.io.IOException;
import java.util.Scanner;

//TASKS LIST
//1. enable input command describe information_schema.columns;
//2. show schemas (3 columns -> means create 3 files)
//3. show tables (1 column)
//4. create table (n columns constraints attached)
//5. insert into tables (write files at the end, including index file)
//6. delete from where (read files and set empty(02)
//7. drop table (file.delete())
//8. select from where (read files and print in a good manner)
//9. exit (save tables and index info)

public class Main {
	public static void main(String args[]) throws ProcessException, IOException
	{
		/* Display the welcome splash screen */
		splashScreen();
		
        SystemTables.init();
        
		Scanner scanner = new Scanner(System.in).useDelimiter(";");
		String userCommand; // Variable to collect user input from the prompt
        do {  // do-while !exit
			System.out.print("davisql>");
			userCommand = scanner.next().trim();
    		ParseCmd.run(userCommand); 
		}while(!userCommand.equals("exit"));
	    scanner.close();	
	}
	
	/**
	 *  Display the welcome "splash screen"
	 */
	public static void splashScreen() {
		System.out.println(line("*",80));
        System.out.println("Welcome to DavisBase"); // Display the string.
		version();
		System.out.println(line("*",80));
	}

	public static String line(String s,int num) {
		String a = "";
		for(int i=0;i<num;i++) {
			a += s;
		}
		return a;
	}
	
	public static void version() {
		System.out.println("DavisBaseLite v1.0\n");
	}
}	
	








