package com.kevin.zuyu.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kevin.orm.mapping.BaseService;
import com.kevin.role.action.BaseController;
import com.kevin.zuyu.model.Service;
import com.kevin.zuyu.service.OrderService;

/**
 * 服务项目管理
 * 
 * @author SHOUSHEN LUAN
 *
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/service")
public class ServiceAction extends BaseController {
  @Resource(name = "baseService")
  private BaseService<Service> baseService;
  @Autowired
  private OrderService orderService;
  @RequestMapping(value = "/index.do")
  public ModelAndView index(HttpServletRequest request, ModelAndView mv) {
    mv.setViewName("service/index");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/findAll.do")
  public Map<String, Object> findAll(HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    List<Service> list = (List<Service>) baseService.findAll(new Service());
    results.put("rows", list);
    results.put("total", list.size());
    return results;
  }
  
  @ResponseBody
  @RequestMapping(value = "/findAllValid.do")
  public Map<String, Object> findAllValid(HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    List<Service> list = orderService.findAllValidService();
    results.put("rows", list);
    results.put("total", list.size());
    return results;
  }

  @ResponseBody
  @RequestMapping(value = "/addOrUpdateItem.do")
  public Map<String, Object> addOrUpdateItem(@RequestBody Service bean, HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    if (bean.getId() == null || bean.getId() < 1) {
      baseService.save(bean);
    } else {
      baseService.update(bean);
    }
    results.put("msg", "操作成功！");
    return results;
  }

  @ResponseBody
  @RequestMapping("/deleteById.do")
  public Map<String, Object> delete(int id) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    String sql = "DELETE FROM service WHERE id=?";
    if (baseService.delete(sql, id)) {
      results.put("msg", "操作成功！");
    } else {
      results.put("msg", "操作失败！");
    }
    return results;
  }
}
