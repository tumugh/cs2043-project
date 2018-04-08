package spike;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cs2043.absence.Absence;
import cs2043.driver.ImportAbsencesDriver;
import cs2043.driver.School;

public class GUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnOpenFile, btnPrint, btnAssignCoverage;
	private static DefaultTableModel dtm;
	private final JFileChooser fc = new JFileChooser();
	private static int numColumns;
	//private static final int COLUMN_PERIOD = 0; //will be for sorting
	private JLabel lblDate, lblCurrentFile;
	private School school = null;
	private JTable tblAssignments;
	private JTable tblCoverageStats;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setTitle("On-call Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		this.setResizable(false);
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
		
		btnAssignCoverage = new JButton("Assign Coverage");
		btnAssignCoverage.addActionListener(this);
		btnAssignCoverage.setBounds(524, 142, 152, 25);
		contentPane.add(btnAssignCoverage);
		
		//TODO make this dynamic
		String[] columnNames = {"Period", "Day", "Class", "Absentee", "Coverage"};
		setColumns(columnNames.length);
		dtm = new DefaultTableModel(columnNames, 0);
		tblAssignments = new JTable(dtm) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		tblAssignments.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrTableAssignments = new JScrollPane(tblAssignments);
		scrTableAssignments.setBounds(12, 47, 500, 238);
		tblAssignments.setFillsViewportHeight(true);
		tblAssignments.setAutoCreateRowSorter(true);
		contentPane.add(scrTableAssignments);
		
		JScrollPane scrCurrentFile = new JScrollPane();
		scrCurrentFile.setBounds(12, 0, 500, 50);
		contentPane.add(scrCurrentFile);
		
		lblCurrentFile = new JLabel("Current File: None selected.");
		scrCurrentFile.setViewportView(lblCurrentFile);
		
		JLabel lblCoverageCount = new JLabel("Coverage Count to Date");
		lblCoverageCount.setBounds(12, 297, 500, 15);
		contentPane.add(lblCoverageCount);
		lblCoverageCount.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCoverageCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(524, 299, 70, 15);
		contentPane.add(lblName);
		
		JLabel lblSpare = new JLabel("Spare Period");
		lblSpare.setBounds(524, 316, 98, 15);
		contentPane.add(lblSpare);
		
		JLabel lblWeek = new JLabel("This Week");
		lblWeek.setBounds(534, 343, 82, 15);
		contentPane.add(lblWeek);
		
		JLabel lblMonth = new JLabel("This Month");
		lblMonth.setBounds(522, 368, 90, 15);
		contentPane.add(lblMonth);
		
		JLabel lblTermTotal = new JLabel("Total/Term");
		lblTermTotal.setBounds(531, 392, 91, 15);
		contentPane.add(lblTermTotal);
		
		JScrollPane scrCoverageStats = new JScrollPane();
		scrCoverageStats.setBounds(12, 316, 500, 232);
		contentPane.add(scrCoverageStats);
		
		tblCoverageStats = new JTable();
		tblCoverageStats.setFillsViewportHeight(true);
		scrCoverageStats.setViewportView(tblCoverageStats);
		//TODO finish table settings & columns
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// bad practice to set to null TODO
		if (e.getSource() == btnOpenFile) {
			int returnVal = fc.showOpenDialog(GUI.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            lblCurrentFile.setText("Current File: " + file.getAbsolutePath());
	            try {
					school = ImportAbsencesDriver.importAbsences(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
		} else if (e.getSource() == btnAssignCoverage) {
			//TODO add school null check
//			for (Absence a : school.getRecord().getAbsencesByWeek(2)) {
//		    	System.out.println(a);
//		    }
			school.assignmentCoveragesForWeek(2);
			ArrayList<Absence> covered = school.getRecord().getAbsencesByWeek(2);
//			for (Absence a : school.getRecord().getAbsencesByWeek(2)) {
//		    	System.out.println(a);
//		    }
			for (Absence a : covered) {
				newRow(dtm, a.stringConvert());
			}
		} else if (e.getSource() == btnPrint) {
			try {
				tblAssignments.print();
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			PrinterJob pj = PrinterJob.getPrinterJob();
//			if (pj.printDialog()) {
//		        try {pj.print();}
//		        catch (PrinterException exc) {
//		            System.out.println(exc);
//		         }
//		     } 
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
//	public static void newRow(DefaultTableModel model, Stringable list) {
//		ArrayList<String[]> data = list.stringConvert();
//		newRow(model, data);
//	}
	
	private static void setColumns(int amount) {
		numColumns = amount;
	}
	
	public String getDate() {
		return lblDate.getText();
	}
}
