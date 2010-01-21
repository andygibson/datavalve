Wicket Mega Demo
----------------

This application demonstrates using JDataset to interface with different pieces of the wicket application. 

To Run 
------

1) First type 'mvn install' in the <install>/modules directory to install JDataset into maven.
2) in the wicketDemo folder, type 'mvn jetty:run' to run the application 
3) Point your browser to 'http://localhost:8080/wicketDemo/'

The demo demonstrates three different view elements - custom pagination, default wicket pagination with ordering and a search form with a default pagination interface. Each of these view elements connect to the same type of datasets, a SQL based query dataset, a JPA based query dataset, and for the custom pagination, a file based dataset. 

The file based dataset gives you random access to browse the contents of a file with 100,000 rows without any kind of delay.