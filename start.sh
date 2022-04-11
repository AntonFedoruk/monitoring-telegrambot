#!/bin/bash

# Pull new changes
git pull

# Add environment variables for Maven
export TG_MTB_USERNAME=$1
export TG_MTB_TOKEN=$2
export TG_MTB_DB_USERNAME='mtb_db_user'
export TG_MTB_DB_PASSWORD='mtb_db_password'

# Prepare Jar
#mvn clean
#mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables for Docker
export BOT_NAME=$1
export BOT_TOKEN=$2
export BOT_DB_USERNAME='mtb_db_user'
export BOT_DB_PASSWORD='mtb_db_password'

# Start new deployment
docker-compose up --build -d