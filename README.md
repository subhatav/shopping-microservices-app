# Shopping Application

This an **implementation** of *real-world*, *industry-approved* practices of several important **concepts**.

It consists of some **backend** *microservices* with *infrastructure* which **function** in a *unified* way.

These **microservices** are *developed* using:

* *Spring* **Boot**/**Cloud**/**Security** for *foundations*;
* **MySQL**/**PostgreSQL**/**MongoDB** for *storage*;
* *Netflix* **Eureka** for *service discovery*;
* *Spring* **Kafka** for *message queue*;
* **Zipkin** for *distributed span tracing*;
* **Keycloak** for *user(s) authorization*;
* **Prometheus** for *scraping live metrics*;
* **Grafana** for *statistics visualization*.

<hr/>

## Requirements

The **stable** releases of the following *technologies* are used:

| Technology     | Version | Technology   | Version |
| -------------- | ------- | ------------ | ------- |
| JDK            | 17+     | Spring Boot  | 3.0+    |
| Spring Cloud   | 2022+   | Spring Kafka | 3.0+    |
| Lombok         | 1.18+   | MySQL        | 8.0+    |
| MongoDB        | 5.0+    | PostgreSQL   | 15.3+   |
| Netflix Eureka | 4.0+    | Keycloak     | 21.0+   |
| Prometheus     | 2.40+   | Grafana      | 9.0+    |
| Maven          | 3.8+    | Docker       | 20+     |

P.S. For *production* purposes, only **Docker** is *sufficient*.

<hr/>

## Development

For *development* purposes (**without** *Prometheues* and *Grafana*), **follow** as below:

### Preparing Infrastructure

1. **Deploy** the **Mongo** *database* for the `product-service` with this **Docker** *command*:

    ```
    docker run --detach --name dev-mongo --publish 27017:27017 mongo:5.0.18-focal
    ```

2. **Deploy** the **MySQL** *database* for the `order/inventory-Services` with this **Docker** *command*:

    ```
    docker run --detach --name dev-mysql --publish 3306:3306 \
               --env MYSQL_ROOT_PASSWORD=dev-root mysql:8-oracle
    ```

3. **Enter** `mysqlsh` in the *terminal* and **execute** these *commands*:

    ```
    \connect root@localhost:3306
    \sql
    create schema `order-data`;
    create schema `inventory-data`;
    \quit
    ```

4. **Deploy** the **Keycloak** *server* for **authorization** with this **Docker** *command*:

    ```
    docker run --detach --name dev-keycloak --publish 8181:8080 \
               --env KEYCLOAK_ADMIN=admin --env KEYCLOAK_ADMIN_PASSWORD=pass \
                 quay.io/keycloak/keycloak:21.1 start-dev
    ```

5. **Deploy** the **Zipkin** *server* for **tracing** with this **Docker** *command*:

    ```
    docker run --detach --name dev-zipkin \
               --publish 9411:9411 openzipkin/zipkin
    ```

6. **Start** the **Kafka** *service* for **communication** with this **Docker** *command*:

    ```
    docker-compose --detach start kafka-broker
    ```

### Booting Microservices

1. **Build** the *Maven* **application** with this **Maven** *command*:

    >mvn clean install

2. **Execute** each *Maven* **module** with this **Maven** *command*:

    >mvn spring-boot:run

### Utilizing Application

1. **Go** to the *Keycloak* server *URL*: `http://localhost:8181` and **login** with: [`admin:pass`]

2. **Open** the `shopping-microservices-realm` from the *list* of available **Realms**.

3. **Generate** new **Access** *Token* through the **Postman** *client* with these *settings*:

    ```
    Authorization -> Type = OAuth 2.0

    Configure New Token -> Token Name = keycloak-token
                           Grant Type = Client Credentials
                           Access Token URL = Value of "token_endpoint" from Realm Settings -> OpenID Config.
                           Client ID = spring-cloud-client
                           Client Secret = Value from Clients -> "spring-cloud-client" -> Credentials
                           Scope = openid offline_access
    ```

4. **Test** these *URLs* with this *Access* Token to **consume** the *APIs*:

    ```
    http://localhost:8080/api/product
    http://localhost:8080/api/order
    ```

### Monitoring Application

1. **Connect** to the *Mongo* DB at the following *URI*:

    ```
    mongodb://localhost:27017/product-data
    ```

2. **Connect** to the *MySQL* DBs at the following *URI*:

    ```
    jdbc:mysql://localhost:3306/order-data
    jdbc:mysql://localhost:3306/inventory-data
    ```

    ... with these **credentials**: [`root:dev-root`]

3. **Check** the *instances* **available** at the *Eureka* server *URL*:

   >`http://localhost:8761` or
   >`http://localhost:8080/eureka/web`

4. **Check** the *distributed* **tracing** at the *Zipkin* server *URL*:

   >`http://localhost:9411/zipkin`

<hr/>

## Production

For *production* purposes (**with** *Prometheues* and *Grafana*), **follow** as below:

### Running Application

1. **Create** the *Docker* **containers** with this **Maven** *command*:

    >mvn clean install jib:build

2. **Start** the entire **application** with this **Docker** *command*:

    >docker-compose --detach up

### Utilizing Application

1. **Go** to the *Keycloak* server *URL*: `http://localhost:8181` and **login** with: [`admin:pass`]

2. **Open** the `shopping-microservices-realm` from the *list* of available **Realms**.

3. **Generate** new **Access** *Token* through the **Postman** *client* with these *settings*:

    ```
    Authorization -> Type = OAuth 2.0

    Configure New Token -> Token Name = keycloak-token
                           Grant Type = Client Credentials
                           Access Token URL = Value of "token_endpoint" from Realm Settings -> OpenID Config.
                           (Replace `localhost` with `keycloak-auth`, i.e. the name of its Container service)
                           (Include `127.0.0.1 keycloak-auth` inside `C:\Windows\System32\drivers\etc\hosts`)
                           Client ID = spring-cloud-client
                           Client Secret = Value from Clients -> "spring-cloud-client" -> Credentials
                           Scope = openid offline_access
    ```

4. **Test** these *URLs* with this *Access* Token to **consume** the *APIs*:

    ```
    http://localhost:8080/api/product
    http://localhost:8080/api/order
    ```

### Monitoring Application

1. **Connect** to the *Mongo* DB at the following *URI*:

    ```
    mongodb://localhost:27017/product-data
    ```

2. **Connect** to the *PostgreSQL* DBs at the following *URI*:

    ```
    jdbc:postgresql://localhost:3306/order-data
    jdbc:postgresql://localhost:3306/inventory-data
    ```
    ... with these **credentials**: [`user:pass`]

3. **Check** the *instances* **available** at the *Eureka* server *URL*:

   >`http://localhost:8761` or
   >`http://localhost:8080/eureka/web`

4. **Check** the *distributed* **tracing** at the *Zipkin* server *URL*:

   >`http://localhost:9411/zipkin`

5. **Check** the *application* **metrics** at the *Prometheus* server *URL*:

   >`http://localhost:9090`

6. **Visualize** the *application* **activity** at the *Grafana* server *URL*:

   >`http://localhost:3000` 

   ... with these **credentials**: [`admin:pass`]

7. **Add** `Prometheus` as a **Data Source** for *Grafana*.

8. **Import** the `grafana-dashboard.json` *file* into the **Dashboards**.

<hr/>

**Thank** you for *using* it!

<hr/>