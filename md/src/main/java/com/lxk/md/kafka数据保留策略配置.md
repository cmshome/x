Kafka的数据保留策略配置涉及时间、大小和压缩策略，以下为详细步骤及注意事项：



首先，我需要找到Kafka的配置文件，应该是server.properties吧。在这个文件里，需要设置一些参数。常见的参数可能有log.retention.hours，log.retention.minutes，或者log.retention.ms。这三个都是用来控制日志保留时间的，但它们的优先级是怎样的呢？可能log.retention.ms的优先级最高，因为更精确。比如，如果同时设置了小时和毫秒，可能毫秒的那个会生效。那默认情况下，Kafka的保留时间是多久呢？好像默认是7天，也就是168小时。

然后是日志大小的保留策略，对应的参数是log.retention.bytes，这个参数指定了每个分区保留的最大字节数。比如设置为1GB的话，超过这个大小就会删除旧的数据。不过这里有个问题，如果同时启用了时间和大小两种策略，Kafka会怎么处理呢？应该是哪个条件先满足就触发删除吧？比如数据超过了大小限制，或者超过了时间限制，都会被删除。

另外，可能还有一个参数是log.cleanup.policy，默认是delete，也就是删除旧数据。但还有compact策略，用于压缩日志，只保留每个键的最新值。这个compact策略可能适合某些场景，比如需要维护最新的状态。不过如果我要配置的是保留策略，可能主要还是用delete。不过需要确认log.cleanup.policy的设置是否正确，如果是compact的话可能不会按时间或大小删除数据。

然后，这些参数是全局配置的吗？也就是对所有的topic都生效。不过有时候可能需要为某个特定的topic设置不同的策略，这时候可以在创建topic的时候指定参数。比如用kafka-topics.sh脚本创建时，加上--config参数，设置retention.ms或者retention.bytes。这样的话，这个topic就会覆盖全局的配置，使用自己的保留策略。

接下来，我需要考虑如何验证配置是否生效。可能需要创建一个测试topic，设置较小的保留时间，比如1分钟，然后发送一些消息，过一段时间看看是否被删除。或者查看日志段的文件时间戳，确认是否被清理。另外，Kafka的日志清理是由一个后台线程定期执行的，默认是5分钟检查一次，这个参数是log.retention.check.interval.ms，默认是300000毫秒。如果测试的时候可能需要调整这个时间，让清理更频繁，方便观察效果。






---

### **1. 主要配置参数**
- **全局配置**（`server.properties`文件）：
    - **基于时间保留**：
        - `log.retention.hours`（默认168，即7天）
        - `log.retention.minutes`（优先级高于小时）
        - `log.retention.ms`（优先级最高，如 `log.retention.ms=86400000` 表示1天）
    - **基于大小保留**：
        - `log.retention.bytes`（每个分区的最大字节数，如 `log.retention.bytes=1073741824` 表示1GB）
    - **清理策略**：
        - `log.cleanup.policy=delete`（默认，删除旧数据）或 `compact`（压缩保留最新键值）。
    - **检查间隔**：
        - `log.retention.check.interval.ms=300000`（默认5分钟检查一次）。
    - **日志段配置**：
        - `log.segment.bytes=1073741824`（默认1GB，单个日志段大小）。

- **Topic级别配置**（覆盖全局设置）：
  ```bash
  # 创建Topic时指定参数
  bin/kafka-topics.sh --create --topic my_topic --partitions 1 --replication-factor 1 \
    --config retention.ms=3600000 \
    --config retention.bytes=536870912

  # 动态修改已有Topic配置
  bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics \
    --entity-name my_topic --alter --add-config retention.ms=3600000
  ```

---

### **2. 策略生效规则**
- **删除条件**：时间或大小任一条件满足即触发删除。
- **日志段机制**：删除以日志段为单位，仅当段内所有消息过期才会删除整个段。
- **参数优先级**：
    - 时间策略：`ms > minutes > hours`。
    - 若同时设置时间和大小，两者独立生效，任一条件触发即删除。

---

### **3. 验证与测试**
1. **创建测试Topic**：
   ```bash
   bin/kafka-topics.sh --create --topic test_retention --config retention.ms=60000 \
     --config retention.bytes=1048576
   ```
2. **生产消息**：
   ```bash
   bin/kafka-console-producer.sh --topic test_retention --bootstrap-server localhost:9092
   ```
3. **观察日志清理**：
    - 检查日志目录（`/tmp/kafka-logs/test_retention-0`），观察旧日志段是否被删除。
    - 使用`kafka-log-dirs.sh`查看分区大小：
      ```bash
      bin/kafka-log-dirs.sh --describe --bootstrap-server localhost:9092 --topic-list test_retention
      ```

---

### **4. 注意事项**
- **动态配置**：Kafka 1.1+ 支持动态修改Topic参数（无需重启Broker）。
- **磁盘监控**：保留策略不替代磁盘监控，需配合报警机制防止磁盘写满。
- **数据安全**：
    - 避免设置过短的时间或过小的尺寸，防止意外数据丢失。
    - 重要数据可结合日志压缩（`cleanup.policy=compact`）或外部归档。

---

### **5. 示例配置**
```properties
# server.properties 全局配置
log.retention.ms=604800000  # 7天
log.retention.bytes=10737418240  # 10GB
log.cleanup.policy=delete
log.retention.check.interval.ms=300000  # 5分钟检查一次
log.segment.bytes=268435456  # 256MB/段，更频繁触发清理
```

---

通过合理配置时间和大小策略，结合业务需求调整参数，可有效管理Kafka数据存储与清理。