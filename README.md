# LIBOOKS(도서관 서비스) BACK

> BTC 3기 개발자과정 - 4조(우승나조)
>
> ## 개발자
>
> **홍재희 이시영 강동훈**
>
> ## 프로젝트 기간
>
> 2023.10.19 ~ 2022.11.20
>
> ## 담당 멘토님
>
> 전희연 멘토님
>
> ## FRONT Git
>
> https://github.com/beanbeeean/TEAM4-FINAL-SPRINGBOOT.git
>
> <br/> <br/>

- [실행](#1-실행)
- [개요](#2-개요)
  - [주제](#주제)
  - [선정배경](#선정배경)
  - [프로젝트 폴더 설명](#프로젝트-폴더-설명)
- [아키텍처](#3-아키텍처)
  - [ERD](#ERD)
  - [Infra Architecture](#Infra Architecture)
  - [API 명세서](#API 명세서)
- [환경 및 버전](#4-환경-및-버전)
  - [Environment](#environment)
  - [React](#react)
- [팀원 역할](#5.팀원-역할)
- [PPT](#6-ppt)
  <br/>
  <br/>

# 1. 실행

---

-   gradle build

```bash
build.gradle -> build module 'libooks_server'
```

<br/>

- run project

```bash
Run LibooksServerApplication 
```

<br/>

<br/>

# 2. 개요

---

## 주제

- 검색 기능과 필터링을 통한 도서 대여 기능 제공
- 사용자가 직접 예약하여 사용할 수 있도록 열람실 및 스터디룸 예약 기능 제공
- 커뮤니티 및 실시간 채팅 기능 제공
- 도서관 서비스 **(LIBOOKS)**
  <br/>
  <br/>

## 선정배경

- 요즘 취준생들이 늘어나는 시점에서 혼자서 취업 준비 혹은 공부하기 어려운 사람들을 위해서 기획하였음.
- 도서 대여와 열람실 예약을 기본 서비스로 생각하였고, 더 나아가 스터디원 모집, 예약을 통해서 다른 사람들과 서로 공유할 수 있도록 서비스 제공하였음.
- 실시간 채팅을 통해서 스터디원이 빠르게 소통하고 예약하는 등의 서비스를 진행할 수 있도록 기획하였음.
- 이 모든 기능들을 한 페이지에서 진행하게 된다면 일반적인 도서관 사이트와 차별되어 사용자 수에 대한 기대 효과가 있을 것이라 판단되어 선정하였음.
  <br/>
  <br/>

## 프로젝트 폴더 설명

-   `src - main - java/com/office/libooksserver` : 실행시킬 java 폴더
-   `src - main - java/com/office/libooksserver - admin` : 관리자 페이지 Controller, Service
-   `src - main - java/com/office/libooksserver - user` : 사용자 페이지 Controller, Service
-   `src - main - java/com/office/libooksserver - common ` : Config, S3 Util, WebSocketConfig .. 
-   `src - main - java/com/office/libooksserver - login` : Spring Security, JWT, Oauth2.0
-   `src - main - resources - mybatis` : mybatis mapper.xml Package
-   `src - main - resources - application.properties` : application을 구동시킬 때 필요한 properties
-   `src - main - resources - springdoc.properties` : swagger 사을 위해 필요한 properties
-   `src - main - resources - oauth2.properties` : Oauth2.0 로그인을 위해 필요한 properties
-   `build.gradle` : build할 gradle
    <br/>
    <br/>

# 3. 아키텍처

---

## ERD

![ERD](https://github.com/beanbeeean/TEAM4-FINAL-REACT/assets/93970771/1cb24627-de26-4393-bf0e-195d939f77eb)

## Infra Architecture

![Infra](https://github.com/beanbeeean/TEAM4-FINAL-REACT/assets/93970771/cdd28b62-a4d9-4096-ac3f-5b9733b7d24b)

# 4. 환경 및 버전

---

### Environment

-   Spring boot 2.6.7

### Dependency

-   Spring dependency-management 1.0.11

### Java

-   Java 17

<br/>
<br/>

# 5. 팀원 역할

---

| 이름                                     | 담당 직무                                                            |
| ---------------------------------------- | -------------------------------------------------------------------- |
| [홍재희](https://github.com/beanbeeean)  | 팀장 / 뷰 설계, 실시간 채팅, 커뮤니티 서비스, 통합 검색, 인프라 구축 |
| [이시영](https://github.com/siyeong1013) | 팀원 / 뷰 설계, 도서 대여, 관리자 서비스, 마이 페이지, 인프라 구축   |
| [강동훈](https://github.com/gilgan9852)  | 팀원 / JWT 토큰, 열람실 및 스터디룸 예약, Redis, ERD 설계            |

<br/>
<br/>

# 6. PPT

---

발표 PPT : https://docs.google.com/presentation/d/1BRN8iYt7B4sFcUhf7Y8t2sR0rn2pBPvJvnMIA7Dj2Mw/edit#slide=id.g27c01c8515a_1_0
