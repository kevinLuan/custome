package com.kevin.commons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonResult implements Serializable {
  private static final long serialVersionUID = -3222522136199768585L;
  private Status status;
  private Object result;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public static class Status {
    private int code;
    private String description;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Status(int code, String description) {
      this.code = code;
      this.description = description;
    }

  }

  public static JsonResult failed(int code, String message) {
    JsonResult instance = new JsonResult();
    instance.setStatus(new Status(code, message));
    return instance;
  }

  public static JsonResult success(Object result) {
    JsonResult jsonResult = new JsonResult();
    jsonResult.setResult(result);
    jsonResult.setStatus(new Status(0, ""));
    return jsonResult;
  }

  public static JsonResult success(String key, Object value) {
    JsonResult jsonResult = new JsonResult();
    Map<String, Object> result = new HashMap<String, Object>();
    result.put(key, value);
    jsonResult.setResult(result);
    jsonResult.setStatus(new Status(0, ""));
    return jsonResult;
  }

}
