package com.cssl.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

public class Test02 {


    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        //GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(5); //最大
        config.setMaxIdle(1);//空闲数
        config.setMaxWaitMillis(-1);//最大等待
        //注意超时时间外网不要设太低
        //JedisPool pool = new JedisPool(config, "localhost", 6379, 60);
        JedisPool pool = new JedisPool(config, "192.168.10.130",
                6379, 60000, "123456");
        Jedis jd = pool.getResource();
        System.out.println("连接成功！"+jd);
        System.out.println("name:"+jd.get("name"));

        System.out.println("*******集合*******");
        for(String s: jd.lrange("list",0,5)){
            System.out.println("s:"+s);
        }
        System.out.println("**********************");
        for(int i=0;i<jd.llen("list");i++){
            System.out.println("s:"+jd.lindex("list",i));
        }
        System.out.println("****************保存对象***************");
        //保存对象
        Person p1=new Person("1011","admin11",181);
        Person p2=new Person("102","admin2",180);
        Person p3=new Person("103","admin3",1800);
        //将对象信息保存二进制数据
        jd.set("p1".getBytes(),SerializeUtil.serialize(p1));
        jd.expire("p1".getBytes(),300);

        //从redis读取
        byte[] value = jd.get("p1".getBytes());
        Object obj = SerializeUtil.unserialize(value);
        if(obj!=null && obj instanceof Person){
            Person per = (Person)obj;
            System.out.println("pp:"+per.getId()+"\t"+per.getName()+"\t"+per.getAge());
        }
        System.out.println("***************集合***************");
        List<Person> list = new ArrayList<Person>();
        list.add(p2);
        list.add(p3);
        jd.set("plist".getBytes(), SerializeUtil.serialize(list));
        jd.expire("plist".getBytes(), 300);

        //读取
        byte[] value2 = jd.get("plist".getBytes());
        Object obj2 = SerializeUtil.unserialize(value2);
        if(obj2!=null && obj2 instanceof List<?>){
            List<Person> pl = (List<Person>)obj2;
            for (Person per : pl) {
                System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
            }
        }

        System.out.println("***************集合2************");
        jd.lpush("bean".getBytes(), SerializeUtil.serialize(p2));
        jd.lpush("bean".getBytes(), SerializeUtil.serialize(p3));
        jd.expire("bean".getBytes(), 300);

        for (int i = 0; i < jd.llen("bean".getBytes()); i++) {
            Person per = (Person)SerializeUtil.unserialize(jd.lindex("bean".getBytes(), i));
            System.out.println("bean:"+per.getId()+"\t"+per.getName()+"\t"+per.getAge());
        }
        System.out.println("=======================");

        byte[] value3 = jd.lpop("bean".getBytes());
        Object obj3 = SerializeUtil.unserialize(value3);
        if(obj3!=null && obj3 instanceof Person){
            Person per = (Person)obj;
            System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
        }

        //value = jd.lpop("bean".getBytes());
        value = jd.lindex("bean".getBytes(), 0);
        obj = SerializeUtil.unserialize(value);
        if(obj!=null && obj instanceof Person){
            Person per = (Person)obj;
            System.out.println(per.getId()+"\t"+per.getName()+"\t"+per.getAge());
        }
    }
}
