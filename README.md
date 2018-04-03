[![Build Status](https://travis-ci.org/tanerdiler/spring-boot-ecommerce-basketmanagement.svg?branch=master)](https://travis-ci.org/tanerdiler/spring-boot-ecommerce-basketmanagement)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.tanerdiler%3Aecommerce-basket-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.tanerdiler%3Aecommerce-basket-api)
[![codecov](https://codecov.io/gh/tanerdiler/spring-boot-ecommerce-basketmanagement/branch/master/graph/badge.svg)](https://codecov.io/gh/tanerdiler/spring-boot-ecommerce-basketmanagement)




# spring-boot-ecommerce-basketmanagement
Basket Management Project for E-Commerce Platforms includes defining campaigns and calculation discounts for products added in basket.

# Database

For Dev and Prod, you must create a database names basket_service. Dev and Prod profile uses flyway to update db changes.
For test, h2 memory db is being used.

# Docker

# Run mysql container 

docker run --name basket-service-mysql -e MYSQL_USER=[USERNAME] -e MYSQL_ROOT_PASSWORD=[PASSWORD] -e MYSQL_DATABASE=basket_service -p3306:3306 -d mysql:5.7

# Build & Create Image

mvn clean install

docker build . --build-arg JAR_FILE=target/ecommerce-basket-api-0.0.1-SNAPSHOT.jar -t tanerdiler/basket-service-api

or

mvn dockerfile:build

# Run Container

docker run -d --name basket-service-api --link basket-service-mysql:mysql -p8080:8080 \
            -e DATABASE_HOST=basket-service-mysql \
            -e DATABASE_PORT=3306 \
            -e DATABASE_NAME=basket_service \
            -e DATABASE_USER=[USERNAME] \
            -e DATABASE_PASSWORD=[PASSWORD] \
            tanerdiler/basket-service-api

# Container Compose

TODO FIX : corrupted jar file

docker-compose up --build

