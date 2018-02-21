package spike;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Spike {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(new File("workbook/spike.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(fis);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row = sheet.getRow(23);
		    XSSFCell cell = row.getCell(5);
		    System.out.println(cell.getStringCellValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("It didnt work");
		}
	}
}