sample-tools-java
=================

Java-based sample application demonstrating how to parse a Europass document and store the information in a DB.

Features
=========
This is a Java-based (Swing) standalone application in order to show how the information contained in a Europass document generated using the online editor, can be extracted and imported into a custom database schema (MS SQL, MySQL and Oracle). Specifically the app:

- Presents a graphical interface to the user allowing to select a Europass document from his local hard disk.
- Extracts the XML from the document.
- Parses the XML and extract the data.
- Stores the extracted data in a custom schema and present a report to the end user.

Technologies used
=================
- Hibernate ORM (http://hibernate.org/) for database persistence.
- Xalan library for XML transformation and manipulation (http://xalan.apache.org/index.html).
- Web Services by Europass REST API (http://interop.europass.cedefop.europa.eu/web-services/rest-api-reference) for extracting the Europass XML attachment from the PDF.

System requirements
====================
- JDK 7 or newer (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- Apache Maven Project (http://maven.apache.org/download.cgi#Installation)
- One of relational DB systems: MS SQL (https://www.microsoft.com/en-us/sql-server/sql-server-downloads)/ MySQL (https://dev.mysql.com/downloads/mysql/)/ Oracle (https://www.oracle.com/database/index.html) 

Installation/ Run
==================
1. Run **mvn install** in project root dir to build project.
2. Run main method of **SoftTool** class to run app. 

From the main menu of app (File -> Configuration) or Config icon, user can configure DB systems (MS SQL, MySQL, Oracle) & properties to use.
This can be done also under resources/softtool.properties.
 
Finally, user should run DB schema query to initialize all tables (all these are under resources/sql/*).
 
