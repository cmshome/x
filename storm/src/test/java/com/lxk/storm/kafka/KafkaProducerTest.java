package com.lxk.storm.kafka;

import com.google.common.collect.Lists;
import com.lxk.storm.kafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * kafka 发数据
 *
 * @author LiXuekai on 2020/7/20
 */
public class KafkaProducerTest {
    private boolean xml = false;
    private boolean all = true;

    @Test
    public void producer() {
        String toTopic = "a_x_test_lxk";
        produceOnce(toTopic);
    }

    @Test
    public void producerAll() {
        xml = true;
        String toTopic = "x_test_lxk";
        produceOnce(toTopic);
    }

    @Test
    public void producerXml() {
        xml = true;
        all = false;
        String toTopic = "x_test_lxk";
        produceOnce(toTopic);
    }

    private void produceOnce(String toTopic) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.producerConf());
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String ttm2 = "";
        List<String> list = Lists.newArrayList(
                //"a"
                //ttm1.replace("xxxxxx",format),
                ttm2.replace("xxxxxx",format)
        );
        if (!all) {
            list.clear();
        }
        //if (xml) {
        //    list.add("");
        //}

        for (String s : list) {
            ProducerRecord<String, String> record = new ProducerRecord<>(toTopic, "asd", s);
            try {
                //producer.send(record); // 异步发送数据，先发到本地缓存池，等kafka自己的线程来消费，批量发送数据到kafka
                producer.send(record).get();//同步发送数据，一个个的发送，都需要发送的结果。
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        //会堵塞，直到record发送实际完成。
        producer.close();
    }

    @Test
    public void aaa() {

        Properties props = new Properties();
        props.put("bootstrap.servers", "1.1.1.1:1");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++){
            producer.send(new ProducerRecord<>("lxk", Integer.toString(i), Integer.toString(i)));
        }

        producer.close();

    }

    @Test
    public void always() throws InterruptedException {
        String toTopic1 = "a_x_test_lxk";
        String toTopic2 = "x_test_lxk_2";
        Random random = new Random();

        while (true) {
            TimeUnit.SECONDS.sleep(random.nextInt(4));
            produceOnce(toTopic1);
            //produceOnce(toTopic2);
            System.out.println("send 2........");
        }
    }

}
