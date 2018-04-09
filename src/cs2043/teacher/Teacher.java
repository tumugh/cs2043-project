package cs2043.teacher;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.util.WorkbookUtils;

public class Teacher {
	
	private int id;
	private String initials;
	private String[] schedule;
	private ArrayList<String> skills;
	
	public Teacher(int id, String initials) {
		this.id = id;
		this.initials = initials;
		this.skills = new ArrayList<String>();
		this.schedule = new String[5];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}
	
	public void setSchedulePeriod(int period, String className) {
		schedule[period] = className;
	}
	
	public void setSchedule(String[] schedule) {
		this.schedule = schedule;
	}
	
	public String getClassByPeriod(int period) {
		return schedule[period];
	}
	
	public int getFreePeriod() {
		for (int i = 0; i < schedule.length; i++) {
			if (getClassByPeriod(i).equals(WorkbookUtils.FREE_PERIOD)) {
				return i;
			}
		}
		// TODO figure out what to do in this case
		return -1;
	}
	
	public ArrayList<String> getSkills() {
		return skills;
	}
	
	public void addSkill(String str) {
		skills.add(str);
	}
	
	public boolean containsSkill(String skill) {
		for (String s : skills) {
			if (s.equals(skill)) {
				return true;
			}
		}
		return false;
	}
	
	public void generateTeacherScheduleSkills(XSSFSheet sheet, int row) {
		int period = 0;
		String skill;
		for (int i = WorkbookUtils.SCHEDULE_PERIOD1_COL; i <= WorkbookUtils.SCHEDULE_PERIOD4_COL; i++) {
			skill = WorkbookUtils.getCellValueAsString(sheet, row, i);
			setSchedulePeriod(period, skill);
			if (!skill.equals("LUNCH") && !skill.equals("FREE") && !containsSkill(skill)) {
				addSkill(skill);
			}
			period++;
		}
	}
	
	public int checkTalliesByWeek(AbsenceRecord record, int week) {
		int count = 0;
		for (Absence covered : record.getCoveredAbsencesByWeek(week)) {
			// This is HORRIBLE TODO
			if (covered.getCoverage().getSkills().size() != 0 && covered.getCoverage().getId() == getId()) {
				count++;
			}
		}
		return count;
	}
	
	public int checkTalliesByMonth(AbsenceRecord record, int month) {
		// assumption that a month is 4 weeks
		int endWeek = month * 4;
		int startWeek = endWeek - 3;
		int count = 0;
		for (int week = startWeek; week <= endWeek; week++) {
			count = count + checkTalliesByWeek(record, week);
		}
		return count;
	}
	
	public int checkTalliesByTerm(AbsenceRecord record) {
		int count = 0;
		for (int month = 1; month <= 5; month++) {
			count = count + checkTalliesByMonth(record, month);
		}
		return count;
	}
	
	public void getTeacherAbsences(AbsenceRecord record, TeacherRoster roster, XSSFSheet sheet, int row) {
		for (int col = WorkbookUtils.START_COL; col <= WorkbookUtils.END_COL; col++) {
			// If VP indicates teacher is absent on workbook
			if (WorkbookUtils.getCellValueAsString(sheet, row, col).equalsIgnoreCase(WorkbookUtils.ABSENCE_INDICATOR)) {
				Absence absence = new Absence(this, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			} else if (WorkbookUtils.getCellValueAsString(sheet, row, col) != "" && WorkbookUtils.getCellValueAsString(sheet, row, col).substring(0, 1).equals("S")) {
				int supplyId = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, col).substring(1));
				Teacher cover = roster.getSupplyTeacherById(supplyId);
				Absence absence = new Absence(this, cover, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			} else if (WorkbookUtils.getCellValueAsString(sheet, row, col) != "" && !WorkbookUtils.getCellValueAsString(sheet, row, col).substring(0, 1).equals("S")) {
				int fullTimeId = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, col));
				Teacher cover = roster.getFullTeacherById(fullTimeId);
				Absence absence = new Absence(this, cover, WorkbookUtils.getPeriod(sheet,col), WorkbookUtils.getDay(sheet, col), sheet.getSheetName());
				record.addAbsence(absence);
			}
		}
	}
	
	public String printSchedule() {
		String out = "";
		int i = 1;
		for (String s : schedule) {
			out = out + "Period " + i + " - " + s + " ";
			i++;
		}
		return out;
	}
	
	public String printSkills() {
		String str = "";
		for (String skill : skills) {
			str = str + skill + " ";
		}
		return str;
	}
	
	public String toString() {
		return "ID: " + id + " Initials: " + initials;
	}

}
