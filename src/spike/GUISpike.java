package spike;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUISpike extends JFrame {

	private JPanel contentPane;
	private JTable table;

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
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 500, 250);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Period");
		lblNewLabel_1.setBounds(0, 63, 70, 15);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Class");
		lblNewLabel_2.setBounds(105, 63, 70, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Room");
		lblNewLabel_3.setBounds(209, 63, 70, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Absentee");
		lblNewLabel_4.setBounds(306, 63, 70, 15);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("List of assigned teachers");
		lblNewLabel.setBounds(41, 12, 400, 33);
		panel.add(lblNewLabel);
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblSubstitute = new JLabel("Substitute");
		lblSubstitute.setBounds(403, 63, 97, 15);
		panel.add(lblSubstitute);
		
		table = new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2", "Column3", "Column4", "Column5"}, 20));
		table.setBounds(0, 77, 500, 200);
		panel.add(table);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		JTextPane txtpnDate = new JTextPane();
		txtpnDate.setText("Date: " + dateFormat.format(date));
		txtpnDate.setBounds(524, 47, 143, 21);
		contentPane.add(txtpnDate);
		
		JButton btnNewButton = new JButton("Initialize");
		btnNewButton.setBounds(524, 104, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Print");
		btnNewButton_1.setBounds(524, 175, 117, 25);
		contentPane.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 262, 500, 286);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblCoverageCountTo = new JLabel("Coverage Count to Date");
		lblCoverageCountTo.setBounds(0, 12, 500, 15);
		lblCoverageCountTo.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCoverageCountTo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblCoverageCountTo);
		
		JLabel lblNewLabel_5 = new JLabel("Name");
		lblNewLabel_5.setBounds(10, 39, 70, 15);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Spare Period");
		lblNewLabel_6.setBounds(80, 39, 98, 15);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("This Week");
		lblNewLabel_7.setBounds(190, 39, 82, 15);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("This Month");
		lblNewLabel_8.setBounds(295, 39, 90, 15);
		panel_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Total/Term");
		lblNewLabel_9.setBounds(397, 39, 91, 15);
		panel_1.add(lblNewLabel_9);
		
		JLabel label = new JLabel("_________________________________________________________________________________");
		label.setBounds(-2, 39, 502, 15);
		panel_1.add(label);
	}
}
