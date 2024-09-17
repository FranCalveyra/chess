CHCP 65001
@echo off
TITLE Restart JPro Server

setLocal

SET DIR=%~dp0
cd /D "%DIR%"

call stop-service.bat restart

cd /D "%DIR%"
call start-service.bat %*

endLocal
