package main;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class MainMultiCounterWindow extends JPanel implements ActionListener, KeyListener{
	private Integer currentValue;
	private ArrayList<String> totalsHistory;
	private ArrayList<String> addHistory;
	
	private ControlPane controlPane;
	private HistoryTablePane totalsPane;
	private HistoryTablePane addPane;
	
	public MainMultiCounterWindow()
	{
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		currentValue = 0;
		controlPane = new ControlPane(currentValue);
		controlPane.addResetButtonActionListener(this);
		controlPane.addKeyListener(this);
		this.add(controlPane);
		
		addHistory = new ArrayList<String>();
		totalsHistory = new ArrayList<String>();
		
		addPane = new HistoryTablePane(addHistory, "Additions");
		addPane.addKeyListener(this);
		this.add(addPane);
		
		totalsPane = new HistoryTablePane(totalsHistory, "Count History");
		totalsPane.setEditable(true);
		this.add(totalsPane);
		
		this.setPreferredSize(new Dimension(300, 500));
		this.setFocusTraversalKeysEnabled(false);
		
	}
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MultiCounter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        MainMultiCounterWindow newContentPane = new MainMultiCounterWindow();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        //frame.setLocation(100, 100);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
	private void reset() //clears everything.
	{
		currentValue = 0;
		controlPane.setReadout(currentValue);
		addHistory.clear();
		addPane.clear();
		totalsHistory.clear();
		totalsPane.clear();
	}
	
	private void forget() //clears addHistory, resets currentValue; updates GUI.
	{
		currentValue = 0;
		controlPane.setReadout(currentValue);
		addHistory.clear();
		addPane.clear();
	}
	
	private void backspace() //removes last addition, removes from currentValue; updates GUI.
	{
		if (addHistory.size() <= 0)
		{
			return;
		}
		currentValue -= Integer.valueOf(addHistory.get(addHistory.size()-1));
		controlPane.setReadout(currentValue);
		addHistory.remove(addHistory.size()-1);
		addPane.update();
	}
	
	private void collect() //appends currentValue to totalsHistory, clears addHistory, resets currentValue; updates GUI.
	{
		totalsHistory.add(currentValue.toString());
		currentValue = 0;
		controlPane.setReadout(currentValue);
		addPane.clear();
		totalsPane.update();
	}
	
	private void add( int num ) //adds num to currentValue; updates GUI.
	{
		addHistory.add(String.valueOf(num));
		currentValue += num;
		controlPane.setReadout(currentValue);
		addPane.update();
	}
	
	private void copy() //copies totalsHistory to clipboard; highlights totalsHistory.
	{
		String str = "";
		for( int i = 0; i<totalsHistory.size(); i++ )
		{
			str += totalsHistory.get(i);
			str += "\n";
		}
		Clipboard myClipboard = getToolkit().getSystemClipboard(); 
		StringSelection clipData=new StringSelection(str);
		myClipboard.setContents(clipData,clipData);
		totalsPane.selectAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e);
		if (e.getActionCommand().equals("Reset"))
		{
			reset();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		//System.out.println(e);
		int code = e.getKeyCode();
		int num = -1;
		if (code == KeyEvent.VK_TAB || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
		{
			collect();
		}
		else if (code == KeyEvent.VK_R)
		{
			reset();
		}
		else if (code == KeyEvent.VK_X)
		{
			forget();
		}
		else if (code == KeyEvent.VK_BACK_SPACE)
		{
			backspace();
		}
		else if (code == KeyEvent.VK_C)
		{
			copy();
		}
		else
		{
			switch (code)
			{
				case KeyEvent.VK_1:
					num = 1;
					break;
				case KeyEvent.VK_2:
					num = 2;
					break;
				case KeyEvent.VK_3:
					num = 3;
					break;
				case KeyEvent.VK_4:
					num = 4;
					break;
				case KeyEvent.VK_5:
					num = 5;
					break;
				case KeyEvent.VK_6:
					num = 6;
					break;
				case KeyEvent.VK_7:
					num = 7;
					break;
				case KeyEvent.VK_8:
					num = 8;
					break;
				case KeyEvent.VK_9:
					num = 9;
					break;
				case KeyEvent.VK_J:
					num = 1;
					break;
				case KeyEvent.VK_K:
					num = 2;
					break;
				case KeyEvent.VK_L:
					num = 3;
					break;
				case KeyEvent.VK_SEMICOLON:
					num = 4;
					break;
				/*case KeyEvent.VK_QUOTE:
					num = 5;
					break;*/
				case KeyEvent.VK_F:
					num = 1;
					break;
				case KeyEvent.VK_D:
					num = 2;
					break;
				case KeyEvent.VK_S:
					num = 3;
					break;
				case KeyEvent.VK_A:
					num = 4;
					break;
				case KeyEvent.VK_NUMPAD1:
					num = 1;
					break;
				case KeyEvent.VK_NUMPAD2:
					num = 2;
					break;
				case KeyEvent.VK_NUMPAD3:
					num = 3;
					break;
				case KeyEvent.VK_NUMPAD4:
					num = 4;
					break;
				case KeyEvent.VK_NUMPAD5:
					num = 5;
					break;
				case KeyEvent.VK_NUMPAD6:
					num = 6;
					break;
				case KeyEvent.VK_NUMPAD7:
					num = 7;
					break;
				case KeyEvent.VK_NUMPAD8:
					num = 8;
					break;
				case KeyEvent.VK_NUMPAD9:
					num = 9;
					break;
				case KeyEvent.VK_NUMPAD0:
					num = 10;
					break;
			}
		}
		
		if (num > -1)
		{
			add(num);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
