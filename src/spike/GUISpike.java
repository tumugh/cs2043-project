package spike;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

public class GUISpike extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnOpenFile, btnPrint, btnAssignCoverage;
	private static DefaultTableModel dtm;
	private final JFileChooser fc = new JFileChooser();
	private static int numColumns;
	//private static final int COLUMN_PERIOD = 0; //will be for sorting
	private JLabel lblDate, lblCurrentFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISpike frame = new GUISpike();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUISpike() {
		setTitle("On-call Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		lblDate = new JLabel("Date: " + dateFormat.format(date));
		lblDate.setBounds(524, 47, 143, 21);
		contentPane.add(lblDate);
		
		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(this);
		btnOpenFile.setBounds(524, 104, 152, 25);
		contentPane.add(btnOpenFile);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(this);
		btnPrint.setBounds(524, 179, 152, 25);
		contentPane.add(btnPrint);
		
		JPanel panelCoverage = new JPanel();
		panelCoverage.setBounds(12, 299, 500, 249);
		contentPane.add(panelCoverage);
		panelCoverage.setLayout(null);
		
		JLabel lblCoverageCount = new JLabel("Coverage Count to Date");
		lblCoverageCount.setBounds(0, 12, 500, 15);
		lblCoverageCount.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCoverageCount.setHorizontalAlignment(SwingConstants.CENTER);
		panelCoverage.add(lblCoverageCount);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 39, 70, 15);
		panelCoverage.add(lblName);
		
		JLabel lblSpare = new JLabel("Spare Period");
		lblSpare.setBounds(80, 39, 98, 15);
		panelCoverage.add(lblSpare);
		
		JLabel lblWeek = new JLabel("This Week");
		lblWeek.setBounds(190, 39, 82, 15);
		panelCoverage.add(lblWeek);
		
		JLabel lblMonth = new JLabel("This Month");
		lblMonth.setBounds(295, 39, 90, 15);
		panelCoverage.add(lblMonth);
		
		JLabel lblTermTotal = new JLabel("Total/Term");
		lblTermTotal.setBounds(397, 39, 91, 15);
		panelCoverage.add(lblTermTotal);
		
		//Likely to delete this weird label line
		JLabel lblDivider = new JLabel("_________________________________________________________________________________");
		lblDivider.setBounds(-2, 39, 502, 15);
		panelCoverage.add(lblDivider);
		
		btnAssignCoverage = new JButton("Assign Coverage");
		btnAssignCoverage.addActionListener(this);
		btnAssignCoverage.setBounds(524, 142, 152, 25);
		contentPane.add(btnAssignCoverage);
		
		String[] columnNames = {"Period", "Class", "Room", "Absentee", "Coverage"};
		setColumns(columnNames.length);
		dtm = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(dtm);
		
		JScrollPane scrTable = new JScrollPane(table);
		scrTable.setBounds(12, 47, 500, 238);
		table.setFillsViewportHeight(true);
		//table.setAutoCreateRowSorter(true);
		contentPane.add(scrTable);
		
		JScrollPane scrCurrentFile = new JScrollPane();
		scrCurrentFile.setBounds(12, 0, 500, 50);
		contentPane.add(scrCurrentFile);
		
		lblCurrentFile = new JLabel("Current File: None selected.");
		scrCurrentFile.setViewportView(lblCurrentFile);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpenFile) {
			int returnVal = fc.showOpenDialog(GUISpike.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            lblCurrentFile.setText("Current File: " + file.getAbsolutePath());
	            //System.out.println(file.getAbsolutePath());
	        }
		}
		
		else if (e.getSource() == btnAssignCoverage) {
			System.out.println("Assign Button Test");
		}
		
		else if (e.getSource() == btnPrint) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			if (pj.printDialog()) {
		        try {pj.print();}
		        catch (PrinterException exc) {
		            System.out.println(exc);
		         }
		     } 
		}
	}
	
	/**
	 * These overloaded newRow methods rely on information being in the correct column format already.
	 */
	public static void newRow(DefaultTableModel model) {
		String[] data = new String[numColumns];
		model.addRow(data);
	}
	
	public static void newRow(DefaultTableModel model, String[] data) {
		model.addRow(data);
	}
	
	public static void newRow(DefaultTableModel model, ArrayList<String[]> list) {
		for(int i=0; i<list.size(); i++) {
			model.addRow(list.get(i));
		}
	}
	
	//Requires a class to implement Stringable
	public static void newRow(DefaultTableModel model, Stringable list) {
		ArrayList<String[]> data = list.stringConvert();
		newRow(model, data);
	}
	
	private static void setColumns(int amount) {
		numColumns = amount;
	}
	
	public String getDate() {
		return lblDate.getText();
	}
	
	/**
	 * Ideally, this will prepare a page containing the necessary info from the JTable. TODO
	 */
	private static void printOut() {
		
	}
	//TODO
	private static String lorem() {
		String result = "";
		for (int i=0; i<10; i++) {
			result+= "Lorem ipsum dolor sit amet," + "consectetur adipiscing elit, "
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
					+ "Ut enim ad minim veniam, "
					+ "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
					+ "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
					+ "Excepteur sint occaecat cupidatat non proident, "
					+ "sunt in culpa qui officia deserunt mollit anim id est laborum.";
		}
		return result;
	}
}
