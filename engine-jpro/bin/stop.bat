CHCP 65001
@echo off
TITLE Stop JPro application

setLocal

SET DIR=%~dp0
cd /D "%DIR%"
cd ..

SET PIDFILE="%cd%\RUNNING_PID"
setlocal EnableDelayedExpansion

if exist %PIDFILE% (
  SET /P PID=<%PIDFILE%
  echo Killing process !PID!
  taskkill /PID !PID!
  timeout /t 5 /nobreak
  if exist %PIDFILE% (
    echo Now force killing !PID!
    taskkill /PID !PID! /F
  )
)

if exist !PIDFILE! (
  del !PIDFILE!
  echo Pid file !PIDFILE! deleted.
) else (
  echo Pid file not exists, process might successfully deleted when it stopped.
)

endLocal