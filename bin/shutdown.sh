#!/bin/sh

target_dir=`pwd`

pid=`ps ax | grep -i 'coupons.main' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No coupons.main running."
        exit -1;
fi

echo "The coupons.main(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to coupons.main(${pid}) OK"
