@echo off
echo ===================================================
echo Compiling Food Delivery System...
echo ===================================================

if not exist bin mkdir bin

javac -d bin src/main/java/com/fooddelivery/*.java src/test/java/com/fooddelivery/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation succeeded! Output stored in bin/
) else (
    echo Compilation failed!
)
pause
