#!/bin/sh

error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/opt/taobao/java
[ ! -e "$JAVA_HOME/bin/java" ] && unset JAVA_HOME

if [ -z "$JAVA_HOME" ]; then
  JAVA_PATH=`dirname $(readlink -f $(which javac))`
  if [ "x$JAVA_PATH" != "x" ]; then
    export JAVA_HOME=`dirname $JAVA_PATH 2>/dev/null`
  fi
  if [ -z "$JAVA_HOME" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi

pid=`ps ax | grep -i 'grape.server' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ "$pid" ] ; then
  echo "grape-server running."
  exit -1;
fi


export SERVER="grape-server"

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`pwd`
export CUSTOM_CONFIG_LOCATIONS=file:${BASE_DIR}/application.yml
#===========================================================================================
# JVM Configuration
#===========================================================================================
JAVA_OPT="${JAVA_OPT} -server -Xms512m -Xmx512m -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"

JAVA_MAJOR_VERSION=$($JAVA -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')
if [[ "$JAVA_MAJOR_VERSION" -ge "9" ]] ; then
  JAVA_OPT="${JAVA_OPT} -Xlog:gc*:file=${BASE_DIR}/logs/nacos_gc.log:time,tags:filecount=10,filesize=102400"
else
  JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ext"
  JAVA_OPT="${JAVA_OPT} -Xloggc:${BASE_DIR}/logs/nacos_gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
fi

JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/${SERVER}.jar"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${CUSTOM_CONFIG_LOCATIONS}"

if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

echo "$JAVA ${JAVA_OPT}"

# start
nohup $JAVA ${JAVA_OPT} grape.service >/dev/null 2>&1 &
echo -e "\033[36m start 'SERVER' success \033[0m"