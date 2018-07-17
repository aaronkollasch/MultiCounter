package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ControlPane extends JPanel {
	private Integer readout;
	private JTextPane titleBox;
	private JTextField readoutBox;
	private JTextPane instrText;
	private JButton resetButton;
	
	public ControlPane(Integer readout)
	{
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.readout = readout;
		this.setPreferredSize(new Dimension(100,10));
		
		JPanel readoutPanel = new JPanel(new FlowLayout());
		
		titleBox = new JTextPane();
		StyledDocument doc = titleBox.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		titleBox.setText("Current Value:");
		titleBox.setEditable(false);
		titleBox.setFocusTraversalKeysEnabled(false);
		titleBox.setOpaque(false);
		titleBox.setFont(new Font(titleBox.getFont().getName(), Font.BOLD, 12));
		//titleBox.setPreferredSize(new Dimension(80, 15));
		readoutPanel.add(titleBox);
		
		readoutBox = new JTextField();
		readoutBox.setHorizontalAlignment(JTextField.CENTER);
		readoutBox.setText(readout.toString());
		readoutBox.setEditable(false);
		//readoutBox.setFocusable(false);
		readoutBox.setPreferredSize(new Dimension(getPreferredSize().width, readoutBox.getPreferredSize().height));
		readoutPanel.add(readoutBox);
		
		readoutPanel.setPreferredSize(new Dimension(readoutPanel.getPreferredSize().width, 50));
		
		this.add(readoutPanel);
		
		instrText = new JTextPane();
		doc = instrText.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		instrText.setText(
				"Num Keys Add\n"
				+ "J, F \u2192 +1\n"
				+ "K, D \u2192 +2\n"
				+ "L, S \u2192 +3\n"
				+ ";, A \u2192 +4\n"
				//+ "'\t5\n"
				+ "Space: Save\n"
				+ "Tab: Save\n"
				+ "Enter: Save\n"
				+ "R: Reset\n"
				+ "X: Drop\n"
				+ "Bksp: Bksp\n"
				+ "C: Copy Data");
		instrText.setEditable(false);
		instrText.setFocusTraversalKeysEnabled(false);
		instrText.setOpaque(false);
		this.add(instrText);
		
		resetButton = new JButton("Reset");
		resetButton.setFocusTraversalKeysEnabled(false);
		this.add(resetButton);
		
		this.setMinimumSize(new Dimension(getPreferredSize().width, 1));
		this.setFocusTraversalKeysEnabled(false);
	}
	
	public void update()
	{
		readoutBox.setText(readout.toString());
		readoutBox.repaint();
	}
	
	public void setReadout(Integer readout)
	{
		this.readout = readout;
		update();
	}
	
	public void addResetButtonActionListener(ActionListener arg0)
	{
		resetButton.addActionListener(arg0);
	}
	
	public void addKeyListener(KeyListener l)
	{
		super.addKeyListener(l);
		titleBox.addKeyListener(l);
		readoutBox.addKeyListener(l);
		instrText.addKeyListener(l);
		resetButton.addKeyListener(l);
	}
	
	public boolean isFocusable()
	{
		return true;
	}
}
