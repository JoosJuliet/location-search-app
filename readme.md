
# 데모영상

https://youtu.be/GvMzoa5khSY


# 계정 정보

로그인은 OAuth 2.0 구조의 Spring security로 구현했습니다.

계정은 두 개의 계정이 기본적으로 등록되어 있습니다.  
  
첫 번째 계정
- 이름: user
- 비밀번호: password

두 번째 계정
- 이름: user2
- 비밀번호: password

앞단의 경우 http://localhost:3000/login 으로 접속해 로그인 하실 수 있습니다.  
뒷단은 http://localhost:8000 으로 접속하실 수 있습니다.

# 프론트 엔드

./frontend/package.json이 존재하는 프로젝트 경로 내에서

``` shell
npm install && npm start
```

# 백엔드

프로젝트 경로 내 

``` shell
java -jar ./locationsearch-0.0.1-SNAPSHOT.jar
```
![jar 파일 다운로드 링크](https://github.com/JoosJuliet/location-search-app/raw/master/locationsearch-0.0.1-SNAPSHOT.jar)
