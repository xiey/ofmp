Eclipse Open Financial Market Platform (OFMP)
---------------------------------------------
http://www.elcipse.org/ofmp

1. Eclipse OFMP Building Requirement

Eclipse OFMP 1.x requires JDK 1.5 and Maven 2 for building.
Currently, Maven 2.0.9 is used for building the framework.

2. Building Eclipse OFMP

Currently, Eclipse OFMP uses Maven 2 to handle the building
process. Maven profiles have been used to allow configuration of the building process.

For more info on Maven profiles, please see this page: 
http://maven.apache.org/guides/introduction/introduction-to-profiles.html

To build OFMP, from the OFMP root folder, simply run 
# mvn clean install

By default, the database setup scripts are run. If you want to avoid them on further runs, use the no_database_setup system property:

# mvn -Dno_database_setup=true clean install

2a. Running the integration tests

By default the project builds only the distributable modules without running any
integration tests. To run them, one should select the 'it' profile:

# mvn -Pit clean install

2b. Packaging sources and javadoc

To package sources and javadoc, use the 'src' profile:

# mvn -Psrc clean install

3. Release deliverables

From the ofmp/release module, simply run 
# mvn clean install

Deliverables can be found in target folder of ofmp/release submodules 