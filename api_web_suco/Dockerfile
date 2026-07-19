# Maven build container
#FROM maven:3.5.2-jdk-8-alpine AS maven_build
FROM crelease.devops.vnpt.vn:10100/maven:3.6.2-jdk-8 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
RUN mvn dependency:go-offline
COPY src /tmp/src/
COPY glowroot /tmp/glowroot/
RUN mvn package


#pull base image
FROM crelease.devops.vnpt.vn:10100/openjdk:8u242-jdk-slim
RUN addgroup --gid 2000 --system app && \
    adduser --uid 2000 --system --ingroup app app
RUN mkdir -p /glowroot && chown -R app:app /glowroot
USER app
EXPOSE 80
COPY --from=maven_build /tmp/glowroot/ ./glowroot/
ENTRYPOINT [ "sh", "-c", "java -javaagent:glowroot/glowroot.jar   $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
COPY --from=maven_build /tmp/target/*.jar /app.jar
