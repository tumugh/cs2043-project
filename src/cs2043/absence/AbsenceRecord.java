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

	public ArrayList<Absence> getAbsencesByWeek(int weekNum) {
		ArrayList<Absence> weekAbsences = new ArrayList<Absence>();
		for (Absence a : absences) {
			if (a.getWeekNum() == weekNum) {
				weekAbsences.add(a);
			}
		}
		
		return weekAbsences;
	}
	
	public ArrayList<Absence> getUncoveredAbsencesByWeek(int weekNum) {
		ArrayList<Absence> uncovered = new ArrayList<Absence>();
		for (Absence a : absences) {
			if (a.getWeekNum() == weekNum && a.getCoverage() == null) {
				uncovered.add(a);
			}
		}
		
		return uncovered;
	}
	
//	private void getAbsencesByDate() {
//	
//}
	
//	private void getAbsencesByTeacher() {
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
