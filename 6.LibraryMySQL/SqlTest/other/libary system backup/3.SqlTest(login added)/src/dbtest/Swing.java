package dbtest;

import java.awt.BorderLayout;
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

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Swing implements ActionListener{
	Statement stmt;
	/////////////search panel//////////////
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
	
	/////////////login panel//////////////
	private String cardNo;
	private JFrame f2;
    private JPanel f2_panel, f2_panel1;
	private JLabel jLabel4;
	private JTextField jTextField4;
	private JButton jButton2;
	
	/////////////register panel//////////////
	private String registerName, registerSsn, registerAddress;
	private JFrame f3;
    private JPanel f3_panel, f3_panel1, f3_panel2;
	private JLabel jLabel5, jLabel6, jLabel7;
	private JTextField jTextField5, jTextField6, jTextField7;
	private JButton jButton3;
	
	public Swing()
	{
		setSearchPanel();
		setSignInPanel();
		setRegisterPanel();
	}
	
	private void setRegisterPanel() {//need to be rewrite!!!!!!!!!!!!!
//		f2 = new JFrame();
//		f2.setSize(400, 400);
//		f2.setLocation(f.getLocation());//设置窗口居中显示
//		f2.setResizable(false);
//		
//		f2_panel1 = new JPanel(new GridLayout(1, 4, 10, 10));
//		f2_panel1.add(getJLabel("\tCard Number", 4), null);
//	    f2_panel1.add(getJTextField(4), null);    
//	    f2_panel = new JPanel(new GridLayout(3,1));
//	    f2_panel.add(f2_panel1);
//	    f2_panel.add(getJLogButton("Sign in"));
//	    Container contentPane = f2.getContentPane();
//	    contentPane.add(f2_panel, BorderLayout.NORTH);
//	    f2.setTitle("Library Login");
//	    f2.setVisible(true);  
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
	    Container contentPane = f2.getContentPane();
	    contentPane.add(f2_panel, BorderLayout.NORTH);
	    f2.setTitle("Library Login");
	    f2.setVisible(true);  	
	}

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

	    panel3.add(getJLabel("Author", 3), null);
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
		   String[] columnNames = { "Isnb", "Title", "Author" };
		   String[][] tableValues = {{"A1", "B1", "C1"}, {"A2", "B2", "C2"}, {"A3", "B3", "C3"}};
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
						int yes = JOptionPane.showConfirmDialog(null, "borrow "+ searchTable[rowNum][1] + "?", 
								"Comfirmation",JOptionPane.YES_NO_OPTION);
						if (yes == 0)
							System.out.println(searchTable[rowNum][1] + ": yes");
					}
				}
			});
		   s = new JScrollPane(table);
		   return table;
	} 
	
	private javax.swing.JLabel getJLabel(String labelName, int num) {
		switch(num)
		{
			case 1: {
				if(jLabel1 == null) {
				      jLabel1 = new javax.swing.JLabel();
				      jLabel1.setSize(new Dimension(100, 30));
				  //    jLabel1.setBounds(34, (49 * num), 53, 18);
				      jLabel1.setText(labelName + ": ");
				}
				return jLabel1;
			}
			case 2:{
				if(jLabel2 == null) {
				      jLabel2 = new javax.swing.JLabel();
				      jLabel2.setSize(new Dimension(100, 30));
				      
			//	      jLabel2.setBounds(34, (49 * num), 53, 18);
				      jLabel2.setText(labelName + ": ");
				}
				return jLabel2;
			}
			case 3:{
				if(jLabel3 == null) {
				      jLabel3 = new javax.swing.JLabel();
				      jLabel3.setSize(new Dimension(100, 30));
				//      jLabel3.setBounds(34, (49 * num), 53, 18);
				      jLabel3.setText(labelName + ": ");
				}
				return jLabel3;
			}
			case 4:{
				if(jLabel4 == null) {
				      jLabel4 = new javax.swing.JLabel();
				      jLabel4.setSize(new Dimension(100, 30));
				//      jLabel3.setBounds(34, (49 * num), 53, 18);
				      jLabel4.setText(labelName + ": ");
				}
				return jLabel4;
			}
			default:return jLabel1;
		}
	}
	
	private javax.swing.JTextField getJTextField(int num) {
		switch(num)
		{
			case 1: {
				 if(jTextField1 == null) {
				      jTextField1 = new javax.swing.JTextField();
				      jTextField1.setMinimumSize(new Dimension(200, 30));
				//      jTextField1.setBounds(96, (49 * num), 180, 20);
				   }
				   return jTextField1;
			}
			case 2:{
				if(jTextField2 == null) {
					jTextField2 = new javax.swing.JTextField();
					jTextField2.setMinimumSize(new Dimension(200, 30));
		//		    jTextField2.setBounds(96, (49 * num), 180, 20);
				}
				return jTextField2;
			}
			case 3:{
				if(jTextField3 == null) {
					jTextField3 = new javax.swing.JTextField();
					jTextField3.setMinimumSize(new Dimension(200, 30));
			//	    jTextField3.setBounds(96, (49 * num), 180, 20);
				}
				return jTextField3;
			}
			case 4:{
				if(jTextField4 == null) {
					jTextField4 = new javax.swing.JTextField();
					jTextField4.setMinimumSize(new Dimension(200, 30));
			//	    jTextField3.setBounds(96, (49 * num), 180, 20);
				}
				return jTextField4;
			}
			default:return jTextField1;
		}
	}
	
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
  			fuzzySearch(isbn, title, author);//fuzzy search
  		} 
  	}
	
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
  			f2.setVisible(false); 
  			System.out.println(inputCard + "$$$$$$$$$$$$$");	
  			// Create a connection to the local MySQL server, with the "company" database selected.
    		Connection conn;
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
		    		System.out.println("Pass the verification!");
		    	}
		    	else
		    	{
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

//	public void actionPerformed(ActionEvent e)
//	{
//			isbn = jTextField1.getText();
//			title = jTextField2.getText();
//			author = jTextField3.getText(); 			
////			System.out.println("isnb = " + isbn); 
////			System.out.println("title = " + title); 
////			System.out.println("author = " + author); 	
//			fuzzySearch(isbn, title, author);//fuzzy search
//	}
	
    private void fuzzySearch(String isbn, String title, String author)
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
    			int rowcount = defaultModel.getRowCount()-1;//getRowCount返回行数，rowcount<0代表已经没有任何行了。
    			if(rowcount >= 0){
    				defaultModel.removeRow(rowcount);
    				defaultModel.setRowCount(rowcount);
    			}
    			else
    				break;
    		}
    		
    		//add new rows
    		for (int i = 0; i < searchTable.length; i++)
    		{
    			defaultModel.addRow(searchTable[i]);
    		}
    		
			//String[][] str = OperateDatabase.searchBook(isbn, stmt);//absolute search
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}















