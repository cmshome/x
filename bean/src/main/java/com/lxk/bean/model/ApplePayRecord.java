package com.lxk.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 苹果支付记录
 *
 * @author LiXuekai on 2021/5/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplePayRecord {
    private String account = "me";
    /**
     * 消费的时间
     */
    private LocalDate localDate;

    /**
     * 消费的金额
     */
    private int amount;


    public ApplePayRecord(LocalDate localDate, int amount) {
        this.localDate = localDate;
        this.amount = amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = sf.format(localDate);
        String pay = amount + "";
        int amountLength = pay.length();
        if (amountLength == 1){
            pay = "  " + pay;
        } else if (amountLength == 2){
            pay = " " + pay;
        }
        return account + " 于 " + format + " 充值 " + pay + " ¥。";
    }



}
