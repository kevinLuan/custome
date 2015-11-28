package com.kevin.zuyu.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kevin.commons.DateUtils;
import com.kevin.commons.GeneratedExcel;
import com.kevin.commons.StringUtils;
import com.kevin.zuyu.model.BaoJianShi;
import com.kevin.zuyu.model.Order;
import com.kevin.zuyu.model.OrderProduct;
import com.kevin.zuyu.model.ServiceOrder;
import com.kevin.zuyu.model.TaocanOrder;
import com.kevin.zuyu.service.OrderService;

/**
 * 统计报表
 * 
 */
@Controller
@RequestMapping(value = "/report")
public class ReportAction {
  @Autowired
  private OrderService orderService;
  /**
   * 图标统计
   * @param mv
   * @return
   * @throws SQLException
   */
  @RequestMapping(value = "/index.do")
  public ModelAndView index(ModelAndView mv) throws SQLException {
    Set<String> lable = new TreeSet<String>();
    List<Float> flow = new ArrayList<Float>();
    String start = DateUtils.format(DateUtils.addDay(-30), "yyyy-MM-dd");
    String last = DateUtils.format(DateUtils.addDay(-1), "yyyy-MM-dd");
    mv.addObject("start", start);
    mv.addObject("last", last);
    List<Object[]> list = orderService.statLastOneMonthData(start,last);
    Date date = DateUtils.addDay(-30);
    Float max = 0.f;
    BigDecimal decimal = new BigDecimal(0).setScale(2, 4);
    for (int i = 0; i < 30; i++) {
      String dateTmp = DateUtils.format(DateUtils.addDay(date, i), "yyyy-MM-dd");
      for (int j = 0; j < list.size(); j++) {
        Object[] data = list.get(j);
        if (String.valueOf(data[0]).equals(dateTmp)) {
          lable.add(dateTmp);
          BigDecimal val = new BigDecimal(Float.parseFloat(data[1].toString()));
          val.setScale(2, 4);
          decimal = decimal.add(val);
          max = Math.max(max, val.floatValue());
          flow.add(Float.parseFloat(data[1].toString()));
        }
      }
      if (!lable.contains(dateTmp)) {
        lable.add(dateTmp);
        flow.add(0.f);
      }
    }
    mv.addObject("totalMoney", decimal.floatValue());
    if (max < 80) {
      mv.addObject("end_scale", 100);
      mv.addObject("scale_space", 10);
    } else {
      int total = (int) (max + (max * 0.3));
      String str = String.valueOf(total);
      str = str.substring(0, str.length() - 1) + "0";

      mv.addObject("scale_space", (int) (Integer.parseInt(str) / 10));
      mv.addObject("end_scale", str);
    }
    mv.addObject("lable", lable);
    mv.addObject("flow", flow);
    mv.setViewName("report/index");
    return mv;
  }

