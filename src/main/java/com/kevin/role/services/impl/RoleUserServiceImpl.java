/**
 * 
 */
package com.kevin.role.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.role.dao.IBaseDao;
import com.kevin.role.dao.model.RoleUser;
import com.kevin.role.services.RoleUserService;

@Service("roleUserService")
public class RoleUserServiceImpl implements RoleUserService {
	@Resource(name = "baseDao")
	IBaseDao baseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevin.role.services.RoleUserService#insertRoleUser(com.kevin.role
	 * .dao.model.RoleUser)
	 */
	@Override
	public int insertRoleUser(RoleUser roleUser) {
		return (Integer) baseDao.insert("ROLE_ROLEUSER.insert", roleUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevin.role.services.RoleUserService#getRoleUser(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public RoleUser getRoleUser(String userName, String passWord) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", userName);
		param.put("passWord", passWord);
		return (RoleUser) baseDao.queryForObject(
				"ROLE_ROLEUSER.selectRoleUser", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevin.role.services.RoleUserService#getAllRoleUser(int, int)
	 */
	@Override
	public List getAllRoleUser(int start, int rows) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("rows", rows);
		return baseDao
				.queryForList("ROLE_ROLEUSER.selectRoleUserForMap", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevin.role.services.RoleUserService#getCountRoleUser()
	 */
	@Override
	public int getCountRoleUser() {

		return (Integer) baseDao
				.queryForObject("ROLE_ROLEUSER.getRoleUserCount");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevin.role.services.RoleUserService#getRoleUserForId(int)
	 */
	@Override
	public RoleUser getRoleUserForId(int id) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return (RoleUser) baseDao.queryForObject(
				"ROLE_ROLEUSER.getRoleUserForId", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevin.role.services.RoleUserService#updateRoleUser(com.kevin.role
	 * .dao.model.RoleUser)
	 */
	@Override
	public void updateRoleUser(RoleUser roleUser) {
		baseDao.update("ROLE_ROLEUSER.upadteRoleUser", roleUser);

	}

	@Override
	public void deleteRoleUser(Integer id) {
		baseDao.delete("ROLE_ROLEUSER.deleteRoleUser", id);
	}

	@Override
	public List getAllRoleUser() {
		// TODO Auto-generated method stub
		return  baseDao
		.queryForList("ROLE_ROLEUSER.selectRoleUsers");
	}


}
