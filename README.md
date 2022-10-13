# Alarm-Clock-

Author - Daniel Evans 

What is it?
-------------
A useful Alarm Clock application that informs the user of the current date and time (CST), updates the time dynamically, and plays music when alarm is set to go off. It is a simple yet effective alarm clock application that uses JavaFX to power the GUI. 

 How to run
-------------
If you are running mac, you can run install.sh and then execute.sh directly

Alternatively you can follow something like these instructions
________________________________________________________________
I know that this supports java 11 and javafx 11 as is. I'm not sure about other versions. I also tested this on mac so the links below are for mac

To download java 11, go here: https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.16.1%2B1/OpenJDK11U-jdk_x64_mac_hotspot_11.0.16.1_1.tar.gz

To download javafx 11, go here: https://download2.gluonhq.com/openjfx/11/openjfx-11_osx-x64_bin-sdk.zip

To compile:
Unzip jfx zip file and run:
`PATH_TO_FX=/path/to/openjfx/lib`

Then run:
```
/path/to/java11/Contents/Home/bin/javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.swing,javafx.graphics,javafx.base AlarmClock.java Clock.java Sound.java
```

To run the program:
```
/path/to/java11/Contents/Home/bin/javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.swing,javafx.graphics,javafx.base AlarmClock
```
