# 🚀 RahulTraining - Selenium + TestNG + Jenkins CI/CD

> Proyecto de automatización Selenium con TestNG integrado con **CI/CD automático en Jenkins**. Cada push a GitHub dispara automáticamente la ejecución de tests.

[![Jenkins](https://img.shields.io/badge/CI%2FCD-Jenkins-red)](https://www.jenkins.io/)
[![GitHub](https://img.shields.io/badge/VCS-GitHub-black)](https://github.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-orange)](https://maven.apache.org/)
[![Selenium](https://img.shields.io/badge/Framework-Selenium-green)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/Test-TestNG-blue)](https://testng.org/)

---

## 📋 Tabla de Contenidos

- [⚡ Quick Start](#-quick-start)
- [📚 Documentación](#-documentación)
- [🏗️ Arquitectura](#️-arquitectura)
- [🛠️ Configuración Local](#️-configuración-local)
- [🚀 Configuración Jenkins](#-configuración-jenkins)
- [📖 Estructura del Proyecto](#-estructura-del-proyecto)
- [🧪 Ejecutar Tests](#-ejecutar-tests)
- [📊 Reportes](#-reportes)
- [📸 Screenshots + Allure](#-screenshots--allure-new) ← **NUEVO**

---

## ⚡ Quick Start

### Opción 1: Setup Automático (Recomendado)

```powershell
# Windows
.\setup-jenkins.ps1

# Linux/Mac
bash setup-jenkins.sh
```

### Opción 2: Setup Manual (5 minutos)

1. **Leer la guía rápida:**
   ```
   📖 Lee: GUIA_JENKINS_RAPIDA.md (15 min)
   ```

2. **Instalar plugins en Jenkins:**
   - GitHub Integration Plugin
   - Pipeline Plugin
   - TestNG Results Plugin

3. **Crear Pipeline en Jenkins:**
   - New Item → Pipeline
   - SCM: Git (tu repositorio)
   - Script Path: `Jenkinsfile`

4. **Crear Webhook en GitHub:**
   - Settings → Webhooks
   - Payload URL: `http://tu-jenkins:8080/github-webhook/`

5. **Probar:**
   ```bash
   git push
   # Verifica que Jenkins ejecute automáticamente
   ```

---

## 📚 Documentación

| Documento | Descripción | Cuándo Leer |
|-----------|-------------|-----------|
| **GUIA_JENKINS_RAPIDA.md** | Guía paso a paso en Español | 👈 **Comienza aquí** |
| **JENKINS_SETUP.md** | Documentación técnica detallada | Setup avanzado |
| **CHEAT_SHEET_JENKINS.md** | Referencia rápida en 1 página | Consulta durante trabajo |
| **JENKINS_JAVA_MAVEN_CONFIG.md** | Configuración de tools | Si hay errores con Maven/Java |
| **ARQUITECTURA_JENKINS_GITHUB.md** | Diagramas y flujos completos | Entender la arquitectura |
| **README_DOCUMENTACION.md** | Índice de todos los docs | Navegar la documentación |

---

## 🏗️ Arquitectura

```
Tu Máquina Local
    ↓ (git push)
GitHub.com
    ↓ (Webhook)
Jenkins Server
    ↓ (Pipeline)
    ├─ Checkout código
    ├─ mvn clean compile
    ├─ mvn test (Selenium + TestNG)
    └─ Publish TestNG Results
    ↓
Jenkins UI (Reportes)
```

**Ver diagrama detallado:** [ARQUITECTURA_JENKINS_GITHUB.md](ARQUITECTURA_JENKINS_GITHUB.md)

---

## 🛠️ Configuración Local

### Requisitos Previos

- **Java 11+**
  ```bash
  java -version
  # openjdk version "11.0.x" or higher
  ```

- **Maven 3.8.1+**
  ```bash
  mvn -version
  # Apache Maven 3.8.1 or higher
  ```

- **Git**
  ```bash
  git --version
  ```

- **Chrome Browser** (para Selenium)
  ```bash
  google-chrome --version
  # or: chromium --version
  ```

### Clonar Repositorio

```bash
git clone https://github.com/tu-usuario/RahulTraining.git
cd RahulTraining
```

### Instalar Dependencias

```bash
mvn clean install
# Descarga todas las dependencias de pom.xml
```

---

## 🚀 Configuración Jenkins

### Paso 1: Instalar Plugins (5 min)

```
Jenkins → Manage Jenkins → Plugin Manager → Available
```

Instala:
- ✅ GitHub Integration Plugin
- ✅ GitHub Plugin
- ✅ Pipeline
- ✅ TestNG Results Plugin

### Paso 2: Crear Credenciales (3 min)

```
Jenkins → Manage Jenkins → Manage Credentials → System → Global
```

Agrega:
- **Kind:** Username with password
- **Username:** tu-usuario-github
- **Password:** [Personal Access Token de GitHub]
- **ID:** github-credentials

### Paso 3: Crear Pipeline Job (5 min)

```
Jenkins → New Item → Pipeline
```

Configuración:
```
Definition: Pipeline script from SCM
SCM: Git
  Repository URL: https://github.com/tu-usuario/RahulTraining.git
  Credentials: github-credentials
  Branch: */main
  Script Path: Jenkinsfile
```

### Paso 4: Crear Webhook en GitHub (2 min)

```
GitHub → Settings → Webhooks → Add webhook
```

Valores:
```
Payload URL:    http://tu-jenkins:8080/github-webhook/
Content type:   application/json
Events:         Just the push event
Active:         ✅
```

### Paso 5: Probar ✅

```bash
git add .
git commit -m "Test Jenkins"
git push origin main
```

Verifica:
1. GitHub → Webhooks → Recent Deliveries → Status ✅
2. Jenkins → RahulTraining-Tests → Build History → Nuevo build

---

## 📖 Estructura del Proyecto

```
RahulTraining/
├── 📄 Jenkinsfile ⭐
│   └─ Configuración del pipeline CI/CD
│
├── 📚 Documentación/
│   ├── GUIA_JENKINS_RAPIDA.md ⭐ (Comienza aquí)
│   ├── JENKINS_SETUP.md
│   ├── CHEAT_SHEET_JENKINS.md
│   ├── JENKINS_JAVA_MAVEN_CONFIG.md
│   ├── ARQUITECTURA_JENKINS_GITHUB.md
│   └── README_DOCUMENTACION.md
│
├── 🔧 Scripts/
│   ├── setup-jenkins.ps1 (Windows)
│   └── setup-jenkins.sh (Linux/Mac)
│
├── 📦 Configuración Maven/TestNG/
│   ├── pom.xml
│   ├── testng.xml
│   └── pom.xml
│
├── 🧪 Test Automation Code/
│   └── src/
│       ├── main/java/com/automation/Main.java
│       └── test/java/
│           ├── base/
│           │   ├── BaseTest.java
│           │   └── ClientBaseTest.java
│           └── tests/
│               ├── ClientPageTest.java ✅ (Mejorado)
│               ├── LoginTest.java
│               └── ChildWindow.java
│
└── 📊 Build Artifacts (auto-generados)/
    └── target/surefire-reports/
        ├── testng-results.xml
        └── testng-reports.html
```

---

## 🧪 Ejecutar Tests

### Ejecutar Todos los Tests Localmente

```bash
mvn clean test
# Ejecuta testng.xml y genera reportes
```

### Ejecutar un Test Específico

```bash
mvn test -Dtest=ClientPageTest
# Solo ejecuta ClientPageTest
```

### Ejecutar Suite Específica

```bash
mvn test -DsuiteXmlFile=testng.xml
# Usa la configuración en testng.xml
```

### Ejecutar Tests en Jenkins

```bash
# Automático al hacer push
git push origin main
# Jenkins detecta y ejecuta mvn test
```

### Saltar Tests en Build Local

```bash
mvn clean install -DskipTests
# Compilar sin ejecutar tests
```

---

## 📊 Reportes

### Ver Reportes Localmente

Después de ejecutar `mvn test`:

```bash
# Abrir reporte HTML
open target/surefire-reports/testng-reports.html

# O en Windows
start target/surefire-reports/testng-reports.html
```

### Ver Reportes en Jenkins

```
Jenkins → RahulTraining-Tests → [Build #]
├─ TestNG Results (gráficas)
├─ Console Output (logs)
└─ Artifacts (descargas)
```

### Acceder a Resultados XML

```
target/surefire-reports/testng-results.xml
```

---

## 📸 Screenshots + Allure

### Integración con Allure

Para mejorar los reportes, integra Allure:

1. **Agregar dependencia en `pom.xml`:**
   ```xml
   <dependency>
       <groupId>io.qameta.allure</groupId>
       <artifactId>allure-testng</artifactId>
       <version>2.13.9</version>
       <scope>test</scope>
   </dependency>
   ```

2. **Configurar en `testng.xml`:**
   ```xml
   <listeners>
       <listener class="io.qameta.allure.testng.AllureTestNg"/>
   </listeners>
   ```

3. **Generar reportes Allure:**
   ```bash
   mvn allure:serve
   ```

### Screenshots en Reportes

- Agrega la anotación `@Step` en métodos:
  ```java
  @Step("Abrir página de login")
  public void openLoginPage() {
      driver.get("http://tu-app/login");
  }
  ```

- Captura de pantalla en caso de fallo:
  ```java
  @AfterMethod
  public void screenshotOnFailure(ITestResult result) {
      if (result.getStatus() == ITestResult.FAILURE) {
          String filePath = "screenshots/" + result.getName() + ".png";
          try {
              TakesScreenshot ts = (TakesScreenshot) driver;
              File source = ts.getScreenshotAs(OutputType.FILE);
              FileUtils.copyFile(source, new File(filePath));
              Allure.addAttachment("Screenshot", new FileInputStream(new File(filePath)));
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
  ```

---

## 🔄 Flujo de Trabajo Diario

```bash
# 1. Escribir/modificar código
# vim src/test/java/tests/ClientPageTest.java

# 2. Probar localmente
mvn test

# 3. Subir a GitHub
git add .
git commit -m "Agregar nuevo test para funcionalidad X"
git push origin main

# 4. Jenkins ejecuta automáticamente ✨
# 5. Ver resultados en Jenkins UI
```

---

## ❓ Preguntas Frecuentes

### P: ¿Qué pasa si no tengo Jenkins instalado?

R: Puedes instalar Jenkins localmente:
- **Windows:** Descargar desde jenkins.io/download
- **Linux:** `sudo apt-get install jenkins`
- **Mac:** `brew install jenkins-lts`

O usar **Jenkins en Docker:**
```bash
docker run -p 8080:8080 jenkins/jenkins:lts
```

### P: ¿Cómo ejecutar un test solo X número de veces?

R: En `testng.xml`, agrega atributo `invocationCount`:
```xml
<test name="ClientPageTest">
    <parameter name="invocationCount" value="5"/>
    <classes>
        <class name="tests.ClientPageTest"/>
    </classes>
</test>
```

O en el test:
```java
@Test(invocationCount = 5)
public void validClientPage(String email, String productName) {
    // Se ejecutará 5 veces
}
```

### P: ¿Qué hacer si el error `NoSuchElementException` aparece?

R: Ya está manejado en el código:
```java
try {
    WebElement element = wait.until(...);
    Assert.assertTrue(element.isDisplayed());
} catch (TimeoutException e) {
    Assert.fail("Elemento no encontrado en el tiempo esperado", e);
} catch (NoSuchElementException e) {
    Assert.fail("Elemento no existe en la página", e);
}
```

### P: ¿Cómo obtener el Personal Access Token de GitHub?

R:
1. GitHub.com → Settings → Developer settings → Personal access tokens
2. Generate new token (classic)
3. Scopes: `repo`, `admin:repo_hook`, `user:email`
4. **Copia el token** (solo aparece una vez)

---

## 🐛 Troubleshooting

| Problema | Solución |
|----------|----------|
| `mvn: command not found` | Instalar Maven o configurar PATH |
| Webhook no entrega | Verificar URL Jenkins es accesible públicamente |
| Tests no ejecutan | Ver Console Output en Jenkins para logs |
| Reportes no se publican | Verificar Jenkinsfile tiene `testng` block |
| JAVA_HOME not set | Configurar en Jenkins → Manage Jenkins → System |

**Ver más:** [JENKINS_JAVA_MAVEN_CONFIG.md](JENKINS_JAVA_MAVEN_CONFIG.md)

---

## 📝 Notas Importantes

⚠️ **URL Jenkins debe ser pública** - GitHub no puede conectar a `http://localhost`

⚠️ **Personal Access Token** - Solo se muestra una vez, guárdalo seguro

⚠️ **Jenkinsfile en raíz** - Debe estar en el directorio raíz del repositorio

⚠️ **Reinicia Jenkins** - Después de instalar plugins

---

## 🎓 Recursos y Referencias

| Recurso | Link |
|---------|------|
| Jenkins Documentation | https://www.jenkins.io/doc/ |
| GitHub Webhooks | https://docs.github.com/en/developers/webhooks-and-events/webhooks |
| Selenium WebDriver | https://www.selenium.dev/documentation/ |
| TestNG | https://testng.org/doc/ |
| Maven | https://maven.apache.org/guides/ |

---

## 📞 Contacto y Soporte

- **Documentación:** Ver archivos `.md` en raíz del repositorio
- **Issues:** GitHub → Issues
- **Wiki:** [GUIA_JENKINS_RAPIDA.md](GUIA_JENKINS_RAPIDA.md)

---

## 📜 Licencia

MIT License - Libre para usar y modificar

---

## ✨ Características Principales

- ✅ **CI/CD Automático** - Tests ejecutan con cada push a GitHub
- ✅ **Reporte TestNG** - Visualización gráfica de resultados
- ✅ **Manejo de Excepciones Robusto** - Tests confiables
- ✅ **Data-Driven Tests** - DataProvider para múltiples casos
- ✅ **Safe Click Methods** - Clicks seguros en Selenium
- ✅ **Documentación Completa** - Guías y referencias

---

## 🚀 Próximos Pasos

1. **Lee:** [GUIA_JENKINS_RAPIDA.md](GUIA_JENKINS_RAPIDA.md) (15 min)
2. **Configura:** Jenkins según los pasos anteriores (20 min)
3. **Prueba:** Haz un `git push` y verifica que Jenkins ejecute (5 min)
4. **Optimiza:** Agrega notificaciones, múltiples branches, etc.

---

**¡A disfrutar de la automatización! 🎉**

Última actualización: **2026-04-07**  
Versión: **1.0.0**  
Autor: **RahulTraining Team**
