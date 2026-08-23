@echo off
chcp 65001 >nul
cd /d "%~dp0"
title SmartTask - Demo
java -jar dist\SmartTask.jar < CAPTURASscenario_A.txt
echo.
pause
