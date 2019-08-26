package com.cssl.mongodb;

import com.cssl.redis.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JTest {

    private  static  BaseDAOImpl baseDAOImpl;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        baseDAOImpl = new BaseDAOImpl();
    }

    @Test
    public void testsave(){
        Person pp=new Person();
        pp.setId("101");
        pp.setAge(25);
        pp.setName("大司马三号");
        baseDAOImpl.insert("person",pp);
        System.out.println("新增成功!");

    }

    //查询全部
    @Test
    public void testQuery(){
        //不能使用父接口只能使用子对象
        BasicDBObject bo=new BasicDBObject();
        //bo.put("name","大司马");
        List<DBObject> list = baseDAOImpl.find("person", bo);
        System.out.println("listSize: "+list.size());
        for (DBObject i: list) {
            System.out.println(i.get("id")+"\t"+i.get("name")+"\t"+i.get("age"));

        }
        System.out.println("查询完成");

    }

    @Test
    public void testQueryLike(){
        String key="马";
        Pattern pattern=Pattern.compile("^.*"+key+"*.$",Pattern.CASE_INSENSITIVE);
        DB mdb=MongoDBUtil.getDB();
        List<DBObject> list = mdb.getCollection("person").find(new BasicDBObject("name", pattern)).toArray();
        for (int i=0; i<list.size();i++) {
           // System.out.println(list.get(i));
            System.out.println(list.get(i).get("age"));
        }
    }


    //分页查询
    @Test
    public void testQueryPage(){
        BasicDBObject beanOne = new BasicDBObject();
        List<Map<String, Object>> list = baseDAOImpl.query("person", beanOne, 0, 2);
        System.out.println("count: "+baseDAOImpl.queryCount("person",beanOne));
        System.out.println("listSize: "+list.size());
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }

        System.out.println("===============================");


        Long Count=baseDAOImpl.queryCount("person",beanOne);
        System.out.println("count: "+Count);
        DBObject sort=new BasicDBObject("age",1);
        DBObject fields=new BasicDBObject("name",1);
        //指定查询字段
        //表名person
        //查询条件beanOne
        //查询字段fields
        //排序sort
        DBCursor cur=
                baseDAOImpl.getCursor("person", beanOne, null, sort, 0,2);
        while(cur.hasNext()){
            DBObject db = cur.next();
            System.out.println(db.get("id")+"\t"+db.get("name")+"\t"+db.get("age"));
        }

    }
        //修改方法
        @Test
        public void testUpdate(){
            BasicDBObject bdb=(BasicDBObject) baseDAOImpl.find("person", new BasicDBObject("name", "大司马")).get(0);
            BasicDBObject object=(BasicDBObject) bdb.clone();
            object.put("name","青蛙");
            object.put("age",29);
            //修改之前
            System.out.println("name: "+bdb.get("name")+"\t"+"age: "+bdb.get("age"));


            //修改之后
            baseDAOImpl.update("person",bdb,object);
            System.out.println("name: "+object.get("name")+"\t"+"age: "+object.get("age"));
            System.out.println("修改成功");
        }


        //删除
        @Test
        public void testdelete(){
                Person pp=new Person();
                pp.setName("大司马一号");
                baseDAOImpl.delete("person",pp);
                System.out.println("删除成功");
        }




        @AfterClass
        public static void tearDownAfterClass() throws Exception {
            MongoDBUtil.close();
        }
        @Before
        public void setUp() throws Exception {

        }
        @After
        public void tearDown() throws Exception {

        }



}
