package cs2043.driver;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;

public class School {
	
	private AbsenceRecord record;
	private TeacherRoster roster;
	
	public School(AbsenceRecord record, TeacherRoster roster) {
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
	
	public void assignmentCoveragesForWeek(int week) {
		for (Absence uncovered : record.getUncoveredAbsencesByWeek(week)) {
			int weekNum = uncovered.getWeekNum();
			int period = uncovered.periodStrToInt();
			String day = uncovered.getDay();
			for (Teacher t : roster.getFullTeacherWithFreePeriod(period)) {
				// if teacher isn't already covering absence
				if (!record.isTeacherCovering(t, weekNum, period, day)) {
					uncovered.setCoverage(t);
					break;
				}
			}
		}
	}
}
