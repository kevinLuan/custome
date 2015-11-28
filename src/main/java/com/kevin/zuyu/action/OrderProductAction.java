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
import com.kevin.zuyu.model.OrderProduct;
import com.kevin.zuyu.model.Product;
import com.kevin.zuyu.service.OrderService;

@Controller
@RequestMapping(value = "/order_product")
public class OrderProductAction extends BaseController {

  @Resource(name = "baseService")
  private BaseService<OrderProduct> baseService;
  @Autowired
  private OrderService orderService;

  @ResponseBody
  @RequestMapping(value = "/findProductByOrder.do")
  public JsonResult findProductByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM order_product WHERE order_id=?";
    List<OrderProduct> list = baseService.findAll(new OrderProduct(), sql, orderId);
    List<Product> products = new ArrayList<Product>();
    for (OrderProduct order : list) {
      Product product = orderService.getProduct(order.getProductId());
      if (product != null) {
        for (int i = 0; i < order.getNum(); i++) {
          products.add(product);
        }
      }
    }
    return JsonResult.success(products);
  }

  @ResponseBody
  @RequestMapping(value = "/addProduct.do")
  public JsonResult addProduct(String json, int orderId) throws Exception {
    Map<String, String> map = (Map<String, String>) JsonUtils.parserJson(json);
    String sql = "DELETE FROM order_product WHERE order_id=?";
    baseService.delete(sql, orderId);
    for (Map.Entry<String, String> entry : map.entrySet()) {
      int productId = Integer.parseInt(entry.getKey());
      Product product = orderService.getProduct(productId);
      if (product != null) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setNum(Integer.parseInt(entry.getValue()));
        orderProduct.setOrderId(orderId);
        orderProduct.setProductId(productId);
        orderProduct.setCreateTime(new Date());
        orderProduct.setPrice(product.getPrice());
        baseService.save(orderProduct);
      }
    }
    orderService.updateOrderPrice(orderId);
    return JsonResult.success(true);
  }

}
