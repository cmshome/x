package com.lxk.storm.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * kafka测试相关的配置
 *
 * @author LiXuekai on 2021/3/22
 */
public class KafkaConfig {

    /**
     * 消费kafka时候的配置
     */
    public static Properties consumerConf() {
        Properties conf = new Properties();
        conf.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "1.1.1.1:1");
        conf.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "lxk-test");
        conf.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        conf.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        conf.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        conf.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        conf.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        //默认值是10秒，影响kafka的rebalance
        conf.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //默认值是5分钟，影响kafka的rebalance
        conf.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000");
        //默认值是30秒，影响kafka的rebalance
        conf.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "3000");


        // 一次 poll 能取到的最大量的数据
        conf.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");


        //conf.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "100");


        // 5 分钟
        //conf.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "300000");

        return conf;
    }

    /**
     * 生产者的配置
     */
    public static Properties producerConf() {
        Properties conf = new Properties();
        conf.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        conf.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "1.1.1.1:1");
        conf.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        conf.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return conf;
    }

}
