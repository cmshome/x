package com.lxk.storm.kafka.listener;

import com.google.common.collect.Sets;
import com.lxk.tool.util.JsonUtils;
import com.lxk.tool.util.TimeUtils;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.TreeSet;

/**
 * kafka的rebalance
 * （1）消费者组中新添加消费者读取到原本是其他消费者读取的消息
 * （2）消费者关闭或崩溃之后离开群组，原本由他读取的partition将由群组里其他消费者读取
 * （3）当向一个Topic添加新的partition，会发生partition在消费者中的重新分配
 *
 * @author LiXuekai on 2021/3/19
 */
public class MyRebalanceListener implements ConsumerRebalanceListener {

    /**
     * rebalance开启新一轮的重平衡前会调用，一般用于手动提交位移，及审计功能
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        TreeSet<String> treeSet = Sets.newTreeSet();
        partitions.forEach(partition -> treeSet.add(partition.toString()));
        System.out.println("kafka rebalance  start. onPartitionsRevoked " + TimeUtils.now() + JsonUtils.parseObjToFormatJson(treeSet));
    }

    /**
     * rebalance在重平衡结束后会调用，一般用于消费逻辑处理
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        TreeSet<String> treeSet = Sets.newTreeSet();
        partitions.forEach(partition -> treeSet.add(partition.toString()));
        System.out.println("kafka rebalance start. onPartitionsAssigned " + TimeUtils.now() + JsonUtils.parseObjToFormatJson(treeSet));
    }
}
