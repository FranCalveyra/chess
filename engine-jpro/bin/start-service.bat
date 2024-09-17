CHCP 65001
@echo off
TITLE Start JPro Service

setLocal

set SERVICENAME=JProServer

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
echo Service status: %VAR%
if "%VAR%"=="SERVICE_RUNNING" (
  echo %SERVICENAME% service is already running please stop first.
  goto exit
)
if "%VAR%"=="SERVICE_STOPPED" (
  echo %SERVICENAME% service is stopped. Starting service.
  goto start_service
)
if "%VAR%"=="" (
  echo %SERVICENAME% service is not installed. Please install first.
  goto end
)
rem SERVICE_PAUSED
rem SERVICE_START_PENDING
rem echo Service is in state: %VAR% (Script now exit)
rem goto exit

echo Service is in state: %VAR% (Trying to start)
gogo start_service

:start_service

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

echo %SERVICENAME% will be started in the background.
rem %NSSM% start %SERVICENAME%
sc start %SERVICENAME%
timeout /t 4 /nobreak
sc query %SERVICENAME%

goto end

:exit
echo Press any key to exit.
pause >nul
goto finally

:end

pause

:finally
endLocal