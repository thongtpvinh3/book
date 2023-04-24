FROM openjdk:11 as build

ENV TZ=Asia/Ho_Chi_Minh

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN  chmod +x mvnw

RUN ./mvnw package -DskipTests

FROM openjdk:11-jre

WORKDIR /app

ARG DEPENDENCY=/app/target/book-1.0

COPY --from=build /app/target/book-1.0.jar /app

ENTRYPOINT ["java","-jar","/app/book-1.0.jar"]