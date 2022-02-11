package com.stranger.lang;

public class ObjectTest {


    public static void main(String args[]){
        Object test = newInstance();
        System.out.println(test.getClass());
        System.out.println(newInstance().hashCode());
        System.out.println(test.hashCode());

    }

    public static Object newInstance(){
        return new Object();
    }

}
