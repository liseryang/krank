#!/bin/sh
MAVEN_OPTS="-Xdebug -Xnoagent -Dsun.lang.ClassLoader.allowArraySyntax=true -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9009 -ea"
export MAVEN_OPTS
echo $MAVEN_OPTS
mvn -Ddb=mysql -Dlog4j.configuration=file:./log4j.xml  jetty:run
