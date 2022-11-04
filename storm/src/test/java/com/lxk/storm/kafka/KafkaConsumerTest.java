package com.lxk.storm.kafka;

import com.google.common.collect.Lists;
import com.lxk.storm.kafka.config.KafkaConfig;
import com.lxk.tool.util.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消费数据
 *
 * @author LiXuekai on 2020/7/20
 */
public class KafkaConsumerTest {


    @Test
    public void raw() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.consumerConf());
        consumer.subscribe(Lists.newArrayList("lxk"));
        try {
            while (true) {
                // 100 是超时时间，毫秒级别。
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(3));
                Set<TopicPartition> assignment = consumer.assignment();

                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    int partition = record.partition();
                    String topic = record.topic();
                    long offset = record.offset();
                    String value = record.value();
                    System.out.println(value);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            consumer.close();
        }
    }




    @Test
    public void test() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.consumerConf());
        consumer.subscribe(Lists.newArrayList("x"));

        try {
            while (true) {
                // 100 是超时时间，毫秒级别。
                ConsumerRecords<String, String> records = consumer.poll(30000);
                Set<TopicPartition> assignment = consumer.assignment();

                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    int partition = record.partition();
                    String topic = record.topic();
                    long offset = record.offset();
                    String value = record.value();
                    System.out.println(key + "    "   +  value);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            consumer.close();
        }

    }

    /**
     * 同时启动15个线程，每个线程都启动一个consumer去消费kafka的相同topic，看分区情况。
     * topic一共有15个分区，启动18个线程，18个消费者去消费。
     *
     * 分区情况：Thread-3  [1]
     * 分区情况：Thread-16  [12]
     * 分区情况：Thread-13  [2]
     * 分区情况：Thread-14  [7]
     * 分区情况：Thread-12  [14]
     * 分区情况：Thread-0  [6]
     * 分区情况：Thread-17  [8]
     * 分区情况：Thread-5  [10]
     * 分区情况：Thread-8  [4]
     * 分区情况：Thread-1  [9]
     * 分区情况：Thread-6  [5]
     * 分区情况：Thread-4  []
     * 分区情况：Thread-11  [13]
     * 分区情况：Thread-2  []
     * 分区情况：Thread-7  []
     * 分区情况：Thread-9  [3]
     * 分区情况：Thread-10  [0]
     * 分区情况：Thread-15  [11]
     */
    @Test
    public void assignment() throws InterruptedException {
        for (int i = 0; i < 18; i++) {
            int finalIndex = i;
            new Thread(()->{
                System.out.println(finalIndex + " this run................");
                consumer();
            }).start();
        }
        TimeUnit.SECONDS.sleep(100);
    }

    private void consumer() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " this run................");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.consumerConf());
        consumer.subscribe(Lists.newArrayList("x"));


        Set<TopicPartition> assignment = consumer.assignment();
        Set<Integer> set = assignment.stream().map(TopicPartition::partition).collect(Collectors.toSet());
        System.out.println("分区情况：" + name  + "  "+ JsonUtils.parseObjToJson(set));

        while (true){
            // 这个方法得一直不停的poll才行，得 while true 一下，
            consumer.poll(100);
        }
    }

}
