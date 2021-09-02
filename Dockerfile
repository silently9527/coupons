FROM centos:7
MAINTAINER silently9527

EXPOSE 9090

WORKDIR /data/app
RUN yum -y install java-1.8.0-openjdk.x86_64

COPY server/target/mall-coupons-server*.jar .
RUN mv mall-coupons-server*.jar mall-coupons-server.jar

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar mall-coupons-server.jar --spring.profiles.active=prod --spring.config.location=/root/.coupons/conf/application.properties

