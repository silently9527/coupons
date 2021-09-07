#!/bin/sh

target_dir=`pwd`

pid=`ps ax | grep -i 'grape.server' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No grape-server running."
        exit -1;
fi

echo "The grape-server(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to grape-server(${pid}) OK"
