package recognition;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import recognition.panel.DrawArea;
import recognition.panel.TextArea;
import recognition.panel.ToolBar;


public class WritingFrame extends JFrame{
	private int drawArea_width = 300, drawArea_height = 300;
	private DrawArea[] drawAreas;  
	private JSplitPane textSplitPane, drawSplitPane;
	private TextArea textArea;    
	private ToolBar toolBar;       

	public WritingFrame(){
		super("Howie is awesome");
		
		drawAreas = new DrawArea[2];
		drawAreas[0] = new DrawArea(this, drawArea_width, drawArea_height);
		drawAreas[1] = new DrawArea(this, drawArea_width, drawArea_height);
		
		drawSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawAreas[0], drawAreas[1]);
		drawSplitPane.setDividerLocation(drawArea_width);
		drawSplitPane.setDividerSize(10);
		drawSplitPane.setEnabled(false);
		
		textArea = new TextArea();
		
		textSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textArea, drawSplitPane);
		textSplitPane.setDividerSize(10);
		textSplitPane.setEnabled(false);
	
		toolBar = new ToolBar(this);
		
		this.add(toolBar, BorderLayout.EAST);
		this.add(textSplitPane, BorderLayout.CENTER);
		
		this.setSize(drawArea_width * 2 + 73, drawArea_height + 80);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null); 
	}

	//OK
	public DrawArea getCurrentDrawArea(){
		if(drawAreas[0].getStatus()) return drawAreas[0];
		else return drawAreas[1];
	}

	public DrawArea[] getDrawAreas(){
		return drawAreas;
	}
	
	public TextArea getTextAreas(){
		return textArea;
	}
}
