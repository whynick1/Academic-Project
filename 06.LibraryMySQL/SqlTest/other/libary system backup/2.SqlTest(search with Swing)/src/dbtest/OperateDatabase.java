package dbtest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperateDatabase {
	public static void importData(List<ArrayList<String>> books, List<ArrayList<String>> bookCopies, 
			List<ArrayList<String>> bookLoan, List<ArrayList<String>> borrowers, List<ArrayList<String>> library_branch, Statement stmt)
	{
		try {
			insertBook(books, stmt);
			insertAuthor(books, stmt);
			insertLibraryBranch(library_branch, stmt);
			insertBorrower(borrowers, stmt);
			insertBookLoan(bookLoan, stmt);
			insertBookCopy(bookCopies, stmt);
			insertFine(bookLoan, stmt);
			System.out.println("tables insertion finished!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void searchBook(String isbn, String title, String author, Statement stmt) throws SQLException 
	{
		try {
				ResultSet uprs = stmt.executeQuery("SELECT book.isbn, book.title, book_authors.author_na FROM book, book_authors "
						+ "WHERE book.isbn=book_authors.isbn "
						+ "AND book.isbn LIKE \"%"
						+ isbn 
						+ "%\" "
						+ "AND book.title LIKE \"%"
						+ title
						+ "%\" "
						+ "AND book_authors.author_na LIKE \"%"
						+ author
						+ "%\"");
				printResultSet(uprs);
				// Always close the record set
				uprs.close();
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public static void searchBook(String isbn, Statement stmt) throws SQLException 
	{
		try {
				ResultSet uprs = stmt.executeQuery("SELECT book.isbn, book.title, book_authors.author_na, book_copies.branch_id, "
						+ "library_branch.branch_na, no_of_copies, available_copies "
						+ "FROM book_copies, book, library_branch, book_authors "
						+ "WHERE book.isbn=book_copies.book_id "
						+ "AND book_copies.branch_id=library_branch.branch_id "
						+ "AND book.isbn=book_authors.isbn "
						+ "AND book.isbn LIKE \"%"
						+ isbn 
						+ "%\" "
						+ "AND no_of_copies > 0");
				printResultSet(uprs);
				// Always close the record set
				uprs.close();
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public static void printResultSet(ResultSet rs)
	{
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
		    System.out.println("querying SELECT * FROM XXX");
		    int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) System.out.print("\t");
		            String columnValue = rs.getString(i);
		            System.out.print(columnValue + " " + rsmd.getColumnName(i));
		        }
		        System.out.println("");
		    }	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//all the insert function below will trigger exception when inputing duplicate primary key
	//this might be a potential problem!
	private static void insertBook(List<ArrayList<String>> books, Statement stmt) throws SQLException 
	{
		try {
			for (int i = 0; i < books.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "book");
				uprs.moveToInsertRow();
				//System.out.println(books.get(i).get(0) + "\t" + books.get(i).get(2));
				uprs.updateString("Isbn", books.get(i).get(0));
				uprs.updateString("Title", books.get(i).get(2));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert Book down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertAuthor(List<ArrayList<String>> books, Statement stmt) throws SQLException {
		try {
			stmt.executeUpdate("TRUNCATE TABLE book_authors");
			for (int i = 0; i < books.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "book_authors");
				uprs.moveToInsertRow();
				//System.out.println(books.get(i).get(0) + "\t" + books.get(i).get(3));
				uprs.updateString("Isbn", books.get(i).get(0));
				uprs.updateString("Author_na", books.get(i).get(3));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert Author down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertLibraryBranch(List<ArrayList<String>> library_branch, Statement stmt) throws SQLException 
	{
		try {
			for (int i = 0; i < library_branch.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "library_branch");
				uprs.moveToInsertRow();
//				System.out.println(library_branch.get(i).get(0) + "\t" + library_branch.get(i).get(1) + 
//						library_branch.get(i).get(2));
				uprs.updateString("Branch_id", library_branch.get(i).get(0));
				uprs.updateString("Branch_na", library_branch.get(i).get(1));
				uprs.updateString("address", library_branch.get(i).get(2));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert LibrayBranch down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertBorrower(List<ArrayList<String>> borrower, Statement stmt) throws SQLException 
	{
		try {
			for (int i = 0; i < borrower.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "borrower");
				uprs.moveToInsertRow();
				uprs.updateString("Card_no", borrower.get(i).get(0));
				uprs.updateString("ssn", borrower.get(i).get(1));
				uprs.updateString("fname", borrower.get(i).get(2));
				uprs.updateString("lname", borrower.get(i).get(3));
				uprs.updateString("address", borrower.get(i).get(5));
				uprs.updateString("phone", borrower.get(i).get(8));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert Borrower down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertBookLoan(List<ArrayList<String>> bookLoan, Statement stmt) throws SQLException 
	{
		try {
			for (int i = 0; i < bookLoan.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "book_loans");
				uprs.moveToInsertRow();
				uprs.updateString("load_id", bookLoan.get(i).get(0));
				uprs.updateString("Isbn", bookLoan.get(i).get(1));
				uprs.updateString("Branch_id", bookLoan.get(i).get(2));
				uprs.updateString("Card_no", bookLoan.get(i).get(3));
				uprs.updateString("Date_out", bookLoan.get(i).get(4));
				uprs.updateString("Due_date", bookLoan.get(i).get(5));
				uprs.updateString("Date_in", bookLoan.get(i).get(6));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert BookLoan down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertBookCopy(List<ArrayList<String>> bookCopies, Statement stmt) throws SQLException 
	{
		try {
			stmt.executeUpdate("TRUNCATE TABLE book_copies");
			for (int i = 0; i < bookCopies.size(); i++)
			{
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "book_copies");
				uprs.moveToInsertRow();
				uprs.updateString("book_id", bookCopies.get(i).get(0));
				uprs.updateString("branch_id", bookCopies.get(i).get(1));
				uprs.updateString("no_of_copies", bookCopies.get(i).get(2));
				uprs.updateString("available_copies", bookCopies.get(i).get(2));
				uprs.insertRow();
				uprs.beforeFirst();	
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert BookCopy down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}

	private static void insertFine(List<ArrayList<String>> bookLoan, Statement stmt) throws SQLException 
	{
		stmt.executeUpdate("TRUNCATE TABLE fines");
		try {			
			ResultSet uprs = stmt.executeQuery("SELECT DATEDIFF(date_in, due_date), load_id FROM book_loans WHERE DATEDIFF(" 
					+ "date_in" + "," + "due_date" + ") > 0");//result of DATEDIFF is A1 - A2;
			uprs.last();
			int setSize = uprs.getRow();
			uprs.beforeFirst();
			float[] fine_amt = new float[setSize];
			String[] load_id = new String[setSize];
			int i = 0;
			while (uprs.next()) 
			{
				fine_amt[i] = (float) (uprs.getInt(1) * 0.25);//getInt(1) means get value of column 1
				load_id[i] = uprs.getString("load_id");
//				System.out.println(load_id[i] + "\t" + fine_amt[i]);
				i++;
			}
			// Always close the record set
			uprs.close();
				
			//form fines table
			for (int j = 0; j < setSize; j++)
			{
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + "fines");
				rs.moveToInsertRow();
				rs.updateString("load_id", load_id[j]);
				rs.updateFloat("fine_amt", fine_amt[j]);
				rs.updateBoolean("paid", false);
				rs.insertRow();
				rs.beforeFirst();	
				// Always close the record set
				rs.close();
			}
			System.out.println("insert Fine down!!");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	}
}
