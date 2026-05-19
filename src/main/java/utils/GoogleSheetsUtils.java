package utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.util.List;


public class GoogleSheetsUtils {

    public static final String APPLICATION_NAME = "Selenium Framework";

    public static Object[][] getSheetData(String spreadsheetId, String range) throws Exception{
        // Cargar credenciales JSON de la cuenta de servicio
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/test/resources/credentials.json"));

        credentials = credentials.createScoped(List.of("https://www.googleapis.com/auth/spreadsheets.readonly"));

        Sheets service = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        )
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

        List<List<Object>> values = response.getValues();

        int rows = values.size() - 1;
        int cols = values.get(0).size();

        Object[][] data = new Object[rows][cols];

        for (int i=1; i < values.size(); i++){
            for (int j=0; j < cols; j++){
                data[i-1][j] = values.get(i).get(j).toString();
            }
        }
        return data;
    }
}
