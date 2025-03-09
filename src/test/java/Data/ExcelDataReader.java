package Data;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataReader {


    public static String GetDataFromExcel(String path, int sheetIndex, int RowNum, int CellNum) throws IOException {

        File src = new File(path);
        FileInputStream FIS = new FileInputStream(src);
        XSSFWorkbook xsf = new XSSFWorkbook(FIS);
        XSSFSheet sheet = xsf.getSheetAt(sheetIndex);
        String FirstData = sheet.getRow(RowNum).getCell(CellNum).getStringCellValue();
        FIS.close();
        xsf.close();
        return FirstData;
    }





}