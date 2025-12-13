=== src/main/resources/db/migration/V1__create_kafka_log_table.sql ===
-- Oracle-compatible DDL
CREATE TABLE KAFKA_LOG
(
    MESSAGE_ID   VARCHAR2(64) PRIMARY KEY,
    TOPIC        VARCHAR2(200),
    KEY          VARCHAR2(200),
    PAYLOAD      CLOB,
    STATUS       VARCHAR2(400),
    PARTITION_ID NUMBER,
    OFFSET_VALUE NUMBER,
    LEADER_NODE  NUMBER,
    REPLICAS     VARCHAR2(400),
    ISR          VARCHAR2(400),
    CREATED_AT   TIMESTAMP,
    UPDATED_AT   TIMESTAMP
);


-- Indexes for common queries
CREATE INDEX IDX_KAFKALOG_TOPIC ON KAFKA_LOG (TOPIC);
CREATE INDEX IDX_KAFKALOG_STATUS ON KAFKA_LOG (STATUS);


=== README.md ===
# Spring Boot Kafka Oracle Logging


Quick start:
1.
Update `application.yml`
with Oracle DB credentials and Kafka bootstrap servers.
    2.Ensure Oracle DB is available;
run
the SQL in `src/main/resources/db/migration/V1__create_kafka_log_table.sql` if you don't want JPA to create the table.
3. Create the Kafka topics: `products-topic`, `orders-topic`, `companies-topic`.
4. Build: `mvn clean package` and run: `java -jar target/*.jar`.


API endpoints:
- POST /api/products -> send product payload
- POST /api/orders -> send order payload
- POST /api/companies -> send company payload


Notes:
- This example uses `producer.partitionsFor(...)` to fetch replication info; that requires valid Kafka broker reachability.
- Oracle JDBC driver must be available at runtime (ojdbc11 in runtime scope).