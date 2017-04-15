#!/usr/bin/env bash

sudo yum update

sudo yum install ruby

sudo yum install wget

cd /home/ec2-user

wget https://aws-codedeploy-us-east-1.s3.amazonaws.com/latest/install

chmod +x ./install

sudo ./install auto

#Installing tomcat
sudo mkdir /opt/tomcat/ && cd /opt/tomcat

sudo wget http://redrockdigimark.com/apachemirror/tomcat/tomcat-8/v8.5.11/bin/apache-tomcat-8.5.11.zip

sudo wget https://www.apache.org/dist/tomcat/tomcat-8/v8.5.11/bin/apache-tomcat-8.5.11.zip.md5

cat apache-tomcat-8.5.11.zip.md5

md5sum apache-tomcat-8.5.11.zip

sudo unzip apache-tomcat-8.5.11.zip

sudo chmod 700 /opt/tomcat/apache-tomcat-8.5.11/bin/*.sh

sudo yum install java-1.8.0

sudo yum remove java-1.7.0-openjdk