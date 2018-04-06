package cs2043.teacher;

import java.util.ArrayList;

public class TeacherRoster {

	private ArrayList<Teacher> fullTeachers;
	private ArrayList<Teacher> supplyTeachers;
	
	public TeacherRoster() {
		fullTeachers = new ArrayList<Teacher>();
		supplyTeachers = new ArrayList<Teacher>();
	}
	
	public TeacherRoster(ArrayList<Teacher> fullTeachers, ArrayList<Teacher> supplyTeachers) {
		this.fullTeachers = fullTeachers;
		this.supplyTeachers = supplyTeachers;
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
}
