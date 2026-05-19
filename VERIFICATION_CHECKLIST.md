# ✅ Checklist de Verificación - Screenshots + Allure

Use este checklist para verificar que todo está correctamente integrado.

## 1. Dependencias Maven ✅

```
[ ] pom.xml contiene:
    [ ] org.testng:testng:7.12.0 (scope=test)
    [ ] io.qameta.allure:allure-testng:2.24.0
    [ ] io.qameta.allure:allure-maven:2.12.0
```

**Verificar con:**
```bash
mvn dependency:tree | grep allure
```

---

## 2. Clases Base (Driver Management) ✅

```
[ ] src/test/java/base/DriverManager.java existe
[ ] DriverManager tiene:
    [ ] ThreadLocal<WebDriver>
    [ ] getDriver()
    [ ] setDriver(driver)
    [ ] quitDriver()

[ ] BaseTest.java tiene:
    [ ] @BeforeMethod llama DriverManager.setDriver()
    [ ] @AfterMethod llama DriverManager.quitDriver()

[ ] ClientBaseTest.java tiene:
    [ ] @BeforeMethod llama DriverManager.setDriver()
    [ ] @AfterMethod llama DriverManager.quitDriver()

[ ] EventHubBaseTest.java tiene:
    [ ] @BeforeMethod llama DriverManager.setDriver()
    [ ] @AfterMethod llama DriverManager.quitDriver()
```

**Verificar compilación:**
```bash
mvn test-compile
```

---

## 3. TestListener ✅

```
[ ] src/test/java/listeners/TestListener.java existe
[ ] TestListener implementa ITestListener
[ ] Contiene métodos:
    [ ] onTestFailure(ITestResult)
        [ ] Llama takeScreenshot()
        [ ] Llama attachScreenshotToAllure()
    [ ] onTestSuccess(ITestResult) [Opcional]
    [ ] takeScreenshot(String)
        [ ] Crea archivo PNG
        [ ] Lo guarda en "target/screenshots/"
    [ ] attachScreenshotToAllure(String, String)
        [ ] Adjunta a Allure con FileInputStream
```

**Verificar con:**
```bash
grep "onTestFailure\|onTestSuccess\|takeScreenshot" src/test/java/listeners/TestListener.java
```

---

## 4. Configuración TestNG ✅

```
[ ] testng.xml contiene:
    [ ] <listeners>
    [ ] <listener class-name="listeners.TestListener"/>
```

**Contenido esperado:**
```xml
<suite name="Selenium Suite">
    <listeners>
        <listener class-name="listeners.TestListener"/>
    </listeners>
    ...
</suite>
```

---

## 5. Jenkinsfile ✅

```
[ ] Jenkinsfile tiene stages:
    [ ] stage('Clean')
        [ ] Ejecuta: mvn clean
    [ ] stage('Run Tests')
        [ ] Ejecuta: mvn test -Dheadless=false

[ ] Jenkinsfile tiene post { always }:
    [ ] junit statement
    [ ] allure statement con:
        [ ] commandline: 'Allure'
        [ ] results: [[path: 'target/allure-results']]
    [ ] archiveArtifacts
```

**Contenido esperado:**
```groovy
post {
    always {
        junit 'target/surefire-reports/*.xml'
        allure commandline: 'Allure', results: [[path: 'target/allure-results']]
        archiveArtifacts artifacts: 'target/**/*', fingerprint: true
    }
}
```

---

## 6. .gitignore ✅

```
[ ] .gitignore existe en raíz del proyecto
[ ] Contiene: target/
```

**Verificar con:**
```bash
cat .gitignore | grep "^target"
```

---

## 7. Tests Compilables ✅

```
[ ] src/test/java/tests/LoginTest.java compila
[ ] src/test/java/tests/LoginTestWithAllure.java compila (NUEVO)
[ ] src/test/java/tests/EventHubTest.java compila
[ ] src/test/java/tests/ClientPageTest.java compila
```

**Verificar con:**
```bash
mvn test-compile
```

Esperado: `BUILD SUCCESS`

---

## 8. Ejecución Local ✅

### 8.1 Ejecutar Tests
```bash
mvn clean test -Dheadless=true
```

**Verificar:**
```
[ ] Build termina con EXIT CODE 0 (sin errores)
[ ] Se crean archivos en target/allure-results/ (JSON)
[ ] Si hay fallos, se crean archivos en target/screenshots/ (PNG)
```

### 8.2 Ver Reporte Localmente
```bash
mvn allure:serve
```

**Verificar:**
```
[ ] Abre navegador automáticamente en localhost:4040
[ ] Muestra "Dashboard" con resumen de tests
[ ] Puedes ver Screenshots en Attachments
[ ] Puedes ver todos los tests ejecutados
```

**Para salir:**
```bash
Ctrl+C en la terminal
```

---

## 9. Integración Jenkins ✅

### 9.1 Ejecutar Job desde Jenkins

