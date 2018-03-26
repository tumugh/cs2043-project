package cs2043.absence;

import java.util.ArrayList;

public class AbsenceRecord {

	ArrayList<Absence> absences;
	
	public AbsenceRecord() {
		absences = new ArrayList<Absence>();
	}
	
	public void addAbsences(Absence a) {
		absences.add(a);
	}
	
//	private void getAbsencesByTeacher() {
//		ArrayList<ArrayList<Absence>> teacherAbsenceRecord = new ArrayList<>
//		
//	}
	
	public String toString(){
		String out = "";
		for (Absence a : absences) {
			out += a.toString() + '\n';
		}
		return out;
	}
}
