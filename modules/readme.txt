JDataset 0.9 Alpha build
=========================

Caution
=======

The packaging naming will very likely change somewhat in the next release and 
the whole project will be renamed so don'be prepared.


  
System requirements
=======================

 All you need to run this project is Java 5.0 (Java SDK 1.5) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on a Servlet
 Container. The embedded Jetty and Tomcat containers get downloaded
 automatically by the Maven commands.



Getting Started
================

To get started, open a command prompt in the modules directory and execute :

mvn clean install

This will install all the modules in your maven repository. 


 
Running the samples
===================

Navigate to the samples\ folder and there are several samples included. Each of 
the samples builds their own databases using hsql so you don't have to set up 
a datastore in a server and all the demos are self contained running in embedded
jetty so there is no configuration needed other than to install the jdataset 
libraries in maven using the above step. 


cdi Demo
=========

Jetty demo using CDI and JSF

to run, type : mvn jetty:run

           
wicket Demo
============
 
Mega demo showing the different provider mechanisms with different view mechanisms.

to run, type : mvn jetty:run


swingdemo 
==========

Uses the IndexedDataProviderCache to provide random access to the source data in
a cohesive manner. This lets you browser thousands of rows of data efficiently.

to run, type : mvn exec:java -Dexec.mainClass="org.jdataset.swingdemo.Main"


examples 
========

The example classes from the documentation with some code showing how to use them.



Building the documentation
===========================

Navigate to the docs folder and type : 

mvn clean package

This will produce the documentation in the folders under docs\target\docbkx\
            