#!/bin/bash
# Hotel Reservation System - Compilation & Execution Script (Unix/Linux/Mac)
# This script compiles all Java files and runs the application

# Set source and output directories
SRC_DIR="src"
BIN_DIR="bin"
MAIN_CLASS="com.hotel.HotelReservationSystem"

# Create bin directory if it doesn't exist
mkdir -p $BIN_DIR

echo "========================================"
echo "Hotel Reservation System"
echo "========================================"
echo ""

# Compile all Java files
echo "[1/2] Compiling Java files..."
javac -d $BIN_DIR \
    $SRC_DIR/com/hotel/*.java \
    $SRC_DIR/com/hotel/model/*.java \
    $SRC_DIR/com/hotel/manager/*.java \
    $SRC_DIR/com/hotel/ui/*.java \
    $SRC_DIR/com/hotel/util/*.java

if [ $? -ne 0 ]; then
    echo "[ERROR] Compilation failed!"
    exit 1
fi

echo "[DONE] Compilation successful!"
echo ""

# Run the application
echo "[2/2] Launching application..."
echo ""
java -cp $BIN_DIR $MAIN_CLASS

if [ $? -ne 0 ]; then
    echo "[ERROR] Execution failed!"
    exit 1
fi

echo ""
echo "[DONE] Application closed."
