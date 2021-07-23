import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiTest extends ApiRequest {

    public static Logger log = Logger.getLogger(Log.class.getName());
    static ExcelUtils excelUtils = new ExcelUtils();

    @BeforeClass
    public static void beforeMethodTearUp() {
        log.info("Test Api Automation Started !!!!");
    }

    @Parameters({"fileName"})
    @Test
    public static void postAuthentication(String fileName) throws IOException {
        String excelFilePath = ReadData.getInstance().getData("Path_TestData") + fileName;
        excelUtils.setExcelFile(excelFilePath, "formData");
        Map<String, Object> headerValue = new HashMap<>();
        headerValue.put("Authorization", "Basic ");
        headerValue.put("Content-Type", "application/x-www-form-urlencoded");
        //iterate over all the row to print the data present in each cell.
        for (int i = 1; i <= excelUtils.getRowCountInSheet(); i++) {
            Map<String, String> bodyValue = new HashMap<>();
            bodyValue.put("FirstName", excelUtils.getCellData(i, 0));
            bodyValue.put("LastName", excelUtils.getCellData(i, 1));
            bodyValue.put("Email", excelUtils.getCellData(i, 2));
            bodyValue.put("Password", excelUtils.getCellData(i, 3));
            bodyValue.put("Country", excelUtils.getCellData(i, 4));
            bodyValue.put("MobileNumber", String.valueOf(excelUtils.getCellData(i, 5)));
            bodyValue.put("Referral", excelUtils.getCellData(i, 6));
            bodyValue.put("ReferralCode", String.valueOf(excelUtils.getCellData(i, 7)));
            POST(ReadData.getInstance().getData("ApiBaseURL"), ReadData.getInstance().getData("apiEndPoint"), headerValue, bodyValue);
        }
    }

    @AfterClass
    public static void afterMethodTearUp() {
        log.info("Test Api Automation Completed !!!!");
    }
}
