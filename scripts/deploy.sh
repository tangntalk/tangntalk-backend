#!/bin/bash

PROJECT_REPOSITORY=/home/team02/yonseitalk/backend
PROJECT_NAME=yonseitalk-0.0.1-SNAPSHOT.jar
CURRENT_PID=$(pgrep -f ${PROJECT_NAME})

if [ ! -z "$CURRENT_PID" ]; then
        echo "현재 실행중인 인스턴스가 있습니다. 중단 후 재실행합니다."
        kill -15 $CURRENT_PID
        sleep 3
fi

echo "CURRENT PID: "$CURRENT_PID
echo "> 빌드 중"

cd $PROJECT_REPOSITORY
mvn -N io.takari:maven:wrapper
$PROJECT_REPOSITORY/mvnw clean package -DskipTests

nohup java -jar $PROJECT_REPOSITORY/target/yonseitalk-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=deploy >/dev/null 2>&1 &
CURRENT_PID=$(pgrep -f ${PROJECT_NAME})
echo "> 정상적으로 배포되었습니다."
echo "CURRENT PID: "${CURRENT_PID}
