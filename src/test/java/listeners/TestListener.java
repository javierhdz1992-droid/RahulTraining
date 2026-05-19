package listeners;

import base.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private static final String SCREENSHOTS_PATH = "target/screenshots/";

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        String screenshotPath = takeScreenshot(testName);
        
        if (screenshotPath != null) {
            attachScreenshotToAllure(screenshotPath, "Screenshot on Failure");
            System.out.println("Screenshot adjuntado a Allure para test fallido: " + testName);
        } else {
            System.out.println("No se pudo tomar screenshot para el test: " + testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        String screenshotPath = takeScreenshot(testName);
        
        if (screenshotPath != null) {
            attachScreenshotToAllure(screenshotPath, "Screenshot on Success");
            System.out.println("Screenshot adjuntado a Allure para test exitoso: " + testName);
        }
    }

    /**
     * Toma un screenshot del driver actual y lo guarda en la carpeta de screenshots
     * @param testName Nombre del test (se usa para nombrar el archivo)
     * @return Ruta absoluta del archivo de screenshot o null si falla
     */
    public String takeScreenshot(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            
            if (driver == null) {
                System.out.println("No hay driver disponible para tomar screenshot");
                return null;
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get(SCREENSHOTS_PATH));
            
            String filename = testName + "_" + System.currentTimeMillis() + ".png";
            String path = SCREENSHOTS_PATH + filename;
            Files.copy(src.toPath(), Paths.get(path));
            
            System.out.println("Screenshot guardado en: " + path);
            return path;
            
        } catch (Exception e) {
            System.out.println("Error al tomar screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adjunta el screenshot al reporte de Allure
     * @param screenshotPath Ruta del archivo screenshot
     * @param attachmentName Nombre del attachment en Allure
     */
    private void attachScreenshotToAllure(String screenshotPath, String attachmentName) {
        try (FileInputStream fis = new FileInputStream(screenshotPath)) {
            Allure.addAttachment(attachmentName, "image/png", fis, ".png");
        } catch (IOException e) {
            System.out.println("Error al adjuntar screenshot a Allure: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
