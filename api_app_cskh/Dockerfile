# Maven build container
#FROM maven:3.5.2-jdk-8-alpine AS maven_build 
FROM crelease.devops.vnpt.vn:10100/maven:3.6.2-jdk-8 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
COPY lib /tmp/lib
RUN mvn install:install-file -Dfile=lib/zalo-java-sdk-v2.jar -DgroupId=com.vng.zalo -DartifactId=zalosdk -Dversion=2.0 -Dpackaging=jar
RUN mvn dependency:go-offline
COPY src /tmp/src/
COPY glowroot /tmp/glowroot/
COPY opentelemetry /tmp/opentelemetry/
RUN mvn package
RUN java -Djarmode=layertools -jar target/one-app-cskh.jar extract

#pull base image
FROM crelease.devops.vnpt.vn:10100/bellsoft/liberica-openjre-debian:8u442
RUN addgroup --gid 2000 --system app && \
    adduser --uid 2000 --system --ingroup app app
RUN mkdir -p /glowroot && chown -R app:app /glowroot
USER app
EXPOSE 80
COPY --from=maven_build /tmp/dependencies/ ./
COPY --from=maven_build /tmp/spring-boot-loader ./
COPY --from=maven_build /tmp/snapshot-dependencies/ ./
COPY --from=maven_build /tmp/glowroot/ ./glowroot/
COPY --from=maven_build /tmp/opentelemetry/ ./opentelemetry/
COPY --from=maven_build /tmp/application/ ./
ENTRYPOINT [ "sh", "-c", "java -javaagent:glowroot/glowroot.jar -javaagent:opentelemetry/opentelemetry-javaagent.jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Doracle.jdbc.fanEnabled=false org.springframework.boot.loader.JarLauncher" ]
