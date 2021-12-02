#!/bin/bash

# Pull new changes
git pull

# Add environment variables for Maven
export TG_MTB_USERNAME=$1
export TG_MTB_TOKEN=$2

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables for Docker
export BOT_NAME=$1
export BOT_TOKEN=$2

# Start new deployment
docker-compose up --build -d