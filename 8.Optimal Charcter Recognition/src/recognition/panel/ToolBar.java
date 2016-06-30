package recognition.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import recognition.WritingFrame;
import term_project.MainFunc;

public class ToolBar extends JToolBar {
	private JButton recognitionButton, cleanButton, redoButton,
			learnButton, wordsButton, sizeUpButton, sizeDownButton;
	private WritingFrame writingFrame;
	private MainFunc mf;
	private boolean flag;

	public ToolBar(WritingFrame writingFrame) {
		super();
		this.writingFrame = writingFrame;

		recognitionButton = new JButton("Recog");
		learnButton = new JButton("Learn");
		redoButton = new JButton("Redo");
		cleanButton = new JButton("Clear");
		wordsButton = new JButton("Words");
		sizeUpButton = new JButton("Size+");
		sizeDownButton = new JButton("Size-");
		addAllListeners();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("   "));
		this.add(wordsButton);
		this.add(new JLabel("   "));
		this.add(recognitionButton);
		this.add(new JLabel("   "));
		this.add(learnButton);
		this.add(new JLabel("   "));
		this.add(redoButton);
		this.add(new JLabel("   "));
		this.add(cleanButton);
		this.add(new JLabel("   "));
		this.add(sizeUpButton);
		this.add(new JLabel("   "));
		this.add(sizeDownButton);
		this.add(new JLabel("   "));
		this.setEnabled(false);
		
		this.mf = new MainFunc();
	}
	
	private class sizeUpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("botton pressed!");
			writingFrame.getCurrentDrawArea().sizeUp();
		}	
	}
	
	private class sizeDownButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("botton pressed!");
			writingFrame.getCurrentDrawArea().sizeDown();
		}	
	}

	private class wordsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("botton pressed!");
			try {
				if (writingFrame.getCurrentDrawArea().isBlank()) {
					String message = "Error: Please write a word!";
					JOptionPane.showMessageDialog(writingFrame, message, "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!flag)
				{
					String message = "Error: Please learn first!";
					JOptionPane.showMessageDialog(writingFrame, message, "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{	
					writingFrame.getCurrentDrawArea().getFeatures2();
					List<Integer> left = writingFrame.getCurrentDrawArea().getLeftBorder2();
					List<Integer> up = writingFrame.getCurrentDrawArea().getUpBorder2();
					List<Integer> gridWidth = writingFrame.getCurrentDrawArea().getGridWidth2();
					List<Integer> gridHeight = writingFrame.getCurrentDrawArea().getGridHeight2();
					List<String> letters = new ArrayList<String>();
						
					for (int j = 0; j < left.size(); j++)
					{
						int[] rgbs = writingFrame.getCurrentDrawArea().getImageRGBs(left.get(j), up.get(j),
								gridWidth.get(j), gridHeight.get(j));
						int[] pixels = writingFrame.getCurrentDrawArea().getPixels(rgbs);//pixels is the interface I need!!!!!
						
						writingFrame.getCurrentDrawArea().displayImage(pixels, "character" + j + ".jpg");//pixels must be 1/0
						
						double[] pixels2 = new double[pixels.length];
						//type convert from int[] to double[]
						for(int i = 0; i < pixels.length; i++) {
						    pixels2[i] = pixels[i];
						}
						//recognition step
						letters.add(mf.recognize(pixels2));
					}
						
					TextArea ta = writingFrame.getTextAreas();
					ta.setForeground(Color.BLACK);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < left.size(); i++)
						sb.append(letters.get(i));
					ta.setText("Your input character is: " + sb.toString());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class recognitionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("botton pressed!");
			try {
				if (writingFrame.getCurrentDrawArea().isBlank()) {
					String message = "Error: Please write a letter!";
					JOptionPane.showMessageDialog(writingFrame, message, "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!flag)
				{
					String message = "Error: Please learn first!";
					JOptionPane.showMessageDialog(writingFrame, message, "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					writingFrame.getCurrentDrawArea().getFeatures();
					int left = writingFrame.getCurrentDrawArea().getLeftBorder();
					int up = writingFrame.getCurrentDrawArea().getUpBorder();
					int gridWidth = writingFrame.getCurrentDrawArea().getGridWidth();
					int gridHeight = writingFrame.getCurrentDrawArea().getGridHeight();
						
					int[] rgbs = writingFrame.getCurrentDrawArea().getImageRGBs(left, up, gridWidth, gridHeight);
					int[] pixels = writingFrame.getCurrentDrawArea().getPixels(rgbs);//pixels is the interface I need!!!!!
					double[] pixels2 = new double[pixels.length];
					//type convert from int[] to double[]
					for(int i = 0; i < pixels.length; i++) {
					    pixels2[i] = pixels[i];
					}
					//recognition step
					String letter = mf.recognize(pixels2);
					TextArea ta = writingFrame.getTextAreas();
					ta.setForeground(Color.BLACK);
					ta.setText("Your input character is: " + letter);
					
					writingFrame.getCurrentDrawArea().displayImage(pixels, "character1.jpg");//pixels must be 1/0
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class learnButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				mf.initBP();
				flag = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class cleanButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			writingFrame.getCurrentDrawArea().cleanScreen();
		}
	}

	private class redoButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			writingFrame.getCurrentDrawArea().redo();
		}
	}

	private void addAllListeners() {
		sizeUpButton.addActionListener(new sizeUpButtonListener());
		sizeDownButton.addActionListener(new sizeDownButtonListener());
		learnButton.addActionListener(new learnButtonListener());
		cleanButton.addActionListener(new cleanButtonListener());
		recognitionButton.addActionListener(new recognitionButtonListener());
		redoButton.addActionListener(new redoButtonListener());
		wordsButton.addActionListener(new wordsButtonListener());
	}
}
