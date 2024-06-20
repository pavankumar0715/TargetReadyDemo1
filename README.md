# Target Ready Demo 1

# Kafka Message Tracing with Zipkin and Grafana

## Problem Statement 1
When a Kafka message goes to multiple microservices where each make some changes and enhancements, its hard to keep track of where it fails and what is the current status of a message.  Build a grafana dashboard which uses zipkin/jeager to track the message throughout its life cycle

## Overview

This demo project showcases Zipkin to trace Kafka messages across three microservices: `BankService`, `PaymentService`, and `OrderService`. The purpose is to demonstrate the need and use of a tracing system to monitor and debug message flows in a microservices architecture.




## Microservices Architecture

The project consists of three microservices:


1. **OrderService**: Receives customer orders and sends order messages
2. **PaymentService**: Receives order and sends payment messages
3.  **BankService**: Receives payment messages and sends invoices

![Screenshot 2024-06-06 175426](https://github.com/pavankumar0715/TargetReadyDemo1/assets/114218468/c865a45a-f5b9-4572-ae03-098ed63a1e91)

Messages are exchanged between these services via Apache Kafka.

## Technology Stack
1. Apache Kafka
2. Brave
3. Zipkin
   
## Tracing System

### Zipkin

[Zipkin](https://zipkin.io/) is a distributed tracing system that helps gather timing data needed to troubleshoot latency problems in microservice architectures.

## Prerequisites

- Java 17 (for microservices)
- Maven (for building Java projects)

## Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/pavankumar0715/TargetReadyDemo1.git
   cd TargetReadyDemo1/ecommerce-management
   ```

2. **Start the Environment**

   On 3 terminals, run the following:

   ```powershell
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   ```

   ```powershell
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

   ```powershell
   java -jar .\zipkin-server-3.3.0-exec.jar
   ```

   This will start the following services:

   - Apache Kafka
   - Zipkin

3. **Build the Microservices**

Build and run the microservices

## Configuration

### Zipkin

Zipkin is configured to collect traces from the microservices. Each microservice is set up to send tracing data to Zipkin.

## Running the Demo

1. **Send Messages**
   You can use tools like Postman or cURL to send requests to the microservices. Here are some example requests:

   ```bash
     curl -X POST http://localhost:8080/target/orders -d
     '{"orderId": "1001","amount": 102.0,"bank": "SBI","stock": 5}' -H "Content-Type: application/json"
   ```


2. **View Traces**
   - Access Zipkin UI at `http://localhost:9411` to view the traces.

## Example Scenario

1. **Order Creation**: An order is created in the `OrderService`.
2. **Payment Processing**: The `PaymentService` processes the payment for the order.
3. **Bank Transaction**: The `BankService` processes the transaction and generates the invoice.

Each of these actions generates tracing data that is collected by Zipkin allowing you to see the flow of messages and identify any bottlenecks or issues.


![Zipkin Demo](Pictures/zipkin1.png)

## Conclusion

This demo project illustrates the importance of using a tracing system like Zipkin to monitor and debug message flows in a microservices architecture. By integrating Zipkin with Grafana, you gain powerful visualization capabilities to enhance your observability and troubleshooting efforts.


## Problem Statement 2
Monitor Database changes and store the data change events to Kafka topics

## Overview
This demo project showcases Kafka connect to capture changes from a relational database such as PostgreSQL in real-time. Debezium postgres connector is used to monitor database transaction logs and publish data change events to Kafka topics.

## Architecture
Orders received are sent to Postgres database by Order Service and then the database changes are monitored by Debezium Postgres Connectors and then sent to Kafka Topic dbChange_orders by Kafka Connect.

![Untitled Diagram](https://github.com/pavankumar0715/TargetReadyDemo1/assets/114218468/c1f909be-d2c9-4e26-9d72-23aed91adf55)

## Technology Stack
1. Kafka Connect
2. Postgres Sql
3. Debezium Connector

## Prerequisites
- Apache Kafka


## Setup
1. Download the Debezium Postgres connector and put it in plugins folder.

2. Configuration for Connect-distributed
   ```
   bootstrap.servers=kafka-broker1:9092,kafka-broker2:9092
   group.id=connect-cluster-group
   config.storage.topic=connect-configs
   offset.storage.topic=connect-offsets
   status.storage.topic=connect-status
   key.converter=org.apache.kafka.connect.json.JsonConverter
   value.converter=org.apache.kafka.connect.json.JsonConverter
   plugin.path=/usr/local/share/kafka/plugins

   ```
3. Configuration for Debezium-Postgres connector
   ```
      {
     "name": "postgres-source-connector",
     "config": {
       "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
       "tasks.max": "1",
       "database.hostname": "localhost",
       "database.port": "5432",
       "database.user": "myuser",
       "database.password": "mypassword",
       "database.dbname": "mydb",
       "database.server.name": "dbserver1",
       "table.include.list": "public.mytable",
       "plugin.name": "pgoutput",
       "database.history.kafka.bootstrap.servers": "localhost:9092",
       "database.history.kafka.topic": "schema-changes.mydb",
       "key.converter.schemas.enable": "false",
       "value.converter.schemas.enable": "false",
       "snapshot.mode": "initial"
     }
    }


   ```
   4. Run Kafka connect
      ```
      /bin/connect-distributed.sh /path/to/your/connect-distributed.properties
      ```
   6. Deploy the connector by sending a Post request to Kafka Connect API
      ```
      curl -X POST -H "Content-Type: application/json" --data '{
        "name": "postgres-sink-connector",
        "config": Debezium Config Details
      }' http://localhost:8083/connectors
      ```
   7. Monitor Kafka Connect
      ```
      curl -X GET http://localhost:8083/connectors/postgres-source-connector/status
      ```

   ## Running the Demo
   1. **Send Messages**
   You can use tools like Postman or cURL to send requests to the microservices. Here are some example requests:

   ```bash
     curl -X POST http://localhost:8080/target/orders -d
     '{"orderId": "1001","amount": 102.0,"bank": "SBI","stock": 5}' -H "Content-Type: application/json"
   ```
   2. **Listening to topic**
   Listening to the topic dbChanges_tablename will give the data changes in the database.

   ## Conclusion
   This demo project illustrates the importance of tracking database changes which can be further used in Realtime Inventory management and Order Tracking and Management.
   
      

## References

- [Zipkin Documentation](https://zipkin.io/pages/documentation.html)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Debezium Documentation](https://debezium.io/documentation/)
