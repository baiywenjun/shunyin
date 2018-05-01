package com.shunyin.common.util;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/28 10:36
 */
public class MoneyUtil {

    public static Integer toCent(Float money){
        Float f = money * 100;
        return f.intValue();
    }

}
