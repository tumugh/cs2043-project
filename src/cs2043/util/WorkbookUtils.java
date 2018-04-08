package cs2043.util;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class WorkbookUtils {

	public static final int START_COL = 4;
	public static final int END_COL = 28;
	public static final int TEACHER_ROW_START = 4;
	public static final int TEACHER_ID_COL = 0;
	public static final int TEACHER_INITIALS_COL = 1;
	public static final int WEEKS_PER_TERM = 20;
	public static final String ABSENCE_INDICATOR = "X";
	public static final int SCHEDULE_PERIOD1_COL = 2;
	public static final int SCHEDULE_PERIOD4_COL = 6;
	public static final int FULL_TEACHER_SHEET= 20;
	public static final int SUPPLY_TEACHER_SHEET= 21;
	public static final String FREE_PERIOD = "FREE";

	public static final String SPIKE_WORKBOOK_PATH = "workbook/spike.xlsx";
	public static final String WORKBOOK_PATH = "workbook/workbook.xlsx";
	
	public static int getIntValue(XSSFSheet sheet, int row, int col) {
		return (int) sheet.getRow(row).getCell(col).getNumericCellValue();
	}
	
	public static String getStringValue(XSSFSheet sheet, int row, int col) {
		return sheet.getRow(row).getCell(col).getStringCellValue();
	}
	
	public static String getRawValue(XSSFSheet sheet, int row, int col) {
		return sheet.getRow(row).getCell(col).getRawValue();
	}
	
	public static String getCellValueAsString(XSSFSheet sheet, int row, int col) {
		DataFormatter df = new DataFormatter();
		return df.formatCellValue(sheet.getRow(row).getCell(col));
	}
	
	public static String getPeriod(XSSFSheet sheet, int col) {
		return getCellValueAsString(sheet, 3, col);
	}
	
	public static boolean isNotBlank(String str) {
		return str != null && str != "";
	}
	
	public static boolean isEmptyRow(XSSFSheet sheet, int rn) {
		XSSFRow row = sheet.getRow(rn);
		if (row == null) {
			return true;
		}
		
//		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
//			XSSFCell cell = row.getCell(i);
//	        if (cell == null || cell.getStringCellValue() == " ") {
//	        	return true;
//	        }
//		}
		
		return false;
	}
	
	//TODO Throw Exception if col not valid day reference
	public static String getDay(XSSFSheet sheet, int col) {
		//TODO Extract to constants
		if (col >= 4 && col <= 8) {
			return "Monday";
		} else if (col >= 9 && col <= 13) {
			return "Tuesday";
		} else if (col >= 14 && col <= 18) {
			return "Wednesday";
		} else if (col >= 19 && col <= 23) {
			return "Thursday";
		} else if (col >= 24 && col <= 28) {
			return "Friday";
		} else {
			//throw here
			return "Busted";
		}
	}
	
	public static int convertWeekToMonth(int week) {
		if (week <= 4 && week >= 1) {
			return 1;
		} else if (week <= 8 && week >= 5) {
			return 2;
		} else if (week <= 12 && week >= 19) {
			return 3;
		} else if (week <= 16 && week >= 13) {
			return 4;
		} else if (week <= 20 && week >= 17) {
			return 5;
		} else {
			return -1;
		}
	}
}
