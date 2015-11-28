package com.feinno.role.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.feinno.commons.JsonResult;
import com.feinno.role.dao.model.RoleUser;
import com.feinno.role.services.RoleUserService;


@Controller
public class RoleUserController extends BaseController {
  private static final String LOGIN_USER = "LOGIN_USER";
  @Resource(name = "roleUserService")
  RoleUserService roleUserService;

  @RequestMapping(value = "/login.do")
  public ModelAndView login(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView();
    RoleUser roleUser = this.getSessionUser(request);
    String a = request.getParameter("a");
    if (a != null && a.equals("login")) {
      String userName = request.getParameter("username");
      String password = request.getParameter("password");
      roleUser = roleUserService.getRoleUser(userName, password);
      if (roleUser == null) {
        mv.addObject("ERROR", "用户名密码有误！请重新登录！");
        mv.setViewName("login");
      } else {
        request.getSession().setAttribute(LOGIN_USER, roleUser);
        mv.setViewName("layout");
      }
    } else {
      mv.setViewName("login");
    }

    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/ajaxLogin.do")
  public JsonResult ajaxLogin(String username, String password, HttpServletRequest request) {
    RoleUser roleUser = roleUserService.getRoleUser(username, password);
    if (roleUser != null) {
      request.getSession().setAttribute(LOGIN_USER, roleUser);
      return JsonResult.success("登陆成功");
    } else {
      return JsonResult.failed(-1, "用户名或密码错误");
    }
  }
  /**
   * ping 登陆验证
   */
  @ResponseBody
  @RequestMapping(value = "/ping.do")
  public JsonResult ping(HttpServletRequest request) {
    if (request.getSession().getAttribute(LOGIN_USER) == null) {
      return JsonResult.success(false);
    } else {
      return JsonResult.success(true);
    }
  }
}
