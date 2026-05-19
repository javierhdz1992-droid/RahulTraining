package dataProviders;

import org.testng.annotations.DataProvider;
import utils.GoogleSheetsUtils;

public class TestDataProvider {

    @DataProvider(name = "testData")
    public Object[][] getData() throws Exception{
        String spreadsheetId = "13e0DpIj2RIFNsV3gu-BmnmnEWai77fMbs0j1yUI0NSo";
        String range = "Hoja 1!A1:C11";

        try {
            return GoogleSheetsUtils.getSheetData(spreadsheetId, range);
        } catch (Exception e) {
            System.err.println("[WARN] No se pudo leer Google Sheets. Usando datos por defecto. Error: " + e.getMessage());
            return new Object[][]{
                    {"TestingEmail@gmail.com", "Admin2026*", "ZARA COAT 3"}
            };
        }
    }
}
