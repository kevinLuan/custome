package com.kevin.role.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.role.dao.IBaseDao;
import com.kevin.role.dao.model.RoleGroup;
import com.kevin.role.services.RoleGroupService;

@Service("roleGroupService")
public class RoleGroupServiceImpl implements RoleGroupService {
	@Resource(name = "baseDao")
	IBaseDao baseDao;

	@Override
	public void deleteRoleGroup(Integer id) {
		baseDao.delete("ROLE_ROLEGROUP.deleteRoleGroup", id);
	}

	@Override
	public List getAllRoleGroup(int start, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("rows", rows);
		return baseDao
				.queryForList("ROLE_ROLEGROUP.selectRoleGroupForMap", map);
	}

	@Override
	public int getCountRoleGroup() {
		return  (Integer)baseDao.queryForObject("ROLE_ROLEGROUP.getRoleGroupCount");
	}

	@Override
	public RoleGroup getRoleGroupById(int id) {
		return (RoleGroup)baseDao.queryForObject("ROLE_ROLEGROUP.getRoleGroupById", id);
	}

	@Override
	public int insertRoleGroup(RoleGroup roleGroup) {
		return (Integer)baseDao.insert("ROLE_ROLEGROUP.insertRoleGroup",roleGroup);
	}

	@Override
	public void updateRoleGroup(RoleGroup roleGroup) {
		baseDao.update("ROLE_ROLEGROUP.upadteRoleGroup", roleGroup);
		}
}
