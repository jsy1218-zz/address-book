Spring Integration - War Template
=================================

## Introduction

This sample project demoes how to run the Spring SOAP web service on the local instance. 

## Environment Specification

This project is compiled with and run with java JDK and JRE 1.7.0_67.

Maven is used for the dependency management and class path generation. This project was imported to Spring STS 3.6.0.RELEASE after it generates the eclipse classpath. 


## Command Line using maven

	mvn clean

    mvn eclipse::eclipse
    
You should be able to import the project without any build path error after issuing the above two command lines.
    

## Third party library version

third party library version is specified in the pom.xml file. 

## MySQL setup 

Follow the MySQL setup at: https://dev.mysql.com/usingmysql/get_started.html

Make sure your MySQL connection url and credential is updated in the jdbc.properties file.

This demo project connects to MySQL version 5.6.22.

## Bootstrap Start the Application

If imported into the Spring STS, you should be able to run with "Spring Boot App" option.

If you have any question, feel free to email blackjackjiang@gmail.com