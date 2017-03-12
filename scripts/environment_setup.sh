#!/usr/bin/env bash

#Installing tomcat
sudo mkdir /opt/tomcat/ && cd /opt/tomcat

sudo wget http://redrockdigimark.com/apachemirror/tomcat/tomcat-8/v8.5.11/bin/apache-tomcat-8.5.11.zip

sudo wget https://www.apache.org/dist/tomcat/tomcat-8/v8.5.11/bin/apache-tomcat-8.5.11.zip.md5

cat apache-tomcat-8.5.11.zip.md5

md5sum apache-tomcat-8.5.11.zip

sudo unzip apache-tomcat-8.5.11.zip

sudo chmod 700 /opt/tomcat/apache-tomcat-8.5.11/bin/*.sh