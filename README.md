# Gradle bootstrap build with Maven

*This is a fork of the [official gradle repository](https://github.com/gradle/gradle) and not affiliated with Gradle Inc. in any way.*

This repository contains the required files and modifications to allow Gradle to be built with only Maven. The goal is to prove the feasability of [bootstrapping Gradle](https://bootstrappable.org/projects/java-tools.html) this way and better estimate the effort required with this approach.

The script `./package.sh` contains the steps required to generate a functional distribution zip in `mvn-distribution/target/gradle-8.11-bin.zip`. This distribution can be used in Gradle builds by modifying the `gradle/wrapper/gradle-wrapper.properties` in a Gradle project to reference the build zip file instead. An example can be found in the [dockerfile](#dockerfile) in the repository root.

## Caveats

This approach builds a valid Gradle distribution capable of building Gradle itself that is identical to a version build with an official Gradle distribution blob. However:

1. Dependencies are downloaded as jars from public sources and some of these dependencies require Gradle to build (most notably Kotlin)
2. The bootstrapped distribution can use the Gradle build cache to download artifacts instead of building them from source
3. The bootstrapped distribution is not optimized and contains more dependencies than required for running the distribution. Fixing this is not in the scope of the project.

## Dockerfile

To ensure that the bootstrapped Gradle distribution is capable of building a valid Gradle distribution, the `dockerfile` is provided.
It builds the bootstrapped distribution uses it to build Gradle `v8.11.0`. Finally when running the image, the distribution is compared to a `v8.11.0` distribution built with a Gradle distribution downloaded from Gradle.

To build the image run `docker build -t [name] .`, where name is the image name such a `gradle-maven-bootstrap-test`.
To compare the distributions after the build run `docker run --rm [name]`. This will output the SHA1 of the two distributions:
```
SHA1 (bootstrapped.zip) = e5dad8180c41468a4da06a128842ea1fe802e308
SHA1 (native.zip) = e5dad8180c41468a4da06a128842ea1fe802e308
```
