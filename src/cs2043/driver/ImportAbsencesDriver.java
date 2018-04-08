package cs2043.driver;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;
import cs2043.util.WorkbookUtils;
import spike.GUI;

public class ImportAbsencesDriver {

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
	
	// Keep this here
	public static School importAbsences(File file) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		ArrayList<Teacher> fullTeachers = getFullTeachers(wb);
		ArrayList<Teacher> supplyTeachers= getSupplyTeachers(wb);
		TeacherRoster roster = new TeacherRoster(fullTeachers, supplyTeachers);
		AbsenceRecord record = new AbsenceRecord();
	    
	    for(int week = 0; week < WorkbookUtils.WEEKS_PER_TERM; week++) {
		    XSSFSheet sheet = wb.getSheetAt(week);
			int row = WorkbookUtils.TEACHER_ROW_START;
			Integer id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
			String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			
			while (WorkbookUtils.isNotBlank(initials)) {
				// TODO determine what to do when we can't find a teacher by ID - want to tell user I believe
				Teacher teacher = roster.getFullTeacherById(id);
				getTeacherAbsences(record, roster, sheet, row, teacher);
				row++;
				id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
    			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		
	    }
	    
//    	checkTalliesByWeek(record, roster, 2);
	    wb.close();
	    School school = new School(record, roster);
	    return school;
	}
	
	// keep
	public static void writeAssignmentsToWorkbook(File file, School school, int week) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
	    XSSFSheet sheet = wb.getSheetAt(week);
		
		int row = WorkbookUtils.TEACHER_ROW_START;
		Integer id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
		String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (WorkbookUtils.isNotBlank(initials)) {
			Teacher teacher = school.getRoster().getFullTeacherById(id);
			for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
				// If VP indicates teacher is absent on workbook
				if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
					Absence ab = school.getRecord().findAbsence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
					sheet.getRow(row).getCell(col).setCellValue(ab.getCoverage().getId());
				}
			}
			
			row++;
			id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
			initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		}
		
	    FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
	    fileOut.close();
	    wb.close();
	}
	
	// keep
	private static void getTeacherAbsences(AbsenceRecord record, TeacherRoster roster, XSSFSheet sheet, int row, Teacher teacher) {
		for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
			// If VP indicates teacher is absent on workbook
			if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
				Absence absence = new Absence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			} else if (WorkbookUtils.getCellValueAsString(sheet, row, col) != "" && WorkbookUtils.getCellValueAsString(sheet, row, col).substring(0, 1).equals("S")) {
				int supplyId = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, col).substring(1));
				Teacher cover = roster.getSupplyTeacherById(supplyId);
				Absence absence = new Absence(teacher, cover, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			} else if (WorkbookUtils.getCellValueAsString(sheet, row, col) != "" && !WorkbookUtils.getCellValueAsString(sheet, row, col).substring(0, 1).equals("S")) {
				int fullTimeId = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, col));
				Teacher cover = roster.getFullTeacherById(fullTimeId);
				Absence absence = new Absence(teacher, cover, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			}
		}
	}
	
	// move to supply teacher class
	public static ArrayList<Teacher> getSupplyTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> supply = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(WorkbookUtils.SUPPLY_TEACHER_SHEET);

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
	
	// move to full teacher class
	public static ArrayList<Teacher> getFullTeachers(XSSFWorkbook wb) {
		ArrayList<Teacher> full = new ArrayList<Teacher>();
	    XSSFSheet sheet = wb.getSheetAt(WorkbookUtils.FULL_TEACHER_SHEET);

	    int row = 1;
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (id != null) {
			Teacher teacher  = new Teacher(id, initials);
			teacher.generateTeacherScheduleSkills(sheet, row);
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

	
	
}
