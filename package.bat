REM windows package

REM package
::如果不需要进行maven打包, 则使用 REM 注释掉该行
call mvn clean install -Dmaven.test.skip=true

REM del dist
rmdir build /s /q

REM create dist
mkdir build
mkdir build\plugins
mkdir build\plugin-configs

REM copy main program and config
xcopy grape-core\grape-server\target\grape-server-*-exec.jar dist /s /i
xcopy grape-core\grape-server\src\main\resources\application-prod.yml dist /s
xcopy grape-core\grape-server\src\main\resources\log\logging-config.xml dist /s

REM copy plugin and config
xcopy grape-plugins\system-tools\target\*-jar-with-dependencies.jar dist\plugins /s
xcopy grape-plugins\system-tools\src\main\resources\*-prod.yml dist\plugin-configs /s

REM copy bin
xcopy bin\* dist\ /s

cd dist

REM run main
rename grape-server-*-exec.jar grape-server.jar
rename application-prod.yml application.yml
