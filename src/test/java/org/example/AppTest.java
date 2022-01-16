package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

/**
 * A dirty simple program that reads an Excel file.
 * @author www.codejava.net
 *
 */

public class AppTest {
    static String excelFilePath = "//TestData.xls";
    static String sheetName = "Data";
    static Workbook workbook;
    static int ColIndex_IssueKey;
    static int ColIndex_QACompletion;
    static String SessionId;
    static String BaseURI="http://localhost:8080";
    @Test
    public void test1() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        if(excelFilePath.endsWith(".xlsx"))
        {
            workbook = new XSSFWorkbook(inputStream);
        }
        else if(excelFilePath.endsWith(".xls"))
        {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet DataSheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        Iterator<Row> iterator = DataSheet.iterator();

        Row firstRow = DataSheet.getRow(0);
        Iterator<Cell> cellIterator1 = firstRow.cellIterator();

        while (cellIterator1.hasNext()) {
            Cell cell = cellIterator1.next();
            String Cell_Value = formatter.formatCellValue(cell);
            System.out.println(Cell_Value);
            if(Cell_Value.equalsIgnoreCase("IssueKey"))
            {
                ColIndex_IssueKey = cell.getColumnIndex();
            }
            if(Cell_Value.equalsIgnoreCase("QACompletion"))
            {
                ColIndex_QACompletion = cell.getColumnIndex();
            }
        }
        int rowCount = DataSheet.getLastRowNum() - DataSheet.getFirstRowNum();
        System.out.println("Total Data Rows : " +(rowCount-1));
        SessionId   = Login.sessionId(BaseURI);
        for(int i=1;i<rowCount;i++)
        {
            Row nextRow = DataSheet.getRow(i);
            System.out.println("IssueKey: "+formatter.formatCellValue(nextRow.getCell(ColIndex_IssueKey)));
            System.out.println("QACompletion: "+formatter.formatCellValue(nextRow.getCell(ColIndex_QACompletion)));
            System.out.print("\n");
            String IssueKey = formatter.formatCellValue(nextRow.getCell(ColIndex_IssueKey));
            String QACompletion = formatter.formatCellValue(nextRow.getCell(ColIndex_QACompletion));
            new UpdateIssue(BaseURI, SessionId, IssueKey,QACompletion);

        }
        workbook.close();
        inputStream.close();
    }

}