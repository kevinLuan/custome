package com.kevin.role.dao.model;

/**
 *role_user和role_group角色组表产生的第三张表
 */
import java.io.Serializable;

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4600063187262790281L;
	private Integer userId;
	private Integer groupId;
	private String groupIs;

	public String getGroupIs() {
		return groupIs;
	}

	public void setGroupIs(String groupIs) {
		this.groupIs = groupIs;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
