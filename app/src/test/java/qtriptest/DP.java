package qtriptest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {
    // TODO: use correct annotation to connect the Data Provider with your Test Cases
    
    @DataProvider(name = "datasetforQTrip")
    public static String[][] readExcelFile(Method method){
        String sheetName = method.getName();
        int rowIndex = 0;
        int cellIndex = 0;
        List<List> outputList = new ArrayList<>();
    try{
        FileInputStream excelFile = new FileInputStream(new File(
                "./src/test/resources/DatasetsforQTrip.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        XSSFSheet selectedSheet =  workbook.getSheet(sheetName);
        Iterator<Row> iterator = selectedSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            List<String> innerList = new ArrayList<>();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (rowIndex > 0 && cellIndex > 0) {
                    if (cell.getCellType() == CellType.STRING) {
                        innerList.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        innerList.add(String.valueOf(cell.getNumericCellValue()));
                    }
                }
                cellIndex = cellIndex + 1;
            }
            rowIndex = rowIndex + 1;
            cellIndex = 0;
            if (innerList.size() > 0)
                outputList.add(innerList);
                System.out.println(innerList);

        }

        excelFile.close();

        String[][] stringArray = outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
    } catch (FileNotFoundException e) {
        System.err.println("Excel file not found: " + e.getMessage());
        e.printStackTrace();
    } catch (IOException e) {
        System.err.println("Error reading Excel file: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }
    return new String[0][0];
}
    
}
