package org.huang.cloud2;

import java.io.Serializable;
import java.util.Date;

public class ShareBean implements Serializable {
	private String userName;
	private int age;
	private Date birthDate;

	public ShareBean() {
	}

	public ShareBean(String userName, int age, Date birthDate) {
		this.userName = userName;
		this.age = age;
		this.birthDate = birthDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