  /**
   * 统计保健师对账信息
   * 
   * @param mv
   * @return
   */
  @RequestMapping(value = "/bjs_reckoning.do")
  public ModelAndView reckoning(ModelAndView mv) {
    mv.setViewName("report/reckoning");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/stat_bjs_reckoning.do")
  public Map<String, Object> stat_bjs_reckoning(String startDate, String endDate) throws SQLException {
    Map<String, Object> results = new HashMap<String, Object>();
    if (StringUtils.isNotEmpty(startDate, endDate)) {
      List<Object[]> list = orderService.stat_bjs_reckoning(startDate, endDate);
      List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
      for (int i = 0; i < list.size(); i++) {
        Object[] data = list.get(i);
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("bjs_id", data[0]);
        map.put("employe_no", data[1]);
        map.put("price", data[2]);
        map.put("name", data[3]);
        resultList.add(map);
      }
      results.put("rows", resultList);
      results.put("total", list.size());
    } else {
      results.put("rows", Collections.EMPTY_LIST);
      results.put("total", 0);
    }
    return results;
  }

  /**
   * 数据导出Excel
   * 
   * @param startDate
   * @param endDate
   * @param response
   * @throws SQLException
   * @throws IOException
   */
  @RequestMapping(value = "/bjs_reckoning.html")
  public void bjs_reckoning(String startDate, String endDate, HttpServletResponse response) throws SQLException,
      IOException {
    List<Object[]> list = orderService.stat_bjs_reckoning(startDate, endDate);
    list.add(0, new Object[] {"保健师ID", "保健师号", "价格", "姓名"});
    GeneratedExcel.generatedExcel(list, response.getOutputStream(), "保健师对账表");
    response.getOutputStream().close();
  }

  @RequestMapping(value = "/order.do")
  public ModelAndView order(ModelAndView mv) {
    mv.setViewName("report/order");
    return mv;
  }

  @ResponseBody
  @RequestMapping(value = "/findOrderAll.do")
  public Map<String, Object> findOrderAll(HttpServletRequest request, String startDate, String endDate)
      throws Exception {
    Map<String, Object> results = new HashMap<String, Object>();
    List<Order> orders = orderService.findOrderAll(startDate, endDate);
    results.put("rows", orders);
    results.put("total", orderService.countOrderAll());
    return results;
  }

  @RequestMapping(value = "/exportOrder.html")
  public void exportOrder(String startDate, String endDate, HttpServletResponse response) throws Exception {
    List<Order> orders = orderService.findOrderAll(startDate, endDate);
    List<Object[]> dataList = new ArrayList<Object[]>();
    List<Object> titleList = new ArrayList<Object>();
    titleList.add("订单号");
    titleList.add("人数");
    titleList.add("进入时间");
    titleList.add("房间号");
    titleList.add("离开时间");
    titleList.add("商品");
    titleList.add("服务项目");
    titleList.add("套餐");
    titleList.add("应收金额");
    titleList.add("实收金额");
    titleList.add("是否结账");
    dataList.add(titleList.toArray());
    for (int i = 0; i < orders.size(); i++) {
      List<Object> data = new ArrayList<Object>();
      Order order = orders.get(i);

      data.add(order.getId());
      data.add(order.getPersionNum());
      data.add(order.getEnterTime());
      if (order.getRoom() != null) {
        data.add(order.getRoom().getName());
      } else {
        data.add(order.getRoomId());
      }
      data.add(order.getLeaveTime() == null ? "" : order.getLeaveTime());
      {
        List<OrderProduct> list = order.getProducts();
        StringBuilder products = new StringBuilder();
        for (int j = 0; j < list.size(); j++) {
          if (list.get(j).getProductName() != null) {
            products.append(list.get(j).getProductName());
          } else {
            products.append(list.get(j).getProductId());
          }
          products.append("|");
        }
        data.add(products.toString());
      }
      {
        List<ServiceOrder> services = order.getServices();
        StringBuilder build = new StringBuilder();
        for (int j = 0; j < services.size(); j++) {
          if (services.get(j).getServiceName() != null) {
            build.append(services.get(j).getServiceName());
          } else {
            build.append(services.get(j).getServiceId());
          }
          BaoJianShi baoJianShi= services.get(j).getBaoJianShi();
          if(baoJianShi!=null){
            build.append("-"+baoJianShi.getEmployeNo());
          }else{
            build.append("-"+services.get(j).getBjsId());
          }
          build.append("|");
        }
        data.add(build.toString());
      }
      {
        List<TaocanOrder> taocans = order.getTaocans();

        StringBuilder build = new StringBuilder();
        for (int j = 0; j < taocans.size(); j++) {
          if (taocans.get(j).getTaocanName() != null) {
            build.append(taocans.get(j).getTaocanName());
          } else {
            build.append(taocans.get(j).getTaocanId());
          }
          BaoJianShi baoJianShi= taocans.get(j).getBaoJianShi();
          if(baoJianShi!=null){
            build.append("-"+baoJianShi.getEmployeNo());
          }else{
            build.append("-"+taocans.get(j).getBjsId());
          }
          build.append("|");
        }
        data.add(build.toString());
      }
      data.add(order.getTotalPrice());//应收金额
      data.add(order.getGather_price());//实收金额
      data.add(order.getJieZhang().intValue() == 1 ? "已结账" : "未结账");
      dataList.add(data.toArray());
    }
    GeneratedExcel.generatedExcel(dataList, response.getOutputStream(), "订单列表");
    response.getOutputStream().close();
  }

}
