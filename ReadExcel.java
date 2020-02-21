/**
 * ReadExcel
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReadExcel implements Serializable {

    /**
     * Default constructor
     */
    public ReadExcel() {
    }

    public static HashMap<Integer, List<String>> readExcel(String inputFile, int sheetNum) {
        HashMap<Integer, List<String>> result = new HashMap<>();

        try {

            File myFile = new File(inputFile);
            FileInputStream fis = new FileInputStream(myFile);

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(sheetNum);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            // Traversing over each row of XLSX file
            int rowId = 0;
            while (rowIterator.hasNext()) {
                List<String> data = new ArrayList<>();
                Row row = rowIterator.next();
                rowId++;
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            data.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        default:
                    }
                }
                result.put(rowId, data);
            }
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
        return result;
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