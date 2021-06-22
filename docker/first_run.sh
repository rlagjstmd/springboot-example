#! /bin/sh

CURRENT_DIR=$PWD

mkdir -p ./elasticsearch/data
mkdir -p ./mariadb/data
chown -R elasticsearch:elasticsearch ./elasticsearch
chmod -R 777 ./elasticsearch
chmod -R 777 ./mariadb
sysctl -w vm.max_map_count=262144

docker network create hportal-network
docker-compose up -d
