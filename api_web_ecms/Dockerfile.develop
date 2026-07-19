# Maven build container
#FROM maven:3.5.2-jdk-8-alpine AS maven_build
FROM artifact-devops.vnpt.vn:10105/maven:3.6.2-jdk-8 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
RUN mvn dependency:go-offline
COPY src /tmp/src/
COPY glowroot /tmp/glowroot/
COPY opentelemetry /tmp/opentelemetry/
RUN mvn package -Dmaven.test.skip

#pull base image
FROM artifact-devops.vnpt.vn:10105/eclipse-temurin:8-jre-jammy

# Thiết lập múi giờ theo cách tốt nhất
ENV TZ=Asia/Ho_Chi_Minh

# Cài đặt các gói cần thiết
# Không cần lệnh 'sed' nữa vì base image này được hỗ trợ
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        fontconfig \
        iputils-ping \
        telnet \
        curl \
        traceroute \
        tzdata && \
    # Dọn dẹp để giảm kích thước image
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
    

COPY --from=maven_build /tmp/glowroot/ ./glowroot/
COPY --from=maven_build /tmp/opentelemetry/ ./opentelemetry/
COPY --from=maven_build /tmp/target/*.jar /app.jar

#RUN apt-get update && apt-get install -y fontconfig libfreetype6

RUN addgroup --gid 2000 --system app && \
    adduser --uid 2000 --system --ingroup app app && \
    apt update && apt install -y curl
RUN mkdir -p /glowroot && chown -R app:app /glowroot
#RUN mkdir -p /opentelemetry && chown -R app:app /opentelemetry
#COPY --from=maven_build /tmp/opentelemetry/ ./opentelemetry/
USER app
EXPOSE 80
ENTRYPOINT [ "sh", "-c", "java -javaagent:glowroot/glowroot.jar -javaagent:opentelemetry/opentelemetry-javaagent.jar  $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
