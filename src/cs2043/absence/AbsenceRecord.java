package cs2043.absence;

import java.util.ArrayList;

import cs2043.teacher.Teacher;

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
	
	public ArrayList<Absence> getCoveredAbsencesByWeek(int weekNum) {
		ArrayList<Absence> covered = new ArrayList<Absence>();
		for (Absence a : absences) {
			if (a.getWeekNum() == weekNum && a.getCoverage() != null) {
				covered.add(a);
			}
		}
		
		return covered;
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
	
	public ArrayList<Absence> getCoveredAbsencesByDate(int week, int period, String day) {
		ArrayList<Absence> covered = new ArrayList<Absence>();
		for (Absence a : absences) {
			if (a.getWeekNum() == week && a.periodStrToInt() == period && a.getDay().equals(day) && a.getCoverage() != null) {
				covered.add(a);
			}
		}
		
		return covered;
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

	public boolean isTeacherCovering(Teacher t, int weekNum, int period, String day) {
		for (Absence covered : getCoveredAbsencesByDate(weekNum, period, day)) {
			if (covered.getCoverage().getId() == t.getId()) {
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}
}
