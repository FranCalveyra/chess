$OutputEncoding = [System.Text.Encoding]::UTF8
Write-Host "Start JPro application"

$SERVICENAME = "JProServer"

$DIR = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location -Path $DIR
Set-Location -Path ".."

Write-Host "Application directory: $DIR"
Write-Host "Arguments: $args"

$CLASSPATH = "libs/*;jfx/win/*"
$JPROCLASSPATHFILE = "$DIR/jprolibs.txt"

Write-Host "Collecting JPro libraries..."
if (Test-Path -Path $JPROCLASSPATHFILE) { Remove-Item -Path $JPROCLASSPATHFILE }

$JPRO_LIBS = Get-ChildItem -Recurse -Path "./jprolibs" -Filter "*.jar" | ForEach-Object { $_.FullName }
($JPRO_LIBS -join ";") | Out-File -FilePath $JPROCLASSPATHFILE -Encoding UTF8 -NoNewline

$JPRO_ARGS = "-Djpro.applications.default=edu.austral.dissis.chess.ui.ChessGameApplication","-Dfile.encoding=UTF-8","-Dprism.useFontConfig=false","-Dhttp.port=8080","-Dquantum.renderonlysnapshots=true","-Dglass.platform=Monocle","-Dmonocle.platform=Headless","-Dcom.sun.javafx.gestures.scroll=true","-Dprism.fontdir=fonts/","-Djpro.deployment=GRADLE-Distribution"
Write-Host "JPro arguments: $JPRO_ARGS"
$JAVA_EXE = "$env:JAVA_HOME/bin/java.exe"
$JAVA_ARGS = "$JPRO_ARGS -Djprocpfile=$JPROCLASSPATHFILE $args -cp $CLASSPATH com.jpro.boot.JProBoot"

Write-Host "JPro will be started."
Start-Process -FilePath $JAVA_EXE -NoNewWindow -Wait -ArgumentList $JAVA_ARGS
