package com.feinno.role.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feinno.role.dao.model.RoleUser;
import com.feinno.role.services.RoleFunctionService;
import com.feinno.role.services.RoleUserService;
import com.feinno.role.services.UserRoleService;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class UserRoleController extends BaseController {
  private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
  @Resource(name = "roleUserService")
  RoleUserService roleUserService;
  @Resource(name = "roleFunctionService")
  RoleFunctionService roleFunctionService;
  @Resource(name = "userRoleService")
  UserRoleService userRoleService;


  @RequestMapping("/role/roleusers.do")
  @ResponseBody
  public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
    List<RoleUser> lists = this.roleUserService.getAllRoleUser((page - 1) * rows, rows);
    results.put("rows", lists);
    results.put("total", this.roleUserService.getCountRoleUser());
    return results;
  }

  @RequestMapping("/role/userupdate.do")
  @ResponseBody
  public Map<String, Object> save(@RequestBody RoleUser user, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    results.put("msg", "操作失败！");
    try {
      if (user.getId() == 0) {
        roleUserService.insertRoleUser(user);
      } else {
        roleUserService.updateRoleUser(user);

      }
      results.put("msg", "操作成功！");
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
    }

    return results;
  }

  @RequestMapping("/role/getuser.do")
  @ResponseBody
  public Map<String, Object> getOne(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    String id = request.getParameter("id");
    results.put("result", roleUserService.getRoleUserForId(Integer.valueOf(id)));
    return results;

  }

  @RequestMapping("/role/deleteroleuser.do")
  @ResponseBody
  public void deleteOne(HttpServletRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    roleUserService.deleteRoleUser(Integer.valueOf(id));
  }

  @RequestMapping("/role/getroleusers.do")
  @ResponseBody
  public List<?> getAllRoleUsers(HttpServletRequest request, HttpServletResponse response) {
    return roleUserService.getAllRoleUser();
  }

  @RequestMapping("/role/getuserRoles.do")
  @ResponseBody
  public List<?> getUserRoles(HttpServletRequest request, HttpServletResponse response) {
    return roleFunctionService.getRoleFuntionsForUser(this.getSessionUser(request).getId() + "");

  }

  @RequestMapping("/role/getUserGroups.do")
  @ResponseBody
  public List<?> getUserGroups(HttpServletRequest request, HttpServletResponse response) {
    String userId = request.getParameter("userId");
    return userRoleService.getRoleGroup(userId);

  }
}
