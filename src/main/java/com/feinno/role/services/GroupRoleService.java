package com.feinno.role.services;

import java.util.List;

import com.feinno.role.dao.model.GroupRole;

public interface GroupRoleService {
	public void insertGroupRole(GroupRole groupRole);	
	public void deleteGroupRole(Integer groupId);
	public List getGroupRoles(Integer groupId);
	public void updateGroupRole(Integer groupId,String roleIds);
}
