package spike;

import java.util.ArrayList;

public class Teacher {
	
	private int id;
	private String initials;
	private ArrayList<Absence> absences;
	
	public Teacher(int id, String initials) {
		this.id = id;
		this.initials = initials;
		this.absences = new ArrayList<Absence>();
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

	public ArrayList<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(ArrayList<Absence> absences) {
		this.absences = absences;
	}
	
	public void addAbsence(Absence absence) {
		this.absences.add(absence);
	}

	@Override
	public String toString() {
		String out = "ID: " + getId() + " Initials: " +  getInitials();
		for (Absence a : absences) {
			out = out + "\n\t" + a.getWeek() + " " + a.getDay() + " Period " + a.getPeriod();
		}
		return out;
	}
	
}
