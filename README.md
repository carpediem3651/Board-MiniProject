# 게시판 만들기

## 사용된 기술

-Spring Boot
-Spring MVC
-Spring JDBC
-MySQL
-thymeleaf

## 아키텍처
```
                      Spring Core
                      Spring MVC             Spring JDBC  MySQL     
브라우저 --- 요청 ---> Controller ---> Service ---> DAO --->DB
       <--- 응답---템플릿         <---         <---     <---
                      <---Layer간에 데이터 전송은 DTO로 한다-->
```

## 게시판 만드는 순서
1.Controller와 템플릿
2.Service - 비즈니스 로직을 처리(하나의 Tx단위로 동작)
3.Service는 비즈니스로직을 처리하기위해 CRUD 하기위해 DAO사용
