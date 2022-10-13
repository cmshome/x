package com.lxk.storm.bolt.rich;

import com.google.common.collect.Maps;
import com.lxk.storm.model.RepeatEvent;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * 订阅sentence spout发射的tuple流，实现分割单词
 * tuple：元组
 *
 * @author LiXuekai on 2020/9/14
 */
public class SplitSentenceBolt extends BaseRichBolt {

    /**
     * BaseRichBolt是IComponent和IBolt接口的实现
     * 继承这个类，就不用去实现本例不关心的方法
     */
    private OutputCollector collector;


    /**
     * prepare()方法类似于ISpout 的open()方法。
     * 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
     * 本例子和SentenceSpout类一样,SplitSentenceBolt类不需要太多额外的初始化,
     * 所以prepare()方法只保存OutputCollector对象的引用。
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
    }

    /**
     * SplitSentenceBolt核心功能就是在类IBolt定义的execute()方法，这个方法是在IBolt接口中定义的。
     * 每次Bolt从流接收一个订阅的 tuple，都会调用这个方法。
     * 本例中,收到的元组中查找“sentence”的值,
     * 并将该值拆分成单个的词,然后按单词发出新的tuple。
     */
    @Override
    public void execute(Tuple input) {
        // TODO Auto-generated method stub
        String sentence = input.getStringByField("sentence");
        String[] words = sentence.split(" ");
        String name = Thread.currentThread().getName();
        System.out.println("一、split sentence bolt：" + Arrays.toString(words) + ", current thread name:" + name);
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();

        treeMap.put("account", 3);
        treeMap.put("amountRange", 2);
        treeMap.put("amountSpecial", 1);
        treeMap.put("time", 4);
        RepeatEvent repeatEvent = RepeatEvent.builder()
                .dataId("xxx")
                .count(10)
                .risk(treeMap)
                .build();
        for (String word : words) {
            //向下一个bolt发射数据   emit：这单词就是"发射"的意思
            this.collector.emit(new Values(word, repeatEvent, treeMap));
        }
    }

    /**
     * splitSentenceBolt类定义一个元组流,每个包含一个字段(“word”)。
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("word", "repeatEvent", "dataMap"));
    }
}
