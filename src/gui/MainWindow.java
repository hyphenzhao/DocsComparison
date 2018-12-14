package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import data.FileIO;
import model.Article;
import model.HighlightArea;
import core.GlobalValue;
import core.StaticMethods;

public class MainWindow extends JFrame{
	private JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
	private JTextField threshold = new JTextField(5);
	private JTextArea paperField[] = new JTextArea[2];
	private JButton compareButton;
	private boolean paperLoaded[] = new boolean[2];
	private Article papers[] = new Article[2];
	private InformationPage infoMessageBox = new InformationPage();
	
	public MainWindow() {
		this.setName("Paper Similarity/Differences Check");
		this.setTitle("Paper Similarity/Differences Check");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		infoMessageBox.setVisible(false);
		this.setupMainPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
	
	public void toggleMainPanel() {
		boolean v = !mainPanel.isEnabled();
		mainPanel.setEnabled(v);
		mainPanel.setFocusable(v);
		Component[] mainComs = mainPanel.getComponents();
		for (int a = 0; a < mainComs.length; a++) {
		     mainComs[a].setEnabled(v);
		}
		infoMessageBox.setVisible(!v);
		Component[] infoComs = infoMessageBox.getComponents();
		for (int a = 0; a < infoComs.length; a++) {
			infoComs[a].setEnabled(!v);
		}
	}
	
	public void setupMainPanel() {
		JButton quitButton = new JButton();
		quitButton.setText("Exit");
		quitButton.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	System.exit(0);
		    }
		});
		compareButton = new JButton();
		compareButton.setText("Compare Now!");
		compareButton.setEnabled(false);
		compareButton.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	toggleMainPanel();
		    	
		    	int preferredThreshold = Integer.parseInt(threshold.getText());
		    	GlobalValue.SIMILARITY_THRESHOLD = preferredThreshold;
		    	HighlightArea area[] = StaticMethods.getComparisonHighlightResult(papers[0], papers[1]);
		    	for(int i = 0; i < 2; i++) {
		    		Highlighter highlighter = paperField[i].getHighlighter();
		    		HighlightPainter samePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
		    		HighlightPainter similarPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
		    		highlighter.removeAllHighlights();
		    		for(int j = 0; j < area[i].size(); j++) {
		    			try {
		    				//System.out.println(area[i].start.get(j) + ", " + area[i].end.get(j));
							highlighter.addHighlight(area[i].start.get(j).intValue(), area[i].end.get(j).intValue(), samePainter);
		    				//highlighter.addHighlight(30000, 31000, painter);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}
		    		for(int j = 0; j < area[i].size1(); j++) {
		    			try {
		    				//System.out.println(area[i].start1.get(j) + ", " + area[i].end1.get(j));
							highlighter.addHighlight(area[i].start1.get(j).intValue(), area[i].end1.get(j).intValue(), similarPainter);
		    				//highlighter.addHighlight(30000, 31000, painter);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}
		    	}
		    	
		    	toggleMainPanel();
		    }
		});
		JPanel leftPanel = getPanelNo(1);
		JPanel rightPanel = getPanelNo(2);
		
		JPanel leftDownPanel = new JPanel();
		leftDownPanel.setLayout(new FlowLayout());
		
		JLabel thresholdLabel = new JLabel("Similarity check threshold:");
		leftDownPanel.add(thresholdLabel);
		threshold.setText(Integer.toString(GlobalValue.SIMILARITY_THRESHOLD));
		leftDownPanel.add(threshold);
		leftPanel.add(leftDownPanel);
		
		JPanel rightDownPanel = new JPanel();
		rightDownPanel.setLayout(new FlowLayout());
		rightDownPanel.add(compareButton);
		rightDownPanel.add(quitButton);
		rightPanel.add(rightDownPanel);
		
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
	}
	
	public JPanel getPanelNo(int no) {
		JPanel panel = new JPanel();
		JButton loadPaperButton = new JButton("Load paper " + no);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		loadPaperButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(mainPanel);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            try {
		              FileIO preprocessedFile = new FileIO(file);
		              papers[no - 1] = new Article(preprocessedFile.loadFileText());
		              paperField[no - 1].setText(papers[no - 1].getWholeArticle());
		              paperLoaded[no - 1] = true;
		              System.out.println(papers[no - 1].getWholeArticle().length());
		              System.out.println(paperField[no - 1].getText().length());
		              if(paperLoaded[0] && paperLoaded[1]) {
		            	  compareButton.setEnabled(true);
		              }
		            } catch (Exception ex) {
		              System.out.println("problem accessing file"+file.getAbsolutePath());
		              ex.printStackTrace();
		            }
		        } 
		        else {
		            System.out.println("File access cancelled by user.");
		        }
			}
			
		});
		panel.add(loadPaperButton);
		paperField[no - 1] = new JTextArea(50, 50);
		paperField[no - 1].setEditable(false);
		paperField[no - 1].setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(paperField[no - 1],
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(23, 40, 394, 191);
		panel.add(scrollPane);
		return panel;
	}
	
}
