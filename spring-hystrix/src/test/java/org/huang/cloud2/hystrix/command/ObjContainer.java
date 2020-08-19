package org.huang.cloud2.hystrix.command;

public class ObjContainer <T> {
	private T obj;
	public void set(T t) {
		this.obj = t;
	}
	public T get(){
		return obj;
	}
}
