# Build and Deployment Instructions

## Building the application

After installing all the dependency, you can build the application.

### **Backend Application**

For the backend Spring Boot application, navigate to the project directory and run the following command to build the application:

```
mvn clean install
```

<p>Or</p>
To build the backend Spring Boot application in your CI/CD pipeline, you can use the following steps:

Before build, code_quality stage will be run on backend code.

performs code quality checks on Java backend application.

```
code_quality:
  stage: code-quality
  image: $BACKEND_IMAGE
  script:
    - echo "Checking code quality"
    - mkdir smells/
    - java -jar $CI_PROJECT_DIR/.gitlab/DesigniteJava.jar -i $CI_PROJECT_DIR/funder-backend -o $CI_PROJECT_DIR/smells/ -d
  artifacts:
    paths:
      - smells/
      - $CI_PROJECT_DIR
```

After code_quality,

- Create a new job in your GitLab CI/CD pipeline and define it as a `build` stage.
- Choose as Appropriate backend Docker image to run the job. In this case, we can use the `maven:3.8.3-openjdk-17` image to build the application.
- In the script section of the job, navigate to the backend project directory using the `cd funder-backend` command.
- Use the `mvn clean install` command to build the application.

These steps can be used to build the backend Spring Boot application in your CI/CD pipeline.

You will be able to see

```
funder-backend-0.0.1-SNAPSHOT.jar
```

in this path:

```
"/builds/courses/2023-fall/csci-5308/Group19/funder-backend/target"
```

### Frontend Application

For the frontend React application, navigate to the project directory and run the following command to build the application:

```
npm install
```

Then, run the following command to build the application:

```
npm start
```

<p>Or</p>
To build the frontend React application in your CI/CD pipeline, you can use the following steps:

- Create a new job in your GitLab CI/CD pipeline and define it as a `build` stage.
- Choose an appropriate Docker image to run the job. In this case, we can use the `node:18.12.1` image to build the React application.
- In the script section of the job, navigate to the project directory where the React application is located using the `cd funder-frontend` command.
- Use the `npm install` command to install the required dependencies for the ReactJS application.
- Use the `CI=false  npm  run  build` command to build the React application.
- These steps can be used to build the frontend React application in your CI/CD pipeline.

You can see your `build` folder in the root folder of project.

## Deploying the application

### docker-compose file

```
version: "3.8"
services:
  funder-frontend:
    build:
      context: ./funder-frontend
      dockerfile: Dockerfile
    ports:
      - "3000:3000"
    networks:
      - funder_network

  funder-backend:
    build:
      context: ./funder-backend
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    networks:
      - funder_network

networks:
  funder_network:
    driver: bridge
```

`funder-frontend:`

`build:` Specifies the build context and Dockerfile for building the Docker image.

`context: ./funder-frontend:` Specifies the path to the directory containing frontend application source code.

`dockerfile: Dockerfile:` Specifies the Dockerfile to use for building the image.

`ports: - "3000:3000":` Maps port 3000 on the host to port 3000 in the container, allowing access to the frontend application.

`networks: - funder_network: `Connects the service to the "funder_network" network.


`funder-backend:`

`build: `Specifies the build context and Dockerfile for building the Docker image.

`context: ./funder-backend: `Specifies the path to the directory containing backend application source code.

`dockerfile: Dockerfile:` Specifies the Dockerfile to use for building the image.

`ports: - "8080:8080":` Maps port 8080 on the host to port 8080 in the container, allowing access to the backend application.

`networks: - funder_network:` Connects the service to the "funder_network" network.


`networks:`

`funder_network:`

`driver: bridge:` Specifies the network driver as "bridge," which is a simple and widely used network mode.

### **Deploying Backend**

The following are the steps to deploy Spring Boot Application using Docker and CI/CD pipeline. Once docker is installed in virtual machine:

**Step 1:** Creating a Dockerfile

```
FROM  maven:3.8.3-openjdk-17  AS  build
COPY  .  /home/app
WORKDIR  /home/app
RUN  mvn  -f  /home/app/pom.xml  clean  install

FROM  openjdk:17-jdk-slim
COPY  --from=build  /home/app/target/funder-backend-0.0.1-SNAPSHOT.jar  /app/funder-backend.jar
EXPOSE  8080

ENTRYPOINT  ["java",  "-jar",  "/app/funder-backend.jar"]
```

**Explanation:**

`FROM maven:3.8.3-openjdk-17 AS build:` Specifies the base image for the build stage as `maven:3.8.3-openjdk-17`

`COPY . /home/app:` Copies the content of the current directory (the context) into the `/home/app` directory in the Docker image.

`WORKDIR /home/app:` Sets the working directory inside the Docker image to `/home/app`

`RUN mvn -f /home/app/pom.xml clean install:` Executes the Maven command to clean the project, resolve dependencies, compile source code, run tests, and package the application into a JAR file.

