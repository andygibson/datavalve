CDI Demo
================

 This is a demo for DataValve using JSF and CDI and mainly demonstrates sortable
columns and a search form.
 
To Run 
================

1) First type 'mvn install' in the <install>/modules directory to install 
DataValve into maven.

2) in the cdidemo folder, to start the server type :

mvn jetty:run
 
3) Point your browser to 

 http://localhost:8080/cdidemo

What is it?
================

This demo features the use of DataValve in a CDI and JSF environment with 
hibernate. It uses CDI injection to pass the hibernate session into the data 
provider and then to pass the data provider into the person search dataset. The 
person results are named and used in the results display using an EL expression.
This project also includes an Expression Parameter Resolver to demonstrate how 
easily it can be to extend the data providers for new environments. This 
expression parameter resolver will probably end up in its own package eventually.     

 System requirements
 ===================

 All you need to run this project is Java 5.0 (Java SDK 1.5) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on embedded Jetty.


