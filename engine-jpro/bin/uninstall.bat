CHCP 65001
@echo off
TITLE Uninstall JPro Service

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
echo Service status: %VAR%
if "%VAR%"=="SERVICE_RUNNING" (
  echo %SERVICENAME% is running, will be stopped first.
  goto stop
)
if "%VAR%"=="SERVICE_PAUSED" (
  echo %SERVICENAME% is paused, will be stopped first.
  goto stop
)
if "%VAR%"=="SERVICE_START_PENDING" (
  echo %SERVICENAME% is running, will be stopped first.
  goto stop
)
if "%VAR%"=="SERVICE_STOPPED" (
  echo %SERVICENAME% is stopped.
  goto uninstall
)
if "%VAR%"=="" (
  echo %SERVICENAME% is not installed. 
  goto exit
) else {
  echo Service state is %VAR%, trying to stop and uninstall.
  goto stop
}
echo This should never happen
goto exit

:stop

cd /D "%DIR%"
call stop.bat

goto uninstall

:uninstall

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

%NSSM% remove %SERVICENAME% confirm

goto end

:exit
echo Press any key to exit.
pause >nul
goto finally

:end

pause

:finally
endLocal
