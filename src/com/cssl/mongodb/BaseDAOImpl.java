package com.cssl.mongodb;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDAOImpl implements BaseDAO {
    @Override
    public boolean insert(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).insert(bean);
        return false;
    }

    @Override
    public boolean insert(String collectionName, Object bean) {
        DB db = MongoDBUtil.getDB();
        try {
            DBObject obj = BeanUtil.bean2DBObject(bean);
            db.getCollection(collectionName).insert(obj);
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).remove(bean);
        return false;
    }

    @Override
    public boolean delete(String collectionName, Object bean) {
        DB db = MongoDBUtil.getDB();
        try {
            db.getCollection(collectionName).remove(BeanUtil.bean2DBObject(bean));
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<DBObject> find(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        List<DBObject> list = db.getCollection(collectionName).find(bean).toArray();
        return list;
    }

    @Override
    public List<DBObject> find(String collectionName, Object bean) {
        DB db = MongoDBUtil.getDB();
        List<DBObject> list = null;
        try {
            list = db.getCollection(collectionName).find(BeanUtil.bean2DBObject(bean)).toArray();
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(String collectionName, BasicDBObject oldBean,
                          BasicDBObject newBean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).update(oldBean, newBean);
        //db.getCollection(collectionName).findAndModify(oldBean, newBean);
        return true;
    }


    @Override
    public boolean update(String collectionName, DBObject oldBean, Object newBean) {
        DB db = MongoDBUtil.getDB();
        try {
            db.getCollection(collectionName).update(oldBean, BeanUtil.bean2DBObject(newBean));
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public long queryCount(String collectionName, DBObject params){
        DB db = MongoDBUtil.getDB();
        long l = 0L;
        DBCollection collection = null;
        DBCursor cursor = null;
        try {
            collection = db.getCollection(collectionName);
            cursor = collection.find(params);
            l = cursor.count();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return l;
    }

    public List<Map<String, Object>> query(String collectionName, BasicDBObject param, int startRow, int rows){
        DB db = MongoDBUtil.getDB();
        List result = new ArrayList();
		/*if (!(db.collectionExists(collectionName))) {
			return result;
		}*/
        DBCollection collection = null;
        DBCursor cursor = null;
        try {
            collection = db.getCollection(collectionName);
            cursor = collection.find(param);
            if ((startRow >= 0) && (rows > 0)) {
                cursor.skip(startRow).limit(rows);
            }
            while (cursor.hasNext()) {
                DBObject next = cursor.next();
                Map map = next.toMap();
                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    /**
     *
     * @param collecName
     * @param params  ��ѯ����
     * @param fields  ��ѯ�ֶ�
     * @param sort    ����
     * @param skip    ��ʼ��¼
     * @param limit   ȡ������
     * @return
     */
    public DBCursor getCursor(String collecName, DBObject params, DBObject fields, DBObject sort, int skip, int limit){
        DB db = MongoDBUtil.getDB();
        DBCollection collection;
        DBCursor dbc = null;
        try {
            collection = db.getCollection(collecName);
            if (params == null)
                dbc = collection.find();
            else if (fields != null)
                dbc = collection.find(params, fields);
            else
                dbc = collection.find(params);
            if (sort != null)
                dbc = dbc.sort(sort);
            if (skip > 0)
                dbc = dbc.skip(skip);
            if (limit > 0)
                dbc = dbc.limit(limit);
            return dbc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
