package com.stranger.lang;

import java.nio.charset.Charset;

public class ObjectTest {


    public static void main(String args[]){
        System.out.println(String.CASE_INSENSITIVE_ORDER.compare("12", "123"));
//        Charset charset = Charset.forName("UTF-8");
//        System.out.println(charset);
//        Object test = newInstance();
//        System.out.println(test.getClass());
//        System.out.println(newInstance().hashCode());
//        System.out.println(test.hashCode());

    }

    public static Object newInstance(){
        return new Object();
    }

}
