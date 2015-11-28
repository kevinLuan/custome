package com.feinno.role.dao.model;

import java.io.Serializable;
import java.util.List;

public class RoleFunction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2398737663123660403L;
	private Integer id;
	private String functionUrl;
	private String name;
	private Integer  fatherId;
	//数据库中没有,因需求新加字段
	private Integer  fatherId1;
	private Integer fatherId2;
	private String  code;
	private Integer status;
	//子连接
	private List<RoleFunction> children;
	public List<RoleFunction> getChildren() {
		return children;
	}
	public void setChildren(List<RoleFunction> children) {
		this.children = children;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFunctionUrl() {
		return functionUrl;
	}
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public Integer getFatherId1() {
		return fatherId1;
	}
	public void setFatherId1(Integer fatherId1) {
		this.fatherId1 = fatherId1;
	}
	public Integer getFatherId2() {
		return fatherId2;
	}
	public void setFatherId2(Integer fatherId2) {
		this.fatherId2 = fatherId2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}