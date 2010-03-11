Wicket Mega Demo
================

This application demonstrates using JDataset to interface with different pieces
 of the wicket application. 

To Run 
================

1) First type 'mvn install' in the <install>/modules directory to install 
JDataset into maven.

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