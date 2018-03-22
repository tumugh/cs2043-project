package src.spike;

public class Absence {
	
	private String period;
	private String day;
	private String week;
	Teacher teacher;
	Teacher coverage;
	
	public Absence(Teacher teacher, String period, String day, String week) {
		super();
		this.teacher = teacher;
		this.period = period;
		this.day = day;
		this.week = week;
		this.coverage = null;
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
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Teacher getTeacher(Teacher teacher) {
		return teacher;
	}
	
	public String toString() {
		String out = "ID: " + teacher.getId() + " Initials: " +  teacher.getInitials()
		 		   + "\n\t" + getWeek() + " " + getDay() + " Period " + getPeriod();
		return out;
	}
	
}
