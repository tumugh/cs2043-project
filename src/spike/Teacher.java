package spike;

import java.util.ArrayList;

public class Teacher {
	
	private int id;
	private String initials;
	private ArrayList<OnCall> oncalls;
	
	public Teacher(int id, String initials) {
		this.id = id;
		this.initials = initials;
		this.oncalls = new ArrayList<OnCall>();
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

	public ArrayList<OnCall> getOncalls() {
		return oncalls;
	}

	public void setOncalls(ArrayList<OnCall> oncalls) {
		this.oncalls = oncalls;
	}

	@Override
	public String toString() {
		return "ID: " + getId() + " Initials: " +  getInitials();
	}
	
}
