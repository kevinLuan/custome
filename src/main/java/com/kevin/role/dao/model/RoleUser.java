/**
 * 
 */
package com.kevin.role.dao.model;

import java.io.Serializable;

/**
 * @author root
 * 
 */
public class RoleUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155938191043180655L;

	private Integer id;

	private String nickName;

	private String passWord;

	private String userName;

	private int status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
