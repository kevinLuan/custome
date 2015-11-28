package com.feinno.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  public JsonUtils() {}

  private JsonElement jsonElement;

  public static JsonUtils getInstance(String json) {
    JsonUtils jsonUtils = new JsonUtils();
    JsonParser jsonParser = new JsonParser();
    jsonUtils.jsonElement = jsonParser.parse(json);
    return jsonUtils;
  }

  public static JsonUtils getInstance(JsonElement jsonElement) {
    JsonUtils jsonUtils = new JsonUtils();
    jsonUtils.jsonElement = jsonElement;
    return jsonUtils;
  }

  public static JsonElement parserJSON(String json) {
    return new JsonParser().parse(json);
  }

  private List<String> asList(String[] keys) {
    List<String> list = new LinkedList<String>();
    if (keys != null && keys.length > 0) {
      for (int i = 0; i < keys.length; i++) {
        list.add(keys[i]);
      }
    }
    return list;
  }

  /**
   * 将字符串key.key.key转化成数组
   * 
   * @param expression
   * @return
   */
  private String[] toKeys(String expression) {
    return expression.split("\\.");
  }


  /**
   * 查询元素
   * 
   * @param jsonElement
   * @param strs
   * @param searchKey
   * @return
   */
  private JsonElement search(JsonElement jsonElement, List<String> list) {
    if (jsonElement.isJsonObject()) {
      return search(jsonElement.getAsJsonObject(), list);
    } else if (jsonElement.isJsonArray()) {
      JsonArray array = jsonElement.getAsJsonArray();
      if (array.size() > 0) {
        if (array.size() > 1) {
          System.err.println("警告：`" + array + "` 默认使用数组中第一个对象 ");
        }
        JsonElement el = array.get(0);
        if (el.isJsonObject()) {
          return search(el.getAsJsonObject(), list);
        }
      }
    }
    return null;
  }

  private JsonElement search(List<String> list) {
    if (jsonElement == null)
      return null;
    if (jsonElement.isJsonObject()) {
      return search(jsonElement.getAsJsonObject(), list);
    } else if (jsonElement.isJsonArray()) {
      return search(jsonElement.getAsJsonArray(), list);
    }
    return null;
  }

  /**
   * 搜索
   * 
   * @param expression 格式：key.key.key{层次结构必须一致}
   * @return
   */
  public JsonElement search(String expression) {
    String[] keys = toKeys(expression);
    List<String> list = asList(keys);
    return search(list);
  }

  public String searchAsString(String expression) {
    JsonElement jsonElement = search(expression);
    if (jsonElement == null) {
      return null;
    }
    if (jsonElement.isJsonPrimitive()) {
      return jsonElement.getAsString();
    }
    return jsonElement.toString();
  }

  private JsonElement search(JsonObject jsonObject, List<String> list) {
    if (list.size() == 0) {
      return null;
    }
    if (list.size() == 1) {
      String lastKey = list.remove(list.size() - 1);
      return jsonObject.get(lastKey);
    } else {
      JsonElement element = jsonObject.get(list.remove(0));
      if (element != null) {
        return search(element, list);
      }
    }
    return null;
  }


  private static Map<String, Object> parserJsonObject(JsonObject jsonObject) {
    Map<String, Object> map = new HashMap<String, Object>();
    Set<Entry<String, JsonElement>> set = jsonObject.entrySet();
    for (Entry<String, JsonElement> entry : set) {
      if (entry.getValue().isJsonPrimitive()) {
        map.put(entry.getKey(), entry.getValue().getAsString());
      } else if (entry.getValue().isJsonNull()) {
        map.put(entry.getKey(), "null");
      } else if (entry.getValue().isJsonArray()) {
        map.put(entry.getKey(), parserArrays(entry.getValue().getAsJsonArray()));
      } else if (entry.getValue().isJsonObject()) {
        map.put(entry.getKey(), parserJsonObject(entry.getValue().getAsJsonObject()));
      }
    }
    return map;
  }

  private static List<Object> parserArrays(JsonArray jsonArray) {
    List<Object> list = new ArrayList<Object>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonElement jsonElement = jsonArray.get(i);
      if (jsonElement.isJsonNull()) {
        list.add("null");
      } else if (jsonElement.isJsonArray()) {
        list.add(parserArrays(jsonElement.getAsJsonArray()));
      } else if (jsonElement.isJsonObject()) {
        list.add(parserJsonObject(jsonElement.getAsJsonObject()));
      } else if (jsonElement.isJsonPrimitive()) {
        list.add(jsonElement.getAsString());
      }
    }
    return list;
  }

  public JsonElement getJsonElement() {
    return jsonElement;
  }

  public void setJsonElement(JsonElement jsonElement) {
    this.jsonElement = jsonElement;
  }

  public String toString() {
    if (this.jsonElement != null) {
      return jsonElement.toString();
    }
    return null;
  }

  /**
   * <p>
   * 解析Json to POJO
   * </p>
   * <p>
   * {XX:XX} -> Map
   * </p>
   * <p>
   * [xx,xx] -> List
   * </p>
   * 
   * @param json
   * @return
   */
  public static Object parserJson(String json) {
    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElement = jsonParser.parse(json);
    if (jsonElement.isJsonArray()) {
      return parserArrays(jsonElement.getAsJsonArray());
    } else if (jsonElement.isJsonNull()) {
      return null;
    } else if (jsonElement.isJsonObject()) {
      return parserJsonObject(jsonElement.getAsJsonObject());
    } else if (jsonElement.isJsonPrimitive()) {
      return jsonElement.getAsString();
    }
    return null;
  }

  private boolean containsKey(JsonElement jsonElement, String key) {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
      if (entry.getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检测Key是否存在
   * 
   * @param expression key.key.xx
   * @return
   */
  public boolean containsKey(String expression) {
    String[] keys = toKeys(expression);
    if (keys.length == 1) {
      return containsKey(jsonElement, keys[0]);
    }
    List<String> list = asList(keys);
    String lastKey = list.remove(list.size() - 1);
    JsonElement element = search(list);
    return containsKey(element, lastKey);
  }

}
