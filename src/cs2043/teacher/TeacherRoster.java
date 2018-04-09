package cs2043.teacher;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cs2043.util.WorkbookUtils;

public class TeacherRoster {

	private ArrayList<Teacher> fullTeachers;
	private ArrayList<Teacher> supplyTeachers;
	
	public TeacherRoster() {
		fullTeachers = new ArrayList<Teacher>();
		supplyTeachers = new ArrayList<Teacher>();
	}
	
	public void addFullTeacher(Teacher t) {
		fullTeachers.add(t);
	}
	
	public void addSupplyTeacher(Teacher t) {
		supplyTeachers.add(t);
	}
	
	public ArrayList<Teacher> getFullTeachers() {
		return fullTeachers;
	}

	public ArrayList<Teacher> getSupplyTeachers() {
		return supplyTeachers;
	}

	public void setFullTeacher(ArrayList<Teacher> fullTeachers) {
		this.fullTeachers = fullTeachers;
	}
	
	public void setSupplyTeacher(ArrayList<Teacher> supplyTeachers) {
		this.supplyTeachers = supplyTeachers;
	}
	
	public Teacher getFullTeacherById(int id) {
		for (Teacher t : fullTeachers) {
			if (t.getId() == id) {
				return t;
			}
		}
		// TODO what do we do here
		return null;
	}

	public Teacher getSupplyTeacherById(int id) {
		for (Teacher t : supplyTeachers) {
			if (t.getId() == id) {
				return t;
			}
		}
		// TODO what do we do here
		return null;
	}
	
	public ArrayList<Teacher> getFullTeacherWithFreePeriod(int period) {
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		for (Teacher t : fullTeachers) {
			if (t.getFreePeriod() == period) {
				teachers.add(t);
			}
		}
		return teachers;
	}
	
	public void retrieveFullTeachers(XSSFWorkbook wb) {
	    XSSFSheet sheet = wb.getSheetAt(WorkbookUtils.FULL_TEACHER_SHEET);

	    int row = 1;
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (id != null) {
			Teacher teacher  = new Teacher(id, initials);
			teacher.generateTeacherScheduleSkills(sheet, row);
			getFullTeachers().add(teacher);
			row++;
			id = null;
			if (!WorkbookUtils.isEmptyRow(sheet, row)) {
				id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL));
				initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		}
	}
	
	public void retrieveSupplyTeachers(XSSFWorkbook wb) {
	    XSSFSheet sheet = wb.getSheetAt(WorkbookUtils.SUPPLY_TEACHER_SHEET);

	    int row = 1;
	    Integer id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
	    String initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
		
		while (id != null) {
			Teacher teacher  = new Teacher(id, initials);
			getSupplyTeachers().add(teacher);
			row++;
			id = null;
			if (!WorkbookUtils.isEmptyRow(sheet, row)) {
				id = Integer.parseInt(WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_ID_COL).substring(1));
				initials = WorkbookUtils.getCellValueAsString(sheet, row, WorkbookUtils.TEACHER_INITIALS_COL);
			}
		}
	}
}
