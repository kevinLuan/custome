package com.feinno.role.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.feinno.role.dao.model.RoleUser;

public class BaseController {
  /**
   * 更新Session 中的登陆用户信息
   * 
   * @param request
   * @param bean
   */
  protected void updateSessionUser(HttpServletRequest request, RoleUser roleUser) {
    HttpSession session = request.getSession();
    session.setAttribute("LOGIN_USER", roleUser);
  }

  /**
   * 获取Session中的用户信息
   * 
   * @param request
   * @return
   */
  protected com.feinno.role.dao.model.RoleUser getSessionUser(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (com.feinno.role.dao.model.RoleUser) session.getAttribute("LOGIN_USER");
  }

}
