# Script para instalar Maven automáticamente en Windows
# Uso: Ejecutar en PowerShell

Write-Host "=== INSTALADOR AUTOMÁTICO DE MAVEN ===" -ForegroundColor Green
Write-Host "=====================================`n" -ForegroundColor Green

# Verificar si ya está instalado
Write-Host "1. Verificando instalación existente..." -ForegroundColor Yellow
try {
    $mvnVersion = mvn --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Maven ya está instalado:" -ForegroundColor Green
        Write-Host $mvnVersion
        exit 0
    }
} catch {
    Write-Host "❌ Maven no está instalado, procediendo con instalación..." -ForegroundColor Yellow
}

# Descargar Maven
Write-Host "`n2. Descargando Maven..." -ForegroundColor Yellow
$url = "https://downloads.apache.org/maven/maven-3/3.8.9/binaries/apache-maven-3.8.9-bin.zip"
$tempDir = $env:TEMP
$zipFile = Join-Path $tempDir "apache-maven-3.8.9-bin.zip"
$mavenDir = Join-Path $env:USERPROFILE "Maven"

try {
    Invoke-WebRequest -Uri $url -OutFile $zipFile -UseBasicParsing
    Write-Host "✅ Maven descargado exitosamente" -ForegroundColor Green
} catch {
    Write-Host "❌ Error al descargar Maven: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Intenta descargar manualmente desde: $url" -ForegroundColor Yellow
    exit 1
}

# Extraer Maven
Write-Host "`n3. Extrayendo Maven..." -ForegroundColor Yellow
try {
    if (!(Test-Path $mavenDir)) {
        New-Item -ItemType Directory -Path $mavenDir -Force | Out-Null
    }
    Expand-Archive -Path $zipFile -DestinationPath $mavenDir -Force
    Write-Host "✅ Maven extraído en: $mavenDir" -ForegroundColor Green
} catch {
    Write-Host "❌ Error al extraer Maven: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Configurar PATH
Write-Host "`n4. Configurando variables de entorno..." -ForegroundColor Yellow
$mavenBinPath = Join-Path $mavenDir "apache-maven-3.8.9\bin"

# Agregar al PATH del usuario
$currentPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($currentPath -notlike "*$mavenBinPath*") {
    $newPath = $currentPath + ";$mavenBinPath"
    [Environment]::SetEnvironmentVariable("Path", $newPath, "User")
    Write-Host "✅ PATH configurado para el usuario actual" -ForegroundColor Green
} else {
    Write-Host "ℹ️  PATH ya estaba configurado" -ForegroundColor Cyan
}

# Agregar MAVEN_HOME
[Environment]::SetEnvironmentVariable("MAVEN_HOME", (Join-Path $mavenDir "apache-maven-3.8.9"), "User")
Write-Host "✅ Variable MAVEN_HOME configurada" -ForegroundColor Green

# Limpiar archivo temporal
Write-Host "`n5. Limpiando archivos temporales..." -ForegroundColor Yellow
try {
    Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
    Write-Host "✅ Archivos temporales eliminados" -ForegroundColor Green
} catch {
    Write-Host "ℹ️  No se pudieron eliminar archivos temporales" -ForegroundColor Cyan
}

# Verificar instalación
Write-Host "`n6. Verificando instalación..." -ForegroundColor Yellow
Write-Host "Reinicia PowerShell y ejecuta: mvn --version" -ForegroundColor Cyan
Write-Host "`nO ejecuta ahora mismo en una nueva ventana de PowerShell:" -ForegroundColor Cyan
Write-Host "mvn --version" -ForegroundColor White

Write-Host "`n🎉 INSTALACIÓN COMPLETADA!" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host "`n📝 NOTAS IMPORTANTES:" -ForegroundColor Yellow
Write-Host "• Reinicia PowerShell para que los cambios surtan efecto" -ForegroundColor White
Write-Host "• Si aún no funciona, ejecuta PowerShell como Administrador" -ForegroundColor White
Write-Host "• Para verificar: mvn --version" -ForegroundColor White
Write-Host "`n🚀 Una vez instalado, puedes ejecutar:" -ForegroundColor Green
Write-Host "mvn clean test" -ForegroundColor White
