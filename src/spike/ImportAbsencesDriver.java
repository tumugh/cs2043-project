package spike;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportAbsencesDriver {

	public static void main(String[] args) {
		
		try {
			FileInputStream fis = new FileInputStream(new File(WorkbookUtils.WORKBOOK_PATH));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
		    // Testing adding absences to all teachers on first sheet/Week 1
			XSSFSheet sheet = wb.getSheetAt(0);
		    
		    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		    int i = WorkbookUtils.TEACHER_ROW_START;
		    int id = WorkbookUtils.getIntValue(sheet, i, WorkbookUtils.TEACHER_ID_COL);
		    String initials = WorkbookUtils.getStringValue(sheet, i, WorkbookUtils.TEACHER_INITIALS_COL);
		    // While there are teachers in the teachers column
		    while (initials != null && initials != "") {
		    	Teacher teacher = new Teacher(id, initials);
		    	addAbsences(sheet, teacher, i);
		    	teachers.add(teacher);
		    	
		    	i++;
		    	id = WorkbookUtils.getIntValue(sheet, i, WorkbookUtils.TEACHER_ID_COL);
		    	initials = WorkbookUtils.getStringValue(sheet, i, WorkbookUtils.TEACHER_INITIALS_COL);
		    }
		    
		    for (Teacher t : teachers) {
		    	System.out.println(t.toString());
		    }
		    wb.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void addAbsences(XSSFSheet sheet, Teacher teacher, int row) {
		for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
			//If VP indicates teacher is absent on workbook
			if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
				teacher.addAbsence(new Absence(WorkbookUtils.getPeriod(sheet, col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName()));
			}
		}
	}
}
