package com.feinno.role.services;

import java.util.List;

import com.feinno.role.dao.model.RoleGroup;

public interface RoleGroupService {
 
	public int insertRoleGroup(RoleGroup roleGroup);
 
	public List getAllRoleGroup(int start,int rows);
	
	public int getCountRoleGroup();
	
	public void updateRoleGroup(RoleGroup roleGroup);
	
	public RoleGroup getRoleGroupById(int id);	

	public void deleteRoleGroup(Integer id);
}
