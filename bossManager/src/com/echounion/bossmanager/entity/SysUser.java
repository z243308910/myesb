package com.echounion.bossmanager.entity;

import java.io.Serializable;

/**
 * 管理员
 * @author 胡礼波 10:41:28 AM Oct 22, 2012
 */
public class SysUser implements Serializable {

	/**
	 * @author 胡礼波
	 * 2012-10-31 下午05:15:21
	 */
	private static final long serialVersionUID = 4459947362492347857L;

	private String userName; // 用户名

	private String password; // 密码

	private String email; // 电子邮件

	private String telephone; // 电话号码

	private String name; // 姓名

	private int id;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
