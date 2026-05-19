FROM ubuntu:24.04

RUN apt-get update && apt-get install -y \
    curl unzip wget gnupg2 software-properties-common \
    fonts-dejavu fonts-liberation

ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Install Java 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Install Gradle
ENV GRADLE_VERSION=8.5
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip && \
    rm gradle-${GRADLE_VERSION}-bin.zip && \
    mv gradle-${GRADLE_VERSION} /opt/gradle

ENV GRADLE_HOME=/opt/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin:$JAVA_HOME/bin

WORKDIR /app

COPY . /app

CMD ["gradle", "test", "--no-daemon"]
