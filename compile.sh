#!/bin/sh
# Define some constants
ONSSERVER=ONSServer
PROJECT_PATH=/home/jack/workspace/FavUrlResolver
JAR_PATH=$PROJECT_PATH/libs
BIN_PATH=$PROJECT_PATH/bin
SRC_PATH=$PROJECT_PATH/src

# First remove the sources.list file if it exists and then create the sources file of the project
# rm -f $SRC_PATH/sources
# find $SRC_PATH/com -name *.java > $SRC_PATH/sources.list

# First remove the ONSServer directory if it exists and then create the bin directory of ONSServer
# rm -rf $BIN_PATH/$ONSSERVER
# mkdir $BIN_PATH/$ONSSERVER

# Compile the project
$JAR_PATHS=$JAR_PATH/commons-beanutils-1.8.0.jar
javac -classpath $JAR_PATHS