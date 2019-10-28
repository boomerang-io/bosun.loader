FROM openjdk:8-jre-alpine
ARG BMRG_TAG
VOLUME /tmp
ADD target/bosun-loader-$BMRG_TAG.jar service.jar
RUN sh -c 'touch /service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=bosun -Djava.security.egd=file:/dev/./urandom -jar /service.jar" ]
