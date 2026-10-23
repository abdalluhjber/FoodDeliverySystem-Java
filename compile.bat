@echo off
echo Compiling Food Delivery System...

if not exist bin mkdir bin

javac -d bin src/main/java/com/fooddelivery/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation succeeded.
) else (
    echo Compilation failed.
)
