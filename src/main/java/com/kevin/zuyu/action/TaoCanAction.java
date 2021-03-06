package com.kevin.zuyu.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kevin.orm.mapping.AutoGenerated;
import com.kevin.orm.mapping.BaseService;
import com.kevin.role.action.BaseController;
import com.kevin.zuyu.model.TaoCan;
import com.kevin.zuyu.service.OrderService;

/**
 * 套餐管理
 * 
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/taocan")
public class TaoCanAction extends BaseController {
  @Resource(name = "baseService")
  private BaseService<TaoCan> baseService;
  @Autowired
  private OrderService orderService;

  @RequestMapping(value = "/index.do")
  public ModelAndView index(HttpServletRequest request, ModelAndView mv) {
    mv.setViewName("taocan/index");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/findAll.do")
  public Map<String, Object> findAll(HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
    List<TaoCan> list = (List<TaoCan>) baseService.findAll(new TaoCan(), (page - 1) * rows, rows);
    results.put("rows", list);
    results.put("total", baseService.countAll(new TaoCan()));
    return results;
  }

  @ResponseBody
  @RequestMapping(value = "/findAllValid.do")
  public Map<String, Object> findAllValid(HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    List<TaoCan> list = orderService.findAllValidTaocan();
    results.put("rows", list);
    results.put("total", baseService.countAll(new TaoCan()));
    return results;
  }

  @ResponseBody
  @RequestMapping(value = "/addOrUpdateItem.do")
  public Map<String, Object> addOrUpdateItem(@RequestBody TaoCan bean, HttpServletRequest request) throws Exception {
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
    String sql = "DELETE FROM tao_can WHERE id=?";
    if (baseService.delete(sql, id)) {
      results.put("msg", "操作成功！");
    } else {
      results.put("msg", "操作失败！");
    }
    return results;
  }
}
