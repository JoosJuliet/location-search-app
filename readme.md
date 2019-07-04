
# 데모영상

https://youtu.be/GvMzoa5khSY




# 실행방법
깃 클론하는 법
```shell
git clone https://github.com/JoosJuliet/location-search-app.git
```

## 1. 백엔드 셋팅
``` shell
java -jar ./locationsearch-0.0.1.SNAPSHOT.jar

```
## 2. 프론트 셋팅
``` shell
cd frontend
npm install && npm start
```

## 3. 화면으로 가기
http://localhost:3000/login 으로 접속
밑에 있는 계정 정보중 원하시는 것을 통해 로그인 하시면 됩니다.



# 계정 정보

로그인은 OAuth 2.0 구조의 Spring security로 구현했습니다.

계정은 두 개의 계정이 기본적으로 등록되어 있습니다.  
  
첫 번째 계정
- 이름: user
- 비밀번호: password

두 번째 계정
- 이름: user2
- 비밀번호: password


# 환경셋팅
FRONT -> React
BACK -> Spring-Boot


## 프론트 엔드

./frontend/package.json이 존재하는 프로젝트 경로 내에서

``` shell
cd frontend
npm install && npm start
```

앞단의 경우 **http://localhost:3000/login** 으로 접속해 로그인 하실 수 있습니다.  


## 백엔드

프로젝트 경로 내 

``` shell
java -jar ./locationsearch-0.0.1-SNAPSHOT.jar
```
![jar 파일 다운로드 링크](https://github.com/JoosJuliet/location-search-app/raw/master/locationsearch-0.0.1-SNAPSHOT.jar)


뒷단은 Spring rest api로 구현해서 있습니다.
