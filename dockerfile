ARG BASE_IMAGE=eclipse-temurin:11-jdk

FROM maven:3.9.9-eclipse-temurin-11-alpine AS maven-build
WORKDIR /build/
COPY . /build/
RUN apk add zip && ./package.sh

FROM alpine/git:2.47.2 AS gradle-repo
RUN git clone --depth 1 --single-branch --branch v8.11.0 https://github.com/gradle/gradle.git /build/

FROM eclipse-temurin:11-jdk-alpine-3.21 AS build-image
RUN apk add git

FROM build-image AS gradle-bootstraped
WORKDIR /build/
COPY --from=gradle-repo /build /build/
COPY --from=maven-build /build/mvn-distribution/target/gradle-8.11-bin.zip /install/

RUN sed -i 's/https\\:\/\/services.gradle.org\/distributions\/gradle-8.11-rc-3-bin.zip/file\\:\/install\/gradle-8.11-bin.zip/g' gradle/wrapper/gradle-wrapper.properties

RUN ./gradlew :distributions-full:binDistributionZip

FROM build-image AS gradle-build
WORKDIR /build/

COPY --from=gradle-repo /build /build/

RUN ./gradlew :distributions-full:binDistributionZip

FROM alpine:3.21.3

WORKDIR /diff

COPY --from=gradle-bootstraped /build/subprojects/distributions-full/build/distributions/gradle-8.11-bin.zip /diff/bootstrapped.zip
COPY --from=gradle-build /build/subprojects/distributions-full/build/distributions/gradle-8.11-bin.zip /diff/native.zip

RUN apk add outils-sha1

CMD ["/bin/sh", "-c", "sha1 bootstrapped.zip native.zip"]
