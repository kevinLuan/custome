package com.kevin.zuyu.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

import com.feinno.commons.JsonResult;
import com.feinno.commons.JsonUtils;
import com.feinno.role.action.BaseController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kevin.orm.mapping.BaseService;
import com.kevin.zuyu.model.Order;
import com.kevin.zuyu.model.ServiceOrder;
import com.kevin.zuyu.model.TaocanOrder;
import com.kevin.zuyu.service.OrderService;

/**
 * 基础服务订单
 * 
 * @author SHOUSHEN LUAN
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderAction extends BaseController {
  @Resource(name = "baseService")
  private BaseService<Order> baseService;
  @Autowired
  private OrderService orderService;

  @RequestMapping(value = "/index.do")
  public ModelAndView index(HttpServletRequest request, ModelAndView mv) {
    mv.setViewName("order/index");
    return mv;
  }



  @ResponseBody
  @RequestMapping(value = "/findAll.do")
  public Map<String, Object> findAll(HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    int rows = ServletRequestUtils.getIntParameter(request, "rows", 10);
    List<Order> orders = orderService.findOrderAll((page - 1) * rows, rows);
    results.put("rows", orders);
    results.put("total", orderService.countOrderAll());
    return results;
  }

  @ResponseBody
  @RequestMapping(value = "/addOrUpdateItem.do")
  public JsonResult addOrUpdateItem(@RequestBody Order bean, HttpServletRequest request) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    if (bean.getId() == null || bean.getId() < 1) {
      bean.setEnterTime(new Timestamp(System.currentTimeMillis()));
      bean.setTotalPrice(new BigDecimal(0));
      if(bean.getJieZhang()==0){
        orderService.setRoomStatus(bean.getRoomId(), true);
      }
      baseService.save(bean);
    } else {
      String sql = "SELECT * FROM orders where id=" + bean.getId();
      Order order = baseService.findOneBySQL(sql, new Order());
      order.setPersionNum(bean.getPersionNum());
      if (bean.getJieZhang().intValue() == 1) {
        order.setLeaveTime(new Timestamp(System.currentTimeMillis()));
        if (orderService.checkServiceIsExistsNoSetBjs(order.getId())) {
          return JsonResult.failed(-1, "存在未指定保健师的服务项。");
        }
        if (orderService.checkTaocanIsExistsNoSetBjs(order.getId())) {
          return JsonResult.failed(-1, "存在未指定保健师的套餐项。");
        }
      }
      order.setJieZhang(bean.getJieZhang());
      order.setGather_price(bean.getGather_price());
      baseService.update(order);
      orderService.setRoomStatus(order.getRoomId(), false);
    }
    results.put("msg", "操作成功！");
    return JsonResult.success(true);
  }

  
  @ResponseBody
  @RequestMapping("/deleteById.do")
  public Map<String, Object> delete(int id) throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    orderService.deleteOrder(id);
    results.put("msg", "操作成功！");
    return results;
  }

  @ResponseBody
  @RequestMapping(value = "/findBusinessByOrder.do")
  public JsonResult findBusinessByOrder(int orderId) throws Exception {
    Map<String, Object> resultMap = new HashMap<String, Object>(3);
    resultMap.put("service_items", orderService.findServiceByOrder(orderId));
    resultMap.put("taocan_items", orderService.findTaocanByOrder(orderId));
    resultMap.put("bjs_items", orderService.getIdleBaojianshi(orderId));
    return JsonResult.success(resultMap);
  }

  @ResponseBody
  @RequestMapping(value = "/updateBjs.do")
  public JsonResult selectBjs(String json) throws Exception {
    JsonObject jsonObject = JsonUtils.parserJSON(json).getAsJsonObject();
    // int orderId = jsonObject.get("orderId").getAsInt();
    JsonArray array = jsonObject.get("items").getAsJsonArray();
    for (int i = 0; i < array.size(); i++) {
      JsonObject jsonEle = array.get(i).getAsJsonObject();
      // 业务ID
      int id = jsonEle.get("id").getAsInt();
      // 保健师ID
      int bjsId = jsonEle.get("bjs_id").getAsInt();
      // 业务类型 taocan,service
      String type = jsonEle.get("type").getAsString();
      Object bean = null;
      if ("taocan".equals(type)) {
        TaocanOrder order = orderService.getTaocanOrderById(id);
        order.setBjsId(bjsId);
        bean = order;
      } else {
        ServiceOrder order = orderService.getServiceOrderById(id);
        order.setBjsId(bjsId);
        bean = order;
      }
      baseService.update(bean);
    }
    return JsonResult.success(true);
  }

}
