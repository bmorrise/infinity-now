#!/bin/bash

mvn clean install -DskipTests
java -jar target/ship-server-1.0-SNAPSHOT-jar-with-dependencies.jar aries.yml config.properties
