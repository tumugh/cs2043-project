package cs2043.driver;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cs2043.absence.Absence;
import cs2043.teacher.Teacher;
import cs2043.util.WorkbookUtils;

import javax.swing.JComboBox;
import javax.swing.JDialog;

public class GUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnOpenFile, btnPrint, btnAssignCoverage;
	private static DefaultTableModel coverageModel, talliesModel;
	private final JFileChooser fc = new JFileChooser();
	private JLabel lblDate, lblCurrentFile;
	private JTable tblAssignments;
	private JTable tblCoverageStats;
	private JComboBox cmbDay, cmbWeek;
	private Assigner assigner;
	private File file;

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
		btnPrint.setBounds(524, 270, 152, 25);
		contentPane.add(btnPrint);
		
		btnAssignCoverage = new JButton("Assign Coverage");
		btnAssignCoverage.addActionListener(this);
		btnAssignCoverage.setBounds(524, 142, 152, 25);
		contentPane.add(btnAssignCoverage);
		
		String[] columnNames = {"Period", "Day", "Class", "Absentee", "Coverage"};
		coverageModel = new DefaultTableModel(columnNames, 0);
		tblAssignments = new JTable(coverageModel) {
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
		
		String[] columnNames2 = {"Initials", "This Week", "This Month", "Total/Term"};
		talliesModel = new DefaultTableModel(columnNames2, 0);
		tblCoverageStats = new JTable(talliesModel) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		tblCoverageStats.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrCoverageStats = new JScrollPane(tblCoverageStats);
		scrCoverageStats.setBounds(12, 316, 500, 232);
		tblCoverageStats.setFillsViewportHeight(true);
		contentPane.add(scrCoverageStats);
		
		cmbWeek = new JComboBox();
		cmbWeek.setBounds(524, 188, 152, 25);
		contentPane.add(cmbWeek);
		cmbWeek.setEditable(false);
		for (int i=1; i<=20; i++) {
			cmbWeek.addItem("" + i);
		}
		
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "All"};
		cmbDay = new JComboBox(days);
		cmbDay.setBounds(524, 234, 152, 24);
		contentPane.add(cmbDay);
		cmbDay.setEditable(false);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(524, 170, 70, 15);
		contentPane.add(lblWeek);
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setBounds(524, 214, 70, 15);
		contentPane.add(lblDay);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpenFile) {
			int returnVal = fc.showOpenDialog(GUI.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = fc.getSelectedFile();
	            lblCurrentFile.setText("Current File: " + file.getAbsolutePath());
	            try {
					assigner = Driver.importAbsences(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
		} else if (e.getSource() == btnAssignCoverage) {
			coverageModel.setRowCount(0);
			String currentDay;
			int currentWeek;
			currentDay = cmbDay.getSelectedItem().toString();
			currentWeek = Integer.parseInt((String)cmbWeek.getSelectedItem());
			assigner.assignCoveragesForWeek(currentWeek);

			try {
				// go back into spreadsheet and write coverage ID
				assigner.writeAssignmentsToWorkbook(file, currentWeek - 1);
			} catch (IOException e1) {			
				e1.printStackTrace();
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(null, "There are some absences we cannot cover with available teachers, please correct file", "UNCOVERED", JOptionPane.WARNING_MESSAGE);
			}
			
			displayCoverages(currentDay, currentWeek);
			displayTallies(currentWeek);
		} else if (e.getSource() == btnPrint) {
			try {
				tblAssignments.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void displayCoverages(String currentDay, int week) {
		if(currentDay.equals("All")) {
			ArrayList<Absence> covered = assigner.getRecord().getAbsencesByWeek(week);
			for (Absence a : covered) {
				newRow(coverageModel, a.convertForDisplay());
			}
		}  else {
			for(int i=0; i<5; i++) {
				ArrayList<Absence> covered = assigner.getRecord().getCoveredAbsencesByDate(week, i, currentDay);
				for (Absence a : covered) {
					newRow(coverageModel, a.convertForDisplay());
				}
			}
		}
	}
	
	public void displayTallies(int week) {
		for (Teacher t : assigner.getRoster().getFullTeachers()) {
			String[] res = {t.getInitials(),
					t.checkTalliesByWeek(assigner.getRecord(), week) + "",
					t.checkTalliesByMonth(assigner.getRecord(), WorkbookUtils.convertWeekToMonth(week)) + "", 
					t.checkTalliesByTerm(assigner.getRecord()) + ""};
			newRow(talliesModel, res);
		}
	}
	
	public static void newRow(DefaultTableModel model, String[] data) {
		model.addRow(data);
	}
	
	public static void newRow(DefaultTableModel model, ArrayList<String[]> list) {
		for(int i=0; i<list.size(); i++) {
			model.addRow(list.get(i));
		}
	}
	
	public String getDate() {
		return lblDate.getText();
	}
}
