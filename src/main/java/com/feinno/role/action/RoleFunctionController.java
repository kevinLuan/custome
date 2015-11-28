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

import com.feinno.role.dao.model.RoleFunction;
import com.feinno.role.dao.model.RoleUser;
import com.feinno.role.services.RoleFunctionService;

@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class RoleFunctionController extends BaseController {
  private static final Logger logger = LoggerFactory.getLogger(RoleFunctionController.class);
  @Resource(name = "roleFunctionService")
  RoleFunctionService roleFunctionService;


  @RequestMapping("/role/rolefunctions.do")
  @ResponseBody
  public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
    List<RoleUser> lists = this.roleFunctionService.getAllRoleFunction((page - 1) * rows, rows);
    results.put("rows", lists);
    results.put("total", this.roleFunctionService.getCountRoleFunction());
    return results;
  }

  @RequestMapping("/role/rolefunctionupdate.do")
  @ResponseBody
  public Map<String, Object> save(@RequestBody RoleFunction roleFunction, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    results.put("msg", "操作失败！");
    try {
      if (roleFunction.getId() == 0) {
        roleFunctionService.insertRoleFunction(roleFunction);
      } else {
        roleFunctionService.updateRoleFunction(roleFunction);

      }
      results.put("msg", "操作成功！");
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
    }
    return results;
  }

  @RequestMapping("/role/getrolefunction.do")
  @ResponseBody
  public Map<String, Object> getOne(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> results = new HashMap<String, Object>();
    String id = request.getParameter("id");
    results.put("result", roleFunctionService.getRoleFunctionById(Integer.valueOf(id)));
    return results;

  }

  @RequestMapping("/role/deleterolefunction.do")
  @ResponseBody
  public void deleteOne(HttpServletRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    roleFunctionService.deleteRoleFunction(Integer.valueOf(id));
  }

  @RequestMapping("/role/getrolefunctions.do")
  @ResponseBody
  public List<?> getRoleFunctions(HttpServletRequest request, HttpServletResponse response) {
    String fatherId = request.getParameter("id");
    String code = request.getParameter("code");
    return roleFunctionService.getRoleFunctions(code, fatherId);
  }
}
