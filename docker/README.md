# Database & Elasticsearch on Docker

### Docker Compose
- Windows : run 'first_run.bat'
- Linux : run 'first_run.sh' (Process Count, Virtual Memory)

### Customizing
- Edit 'docker-compose.yml'
```yml
  version: '3.7'

  services:
    elasticsearch-hportal:
      image: docker.elastic.co/elasticsearch/elasticsearch:7.9.3
      container_name: elasticsearch-hportal
      environment:
        - discovery.type=single-node
        - cluster.name=hportal-cluster
        - node.name=devh-node
        - reindex.remote.whitelist=0.0.0.0:9200
        - bootstrap.memory_lock=true
        - "ES_JAVA_OPTS=-Xms1024m -Xmx1024m"
      volumes:
        - ./elasticsearch/data:/usr/share/elasticsearch/data
      ulimits:
        memlock:
          soft: -1
          hard: -1
      ports:
        - '19200:9200'    # Edit port
      networks:
        - hportal-network
    mariadb-hportal:
      image: mariadb:10.5.8
      container_name: mariadb-hportal
      environment:
        - MYSQL_ROOT_PASSWORD=123123    # Root Password here
        - MYSQL_DATABASE=hportal        # Database Schema here
        - MYSQL_USER=devh               # Database username here
        - MYSQL_PASSWORD=123123         # Database user password here
      volumes:
        - ./mariadb/data:/var/lib/mysql
      ports:
        - '13306:3306'    # Edit port
      networks:
        - hportal-network

  networks:
    hportal-network:
      external: true
```