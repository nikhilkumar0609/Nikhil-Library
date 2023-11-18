package com.springboot.processors.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.processors.services.XLSXService;

public class XLSXUtil {

	private static final Logger LOG = LoggerFactory.getLogger(XLSXService.class);
	
	public static ArrayList<HashMap<String, String>> readXLSXFile(File fileName, String headerTokens[], int headerRows){
		
		ArrayList<HashMap<String, String>> rowsList = new ArrayList<HashMap<String, String>> ();
	
		try {
			FileInputStream fis = new FileInputStream(fileName);
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int columns = headerTokens.length;
			int startRow = headerRows;
			int lastRow = sheet.getLastRowNum();
			
			for(int r = startRow; r <= lastRow; r++){
				Row row = sheet.getRow(r);
				HashMap<String, String> rowMap = new HashMap<>();
				
				for(int c = 0; c < columns; c++){
					Cell cell = row.getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellTypeCheck(headerTokens, rowMap, c, cell);
				}
				rowsList.add(rowMap);
			}
			
		}catch (FileNotFoundException e) {
		    LOG.error("{} File not found...", fileName, e);
		} catch (IOException e) {
		    LOG.error("Error while reading file {}...", fileName, e);
		}

		return rowsList;
		
	}

	private static void cellTypeCheck(String[] headerTokens, HashMap<String, String> rowMap, int c, Cell cell) {
		if(cell == null || cell.getCellType() == CellType.BLANK) {
			rowMap.put(headerTokens[c], "");
		}else if(cell.getCellType() == CellType.STRING) {
			rowMap.put(headerTokens[c], cell.getStringCellValue().trim());
		}else if(cell.getCellType() == CellType.NUMERIC) {
			Double dValue = cell.getNumericCellValue();
			rowMap.put(headerTokens[c], dValue.toString());
		}
		
	}
	
	
	
	// Write to XLSX File
	public static void writeToXLSXFile(String[] headerTokens, ArrayList<HashMap<String,String>> rowsList, String fileName, String filePath) throws IOException {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(fileName);

			XSSFRow row = sheet.createRow(0);
			for (int cellnum = 0; cellnum < headerTokens.length; cellnum++) {
				XSSFCell cell = row.createCell(cellnum);
				cell.setCellValue(headerTokens[cellnum]);
			}

			for (int rownum = 1; rownum <= rowsList.size(); rownum++) {
				row = sheet.createRow(rownum);
				int cellnum = 0;
				for (String header : headerTokens) {
					XSSFCell cell = row.createCell(cellnum++);
					HashMap rowMap = (HashMap) rowsList.get(rownum - 1);
					if (rowMap.get(header) != null)
						cell.setCellValue((String) rowMap.get(header));
					else
						cell.setCellValue("");
				}
			}

			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			LOG.info(fileName + " written successfully..");
		}
	}
	
}
