package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class HistoryTablePane extends javax.swing.JPanel {
	private boolean isEditable = false;
	private JScrollPane scrollPane;
	private JTable table;
	private ArrayList<String> data;
	private String colTitle;
	
	public HistoryTablePane(ArrayList<String> data, String colTitle)
	{
		super(new GridLayout(1,0));
		this.data = data;
		this.colTitle = colTitle;
		
		table = new JTable()
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : ((row+1)%10 == 0 ? Color.LIGHT_GRAY.darker() : Color.LIGHT_GRAY));

				return c;
			}
		};
		table.setFillsViewportHeight(true);
		table.setModel(new HistTableModel());
		/*table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

	        @Override
	        public Component getTableCellRendererComponent(JTable table, 
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, 
	                value, isSelected, hasFocus, row, column);
	            c.setBackground(row%2==0 && !isSelected && !hasFocus ? ((row-1)%10==0? Color.WHITE) : Color.LIGHT_GRAY);                        
	            return c;
	        };
	    });*/
		
		
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		
		
		
	}
	
	public void setEditable(boolean b)
	{
		isEditable = b;
	}
	
	public void update()
	{
		AbstractTableModel model = (AbstractTableModel) table.getModel();
		model.fireTableDataChanged();
		table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
	}
	
	public void clear()
	{
		data.clear();
		AbstractTableModel model = (AbstractTableModel) table.getModel();
		model.fireTableStructureChanged();
	}
	
	public void selectAll()
	{
		table.selectAll();
	}
	
	private class HistTableModel extends AbstractTableModel {

	    public String getColumnName(int col) 
			{return colTitle;}
		public int getColumnCount() 
			{return 1;}
		public int getRowCount() 
			{return data.size();}
		public Object getValueAt(int row, int col) 
			{return data.get(row);}
	    public Class getColumnClass(int c) 
	    	{return String.class;}
	    public boolean isCellEditable(int row, int col)
	    	{return isEditable;}
	    public void setValueAt(Object value, int row, int col) 
	    {
	    	String strValue = (String)value;
	        if (value == null || strValue.length() == 0)
	        {
	        	System.out.println("row deleted");
	        	data.remove(row);
	        	fireTableRowsDeleted(row, row);
	        }
	        else
	        {
	        	data.set(row, (String)value);
	        	fireTableCellUpdated(row, col);
	        }
	    }
	}
	
	public void addKeyListener(KeyListener l)
	{
		super.addKeyListener(l);
		scrollPane.addKeyListener(l);
		table.addKeyListener(l);
	}
}


