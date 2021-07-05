# Hibernate
Hibernate is a Java framework that simplifies the development of Java applications to interact with the database.
It is an open-source, lightweight, ORM (Object Relational Mapping) tool. Hibernate implements the specifications of JPA (Java Persistence API) for data persistence.
An ORM tool simplifies data creation, data manipulation and data access.

## Advantages Hibernate
1. Open Source
2. Lightweight
3. High Performance
4. Independent Query Language
5. Automatic Table Creation
6. Simplifies Complex Joins
7. Status and Statics Matrix

## Hibernate Architecture Diagram
![](hibernate_architecture.jpg)

## Hibernate Components
**1. Configuration** - The Configuration object is the first Hibernate object you create in any Hibernate application. It is usually created only once during application initialization.
It represents a configuration or properties file required by the Hibernate. It provides two keys components −

* Database Connection – This is handled through one or more configuration files supported by Hibernate. These files are hibernate.properties and hibernate.cfg.xml.
* Class Mapping Setup – This component creates the connection between the Java classes and database tables.

**2. SessionFactory** - The SessionFactory is a factory of session and client of ConnectionProvider. It holds second level cache (optional) of data. This provides factory method to get
the object of Session. It is a heavyweight object and usually created during application start up and kept for later use. You would need one SessionFactory object per database
using a separate configuration file.

**3. Session** - The session object provides an interface between the application and data stored in the database. It is a short-lived object and wraps the JDBC connection.
It holds a first-level cache (mandatory) of data. This interface provides methods to insert, update and delete the object. It also provides factory methods for Transaction, Query and Criteria.
It is designed to be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a Session object.The session objects
should not be kept open for a long time because they are not usually thread safe and they should be created and destroyed them as needed.

**4. Transaction** - The transaction object specifies the atomic unit of work. This interface provides methods for transaction management.

**5. Query** - Query objects use SQL or Hibernate Query Language (HQL) string to retrieve data from the database and create objects. A Query instance is used to bind query parameters,
limit the number of results returned by the query, and finally to execute the query.

**6. Criteria** - Criteria objects are used to create and execute object oriented criteria queries to retrieve objects.

## Code Samples
1. Using XML
2. Using Annotation