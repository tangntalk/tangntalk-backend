# 배포 방법


(현재 자동화 진행중)
github action -> AWS Code Deploy를 통한 CI/CD 파이프라인 구축 중

## 서버 배포 시 설정

tangntalk/backend/src/main/resources/ 디렉토리에 설정을 선택 및 추가할 수 있다.</p>
특정 설정파일을 선택하는 법은 application.yaml의 spring.profiles.active 항목으로 설정할 수 있다.</p>
기본 profile은 로컬 3306 Maria DB를 사용하고 8080 포트로 서비스된다.</p>
변경 시 주석 처리 후 해당하는 프로파일을 지정하면 된다.</p>

## 배포 스크립트

tangntalk/backend/scripts/ 디렉토리 밑에 두개의 배포 스크립트가 있다.

<ul>
  <li>
    <strong>deploy.sh</strong>: mvnw를 사용하여 프로젝트를 빌드 후 배포한다. 이미 서비스되고 있다면 해당 서비스 종료 후 배포한다.
  </li>
  <li>
    <strong>shutdown.sh</strong>: 서버를 종료한다.
  </li>
</ul>

## 주의사항 

### shutdown.sh  
배포스크립트는 pgrep으로 'tangntalk-0.0.1-SNAPSHOT.jar'를 검색하여 PID를 찾는다.
동일명의 다른 서비스를 운영중이라면 오류가 날 수 있으니 주의가 필요하다.

### 배포 시 보안
해당 디렉토리에는 개발 편의성을 위해 데이터베이스의 보안 정보가 [docker 디렉토리](./docker-tangntalk)의 `.env` 파일과 
[application-develop.yaml](./src/main/resources/application-develop.yaml) 등에 포암되어 있다. 
따라서 배포 시에는 이러한 정보를 수정하여 배포하는 것이 바람직하다.(기존에 한번 의문의 해킹을 당한 적이 있음) 
