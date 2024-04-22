package fr.dtn.noobwork.io.data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Excel {
    private static final List<String> alphabet;
    private static final List<String> columns;

    static {
        alphabet = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));

        columns = new ArrayList<>();
        columns.addAll(alphabet);

        for(String first : alphabet) {
            for (String second : alphabet) {
                if(columns.size() == 255)
                    break;

                columns.add(first + second);
            }
        }
    }

    private File file;
    private String sheet;
    private final Workbook workbook;

    public Excel() {
        this.workbook = new HSSFWorkbook();
        this.file = null;
        this.sheet = null;
    }

    public Excel(File file) {
        this.file = file;

        try(FileInputStream fis = new FileInputStream(file)) {
            if(file.getPath().endsWith(".xls") || file.getPath().endsWith(".xlsx")) {
                this.workbook = new HSSFWorkbook(fis);
            } else {
                throw new RuntimeException("File extension is wrong : " + file.getPath().substring(file.getPath().lastIndexOf('.')));
            }
        } catch(IOException e) {
            throw new RuntimeException("Unable to load the workbook");
        }

        this.sheet = this.workbook.getSheetAt(0).getSheetName();
    }

    public Excel(String path) {
        this(new File(path));
    }

    public void setWorkingSheet(String sheet) {
        this.sheet = sheet;
    }

    private int getSplit(String name) {
        for(int i = 0; i < name.length(); i++) {
            if(!columns.contains(String.valueOf(name.charAt(i)))) {
                return i;
            }
        }

        return name.length();
    }

    public Cell getCell(String sheetName, String cellName) {
        int columnId = columns.indexOf(cellName.substring(0, getSplit(cellName)));
        int rowId = Integer.parseInt(cellName.substring(getSplit(cellName)))-1;

        Sheet sheet = this.workbook.getSheet(sheetName);

        if(sheet == null)
            throw new IllegalArgumentException("No sheet called '" + sheetName + "'");

        Row row = sheet.getRow(rowId);

        if(row == null)
            row = sheet.createRow(rowId);

        Cell cell = row.getCell(columnId);

        if(cell == null)
            cell = row.createCell(columnId);

        return cell;
    }

    public Cell getCell(String name) {
        return getCell(this.sheet, name);
    }

    public void setCellValue(String sheet, String name, double value) { getCell(sheet, name).setCellValue(value); }
    public void setCellValue(String sheet, String name, Date value) { getCell(sheet, name).setCellValue(value); }
    public void setCellValue(String sheet, String name, LocalDate value) { getCell(sheet, name).setCellValue(value); }
    public void setCellValue(String sheet, String name, LocalDateTime value) { getCell(sheet, name).setCellValue(value); }
    public void setCellValue(String sheet, String name, String value) { getCell(sheet, name).setCellValue(value); }
    public void setCellValue(String sheet, String name, Calendar value) { getCell(sheet, name).setCellValue(value); }
    public void setCellFormula(String sheet, String name, String formula) { getCell(sheet, name).setCellFormula(formula); }

    public void setCellValue(String name, double value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellValue(String name, Date value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellValue(String name, LocalDate value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellValue(String name, LocalDateTime value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellValue(String name, String value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellValue(String name, Calendar value) { getCell(this.sheet, name).setCellValue(value); }
    public void setCellFormula(String name, String formula) { getCell(this.sheet, name).setCellFormula(formula); }

    public void createSheet(String sheetName) {
        this.workbook.createSheet(sheetName);

        if(this.workbook.getNumberOfSheets() == 1)
            this.sheet = sheetName;
    }

    public void save(File file) {
        this.file = file;

        try(FileOutputStream output = new FileOutputStream(file)) {
            if(!file.exists() && !file.createNewFile())
                throw new IOException("Unable to create the Excel file");

            this.workbook.write(output);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() { save(this.file); }
    public void save(String path) { save(new File(path)); }

    public File getFile() { return file; }
    public void setFile(File file) { this.file = file; }
    public void setFilePath(String path) { this.file = new File(path); }
}