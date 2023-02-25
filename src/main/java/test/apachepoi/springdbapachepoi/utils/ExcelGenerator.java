package test.apachepoi.springdbapachepoi.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import test.apachepoi.springdbapachepoi.exceptions.CustomException;
import test.apachepoi.springdbapachepoi.model.ReportData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    private static final String[] HEADERS = {"ID", "FirstName", "LastName", "Balance", "Age",  "Town"};

    private ExcelGenerator() {
        throw new IllegalStateException("ExcelGenerator is a utility class");
    }

    public static byte[] generateExcelReport(List<ReportData> reportDataList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Report");
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            for (int i = 0; i < reportDataList.size(); i++) {
                ReportData reportData = reportDataList.get(i);
                Row row = sheet.createRow(i + 1);

                createCellIfNotNull(row, reportData.getId(), 0);
                createCellIfNotNull(row, reportData.getFirstName(), 1);
                createCellIfNotNull(row, reportData.getLastName(), 2);
                createCellIfNotNull(row, reportData.getBalance(), 3);
                createCellIfNotNull(row, reportData.getAge(), 4);
                createCellIfNotNull(row, reportData.getTown(), 5);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new CustomException("Error generating Excel report: " + e.getMessage());
        }
    }

    private static void createCellIfNotNull(Row row, Object value, int cellIndex) {
        if (value != null) {
            Cell cell = row.createCell(cellIndex);

            if (value instanceof Number number) {
                cell.setCellValue(number.doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }
}