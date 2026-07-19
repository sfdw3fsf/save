# Maven build container
FROM artifact-devops.vnpt.vn:10105/maven:3.6.2-jdk-8 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
COPY lib /tmp/lib
RUN mvn install:install-file -Dfile=lib/axis-ws-client.jar -DgroupId=vn.vnptit.ws.client1 -DartifactId=wsclient1 -Dversion=1.0 -Dpackaging=jar
RUN mvn dependency:go-offline
COPY src /tmp/src/
COPY glowroot /tmp/glowroot/
RUN mvn package
RUN java -Djarmode=layertools -jar target/app-tichhop.jar extract

#pull base image
#FROM crelease.devops.vnpt.vn:10100/bellsoft/liberica-openjre-debian:8u442
#EXPOSE 80
FROM artifact-devops.vnpt.vn:10105/bellsoft/liberica-openjre-debian:8u442
RUN addgroup --gid 2000 --system app && \
    adduser --uid 2000 --system --ingroup app app && \
    apt update && apt install -y curl
RUN mkdir -p /glowroot && chown -R app:app /glowroot
#RUN mkdir -p /opentelemetry && chown -R app:app /opentelemetry
#COPY --from=maven_build /tmp/opentelemetry/ ./opentelemetry/
USER app
EXPOSE 80
COPY --from=maven_build /tmp/dependencies/ ./
COPY --from=maven_build /tmp/spring-boot-loader ./
COPY --from=maven_build /tmp/snapshot-dependencies/ ./
COPY --from=maven_build /tmp/glowroot/ ./glowroot/
COPY --from=maven_build /tmp/application/ ./
COPY src/main/resources/cacerts ./etc/ssl/certs/java/
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Djavax.net.ssl.trustStore=src\main\resources\cacerts -Doracle.jdbc.fanEnabled=false org.springframework.boot.loader.JarLauncher" ]
ENTRYPOINT [ "sh", "-c", "java -javaagent:glowroot/glowroot.jar  $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Doracle.jdbc.fanEnabled=false org.springframework.boot.loader.JarLauncher" ]
#COPY --from=maven_build /tmp/target/*.jar /app.jar
