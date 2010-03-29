Wicket Mega Demo
================

This application demonstrates using Spigot to interface with different pieces
 of the wicket application. 

To Run 
================

1) First type 'mvn install' in the <install>/modules directory to install 
Spigot into maven.

2) in the wicketDemo folder, to start the server type :

mvn jetty:run
 
3) Point your browser to 

http://localhost:8080/wicketDemo/

What is it?
================

The demo demonstrates three different view elements - custom pagination, 
default wicket pagination with ordering and a search form with a default 
pagination interface. Each of these view elements connect to the same type 
of datasets with different implementations a SQL based query dataset, a JPA based query dataset, and 
for the custom pagination, a file based dataset. 

The file based dataset gives you random access to browse the contents of a 
file with a large number of rows without any kind of delay. In many cases, the views are inherited 
with just the data provider used changing across examples. 

The goal is to show you that you need to change very little view code to use the different types of 
data providers in each of the different view types and the query components are both flexible and fast.

Source Code
===========

In the main wicketdemo package, we have the main Wicket application class, as 
well as common components such as the header link panel and the dataset info 
panel.

The source code is organized into different sections under the main 
org.fluttercode.spigot.samples.wicketdemo package for the different types of pages.
In each package, we typically have one abstract web page for the type of provider 
we are demonstrating with an abstract method to get the dataset from the subclass.
The subclasses just extend this abstract page and create the provider for that 
specific page (jpa,sql or file) type.  This demonstrates how you can interchange 
provider implementations without having to change how your application integrates 
with it.



custom - package containing the code for dealing with custom html pagination 
instead of the built in data provider and table components provided by wicket.

dataprovider - package containing the code for pagination using the built in 
wicket components or extensions DefaultDataTable and the ISortableDataProvider. 

search - package for code implementing a search form and wicket data table which
then is subclassed to use different spigot data provider components.

  



         