package cs2043.driver;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;
import cs2043.util.WorkbookUtils;

public class ImportAbsencesDriver {

	public static void main(String[] args) {
		
		try {
			FileInputStream fis = new FileInputStream(new File(WorkbookUtils.WORKBOOK_PATH));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			AbsenceRecord record = new AbsenceRecord();
			TeacherRoster roster = new TeacherRoster();
			
			ArrayList<Teacher> fullTeachers = getFullTeachers(wb);
//			ArrayList<Teacher> supplyTeachers= getSupplyTeachers(wb);
			roster.setFullTeacher(fullTeachers);
//			roster.setSupplyTeacher(supplyTeachers);
			
//			for (Teacher t : fullTeachers) {
//				System.out.print(t.toString());
//				System.out.println(t.printSkills());
//				System.out.println(t.printSchedule());
//			}
		    
//		    for(int j = 0; j < WorkbookUtils.WEEKS_PER_TERM; j++) {
//
//			    XSSFSheet sheet = wb.getSheetAt(j);
//				    
//				int row = WorkbookUtils.TEACHER_ROW_START;
//
//				int id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
//				String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
//				
//				while (initials != null && initials != "") {
//					System.out.println("ID: " + id);
//					Teacher teacher  = new Teacher(id, initials);
////					Teacher teacher = roster.getFullTeacherById(id);
//					
//					for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
//						// If VP indicates teacher is absent on workbook
//						if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
//							// Now we can do something like this so we aren't making duplicate teachers
//							// TODO Teacher t = TeacherList.getTeacherFromID(id);
//			    			Absence absence = new Absence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
//			    			record.addAbsences(absence);
//						}
//						// TODO Look for supplies already assigned to cover an absence
//					}
//					
//					row++;
//					id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
//	    			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
//				}
//			
//		    }
		    
//		    System.out.println(record.toString());
    		wb.close();
		} catch (Exception e) {
			System.out.println("Busted");
			System.out.println(e.getMessage());
		}
	}
	
	// TODO move all this abstract constants
	public static ArrayList<Teacher> getSupplyTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> supply = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(21);

	    int row = 1;
	    int id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (initials != null && initials != "") {
			Teacher teacher  = new Teacher(id, initials);
			supply.add(teacher);
			row++;
			id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
			initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		}
		
		return supply;
	}
	
	// If we don't have extra IDs (first column) then all the retrievals of teachers fail
	// Figure this out later (use Integer object)
	
	public static ArrayList<Teacher> getFullTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> full = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(20);

	    int row = 1;
	    if (WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL) != null) {
	    	Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
	    }
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (initials != null && initials != "" && id != null ) {
			Teacher teacher  = new Teacher(id, initials);
			int period = 0;
			//abstract this somewhere
			String skill;
			for (int i = WorkbookUtils.SCHEDULE_PERIOD1_COL; i <= WorkbookUtils.SCHEDULE_PERIOD4_COL; i++) {
				skill = WorkbookUtils.getCellValueAsString(sheet, row, i);
				teacher.setSchedule(period, skill);
				if (!skill.equals("LUNCH") && !skill.equals("FREE") && !teacher.containsSkill(skill)) {
					teacher.addSkill(skill);
				}
				period++;
			}
			full.add(teacher);
			row++;
			if (WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL) != null) {
				id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
			}
		    initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		}
		
		return full;
	}
	
}
