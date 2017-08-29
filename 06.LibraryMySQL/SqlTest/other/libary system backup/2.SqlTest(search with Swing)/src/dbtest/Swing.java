package dbtest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.event.*;
import java.sql.Statement;

public class Swing extends JFrame{
	private JLabel jLabel1, jLabel2, jLabel3;
	private JTextField jTextField1, jTextField2, jTextField3;
	private JButton jButton;
	String isbn, title, author;
	Statement stmt;
	
	public Swing()
	{
	   super();                           
	   this.setSize(300, 300);
	   this.getContentPane().setLayout(null);
	   this.add(getJLabel("Isbn", 1), null);
	   this.add(getJLabel("Title", 2), null);
	   this.add(getJLabel("Author", 3), null);
	   this.add(getJTextField(1), null);
	   this.add(getJTextField(2), null);
	   this.add(getJTextField(3), null);
	   this.add(getJButton(3), null);
	   this.setTitle("Library Search Engine");
	   //build table
//	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	   String[] columnNames = { "A", "B" };
//	   String[][] tableValues = {{"A1", "B1"}, {"A2", "B2"},
//			   {"A3", "B3"}, {"A4", "B4"}, {"A5", "B5"}};
//	   JTable table = new JTable(tableValues, columnNames);
//	   JScrollPane scrollPane = new JScrollPane(table);
//	   getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	
	private javax.swing.JLabel getJLabel(String labelName, int num) {
		switch(num)
		{
			case 1: {
				if(jLabel1 == null) {
				      jLabel1 = new javax.swing.JLabel();
				      jLabel1.setBounds(34, (49 * num), 53, 18);
				      jLabel1.setText(labelName + ": ");
				}
				return jLabel1;
			}
			case 2:{
				if(jLabel2 == null) {
				      jLabel2 = new javax.swing.JLabel();
				      jLabel2.setBounds(34, (49 * num), 53, 18);
				      jLabel2.setText(labelName + ": ");
				}
				return jLabel2;
			}
			case 3:{
				if(jLabel3 == null) {
				      jLabel3 = new javax.swing.JLabel();
				      jLabel3.setBounds(34, (49 * num), 53, 18);
				      jLabel3.setText(labelName + ": ");
				}
				return jLabel3;
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
				      jTextField1.setBounds(96, (49 * num), 160, 20);
				   }
				   return jTextField1;
			}
			case 2:{
				if(jTextField2 == null) {
					jTextField2 = new javax.swing.JTextField();
				    jTextField2.setBounds(96, (49 * num), 160, 20);
				}
				return jTextField2;
			}
			case 3:{
				if(jTextField3 == null) {
					jTextField3 = new javax.swing.JTextField();
				    jTextField3.setBounds(96, (49 * num), 160, 20);
				}
				return jTextField3;
			}
			default:return jTextField1;
		}
	}
	
	private javax.swing.JButton getJButton(int num) {
	   if(jButton == null) {
	      jButton = new javax.swing.JButton();
	      jButton.setBounds(103, (110 + 2 * 49), 100, 27);
	      jButton.setText("Search");

    	  // Register as a listener 
	  	  jButton.addActionListener(new SwingListener());   
	   }
	   return jButton;
	}
	
	
	public static void main(String[] args)
	{
	   Swing w = new Swing();
	   w.setVisible(true);
	}
	
	class SwingListener implements ActionListener 
  	{ 
  		// The interface method to receive button clicks 
  		public void actionPerformed(ActionEvent e) 
  		{ 
  			isbn = jTextField1.getText();
  			title = jTextField2.getText();
  			author = jTextField3.getText(); 			
//  			System.out.println("isnb = " + isbn); 
//  			System.out.println("title = " + title); 
//  			System.out.println("author = " + author); 	
  			DBTest.fuzzySearch(isbn, title, author);//fuzzy search
  		} 
  	} 
}















