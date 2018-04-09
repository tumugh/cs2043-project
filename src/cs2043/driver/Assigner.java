package cs2043.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;
import cs2043.util.WorkbookUtils;

public class Assigner {
	
	private AbsenceRecord record;
	private TeacherRoster roster;
	
	public Assigner(AbsenceRecord record, TeacherRoster roster) {
		this.record = record;
		this.roster = roster;
	}
	
	public AbsenceRecord getRecord() {
		return record;
	}
	
	public void setRecord(AbsenceRecord record) {
		this.record = record;
	}
	
	public TeacherRoster getRoster() {
		return roster;
	}
	
	public void setRoster(TeacherRoster roster) {
		this.roster = roster;
	}
	
	public void assignCoveragesForWeek(int week) {
		int weekNum;
		int period;
		String day;
		for (Absence uncovered : record.getUncoveredAbsencesByWeek(week)) {
			weekNum = uncovered.getWeekNum();
			period = uncovered.periodStrToInt();
			day = uncovered.getDay();
			for (Teacher t : roster.getFullTeacherWithFreePeriod(period)) {
				// if teacher isn't already covering absence
				if (isTeacherAvailable(t, weekNum, period, day)) {
					uncovered.setCoverage(t);
					break;
				}
			}
		}
	}
	
	private boolean isTeacherAvailable(Teacher t, int week, int period, String day) {
		return !record.isTeacherCovering(t, week, period, day) && t.checkTalliesByWeek(record, week) < Driver.WEEK_THRESHOLD && t.checkTalliesByMonth(record, WorkbookUtils.convertWeekToMonth(week)) < Driver.MONTH_THRESHOLD;
	}
	
	public void writeAssignmentsToWorkbook(File file, int week) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
	    XSSFSheet sheet = wb.getSheetAt(week);
		
		int row = WorkbookUtils.TEACHER_ROW_START;
		Integer id = WorkbookUtils.getIntValue(sheet, row, WorkbookUtils.TEACHER_ID_COL);
		String initials = WorkbookUtils.getStringValue(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (WorkbookUtils.isNotBlank(initials)) {
			Teacher teacher = getRoster().getFullTeacherById(id);
			for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
				// If VP indicates teacher is absent on workbook
				if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
					Absence ab = getRecord().findAbsence(teacher, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
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
}
