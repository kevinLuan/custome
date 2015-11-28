package com.kevin.zuyu.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kevin.orm.mapping.BaseService;
import com.kevin.zuyu.model.BaoJianShi;
import com.kevin.zuyu.model.Order;
import com.kevin.zuyu.model.OrderProduct;
import com.kevin.zuyu.model.Product;
import com.kevin.zuyu.model.Room;
import com.kevin.zuyu.model.ServiceOrder;
import com.kevin.zuyu.model.TaoCan;
import com.kevin.zuyu.model.TaocanOrder;

@Service
@SuppressWarnings("unchecked")
public class OrderService {
  @Resource(name = "baseService")
  private BaseService<?> objService;
  @Resource(name = "baseService")
  private BaseService<Order> baseService;
  @Resource(name = "baseService")
  private BaseService<OrderProduct> productSerice;
  @Resource(name = "baseService")
  private BaseService<Product> productBaseService;
  @Resource(name = "baseService")
  private BaseService<Room> roomBaseService;
  @Resource(name = "baseService")
  private BaseService<TaocanOrder> taocanOrderBaseService;

  @Resource(name = "baseService")
  private BaseService<TaoCan> taocanBaseService;

  @Resource(name = "baseService")
  private BaseService<com.kevin.zuyu.model.Service> serviceBaseService;
  @Resource(name = "baseService")
  private BaseService<BaoJianShi> bjsBaseService;

  @Resource(name = "baseService")
  private BaseService<ServiceOrder> serviceOrderBaseService;

  public Product getProduct(int id) throws Exception {
    String sql = "SELECT * FROM product WHERE id=?";
    return productBaseService.findOneBySQL(sql, new Product(), id);
  }

  public Room getRoom(int id) throws Exception {
    String sql = "SELECT * FROM room WHERE id=?";
    return roomBaseService.findOneBySQL(sql, new Room(), id);
  }

  public TaoCan getTaoCan(int id) throws Exception {
    String sql = "SELECT * FROM tao_can WHERE id=?";
    return taocanBaseService.findOneBySQL(sql, new TaoCan(), id);
  }


  public com.kevin.zuyu.model.Service getService(int id) throws Exception {
    String sql = "SELECT * FROM service WHERE id=?";
    return serviceBaseService.findOneBySQL(sql, new com.kevin.zuyu.model.Service(), id);
  }

