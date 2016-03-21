package dbtest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperateDatabase {
	public static void importData(List<ArrayList<String>> books,
			List<ArrayList<String>> bookCopies,
			List<ArrayList<String>> bookLoan,
			List<ArrayList<String>> borrowers,
			List<ArrayList<String>> library_branch, Statement stmt) {
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

	// fuzzy search by borrower
	public static String[][] searchBook(String isbn, String title,
			String author, Statement stmt) throws SQLException {
		try {
			ResultSet uprs = stmt
					.executeQuery("SELECT book.isbn, book.title, book_authors.author_na FROM book, book_authors "
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
			String[][] str = printResultSet(uprs);
			// Always close the record set
			uprs.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// fuzzy search by administer
	public static String[][] searchBook(String isbn, String card_no,
			String fname, String lname, Statement stmt) throws SQLException {
		try {
			ResultSet uprs = stmt
					.executeQuery("select book.isbn, book.title, book_authors.author_na "
							+ "FROM book, book_authors, book_loans, borrower "
							+ "WHERE book.isbn=book_authors.isbn "
							+ "AND book.isbn=book_loans.isbn "
							+ "AND borrower.card_no=book_loans.card_no AND Date_in IS NULL "
							+ "AND book.isbn LIKE \"%"
							+ isbn
							+ "%\" AND borrower.card_no LIKE \"%"
							+ card_no
							+ "%\" AND borrower.fname LIKE \"%"
							+ fname
							+ "%\" AND borrower.lname LIKE \"%"
							+ lname
							+ "%\"");
			String[][] str = printResultSet(uprs);
			// Always close the record set
			uprs.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// absolution search with Isbn
	public static String[][] searchBook(String isbn, Statement stmt)
			throws SQLException {
		try {
			// ResultSet uprs =
			// stmt.executeQuery("SELECT book_copies.branch_id, "
			// + "library_branch.branch_na, no_of_copies, available_copies "
			// + "FROM book_copies, book, library_branch, book_authors "
			// + "WHERE book_copies.branch_id=library_branch.branch_id "
			// + "AND book_copies.book_id LIKE "
			// + isbn
			// + " AND no_of_copies > 0");
			ResultSet uprs = stmt
					.executeQuery("select book_copies.branch_id, library_branch.branch_na, "
							+ "no_of_copies, available_copies FROM book_copies, library_branch "
							+ "WHERE book_copies.branch_id=library_branch.branch_id AND book_copies.book_id = \""
							+ isbn + "\" AND no_of_copies > 0");

			String[][] str = printResultSet(uprs);
			// Always close the record set
			uprs.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[][] printResultSet(ResultSet rs) {
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			// for getting the rs.row number
			rs.last();
			int setSize = rs.getRow();
			rs.beforeFirst();
			// for the table's array[][]
			String[][] str = new String[setSize][columnsNumber];
			int cntRow = 0;
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					str[cntRow][i - 1] = columnValue;
					// System.out.println("str[" + cntRow + "][" + (i-1) +
					// "] = " + columnValue);
					// System.out.print(columnValue + " " +
					// rsmd.getColumnName(i) + ";");
				}
				cntRow++;
				System.out.println("");
			}
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// all the insert function below will trigger exception when inputing
	// duplicate primary key
	// this might be a potential problem!
	private static void insertBook(List<ArrayList<String>> books, Statement stmt)
			throws SQLException {
		try {
			for (int i = 0; i < books.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "book");
				uprs.moveToInsertRow();
				// System.out.println(books.get(i).get(0) + "\t" +
				// books.get(i).get(2));
				uprs.updateString("Isbn", books.get(i).get(0));
				uprs.updateString("Title", books.get(i).get(2));
				uprs.insertRow();
				uprs.beforeFirst();
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert Book down!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertAuthor(List<ArrayList<String>> books,
			Statement stmt) throws SQLException {
		try {
			stmt.executeUpdate("TRUNCATE TABLE book_authors");
			for (int i = 0; i < books.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM "
						+ "book_authors");
				uprs.moveToInsertRow();
				// System.out.println(books.get(i).get(0) + "\t" +
				// books.get(i).get(3));
				uprs.updateString("Isbn", books.get(i).get(0));
				uprs.updateString("Author_na", books.get(i).get(3));
				uprs.insertRow();
				uprs.beforeFirst();
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert Author down!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertLibraryBranch(
			List<ArrayList<String>> library_branch, Statement stmt)
			throws SQLException {
		try {
			for (int i = 0; i < library_branch.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM "
						+ "library_branch");
				uprs.moveToInsertRow();
				// System.out.println(library_branch.get(i).get(0) + "\t" +
				// library_branch.get(i).get(1) +
				// library_branch.get(i).get(2));
				uprs.updateString("Branch_id", library_branch.get(i).get(0));
				uprs.updateString("Branch_na", library_branch.get(i).get(1));
				uprs.updateString("address", library_branch.get(i).get(2));
				uprs.insertRow();
				uprs.beforeFirst();
				// Always close the record set
				uprs.close();
			}
			System.out.println("insert LibrayBranch down!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertBorrower(List<ArrayList<String>> borrower,
			Statement stmt) throws SQLException {
		try {
			for (int i = 0; i < borrower.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM "
						+ "borrower");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertBookLoan(List<ArrayList<String>> bookLoan,
			Statement stmt) throws SQLException {
		try {
			for (int i = 0; i < bookLoan.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM "
						+ "book_loans");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertBookCopy(List<ArrayList<String>> bookCopies,
			Statement stmt) throws SQLException {
		try {
			stmt.executeUpdate("TRUNCATE TABLE book_copies");
			for (int i = 0; i < bookCopies.size(); i++) {
				ResultSet uprs = stmt.executeQuery("SELECT * FROM "
						+ "book_copies");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertFine(List<ArrayList<String>> bookLoan,
			Statement stmt) throws SQLException {
		stmt.executeUpdate("TRUNCATE TABLE fines");
		try {
			ResultSet uprs = stmt
					.executeQuery("SELECT DATEDIFF(date_in, due_date), load_id FROM book_loans WHERE DATEDIFF("
							+ "date_in" + "," + "due_date" + ") > 0");// result
																		// of
																		// DATEDIFF
																		// is A1
																		// - A2;
			uprs.last();
			int setSize = uprs.getRow();
			uprs.beforeFirst();
			float[] fine_amt = new float[setSize];
			String[] load_id = new String[setSize];
			int i = 0;
			while (uprs.next()) {
				fine_amt[i] = (float) (uprs.getInt(1) * 0.25);// getInt(1) means
																// get value of
																// column 1
				load_id[i] = uprs.getString("load_id");
				// System.out.println(load_id[i] + "\t" + fine_amt[i]);
				i++;
			}
			// Always close the record set
			uprs.close();

			// form fines table
			for (int j = 0; j < setSize; j++) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean identifyCardNo(String cardNo, Statement stmt) {
		try {
			ResultSet uprs = stmt.executeQuery("SELECT * FROM " + "borrower "
					+ "WHERE Card_no = \"" + cardNo + "\"");
			// ResultSet uprs = stmt.executeQuery("select * from fines");
			uprs.last();
			System.out.println("getRow = " + uprs.getRow());
			System.out.println("Card number verification down!!");
			if (uprs.getRow() > 0) {
				System.out.println("getRow = " + uprs.getRow());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int borrowBook(String isbn, int branchID, String userID,
			Statement stmt) {
		try {
			// check if the borrower has 3 unreturned books
			ResultSet check1 = stmt
					.executeQuery("select * from borrower, book_loans "
							+ "WHERE borrower.card_no=book_loans.card_no "
							+ "AND borrower.card_no=\"" + userID
							+ "\" AND date_in IS NULL");
			check1.last();
			if (check1.getRow() >= 3)
				return -1;// -1 means exceed maximum borrow number

			// check if the borrower has unpaid fines
			ResultSet check2 = stmt
					.executeQuery("select * from book_loans, fines "
							+ "WHERE fines.load_id=book_loans.load_id "
							+ "AND fines.paid=0 AND book_loans.card_no=\""
							+ userID + "\"");
			check2.last();
			if (check2.getRow() > 0)
				return -2;// -2 means has unpaid fines

			// check if out of stock
			ResultSet check3 = stmt.executeQuery("select * from book_copies "
					+ "WHERE book_id=\"" + isbn + "\" AND branch_id="
					+ branchID + " AND available_copies>0;");
			check3.last();
			if (check3.getRow() <= 0)// no available book
				return -3;// -3 means no book left

			// update book_copies table decrementing by 1
			stmt.executeUpdate("update book_copies SET available_copies=available_copies-1 "
					+ "WHERE book_id=\""
					+ isbn
					+ "\" AND branch_id="
					+ branchID + " AND available_copies > 0");
			// update book_loans table adding new loan record
			ResultSet uprs = stmt.executeQuery("SELECT * FROM book_loans");
			uprs.last();
			int newLoadID = uprs.getInt(1) + 1;
			stmt.executeUpdate("insert into book_loans VALUES ("
					+ newLoadID
					+ ", \""
					+ isbn
					+ "\", 2, \""
					+ userID
					+ "\", CURDATE(), DATE_ADD(CURDATE(), INTERVAL 13 DAY), null)");
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static String createNewBorrower(String fname, String lname,
			String ssn, String address, String phone, Statement stmt) {
		try {
			// check if ssn already exist in borrower table
			ResultSet rs = stmt
					.executeQuery("select * from borrower WHERE ssn=" + "\""
							+ ssn + "\"");
			rs.last();
			if (rs.getRow() > 0)
				return "Ssn Already Registered";

			// update borrower table adding new borrower
			ResultSet rs2 = stmt.executeQuery("select * from borrower");
			rs2.last();
			int temp = Integer.parseInt(rs2.getString(1).substring(2)) + 1;
			String newLoadID = "ID" + String.format("%06d", temp);
			stmt.executeUpdate("insert into borrower VALUES (\"" + newLoadID
					+ "\",\"" + ssn + "\",\"" + fname + "\",\"" + lname
					+ "\",\"" + address + "\",\"" + phone + "\")");
			return newLoadID;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Ssn Already Registered";
	}

	// Administer could set book return date_in
	public static void returnBook(int branchID, String selectedBookID,
			String checkInDate, Statement stmt) {
		try {
			// set date_in in book_loans table
			stmt.executeUpdate("update book_loans SET date_in=\"" + checkInDate
					+ "\" WHERE isbn=\"" + selectedBookID + "\" AND branch_id="
					+ branchID);

			// update book_copies table available_copies
			stmt.executeUpdate("update book_copies SET available_copies=available_copies+1 "
					+ "WHERE book_id=\""
					+ selectedBookID
					+ "\" AND branch_id="
					+ branchID + " AND no_of_copies > available_copies");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Administer could set fine paid
	public static void paidFine(String fname , String lname, Statement stmt) {
		try {
			stmt.executeUpdate("update fines, book_loans, borrower SET paid=1 "
					+ "WHERE book_loans.card_no=borrower.card_no "
					+ "AND fines.load_id=book_loans.load_id "
					+ "AND fname=\""
					+ fname
					+ "\" AND lname=\""
					+ lname
					+ "\"");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Administer could update the fines table
	public static int updateFine(Statement stmt) {
		try {
			int rowNum = stmt.executeUpdate("update fines, book_loans SET fine_amt=DATEDIFF(CURDATE(), due_date)*0.25  "
					+ "where fines.load_id=book_loans.load_id "
					+ "AND date_in IS NULL;");
			return rowNum;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Administer could display all the fines group by card_no
	public static String[][] displayAllFine(Statement stmt) {
		try {
			ResultSet uprs = stmt.executeQuery("select fname, lname, SUM(fine_amt) from fines, borrower, book_loans "
					+ "WHERE borrower.card_no=book_loans.card_no "
					+ "AND book_loans.load_id=fines.load_id "
					+ "GROUP BY fname, lname");
			String[][] str = printResultSet(uprs);
			// Always close the record set
			uprs.close();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}












