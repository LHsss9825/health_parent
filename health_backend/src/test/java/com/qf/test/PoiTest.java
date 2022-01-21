package com.qf.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class PoiTest {

    //@Test
    public void testWrite() throws IOException {
        //工作簿：Workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //工作表：Sheet
        HSSFSheet sheet = workbook.createSheet("H2104");
        //行：row
        HSSFRow row = sheet.createRow(0);
        //单元格：Cell
        HSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("姓名");

        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("性别");

        for (int i = 0; i < 5; i++) {
            row = sheet.createRow(i+1);
            cell0 = row.createCell(0);
            cell0.setCellValue("刘亦菲"+i+"号");
            cell1 = row.createCell(1);
            cell1.setCellValue("女");
        }

        OutputStream os = new FileOutputStream("C:\\Users\\Hero\\Desktop\\sister.xls");
        workbook.write(os);
        os.close();
    }

    //@Test
    public void testRead() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("C:\\Users\\Hero\\Desktop\\sister.xls"));
        HSSFSheet sheet = workbook.getSheet("H2104");
        for (int i = 0; i < 5; i++) {
            HSSFRow row = sheet.getRow(i + 1);
            String name = row.getCell(0).getStringCellValue();
            String sex = row.getCell(1).getStringCellValue();
            System.out.println(name+"---------"+sex);
        }
    }
}
