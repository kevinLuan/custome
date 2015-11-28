package com.kevin.orm.mapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kevin.orm.mapping.GenerateSQL.SQL;

@SuppressWarnings("unchecked")
public class BaseService<T> {

  private BaseDAO baseDAO;

  public BaseDAO getBaseDAO() {
    return baseDAO;
  }

  public void setBaseDAO(BaseDAO baseDAO) {
    this.baseDAO = baseDAO;
  }

  public int save(Object bean) throws Exception {
    List<Object> params = new ArrayList<Object>();
    String sql = GenerateSQL.buildInsertSQL(bean, params);
    return baseDAO.insert(sql, params.toArray());
  }

  public boolean update(Object bean) throws Exception {
    List<Object> params = new ArrayList<Object>();
    String sql = GenerateSQL.buildUpdateSQL(bean, params);
    return baseDAO.update(sql, params.toArray()) > 0;
  }

  public int updateBySql(String sql, Object... params) throws SQLException {
    return baseDAO.update(sql, params);
  }

  public List<?> findAll(Object bean) throws Exception {
    String sql = GenerateSQL.buildSelectSQL(bean).toSQL();
    return baseDAO.find(sql, null).getResult(bean);
  }

  public List<?> findAll(Object bean, int start, int size) throws Exception {
    String sql = null;
    sql = GenerateSQL.buildSelectSQL(bean, start, size);
    return baseDAO.find(sql, null).getResult(bean);
  }

  public List<?> findBySQL(Object rtnType, String sql, Object... params) throws Exception {
    return baseDAO.find(sql, params).getResult(rtnType);
  }

  public List<?> findBySQL(Object rtnType, SQL sql) throws Exception {
    return baseDAO.find(sql.toSQL(), sql.getWhereConditionVals()).getResult(rtnType);
  }

  public int countAll(Object bean) throws Exception {
    String sql = GenerateSQL.buildCountALLSQL(bean).toSQL();
    return countBySQL(sql);
  }

  public int countBySQL(String sql, Object... parms) throws Exception {
    ResultData<Number> data = baseDAO.find(sql, parms);
    return data.getOneResult().intValue();
  }

  public int findMaxBySQL(String sql, Object... parms) throws Exception {
    ResultData<Number> data = baseDAO.find(sql, parms);
    if (data != null && data.getOneResult() != null)
      return data.getOneResult().intValue();
    else
      return 0;
  }

  public T findOneBySQL(String sql, T type, Object... params) throws Exception {
    ResultData<T> resultData = baseDAO.find(sql, params);
    return resultData.getOneResult(type);
  }

  /**
   * 支持基本数据类型
   * 
   * @param sql
   * @return
   */
  public T findOnePrimitiveTypeBySQL(String sql, Object... params) throws Exception {
    ResultData<T> resultData = baseDAO.find(sql, params);
    return resultData.getOneResult();
  }

  public boolean delete(String sql, Object... params) throws Exception {
    return baseDAO.delete(sql, params) > 0;
  }

  public List<String> getTables(String tablePrefix) throws SQLException {
    return baseDAO.getTables(tablePrefix);
  }

  public void createTable(String createTableShell) throws SQLException {
    baseDAO.createTable(createTableShell);
  }

  public List<T> findAll(T t, String sql, Object... params) throws Exception {
    return baseDAO.find(sql, params).getResult(t);
  }

  public List<?> findBySQL(String sql, Object... params) throws SQLException {
    return (List<?>) baseDAO.find(sql, params).getData();
  }
}
