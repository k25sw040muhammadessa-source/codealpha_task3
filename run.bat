@echo off
REM Hotel Reservation System - Compilation & Execution Script
REM This script compiles all Java files and runs the application

REM Set source and output directories
set SRC_DIR=src
set BIN_DIR=bin
set MAIN_CLASS=com.hotel.HotelReservationSystem

REM Create bin directory if it doesn't exist
if not exist %BIN_DIR% mkdir %BIN_DIR%

echo ========================================
echo Hotel Reservation System
echo ========================================
echo.

REM Compile all Java files
echo [1/2] Compiling Java files...
javac -d %BIN_DIR% %SRC_DIR%\com\hotel\*.java ^
                   %SRC_DIR%\com\hotel\model\*.java ^
                   %SRC_DIR%\com\hotel\manager\*.java ^
                   %SRC_DIR%\com\hotel\ui\*.java ^
                   %SRC_DIR%\com\hotel\util\*.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

echo [DONE] Compilation successful!
echo.

REM Run the application
echo [2/2] Launching application...
echo.
java -cp %BIN_DIR% %MAIN_CLASS%

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Execution failed!
    pause
    exit /b 1
)

echo.
echo [DONE] Application closed.
pause
