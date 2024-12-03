package dsalgo.utilities;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static Object[][] getTestDataFromExcel(String sheetName)  {
		//File excelFile=new File(System.getProperty("src/test/resources/TestData/invalid data.xlsx"));
		FileInputStream fisExcel = null;
		try {
			fisExcel = new FileInputStream("src/test/resources/TestData/dsAlgo_TestData.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fisExcel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet=workbook.getSheet(sheetName);
		
		int rows=sheet.getLastRowNum();
		int cols=sheet.getRow(0).getLastCellNum();
		
		Object[][]data=new Object[rows][cols];
		for (int i=0;i<rows;i++) {
			XSSFRow row = sheet.getRow(i+1);
			
			for(int j=0;j<cols;j++) {
				
			 XSSFCell cell = row.getCell(j);
			 CellType cellType = cell.getCellType();
			 switch(cellType) {
			 case STRING:
			 data[i][j]=cell.getStringCellValue();
			 break;
			 case NUMERIC:
				 data[i][j]=Integer.toString((int)cell.getNumericCellValue());
				 break;
			 case BOOLEAN:
				 data[i][j]=cell.getBooleanCellValue();
				 break;
			 }
				
			}
			
		}
		return data;
		
	
	}
	public static ExcelReader excelReader = null;
	public ExcelReader(String excelFilePath) {
		path = excelFilePath;
	}

	public static ExcelReader getInstance() {
		if (excelReader != null) {
			return excelReader;
		} else {			
			String excelFilePath = ConfigReader.getProperty("excelFilePath");
			excelReader = new ExcelReader(excelFilePath);
			return excelReader;
		}
	} 

	private FileInputStream fi;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private String path;

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellcount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		workbook.close();
		fi.close();
		return data;
	}
	
	private static boolean isRowEmpty(Row row) {
		if (Objects.isNull(row)) {
			return true;
		}
	    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
	        Cell cell = row.getCell(j);
	        if (cell != null && cell.getCellType() != CellType.BLANK) {
	            return false;
	        }
	    }
	    return true;
	}




}



