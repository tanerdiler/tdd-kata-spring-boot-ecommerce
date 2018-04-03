
FROM java:8

CMD echo "Creating /basket-service/bin folder..." && \
    mkdir /basket-service/bin

ARG JAR_FILE

ADD target/${JAR_FILE} target/basket-service.jar

EXPOSE 8080

ENV JAVA_OPTS=""
ENTRYPOINT java $JAVA_OPTS -Dspring.profiles.active=container -jar target/basket-service.jar