package com.apeny.sqlgen;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

/**
 * Created by apeny on 2018/1/4.
 */
public class SqlGenerator {
    private static final String VARCHAR2 = "VARCHAR2";
    private static final String NUMBER = "NUMBER";
    private static final String VARCHAR = "VARCHAR";
    private static final String CHAR = "CHAR";
    private static final String NVARCHAR2 = "NVARCHAR2";
    private static final String NVARCHAR = "NVARCHAR";
    private static final String NCHAR = "NCHAR";
    private static final String CRLF = "\r\n";
    private static final char FILE_SEPARATOR = File.separatorChar;

    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("使用方法:com.apeny.sqlgen.SqlGenerator"
                    + " D:\\tables.xls或com.apeny.sqlgen.SqlGenerator D:\\tables.xlsx");
        }
        generateSqlFile(args[0]);
    }

    private static void generateSqlFile(String filePath) {
        if (isEmpty(filePath)) {
            throw new IllegalArgumentException("传入的Excel文件路径为空");
        }
        if (!(filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
            throw new IllegalArgumentException("传入的文件不是Excel");
        }
        int lastFileSeparator = filePath.lastIndexOf(FILE_SEPARATOR);
        if (lastFileSeparator == -1) {
            throw new IllegalArgumentException("文件格式错误,文件路径至少应有一个文件分隔符");
        }
        String preExcelFileName = filePath.substring(lastFileSeparator + 1, filePath.lastIndexOf("."));
        String sqlFileName = filePath.substring(0, lastFileSeparator + 1) + preExcelFileName + ".sql";
        try {
            System.out.println("使用文件File=" + filePath + "生成insert sql语句开始");
            String sqlStatement = readFile(filePath);
            writeToFile(sqlFileName, sqlStatement);
            System.out.println("生成insert sql语句成功,文件路径为: " + sqlFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Description 读取Excel
     * </p>
     *
     * @param fileName
     * @return
     * @throws Exception
     * @author apy
     * @date 2018年01月05日 15:19:00
     */
    private static String readFile(String fileName) throws Exception {
        if (isEmpty(fileName)) {
            throw new IllegalArgumentException("文件名称为空");
        }
        InputStream inputStream = null;
        StringBuilder sqlBuilder = new StringBuilder();
        try {
            inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);

            int sheetCount = workbook.getNumberOfSheets();
            //循环工作表
            for (int i = 0; i < sheetCount; i++) {
                try {
                    Sheet sheet = workbook.getSheetAt(i);
                    String tableName = sheet.getSheetName();
                    StringBuilder tableBuilder = new StringBuilder();
                    Row row = sheet.getRow(0);
                    Cell cell = row.getCell(0);
                    List<String> columnNameAndTypes = new ArrayList<>();
                    int cellIndex = 0;
                    while (cell != null) {
                        columnNameAndTypes.add(cell.getStringCellValue());
                        cell = row.getCell(++cellIndex);
                    }
                    int columnCount = cellIndex;
                    //生成列名
                    tableBuilder.append("INSERT INTO " + tableName + "(");
                    for (String nameAndType : columnNameAndTypes) {
                        tableBuilder.append(nameAndType.split("#")[0] + ",");
                    }
                    tableBuilder.deleteCharAt(tableBuilder.length() - 1).append(") VALUES" + CRLF);
                    //分析列名称和数据类型
                    Map<Integer, String> columnTypes = columnTypeMap(columnNameAndTypes);

                    int lineIndex = 1;
                    long lineEmptySet = 2 ^ columnCount - 1;

                    //生成每一行记录
                    while (true) {
                        row = sheet.getRow(lineIndex++);
                        int columnIndex = 0;
                        int lineEmpty = 0;
                        StringBuilder lineBuilder = new StringBuilder().append("(");
                        for (; columnIndex < columnCount; columnIndex++) {
                            Cell columnCell = row.getCell(columnIndex);
                            String columnValue = null;
                            if (columnCell != null) {
                                if (columnCell.getCellTypeEnum() == NUMERIC) {
                                    columnValue = Double.toString(columnCell.getNumericCellValue());
                                } else {
                                    columnValue = columnCell.getStringCellValue();
                                }
                            }
                            String sqlColumnValue = getSqlColumnValue(columnTypes, columnIndex, columnValue);
                            lineBuilder.append(sqlColumnValue);
                            if (isEmpty(sqlColumnValue)) {
                                lineEmpty = 2 ^ columnIndex;
                            }
                            lineBuilder.append(columnValue + ",");
                        }
                        if (lineEmptySet == lineEmpty) {
                            break;
                        }
                        lineBuilder.deleteCharAt(lineBuilder.length() - 1);
                        lineBuilder.append(")" + CRLF);
                        tableBuilder.append(lineBuilder);
                    }
                    tableBuilder.append(";" + CRLF);
                    //表示有数据
                    if (lineIndex > 2) {
                        sqlBuilder.append(tableBuilder);
                    }
                } catch (Exception oneSheetException) {
                    oneSheetException.printStackTrace();
                }
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return sqlBuilder.toString();
    }

    /**
     * <p>
     * Description 将生成的Sql语句写入文件
     * </p>
     *
     * @param sqlFileName
     * @param sqlStatement
     * @throws Exception
     * @author apy
     * @date 2018年01月05日 15:27:25
     */
    private static void writeToFile(String sqlFileName, String sqlStatement) throws Exception {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(sqlFileName);
            fileWriter.write(sqlStatement);
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * <p>
     * Description 字符串为空
     * </p>
     *
     * @param string
     * @return
     * @author apy
     * @date 2018年01月05日 15:05:33
     */
    private static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    /**
     * <p>
     * Description 每个列的数据类型
     * </p>
     *
     * @param headColumnValues
     * @return
     * @author apy
     * @date 2018年01月05日 14:42:57
     */
    private static Map<Integer, String> columnTypeMap(List<String> headColumnValues) {
        if (headColumnValues == null) {
            return new HashMap<>();
        }
        Map<Integer, String> columnTypes = new HashMap<>();
        int index = 0;
        for (String nameAndType : headColumnValues) {
            String type = nameAndType.split("#")[1];
            if (VARCHAR2.equalsIgnoreCase(type) || VARCHAR.equalsIgnoreCase(type) || CHAR.equalsIgnoreCase(type)
                    || NVARCHAR2.equalsIgnoreCase(type) || NVARCHAR.equalsIgnoreCase(type) || NCHAR.equalsIgnoreCase(type)) {
                columnTypes.put(index++, VARCHAR2);
            } else if (NUMBER.equalsIgnoreCase(type)) {
                columnTypes.put(index++, NUMBER);
            } else {
                throw new IllegalArgumentException("列的数据类型错误" + nameAndType);
            }
        }
        return columnTypes;
    }

    /**
     * <p>
     * Description 生成列值
     * </p>
     *
     * @param columnTypes
     * @param columnIndex
     * @param excelColumnValue
     * @return
     * @author apy
     * @date 2018年01月05日 14:43:12
     */
    private static String getSqlColumnValue(Map<Integer, String> columnTypes, int columnIndex, String excelColumnValue) {
        if (columnTypes == null || columnTypes.isEmpty()) {
            throw new IllegalArgumentException("列类型Map为空");
        }
        if (columnTypes.size() <= columnIndex) {
            throw new IllegalArgumentException("列索引ColumnIndex=[" + columnTypes + "]大于列个数Map.size()=" + columnTypes.size());
        }
        if (VARCHAR2.equals(columnTypes.get(columnIndex))) {
            return "'" + excelColumnValue + "'";
        }
        if (NUMBER.equals(columnTypes.get(columnIndex))) {
            return excelColumnValue;
        }
        throw new IllegalArgumentException("没有支持的列类型");
    }
}
