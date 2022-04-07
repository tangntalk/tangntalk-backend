#!/bin/bash

PROJECT_NAME=tangntalk-0.0.1-SNAPSHOT.jar

CURRENT_PID=$(pgrep -f ${PROJECT_NAME})

if [ ! -z "$CURRENT_PID" ]; then
        echo "종료 중입니다."
        kill -15 $CURRENT_PID
        sleep 3
else
        echo "> 프로세스 실행 중이 아닙니다."
fi
