package com.feinno.role.services;

import java.util.List;

import com.feinno.role.dao.model.RoleFunction;

public interface RoleFunctionService {
	public int insertRoleFunction(RoleFunction roleFunction);
	 
	public List getAllRoleFunction(int start,int rows);
	
	public int getCountRoleFunction();
	
	public void updateRoleFunction(RoleFunction roleFunction);
	
	public RoleFunction getRoleFunctionById(Integer id);	
	
	public void deleteRoleFunction(Integer id);
	
	public List getRoleFunctions(String code,String id);
	
	public List getTreeObjectsForRoleFunction(Integer groupId);
	/**
	 * 根据userid得到用户权限列表
	 * @param userId
	 * @return
	 */
	public List getRoleFuntionsForUser(String userId);
}
