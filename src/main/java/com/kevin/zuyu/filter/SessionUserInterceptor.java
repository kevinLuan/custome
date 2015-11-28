package com.kevin.zuyu.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionUserInterceptor implements HandlerInterceptor {
  private String include = ".*\\.do.*";
  // 可排除的URL
  private final String exclude = ".*/(login|index|ping|ajaxLogin).do.*";

  @Override
  public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
      throws Exception {

  }

  @Override
  public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
      throws Exception {}

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
    String uri = request.getRequestURI();
    // 判断 URI 是否需要验证,当 URI 不需要验证时 返回 true
    if (!validate(uri)) {
      return true;
    }
    if (request.getSession().getAttribute("LOGIN_USER") == null) {
      response.sendRedirect("/index.do");
      return false;
    }
    return true;
  }

  protected boolean validate(String uri) {
    return uri.matches(include) && (!"".equals(exclude) ? !uri.matches(exclude) : true);
  }

  public String getInclude() {
    return include;
  }

  public void setInclude(String include) {
    this.include = include;
  }

}
