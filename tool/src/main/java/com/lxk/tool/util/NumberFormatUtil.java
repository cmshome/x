package com.lxk.tool.util;

import java.text.DecimalFormat;

/**
 * @author LiXuekai on 2023/8/18
 */
public class NumberFormatUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");

    public static String format(float f){
        return DECIMAL_FORMAT.format(f);
    }

}
