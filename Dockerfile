FROM openjdk:8-jdk-alpine
COPY . /gatling/

ENTRYPOINT ["/bin/sh", "-c", "--", "while true; do sleep 30; done;"]



