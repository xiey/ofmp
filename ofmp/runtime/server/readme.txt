Eclipse Open Financial Market Platform (OFMP)
-----------------------------------------------------
http://www.eclipse.org/ofmp

1. INTRODUCTION
 The OFMP project's goal is to build an extensible, component-based Financial Market Platform 
 based on industry business requirements and state of the art technologies.

2. RELEASE INFO

OFMP is targeted at OSGi R4 and above, and JDK level 1.5 and above.

Release contents:
* "bin" contains the platform launcher and operating system specific binaries.
* "configuration" contains configuration files.
* "docs" contains general documentation and API javadocs
* "log" contains setup and runtime logs
* "plugins" contains runtime OSGi(tm) bundles
* "sql" contains SQL files required to setup the database
* "src" contains the Java source files for the platform

Latest info is available at the public website: http://www.eclipse.org/ofmp

This product includes software developed by the Spring Framework Project (http://www.springframework.org).

3. REQUIREMENTS
- Oracle XE 10g:
http://www.oracle.com/technology/products/database/xe
- JRE 1.5

4. INSTALLATION
- Ensure Oracle XE is started
- Open a Windows shell 
- Set the SYSTEM_PWD variable as your Oracle XE system user password (default is SYSTEM)
- Run the file bin/setup.bat

5. RUNNING THE PLATFORM
If needed configure the database parameters in configuration/datasource.conf
To start the platform, launch the file bin/run.bat

Runtime logs are kept in the /log folder