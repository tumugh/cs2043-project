package cs2043.teacher;

public class Teacher {
	
	private int id;
	private String initials;
	
	public Teacher(int id, String initials) {
		this.id = id;
		this.initials = initials;
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
}
