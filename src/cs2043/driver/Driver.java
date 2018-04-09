package cs2043.driver;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;
import cs2043.util.WorkbookUtils;

public class Driver {

	// TODO grab these from GUI
	public static final int WEEK_THRESHOLD = 4;
	public static final int MONTH_THRESHOLD = 4;

	public static void main(String[] args) throws Exception {
			
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
	
	public static Assigner importAbsences(File file) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		TeacherRoster roster = new TeacherRoster();
		roster.retrieveFullTeachers(wb);
		roster.retrieveSupplyTeachers(wb);
		AbsenceRecord record = new AbsenceRecord();
	    
	    for(int week = 0; week < WorkbookUtils.WEEKS_PER_TERM; week++) {
		    XSSFSheet sheet = wb.getSheetAt(week);
			int row = WorkbookUtils.TEACHER_ROW_START;
			Integer id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
			String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			
			while (WorkbookUtils.isNotBlank(initials)) {
				// TODO determine what to do when we can't find a teacher by ID - want to tell user I believe
				Teacher teacher = roster.getFullTeacherById(id);
				teacher.getTeacherAbsences(record, roster, sheet, row);
				row++;
				id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
    			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		
	    }
	    
	    wb.close();
	    Assigner school = new Assigner(record, roster);
	    return school;
	}
	
}
