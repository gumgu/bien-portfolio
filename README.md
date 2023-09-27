# 🚀 bien-portfolio: BoardSimple (개인 프로젝트)
BoardSimple은 간단한 CRUD 기능을 갖춘 게시판 포털 서비스입니다. 사용자(SPA)와 관리자(MPA) 2가지 버전으로 제작되었습니다.

## 🏠 메인 화면
| 사용자 게시판 | 관리자 게시판 |
|---------|---------|
|![image](https://github.com/gumgu/Spring-Vue-Board/assets/87007010/28495501-7239-41b4-b31b-b053f1fd22de)|![image](https://github.com/gumgu/Spring-Vue-Board/assets/87007010/0b6ee6c2-f597-4a68-864b-e9555450b430)|

## 📑 프로젝트 소개
### 사용자 게시판
Springboot와 Vue.js를 이용하여 RestfulAPI 기반 SPA 방식으로 제작되었습니다.
- **회원(사용자)**: JWT와 Servlet Filter를 통한 회원가입 및 로그인, 아이디 중복 확인.
- **게시판**: 기본 CRUD, Validation(검증), 검색 및 페이징, 조회수.
- **문의글**: 기본 CRUD, privacy(작성자만 조회 가능), 자신의 문의글만 조회.
- **파일**: 파일 업로드, 조회, 다운로드.
- **댓글**: 기본 CRUD.

### 관리자 게시판
Springboot와 thymeleaf를 이용하여 MPA 방식으로 제작되었습니다.
- **회원(관리자)**: session과 Spring Interceptor를 통한 회원가입 및 로그인.
- **게시판**: 모든 게시글 수정 및 삭제, Validation(검증), 검색 및 페이징, 조회수.
- **문의글**: 모든 게시글 수정 조회 및 삭제, 자신의 문의글만 조회.
- **파일**: 파일 업로드, 조회, 다운로드, 삭제
- **댓글**: 기본 CRUD.

## 💡 저는 이 프로젝트를 통해서...
- 사용자 사이트(MPA)와 관리자 사이트(SPA)를 함께 구현하여 CSR, SSR의 전반적인 흐름을 직접 기획하고 구현해보았습니다.
- Vue.js, MyBatis, Spring 새로 학습하고 주요 기능을 직접 적용해보았습니다.
- Restful API를 학습하고 이를 준수하여 설계했습니다.
- 다양한 인증 & 인가 방법을 학습하고 적용해보았습니다. (cookie, session, jwt)

## 🌐 플랫폼
Web

## 🧑‍💻 개발 인원
1명 **(개인 프로젝트)**

## 🗓️ 개발 기간
2023.07.01 ~ 2023.08.29 (2개월)

## ⚙️ 개발 환경
- 언어: Java(JDK 1.8), HTML/CSS, JavaScript
- 프레임워크: Spring Framework(2.7.4), MyBatis(2.3.10), Thymeleaf
- DB: MySQL(8.0)
- IDE: IntelliJ IDEA 2023
  
## 💾 ERD
![adminBoard (7)](https://github.com/gumgu/Spring-Vue-Board/assets/87007010/8b9d5aa2-dc19-4f4d-9761-fa5c6cb09679)

* * *
## 🌟 주요 기능
### [인증 & 인가](https://github.com/gumgu/Spring-Vue-Board/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5:-%EC%9D%B8%EC%A6%9D-&-%EC%9D%B8%EA%B0%80)

### [게시글 CRUD](https://github.com/gumgu/Spring-Vue-Board/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5:-%EA%B2%8C%EC%8B%9C%EA%B8%80-CRUD)

### [에러 화면](https://github.com/gumgu/Spring-Vue-Board/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5:-ErrorPage-%EA%B5%AC%ED%98%84)
