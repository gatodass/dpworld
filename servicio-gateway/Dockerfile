FROM openjdk:17
VOLUME /tmp
EXPOSE 9090
ADD ./servicio-gateway-0.0.1-SNAPSHOT.jar servicio-gateway.jar
ENTRYPOINT [ "java","-jar","/servicio-gateway.jar" ]