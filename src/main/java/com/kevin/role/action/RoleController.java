package com.kevin.role.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevin.role.dao.model.UserRole;
import com.kevin.role.services.UserRoleService;


@Controller
@Scope("prototype")
public class RoleController extends BaseController {
  @Resource(name = "userRoleService")
  UserRoleService userRoleService;


  @RequestMapping("/role/userroleupdate.do")
  @ResponseBody
  public void save(@RequestBody UserRole userRole, HttpServletRequest request, HttpServletResponse response) {
    userRoleService.insertUserRole(userRole);
  }

  @RequestMapping("/role/deleteuserrole.do")
  @ResponseBody
  public void deleteOne(HttpServletRequest request, HttpServletResponse response) {
    String id = request.getParameter("userId");
    userRoleService.deleteUserRole(Integer.valueOf(id));
  }


  @RequestMapping("/role/updateUserRoles.do")
  @ResponseBody
  public void updateUserRole(HttpServletRequest request, HttpServletResponse response) {
    String userId = request.getParameter("userId");
    String groupIds = request.getParameter("groupIds");
    userRoleService.updateUserRole(Integer.valueOf(userId), groupIds);
  }
}
