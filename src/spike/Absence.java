package spike;

public class Absence {
	
	private String period;
	private String day;
	private String week;
	
	public Absence(String period, String day, String week) {
		super();
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
}
