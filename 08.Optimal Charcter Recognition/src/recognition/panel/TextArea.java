package recognition.panel;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class TextArea extends JTextArea implements FocusListener{
	private Font font;
	private final String hint;
	private boolean showingHint;	  
	  
	public TextArea(){
		super("Please input a sentense");
		font = new Font("font1", Font.BOLD, 30);
		this.hint = "Please input a word...";
		this.showingHint = true;
		this.setFont(font);
		this.setSize(WIDTH, HEIGHT);
		this.setEditable(true);
		super.addFocusListener(this);
	}
	
	public int[] getImageRGBs() throws IOException{
		int width = 600;
		int height = 38;
		int[] rgbs = new int[width * height];
		try{
			Robot robot = new Robot();
			Rectangle rec = new Rectangle(this.getLocationOnScreen().x, this.getLocationOnScreen().y, width, height);
			BufferedImage image = robot.createScreenCapture(rec);
			rgbs = image.getRGB(0, 0, width, height, rgbs, 0, width); 
			//output image file
			OutputStream output = new FileOutputStream(new File("sentense1.jpg"));
	        ImageIO.write(image, "jpeg", output);   
	        
		}catch(AWTException e){
			e.printStackTrace();
		}
		return rgbs;
	}
	
	public boolean deleteWord(){
		String text = super.getText();
		
		if(text.isEmpty()) 
			return false;
		super.setText(text.substring(0, text.length() - 1));
		return true;
	}
	
	public void addWord(String text){
		String oldText = super.getText();
		super.setText(oldText + text);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			this.setForeground(Color.black);
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			this.setForeground(Color.gray);
		    showingHint = true;
		}
	}
	
	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}

