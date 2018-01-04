package com.apeny.sqlgen;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apeny on 2018/1/4.
 */
public class SqlGenerator {
    private static final String VARCHAR2 = "VARCHAR2";
    private static final String NUMBER = "NUMBER";
    private static final String VARCHAR = "VARCHAR";
    private static final String CHAR = "CHAR";
    public static void main(String[] args) {

    }

    private static void readFile(String fileName) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);

            int sheetCount = workbook.getNumberOfSheets();
            //循环工作表
            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String tableName = sheet.getSheetName();
                Row row = sheet.getRow(0);
                Cell cell = row.getCell(0);
                String firstLineCellValue = cell.getStringCellValue();
                List<String> columnNames = new ArrayList<>();
                int cellIndex = 0;
                while (isEmpty(firstLineCellValue)) {
                    columnNames.add(firstLineCellValue);
                    firstLineCellValue = row.getCell(++cellIndex).getStringCellValue();
                }
                int columnCount = cellIndex;
                //分析列名称和数据类型

            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    private static Map<Integer, String> columnTypeMap(List<String> headColumnValues) {
        if (headColumnValues == null) {
            return new HashMap<>();
        }
        Map<Integer, String> columnTypes = new HashMap<>();
        int index = 0;
        for (String nameAndType : headColumnValues) {
            if (VARCHAR2.equalsIgnoreCase(nameAndType) || VARCHAR.equalsIgnoreCase(nameAndType) || CHAR.equalsIgnoreCase(nameAndType)) {
                columnTypes.put(index++, VARCHAR2);
            } else if (nameAndType != null && nameAndType.length() >= 9 && NUMBER.equalsIgnoreCase(nameAndType.substring(0, 6))) {
                columnTypes.put(index++, NUMBER);
            } else {
                throw new IllegalArgumentException("列的数据类型错误" + nameAndType);
            }
        }
        return columnTypes;
    }
}
