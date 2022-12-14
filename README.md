# NOTICE - this is the legacy Spring Boot 2.6.6/Spring Security 5.6.6 version of my OAuth2 Resource Server using the deprecated WebSecurityConfigurerAdapter
## You should look my newer [Spring Boot 2.7.3/Spring Security 5.7.3 version implementation](https://github.com/fabianlee/spring-boot-security5-oauth2-oidc)


## spring-boot-security-oauth2-oidc

blog: https://fabianlee.org/2022/08/25/java-spring-security-oauth2-oidc-protecting-client-app-and-resource-server/

Java Spring Boot/Spring Security implementations of OAuth2 entities:

* [Client Application, web app running on port 8080](spring-security-oauth2-client-app/README.md)
* [Resource Server, microservice running on port 8081](spring-security-oauth2-resource-server/README.md)


Using:
* OpenJDK 17
* [Gradle 7.4](https://docs.gradle.org/current/userguide/compatibility.html)
* Spring Boot 2.6
* Spring Security 5.6.6
* [Windows 2019 ADFS as Authentication Server](https://fabianlee.org/2022/08/22/microsoft-configuring-an-application-group-for-oauth2-oidc-on-adfs-2019/)

![OAuth2 Entities](https://github.com/fabianlee/spring-boot-security-oauth2-oidc/raw/main/diagrams/oauth2-entities.drawio.png)


