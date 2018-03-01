package spike;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Util {

	public static final int TEACHER_ROW_START = 4;
	public static final int TEACHER_ID_COL = 0;
	public static final int TEACHER_INITIALS_COL = 1;
	
	public static int getIntValue(XSSFSheet sheet, int row, int col) {
		return (int) sheet.getRow(row).getCell(col).getNumericCellValue();
	}
	
	public static String getStringValue(XSSFSheet sheet, int row, int col) {
		return sheet.getRow(row).getCell(col).getStringCellValue();
	}
}
