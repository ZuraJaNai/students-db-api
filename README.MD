sudo docker pull docker-repository.intra.itera.no:5000/students-db/students-db-api
sudo docker tag docker-repository.intra.itera.no:5000/students-db/students-db-api students-db-api
sudo docker container run -d -p 8081:8080 -e ENV_CODE=stage --restart=always  students-db-api
sudo docker ps