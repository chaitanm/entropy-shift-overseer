FROM index.docker.io/chaitanyameesala/entropy-shift-overseer:latest

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
ADD src /code/src

RUN ["mvn", "dependency:resolve"]

# Adding source, compile and package into a fat jar

RUN ["mvn", "package"]