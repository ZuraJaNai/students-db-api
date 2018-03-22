rem call mvn clean install
docker build -f Dockerfile -t students-db-api .
docker save students-db-api > "target\students-db-api.tar"
echo off
echo *************************************************
echo                   JOB IS DONE
echo *************************************************
pause