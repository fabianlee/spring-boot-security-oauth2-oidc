# NOTICE - this is the legacy Spring Boot 2.6.6/Spring Security 5.6.6 version of my OAuth2 Resource Server using the deprecated WebSecurityConfigurerAdapter
## You should look my newer [Spring Boot 2.7.3/Spring Security 5.7.3 version implementation](https://github.com/fabianlee/spring-boot-security5-oauth2-oidc)

# spring-security-oauth2-client-app

Spring Boot/Spring Security web app that serves as the "Client Application" entity in an OAuth2 Authorization Code flow.

Full Blog: https://fabianlee.org/2022/08/25/java-spring-security-oauth2-oidc-protecting-client-app-and-resource-server/

Tested with:
* OpenJDK 17
* [Gradle 7.4](https://docs.gradle.org/current/userguide/compatibility.html)
* spring-boot:2.6.6
* spring-security-core:5.6.6
* [Windows 2019 ADFS as Authentication Server](https://fabianlee.org/2022/08/22/microsoft-configuring-an-application-group-for-oauth2-oidc-on-adfs-2019/)

![OAuth2 Entities](https://github.com/fabianlee/spring-boot-security5-oauth2-oidc/raw/main/diagrams/oauth2-entities.drawio.png)


## Run using local Docker daemon

```
docker --version

# your ADFS server
export ADFS=win2k19-adfs1.fabian.lee

# OAuth2 client, secret, scope
export ADFS_CLIENT_ID=<the oauth2 client id>
export ADFS_CLIENT_SECRET=<the oauth2 client secret>
export ADFS_SCOPE="openid allatclaims api_delete"

# clear out any older runs
docker rm spring-security-oauth2-client-app

# run docker image locally, listening on localhost:8080
docker run \
--network host \
-p 8080:8080 \
--name spring-security-oauth2-client-app \
-e ADFS_CLIENT_ID=$ADFS_CLIENT_ID \
-e ADFS_CLIENT_SECRET=$ADFS_CLIENT_SECRET \
-e ADFS=$ADFS \
-e ADFS_SCOPE="$ADFS_SCOPE" \
fabianlee/spring-security-oauth2-client-app:1.0
```

  

## Run using host JVM and gradle

```
# need OpenJDK 17+
javac --version
java --version

# your ADFS server
export ADFS=win2k19-adfs1.fabian.lee

# OAuth2 client, secret, scope
export ADFS_CLIENT_ID=<the oauth2 client id>
export ADFS_CLIENT_SECRET=<the oauth2 client secret>
export ADFS_SCOPE="openid allatclaims api_delete"

# start on port 8080
./gradlew bootRun

```

## Create OCI image with buildah, run with podman

```
./gradlew bootJar
./gradlew buildah
./gradlew podmanRun
```

## Create OCI image with docker, run with docker

```
./gradlew bootJar
./gradlew docker
./gradlew dockerRun

```

