package com.kevin.orm.mapping;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO {
	int insert(String sql, Object[] params) throws SQLException;

	int update(String sql, Object[] params) throws SQLException;

	int delete(String sql, Object[] params) throws SQLException;

	ResultData find(String sql, Object[] params) throws SQLException;

	List<String> getTables(String tablePrefix) throws SQLException;
	
	int createTable(String createTableShell) throws SQLException;
	
}
