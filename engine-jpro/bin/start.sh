#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
cd ..

if [ "${OSTYPE//[0-9.]/}" == "darwin" ]
then
	PLATFORM="mac"
else
	PLATFORM="linux"
fi

UARCH="$(uname -m)"

ARCH=""

if [[ "$UARCH" == *"arm"* || "$UARCH" == "aarch64" ]]
then
  ARCH="-aarch64"
fi

CLASSIFIER="$PLATFORM$ARCH"
if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
    
else
    JAVACMD="java"
fi
CLASSPATH=$(JARS=("$(pwd)/libs"/*.jar "$(pwd)/jfx/$CLASSIFIER/"*.jar); IFS=:; echo "${JARS[*]}")
JPROCLASSPATH=$(JARS=("$(pwd)/jprolibs"/*.jar); IFS=:; echo "${JARS[*]}")
JPRO_ARGS=("-Djpro.applications.default=edu.austral.dissis.chess.ui.ChessGameApplication" "-Dfile.encoding=UTF-8" "-Dprism.useFontConfig=false" "-Dhttp.port=8080" "-Dquantum.renderonlysnapshots=true" "-Dglass.platform=Monocle" "-Dmonocle.platform=Headless" "-Dcom.sun.javafx.gestures.scroll=true" "-Dprism.fontdir=fonts/" "-Djpro.deployment=GRADLE-Distribution") 



echo "JPro will be started."
$JAVACMD "${JPRO_ARGS[@]}" "-Djprocp=$JPROCLASSPATH" "$@" -cp "$CLASSPATH"  com.jpro.boot.JProBoot