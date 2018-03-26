package cs2043.driver;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.util.WorkbookUtils;

public class ImportAbsencesDriver {

	public static void main(String[] args) {
		
		try {
			FileInputStream fis = new FileInputStream(new File(WorkbookUtils.WORKBOOK_PATH));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			AbsenceRecord record = new AbsenceRecord();
		    
		    for(int j = 0; j < WorkbookUtils.WEEKS_PER_TERM; j++) {

			    XSSFSheet sheet = wb.getSheetAt(j);
				    
				int row = WorkbookUtils.TEACHER_ROW_START;
				int id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
				String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
				
				while (initials != null && initials != "") {
					Teacher teacher  = new Teacher(id, initials);
					
					for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
						// If VP indicates teacher is absent on workbook
						if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
//							Teacher t = TeacherList.getTeacherFromID(id);
			    			Absence absence = new Absence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
			    			record.addAbsences(absence);
						}
					}
					
					row++;
					id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
	    			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
				}
			
		    }
		    
		    System.out.println(record.toString());
    
    		wb.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
