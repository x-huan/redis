package com.cssl.redis;

import java.io.Serializable;
//redis默认不支持java对象，将对象序列化转成字节数组
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Integer age;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Person(String id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Person() {
	}
}
