package vn.vnpt.common.converter;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelHelper {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static ByteArrayInputStream buildExcelDocument(String SHEET, List<String> HEADERS, ArrayList<Map<String, Object>> list){
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
            // create one sheet
            Sheet sheet = workbook.createSheet(SHEET);
            // create headerRow as a header
            Row headerRow  = sheet.createRow(0);
            for (int col = 0; col < HEADERS.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS.get(col));
            }

            // create row1 onwards from List<T>
            int rowNum = 1;
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.RIGHT);
            for(Map<String, Object> spec : list) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i  < HEADERS.size();i++){
                    Cell cell = row.createCell((short) i);
                    cell.setCellValue(spec.get(HEADERS.get(i)) == null ? "" : spec.get(HEADERS.get(i))+"");
                    if (spec.get(HEADERS.get(i)) != null && NumberUtils.isNumber(spec.get(HEADERS.get(i)).toString()))
                        cell.setCellStyle(style);
                }
            }
            workbook.write(out);

            out.flush();
            out.close();
            return new ByteArrayInputStream(out.toByteArray());
        }catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<Object> parseExcel(InputStream inputStream) {
        List<Object> dataList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<String> headers = new ArrayList<>();

            // Đọc header
            if (rows.hasNext()) {
                Row headerRow = rows.next();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }
            }

            // Đọc từng dòng dữ liệu
            while (rows.hasNext()) {
                Row row = rows.next();
                Map<String, Object> data = new HashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data.put(headers.get(i), getCellValue(cell));
                }

                dataList.add(data); // Add Map<String, Object> to List<Object>
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return dataList;
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? cell.getDateCellValue()
                        : cell.getNumericCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return null;
        }
    }
}
