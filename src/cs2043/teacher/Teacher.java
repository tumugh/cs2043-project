package cs2043.teacher;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;

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
	
	public void setSchedule(int period, String className) {
		schedule[period] = className;
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
			setSchedule(period, skill);
			if (!skill.equals("LUNCH") && !skill.equals("FREE") && !containsSkill(skill)) {
				addSkill(skill);
			}
			period++;
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
