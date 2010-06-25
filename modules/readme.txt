DataValve 0.9 Alpha build
=========================

System requirements
=======================

All you need to use DataValve is Java 5.0 (Java SDK 1.5) or greater and 
Maven 2.0.10 or greater if you want to run the demos or use DataValve from maven. 
 
Getting Started
================

To install DataValve into maven, open a command prompt in the <install>/src 
directory and execute :

mvn clean install

This will install all the modules in your maven repository for use in maven 
projects. The <install>\dist directory contains compiled jars as well as the 
source and javadoc in jar files. Documentation is in the docs directory in the 
form of API javadoc and a reference guide in HTML and PDF. 

 
Running the samples
===================

Navigate to the <install>\samples folder and there are several samples included. 
Each of the samples builds their own databases using hsql so you don't have to 
set up a datastore in a server. All the demos are self contained running in 
embedded jetty so there is no configuration needed other than to install the 
DataValve libraries in maven using the above step.

Each of the demos have a readme.txt describing how to run them and also some 
usually additional notes on the implementation.  


cdi Demo
=========

Jetty demo using CDI and JSF

to run, go to the cdidemo directory and type : mvn jetty:run

           
wicket Demo
============
 
Mega demo showing the different provider mechanisms with different view 
mechanisms all based off the same Wicket pages.

to run, go to the wicket demo directory and type : mvn jetty:run


swingdemo 
==========

Uses the ProviderTableModel to implement random access to the source data in
a cohesive manner. This lets you browse thousands of rows of data efficiently. Also 
implements clickable columns for sorting.

to run, type : mvn exec:java -Dexec.mainClass="org.fluttercode.datavalve.samples.swingdemo.Main"


examples 
========

The example classes from the documentation with some code showing how to use them.


