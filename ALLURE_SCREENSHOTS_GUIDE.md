# Guía: Integración de Screenshots con Allure

## ¿Cómo funcionan los Screenshots en Allure?

### 1. **Toma Automática de Screenshots en Fallos**

Cuando un test falla, `TestListener.java` automáticamente:
- Toma un screenshot del estado actual del navegador
- Lo guarda en `target/screenshots/`
- Lo adjunta al reporte de Allure

**Archivos involucrados:**
- `src/test/java/listeners/TestListener.java` - Implementa `ITestListener` de TestNG
- `testng.xml` - Registra el listener
- `src/test/java/base/DriverManager.java` - Proporciona acceso al driver

### 2. **Flujo de Funcionamiento**

```
Test falla
   ↓
TestListener.onTestFailure() se dispara
   ↓
takeScreenshot() toma una captura del navegador
   ↓
Archivo PNG se guarda en target/screenshots/
   ↓
attachScreenshotToAllure() adjunta la imagen a Allure
   ↓
Se ve en el reporte de Allure bajo "Attachments"
```

### 3. **Usos Actuales**

#### ✅ **Automático al Fallar**
El siguiente código ya está implementado:
```java
@Override
public void onTestFailure(ITestResult result) {
    String testName = result.getName();
    String screenshotPath = takeScreenshot(testName);
    if (screenshotPath != null) {
        attachScreenshotToAllure(screenshotPath, "Screenshot on Failure");
    }
}
```

#### ✅ **Automático al Tener Éxito**
También está implementado (opcional):
```java
@Override
public void onTestSuccess(ITestResult result) {
    String testName = result.getName();
    String screenshotPath = takeScreenshot(testName);
    if (screenshotPath != null) {
        attachScreenshotToAllure(screenshotPath, "Screenshot on Success");
    }
}
```

### 4. **Cómo Tomar Screenshots Manuales en Pasos Específicos**

Si quieres tomar screenshots en pasos específicos de tu test, puedes hacer esto:

```java
// En tus tests (LoginTest, EventHubTest, etc.)
import listeners.TestListener;

@Test
public void verifyLoginSuccess() {
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys("rahulshettyacademy");
    
    // Tomar screenshot después de ingresar usuario
    TestListener testListener = new TestListener();
    testListener.takeScreenshot("step_2_username_entered");
    
    // Adjuntar a Allure
    try (FileInputStream fis = new FileInputStream("target/screenshots/step_2_username_entered_*.png")) {
        Allure.addAttachment("Username Entered", "image/png", fis, ".png");
    }
    
    // Continuar con el test...
}
```

### 5. **Estructura de Archivos**

```
target/
├── allure-results/          # Resultados de pruebas (JSON)
├── allure-report/           # Reporte HTML generado
└── screenshots/             # Screenshots en PNG
    ├── verifyLoginSuccess_1234567890.png
    ├── verifyLoginError_1234567891.png
    └── UIElements_1234567892.png
```

### 6. **Ver Screenshots en el Reporte de Allure**

1. **Localmente:**
   ```bash
   mvn allure:serve
   ```
   Abre el reporte en tu navegador (típicamente http://localhost:4040)

2. **En Jenkins:**
   - Accede al build en Jenkins
   - En el menú lateral, busca "Allure Report"
   - Haz clic en el test que falló
   - En la sección "Attachments", verás los screenshots

### 7. **Configuración en testng.xml**

El listener está registrado en `testng.xml`:

```xml
<suite name="Selenium Suite">
    <listeners>
        <listener class-name="listeners.TestListener"/>
    </listeners>
    ...
</suite>
```

Esto asegura que se ejecute automáticamente para todos los tests.

### 8. **Métodos Disponibles en TestListener**

| Método | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| `takeScreenshot(testName)` | Toma un screenshot del driver actual | Manualmente o por listeners |
| `onTestFailure(result)` | Se dispara cuando falla un test | Automático por TestNG |
| `onTestSuccess(result)` | Se dispara cuando pasa un test | Automático por TestNG |
| `attachScreenshotToAllure(path, name)` | Adjunta screenshot a Allure | Internamente en los listeners |

### 9. **Verificación de Funcionamiento**

Ejecuta un test con un fallo intencional:

```bash
mvn test -Dheadless=true
```

Luego verifica:
- ✅ Archivos PNG en `target/screenshots/`
- ✅ Archivos JSON en `target/allure-results/`
- ✅ Ver reporte con `mvn allure:serve`

### 10. **Troubleshooting**

| Problema | Causa | Solución |
|----------|-------|----------|
| No se toman screenshots | `DriverManager.getDriver()` retorna null | Asegúrate de que `DriverManager.setDriver()` se llama en `@BeforeMethod` |
| Screenshots no aparecen en Allure | Listener no está registrado | Verifica que `testng.xml` tenga el `<listener>` correcto |
| Errores de IO al guardar | Permisos de carpeta | Verifica que `target/` tenga permisos de escritura |
| Screenshots vacíos/oscuros | Browser en headless | Aumenta el tiempo de espera o desactiva headless mode |

### 11. **Mejores Prácticas**

✅ **Hacer:**
- Usar `takeScreenshot()` en pasos críticos
- Incluir timestamp en nombres para evitar sobrescrituras
- Revisar screenshots en fallos para debugging rápido
- Adjuntar screenshots a Allure para documentación

❌ **No hacer:**
- Tomar screenshots en cada línea (ralentiza tests)
- Ignorar archivos de screenshot en Git (ya está en `.gitignore`)
- Comprimir o modificar archivos en `target/`

---

**Resumen:** Los screenshots están completamente integrados con Allure. Se generan automáticamente en fallos y éxitos, se adjuntan al reporte, y están visibles en Jenkins. 🎉