`FROM openjdk:17-jdk-slim:` - Specifies the base image for the final runtime stage as `openjdk:17-jdk-slim`.

`COPY --from=build /home/app/target/funder-backend-0.0.1-SNAPSHOT.jar /app/funder-backend.jar:` Copies the compiled JAR file from the build stage into the `/app` directory in the final image.

`EXPOSE 8080:` - Informs Docker that the application inside the container will use port 8080.

`ENTRYPOINT ["java", "-jar", "/app/funder-backend.jar"]:` Runs the Java application using the JAR file as the entry point.

**Step 2:** Creating a CI/CD Pipeline
The next step is to create a CI/CD pipeline that will automate the deployment process.

### publish-backend

```
publish-backend:
  image: docker:latest
  stage: publish
  tags:
    - deploy
  variables:
    DOCKER_TLS_CERTDIR: ""
    DOCKER_HOST: "tcp://docker:2375"
  services:
    - docker:dind
  script:
    - cd funder-backend
    - pwd
    - echo $SERVER_IP
    - docker --version
    - docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD docker.io
    - docker build -t docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA .
    - docker push docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA
```

Below command Uses the official Docker image (`docker:latest`) to run the Docker commands.

```
image: docker:latest
```

This job belongs to the "publish" stage in the CI/CD pipeline.

```
stage: publish
```

- The DOCKER_TLS_CERTDIR variable is used to specify the directory where Docker TLS certificates are stored. TLS (Transport Layer Security) is a protocol used to secure communication between Docker client and server.
- By setting it to an empty string (""), it indicates that TLS is not being used in this configuration.

The DOCKER_HOST variable specifies the address where the Docker client communicates with the Docker daemon (server). In this case:

- tcp:// indicates the communication protocol (TCP).
- docker is the hostname where the Docker daemon is running. In Docker-in-Docker scenarios, it's common to use the hostname docker.
- 2375 is the port on which the Docker daemon is exposed.

```
variables:
    DOCKER_TLS_CERTDIR: ""
    DOCKER_HOST: "tcp://docker:2375"
```

docker:dind provides a Docker environment within a Docker container, enabling tasks like container builds and runs during testing or development.

```
services:
  - docker:dind
```

`cd funder-backend:` Change the current directory to funder-backend, indicating that the subsequent commands will be executed within this directory.

`pwd:` Print the current working directory to the console. Useful for debugging and verifying the correct directory is set.

`echo $SERVER_IP:` Print the value of the environment variable `$SERVER_IP` to the console.

`docker --version:` Print the Docker version to the console. This provides information about the Docker installation and ensures that the correct Docker version is available.

`docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD docker.io:` Authenticate with Docker Hub using the provided Docker Hub username `($DOCKERHUB_USERNAME)` and password `($DOCKERHUB_PASSWORD)`. This step is necessary to push the Docker image to Docker Hub.

`docker build -t docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA .:` Build a Docker image using the Dockerfile in the current directory (.). The -t flag specifies the image tag, and `$CI_COMMIT_SHORT_SHA` is likely a unique identifier (short SHA) associated with the current commit in a CI/CD environment.

`docker push docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA:` Push the built Docker image to Docker Hub. This makes the image available for deployment or use by other services.

### deploy-backend

```
deploy_backend:
  image: alpine:latest
  stage: deploy
  script:
    - chmod og= $SSH_PRIVATE_KEY
    - apk update && apk add openssh-client
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker login -u $DOCKERHUB_USERNAME -p '$DOCKERHUB_PASSWORD' docker.io"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f my-app || true"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p 8080:8080 --name my-app docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA"
```

`image: alpine:latest:` The base Docker image used for running this job. In this case, it's using Alpine Linux.

`stage: deploy:` Specifies that this job belongs to the "deploy" stage in your CI/CD pipeline.

`chmod og= $SSH_PRIVATE_KEY:` Changes the permissions of the SSH private key file to ensure it's not readable by others.

`apk update && apk add openssh-client:` Updates the package list and installs the OpenSSH client on the Alpine Linux image.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker login -u $DOCKERHUB_USERNAME -p '$DOCKERHUB_PASSWORD' docker.io":`Authenticate with Docker Hub using the provided Docker Hub username `($DOCKERHUB_USERNAME)` and password `($DOCKERHUB_PASSWORD)`.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA":` Pulls the specified version of the Docker image from Docker Hub onto the remote server.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f my-app || true":` Removes the existing Docker container named "my-app" on the remote server if it exists.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p 8080:8080 --name my-app docker.io/funderasdc/asdc-backend:$CI_COMMIT_SHORT_SHA":
` Runs a new Docker container named "my-app" in detached mode, mapping port 8080 on the host to port 8080 in the container.

### **Deploying frontend**

The following are the steps to deploy React Application using Docker and CI/CD pipeline. Once docker is installed in virtual machine:

**Step 1:** Creating a Dockerfile

```
FROM node:16-alpine
WORKDIR /usr/src/app/funder-frontend
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

