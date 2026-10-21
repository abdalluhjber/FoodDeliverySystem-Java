@echo off
echo ===================================================
echo Running Food Delivery System Main Application...
echo ===================================================

java -cp bin com.fooddelivery.Main

echo.
echo ===================================================
echo Running Automated Unit Tests...
echo ===================================================

java -cp bin com.fooddelivery.FoodDeliverySystemTest

pause
