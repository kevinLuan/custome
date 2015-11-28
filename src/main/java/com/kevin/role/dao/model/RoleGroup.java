package com.kevin.role.dao.model;

import java.io.Serializable;


public class RoleGroup  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3350215312265379215L;
	private Integer id;
	private String name;
	private Integer status;
	private String checked;
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
