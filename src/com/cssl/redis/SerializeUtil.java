package com.cssl.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeUtil {

	/**
	 * 将对象序列化成byte[]
	 * @param object
	 * @return
	 */
	 public static byte[] serialize(Object object) {
		 ObjectOutputStream oos = null;
		 //二进制数组的内存流
	     ByteArrayOutputStream baos = null;
	     try {
	            // 序列化:new FileOutputStream("");
	    	 	baos = new ByteArrayOutputStream();
	    	 	oos = new ObjectOutputStream(baos);
	    	 	oos.writeObject(object);//写入内存
	    	 	byte[] bytes = baos.toByteArray();//取出就是二进制数组
	    	 	return bytes;
	     } catch (Exception e) {
	        	e.printStackTrace();
	     } finally {
	    	 try {
				 oos.close();
				 baos.close();
			 } catch (IOException e) {				
				 e.printStackTrace();
			 }
	     }
	     return null;
	}

	/**
	 * 将byte[]反序列化成对象
	 * @param bytes
	 * @return
	 */
    public static Object unserialize(byte[] bytes) {
         ByteArrayInputStream bais = null;
         ObjectInputStream ois = null;
         try {
        	 // 反序列化:new FileInputStream("");
        	 bais = new ByteArrayInputStream(bytes);
        	 ois = new ObjectInputStream(bais);
        	 return ois.readObject();
         } catch (Exception e) {
        	 e.printStackTrace();
         } finally {
        	 try {
				 ois.close();
				 bais.close();
			 } catch (IOException e) {				
				 e.printStackTrace();
			 }
         }
         return null;
	}	
}