```
[ ] Ve a Jenkins → Tu Job
[ ] Haz clic en "Build Now"
[ ] Espera a que termine el build
```

### 9.2 Verificar Resultados

```
[ ] Build completado (verde o rojo según tests)
[ ] En la página del build, menú izquierdo ve "Allure Report"
[ ] Haz clic en "Allure Report" → Se abre el reporte
[ ] En el reporte ves los tests ejecutados
[ ] Si hay fallos, ves Screenshots en Attachments
```

### 9.3 Si NO ves "Allure Report" en Jenkins

```
[ ] Verifica que el plugin de Allure esté instalado:
    Manage Jenkins → Manage Plugins → Busca "Allure"
    Debe estar instalado y habilitado

[ ] En Consola del Build revisa errores:
    Build → Console Output
    Busca: "Allure report"

[ ] Verifica que Jenkinsfile tenga la línea:
    allure commandline: 'Allure', results: [[path: 'target/allure-results']]
```

---

## 10. Verificación de Archivos Generados ✅

Después de ejecutar tests, verifica que existen:

```bash
# En tu máquina local
C:\Users\User\IdeaProjects\RahulTraining\target\

[ ] allure-results/           (JSON files - siempre)
    [ ] *.json files
    
[ ] allure-report/            (HTML files - después de mvn allure:serve)
    [ ] index.html
    
[ ] screenshots/              (PNG files - solo si hay fallos)
    [ ] *.png files
    
[ ] surefire-reports/         (XML files - siempre)
    [ ] TEST-*.xml
```

**Comando para listar:**
```powershell
# En Windows PowerShell
Get-ChildItem -Path "C:\Users\User\IdeaProjects\RahulTraining\target" -Recurse
```

---

## 11. Prueba con Fallo Intencional ✅

Para verificar que los screenshots se generan en fallos:

### Paso 1: Modificar un test
Edita `LoginTest.java`:
```java
// Línea aproximadamente 45
Assert.assertEquals(cards.size(), 10);  // ← Cambia 10 a 5
```

### Paso 2: Ejecutar tests
```bash
mvn clean test -Dheadless=true
```

### Paso 3: Verificar
```
[ ] Test falla (esperado)
[ ] Se crea archivo PNG en target/screenshots/
[ ] El reporte de Allure muestra: FAILED
[ ] En Attachments ves el Screenshot
```

### Paso 4: Revertir cambio
```java
Assert.assertEquals(cards.size(), 10);  // ← Vuelve a 10
```

---

## 12. Anotaciones Allure (Opcional) ✅

Para mejorar los reportes:

```
[ ] Tests usan @Epic (Categoría general)
[ ] Tests usan @Feature (Funcionalidad)
[ ] Tests usan @Description (Descripción)
[ ] Métodos @Step (documentan pasos)

Ejemplo en: src/test/java/tests/LoginTestWithAllure.java
```

---

## Resumen Estado ✅

| Componente | Estado | Verificado |
|-----------|--------|-----------|
| DriverManager | ✅ Implementado | [ ] |
| BaseTests | ✅ Actualizado | [ ] |
| TestListener | ✅ Mejorado | [ ] |
| testng.xml | ✅ Configurado | [ ] |
| pom.xml | ✅ Configurado | [ ] |
| Jenkinsfile | ✅ Actualizado | [ ] |
| .gitignore | ✅ Correcto | [ ] |
| Compilación | ✅ Success | [ ] |
| Ejecución Local | ✅ Funciona | [ ] |
| Jenkins Integration | ✅ Funciona | [ ] |
| Screenshots en Fallos | ✅ Automático | [ ] |
| Reporte Allure | ✅ Visible | [ ] |

---

## 🎯 Si Algo No Funciona

### Error: "No se toman screenshots"
```
❌ Causa: DriverManager.getDriver() retorna null
✅ Solución: Verifica que @BeforeMethod llama DriverManager.setDriver(driver)
```

### Error: "TestListener no ejecuta"
```
❌ Causa: Listener no está registrado en testng.xml
✅ Solución: Agrega <listener class-name="listeners.TestListener"/> en testng.xml
```

### Error: "Allure Report no aparece en Jenkins"
```
❌ Causa: Plugin no instalado o configuración incompleta
✅ Solución: 
   1. Instala: Manage Jenkins → Manage Plugins → Allure Jenkins Plugin
   2. Agrega: allure commandline: 'Allure', results: [[path: 'target/allure-results']]
   3. Re-ejecuta el build
```

### Error: "target/screenshots vacío después de fallos"
```
❌ Causa: Screenshot se toma pero Allure.addAttachment falla
✅ Solución: Verifica FileInputStream y permisos de archivos
```

---

## ✨ ¡Éxito!

Si todas las verificaciones pasan ✅, tu integración de Screenshots + Allure está lista para usar en Jenkins.

**Próximo paso:** Ejecuta un build en Jenkins y revisa el Allure Report. 🚀

