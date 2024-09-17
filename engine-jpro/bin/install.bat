CHCP 65001
@echo off
TITLE Install JPro Service

net session >nul 2>&1
if %errorLevel% == 0 (
  echo Administrative permissions confirmed.
) else (
  echo -------------------------------------------------------------------
  echo. 
  echo !!! Please restart this script with administrative permissions. !!!
  echo. 
  echo -------------------------------------------------------------------
  goto exit
)

setLocal

SET DIR=%~dp0
cd /D "%DIR%"
cd ..

reg Query "HKLM\Hardware\Description\System\CentralProcessor\0" | find /i "x86" > NUL && set OS=32BIT || set OS=64BIT
if %OS%==32BIT (
  echo This is a 32bit operating system
  set NSSM="%DIR%win32\nssm.exe"
)
if %OS%==64BIT (
  echo This is a 64bit operating system
  set NSSM="%DIR%win64\nssm.exe"
)

echo Application directory: %cd%
echo Arguments: %*

echo Checking service state...
set SERVICENAME=JProServer

for /f "tokens=1" %%a in ('%NSSM% status %SERVICENAME% ^| findstr SERVICE') do set VAR=%%a
if "%VAR%"=="SERVICE_RUNNING" (
  echo %SERVICENAME% is already installed and running.
  goto exit
)
if "%VAR%"=="SERVICE_STOPPED" (
  echo %SERVICENAME% is already installed and stopped.
  goto exit
)
if "%VAR%"=="" (
  echo %SERVICENAME% is not installed. Installing.
  goto install
)
rem SERVICE_PAUSED
rem SERVICE_START_PENDING
echo Service is in state: %VAR% (Script now exit.)
goto exit

:install

set CLASSPATH=libs/*;jfx/win/*
rem echo CLASSPATH=%CLASSPATH%

set JPROCLASSPATHFILE=%DIR%jprolibs.txt
rem echo JPROCLASSPATHFILE=%JPROCLASSPATHFILE%

echo Collecting JPro libraries...

if exist "%JPROCLASSPATHFILE%" (
  del "%JPROCLASSPATHFILE%"
)

setLocal EnableDelayedExpansion
set ISFIRST=
for /R ./jprolibs %%a in (*.jar) do (
  rem echo %%a
  if defined ISFIRST (
    echo|SET /P =";%%a">>"%JPROCLASSPATHFILE%"
  ) else (
    echo|SET /P ="%%a">>"%JPROCLASSPATHFILE%"
  )

  set ISFIRST=n
)

set JPRO_ARGS="-Djpro.applications.default=edu.austral.dissis.chess.ui.ChessGameApplication" "-Dfile.encoding=UTF-8" "-Dprism.useFontConfig=false" "-Dhttp.port=8080" "-Dquantum.renderonlysnapshots=true" "-Dglass.platform=Monocle" "-Dmonocle.platform=Headless" "-Dcom.sun.javafx.gestures.scroll=true" "-Dprism.fontdir=fonts/" "-Djpro.deployment=GRADLE-Distribution"
set JAVA_EXE=%JAVA_HOME%/bin/java.exe
rem echo JPRO_ARGS=%JPRO_ARGS%


%NSSM% install %SERVICENAME% "%JAVA_EXE%" "%JPRO_ARGS% -Djprocpfile=""%JPROCLASSPATHFILE%"" %* -cp %CLASSPATH% com.jpro.boot.JProBoot"
%NSSM% set %SERVICENAME% DisplayName "JPro Server"
%NSSM% set %SERVICENAME% AppDirectory "%cd%"
%NSSM% set %SERVICENAME% AppRestartDelay 1000
%NSSM% set %SERVICENAME% Description "JPro project as windows service"
%NSSM% set %SERVICENAME% Start SERVICE_DEMAND_START
%NSSM% set %SERVICENAME% AppStdout "%DIR%service.log"
%NSSM% set %SERVICENAME% AppStderr "%DIR%service-error.log"

echo %SERVICENAME% has been installed
rem timeout /t 2 /nobreak

goto end

:exit
echo Press any key to exit.
pause >nul
goto finally

:end

pause

:finally
endLocal