FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./logger-registrar-0.0.1-SNAPSHOT.jar logger.jar
ENTRYPOINT [ "java","-jar","/logger.jar" ]