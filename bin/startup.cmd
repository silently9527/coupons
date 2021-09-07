@echo off

if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

set BASE_DIR=%~dp0

set CUSTOM_CONFIG_LOCATIONS=file:%BASE_DIR%application.yml

set SERVER=grape-server

set "JAVA_OPT=-server -Xms512m -Xmx512m -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
set "JAVA_OPT=%JAVA_OPT% -XX:-OmitStackTraceInFastThrow -XX:HeapDumpPath=%BASE_DIR%java_heapdump.hprof"
set "JAVA_OPT=%JAVA_OPT% -XX:-UseLargePages"

set "JAVA_OPT=%JAVA_OPT% -jar %BASE_DIR%%SERVER%.jar"
set "JAVA_OPT=%JAVA_OPT% --spring.config.location=%CUSTOM_CONFIG_LOCATIONS%"
call "%JAVA%" %JAVA_OPT% grape.service %*
