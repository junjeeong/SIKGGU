#!/bin/bash

# 프로젝트 이름 (Jar 파일이나 프로세스 찾을 때 사용)
PROJECT_NAME="sikggu"
JAR_PATH="/home/ec2-user/app/sikggu.jar"
DEPLOY_LOG="/home/ec2-user/app/deploy.log"

echo "==== 배포 시작 : $(date) ====" >> $DEPLOY_LOG

# 1. 현재 구동 중인 애플리케이션 pid 확인
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
    echo "> 구동 중인 애플리케이션 종료 (PID: $CURRENT_PID)" >> $DEPLOY_LOG
    kill -15 $CURRENT_PID
    sleep 5
fi

# 2. 새 애플리케이션 배포
echo "> 새 애플리케이션 배포" >> $DEPLOY_LOG

# 실행 권한 부여
chmod +x $JAR_PATH

# 백그라운드 실행 (로그는 nohup.out에 남김)
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH > /home/ec2-user/app/nohup.out 2>&1 &

echo "==== 배포 완료 : $(date) ====" >> $DEPLOY_LOG
