package com.kevin.role.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.role.dao.IBaseDao;
import com.kevin.role.dao.model.GroupRole;
import com.kevin.role.services.GroupRoleService;

@Service("groupRoleService")
public class GroupRoleServiceImpl implements GroupRoleService {
	@Resource(name = "baseDao")
	private IBaseDao baseDao;

	@Override
	public void deleteGroupRole(Integer groupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", groupId);
		baseDao.delete("ROLE_GROUPROLE.deleteGroupRole", params);
	}

	@Override
	public void insertGroupRole(GroupRole groupRole) {
		baseDao.insert("ROLE_GROUPROLE.insertGroupRole",	groupRole);
	}

	@Override
	public List getGroupRoles(Integer groupId) {  
		System.out.println(baseDao);
		return baseDao.queryForList("ROLE_GROUPROLE.getGroupRoles", groupId);
	}

	@Override
	public void updateGroupRole(Integer groupId, String roleIds) {
		
		String ids[] = roleIds.split(",");
		//先删除groupId对应的以前的权限
		deleteGroupRole(groupId);
		GroupRole groupRole =null;
		if(ids.length>0){
		for (String id : ids) {
			groupRole= new GroupRole();
			groupRole.setGroupId(groupId);
			groupRole.setRoleFunctionId(Integer.parseInt(id));
			//生成新的权限
			insertGroupRole(groupRole);
		}
		}
	}

}
