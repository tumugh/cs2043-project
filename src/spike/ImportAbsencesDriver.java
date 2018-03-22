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
			
		    
		    for(int j = 1; j < WorkbookUtils.WEEKS_PER_TERM; j++) {
		    	
		    	int 	week = 0;
		    XSSFSheet sheet = wb.getSheetAt(week);
			    
			ArrayList<Absence> absences = new ArrayList<Absence>();
			    
			int i = WorkbookUtils.TEACHER_ROW_START;
			int id = WorkbookUtils.getIntValue(sheet, i, WorkbookUtils.TEACHER_ID_COL);
			String initials = WorkbookUtils.getStringValue(sheet, i, WorkbookUtils.TEACHER_INITIALS_COL);
			
			int col = WorkbookUtils.START_COL;
			
		    	
		    // While there are teachers in the teachers column
		    		while (initials != null && initials != "") {
		    			//Create Teacher
		    			Teacher teacher = new Teacher(id, initials);
		    			//Create Absence
		    			Absence absence = new Absence(teacher,WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
		    			absences.add(absence);
		    			i++;
		    			id = WorkbookUtils.getIntValue(sheet, i, WorkbookUtils.TEACHER_ID_COL);
		    			initials = WorkbookUtils.getStringValue(sheet, i, WorkbookUtils.TEACHER_INITIALS_COL);
		    		}
		    
		    		for (Absence a : absences) {
		    			System.out.println(a.toString());
		    		}
		    		wb.close();
		    		week++;
		    }
		    
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*private static void addAbsences(XSSFSheet sheet, Teacher teacher, int row) {
		for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
			//If VP indicates teacher is absent on workbook
			if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
				teacher.addAbsence(new Absence(WorkbookUtils.getPeriod(sheet, col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName()));
			}
		}
	}*/
}
