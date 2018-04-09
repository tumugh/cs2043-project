package cs2043.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cs2043.absence.Absence;
import cs2043.absence.AbsenceRecord;
import cs2043.driver.Assigner;
import cs2043.teacher.Teacher;
import cs2043.teacher.TeacherRoster;

public class AssignerTests {

	@Test
	public void testAssignment() {
		AbsenceRecord record = new AbsenceRecord();
		TeacherRoster roster = new TeacherRoster();
		Teacher absentee = new Teacher(1, "CB");
		String[] absenteeSchedule = {"MATH", "MATH", "FREE", "LUNCH", "MATH"};
		absentee.setSchedule(absenteeSchedule);
		absentee.addSkill("MATH");
		Teacher cover = new Teacher(2, "BS");
		String[] coverSchedule = {"FREE", "MATH", "MATH", "LUNCH", "MATH"};
		cover.addSkill("MATH");
		cover.setSchedule(coverSchedule);
		roster.addFullTeacher(absentee);
		roster.addFullTeacher(cover);
		
		Absence a = new Absence(absentee, "1", "Monday", "Week 1");
		record.addAbsence(a);
		Assigner assigner = new Assigner(record, roster);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a.getWeekNum()).size(), 1);

		assigner.assignCoveragesForWeek(1);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a.getWeekNum()).size(), 1);
		assertEquals(assigner.getRecord().getCoveredAbsencesByDate(a.getWeekNum(), a.periodStrToInt(), a.getDay()).size(), 1);
		Absence covered = assigner.getRecord().getCoveredAbsencesByDate(a.getWeekNum(), a.periodStrToInt(), a.getDay()).get(0);
		assertEquals(covered.getTeacher().getId(), absentee.getId());
		assertEquals(covered.getCoverage().getId(), cover.getId());
	}
	
	@Test
	public void testSupplyAssignment() {
		AbsenceRecord record = new AbsenceRecord();
		TeacherRoster roster = new TeacherRoster();
		Teacher absentee = new Teacher(1, "CB");
		String[] absenteeSchedule = {"MATH", "MATH", "FREE", "LUNCH", "MATH"};
		absentee.addSkill("MATH");
		absentee.setSchedule(absenteeSchedule);
		Teacher cover = new Teacher(2, "BS");
		roster.addFullTeacher(absentee);
		roster.addSupplyTeacher(cover);
		
		Absence a = new Absence(absentee, cover, "1", "Monday", "Week 1");
		record.addAbsence(a);
		Assigner assigner = new Assigner(record, roster);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a.getWeekNum()).size(), 0);
		
		assigner.assignCoveragesForWeek(1);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a.getWeekNum()).size(), 1);
		assertEquals(assigner.getRecord().getCoveredAbsencesByDate(a.getWeekNum(), a.periodStrToInt(), a.getDay()).size(), 1);
		Absence covered = assigner.getRecord().getCoveredAbsencesByDate(a.getWeekNum(), a.periodStrToInt(), a.getDay()).get(0);
		assertEquals(covered.getTeacher().getId(), absentee.getId());
		assertEquals(covered.getCoverage().getId(), cover.getId());
	}
	
	@Test
	public void testOverWeeklyThresholdAssignment() {
		AbsenceRecord record = new AbsenceRecord();
		TeacherRoster roster = new TeacherRoster();
		Teacher absentee = new Teacher(1, "CB");
		String[] absenteeSchedule = {"MATH", "MATH", "FREE", "LUNCH", "MATH"};
		absentee.setSchedule(absenteeSchedule);
		absentee.addSkill("MATH");
		Teacher cover1 = new Teacher(2, "BS");
		Teacher cover2 = new Teacher(3, "BD");
		String[] coverSchedule = {"FREE", "MATH", "MATH", "LUNCH", "MATH"};
		cover1.setSchedule(coverSchedule);
		cover2.setSchedule(coverSchedule);
		cover1.addSkill("MATH");
		cover2.addSkill("MATH");
		roster.addFullTeacher(absentee);
		roster.addFullTeacher(cover1);
		roster.addFullTeacher(cover2);
		
		Absence a1 = new Absence(absentee, cover1, "1", "Monday", "Week 1");
		Absence a2 = new Absence(absentee, cover1, "1", "Monday", "Week 1");
		Absence a3 = new Absence(absentee, "1", "Monday", "Week 1");

		record.addAbsence(a1);
		record.addAbsence(a2);
		record.addAbsence(a3);
		Assigner assigner = new Assigner(record, roster);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a1.getWeekNum()).size(), 1);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a1.getWeekNum()).size(), 2);

		assigner.assignCoveragesForWeek(1);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a1.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a1.getWeekNum()).size(), 3);
		assertEquals(assigner.getRecord().getCoveredAbsencesByDate(a1.getWeekNum(), a1.periodStrToInt(), a1.getDay()).size(), 3);
		Absence covered = assigner.getRecord().getCoveredAbsencesByDate(a1.getWeekNum(), a1.periodStrToInt(), a1.getDay()).get(2);
		assertEquals(covered.getTeacher().getId(), absentee.getId());
		assertEquals(covered.getCoverage().getId(), cover2.getId());
	}

	
	@Test
	public void testOverMonthlyThresholdAssignment() {
		AbsenceRecord record = new AbsenceRecord();
		TeacherRoster roster = new TeacherRoster();
		Teacher absentee = new Teacher(1, "CB");
		String[] absenteeSchedule = {"MATH", "MATH", "FREE", "LUNCH", "MATH"};
		absentee.setSchedule(absenteeSchedule);
		absentee.addSkill("MATH");
		Teacher cover1 = new Teacher(2, "BS");
		Teacher cover2 = new Teacher(3, "BD");
		String[] coverSchedule = {"FREE", "MATH", "MATH", "LUNCH", "MATH"};
		cover1.setSchedule(coverSchedule);
		cover2.setSchedule(coverSchedule);
		cover1.addSkill("MATH");
		cover2.addSkill("MATH");
		roster.addFullTeacher(absentee);
		roster.addFullTeacher(cover1);
		roster.addFullTeacher(cover2);
		
		Absence a1 = new Absence(absentee, cover1, "1", "Monday", "Week 1");
		Absence a2 = new Absence(absentee, cover1, "1", "Monday", "Week 1");
		Absence a3 = new Absence(absentee, cover1, "1", "Monday", "Week 2");
		Absence a4 = new Absence(absentee, cover1, "1", "Monday", "Week 2");
		Absence a5 = new Absence(absentee, "1", "Monday", "Week 3");

		record.addAbsence(a1);
		record.addAbsence(a2);
		record.addAbsence(a3);
		record.addAbsence(a4);
		record.addAbsence(a5);
		Assigner assigner = new Assigner(record, roster);
		
		//TODO getUncovered/CoveredAbsencesByMonth WBN
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a1.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a3.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a5.getWeekNum()).size(), 1);

		assigner.assignCoveragesForWeek(1);
		assigner.assignCoveragesForWeek(2);
		assigner.assignCoveragesForWeek(3);
		
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a1.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a3.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getUncoveredAbsencesByWeek(a5.getWeekNum()).size(), 0);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a1.getWeekNum()).size(), 2);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a3.getWeekNum()).size(), 2);
		assertEquals(assigner.getRecord().getCoveredAbsencesByWeek(a5.getWeekNum()).size(), 1);
		assertEquals(assigner.getRecord().getCoveredAbsencesByDate(a5.getWeekNum(), a5.periodStrToInt(), a5.getDay()).size(), 1);
		Absence covered = assigner.getRecord().getCoveredAbsencesByDate(a5.getWeekNum(), a5.periodStrToInt(), a5.getDay()).get(0);
		assertEquals(covered.getTeacher().getId(), absentee.getId());
		assertEquals(covered.getCoverage().getId(), cover2.getId());
	}
}
