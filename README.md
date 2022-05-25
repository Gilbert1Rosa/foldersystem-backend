# Folder System backend

## Description

This project serves as a backend for the angular 
project: https://github.com/Gilbert1Rosa/explorer-ui2

<br>

Remember to configure proxy.conf.json to use the server and port this app is deployed.

## Configuration

Change (or create) the src/main/resources/application.properties file:

<pre>
    <code>
        spring.datasource.url=jdbc:mariadb://host:port/database
        spring.datasource.username=dbuser
        spring.datasource.password=dbpassword
        spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
        spring.jpa.show-sql=true
        spring.jpa.database-platform=org.hibernate.dialect.MariaDB103Dialect
        spring.servlet.multipart.max-file-size=upload-max-size
        spring.servlet.multipart.max-request-size=upload-max-size
        foldersystem.s3-bucket=s3-bucket
    </code>
</pre>

The host and port values correspond to your database's host and port,
and the database value corresponds to the database in which you executed
the sql scripts in this repo: https://github.com/Gilbert1Rosa/explorer-database.

<br>

The "foldersystem.s3-bucket" controls the S3 we will be using to store the files,
you have to have previously configure your S3 bucket and your AWS account for this.

<br>

Optionally, you can use "jdbc:mysql" in "spring.datasource.url" and
and the JDBC MySQL driver for "spring.datasource.driver-class-name",
it should also work fine with other DBMS, but it is not tested with other than 
MySQL/MariaDB.

## Run

### Windows

gradlew.bat bootRun

### Linux

./gradlew bootRun


