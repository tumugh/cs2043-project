package cs2043.absence;

import cs2043.teacher.Teacher;

public class Absence {
	
	private String period;
	private String day;
	private String week;
	private Teacher teacher;
	private Teacher coverage;
	
	public Absence(Teacher teacher, String period, String day, String week) {
		super();
		this.teacher = teacher;
		this.coverage = null;
		this.period = period;
		this.day = day;
		this.week = week;
	}
	
	public Absence(Teacher teacher, Teacher coverage, String period, String day, String week) {
		super();
		this.teacher = teacher;
		this.coverage = coverage;
		this.period = period;
		this.day = day;
		this.week = week;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	public int getWeekNum() {
		return Integer.parseInt(week.substring(5));
	}
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Teacher getCoverage() {
		return coverage;
	}

	public void setCoverage(Teacher coverage) {
		this.coverage = coverage;
	}
	
	public int periodStrToInt() {
		if (period.equals("1")) {
			return 0;
		} else if (period.equals("2")) {
			return 1;
		} else if (period.equals("3A")) {
			return 2;
		} else if (period.equals("3B")) {
			return 3;
		} else if (period.equals("4")) {
			return 4;
		} else {
			// TODO
			return -1;
		}
	}
		
	public String toString() {
		String out = "ID: " + teacher.getId() + " Initials: " +  teacher.getInitials()
		 		   + "\n\t" + getWeekNum() + " " + getDay() + " Period " + getPeriod();
		if (coverage != null) {
			out = out + "\n\tCovered by: " + coverage.getId() + " - " + coverage.getInitials();
		} else {
			out = out + "\n\tNot covered!";
		}
		return out;
	}

	public String[] convertForDisplay() {
		String[] res = {getPeriod(), getDay(), getTeacher().getClassByPeriod(periodStrToInt()), getTeacher().getInitials(), getCoverage().getInitials()};
		return res;
	}
}
