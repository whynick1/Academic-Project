package dbtest;

import java.sql.SQLException;
import java.sql.Statement;

public class InitSchema {

	public static void initSchema(Statement state)
	{
		// Set the current database, if not already set in the getConnection
		try {
			state.execute("DROP DATABASE IF EXISTS library;");
			state.execute("CREATE DATABASE library;");
			state.execute("use library;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initBook(state);
		initAuthor(state);
		initLibraryBranch(state);
		initBorrower(state);
		initBookLoan(state);
		initBookCopies(state);
		initFine(state);
		System.out.println("Schema initiated!");
	}
	
	public static void initBook(Statement state)
	{
		try {
			state.executeUpdate("CREATE TABLE book ( "
			                     +"Isbn VARCHAR(10) PRIMARY KEY, " 
								 +"Title VARCHAR(300)"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void initAuthor(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE book_authors ( "
			                     +"Isbn VARCHAR(10), " 
			                     +"Author_na VARCHAR(100), "
			                     +"PRIMARY KEY(Isbn), "
								 +"FOREIGN KEY(Isbn) REFERENCES book(Isbn)"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initBookCopies(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE book_copies ( "
								 +"book_id VARCHAR(10), "
								 +"branch_id INT, "
			                  	 +"FOREIGN KEY(book_id) REFERENCES book(Isbn), "
			                  	 +"FOREIGN KEY(branch_id) REFERENCES library_branch(branch_id), "
			                  	 +"no_of_copies INT, "
			                  	 +"PRIMARY KEY(book_id, branch_id), "
			                  	 +"available_copies INT"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private static void initLibraryBranch(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE library_branch ( "
			                     +"Branch_id INT PRIMARY KEY, " 
								 +"Branch_na VARCHAR(30), "
			                     +"address VARCHAR(50)"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initBorrower(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE borrower ( "
			                     +"Card_no VARCHAR(10) PRIMARY KEY, " 
								 +"ssn VARCHAR(15), "
			                     +"fname VARCHAR(20), "
								 +"lname VARCHAR(20), "
			                     +"address VARCHAR(50), "
								 +"phone VARCHAR(15)"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initBookLoan(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE book_loans ( "
			                     +"load_id INT PRIMARY KEY, "
			                     +"Isbn VARCHAR(10), "
								 +"FOREIGN KEY(Isbn) REFERENCES book(Isbn), "
								 +"Branch_id INT, "
								 +"FOREIGN KEY(Branch_id) REFERENCES library_branch(Branch_id), "
								 +"Card_no VARCHAR(10), "
								 +"FOREIGN KEY(Card_no) REFERENCES borrower(Card_no), "
			                     +"Date_out DATE, "
								 +"Due_date DATE, "
			                     +"Date_in DATE"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initFine(Statement state) {
		try {
			state.executeUpdate("CREATE TABLE fines ( "
			                     +"load_id INT, "
								 +"FOREIGN KEY(load_id) REFERENCES book_loans(load_id), "
			                     +"PRIMARY KEY(load_id), "
								 +"fine_amt FLOAT(2), "
			                     +"paid BOOLEAN DEFAULT 0"
								 + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
