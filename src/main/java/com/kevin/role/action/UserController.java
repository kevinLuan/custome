package com.kevin.role.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kevin.role.dao.model.RoleUser;
import com.kevin.role.services.RoleUserService;

@Controller
public class UserController extends BaseController {
  @Autowired
  private RoleUserService roleUserService;

  @RequestMapping(value = "/modifyPwd.do", method = RequestMethod.GET)
  public ModelAndView toModifyPwd(ModelAndView mv) {
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/modifyPwd.do", method = RequestMethod.POST)
  public Map<String, Object> modifyPwd(HttpServletRequest request, String oldPwd, String newPwd) {
    RoleUser user = getSessionUser(request);
    Map<String, Object> resultMap = new HashMap<String, Object>(1);
    if (user.getPassWord().equals(oldPwd)) {
      user.setPassWord(newPwd);
      roleUserService.updateRoleUser(user);
      resultMap.put("status", true);
      resultMap.put("desc", "success");
    } else {
      resultMap.put("desc", "原始密码错误。");
      resultMap.put("status", false);
    }
    return resultMap;
  }

}
