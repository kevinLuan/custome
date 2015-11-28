package com.kevin.zuyu.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feinno.commons.JsonResult;
import com.feinno.commons.JsonUtils;
import com.feinno.role.action.BaseController;
import com.kevin.orm.mapping.BaseService;
import com.kevin.zuyu.model.TaoCan;
import com.kevin.zuyu.model.TaocanOrder;
import com.kevin.zuyu.service.OrderService;

@Controller
@RequestMapping(value = "/taocan_order")
public class TaocanOrderAction extends BaseController {
  @Resource(name = "baseService")
  private BaseService<TaocanOrder> baseService;
  @Resource(name = "baseService")
  private BaseService<TaoCan> taocanBaseService;
  @Autowired
  private OrderService orderService;

  @ResponseBody
  @RequestMapping(value = "/findTaocanByOrder.do")
  public JsonResult findTaocanByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM taocan_order WHERE order_id=?";
    List<TaocanOrder> list = baseService.findAll(new TaocanOrder(), sql, orderId);
    List<TaoCan> taoCans = new ArrayList<TaoCan>();
    for (TaocanOrder order : list) {
      TaoCan taoCan = orderService.getTaoCan(order.getTaocanId());
      if (taoCan != null) {
        taoCans.add(taoCan);
      }
    }
    return JsonResult.success(taoCans);
  }

  @ResponseBody
  @RequestMapping(value = "/addTaocan.do")
  public JsonResult addOrUpdateItem(String json, int orderId) throws Exception {
    Map<String, String> map = (Map<String, String>) JsonUtils.parserJson(json);
    String sql = "DELETE FROM taocan_order WHERE order_id=?";
    baseService.delete(sql, orderId);
    for (Map.Entry<String, String> entry : map.entrySet()) {
      int num = Integer.parseInt(entry.getValue());
      int taocanId = Integer.parseInt(entry.getKey());
      TaoCan taoCan = orderService.getTaoCan(taocanId);
      if (taoCan != null) {
        for (int i = 0; i < num; i++) {
          TaocanOrder order = new TaocanOrder();
          order.setBjsId(0);
          order.setCreateTime(new Date());
          order.setOrderId(orderId);
          order.setTaocanId(taocanId);
          order.setPrice(taoCan.getPrice());
          baseService.save(order);
        }
      }
    }
    orderService.updateOrderPrice(orderId);
    return JsonResult.success(true);
  }

}