  public List<OrderProduct> findProductByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM order_product WHERE order_id=?";
    List<OrderProduct> list = productSerice.findAll(new OrderProduct(), sql, orderId);
    for (OrderProduct order : list) {
      Product product = getProduct(order.getProductId());
      if (product != null) {
        order.setProductName(product.getName());
      }
    }
    return list;
  }

  public List<BaoJianShi> findAllBjs() throws Exception {
    return (List<BaoJianShi>) bjsBaseService.findAll(new BaoJianShi());
  }

  public BaoJianShi getBjs(int id) throws Exception {
    if (id == 0) {
      return null;
    }
    String sql = "SELECT * FROM bao_jian_shi WHERE id=?";
    return bjsBaseService.findOneBySQL(sql, new BaoJianShi(), id);
  }

  public List<TaocanOrder> findTaocanByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM taocan_order WHERE order_id=?";
    List<TaocanOrder> list = taocanOrderBaseService.findAll(new TaocanOrder(), sql, orderId);
    for (TaocanOrder order : list) {
      TaoCan taoCan = getTaoCan(order.getTaocanId());
      if (taoCan != null) {
        order.setTaocanName(taoCan.getName());
      }
      BaoJianShi baoJianShi = getBjs(order.getBjsId());
      order.setBaoJianShi(baoJianShi);
      order.setBjsId(order.getBjsId());
    }
    return list;
  }

  public List<ServiceOrder> findServiceByOrder(int orderId) throws Exception {
    String sql = "SELECT * FROM service_order WHERE order_id=?";
    List<ServiceOrder> list = serviceOrderBaseService.findAll(new ServiceOrder(), sql, orderId);
    for (ServiceOrder order : list) {
      com.kevin.zuyu.model.Service service = getService(order.getServiceId());
      if (service != null) {
        order.setServiceName(service.getName());
      }
      order.getBjsId();
      order.setBaoJianShi(getBjs(order.getBjsId()));
    }
    return list;
  }

  public List<Order> findOrderAll(int start, int rows) throws Exception {
    String sql = "SELECT * FROM orders order by jie_zhang,id desc limit " + start + "," + rows;
    List<Order> list = baseService.findAll(new Order(), sql);
    for (int i = 0; i < list.size(); i++) {
      Order order = list.get(i);
      order.setRoom(getRoom(order.getRoomId()));
      order.setProducts(findProductByOrder(order.getId()));
      order.setTaocans(findTaocanByOrder(order.getId()));
      order.setServices(findServiceByOrder(order.getId()));
    }
    return list;
  }

  public List<Order> findOrderAll(String startDate, String endDate) throws Exception {
    String sql = "SELECT * FROM orders WHERE date(enter_time)>=? and date(enter_time)<=?";
    List<Order> list = baseService.findAll(new Order(), sql, startDate, endDate);
    for (int i = 0; i < list.size(); i++) {
      Order order = list.get(i);
      order.setRoom(getRoom(order.getRoomId()));
      order.setProducts(findProductByOrder(order.getId()));
      order.setTaocans(findTaocanByOrder(order.getId()));
      order.setServices(findServiceByOrder(order.getId()));
    }
    return list;
  }

  public TaocanOrder getTaocanOrderById(int id) throws Exception {
    String sql = "SELECT * FROM taocan_order WHERE id=?";
    TaocanOrder order = taocanOrderBaseService.findOneBySQL(sql, new TaocanOrder(), id);
    return order;
  }

  public ServiceOrder getServiceOrderById(int id) throws Exception {
    String sql = "SELECT * FROM service_order WHERE id=?";
    ServiceOrder order = serviceOrderBaseService.findOneBySQL(sql, new ServiceOrder(), id);
    return order;
  }

  public boolean updateOrderPrice(int orderId) throws Exception {
    String sql = "SELECT sum(price) FROM taocan_order where order_id=" + orderId;
    int total = 0;
    {
      BigDecimal decimal = (BigDecimal) objService.findOnePrimitiveTypeBySQL(sql);
      if (decimal != null) {
        total = decimal.intValue();
      }
      sql = "SELECT sum(price*num) FROM order_product where order_id=" + orderId;
      decimal = (BigDecimal) objService.findOnePrimitiveTypeBySQL(sql);
      if (decimal != null) {
        total += decimal.intValue();
      }
      sql = "SELECT sum(price) FROM service_order where order_id=" + orderId;
      decimal = (BigDecimal) objService.findOnePrimitiveTypeBySQL(sql);
      if (decimal != null) {
        total += decimal.intValue();
      }
    }
    sql = "update orders set total_price=? where id=?";
    return baseService.updateBySql(sql, total, orderId) > 0;
  }

  public int countOrderAll() throws Exception {
    return objService.countAll(new Order());
  }

  /**
   * 检测是否存在未指定保健师的服务
   * 
   * @param orderId
   * @return
   * @throws Exception
   */
  public boolean checkServiceIsExistsNoSetBjs(int orderId) throws Exception {
    String sql = "SELECT count(id) FROM service_order where order_id=? and bjs_id=0";
    return this.objService.countBySQL(sql, orderId) > 0;
  }

  /**
   * 检测是否存在未指定保健师的套餐
   * 
   * @param orderId
   * @return
   * @throws Exception
   */
  public boolean checkTaocanIsExistsNoSetBjs(int orderId) throws Exception {
    String sql = "SELECT count(id) FROM taocan_order where order_id=? and bjs_id=0";
    return this.objService.countBySQL(sql, orderId) > 0;
  }

  public int setRoomStatus(int roomId, boolean use) throws SQLException {
    String sql = "UPDATE room SET status=" + (use ? "1" : "0") + " WHERE id=" + roomId;
    return objService.updateBySql(sql);
  }

  /**
   * 获取所有空闲保健师列表
   * 
   * @return
   * @throws Exception
   */
  public List<BaoJianShi> getIdleBaojianshi(int excuteOrderId) throws Exception {
    String sql =
        "SELECT * FROM bao_jian_shi where status=1 and id not in (" + "SELECT * FROM ("
            + "SELECT bjs_id FROM service_order WHERE order_id in ("
            + "select id from orders where jie_zhang=0 and id<>?" + ")"
            + "union all SELECT bjs_id FROM taocan_order WHERE order_id in ("
            + "select id from orders where jie_zhang=0 and id<>?" + ") )AS A )";
    return bjsBaseService.findAll(new BaoJianShi(), sql, excuteOrderId, excuteOrderId);
  }

  public void deleteOrder(int id) throws Exception {
    String sql = "DELETE FROM orders WHERE id=?";
    baseService.delete(sql, id);
    sql = "DELETE FROM service_order WHERE order_id=?";
    serviceOrderBaseService.delete(sql, id);
    sql = "DELETE FROM taocan_order WHERE order_id=?";
    taocanOrderBaseService.delete(sql, id);
  }
  /**
   * 按照时间统计收入总金额
   * @param start
   * @param last
   * @return
   * @throws SQLException
   */
  public List<Object[]> statLastOneMonthData(String start,String last) throws SQLException {
    String sql =
        "SELECT enter_time,gather_price FROM "
            + "(SELECT date(enter_time)as enter_time ,"
            + "sum(if(gather_price is null or gather_price=0,total_price,gather_price)) as gather_price"
            + " FROM orders where jie_zhang=1 and date(enter_time)>='" + start + "'"
            + " and date(enter_time)<='" + last + "' group by date(enter_time) )AS A" + "  order by enter_time asc";
    System.out.println(sql);
    return (List<Object[]>) this.objService.findBySQL(sql);
  }

  /**
   * 统计保健师结算金额
   * 
   * @param startTime
   * @param endTime
   * @return
   * @throws SQLException
   */
  public List<Object[]> stat_bjs_reckoning(String startTime, String endTime) throws SQLException {
    String sql =
        "SELECT B1.bjs_id,B2.employe_no,B1.price,B2.name FROM " + "(SELECT bjs_id,sum(price) as price FROM "
            + "(SELECT bjs_id,sum(price) as price FROM service_order where order_id in"
            + "(SELECT id FROM orders where jie_zhang=1 and leave_time>='" + startTime + "' "
                + "and date(leave_time)<='"+ endTime + "') group by bjs_id " + "union all "
            + "SELECT bjs_id,sum(price) as price FROM taocan_order where order_id in "
            + "(SELECT id FROM orders where jie_zhang=1 and leave_time>='" + startTime + "' and date(leave_time)<='"
            + endTime + "') group by bjs_id)AS A group by bjs_id) AS B1 "
            + "INNER JOIN bao_jian_shi as B2 ON B1.bjs_id=B2.id";

    return (List<Object[]>) objService.findBySQL(sql);
  }

  /**
   * 获取所有有效的套餐
   * 
   * @return
   * @throws Exception
   */
  public List<TaoCan> findAllValidTaocan() throws Exception {
    String sql = "SELECT * FROM tao_can WHERE status=1";
    return taocanBaseService.findAll(new TaoCan(), sql);
  }

  public List<com.kevin.zuyu.model.Service> findAllValidService() throws Exception {
    String sql = "SELECT * FROM service WHERE status=1";
    return serviceBaseService.findAll(new com.kevin.zuyu.model.Service(), sql);
  }
}
