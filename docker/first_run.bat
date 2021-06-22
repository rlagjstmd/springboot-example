set CURRENT_DIR=%cd%

mkdir ./elasticsearch/data
mkdir ./mariadb/data

docker network create hportal-network
docker-compose up -d