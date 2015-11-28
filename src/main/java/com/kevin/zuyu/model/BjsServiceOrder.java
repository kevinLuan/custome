package com.kevin.zuyu.model;

import java.sql.Timestamp;

import com.kevin.orm.mapping.AutoGenerated;
import com.kevin.orm.mapping.Column;
import com.kevin.orm.mapping.PK_ID;
import com.kevin.orm.mapping.Table;

@Table(name = "bjs_service_order")
public class BjsServiceOrder implements java.io.Serializable {

  private static final long serialVersionUID = -3417033750130871170L;
  @PK_ID
  @AutoGenerated
  private Integer id;
  @Column(name = "bjs_id")
  private Integer bjsId;
  @Column(name = "service_time")
  private Timestamp serviceTime;
  @Column(name = "service_order_no")
  private Integer serviceOrderNo;
  @Column(name = "service_id")
  private Integer serviceId;
  @Column(name = "tao_can_id")
  private Integer taoCanId;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setBjsId(Integer bjsId) {
    this.bjsId = bjsId;
  }

  public Integer getBjsId() {
    return this.bjsId;
  }

  public void setServiceTime(Timestamp serviceTime) {
    this.serviceTime = serviceTime;
  }

  public Timestamp getServiceTime() {
    return this.serviceTime;
  }

  public void setServiceOrderNo(Integer serviceOrderNo) {
    this.serviceOrderNo = serviceOrderNo;
  }

  public Integer getServiceOrderNo() {
    return this.serviceOrderNo;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public Integer getServiceId() {
    return this.serviceId;
  }

  public void setTaoCanId(Integer taoCanId) {
    this.taoCanId = taoCanId;
  }

  public Integer getTaoCanId() {
    return this.taoCanId;
  }

  public BjsServiceOrder() {}

  public BjsServiceOrder(Integer id, Integer bjsId, Timestamp serviceTime, Integer serviceOrderNo, Integer serviceId,
      Integer taoCanId) {
    this.id = id;
    this.bjsId = bjsId;
    this.serviceTime = serviceTime;
    this.serviceOrderNo = serviceOrderNo;
    this.serviceId = serviceId;
    this.taoCanId = taoCanId;
  }
}