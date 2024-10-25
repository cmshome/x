package com.lxk.jdk.file.scanner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lxk.tool.util.JsonUtils;
import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 读一个json 然后删掉不要的字段，然后按照时间字段去统计，然后输出csv
 *
 * @author LiXuekai on 2022/1/13
 */
public class LxkTest {
    /**
     * <pid, message>
     */
    private final Map<String, List<Message>> map = Maps.newHashMap();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSS");

    private static final long start = 1633017600;
    private static final long end = 1638287999;

    /**
     * 351360 个点
     * 273777 个详细交易
     */
    @Test
    public void a() {
        System.out.println(61 * 24 * 60 * 4);
    }


    @Test
    public void time() {
        String s = "2021-10-01T04:37:34,703";
        LocalDateTime localDateTime = LocalDateTime.parse(s, FORMATTER);
        long toS = TimeUtils.toS(localDateTime);
        System.out.println(toS);
    }

    @Test
    public void ttt() {
        find();
        ana();
    }

    /**
     * 545 个 pid
     */
    private void ana() {
        for (String spid : map.keySet()) {
            List<Res> resList = Lists.newArrayList();
            List<Message> list = map.get(spid);
            int size = list.size();
            String fileName = size + "-" + spid;

            for (long i = start; i <= end; ) {
                Res res = new Res();
                res.setTime(i);
                i = i + 15;
                resList.add(res);
            }

            int pointTotal = resList.size();

            for (Message message : list) {

                boolean exist = false;
                List<String> result = message.getResult();
                if (result != null && !result.isEmpty()) {
                    exist = true;
                }
                long sec = message.getSec();
                for (Res res : resList) {
                    long time = res.getTime();
                    if (sec >= time && sec < time + 15) {
                        long l = res.getCount() + 1;
                        res.setCount(l);
                        if (exist) {
                            long x = res.getArrayCount() + 1;
                            res.setArrayCount(x);
                        }
                    }
                }
            }

            long sum = 0;
            for (Res res : resList) {
                sum = sum + res.getCount();
                if (res.getCount() != res.getArrayCount()) {
                    System.out.println("俩值不等。。。。");
                }
            }
            System.out.println("统计的点数：" + pointTotal + " 实际的交易个数：" + sum + "  准备写文件：" + fileName + ".csv");

            csv(resList, fileName);
        }
    }

    private void csv(List<Res> all, String fileName) {
        PrintWriter pw = null;
        try {
            File file = new File("/Users/fang/Downloads/data/s/" + fileName + ".csv");
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            for (Res res : all) {
                pw.println(res.getTime() + "," + res.getCount() + "," + res.getArrayCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }

    }

    private void find() {
        String file = "/Users/fang/Downloads/data/x.xxx";
        try (Scanner scanner = new Scanner(new File(file), StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Message message = JsonUtils.parseJsonToObj(line, Message.class);
                if (message == null) {
                    continue;
                }
                LocalDateTime localDateTime = LocalDateTime.parse(message.getTime(), FORMATTER);
                long toS = TimeUtils.toS(localDateTime);
                message.setSec(toS);
                String spid = message.getSpid();
                List<Message> list = map.computeIfAbsent(spid, k -> Lists.newArrayList());
                list.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void yyy() {
        find();
        System.out.println(map.size());
    }


    @Test
    public void test() {
        load();
    }

    private void load() {
        String file = "/Users/fang/Downloads/data/new_es_2.json";
        File x = new File("/Users/fang/Downloads/data/x.xxx");
        FileWriter fw = null;
        try (Scanner scanner = new Scanner(new File(file), StandardCharsets.UTF_8.name())) {
            fw = new FileWriter(x, true);
            PrintWriter pw = new PrintWriter(fw);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Message message = JsonUtils.parseJsonToObj(line, Message.class);
                if (message == null) {
                    continue;
                }
                String json = JsonUtils.parseObjToJson(message);
                pw.println(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
