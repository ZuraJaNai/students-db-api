## Students DB API

Backend for Sutdents DB application

### Runtime

Docker container

### Development tools

- IDEA IDE
- Maven 3.X
- JDK 1.8

### Deployment

Login to placemaster.itera.no

Find already started container and remove it

	$ sudo docker ps
	...
	989c5b591fb9        students-db-api ...
	...
	$ sudo docker stop 989c5b591fb9
	$ sudo docker rm -f 989c5b591fb9 

Get more recent container and deploy it
	
	sudo docker pull docker-repository.intra.itera.no:5000/students-db/students-db-api
	sudo docker tag docker-repository.intra.itera.no:5000/students-db/students-db-api students-db-api
	sudo docker container run -d -p 8081:8080 -e ENV_CODE=prod --restart=always  students-db-api
