#!/bin/bash

git clone https://github.com/EvansDaniel/Alarm-Clock

wget https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.16.1%2B1/OpenJDK11U-jdk_x64_mac_hotspot_11.0.16.1_1.tar.gz
wget https://download2.gluonhq.com/openjfx/11/openjfx-11_osx-x64_bin-sdk.zip

tar xvfz OpenJDK11U-jdk_x64_mac_hotspot_11.0.16.1_1.tar.gz
unzip openjfx-11_osx-x64_bin-sdk.zip
