package com.kevin.role.services;

import java.util.List;

import com.kevin.role.dao.model.RoleGroup;

public interface RoleGroupService {
 
	public int insertRoleGroup(RoleGroup roleGroup);
 
	public List getAllRoleGroup(int start,int rows);
	
	public int getCountRoleGroup();
	
	public void updateRoleGroup(RoleGroup roleGroup);
	
	public RoleGroup getRoleGroupById(int id);	

	public void deleteRoleGroup(Integer id);
}
