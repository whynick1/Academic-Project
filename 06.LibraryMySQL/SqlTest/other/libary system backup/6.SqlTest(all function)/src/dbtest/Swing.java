package dbtest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.naming.directory.SearchControls;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument.BranchElement;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Swing implements ActionListener{
	Statement stmt;
	Connection conn;
	/////////////search variables//////////////
	private JFrame f;
	private JScrollPane s;
    private JPanel panel, panel1, panel2, panel3;
	private JLabel jLabel1, jLabel2, jLabel3;
	private JTextField jTextField1, jTextField2, jTextField3;
	private JButton jButton;
	private JTable table;
	private String[][] searchTable;
	DefaultTableModel defaultModel = null;
	String isbn, title, author;
	
	/////////////login variables//////////////
	private String cardNo;
	private JFrame f2;
    private JPanel f2_panel, f2_panel1;
	private JLabel jLabel4;
	private JTextField jTextField4;
	private JButton jButton2, jButton3;
	
	/////////////book info variables///////////
	private JFrame f4;
	private JScrollPane s4;
	private JTable table2;
	private String[][] bookInfoTable;
	private String selectedBookID;
	private int branchID;
	DefaultTableModel defaultModel4 = null;
	
	/////////////register variables//////////////
	private String registerFname, registerLname, registerSsn, registerAddress, phone;
	private JFrame f3;
    private JPanel f3_panel, f3_panel1, f3_panel2, f3_panel3, f3_panel4, f3_panel5;
	private JLabel jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
	private JTextField jTextField5, jTextField6, jTextField7, jTextField8, jTextField9;
	private JButton jButton4;
	
	/////////////administer variables////////////
	private JFrame f_admin;
	private JScrollPane s_admin;
	private JTable table_admin;
	private static JPanel panel_admin, panel_admin1, panel_admin2, panel_admin3;
	private JTextField jTextField10, jTextField11, jTextField12; 
	private static JLabel jLabel10, jLabel11, jLabel12;
	private static JButton jButton6, jButton7;
	DefaultTableModel defaultModel_admin = null;
	
	///////////////date variables////////////////
	private static JFrame f_date;
	private static JSpinner spinner;
	private static JButton jButton5;
	private static String loanID;
	
	/////////////check fine variables//////////////
	private JFrame f_fine;
	private JScrollPane s_fine;
    private JPanel panel_fine;
	private JButton jButton_all,jButton_update;
	private JTable table_fine;
	private String[][] searchTable_fine;
	DefaultTableModel defaultModel_fine = null;
	
	///////////////constructor////////////////
	public Swing()
	{
		setSearchPanel();
		setSignInPanel();
		setRegisterPanel();
		setBookInfoPanel();
		setadminPanel();
    	setdatePanel();
    	setFinePanel();
	}
	
	///////////////admin fine panel/////////////////
	private void setFinePanel()
	{
		f_fine = new JFrame();
		f_fine.setSize(400, 400);
		f_fine.setLocation(f.getLocation());
		f_fine.setResizable(false);
	
		panel_fine = new JPanel(new GridLayout(3,1));
		panel_fine.add(getFineJButton(1)); 
		panel_fine.add(getFineJButton(2));
		
		getFineTable();
		
		Container contentPane = f_fine.getContentPane();
		contentPane.add(panel_fine, BorderLayout.NORTH);
		contentPane.add(s_fine, BorderLayout.CENTER);	    
		
		f_fine.setTitle("Fine Display");
		f_fine.setVisible(false);  	
	}
	
	private javax.swing.JTable getFineTable(){
		   String[] columnNames = {"first name", "last name", "fine amount"};
		   String[][] tableValues = new String[1][];
		   defaultModel_fine = new DefaultTableModel(tableValues, columnNames){
			   @Override
			   public boolean isCellEditable(int row, int column)
			   {
				   return false;
			   }
		   };
		   
		   table_fine = new JTable(defaultModel_fine);
	       table_fine.setVisible(true);
		   //set width of each column
		   TableColumn column = null;
		   for (int i = 0; i < 3; i++)
		   {
			   column = table_fine.getColumnModel().getColumn(i);
			   column.setPreferredWidth(150);
		   }
		   table_fine.setPreferredScrollableViewportSize(new Dimension(400,150));
		   
		   table_fine.addMouseListener(new MouseListener() {
			
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int rowNum = table_fine.getSelectedRow();
					if (rowNum == -1)
						return;
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					{
						try {
							conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
							Statement stmt = conn.createStatement(
					    			ResultSet.TYPE_SCROLL_SENSITIVE,
					                ResultSet.CONCUR_UPDATABLE
					                );
					    	stmt.execute("use library;");
							OperateDatabase.paidFine(searchTable_fine[rowNum][0], searchTable_fine[rowNum][1], stmt);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		   s_fine = new JScrollPane(table_fine);
		   return table_fine;
	} 
	
	/////////////////////Check Fine Button//////////////////////
	private javax.swing.JButton getFineJButton(int btn) {
		if (btn == 1)
		{
			if (jButton_all == null) {
				jButton_all = new javax.swing.JButton("str");
				jButton_all.setText("Check All(Refresh)");
				jButton_all.setSize(new Dimension(100, 30));
				
				// Register as a listener 
				jButton_all.addActionListener(new ButtonAllListener()); 
			}
			return jButton_all;
		}
		else
		{
			if (jButton_update == null) {
				jButton_update = new javax.swing.JButton("str");
				jButton_update.setText("Update Fine Table");
				jButton_update.setSize(new Dimension(100, 30));
				
				// Register as a listener 
				jButton_update.addActionListener(new ButtonUpdateListener()); 
			}
			return jButton_update;
		}
	}
	
	class ButtonAllListener implements ActionListener 
	{ 
		// The interface method to receive button clicks 
		public void actionPerformed(ActionEvent e) 
		{ 
			displayAllFine();//fuzzy search
		} 
	}
	
	class ButtonUpdateListener implements ActionListener 
	{ 
		// The interface method to receive button clicks 
		public void actionPerformed(ActionEvent e) 
		{ 
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
				Statement stmt = conn.createStatement(
		    			ResultSet.TYPE_SCROLL_SENSITIVE,
		                ResultSet.CONCUR_UPDATABLE
		                );
		    	stmt.execute("use library;");
				int affectedtuple = OperateDatabase.updateFine(stmt);
				JOptionPane.showMessageDialog(panel_fine, "Update sucessful\n" + affectedtuple + 
						" tuple affected", "Update done",JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
	}

	//////////////Administer panel/////////////////
	private void setadminPanel() {
		f_admin = new JFrame();
		f_admin.setSize(400, 400);
		f_admin.setLocation(f.getLocation());//设置窗口居中显示
		f_admin.setResizable(false);		
		
		
		panel_admin1 = new JPanel(new GridLayout(1, 4, 10, 10));
		panel_admin2 = new JPanel(new GridLayout(1, 4, 10, 10));
		panel_admin3 = new JPanel(new GridLayout(1, 4, 10, 10));
		    
		panel_admin1.add(getJLabel("\tIsbn", 10), null);
		panel_admin1.add(getJTextField(10), null);
		panel_admin2.add(getJLabel("\tTitle", 11), null);
		panel_admin2.add(getJTextField(11), null);
		panel_admin3.add(getJLabel("\tAuthor", 12), null);
		panel_admin3.add(getJTextField(12), null);
		    
		panel_admin = new JPanel(new GridLayout(5,1));
	    panel_admin.add(panel_admin1);
	    panel_admin.add(panel_admin2);
	    panel_admin.add(panel_admin3);
	    panel_admin.add(getAdminSearchButton("Search"));
	    panel_admin.add(getAdminFineButton("Check fines"));
	    
	    getAdminJTable();
	    Container contentPane = f_admin.getContentPane();
	    contentPane.add(panel_admin, BorderLayout.NORTH);
	    contentPane.add(s_admin, BorderLayout.CENTER);	
	    f_admin.setTitle("Administer Page");
	    f_admin.setVisible(false);  
	}
	
	private javax.swing.JTable getAdminJTable(){
		   String[] columnNames = {"1", "2", "3"};
		   String[][] tableValues = {{"a", "b","c"}};
		   defaultModel_admin = new DefaultTableModel(tableValues, columnNames){
			   @Override
			   public boolean isCellEditable(int row, int column)
			   {
				   return false;
			   }
		   };
		   
		   table_admin = new JTable(defaultModel_admin);
	       table_admin.setVisible(true);
		   //set width of each column
		   TableColumn column = null;
		   for (int i = 0; i < 3; i++)
		   {
			   column = table_admin.getColumnModel().getColumn(i);
			   column.setPreferredWidth(150);
		   }
		   table_admin.setPreferredScrollableViewportSize(new Dimension(400,150));
		   
		   table_admin.addMouseListener(new MouseListener() {
			
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int rowNum = table_admin.getSelectedRow();
					if (rowNum == -1)
						return;
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					{
						//selectedBookID = (String) table.getValueAt(rowNum, 0);
						selectedBookID = searchTable[rowNum][0];
						f4.setVisible(true);
						System.out.println("double click! book Isnb is: " + searchTable[rowNum][0]);
						absoluteSearch(searchTable[rowNum][0]);
					}
				}
			});
		   s_admin = new JScrollPane(table_admin);
		   return table_admin;
	} 
	
	//////////////////Date panel//////////////////
	private void setdatePanel() {
		f_date = new JFrame();
		f_date.setSize(200, 200);
		int windowWidth = f_date.getWidth();                     //获得窗口宽
		int windowHeight = f_date.getHeight();                   //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包
		Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸
		int screenWidth = screenSize.width;                     //获取屏幕的宽
		int screenHeight = screenSize.height;                   //获取屏幕的高
		f_date.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
		f_date.setResizable(false);
	     
		// Create a date spinner & set default to today, no minimum, or max
		// Increment the days on button presses
		// Can also increment YEAR, MONTH, or DAY_OF_MONTH
		Date todaysDate = new Date();
		spinner = new JSpinner(new SpinnerDateModel(todaysDate, null, null,
		        Calendar.DAY_OF_MONTH));
		
		// DateEditor is an editor that handles displaying & editing the dates
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yy");
		spinner.setEditor(dateEditor);
		
		jButton5 = getDateJButton("Comfirm");
		
	    Container contentPane = f_date.getContentPane();  
	    contentPane.add(spinner, BorderLayout.NORTH); 
	    contentPane.add(jButton5, BorderLayout.CENTER);  
	    
	    f_date.setTitle("Return Confirm");
	    f_date.setVisible(false);  	
	}
	
	private javax.swing.JButton getDateJButton(String str) {
		   if(jButton5 == null) {
		      jButton5 = new javax.swing.JButton("str");
		      jButton5.setText(str);
		      jButton5.setSize(new Dimension(100, 30));

	    	  // Register as a listener 
		  	  jButton5.addActionListener(new DateButtonListener()); 
		   }
		   return jButton5;
	}
		
	class DateButtonListener implements ActionListener 
	{ 
	  	// The interface method to receive button clicks 
	  	public void actionPerformed(ActionEvent e) 
	  	{ 
	  		try {
	  			Date temp = (Date) spinner.getValue();
		  		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		  		String checkInDate = sdf.format(temp);  
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
				Statement stmt = conn.createStatement(
		    			ResultSet.TYPE_SCROLL_SENSITIVE,
		                ResultSet.CONCUR_UPDATABLE
		                );
		    	stmt.execute("use library;");
				OperateDatabase.returnBook(branchID, selectedBookID, checkInDate, stmt);
				
				System.out.println("Branch ID is: " + branchID + " Book isbn is: " + selectedBookID + " Check in date is: " + checkInDate);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  	} 
	}
	
	/////////////////Book information panel///////////////
	private void setBookInfoPanel() 
	{
		f4 = new JFrame();
		f4.setSize(400, 400);
		f4.setLocation(f.getLocation());//设置窗口居中显示
		f4.setResizable(false);
	   
	    getBookInfoTable();
	   
	    Container contentPane = f4.getContentPane();
	    contentPane.add(s4, BorderLayout.CENTER);	    
	    
	    f4.setTitle("Book Information");
	    f4.setVisible(false);    	
	}

	/////////////////////Register panel///////////////////
	private void setRegisterPanel() {//need to be rewrite!!!!!!!!!!!!!
		f3 = new JFrame();
		f3.setSize(400, 400);
		f3.setLocation(f.getLocation());//设置窗口居中显示
		f3.setResizable(false);
		f3_panel = new JPanel(new GridLayout(6, 1, 10, 10));
		
		f3_panel1 = new JPanel(new GridLayout(1, 4, 10, 10));
		f3_panel1.add(getJLabel("\t*First Name", 5), null);
	    f3_panel1.add(getJTextField(5), null);    

	    f3_panel2 = new JPanel(new GridLayout(1, 4, 10, 10));
		f3_panel2.add(getJLabel("\t*Last Name", 6), null);
	    f3_panel2.add(getJTextField(6), null); 
	    
	    f3_panel3 = new JPanel(new GridLayout(1, 4, 10, 10));
		f3_panel3.add(getJLabel("\t*Ssn", 7), null);
	    f3_panel3.add(getJTextField(7), null); 
	    
	    f3_panel4 = new JPanel(new GridLayout(1, 4, 10, 10));
		f3_panel4.add(getJLabel("\t*Address", 8), null);
	    f3_panel4.add(getJTextField(8), null); 
	    
	    f3_panel5 = new JPanel(new GridLayout(1, 4, 10, 10));
		f3_panel5.add(getJLabel("\tPhone", 9), null);
	    f3_panel5.add(getJTextField(9), null); 

	    f3_panel.add(f3_panel1);
	    f3_panel.add(f3_panel2);
	    f3_panel.add(f3_panel3);
	    f3_panel.add(f3_panel4);
	    f3_panel.add(f3_panel5);
	    
	    f3_panel.add(getRegisterJButton("Register"));

	    Container contentPane = f3.getContentPane();
	    contentPane.add(f3_panel, BorderLayout.NORTH);
	    f3.setTitle("Register Page");
	    f3.setVisible(false);  	
	}

	private void setSignInPanel() {
		f2 = new JFrame();
		f2.setSize(400, 400);
		f2.setLocation(f.getLocation());//设置窗口居中显示
		f2.setResizable(false);
		
		f2_panel1 = new JPanel(new GridLayout(1, 4, 10, 10));
		f2_panel1.add(getJLabel("\tCard Number", 4), null);
	    f2_panel1.add(getJTextField(4), null);    
	    f2_panel = new JPanel(new GridLayout(3,1));
	    f2_panel.add(f2_panel1);
	    f2_panel.add(getJLogButton("Sign in"));
	    f2_panel.add(getJumpToRegButton("New User"));	    
	    Container contentPane = f2.getContentPane();
	    contentPane.add(f2_panel, BorderLayout.NORTH);
	    f2.setTitle("Library Login");
	    f2.setVisible(true);  	
	}

	///////////////book search panel/////////////////
	private void setSearchPanel()
	{
		f = new JFrame();
		f.setSize(400, 400);
		int windowWidth = f.getWidth();                     //获得窗口宽
		int windowHeight = f.getHeight();                   //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包
		Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸
		int screenWidth = screenSize.width;                     //获取屏幕的宽
		int screenHeight = screenSize.height;                   //获取屏幕的高
		f.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
	    panel1 = new JPanel(new GridLayout(1, 4, 10, 10));
	    panel2 = new JPanel(new GridLayout(1, 4, 10, 10));
	    panel3 = new JPanel(new GridLayout(1, 4, 10, 10));
	    
	    panel1.add(getJLabel("\tIsbn", 1), null);
	    panel1.add(getJTextField(1), null);

	    panel2.add(getJLabel("\tTitle", 2), null);
	    panel2.add(getJTextField(2), null);

	    panel3.add(getJLabel("\tAuthor", 3), null);
	    panel3.add(getJTextField(3), null);
	    
	    panel = new JPanel(new GridLayout(4,1));
	    panel.add(panel1);
	    panel.add(panel2);
	    panel.add(panel3);
	    panel.add(getSearchJButton("Search"));
	    getJTable();
	   
	    Container contentPane = f.getContentPane();
	    contentPane.add(panel, BorderLayout.NORTH);
	    contentPane.add(s, BorderLayout.CENTER);	    
	    
	    f.setTitle("Library Search Engine");
	    f.pack();//auto resize
	    f.setVisible(true);  	
	}
	
	private javax.swing.JTable getJTable(){
		   String[] columnNames = new String[3];
		   String[][] tableValues = new String[1][];
		   defaultModel = new DefaultTableModel(tableValues, columnNames){
			   @Override
			   public boolean isCellEditable(int row, int column)
			   {
				   return false;
			   }
		   };
		   
		   table = new JTable(defaultModel);
	       table.setVisible(true);
		   //table.setBounds(34, 250, 250, 150);
		   //set width of each column
		   TableColumn column = null;
		   for (int i = 0; i < 3; i++)
		   {
			   column = table.getColumnModel().getColumn(i);
			   column.setPreferredWidth(150);
		   }
		   table.setPreferredScrollableViewportSize(new Dimension(400,150));
		   
		   table.addMouseListener(new MouseListener() {
			
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int rowNum = table.getSelectedRow();
					if (rowNum == -1)
						return;
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					{
						selectedBookID = (String) table.getValueAt(rowNum, 0);
						f4.setVisible(true);
						System.out.println("double click! book Isnb is: " + searchTable[rowNum][0]);
						absoluteSearch(searchTable[rowNum][0]);
					}
				}
			});
		   s = new JScrollPane(table);
		   return table;
	} 
	
		
	//////////////book detail info panel///////////////
	private javax.swing.JTable getBookInfoTable(){
		   String[] columnNames2 = {"branch_ID", "branch_Name", "total copy", "available copy"};
		   bookInfoTable = new String[1][];
		   defaultModel4 = new DefaultTableModel(bookInfoTable, columnNames2){
			   @Override
			   public boolean isCellEditable(int row, int column)
			   {
				   return false;
			   }
		   };
		   
		   table2 = new JTable(defaultModel4);
	       table2.setVisible(true);
		   //table.setBounds(34, 250, 250, 150);
		   //set width of each column
		   TableColumn column = null;
		   for (int i = 0; i < 4; i++)
		   {
			   column = table2.getColumnModel().getColumn(i);
			   column.setPreferredWidth(150);
		   }
		   table2.setPreferredScrollableViewportSize(new Dimension(400,150));
		   
		   table2.addMouseListener(new MouseListener() {
			
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int rowNum = table2.getSelectedRow();
					if (rowNum == -1)
						return;
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					{
						String str = (String) table2.getValueAt(rowNum, 0);
						branchID = Integer.parseInt(str); 
						if (cardNo == null)
						{
							f2.setVisible(true);
							JOptionPane.showMessageDialog(f2_panel, "Please Log in first", "Login",JOptionPane.WARNING_MESSAGE);
						}
						else if (cardNo.equals("admin"))
						{
							f4.setVisible(true);
							f_date.setVisible(true);
						}
						else
						{
							int yes = JOptionPane.showConfirmDialog(null, "borrow book: "+ selectedBookID + "?", 
									"Comfirmation",JOptionPane.YES_NO_OPTION);
							if (yes == 0)
							{
								try {
									conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
									Statement stmt = conn.createStatement(
							    			ResultSet.TYPE_SCROLL_SENSITIVE,
							                ResultSet.CONCUR_UPDATABLE
							                );
							    	stmt.execute("use library;");
									int attemp = OperateDatabase.borrowBook(selectedBookID, branchID, cardNo, stmt);
									if (attemp == -1)
										JOptionPane.showMessageDialog(f2_panel, "Failed: cannot borrowe more than 3 boooks", 
												"Borrow failed",JOptionPane.WARNING_MESSAGE);
									else if (attemp == -2)
										JOptionPane.showMessageDialog(f2_panel, "Failed: unpaid fines", 
												"Borrow failed",JOptionPane.WARNING_MESSAGE);
									else if (attemp == -3)
										JOptionPane.showMessageDialog(f2_panel, "Failed: out of store", 
												"Borrow failed",JOptionPane.WARNING_MESSAGE);
									else
										JOptionPane.showMessageDialog(f2_panel, "Borrow succeed!", 
												"Succeed",JOptionPane.WARNING_MESSAGE);
									f4.setVisible(false);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
			});
		   s4 = new JScrollPane(table2);
		   return table2;
	} 
	
	private javax.swing.JLabel getJLabel(String labelName, int num) {
		switch(num)
		{
			case 1: {
				if(jLabel1 == null) {
				      jLabel1 = new javax.swing.JLabel();
				      jLabel1.setSize(new Dimension(100, 30));
				      jLabel1.setText(labelName + ": ");
				}
				return jLabel1;
			}
			case 2:{
				if(jLabel2 == null) {
				      jLabel2 = new javax.swing.JLabel();
				      jLabel2.setSize(new Dimension(100, 30));
				      jLabel2.setText(labelName + ": ");
				}
				return jLabel2;
			}
			case 3:{
				if(jLabel3 == null) {
				      jLabel3 = new javax.swing.JLabel();
				      jLabel3.setSize(new Dimension(100, 30));
				      jLabel3.setText(labelName + ": ");
				}
				return jLabel3;
			}
			case 4:{
				if(jLabel4 == null) {
				      jLabel4 = new javax.swing.JLabel();
				      jLabel4.setSize(new Dimension(100, 30));
				      jLabel4.setText(labelName + ": ");
				}
				return jLabel4;
			}
			case 5:{
				if(jLabel5 == null) {
				      jLabel5 = new javax.swing.JLabel();
				      jLabel5.setSize(new Dimension(100, 30));
				      jLabel5.setText(labelName + ": ");
				}
				return jLabel5;
			}
			case 6:{
				if(jLabel6 == null) {
				      jLabel6 = new javax.swing.JLabel();
				      jLabel6.setSize(new Dimension(100, 30));
				      jLabel6.setText(labelName + ": ");
				}
				return jLabel6;
			}
			case 7:{
				if(jLabel7 == null) {
				      jLabel7 = new javax.swing.JLabel();
				      jLabel7.setSize(new Dimension(100, 30));
				      jLabel7.setText(labelName + ": ");
				}
				return jLabel7;
			}
			case 8:{
				if(jLabel8 == null) {
				      jLabel8 = new javax.swing.JLabel();
				      jLabel8.setSize(new Dimension(100, 30));
				      jLabel8.setText(labelName + ": ");
				}
				return jLabel8;
			}
			case 9:{
				if(jLabel9 == null) {
				      jLabel9 = new javax.swing.JLabel();
				      jLabel9.setSize(new Dimension(100, 30));
				      jLabel9.setText(labelName + ": ");
				}
				return jLabel9;
			}
			case 10:{
				if(jLabel10 == null) {
				      jLabel10 = new javax.swing.JLabel();
				      jLabel10.setSize(new Dimension(100, 30));
				      jLabel10.setText(labelName + ": ");
				}
				return jLabel10;
			}
			case 11:{
				if(jLabel11 == null) {
				      jLabel11 = new javax.swing.JLabel();
				      jLabel11.setSize(new Dimension(100, 30));
				      jLabel11.setText(labelName + ": ");
				}
				return jLabel11;
			}
			case 12:{
				if(jLabel12 == null) {
				      jLabel12 = new javax.swing.JLabel();
				      jLabel12.setSize(new Dimension(100, 30));
				      jLabel12.setText(labelName + ": ");
				}
				return jLabel12;
			}
			default:return jLabel9;
		}
	}
	
	private javax.swing.JTextField getJTextField(int num) {
		switch(num)
		{
			case 1: {
				 if(jTextField1 == null) {
				      jTextField1 = new javax.swing.JTextField();
				      jTextField1.setMinimumSize(new Dimension(200, 30));
				   }
				   return jTextField1;
			}
			case 2:{
				if(jTextField2 == null) {
					jTextField2 = new javax.swing.JTextField();
					jTextField2.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField2;
			}
			case 3:{
				if(jTextField3 == null) {
					jTextField3 = new javax.swing.JTextField();
					jTextField3.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField3;
			}
			case 4:{
				if(jTextField4 == null) {
					jTextField4 = new javax.swing.JTextField();
					jTextField4.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField4;
			}
			case 5:{
				if(jTextField5 == null) {
					jTextField5 = new javax.swing.JTextField();
					jTextField5.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField5;
			}
			case 6:{
				if(jTextField6 == null) {
					jTextField6 = new javax.swing.JTextField();
					jTextField6.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField6;
			}
			case 7:{
				if(jTextField7 == null) {
					jTextField7 = new javax.swing.JTextField();
					jTextField7.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField7;
			}
			case 8:{
				if(jTextField8 == null) {
					jTextField8 = new javax.swing.JTextField();
					jTextField8.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField8;
			}
			case 9:{
				if(jTextField9 == null) {
					jTextField9 = new javax.swing.JTextField();
					jTextField9.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField9;
			}
			case 10:{
				if(jTextField10 == null) {
					jTextField10 = new javax.swing.JTextField();
					jTextField10.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField10;
			}
			case 11:{
				if(jTextField11 == null) {
					jTextField11 = new javax.swing.JTextField();
					jTextField11.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField11;
			}
			case 12:{
				if(jTextField12 == null) {
					jTextField12 = new javax.swing.JTextField();
					jTextField12.setMinimumSize(new Dimension(200, 30));
				}
				return jTextField12;
			}
			default:return jTextField1;
		}
	}
	
	/////////////////////Search Button//////////////////////
	private javax.swing.JButton getSearchJButton(String str) {
	   if(jButton == null) {
	      jButton = new javax.swing.JButton("str");
	      jButton.setText(str);
	      jButton.setSize(new Dimension(100, 30));

    	  // Register as a listener 
	  	  jButton.addActionListener(new SearchButtonListener()); 
	   }
	   return jButton;
	}
	
	class SearchButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			isbn = jTextField1.getText();
  			title = jTextField2.getText();
  			author = jTextField3.getText(); 	
  			fuzzySearch(isbn, title, author, 0);//fuzzy search
  		} 
  	}
	
	/////////////////////Search Button//////////////////////
	private javax.swing.JButton getAdminSearchButton(String str) {
	   if(jButton6 == null) {
	      jButton6 = new javax.swing.JButton("str");
	      jButton6.setText(str);
	      jButton6.setSize(new Dimension(100, 30));

    	  // Register as a listener 
	  	  jButton6.addActionListener(new AdminSearchButtonListener()); 
	   }
	   return jButton6;
	}
	
	class AdminSearchButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			isbn = jTextField10.getText();
  			title = jTextField11.getText();
  			author = jTextField12.getText(); 	
  			fuzzySearch(isbn, title, author, 1);//fuzzy search
  		} 
  	}
	
	/////////////////////Admin Check fines Button//////////////////////
	private javax.swing.JButton getAdminFineButton(String str) {
	   if(jButton7 == null) {
	      jButton7 = new javax.swing.JButton("str");
	      jButton7.setText(str);
	      jButton7.setSize(new Dimension(100, 30));

    	  // Register as a listener 
	  	  jButton7.addActionListener(new AdminFineButtonListener()); 
	   }
	   return jButton7;
	}
	
	class AdminFineButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			f_fine.setVisible(true);
  		} 
  	}

	/////////////////Login Button////////////////
	private javax.swing.JButton getJLogButton(String str) {
		if(jButton2 == null) {
		     jButton2 = new javax.swing.JButton("str");
		     jButton2.setText(str);
		     jButton2.setSize(new Dimension(100, 30));

		     // Register as a listener 
		     jButton2.addActionListener(new LogButtonListener()); 
		}
		return jButton2;
	}
	
	class LogButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			String inputCard = jTextField4.getText();
  			if (inputCard.equals("admin"))
  			{
  				f_admin.setVisible(true);
  				cardNo = inputCard;
  				f2.setVisible(false); 
  				return;
  			}
  			f2.setVisible(false); 	
  			// Create a connection to the local MySQL server, with the "company" database selected.
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
				Statement stmt = conn.createStatement(
		    			ResultSet.TYPE_SCROLL_SENSITIVE,
		                ResultSet.CONCUR_UPDATABLE
		                );
		    	stmt.execute("use library;");
		    	
		    	//call the search book to query the database, str store the search result
		    	Boolean verified = OperateDatabase.identifyCardNo(inputCard, stmt);
		    	if (verified)
		    	{
		    		f2.setVisible(false);
		    		cardNo = inputCard;
		    		JOptionPane.showMessageDialog(f3_panel, "Welcome back, " + cardNo, "Log in succeed",JOptionPane.WARNING_MESSAGE);
		    	}
		    	else
		    	{
		    		cardNo = null;
		    		JOptionPane.showMessageDialog(f3_panel, "Invalid Card Number!\n"
		    				+ "Please try another Card.", "Error",JOptionPane.WARNING_MESSAGE);
		    		f2.setVisible(true);
		    	}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  			
  		} 
  	}
 
	/////////////////////Jump To Register Button//////////////////////
	private javax.swing.JButton getJumpToRegButton(String str) {
		if(jButton3 == null) {
		     jButton3 = new javax.swing.JButton("str");
		     jButton3.setText(str);
		     jButton3.setSize(new Dimension(100, 30));

		     // Register as a listener 
		     jButton3.addActionListener(new JumpToRegButtonListener()); 
		}
		return jButton3;
	}
	
	class JumpToRegButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			f2.setVisible(true); 		
  			f3.setVisible(true); 	
  		} 
  	}

	/////////////////////Register Button//////////////////////
	private javax.swing.JButton getRegisterJButton(String str) {
		if(jButton4 == null) {
		     jButton4 = new javax.swing.JButton("str");
		     jButton4.setText(str);
		     jButton4.setSize(new Dimension(100, 30));

		     // Register as a listener 
		     jButton4.addActionListener(new RegisterJButtonListener()); 
		}
		return jButton4;
	}
	
	class RegisterJButtonListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			String fname = jTextField5.getText();
  			String lname = jTextField6.getText();
  			String ssn = jTextField7.getText();
  			String address = jTextField8.getText();
  			String phone = jTextField9.getText();
	
  			// Create a connection to the local MySQL server, with the "company" database selected.
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
				Statement stmt = conn.createStatement(
		    			ResultSet.TYPE_SCROLL_SENSITIVE,
		                ResultSet.CONCUR_UPDATABLE
		                );
		    	stmt.execute("use library;");
		    	
		    	//call the search book to query the database, str store the search result
		    	String createdID = OperateDatabase.createNewBorrower(fname, lname, ssn, address, phone, stmt);
		    	if (createdID.equals("Ssn Already Registered"))
		    	{
		    		JOptionPane.showMessageDialog(f3_panel, "Ssn Already Registered", "Error",JOptionPane.WARNING_MESSAGE);
		    		f3.setVisible(true);
		    	}
		    	else
		    	{
		    		JOptionPane.showMessageDialog(f3_panel, "User created!\nYour Card ID is: " + createdID
		    				, "Succeed",JOptionPane.WARNING_MESSAGE);
		    		f3.setVisible(false);
		    	}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}  			
  		} 
  	}
	
	
	private void absoluteSearch(String isbn)
    {
    	try {
    		// Create a connection to the local MySQL server, with the "company" database selected.
    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
    		Statement stmt = conn.createStatement(
    			ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
                );
    		stmt.execute("use library;");
    		
    		//call the search book to query the database, str store the search result
    		bookInfoTable = OperateDatabase.searchBook(isbn, stmt);
    	
    		//delete all row
    		while(true)
    		{
    			int rowcount = defaultModel4.getRowCount()-1;//getRowCount return row number
    			if(rowcount >= 0){
    				defaultModel4.removeRow(rowcount);
    				defaultModel4.setRowCount(rowcount);
    			}
    			else
    				break;
    		}
    		
    		//add new rows
    		for (int i = 0; i < bookInfoTable.length; i++)
    			defaultModel4.addRow(bookInfoTable[i]);
    		f4.setVisible(true);
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	//adminSearch = 1: change table in admin panel; adminSearch = 0: change table in search panel
    private void fuzzySearch(String isbn, String title, String author, int adminSearch)
    {
    	try {
    		// Create a connection to the local MySQL server, with the "company" database selected.
    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
    		Statement stmt = conn.createStatement(
    			ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
                );
    		stmt.execute("use library;");
    		
    		//call the search book to query the database, str store the search result
    		searchTable = OperateDatabase.searchBook(isbn, title, author, stmt);
    		//delete all row
    		while(true)
    		{
    			if (adminSearch == 1)
    			{
    				int rowcount = defaultModel_admin.getRowCount()-1;//getRowCount return row number
        			if(rowcount >= 0){
        				defaultModel_admin.removeRow(rowcount);
        				defaultModel_admin.setRowCount(rowcount);
        			}
        			else
        				break;
    			}
    			else
    			{
    				int rowcount = defaultModel.getRowCount()-1;//getRowCount return row number
        			if(rowcount >= 0){
        				defaultModel.removeRow(rowcount);
        				defaultModel.setRowCount(rowcount);
        			}
        			else
        				break;	
    			}
    		}
    		//add new rows
    		if (adminSearch == 1)
    		{
	    		for (int i = 0; i < searchTable.length; i++)
	    		{
	    			defaultModel_admin.addRow(searchTable[i]);
	    		}
    		}
    		else
    		{
    			for (int i = 0; i < searchTable.length; i++)
	    		{
	    			defaultModel.addRow(searchTable[i]);
	    		}
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void displayAllFine()
    {
    	try {
    		// Create a connection to the local MySQL server, with the "company" database selected.
    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "128517");
    		Statement stmt = conn.createStatement(
    			ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
                );
    		stmt.execute("use library;");
    		
    		//call the search book to query the database, str store the search result
    		searchTable_fine = OperateDatabase.displayAllFine(stmt);
	
    		//delete all row
    		while(true)
    		{
    			int rowcount = defaultModel_fine.getRowCount()-1;//getRowCount return row number
        		if(rowcount >= 0){
        			defaultModel_fine.removeRow(rowcount);
        			defaultModel_fine.setRowCount(rowcount);
        		}
        		else
        			break;
    		}
    		//add new rows
	    	for (int i = 0; i < searchTable_fine.length; i++)
	    	{
	    		defaultModel_fine.addRow(searchTable_fine[i]);
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}



