CDI Demo
================

 This is a demo for jdatasets using JSF and CDI and mainly demonstrates sortable
columns and a search form.
 
To Run 
================

1) First type 'mvn install' in the <install>/modules directory to install 
JDataset into maven.

2) in the cdidemo folder, to start the server type :

mvn jetty:run
 
3) Point your browser to 

 http://localhost:8080/cdidemo

What is it?
================

This demo features the use of jdatasets in a CDI and JSF environment with 
hibernate. It uses CDI injection to pass the hibernate session into the data 
provider and then to pass the data provider into the person search dataset. The 
person results are named and used in the results display using an EL expression.
This project also includes an Expression Parameter Resolver to demonstrate how 
easily it can be to extend the data providers for new environments. This 
expression parameter resolver will probably end up in its own package eventually.     

 System requirements
 ===================

 All you need to run this project is Java 5.0 (Java SDK 1.5) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on a Servlet
 Container. The embedded Jetty and Tomcat containers get downloaded
 automatically by the Maven commands.





CDI Demo
================ 

 What is it?
 ===========

 This is a demo for jdatasets using JSF and CDI and mainly demonstrates the sortable columns concepts.  
 
 System requirements
 ===================

 All you need to run this project is Java 5.0 (Java SDK 1.5) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on a Servlet
 Container. The embedded Jetty and Tomcat containers get downloaded
 automatically by the Maven commands.

 To Run
 =========================

 To run the application using embedded Jetty, execute this command:

  mvn jetty:run

 The application will be running at the following URL:
 
  http://localhost:8080/cdidemo

 To run the application on a standalone container, first execute this command:

  mvn package

 Then copy the archive target/cdidemo.war to the hot deploy directory of
 the container (e.g., the webapps directory of Tomcat). Of course, you also
 need to start the container.

 Importing the project into an IDE
 =================================

 If you created the project using the Maven 2 archetype wizard in your IDE
 (Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
 already have an IDE project.

 If you created the project from the commandline using archetype:generate, then
 you need to bring the project into your IDE. If you are using NetBeans 6.8 or
 IntelliJ IDEA 9, then all you have to do is open the project as an existing
 project. Both of these IDEs recognize Maven 2 projects natively.

 To import into Eclipse, you first need to install the m2eclipse plugin. To get
 started, add the m2eclipse update site (http://m2eclipse.sonatype.org/update/)
 to Eclipse and install the m2eclipse plugin and required dependencies. Once
 that is installed, you'll be ready to import the project into Eclipse.

 Select File > Import... and select "Import... > Maven Projects" and select
 your project directory. m2eclipse should take it from there.

 Once in the IDE, you can execute the Maven commands through the IDE controls
 to run the application on an embedded Servlet Container.

 Resources
 =========

 Weld archetypes:
 -  Quickstart:        http://seamframework.org/Documentation/WeldQuickstartForMavenUsers
 -  Issue tracker:     https://jira.jboss.org/jira/browse/WELDX
 -  Source code:       http://anonsvn.jboss.org/repos/weld/archetypes
 -  Forums:            http://seamframework.org/Community/WeldUsers
 JSR-299 overview:     http://seamframework.org/Weld
 JSF community site:   http://www.javaserverfaces.org


