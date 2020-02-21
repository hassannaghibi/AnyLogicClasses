/**
 * WriteToExcel
 */	
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WriteToExcel implements Serializable {

    /**
     * Default constructor
     */
	public WriteToExcel() {
    }


	public void write(String excelName ,String sheetName ,List<String> headData,List<List<String>> data) {

		try {
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet(sheetName);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < headData.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headData.get(i));
        }

		// Resize all columns to fit the content size
        for(int i = 0; i < headData.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        for(int i = 0; i < data.size(); i++) {
        	Row row = sheet.createRow(i+1);
        	for(int j = 0; j < data.get(i).size(); j++) {
            	Cell cell = row.createCell(j);
                cell.setCellValue(data.get(j).get(i));
            }
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(excelName + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        
		}catch(Exception ex) {
			ex.getMessage();
		}
    }
    
    
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * This number is here for model snapshot storing purpose<br>
	 * It needs to be changed when this class gets changed
	 */ 
	private static final long serialVersionUID = 1L;

}