**Explanation:**

`FROM node:16-alpine:` Specifies the base image for the Docker image. In this case, it uses Node.js version 16 on Alpine Linux.

`WORKDIR /usr/src/app/funder-frontend:` Sets the working directory inside the container to /usr/src/app/funder-frontend. Subsequent instructions will be executed in this directory.

`COPY package*.json ./:` Copies the package.json and package-lock.json (if it exists) files from the build context (the directory containing the Dockerfile) to the working directory inside the container.

`RUN npm install:` Runs the `npm install` command inside the container to install the dependencies specified in the package.json file.

`COPY . .:` Copies the entire content of the build context (the application source code) to the working directory inside the container. This includes source code, configuration files, and any other necessary files.

`EXPOSE 3000:` Informs Docker that the application inside the container will use port 3000.

`CMD ["npm", "start"]:` Specifies the default command to run when the container starts. In this case, it runs the npm start command, which typically starts the Node.js application.

**Step 2:** Creating a CI/CD Pipeline
The next step is to create a CI/CD pipeline that will automate the deployment process.

### publish-frontend

```
publish_frontend:
  image: docker:latest
  stage: publish
  tags:
    - deploy
  variables:
    DOCKER_TLS_CERTDIR: ""
    DOCKER_HOST: "tcp://docker:2375"
  services:
    - docker:dind
  script:
    - cd funder-frontend
    - pwd
    - echo $SERVER_IP
    - docker --version
    - docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD docker.io
    - docker build -t docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA .
    - docker push docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA
```

`image: docker:latest:` Specifies the Docker image to be used for running this job. In this case, it's using the latest version of the official Docker image.

`stage: publish:` Indicates that this job belongs to the "publish" stage in your CI/CD pipeline.

`DOCKER_TLS_CERTDIR: "":` Disables TLS for Docker.

`DOCKER_HOST: "tcp://docker:2375":` Specifies the Docker daemon's host address and port.

```
variables:
    DOCKER_TLS_CERTDIR: ""
    DOCKER_HOST: "tcp://docker:2375"
```

`services: - docker:dind:` Uses the Docker-in-Docker (dind) service to provide a Docker environment within the Docker container.

`cd funder-frontend:` Change the current directory to funder-frontend, indicating that the subsequent commands will be executed within this directory.

`pwd:` Print the current working directory to the console. Useful for debugging and verifying the correct directory is set.

echo $SERVER_IP: Prints the value of the $SERVER_IP variable to the console.

`docker --version:` Print the Docker version to the console. This provides information about the Docker installation and ensures that the correct Docker version is available.

`docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD docker.io:` Authenticate with Docker Hub using the provided Docker Hub username
`($DOCKERHUB_USERNAME)` and password `($DOCKERHUB_PASSWORD)`. This step is necessary to push the Docker image to Docker Hub.

`docker build -t docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA .:` Build a Docker image using the Dockerfile in the current directory (.). The -t flag specifies the image tag, and `$CI_COMMIT_SHORT_SHA` is likely a unique identifier (short SHA) associated with the current commit in a CI/CD environment.

`docker push docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA:` Push the built Docker image to Docker Hub. This makes the image available for deployment or use by other services.

### deploy-frontend

```
deploy_frontend:
  image: alpine:latest
  stage: deploy
  script:
    - chmod og= $SSH_PRIVATE_KEY
    - apk update && apk add openssh-client
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker login -u $DOCKERHUB_USERNAME -p '$DOCKERHUB_PASSWORD' docker.io"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f my-frontend || true"
    - ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p 3000:3000 --name my-frontend docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA"
```

`image: alpine:latest:` The base Docker image used for running this job. In this case, it's using Alpine Linux.

`stage: deploy:` Specifies that this job belongs to the "deploy" stage in your CI/CD pipeline.

`chmod og= $SSH_PRIVATE_KEY:` Changes the permissions of the SSH private key file to ensure it's not readable by others.

`apk update && apk add openssh-client:` Updates the package list and installs the OpenSSH client on the Alpine Linux image.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker login -u $DOCKERHUB_USERNAME -p '$DOCKERHUB_PASSWORD' docker.io":`
Authenticate with Docker Hub using the provided Docker Hub username `($DOCKERHUB_USERNAME)` and password `($DOCKERHUB_PASSWORD)`.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA":` Pulls the specified version of the Docker image from Docker Hub onto the remote server.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f my-frontend || true":` Removes the existing Docker container named "my-frontend" on the remote server if it exists.

`ssh -i $SSH_PRIVATE_KEY -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p 3000:3000 --name my-frontend docker.io/funderasdc/asdc-frontend:$CI_COMMIT_SHORT_SHA":` Runs a new Docker container named "my-app" in detached mode, mapping port 3000 on the host to port 3000 in the container.

[**Go back to README.md**](../README.md)
