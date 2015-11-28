/**
 * 
 */
package com.kevin.role.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @author <a href="mailto:wxh512@gmail.com">Wang XuHui</a>
 * 
 */
public class BaseDaoImpl extends SqlMapClientDaoSupport implements IBaseDao {

	// 查询返回List
	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String statementName) {
		return getSqlMapClientTemplate().queryForList(statementName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, int skipResults,
			int maxResults) {
		return getSqlMapClientTemplate().queryForList(statementName,
				skipResults, maxResults);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementName,
				parameterObject);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, Object parameterObject,
			int skipResults, int maxResults) {
		return getSqlMapClientTemplate().queryForList(statementName,
				parameterObject, skipResults, maxResults);
	}

	// 查询返回Map
	@Override
	@SuppressWarnings("unchecked")
	public Map queryForList(String statementName, Object parameterObject,
			String keyProperty) {
		return getSqlMapClientTemplate().queryForMap(statementName,
				parameterObject, keyProperty);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map queryForList(String statementName, Object parameterObject,
			String keyProperty, String valueProperty) {
		return getSqlMapClientTemplate().queryForMap(statementName,
				parameterObject, keyProperty, valueProperty);
	}

	// 查询返回实体对象
	@Override
	public Object queryForObject(String statementName) {
		return getSqlMapClientTemplate().queryForObject(statementName);
	}

	@Override
	public Object queryForObject(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
	}

	@Override
	public Object queryForObject(String statementName, Object parameterObject,
			Object resultObject) {
		return getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject, resultObject);
	}

	// 删除语句
	@Override
	public int delete(String statementName) {
		return getSqlMapClientTemplate().delete(statementName);
	}

	@Override
	public int delete(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	@Override
	public void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) {
		getSqlMapClientTemplate().delete(statementName, parameterObject,
				requiredRowsAffected);
	}

	// 插入语句
	@Override
	public Object insert(String statementName) {
		return getSqlMapClientTemplate().insert(statementName);
	}

	@Override
	public Object insert(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	// 更新语句
	@Override
	public int update(String statementName) {
		return getSqlMapClientTemplate().update(statementName);
	}

	@Override
	public int update(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	@Override
	public void update(String statementName, Object parameterObject,
			int requiredRowsAffected) {
		getSqlMapClientTemplate().update(statementName, parameterObject,
				requiredRowsAffected);
	}

	// 接口回调
	@Override
	public void execute(SqlMapClientCallback sccb) {
		getSqlMapClientTemplate().execute(sccb);
	}

}
