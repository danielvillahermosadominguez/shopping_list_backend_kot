FROM openjdk:18-jdk-alpine3.14

WORKDIR /app

COPY . /app

RUN ./gradlew build

EXPOSE 8080
EXPOSE 35729
CMD sh start.sh