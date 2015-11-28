package com.kevin.role.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.role.dao.IBaseDao;
import com.kevin.role.dao.model.RoleGroup;
import com.kevin.role.dao.model.UserRole;
import com.kevin.role.services.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Resource(name = "baseDao")
	IBaseDao baseDao;

	@Override
	public void deleteUserRole(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		baseDao.delete("ROLE_USERROLE.deleteUserRole", params);
	}

	@Override
	public void insertUserRole(UserRole userRole) {
		baseDao.insert("ROLE_USERROLE.insertUserRole", userRole);
	}

	@Override
	public UserRole getUserRoleById(Integer id) {
		return (UserRole) baseDao.queryForObject(
				"ROLE_USERROLE.getUserRoleById", id);
	}

	@Override
	public void updateUserRole(Integer userId, String groupIds) {
		String ids[] = groupIds.split(",");
		UserRole userRole = null;
		// 删除旧权限
		deleteUserRole(userId);
		if (ids.length > 0) {
			for (String id : ids) {
				userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setGroupId(Integer.valueOf(id));
				// 给用户添加新权限
				insertUserRole(userRole);
			}
		}
	}

	@Override
	public List getRoleGroup(String userId) {
		Map<Integer, Object> userGroup = new HashMap<Integer, Object>();
		List list = baseDao.queryForList("ROLE_ROLEGROUP.getRoleGroups");
		getGroupForUserId(userGroup, userId);
		List rList = new ArrayList();
		RoleGroup roleGroup = null;
		for (int i = 0; i < list.size(); i++) {
			roleGroup = (RoleGroup) list.get(i);
			if (userGroup.get(roleGroup.getId()) != null) {
				roleGroup.setChecked("true");
			}else{
				roleGroup.setChecked("false");
			}
			rList.add(roleGroup);
		}
		return rList;
	}

	private void getGroupForUserId(Map<Integer, Object> userGroup, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);

		List list = baseDao.queryForList("ROLE_USERROLE.getUserRoleById",
				params);
		UserRole userRole=null;
		for(int i=0 ;i<list.size();i++){
			userRole = (UserRole) list.get(i);
			userGroup.put(userRole.getGroupId(), userRole.getGroupId());
		}
	}

}
