package com.lxk.jdk8.date;

import com.google.common.collect.Lists;
import com.lxk.bean.model.ApplePayRecord;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 苹果支付记录
 *
 * @author LiXuekai on 2021/5/17
 */
public class ApplePayRecordTest {

    @Test
    public void total() {
        List<ApplePayRecord> records = wangZheRongYao();
        Collections.reverse(records);
        records.forEach(record -> System.out.println(record.toString()));
        int sum = records.stream().mapToInt(ApplePayRecord::getAmount).sum();

        //int sum = records.stream().map(ApplePayRecord::getAmount).reduce(0, Integer::sum);

        System.out.println();
        System.out.println("你竟然已经充值了这么多：" + sum + "¥ ，简直不可原谅啊！");
    }


    /**
     * "王者荣耀"充值记录
     *
     * @return list
     */
    private List<ApplePayRecord> wangZheRongYao() {
        List<ApplePayRecord> list = Lists.newArrayList();
        list.add(new ApplePayRecord(LocalDate.of(2022, 5, 24), 37));
        list.add(new ApplePayRecord(LocalDate.of(2022, 4, 17), 46));
        list.add(new ApplePayRecord(LocalDate.of(2021, 12, 8), 12));
        list.add(new ApplePayRecord(LocalDate.of(2021, 11, 11), 5));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 30), 68));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 30), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 28), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 26), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 26), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 21), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 17), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 15), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 14), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 13), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 12), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 10, 9), 99));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 30), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 29), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 28), 2));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 23), 30));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 21), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 20), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 9, 19), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 6, 23), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 6, 14), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 6, 6), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 6, 5), 6));
        list.add(new ApplePayRecord(LocalDate.of(2021, 5, 22), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 5, 20), 1));
        list.add(new ApplePayRecord(LocalDate.of(2021, 5, 1), 41));
        list.add(new ApplePayRecord(LocalDate.of(2021, 4, 1), 57));
        list.add(new ApplePayRecord(LocalDate.of(2021, 3, 1), 7));
        list.add(new ApplePayRecord(LocalDate.of(2021, 2, 1), 14));
        list.add(new ApplePayRecord(LocalDate.of(2021, 1, 1), 47));
        list.add(new ApplePayRecord(LocalDate.of(2020, 12, 1), 6));
        list.add(new ApplePayRecord(LocalDate.of(2020, 9, 1), 51));
        list.add(new ApplePayRecord(LocalDate.of(2020, 8, 1), 6));
        list.add(new ApplePayRecord(LocalDate.of(2020, 7, 1), 39));
        list.add(new ApplePayRecord(LocalDate.of(2020, 3, 1), 45));
        list.add(new ApplePayRecord(LocalDate.of(2020, 1, 1), 45));
        list.add(new ApplePayRecord(LocalDate.of(2019, 10, 1), 45));
        list.add(new ApplePayRecord(LocalDate.of(2019, 6, 1), 36));
        list.add(new ApplePayRecord(LocalDate.of(2019, 4, 1), 45));
        list.add(new ApplePayRecord(LocalDate.of(2019, 2, 1), 45));
        list.add(new ApplePayRecord(LocalDate.of(2018, 11, 9), 6));
        return list;
    }
}
