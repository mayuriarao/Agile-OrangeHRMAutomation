package utils;

import org.testng.annotations.Test;

import java.util.List;

public class ExcelUtilTest {

    @Test
    public void testReadExcelData() {
        String filePath = "Excel/loginData.xlsx";
        String sheetName = "LoginData";

        List<String[]> credentials = ExcelUtil.readLoginData(filePath, sheetName);

        for (String[] data : credentials) {
            System.out.println("Username: " + data[0] + ", Password: " + data[1]);
        }
    }
}
