package com.lxk.storm.kafka;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.storm.kafka.config.KafkaConfig;
import com.lxk.tool.util.JsonUtils;
import com.lxk.tool.util.TimeUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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

        String ttm1 = "0|请求交易-async-lvj|524931|8080|xxxxxx|{\"橘右京\":\"不知好歹\",\"amount\":\"100\",\"TT\":\"AB\",\"lxk\":\"帅\",\"cus\":\"123\"}|{\"RC\": \"SUCCESS\",\"return_msg\": \"OK\"}|6|1568101860|1568101860123|11|22";
        String ttm2 = "0|响应交易-async-lvj|524932|8080|xxxxxx|{\"amount\":\"100\",\"TT\":\"AC\",\"lxk\":\"帅\",\"cus\":\"123\"}|{\"RC\": \"SUCCESS\",\"return_msg\": \"lxk-for-copy\"}|12|1568101860|1568101860123|22|11";
        List<String> list = Lists.newArrayList(
                //"a"
                //ttm1.replace("xxxxxx",format),
                ttm2.replace("xxxxxx",format)
                //"0|请求交易-async-lvj-3-4|52493|8080|lvj-ID-3-4|{\"amount\":\"100\",\"TT\":\"AB\",\"lxk\":\"帅\",\"cus\":\"123\"}|{\"RC\": \"SUCCESS\",\"return_msg\": \"OK\"}|22|1568101860|1568101860123|3|4",
                //"0|响应交易-async-lvj-3-4|52493|8080|lvj-ID-3-4|{\"amount\":\"100\",\"TT\":\"AC\",\"lxk\":\"帅\",\"cus\":\"123\"}|{\"RC\": \"SUCCESS\",\"return_msg\": \"OK\"}|11|1568101860|1568101860123|4|3"
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
    public void create() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.producerConf());
        send(producer);
    }


    @Test
    public void ddd() throws InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.producerConf());
        while (true){
            send(producer);
            System.out.println("sleep 1s ");
            TimeUnit.SECONDS.sleep(1);
        }

    }

    private void send(KafkaProducer<String, String> producer) {
        long nowS = TimeUtils.nowS();
        String ttm2 = "0|fhq_test1|188.188.8.111|10999|188.188.8.91|43558|grcb8583|{\"convers_id\": 12665389, \"msg_size\": 4132, \"mti\": \"\", \"AccOut\": \"22123342222\", \"SQEN_ID\": \"20211102\", \"host\": \"hash711363546\", \"amount\": \"3324.33\", \"TT\": \"A3211\", \"AccIn\": \"JYJK\", \"STAN\": \"2220\", \"F38\": \"交易成功\", \"F41\": \"\", \"mid\": \"\"}|{\"RC\": \"0001\"}||1|4132|0|0|0|0|0|0|3000|0|0|0|0|0|0|0|0|0|xxxxxx|1536646260394|2018-09-11T06:11:00|3166439535|||3166439515|||6|0|1";
        List<String> list = Lists.newArrayList(
                ttm2.replace("xxxxxx", "" + nowS)
        );

        for (String s : list) {
            System.out.println(s);
            ProducerRecord<String, String> record = new ProducerRecord<>("lxk", "asd", s);
            try {
                //producer.send(record); // 异步发送数据，先发到本地缓存池，等kafka自己的线程来消费，批量发送数据到kafka
                producer.send(record).get();//同步发送数据，一个个的发送，都需要发送的结果。
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
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
    public void ttt() {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
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


    @Test
    public void te() {
        Map<String, String> map = Maps.newHashMap();
        map.put("lxk", "");
        String s = JsonUtils.parseObjToFormatJson(map);
        System.out.println(s);
    }

}
