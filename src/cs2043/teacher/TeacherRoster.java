package cs2043.teacher;

import java.util.ArrayList;

public class TeacherRoster {

	private ArrayList<Teacher> fullTeachers;
	private ArrayList<Teacher> supplyTeachers;
	
	public TeacherRoster() {
		fullTeachers = new ArrayList<Teacher>();
		supplyTeachers = new ArrayList<Teacher>();
	}
	
//	public void addFullTeacher(Teacher t) {
//		fullTeachers.add(t);
//	}
//	
//	public void addSupplyTeacher(Teacher t) {
//		supplyTeachers.add(t);
//	}
	
	
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
	
	public void setFullTeacher(ArrayList<Teacher> fullTeachers) {
		this.fullTeachers = fullTeachers;
	}
	
	public void setSupplyTeacher(ArrayList<Teacher> supplyTeachers) {
		this.supplyTeachers = supplyTeachers;
	}
	
	
//	public Teacher getTeacherById(int id) {
//		
//	}
}
