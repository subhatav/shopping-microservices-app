
# COPY the JAR File and EXTRACT its Contents

FROM eclipse-temurin:17-jre-alpine AS builder

WORKDIR /extracted

ADD target/*.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

# COPY the Contents and RUN the Application

FROM eclipse-temurin:17-jre-alpine AS runner

WORKDIR /application

COPY --from=builder extracted/application/ ./
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
