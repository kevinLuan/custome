package com.kevin.role.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.role.dao.IBaseDao;
import com.kevin.role.dao.model.GroupRole;
import com.kevin.role.dao.model.RoleFunction;
import com.kevin.role.dao.model.TreeAttributes;
import com.kevin.role.dao.model.TreeObject;
import com.kevin.role.dao.model.UserRole;
import com.kevin.role.services.RoleFunctionService;

@Service("roleFunctionService")
public class RoleFunctionServiceImpl implements RoleFunctionService {
  @Resource(name = "baseDao")
  IBaseDao baseDao;

  @Override
  public List getAllRoleFunction(int start, int rows) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("start", start);
    map.put("rows", rows);
    return baseDao.queryForList("ROLE_ROLEFUNCTION.selectRoleFunctionForMap", map);
  }

  @Override
  public int getCountRoleFunction() {
    return (Integer) baseDao.queryForObject("ROLE_ROLEFUNCTION.getRoleFunctionCount");
  }

  @Override
  public RoleFunction getRoleFunctionById(Integer id) {
    return (RoleFunction) baseDao.queryForObject("ROLE_ROLEFUNCTION.getRoleFunctionById", id);
  }

  @Override
  public int insertRoleFunction(RoleFunction roleFunction) {
    // 如果fatherId为0，说明是第一级
    if (roleFunction.getFatherId() == 0) {
      roleFunction.setCode("0001");
    } else {
      Integer fatherId2 = roleFunction.getFatherId2();
      Integer fatherId1 = roleFunction.getFatherId1();
      // 判断fatherId2,如果为0，判断faherId1，否则,就是第4级
      if (fatherId2 == 0) {
        // 当fatherId2不为0判断fatherId1,如果为0则为2级,否则,就是第3级
        if (fatherId1 == 0) {
          roleFunction.setCode("0011");
        } else {
          // fatherId==fatherId1
          roleFunction.setFatherId(fatherId1);
          roleFunction.setCode("0111");
        }
      } else {
        // fatherId==fatherId2
        roleFunction.setFatherId(fatherId2);
        roleFunction.setCode("1111");
      }
    }
    return (Integer) baseDao.insert("ROLE_ROLEFUNCTION.insertRoleFunction", roleFunction);
  }

  @Override
  public void updateRoleFunction(RoleFunction roleFunction) {
    if (roleFunction.getFatherId() == 0) {
      roleFunction.setCode("0001");
    } else {
      Integer fatherId2 = roleFunction.getFatherId2();
      Integer fatherId1 = roleFunction.getFatherId1();
      if (fatherId2 == 0) {
        if (fatherId1 == 0) {
          roleFunction.setCode("0011");
        } else {
          roleFunction.setFatherId(fatherId1);
          roleFunction.setCode("0111");
        }
      } else {
        roleFunction.setFatherId(fatherId2);
        roleFunction.setCode("1111");
      }
    }
    baseDao.update("ROLE_ROLEFUNCTION.upadteRoleFunction", roleFunction);
  }

  @Override
  public void deleteRoleFunction(Integer id) {
    baseDao.delete("ROLE_ROLEFUNCTION.deleteRoleFunction", id);
  }

  @Override
  public List getRoleFunctions(String code, String id) {
    Map<String, Object> params = new HashMap<String, Object>();
    if (code != null && code.trim().length() > 0) {
      params.put("code", code);
    }
    if (id != null && id.trim().length() > 0) {
      params.put("fatherId", id);
    }
    return baseDao.queryForList("ROLE_ROLEFUNCTION.getRoleFunctions", params);
  }

  /**
   * @param groupId 构建权限组的树
   */
  @Override
  public List getTreeObjectsForRoleFunction(Integer groupId) {
    Map<Integer, Integer> gRoles = getGroupId(groupId);
    // 第一个结点0001
    List<RoleFunction> fathers = this.getRoleFunctions("0001", null);
    // 创建树的集合
    List<TreeObject> treeList = new ArrayList<TreeObject>();
    TreeObject treeObject = null;
    TreeAttributes treeAttributes = null;
    for (RoleFunction roleFunction : fathers) {
      // 构建树
      treeObject = new TreeObject();
      treeAttributes = new TreeAttributes();
      treeAttributes.setUrl(roleFunction.getFunctionUrl());
      // 给树的各个属性赋值,树的id就是roleFunction的id,
      treeObject.setId(roleFunction.getId());
      treeObject.setAttributes(treeAttributes);
      treeObject.setText(roleFunction.getName());
      if (gRoles.get(roleFunction.getId()) == null) {

        treeObject.setChecked(false);
      } else {
        // 如果grouprole的roleid不是空,说明被选中
        treeObject.setChecked(true);
      }
      // 如果结点有子结点,就添加,添加的时候需要3个参数:父结点id,roleFunction的id,gRoles的id
      addChildren(treeObject, roleFunction, gRoles);
      // 树的集合添加children
      treeList.add(treeObject);
    }
    return treeList;
  }

  /**
   * 
   * 构建私有的添加子节点的方法
   */
  private void addChildren(TreeObject treeObject, RoleFunction roleFunction, Map<Integer, Integer> roles) {
    List<RoleFunction> fathers = this.getRoleFunctions(null, roleFunction.getId() + "");
    // 创建一个新的树
    TreeObject childrenTreeObject = null;
    TreeAttributes childrenTreeAttributes = null;
    List<TreeObject> childrenTreeList = new ArrayList<TreeObject>();
    for (RoleFunction roleFunction1 : fathers) {
      childrenTreeObject = new TreeObject();
      childrenTreeAttributes = new TreeAttributes();
      childrenTreeAttributes.setUrl(roleFunction1.getFunctionUrl());
      childrenTreeObject.setAttributes(childrenTreeAttributes);
      // 放roleId
      childrenTreeObject.setId(roleFunction1.getId());
      childrenTreeObject.setText(roleFunction1.getName());
      if (roles.get(roleFunction1.getId()) == null) {
        childrenTreeObject.setChecked(false);
      } else {
        childrenTreeObject.setChecked(true);
      }
      // 如果有children继续添加
      addChildren(childrenTreeObject, roleFunction1, roles);
      childrenTreeList.add(childrenTreeObject);
    }
    if (childrenTreeList.size() > 0)
      treeObject.setChildren(childrenTreeList);
  }

  /**
   * @param groupId 把roleFunctionId放入map中,方便构建树时使用
   */
  private Map getGroupId(Integer groupId) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("groupId", groupId);
    List gRoles = baseDao.queryForList("ROLE_GROUPROLE.getGroupRoles", params);
    Map<Integer, Integer> reMap = new HashMap<Integer, Integer>();
    if (gRoles != null) {
      for (int i = 0; i < gRoles.size(); i++) {
        reMap.put(((GroupRole) gRoles.get(i)).getRoleFunctionId(), ((GroupRole) gRoles.get(i)).getRoleFunctionId());
      }
    }
    return reMap;
  }

  /**
   * @param userId 根据userId得到权限列表
   */

  public List getRoleFuntionsForUser(String userId) {
    Map<Integer, Integer> userGroup = new HashMap<Integer, Integer>();
    getUserGroup(userGroup, userId);
    List<RoleFunction> fathers = this.getRoleFunctions("0001", null);
    List<TreeObject> treeList = new ArrayList<TreeObject>();
    TreeObject treeObject = null;
    TreeAttributes treeAttributes = null;
    for (RoleFunction roleFunction : fathers) {
      if (userGroup.get(roleFunction.getId()) != null) {
        treeObject = new TreeObject();
        treeAttributes = new TreeAttributes();
        treeAttributes.setUrl(roleFunction.getFunctionUrl());
        // 给树的各个属性赋值,树的id就是roleFunction的id,
        treeObject.setId(roleFunction.getId());
        treeObject.setAttributes(treeAttributes);
        treeObject.setText(roleFunction.getName());
        addChildren(roleFunction, treeObject, userGroup);
        treeList.add(treeObject);
      }
    }
    defAuthority(treeList);
    return treeList;
  }

  /**
   * 增加所有用户的默认权限
   * 
   * @param list
   */
  public void defAuthority(List<TreeObject> list) {
    TreeObject treeObject = new TreeObject();
    treeObject.setId(1000);
    treeObject.setText("设置");
    List<TreeObject> children = new ArrayList<TreeObject>();
    treeObject.setChildren(children);
    children.add(getModifyPwdNode());
    list.add(treeObject);
  }

  private TreeObject getModifyPwdNode() {
    TreeObject modifyPwdNode = new TreeObject();
    modifyPwdNode.setId(1000);
    modifyPwdNode.setText("修改密码");
    TreeAttributes treeAttributes = new TreeAttributes();
    treeAttributes.setUrl("./modifyPwd.do");
    modifyPwdNode.setAttributes(treeAttributes);
    return modifyPwdNode;
  }

  /**
   * 
   * @param roleFunction
   * @param userGroup
   */
  private void addChildren(RoleFunction roleFunction, TreeObject treeObject, Map<Integer, Integer> userGroup) {
    List<RoleFunction> fathers = this.getRoleFunctions(null, roleFunction.getId() + "");
    TreeObject childrenTreeObject = null;
    TreeAttributes childrenTreeAttributes = null;
    List<TreeObject> childrenTreeList = new ArrayList<TreeObject>();
    for (RoleFunction childrenFunction : fathers) {
      if (userGroup.get(childrenFunction.getId()) != null) {
        childrenTreeObject = new TreeObject();
        childrenTreeAttributes = new TreeAttributes();
        childrenTreeAttributes.setUrl(childrenFunction.getFunctionUrl());
        childrenTreeObject.setAttributes(childrenTreeAttributes);
        // 放roleId
        childrenTreeObject.setId(childrenFunction.getId());
        childrenTreeObject.setText(childrenFunction.getName());
        addChildren(childrenFunction, childrenTreeObject, userGroup);
        childrenTreeList.add(childrenTreeObject);
      }
    }

    if (childrenTreeList.size() > 0)
      treeObject.setChildren(childrenTreeList);


  }

  /**
   * 根据userId得到用户权限组并得到权限组的权限id的Map
   * 
   * @param userGroup
   * @param userId
   */

  private void getUserGroup(Map<Integer, Integer> userGroup, String userId) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", userId);

    List userGroups = baseDao.queryForList("ROLE_USERROLE.getUserRoleById", params);
    UserRole userRole = null;
    for (int i = 0; i < userGroups.size(); i++) {
      userRole = (UserRole) userGroups.get(i);
      getGroupRoles(userGroup, userRole.getGroupId());
    }
  }

  /**
   * 
   * @param userGroup
   * @param id
   */
  private void getGroupRoles(Map<Integer, Integer> userGroup, Integer id) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("groupId", id);
    List gRoles = baseDao.queryForList("ROLE_GROUPROLE.getGroupRoles", params);
    if (gRoles != null) {
      for (int i = 0; i < gRoles.size(); i++) {
        userGroup.put(((GroupRole) gRoles.get(i)).getRoleFunctionId(), ((GroupRole) gRoles.get(i)).getRoleFunctionId());
      }
    }
  }
}
