package spike;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Spike2 {

	public static void main(String[] args) {
		
		try {
			FileInputStream fis = new FileInputStream(new File("workbook/workbook.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    
		    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		    int id;
		    String initials;
		    int i = Util.TEACHER_ROW_START;
	    	id = Util.getIntValue(sheet, i, Util.TEACHER_ID_COL);
	    	initials = Util.getStringValue(sheet, i, Util.TEACHER_INITIALS_COL);
		    while (initials != null && initials != "") {
		    	teachers.add(new Teacher(id, initials));
		    	i++;
		    	id = Util.getIntValue(sheet, i, Util.TEACHER_ID_COL);
		    	initials = Util.getStringValue(sheet, i, Util.TEACHER_INITIALS_COL);
		    }
		    
		    for (Teacher t : teachers) {
		    	System.out.println(t.toString());
		    }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("It didnt work");
		}
	}
}
