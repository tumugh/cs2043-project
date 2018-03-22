package spike;

public class FullTeacher extends Teacher{
	
	public int tally = 0;
	public String skills;
	
	public FullTeacher(int id, String initials, String skills) {
		super(id, initials);
		this.skills = skills;
	}
	
	public void incrementTally(){
		tally = tally + 1;
	}
}
