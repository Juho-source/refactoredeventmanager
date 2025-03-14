# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -B -DskipTests

# Stage 2: Create runtime image with JavaFX and Xvfb setup
FROM openjdk:21-slim
WORKDIR /app

# Install required packages: wget, unzip, GTK libraries and Xvfb for a virtual display
RUN apt-get update && \
    apt-get install -y wget unzip libgtk-3-0 xvfb && \
    rm -rf /var/lib/apt/lists/*

# Download and extract the JavaFX SDK for Java 21
RUN wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip && \
    unzip openjfx-21.0.2_linux-x64_bin-sdk.zip -d /opt && \
    rm openjfx-21.0.2_linux-x64_bin-sdk.zip

# Set environment variable for JavaFX modules
ENV PATH_TO_FX=/opt/javafx-sdk-21.0.2/lib

# Copy the packaged jar file from the build stage (update the jar name if needed)
COPY --from=build /app/target/mainapp.jar /app/mainapp.jar

# Set the DISPLAY environment variable to use the virtual display
ENV DISPLAY=:99

# Remove the Xvfb lock file if it exists, start Xvfb and then launch the JavaFX application
ENTRYPOINT ["sh", "-c", "rm -f /tmp/.X99-lock; Xvfb :99 -screen 0 1024x768x24 & exec java --module-path /opt/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml -jar mainapp.jar"]