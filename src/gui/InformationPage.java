package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InformationPage extends JFrame{
	private JPanel mainPanel = new JPanel(new GridLayout(0, 1, 20, 20));
	
	public InformationPage() {
		this.setName("Processing...");
		this.setTitle("Processing...");
		this.setSize(400, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getSize().width/2, dim.height/2 - this.getSize().height/2);
		this.setResizable(false);
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JTextArea info = new JTextArea();
		info.setLineWrap(true);
		info.setText("The program is processing similarity check, please be patient.\n If no response for too long time, please contact the developer.");
		info.setEditable(false);
		infoPanel.add(info);
		mainPanel.add(infoPanel);
		mainPanel.setVisible(true);
		infoPanel.setVisible(true);
		info.setVisible(true);
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
