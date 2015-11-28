package com.feinno.role.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feinno.role.dao.model.GroupRole;
import com.feinno.role.services.GroupRoleService;
import com.feinno.role.services.RoleFunctionService;

@Controller
@Scope("prototype")
public class GroupRoleController extends BaseController {
  @Resource(name = "groupRoleService")
  GroupRoleService groupRoleService;
  @Resource(name = "roleFunctionService")
  RoleFunctionService roleFunctionService;

  @RequestMapping("/role/groupRoleSave.do")
  @ResponseBody
  public void insertGroupRole(@RequestBody GroupRole groupRole, HttpServletRequest request, HttpServletResponse response) {
    groupRoleService.insertGroupRole(groupRole);
  }

  @RequestMapping("/role/deleteGroupRole.do")
  @ResponseBody
  public void deleteOne(HttpServletRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    groupRoleService.deleteGroupRole(Integer.valueOf(id));
  }

  @RequestMapping("/role/getTrees.do")
  @ResponseBody
  public List<?> getTreeObjectsForRoleFunction(HttpServletRequest request, HttpServletResponse response) {
    String groupId = request.getParameter("id");
    return roleFunctionService.getTreeObjectsForRoleFunction(Integer.valueOf(groupId));
  }

  @RequestMapping("/role/groupRoleUpdate.do")
  @ResponseBody
  public void updateGroupRole(HttpServletRequest request, HttpServletResponse response) {
    String groupId = request.getParameter("groupId");
    String roleIds = request.getParameter("roleIds");
    this.groupRoleService.updateGroupRole(Integer.valueOf(groupId), roleIds);
  }
}
