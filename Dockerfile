# Usage
# docker build -t lara283/djl-footwear-classification 
# docker run --name djl-footwear-classification -p 8080:8080 -d lara283/djl-footwear-classification

FROM openjdk:21-jdk-slim

# Copy Files
WORKDIR /usr/src/app
COPY models models
COPY src src
COPY .mvn .mvn
COPY pom.xml mvnw ./

# Install
RUN ./mvnw -Dmaven.test.skip=true package

# Docker Run Command
EXPOSE 8080
CMD ["java", "-jar", "target/food-0.0.1-SNAPSHOT.jar"]
