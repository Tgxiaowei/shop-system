# AGENTS.md

## Cursor Cloud specific instructions

### Project Overview

Spring Cloud microservices e-commerce platform (`com.shop`) with 5 runnable services and 2 library modules. Uses JDK 8, Spring Boot 1.5.x, and Maven.

### Prerequisites

- **JDK 8** (`openjdk-8-jdk`): Required. The project targets Java 1.8 and is incompatible with JDK 11+.
- **Maven 3.x**: Build tool.
- **MySQL 8.0**: Must have SSL disabled (`/etc/mysql/conf.d/disable-ssl.cnf` with `ssl=0`) because the old MySQL connector 5.1.43 cannot negotiate TLS with MySQL 8.
- **Redis 7.x**: Default config on port 6379 (no auth).

### Build

```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
# Build all parent POM modules (base-service, register-service, config-service, feign-client, cust-service, trans-service):
mvn clean install -DskipTests -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8
# Build redis-service separately (not in parent POM modules):
cd redis-service && mvn clean install -DskipTests -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8
```

### Non-obvious gotchas

- **`redis-service` is NOT listed in the parent POM `<modules>`**: It must be built independently.
- **`src/main/resource` (singular)**: All modules use non-standard `src/main/resource` instead of Maven's `src/main/resources`. When running services via `java -cp`, include `src/main/resource` on the classpath.
- **No `spring-boot-maven-plugin`**: Services must be run with `java -cp` using the full dependency classpath, not `mvn spring-boot:run` or executable JARs.
- **MySQL connector 5.1.43 vs MySQL 8.0**: The old connector doesn't support the `tx_isolation` → `transaction_isolation` rename in MySQL 8. A workaround is to replace the JAR in the Maven cache with version 5.1.49: `cp ~/.m2/.../5.1.49/mysql-connector-java-5.1.49.jar ~/.m2/.../5.1.43/mysql-connector-java-5.1.43.jar`.
- **`mock.flag=1`** in cust-service: Enables mock mode for SMS verification. Use code `"8888"` for registration/login bypass.
- **Maven settings**: Custom (defunct) repos in POMs (`to.zhongmafu.com`) are unreachable. Configure `~/.m2/settings.xml` to mirror them to Maven Central.
- **Config-service native profile**: Uses `spring.profiles.active=native` to serve config from `classpath:/config-repo/` (local files, no Git).

### Service startup order and ports

1. **register-service** (Eureka): port 8888 — must start first
2. **config-service** (Config Server): port 8000 — registers with Eureka
3. **redis-service**: port 8009 — registers with Eureka, needs Redis on 6379
4. **cust-service**: port 8002 — needs config-service, Eureka, redis-service, MySQL
5. **trans-service**: port 8001 — needs config-service and Eureka

### Running services

Each service is started with:
```bash
cd <service-dir>
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt
CP=$(cat cp.txt):target/classes:src/main/resource
$JAVA_HOME/bin/java -cp "$CP" com.shop.<package>.<MainClass>
```

### Database

MySQL database `shop` with table `t_cust_customer`. Credentials: `root`/`root` on `127.0.0.1:3306`.

### Tests

- Unit tests: `mvn test -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8 -pl base-service,register-service,config-service,feign-client,trans-service`
- Integration tests (`cust-service`, `redis-service`): Require all services running. These are `@SpringBootTest` tests that need config-service for datasource properties. The `cust-service` `LoginServiceTest` and `redis-service` `RedisClientTest` fail at build time because they need runtime infrastructure.

### Key API endpoints

- `POST /login/register` on :8002 — Register customer (body: `{"mobile","msgCode":"8888","psw","pswRept"}`)
- `POST /login/login` on :8002 — Login (body: `{"mobile","psw"}`)
- `GET /cust/test` on :8002 — Lookup test customer
- `GET /trans/getjson` on :8001 — Get config value from config-service
- `POST /redis/set?key=&value=` on :8009 — Set Redis key
- `POST /redis/get?key=` on :8009 — Get Redis key
