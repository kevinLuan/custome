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

import com.feinno.role.dao.model.RoleGroup;
import com.feinno.role.dao.model.RoleUser;
import com.feinno.role.services.RoleGroupService;

@Controller
@Scope("prototype")
public class RoleGroupController extends BaseController {
  private static final Logger logger = LoggerFactory.getLogger(RoleGroupController.class);
  @Resource(name = "roleGroupService")
  RoleGroupService roleGroupService;


  @SuppressWarnings("unchecked")
  @RequestMapping("/role/rolegroups.do")
  @ResponseBody
  public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
    List<RoleUser> lists = this.roleGroupService.getAllRoleGroup((page - 1) * rows, rows);
    results.put("rows", lists);
    results.put("total", this.roleGroupService.getCountRoleGroup());
    return results;
  }

  @RequestMapping("/role/rolegroupupdate.do")
  @ResponseBody
  public Map<String, Object> save(@RequestBody RoleGroup roleGroup, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    results.put("msg", "操作失败！");
    try {
      if (roleGroup.getId() == 0) {
        roleGroupService.insertRoleGroup(roleGroup);
      } else {
        roleGroupService.updateRoleGroup(roleGroup);

      }
      results.put("msg", "操作成功！");
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
    }

    return results;
  }

  @RequestMapping("/role/getrolegroup.do")
  @ResponseBody
  public Map<String, Object> getOne(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    String id = request.getParameter("id");
    results.put("result", roleGroupService.getRoleGroupById(Integer.valueOf(id)));
    return results;

  }

  @RequestMapping("/role/deleterolegroup.do")
  @ResponseBody
  public void deleteOne(HttpServletRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    roleGroupService.deleteRoleGroup(Integer.valueOf(id));
  }
}
