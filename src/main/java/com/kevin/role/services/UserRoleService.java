package com.kevin.role.services;

import java.util.List;

import com.kevin.role.dao.model.UserRole;

public interface UserRoleService {
	public void insertUserRole(UserRole userRole);

	public void deleteUserRole(Integer userId);

	public UserRole getUserRoleById(Integer id);

	/*
	 * 
	 * 修改用户权限
	 */
	public void updateUserRole(Integer userId, String groupIds);

	public List getRoleGroup(String userId);
}
