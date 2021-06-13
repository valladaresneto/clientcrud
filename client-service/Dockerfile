FROM alpine as build

ARG MAVEN_VERSION=3.8.1
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries

# Install Java.
RUN apk --update --no-cache add openjdk11 curl

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV HOME /app
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/default-jvm/

ADD ./ $HOME/

# Define working directory.
WORKDIR $HOME/client-service

RUN ["mvn", "clean", "install", "-DskipTests"]

# Define default command.
#CMD ls -laR $HOME
#CMD ["mvn", "-version"]
CMD java -jar /app/client-service/target/client-service-0.0.1-SNAPSHOT.jar