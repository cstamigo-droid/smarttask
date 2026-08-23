@echo off
chcp 65001 >nul
cd /d "%~dp0"
title SmartTask - Demo 2
java -jar dist\SmartTask.jar < CAPTURASscenario_B.txt
echo.
pause
