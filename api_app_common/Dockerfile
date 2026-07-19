# Maven build container
#FROM maven:3.5.2-jdk-8-alpine AS maven_build
FROM artifact-devops.vnpt.vn:10105/maven:3.6.2-jdk-8 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
RUN mvn dependency:go-offline
COPY src /tmp/src/
COPY glowroot /tmp/glowroot/
COPY opentelemetry /tmp/opentelemetry/
RUN mvn package

#pull base image
FROM artifact-devops.vnpt.vn:10105/bellsoft/liberica-openjre-debian:8u442
RUN addgroup --gid 2000 --system app && \
    adduser --uid 2000 --system --ingroup app app && \
    apt update && apt install -y curl telnet
RUN mkdir -p /glowroot && chown -R app:app /glowroot
RUN mkdir -p /opentelemetry && chown -R app:app /opentelemetry
COPY --from=maven_build /tmp/opentelemetry/ ./opentelemetry/

USER app
EXPOSE 80
COPY --from=maven_build /tmp/glowroot/ ./glowroot/
ENTRYPOINT [ "sh", "-c", "java -javaagent:glowroot/glowroot.jar -Dglowroot.agent.id=app-common  $JAVA_OPTS -Dlog4j2.formatMsgNoLookups=true -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
COPY --from=maven_build /tmp/target/*.jar /app.jar

COPY --from=maven_build /tmp/glowroot/ ./glowroot/
