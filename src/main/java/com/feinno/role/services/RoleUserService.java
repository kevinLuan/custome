/**
 * 
 */
package com.feinno.role.services;

import java.util.List;

import com.feinno.role.dao.model.RoleUser;

/**
 * @author <a href="mailto:wxh512@gmail.com">Wang XuHui</a>
 * 
 */
public interface RoleUserService {
	/**
	 * 插入一个用户
	 * 
	 * @param roleUser
	 * @return
	 */
	public int insertRoleUser(RoleUser roleUser);

	public RoleUser getRoleUser(String userName, String passWord);

	public List getAllRoleUser(int start, int rows);

	public int getCountRoleUser();

	public void updateRoleUser(RoleUser roleUser);

	public RoleUser getRoleUserForId(int id);

	public void deleteRoleUser(Integer id);
	
	public List getAllRoleUser();

}
