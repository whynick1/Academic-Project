package dbtest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static Connection conn = null;
    static int CONTAIN_TITLE = 1;
    static int NOT_CONTAIN_TITLE = 0;
    
    public static void main(String[] args)
    {
    	// (0) ISBN10		(1) ISBN13		(2) Title		(3) Author		(4) Cover		(5) Publisher
    	// (6) Pages
    	List<ArrayList<String>> books = ReadFiles.loadBooks("Library_data/books.csv");
    	
    	// (0) book_id	    (1) branch_id	(2) no_of_copies
    	List<ArrayList<String>> bookCopies = ReadFiles.loadSet("Library_data/book_copies.csv", CONTAIN_TITLE);
    	
    	// (0) loan_id 		(1) ISBN10		(2) branch_id	(3) card_no		(4) date_out	(5) date_due
    	// (6) date_in
    	List<ArrayList<String>> bookLoan = ReadFiles.loadSet("Library_data/book_loans.csv", NOT_CONTAIN_TITLE);
    	
    	// (0) ID0000id 	(1) ssn			(2) first_name 	(3) last_name 	(4) email 		(5) address      
    	// (6) city  	    (7) state       (8) phone
    	List<ArrayList<String>> borrowers = ReadFiles.loadSet("Library_data/borrowers.csv", CONTAIN_TITLE);
    	
    	// (0) branch_id	(1) branch_name	(2) address
    	List<ArrayList<String>> library_branch = ReadFiles.loadSet("Library_data/library_branch.csv", CONTAIN_TITLE);

		try {
			// Create a connection to the local MySQL server, with the "company" database selected.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
		            ResultSet.CONCUR_UPDATABLE
		            );
			if (args[0].equals("import"))
			{
				//initiate the schema of database's tables
				//call initSchema only when modify any schema in database;
				InitSchema.initSchema(stmt);
				
				//import original data, in order to insert into each table
				//call importData only when first time insert data into tables
				stmt.execute("use library;");
				OperateDatabase.importData(books, bookCopies, bookLoan, borrowers, library_branch, stmt);
			}
			else if (args[0].equals("login"))
			{
				stmt.execute("use library;");
				//Call the Swing window for user log in
				Swing w = new Swing();
			}
			
			conn.close();
			System.out.println("Setup sucessfully!!");
		} 
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
    }
}













