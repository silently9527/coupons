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
mkdir build/plugins-zip

# 拷贝客户端代码
cp -R coupons-client/* build/client

# 拷贝主项目文件
cp coupons-main/target/coupons-main-*-exec.jar build
cp coupons-main/src/main/resources/application-prod.yml build 
cp coupons-main/src/main/resources/log/logging-config.xml build 

# 拷贝插件文件
#cp plugins/*/target/*-jar-with-dependencies.jar build/plugins
#cp plugins/*/src/main/resources/*-prod.yml build/plugin-configs

base_dir=$(cd "$(dirname "$0")";pwd)

for file in `ls plugins`; {
  if [ -f $file ] || [ "${file##*.}"x = "iml"x ]; then
    echo $file;
  else
    mkdir -p plugins/$file/target/$file
    cp plugins/$file/src/main/resources/*-prod.yml plugins/$file/target/$file
    cp plugins/$file/target/*-jar-with-dependencies.jar plugins/$file/target/$file
    cp -R plugins/$file/client plugins/$file/target/$file
    cd plugins/$file/target/$file
    zip -r $file.zip ./*
    cd $base_dir
    cp plugins/$file/target/$file/$file.zip build/plugins-zip
  fi;
}


cp bin/* build

cd build

mv coupons-main-*-exec.jar coupons-main.jar
mv application-prod.yml application.yml
