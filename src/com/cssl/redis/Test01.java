package com.cssl.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class Test01 {
    public static void main(String[] args) {

        System.out.println("准备连接redis");
        //根据参数连接redis服务器
        Jedis jd = new Jedis("192.168.10.130", 6379);
        //密码 ，如果没有则无需设置
        jd.auth("123456");
        //设置选择数据 默认就是第0个
        jd.select(0);
        //获取全部的key
        Set<String> keys = jd.keys("*");
        //输出key的值
        System.out.println("s:"+keys);
        System.out.println("size:"+keys.size());
        //设置值
        jd.set("hk","helloWorld");
        //设置有效时间
        jd.expire("hk",30);
        //取值
        String hk = jd.get("hk");
        System.out.println("hk:"+hk);
        System.out.println("时间："+jd.ttl("hk"));
        System.out.println("*****************");
        //删除
        jd.del("hk");
        String hk2 = jd.get("hk");
        System.out.println("hk2:"+hk2);

        System.out.println("************************");

        //实际运用
        if(jd.exists("name")) {
            System.out.println("exists:"+jd.get("name"));
            System.out.println("ttl:"+jd.ttl("name")+"s");
            //jd.del("age");
        }else {
            System.out.println("not exists:");
            jd.set("name","jack");
            jd.expire("name", 30);
        }
        System.out.println("**************************");
        //存集合
        jd.lpush("list","厉害");
        String list = jd.lindex("list", 0);
        String list1 = jd.rpop("list");
        System.out.println("0:"+list1);
        for (String s:jd.lrange("list",0,6)
        ) {
            System.out.println("s:"+s);

        }

    }
}
