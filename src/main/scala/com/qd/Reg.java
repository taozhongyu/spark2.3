package com.qd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Regu {

    public static void main(String[] args) {
        //匹配6位顺增
        String pattern = "(12(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){4}\\d";
        Pattern pa = Pattern.compile(pattern);
        String mc = "123456";
        Matcher ma = pa.matcher(mc);
        System.out.println("6位顺增 ：" + ma.matches());
        System.out.println("*******分割线*******");

        //匹配6位顺降
        pattern = "(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5}\\d";
        pa = Pattern.compile(pattern);
        mc = "654321";
        ma = pa.matcher(mc);
        System.out.println("6位顺降 ：" + ma.matches());
        System.out.println("*******分割线*******");

        //匹配6位顺增或顺降
        pattern = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5})\\d";
        pa = Pattern.compile(pattern);
        mc = "234567";
        ma = pa.matcher(mc);
        System.out.println("6位顺增或顺降 ：" + ma.matches());
        System.out.println("*******分割线*******");

        //匹配4-9位连续的数字
        pattern = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3,}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){3,})\\d";
        pa = Pattern.compile(pattern);
        mc = "123456789";
        ma = pa.matcher(mc);
        System.out.println("4-9位连续的数字 ：" + ma.matches());
        System.out.println("*******分割线*******");



    }
}