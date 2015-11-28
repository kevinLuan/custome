package com.kevin.role.dao.model;
/**
 * role_function和role_group产生的第三张表
 */
import java.io.Serializable;

public class GroupRole implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6077219610449998431L;
	private Integer groupId;
    private Integer roleFunctionId;
    private String roleIds;
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getRoleFunctionId() {
		return roleFunctionId;
	}
	public void setRoleFunctionId(Integer roleFunctionId) {
		this.roleFunctionId = roleFunctionId;
	}
}
