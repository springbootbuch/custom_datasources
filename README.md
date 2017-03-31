# custom_datasources

[![Build Status](https://travis-ci.org/springbootbuch/custom_datasources.svg?branch=master)](https://travis-ci.org/springbootbuch/custom_datasources)

## Running

You'll need PostgreSQL on its standard ports on your host.

Use

```
mvn docker:start
```

to startup a container with the database.

## Building

The project does some integration tests using docker. You got to have Docker installed for your system to build the project using

```
mvn clean verify
```