package spike;
import java.util.*;
import spike.Absence;


public class Driver {

	
	//list of absences
	//list of teachers
	//list of supplies
	
	//fill in absence with supplies then oncallers
	
	ArrayList<Absence> absenceList = new ArrayList<Absence>();
	ArrayList<SupplyTeacher> supplyList = new ArrayList<SupplyTeacher>();
	
	Teacher brado = new FullTeacher(1, "Brado", "Math, History");
	absenceList.add(new Absence(brado, "1", "Monday", "2"));
	
	Teacher owen = new FullTeacher(1, "owen", "Math, History");
	absenceList.add(new Absence(brado, "2", "Monday", "2"));
	
	Teacher colin = new FullTeacher(1, "colin", "Math, History");
	absenceList.add(new Absence(brado, "3", "Monday", "2"));
	
	for(item in absenceList) {
		fillAbsence(absenceList[item]);
	}
}

public void fillAbsence(Absence fillThis) {
	
	//loop through the list of available people
	//if its a fulltime teacher, increment theiur tally
	//else do nothing
	
	if(currentTeacher instanceof FullTeacher) {
		incrementTally();
	}
}
