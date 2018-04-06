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

	public static void main(String[] args) throws Exception {
		
			FileInputStream fis = new FileInputStream(new File(WorkbookUtils.WORKBOOK_PATH));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			AbsenceRecord record = new AbsenceRecord();
			TeacherRoster roster = new TeacherRoster();
			
			ArrayList<Teacher> fullTeachers = getFullTeachers(wb);
			ArrayList<Teacher> supplyTeachers= getSupplyTeachers(wb);
			roster.setFullTeacher(fullTeachers);
			roster.setSupplyTeacher(supplyTeachers);
//			
//			for (Teacher t : fullTeachers) {
//				System.out.println(t.toString());
//				System.out.println(t.printSkills());
//				System.out.println(t.printSchedule());
//			}
		    
		    for(int j = 0; j < WorkbookUtils.WEEKS_PER_TERM; j++) {

			    XSSFSheet sheet = wb.getSheetAt(j);
				    
				int row = WorkbookUtils.TEACHER_ROW_START;

				Integer id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
				String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
				
				// TODO remove Ids from excel spreadsheet for weeks
				while (initials != null && initials != "") {
					// TODO determine what to do when we can't find a teacher by ID - want to tell user I believe
					Teacher teacher = roster.getFullTeacherById(id);
					
					for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
						// If VP indicates teacher is absent on workbook
						if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
			    			Absence absence = new Absence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
			    			record.addAbsences(absence);
						} else if (WorkbookUtils.getCellValueAsString(sheet, row, col) != "" && WorkbookUtils.getCellValueAsString(sheet, row, col).substring(0, 1).equals("S")) {
							int supplyId = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, col).substring(1));
							Teacher cover = roster.getSupplyTeacherById(supplyId);
			    			Absence absence = new Absence(teacher, cover, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
			    			record.addAbsences(absence);
						}
					}
					
					row++;
					id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
	    			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
				}
			
		    }
		    
//		    System.out.println(record.toString());
		    testCoverages(record, roster);
    		wb.close();
	}
	
	// TODO move all this & abstract constants
	public static ArrayList<Teacher> getSupplyTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> supply = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(21);

	    int row = 1;
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (id != null) {
			Teacher teacher  = new Teacher(id, initials);
			supply.add(teacher);
			row++;
			id = null;
			if (!WorkbookUtils.isEmptyRow(sheet, row)) {
				id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
				initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		}
		
		return supply;
	}
	
	public static ArrayList<Teacher> getFullTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> full = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(20);

	    int row = 1;
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (id != null) {
			Teacher teacher  = new Teacher(id, initials);
			generateTeacherScheduleSkills(sheet, row, teacher);
			full.add(teacher);
			row++;
			id = null;
			if (!WorkbookUtils.isEmptyRow(sheet, row)) {
				id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
				initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		}
		
		return full;
	}
	
	public static void generateTeacherScheduleSkills(XSSFSheet sheet, int row, Teacher teacher) {
		int period = 0;
		String skill;
		for (int i = WorkbookUtils.SCHEDULE_PERIOD1_COL; i <= WorkbookUtils.SCHEDULE_PERIOD4_COL; i++) {
			skill = WorkbookUtils.getCellValueAsString(sheet, row, i);
			teacher.setSchedule(period, skill);
			if (!skill.equals("LUNCH") && !skill.equals("FREE") && !teacher.containsSkill(skill)) {
				teacher.addSkill(skill);
			}
			period++;
		}
	}
	
	public static void testCoverages(AbsenceRecord record, TeacherRoster roster) {
//		ArrayList<Absence> w1absences = record.getAbsencesByWeek(1);
		ArrayList<Absence> w1absences = record.getUncoveredAbsencesByWeek(1);
		for (Absence a : w1absences) {
			System.out.println(a.toString());
		}
	}
	
	// TODO assignment alg
	// get all uncovered absences
	// for each absence get week, get day, get period
	// find full time teachers where their free period is that period
	// pick one
	// MAKE SURE THEY AREN'T ALREADY COVERING SOMETHING THAT DAY
		// go through all absences of that week, day, period - make sure they aren't already as a coverage - inefficient
	// make them the coverage
	
}
