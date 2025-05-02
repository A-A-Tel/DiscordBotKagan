FROM alpine:latest

ENV JAVA_HOME=/opt/java/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN apk add --no-cache curl bash wget ca-certificates

RUN mkdir -p /opt/java && \
    cd /opt/java && \
    wget https://download.java.net/java/GA/jdk23/36/GPL/openjdk-23_linux-x64_bin.tar.gz && \
    tar -xzf openjdk-23_linux-x64_bin.tar.gz && \
    rm openjdk-23_linux-x64_bin.tar.gz && \
    mv jdk-23* jdk


CMD ["chmod", "+x", "/usr/src/bot/gradlew"]
CMD ["/usr/src/bot/gradlew", "run"]