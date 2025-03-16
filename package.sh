#!/bin/sh
set -e

GRADLE_VERSION=8.11

PROJECTS_TO_BUILD=:gradle-mvn-distribution-core,:gradle-mvn-distribution-agents,:gradle-mvn-distribution-plugins
DESTINATION_DIR=./mvn-distribution/target
ARTIFACTS_DIR_NAME=gradle-$GRADLE_VERSION-$(date +"%Y%d%m")-000000+0000
ARTIFACTS_DIR=$DESTINATION_DIR/$ARTIFACTS_DIR_NAME
JAR_DESTINATION_DIR=$ARTIFACTS_DIR/lib


# 1. Compile and install core modulesto local repository
mvn clean install -pl $PROJECTS_TO_BUILD -am
# 2. Copy core modules
mvn clean dependency:copy-dependencies -pl $PROJECTS_TO_BUILD
# 3. Remove old dist dir
rm -rf ./mvn-distribution/target
# 4. Make dist target dir
mkdir -p $JAR_DESTINATION_DIR/agents && \
mkdir -p $JAR_DESTINATION_DIR/plugins
# 5. Copy files
cp ./mvn-distribution/core/target/lib/* $JAR_DESTINATION_DIR && \
cp ./mvn-distribution/agents/target/lib/* $JAR_DESTINATION_DIR/agents/ # && \
cp -r ./mvn-distribution/plugins/target/lib/* $JAR_DESTINATION_DIR/plugins
# 6. Rename odd dependency
mv $JAR_DESTINATION_DIR/fastutil-8.5.2.jar $JAR_DESTINATION_DIR/fastutil-8.5.2-min.jar
# 7. Copy binary scripts
mkdir $ARTIFACTS_DIR/bin/
cp mvn-distribution/bin-scripts/* $ARTIFACTS_DIR/bin/
# 8. Zip up distribution
(
  cd $DESTINATION_DIR && 
  zip -r gradle-$GRADLE_VERSION-bin.zip $ARTIFACTS_DIR_NAME
)
