CHCP 65001
@echo off
TITLE Stop JPro Service

setLocal

SET DIR=%~dp0
cd /D "%DIR%"
cd ..

SET PIDFILE="%cd%\RUNNING_PID"

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
echo Servicename: %SERVICENAME%

setlocal EnableDelayedExpansion

for /f "tokens=1" %%a in ('%NSSM% status %SERVICENAME% ^| findstr SERVICE') do set VAR=%%a
echo Service status: %VAR%
if "!VAR!"=="SERVICE_RUNNING" (
  echo %SERVICENAME% service is running, will be stopped.
  goto stop
)
if "!VAR!"=="SERVICE_STOPPED" (
  echo %SERVICENAME% service is already stopped.
  goto finally
)
if "!VAR!"=="SERVICE_PAUSED" (
  echo %SERVICENAME% service is paused, will be stopped.
  goto stop
)
if "!VAR!"=="SERVICE_START_PENDING" (
  echo %SERVICENAME% service is pending to start, try to stop.
  goto stop
)
if "!VAR!"=="" (
  echo %SERVICENAME% service is not installed.
  goto exit
)
rem SERVICE_PAUSED
rem SERVICE_START_PENDING
rem echo Service is in state (exiting): !VAR!
rem goto exit

echo Service is in state: %VAR% (Trying to stop)
gogo stop


:stop

net session >nul 2>&1
if !errorLevel! == 0 (
  echo Administrative permissions confirmed.
) else (
  echo -------------------------------------------------------------------
  echo. 
  echo !!! Please restart this script with administrative permissions. !!!
  echo. 
  echo -------------------------------------------------------------------
  goto exit
)

rem %NSSM% stop %SERVICENAME%
sc stop %SERVICENAME%
timeout /t 4 /nobreak
sc query %SERVICENAME%

goto end

:exit
rem echo Press any key to exit.
rem pause >nul
goto finally

:end
echo Statuslevel: %ERRORLEVEL%

rem NetStat -na | Find ":8080" >Nul && (
rem   echo Port number 8080 is occupied. PID file will not be deleted.
rem   echo Application instance might be running, not a service.
rem   goto finally
rem )

if !ERRORLEVEL!==0 (
  if exist !PIDFILE! (
    del !PIDFILE!
    echo Pid file !PIDFILE! deleted.
  ) else (
    echo Pid file not exists, process might successfully deleted when it stopped.
  )
) else (
  if exist !PIDFILE! (
    echo. 
    echo. 
    echo ----------------------------------------------------------------
    echo Pid file is not deleted, because service could not be stopped.
    echo Maybe it runs with Administrator privilegs.
    echo Please try to start this script in administrator mode.  
    echo ----------------------------------------------------------------
  )
)

:finally
if "%1"=="restart" (
  echo ----------------------------------------------------------------
) else (
  pause
)

endLocal