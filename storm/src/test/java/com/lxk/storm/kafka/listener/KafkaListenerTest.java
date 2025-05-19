package com.lxk.storm.kafka.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lxk.storm.kafka.config.KafkaConfig;
import com.lxk.storm.kafka.constants.LxkConstants;
import com.lxk.tool.util.JsonUtils;
import com.lxk.tool.util.TimeUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2021/3/22
 */
public class KafkaListenerTest {
    private static final int MAX = 5;
    private static final int TOTAL_TOPIC_PARTITION = 15;

    /**
     * 我测试的topic是15个分区，我启动15个producer，分别往特定的分区造数。
     * 看看n个消费者，在rebalance的时候，是怎么消费的。
     * 单单根据consumer的分区信息，怎么感觉和理论上不一致呢，那就再换个方式测试验证一下。
     */
    @Test
    public void producer() throws InterruptedException {
        for (int i = 0; i < TOTAL_TOPIC_PARTITION; i++) {
            int finalIndex = i;
            new Thread(() -> {
                System.out.println("producer " + finalIndex + " this run................");
                String name = "producer()-[" + finalIndex + "]";
                produce(name, finalIndex);
            }).start();
        }
        TimeUnit.MINUTES.sleep(100);
    }

    /**
     * 造数程序，都往指定的分区造数。
     *
     * @param name      name
     * @param partition partition
     */
    private void produce(String name, int partition) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.producerConf());
        while (true) {
            String now = TimeUtils.now();
            // 直接转json，竟然是{}，因为这个对象没得getter and setter方法，醉了，直接toString呢，打印的又太多了。醉了又。。。。
            ProducerRecord<String, String> record = new ProducerRecord<>(LxkConstants.TEST_TOPIC_NAME, partition, now, name);
            String partitionInfo = record.partition() + " ";
            System.out.println(now + " 生产者【" + name + "】  record partition info is " + partitionInfo);
            producer.send(record);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("sleep error....");
            }
        }
    }

    @Test
    public void rebalance1() throws InterruptedException {
        for (int i = 0; i < MAX; i++) {
            int finalIndex = i;
            new Thread(() -> {
                System.out.println("rebalance1 " + finalIndex + " this run................");
                consumer("rebalance1()-[" + finalIndex + "]");

            }).start();
        }
        TimeUnit.MINUTES.sleep(100);
    }

    @Test
    public void rebalance2() throws InterruptedException {
        for (int i = 0; i < MAX; i++) {
            int finalIndex = i;
            new Thread(() -> {
                System.out.println("rebalance2 " + finalIndex + " this run................");
                consumer("rebalance2()-[" + finalIndex + "]");
            }).start();
        }
        TimeUnit.MINUTES.sleep(100);
    }

    @Test
    public void rebalance3() throws InterruptedException {
        for (int i = 0; i < MAX; i++) {
            int finalIndex = i;
            new Thread(() -> {
                System.out.println("rebalance3 " + finalIndex + " this run................");
                consumer("rebalance3()-[" + finalIndex + "]");
            }).start();
        }
        TimeUnit.MINUTES.sleep(100);
    }

    /**
     * 启动消费者消费数据
     */
    private void consumer(String index) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " this run................");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.consumerConf());
        consumer.subscribe(Lists.newArrayList(LxkConstants.TEST_TOPIC_NAME), new MyRebalanceListener());

        //Runnable task = () -> {
        //    Set<TopicPartition> assignment = consumer.assignment();
        //    Set<Integer> set = assignment.stream().map(TopicPartition::partition).collect(Collectors.toSet());
        //    System.out.println(index + " 分区情况：" + name + "  当前consumer所占有分区信息：" + JsonUtils.parseObjToJson(set) + "  消费的recordInfoSet：" + JsonUtils.parseObjToJson(recordInfoSet));
        //};
        //
        //MonitorService.getMonitorExecutor().scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            Set<String> recordInfoSet = Sets.newHashSet();
            records.forEach(record -> {
                String s = record.partition() + "";
                recordInfoSet.add(s);
            });
            System.out.println(TimeUtils.now() + "  " + index + "  当前消费者消费的记录的所有分区信息 " + JsonUtils.parseObjToJson(recordInfoSet));
            try {
                //2秒一消费，因为造数是1秒一造，保证能消费到数据。
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recordInfoSet.clear();
        }
    }


}
