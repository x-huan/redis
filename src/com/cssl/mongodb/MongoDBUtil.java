package com.cssl.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBUtil {
    private static MongoClient client;
    private static Mongo mongo = null;
    private static String DBString = "test";//���ݿ���
    private static String hostName = "192.168.10.130";
    private static int port = 27017;//�˿ں�
    private static int poolSize = 10;//���ӳش�С
    private static String url = "mongodb://192.168.10.130:27017/test?AutoConnectRetry=true";
    //private static String url = "mongodb://testrw:testrw@127.0.0.1:27017/test?AutoConnectRetry=true";

    //��ȡ���ݿ�����
    public static DB getDB() {
        MongoClientURI uri = new MongoClientURI(url);
        client = new MongoClient(uri);
        return client.getDB(DBString);
    }

    public static void close(){
        client.close();
    }

    //��ȡ���ݿ�����
    /*public static DB getDB(){
        if(mongo == null){
            try {
                //ʵ����Mongo,����ʹ������
                mongo = new Mongo(hostName,port);
                MongoOptions opt = mongo.getMongoOptions();
                //�������ӳش�С
                opt.connectionsPerHost = poolSize;
                opt.maxWaitTime = 10;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        DB db = mongo.getDB(DBString);
        return db;
    }*/


    public static void main(String[] args) {
        DB db = getDB();
        System.out.println("db:"+db);
        //db.requestStart();
        //db.requestDone();
    }

}
