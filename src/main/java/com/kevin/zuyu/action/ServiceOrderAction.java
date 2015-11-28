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

import com.kevin.commons.JsonResult;
import com.kevin.commons.JsonUtils;
import com.kevin.orm.mapping.BaseService;
import com.kevin.role.action.BaseController;
import com.kevin.zuyu.model.Service;
import com.kevin.zuyu.model.ServiceOrder;
import com.kevin.zuyu.service.OrderService;

/**
 * 服务订单项管理
 * 
 * @author SHOUSHEN LUAN
 *
 */
@Controller
@RequestMapping(value = "/service_order")
public class ServiceOrderAction extends BaseController {
  @Resource(name = "baseService")
  private BaseService<ServiceOrder> baseService;
  @Resource(name = "baseService")
  private BaseService<Service> serviceBaseService;
  @Autowired
  private OrderService orderService;

  @ResponseBody
  @RequestMapping(value = "/findServiceByOrder.do")
  public JsonResult findProductByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM service_order WHERE order_id=?";
    List<ServiceOrder> list = baseService.findAll(new ServiceOrder(), sql, orderId);
    List<Service> services = new ArrayList<Service>();
    for (ServiceOrder order : list) {
      Service service = orderService.getService(order.getServiceId());
      services.add(service);
    }
    return JsonResult.success(services);
  }

  @ResponseBody
  @RequestMapping(value = "/addService.do")
  public JsonResult addOrUpdateItem(String json, int orderId) throws Exception {
    Map<String, String> map = (Map<String, String>) JsonUtils.parserJson(json);
    String sql = "DELETE FROM service_order WHERE order_id=?";
    baseService.delete(sql, orderId);
    for (Map.Entry<String, String> entry : map.entrySet()) {
      int num = Integer.parseInt(entry.getValue());
      int serviceId = Integer.parseInt(entry.getKey());
      Service service = orderService.getService(serviceId);
      if (service != null) {
        for (int i = 0; i < num; i++) {
          ServiceOrder order = new ServiceOrder();
          order.setCreateTime(new Date());
          order.setOrderId(orderId);
          order.setBjsId(0);
          order.setOrderId(orderId);
          order.setServiceId(serviceId);
          order.setPrice(service.getPrice());
          baseService.save(order);
        }
      }
    }
    orderService.updateOrderPrice(orderId);
    return JsonResult.success(true);
  }

  public boolean isExists(List<ServiceOrder> list, int serviceId) {
    for (ServiceOrder order : list) {
      return order.getServiceId() == serviceId;
    }
    return false;
  }

  private boolean isEquals(List<ServiceOrder> list, Service service) {
    for (int i = 0; i < list.size(); i++) {
      ServiceOrder order = list.get(i);
      if (order.getServiceId() == service.getId()) {
        if (order.getPrice().intValue() == service.getPrice().intValue()) {
          return true;
        }
      }
    }
    return false;
  }

  private ServiceOrder remove(List<ServiceOrder> list, Service service) {
    for (int i = 0; i < list.size(); i++) {
      ServiceOrder order = list.get(i);
      if (order.getServiceId() == service.getId()) {
        return list.remove(i);
      }
    }
    return null;
  }

  public List<ServiceOrder> findAllServiceOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM service_order WHERE order_id=?";
    return baseService.findAll(new ServiceOrder(), sql, orderId);
  }

}
