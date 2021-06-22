# HPortal API Server

### Library & Framework
- Spring Data JPA
- Spring Data Elasticsearch
- Lombok
- Jsoup
- Spring Boot
- Swagger
- Gradle

### Packages
- com.devh.hportal.component : Spring bean components
- com.devh.hportal.configuration : Java Configurations
- com.devh.hportal.constant : Constants
- com.devh.hportal.controller : API Controllers
- com.devh.hportal.dto : DTO
- com.devh.hportal.entity : Entity
- com.devh.hportal.projection : Spring Data JPA Projection
- com.devh.hportal.repository : Spring Data Repository
- com.devh.hportal.result : Response Result
- com.devh.hportal.scheduler : Data Scraping Scheduler
- com.devh.hportal.service : Service for request
- com.devh.hportal.util : utility

### API Specs
- http://localhost:${port}/swagger-ui.html

### Build  
1. Gradle clean & build
2. Create some directory
   - mkdir hportal
3. Move Jar to directory
   - mv build/libs/hportal-v1.jar hportal
4. Create conf file
   - hportal/conf/hportal.properties
5. Package Directory Tree
   - hportal
     - hportal-v1.jar
     - conf
       - hportal.properties
6. Check Database & Elasticsearch
7. Run Jar (java)
---
## Database & Elasticsearch Settings
- [Check docker directory] (https://github.com/rlagjstmd/springboot-example/tree/master/docker)
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
