#!/bin/sh

# 删除上次构建的包
rm -rf build

# linux、mac上打包的脚本. 暂未实现
# 打包项目
mvn clean package -Dmaven.test.skip=true

mkdir build
mkdir build/client
mkdir build/plugins
mkdir build/plugin-configs

# 拷贝客户端代码
cp coupons-client/* build/client

# 拷贝主项目文件
cp coupons-main/target/coupons-main-*-exec.jar build
cp coupons-main/src/main/resources/application-prod.yml build 
cp coupons-main/src/main/resources/log/logging-config.xml build 

# 拷贝插件文件
cp plugins/*/target/*-jar-with-dependencies.jar build/plugins
cp plugins/*/src/main/resources/*-prod.yml build/plugin-configs


cp bin/* build

cd build

mv coupons-main-*-exec.jar coupons-main.jar
mv application-prod.yml application.yml
