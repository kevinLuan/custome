package com.feinno.role.dao.model;

import java.io.Serializable;
import java.util.List;
/*
 * 
 * 构建树对象,对象的属性必须和tree_data.json对应的属性一致
 */
public class TreeObject  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5484247181231881116L;
	private Integer id;  
    private String text;  
    private List<TreeObject> children;
    private boolean checked;
    private String state;
	private TreeAttributes attributes;
	public TreeAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(TreeAttributes attributes) {
		this.attributes = attributes;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeObject> getChildren() {
		return children;
	}
	public void setChildren(List<TreeObject> children) {
		this.children = children;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
