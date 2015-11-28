package com.kevin.orm.mapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BaseDAOImpl extends SqlMapClientDaoSupport implements BaseDAO {
	private static final Logger LOGGER = Logger.getLogger(BaseDAOImpl.class);

	@Override
	public int insert(String sql, Object[] params) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		try {
			pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}
			pStatement.executeUpdate();
			resultSet = pStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException ex) {
			LOGGER.error("SQL:" + sql + "|params:" + Arrays.toString(params) + "|error:" + ex.getMessage());
			throw ex;
		} finally {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (pStatement != null) {
				pStatement.close();
				pStatement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		}
		return -1;
	}

	@Override
	public int update(String sql, Object[] params) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}
			return pStatement.executeUpdate();
		} catch (SQLException ex) {
			LOGGER.error("SQL:" + sql + "|params:" + Arrays.toString(params) + "|error:" + ex.getMessage());
			throw ex;
		} finally {
			if (pStatement != null) {
				pStatement.close();
				pStatement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		}
	}

	@Override
	public int delete(String sql, Object[] params) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}
			return pStatement.executeUpdate();
		} catch (SQLException ex) {
			LOGGER.error("SQL:" + sql + "|params:" + Arrays.toString(params) + "|error:" + ex.getMessage());
			throw ex;
		} finally {
			if (pStatement != null) {
				pStatement.close();
				pStatement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		}
	}

	@Override
	public ResultData<?> find(String sql, Object[] params) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		try {
			pStatement = connection.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}
			resultSet = pStatement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<Object[]> list = new ArrayList<Object[]>();
			while (resultSet.next()) {
				Object[] row = new Object[metaData.getColumnCount()];
				for (int i = 0; i < row.length; i++) {
					row[i] = resultSet.getObject(i + 1);
				}
				list.add(row);
			}
			return ResultData.newResultData(list, metaData);
		} catch (SQLException ex) {
			LOGGER.error("SQL:" + sql + "|params:" + Arrays.toString(params) + "|error:" + ex.getMessage());
			throw ex;
		} finally {
			if (pStatement != null) {
				pStatement.close();
				pStatement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		}
	}
	

	public List<String> getTables(String tablePrefix) throws SQLException {
		Connection connection = getConnection();
		List<String> tableList = new ArrayList<String>();
		String sql = "show tables like ?;";
		PreparedStatement pStatement=null;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, tablePrefix+"%");
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			throw e;
		}finally {
			if (pStatement != null) {
				pStatement.close();
				pStatement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		}
		return tableList;
	}

	public int createTable(String createTableSQL) throws SQLException {
		try {
			Connection connection = null;
			Statement statement = null;
			try {
				connection = getDataSource().getConnection();
				statement = connection.createStatement();
				return statement.executeUpdate(createTableSQL);
			} catch (SQLException e) {
				LOGGER.error("createTable(" + createTableSQL + ")", e);
				throw e;
			} finally {
				if (statement != null) {
					statement.close();
					statement = null;
				}
				if (connection != null) {
					connection.close();
					connection = null;
				}
			}
		} catch (RuntimeException e) {
			LOGGER.error("createTable(" + createTableSQL + ")", e);
			throw e;
		}
	}
	
	private Connection getConnection() throws SQLException {
		DataSource dataSource = this.getSqlMapClientTemplate().getDataSource();
		Connection connection;
		do {
			connection = dataSource.getConnection();
		} while (connection.isClosed());
		return connection;
	}
}
