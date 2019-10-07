Spring Boot POA Overview
---

Welcome to the Spring Boot POA Overview!

### Introduction
This simple application provides REST API for accessing aggregated information of power of Power of Attorney Service
   
  
  - User Login (/users/signin - Test login: username - Super duper company, pw - TESTPW)
  - User Registration (/users/signup)
  - Token refresh(/users/refresh)
  - Overview of the logged users Power Of Attorneys/Accounts/cards (/poa/overview)
  - Overview of the single Power Of Attorney if the user is authorized(/poa/{poaid})
  - Search for and save all the user related Power Of Attorneys for POA REST API - saving them to the DB(/poa/findmypoas)
  
### REMARKS
    
    - In dev environment there is a possibility to access all the Power Of Attorneys of the user Super duper company 
    without authentication or authorization(/poa/testoverview)
    
    - After analizing the accessing client POA REST API, it's seems that the grantor and the grantee are wrongly connected
    
    - Because of the same reason for the Super duper company, 0 cards are visible after validation
    
    - There are some typos in the POA REST API aswell - missing letter.  
    

# Stack

![](https://img.shields.io/badge/java_11-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mongodb-%E2%9C%93-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/hysterix-%E2%9C%93-blue.svg)
![](https://img.shields.io/badge/lombok-%E2%9C%93-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)
![](https://img.shields.io/badge/maven-✓-blue.svg)

### How to run the project

**To build and run:** use `mvn spring-boot:run`

**Application runs on:** http://localhost:8082

**REST API documentation:** http://localhost:8082/swagger/

