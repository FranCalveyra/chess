CHCP 65001
@echo off
TITLE Restart JPro Server

setLocal

SET DIR=%~dp0
cd /D "%DIR%"

call stop.bat restart

cd /D "%DIR%"
call start.bat %*

endLocal