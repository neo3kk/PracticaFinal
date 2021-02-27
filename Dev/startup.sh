#!/bin/bash

cd ./FrontEnd
docker-compose run node npm install
docker-compose up -d
cd ../BackEnd
docker-compose up -d
