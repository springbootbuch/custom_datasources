# custom_datasources

## Running

You'll need PostgreSQL and MySQL on their respective standard ports on your host.

Use

```
mvn docker:start
```

to startup 2 containers with the databases.

## Building

The project does some integration tests using docker. You got to have Docker installed for your system to build the project using

```
mvn clean verify
```