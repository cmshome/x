package com.lxk.storm.kafka.listener;

import com.google.common.collect.Lists;
import com.lxk.storm.kafka.config.KafkaConfig;
import com.lxk.tool.util.TimeUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2022/11/2
 */
public class MyProducerTest {

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
        String ttm2 = "";
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
}
