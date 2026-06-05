# Starts the API with bundled Maven wrapper (no global mvn required).
$jdk = Get-ChildItem "C:\Program Files\Microsoft" -Filter "jdk*" -Directory -ErrorAction SilentlyContinue |
  Sort-Object Name -Descending |
  Select-Object -First 1

if (-not $jdk) {
  Write-Error "JDK 17 not found. Install with: winget install -e --id Microsoft.OpenJDK.17"
  exit 1
}

$env:JAVA_HOME = $jdk.FullName
Set-Location $PSScriptRoot
& .\mvnw.cmd spring-boot:run
