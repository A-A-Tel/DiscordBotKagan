FROM alpine:latest

FROM alpine:latest

# Install dependencies
RUN apk add --no-cache bash curl wget ca-certificates

# Install Eclipse Temurin JDK 23
# Set working environment
ENV JAVA_HOME=/opt/java
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Eclipse Temurin JDK 23 using the AdoptOpenJDK Alpine package
RUN apk add --no-cache --repository=https://dl-cdn.alpinelinux.org/alpine/edge/community openjdk23 || \
    (echo "OpenJDK 23 not available in Alpine repositories yet, installing JDK 21 instead" && \
    apk add --no-cache openjdk21 && \
    ln -s /usr/lib/jvm/java-23-openjdk /opt/java)

WORKDIR /usr/src/bot/

CMD ["chmod", "+x", "/usr/src/bot/gradlew"]
CMD ["/usr/src/bot/gradlew", "run"]