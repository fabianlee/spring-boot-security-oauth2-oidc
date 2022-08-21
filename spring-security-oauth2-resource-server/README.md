# spring-security-oauth2-client

Minimal Spring Boot project that shows how to use gradle and [buildah](https://buildah.io/) to create an OCI-compatible (Docker) image, then run it with [podman](https://podman.io/).

Full blog: https://fabianlee.org/2022/08/03/java-creating-oci-compatible-image-for-spring-boot-web-using-buildah/

## Create OCI image with buildah

```
./gradlew bootJar
./gradlew buildah
```

## Run image with podman

```
./gradlew podman
```

## Validate podman image running locally

```
curl http://localhost:8080/info

curl -s http://localhost:8080/api/user | jq

curl -s http://localhost:8081/actuator/health | jq
```

## Project initially created using Spring Intializer

[Spring Initializer Web UI](https://start.spring.io/)

```
id=spring-boot-with-buildah
artifact_id="${id//-}"
SpringAppClassName=SpringMain
version="0.0.2-SNAPSHOT"
groupId="org.fabianlee"

curl https://start.spring.io/starter.zip \
    -d type=gradle-project \
    -d dependencies=web,prometheus,devtools,actuator \
    -d javaVersion=11 \
    -d bootVersion=2.7.0 \
    -d groupId=$groupId \
    -d artifactId=$artifact_id \
    -d name=$SpringAppClassName \
    -d baseDir=$id \
    -d version=$version \
    -o $id.zip

unzip $id.zip
cd $id
chmod +x ./gradlew
```


https://www.baeldung.com/spring-security-oauth-resource-server
https://howtodoinjava.com/spring-boot2/oauth2-auth-server/
