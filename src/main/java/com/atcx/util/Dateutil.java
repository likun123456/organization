package com.atcx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C), 2017-2022, MIDOU
 * ClassName: Dateutil
 * Author:   pangshu
 * Date:     2022/1/19 9:38
 * Version: 1.0
 * Description:
 */
public class Dateutil {
    /**
     * 判断日期格式和范围
     */
    public static boolean isDate(String date) {
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

    public static void main(String[] args) {
        /**
         * 日期格式正确
         */
        String date1 = "2014-01-03";
        System.out.println(isDate(date1));

        /**
         * 日期范围不正确---平年二月没有29号
         */
        String date2 = "2014-02-29";
        System.out.println(isDate(date2));
        /**
         * 日期月份范围不正确---月份没有13月
         */
        String date3 = "2014-13-03";
        System.out.println(isDate(date3));
        /**
         * 日期范围不正确---六月没有31号
         */
        String date4 = "2014-06-31";
        System.out.println(isDate(date4));
        /**
         * 日期范围不正确 ----1月超过31天
         */
        String date5 = "2014-01-32";
        System.out.println(isDate(date5));
        /**
         * 这个测试年份
         */
        String date6 = "0014-01-03";
        System.out.println(isDate(date6));
    }
}
