FROM java:8-jre
EXPOSE 8080
ADD /target/students-db-api-1.0-SNAPSHOT.jar /var/lib/students-db-api.jar

ENV ENV_CODE=dev

ENTRYPOINT ["java", "-jar", "/var/lib/students-db-api.jar", "--spring.profiles.active=$ENV_CODE"]
ENV LOG_DIR="/var/log/"