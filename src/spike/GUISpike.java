package spike;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class GUISpike extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JButton btnOpenFile, btnPrint, btnAssignCoverage;
	final JFileChooser fc = new JFileChooser();

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
		
		JPanel panelTeacherList = new JPanel();
		panelTeacherList.setBounds(12, 0, 500, 250);
		contentPane.add(panelTeacherList);
		panelTeacherList.setLayout(null);
		
		JLabel lblPeriod = new JLabel("Period");
		lblPeriod.setBounds(0, 63, 70, 15);
		lblPeriod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		panelTeacherList.add(lblPeriod);
		
		JLabel lblClass = new JLabel("Class");
		lblClass.setBounds(105, 63, 70, 15);
		panelTeacherList.add(lblClass);
		
		JLabel lblRoom = new JLabel("Room");
		lblRoom.setBounds(209, 63, 70, 15);
		panelTeacherList.add(lblRoom);
		
		JLabel lblAbsentee = new JLabel("Absentee");
		lblAbsentee.setBounds(306, 63, 70, 15);
		panelTeacherList.add(lblAbsentee);
		
		JLabel lblAssignmentList = new JLabel("List of assigned teachers");
		lblAssignmentList.setBounds(41, 12, 400, 33);
		panelTeacherList.add(lblAssignmentList);
		lblAssignmentList.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAssignmentList.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblSubstitute = new JLabel("Substitute");
		lblSubstitute.setBounds(403, 63, 97, 15);
		panelTeacherList.add(lblSubstitute);
		
		// It is very likely that this table will be axed, and something else put in its place.
		table = new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2", "Column3", "Column4", "Column5"}, 20));
		table.setBounds(0, 77, 500, 200);
		panelTeacherList.add(table);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		JTextPane txtpnDate = new JTextPane();
		txtpnDate.setText("Date: " + dateFormat.format(date));
		txtpnDate.setBounds(524, 47, 143, 21);
		contentPane.add(txtpnDate);
		
		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(this);
		btnOpenFile.setBounds(524, 104, 152, 25);
		contentPane.add(btnOpenFile);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(this);
		btnPrint.setBounds(524, 179, 152, 25);
		contentPane.add(btnPrint);
		
		JPanel panelCoverage = new JPanel();
		panelCoverage.setBounds(12, 262, 500, 286);
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
		
		JLabel lblDivider = new JLabel("_________________________________________________________________________________");
		lblDivider.setBounds(-2, 39, 502, 15);
		panelCoverage.add(lblDivider);
		
		btnAssignCoverage = new JButton("Assign Coverage");
		btnAssignCoverage.addActionListener(this);
		btnAssignCoverage.setBounds(524, 142, 152, 25);
		contentPane.add(btnAssignCoverage);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpenFile) {
			int returnVal = fc.showOpenDialog(GUISpike.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            System.out.println(file.getAbsolutePath());
	            // Temporarily just prints to console; will collaborate to integrate with WorkbookUtils, possibly.
	        }
		}
		
		else if (e.getSource() == btnAssignCoverage) {
			System.out.println("Assign Button Test");
			// will call stuff to assign substitutes
		}
		
		else if (e.getSource() == btnPrint) {
			System.out.println("Print Button Test");
			// will send output of assigned substitutes to printer
		}
	}
}
