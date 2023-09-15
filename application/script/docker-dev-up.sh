#!/bin/bash

# -f docker-compose 파일 위치 설정
# -p Project Name 설정
# -d 백그라운드 실행
# --scale container 갯수 설정
#./gradlew build

docker-compose -f ./docker/docker-compose-dev.yml -p carrot-dev up -d --scale app=2
