FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./target/springboot-servicio-eureka-server-0.0.1-SNAPSHOT.jar servicio-regitro.jar
ENTRYPOINT [ "java","-jar","/servicio-regitro.jar" ]