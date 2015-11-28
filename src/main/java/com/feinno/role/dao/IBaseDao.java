/**
 * 
 */
package com.feinno.role.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

/**
 * @author <a href="mailto:wxh512@gmail.com">Wang XuHui</a>
 * 
 */
public interface IBaseDao {

	// 查询返回List
	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String statementName);

	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String statementName, int skipResults,
			int maxResults);

	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String statementName,
			Object parameterObject);

	@SuppressWarnings("unchecked")
	public List<Object> queryForList(String statementName,
			Object parameterObject, int skipResults, int maxResults);

	// 查询返回Map
	@SuppressWarnings("unchecked")
	public Map queryForList(String statementName, Object parameterObject,
			String keyProperty);

	@SuppressWarnings("unchecked")
	public Map queryForList(String statementName, Object parameterObject,
			String keyProperty, String valueProperty);

	// 查询返回实体对象
	public Object queryForObject(String statementName);

	public Object queryForObject(String statementName, Object parameterObject);

	public Object queryForObject(String statementName, Object parameterObject,
			Object resultObject);

	// 删除语句
	public int delete(String statementName);

	public int delete(String statementName, Object parameterObject);

	public void delete(String statementName, Object parameterObject,
			int requiredRowsAffected);

	// 插入语句
	public Object insert(String statementName);

	public Object insert(String statementName, Object parameterObject);

	// 更新语句
	public int update(String statementName);

	public int update(String statementName, Object parameterObject);

	public void update(String statementName, Object parameterObject,
			int requiredRowsAffected);

	// 接口回调
	public void execute(SqlMapClientCallback sccb);
}
