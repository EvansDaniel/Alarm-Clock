#!/bin/bash

cd Alarm-Clock

PATH_TO_FX=../javafx-sdk-11/lib
PATH_TO_JDK=../jdk-11.0.16.1+1/Contents/Home/bin

$PATH_TO_JDK/javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.swing,javafx.graphics,javafx.base AlarmClock.java Clock.java Sound.java
$PATH_TO_JDK/java --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.swing,javafx.graphics,javafx.base AlarmClock
