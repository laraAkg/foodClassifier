# Usage
# docker build -t lara283/djl-food-classification 
# docker run --name djl-food-classification -p 8080:8080 -d lara283/djl-food-classification
# docker push lara283/djl-food-classification
FROM openjdk:21-jdk-slim

# Copy Files
WORKDIR /usr/src/app
COPY models models
COPY src src
COPY .mvn .mvn
COPY pom.xml mvnw ./

# Install
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw -Dmaven.test.skip=true package

# Docker Run Command
EXPOSE 8080
CMD ["java", "-jar", "target/tinyfood-0.0.1-SNAPSHOT.jar"]
