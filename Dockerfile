FROM openjdk:17-jdk-slim

# Install necessary packages
RUN apt-get update && apt-get install -y wget

# Install Firefox
RUN apt-get install -y firefox-esr

# geckodriver 다운로드 및 설치
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.33.0/geckodriver-v0.33.0-linux64.tar.gz \
    && tar -xvzf geckodriver-v0.33.0-linux64.tar.gz \
    && mv geckodriver /usr/local/bin/ \
    && chmod +x /usr/local/bin/geckodriver

WORKDIR /app
COPY build/libs/MkNewsScrappingBot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
