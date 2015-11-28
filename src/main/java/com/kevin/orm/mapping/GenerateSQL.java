package com.kevin.orm.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.feinno.commons.StringUtils;

public class GenerateSQL {
	public static SUPPORT_NO_ANNOTATION SUPPORT = SUPPORT_NO_ANNOTATION.DISABLE;

	public static enum SUPPORT_NO_ANNOTATION {
		DISABLE, /* 屏蔽没有注解的支持 */
		ENABLE /* 激活没有注解的支持 */
	}

	public static String getTableName(Object pojo) {
		Table table = pojo.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		} else {
			String className = pojo.getClass().getSimpleName();
			return pojoAttrToDBColumn(className);
		}
	}

	public static String pojoAttrToDBColumn(String name) {
		char[] chat = name.toCharArray();
		String dbName = String.valueOf(StringUtils.A_ZToa_z(chat[0]));
		for (int i = 1; i < chat.length; i++) {
			if (StringUtils.isA_Z(chat[i])) {
				dbName += "_" + StringUtils.A_ZToa_z(chat[i]);
			} else {
				dbName += chat[i];
			}
		}
		return dbName;
	}

	/**
	 * 生成:SELECT XXX FROM TABLE WHERE {PK}=?
	 * 
	 * @param pojo
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String buildSelectSQL_PK(Object pojo, List<Object> values) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = pojo.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder(100);
		builder.append("SELECT ");
		String where = "";
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			if (isDBField(fields[i])) {
				if (fields[i].isAnnotationPresent(PK_ID.class)) {
					if (where.length() == 0) {
						where = " WHERE " + getColumn(fields[i]) + "=?";
					} else {
						where += " AND " + getColumn(fields[i]) + "=?";
					}
					values.add(fields[i].get(pojo));
				}
				builder.append(getColumn(fields[i])).append(",");
			}
		}
		String start = builder.toString();
		start = start.substring(0, start.length() - 1);
		return start + " FROM " + getTableName(pojo) + where;
	}

	/**
	 * 生成:SELECT XXX FROM TABLE
	 * 
	 * @param pojo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static SQL buildSelectSQL(Object pojo) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = pojo.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder(100);
		builder.append("SELECT ");
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			if (isDBField(fields[i])) {
				builder.append(getColumn(fields[i])).append(",");
			}
		}
		String start = builder.toString();
		start = start.substring(0, start.length() - 1);
		return new SQL(start + " FROM " + getTableName(pojo));
	}

	/**
	 * 生成:SELECT XXX FROM TABLE LIMIT {start},{size}
	 * 
	 * @param pojo
	 * @param start
	 * @param size
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String buildSelectSQL(Object pojo, int start, int size) throws IllegalArgumentException, IllegalAccessException {
		return buildSelectSQL(pojo) + " LIMIT " + start + "," + size;
	}

	/**
	 * 生成UPDATE TABLE SET XX=XX WHERE PK={PK} 如果Pojo中不存在@PK_ID 则生成的SQL则没有Where条件
	 * 
	 * @param pojo
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String buildUpdateSQL(Object pojo, List<Object> values) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = pojo.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder(100);
		builder.append("UPDATE " + getTableName(pojo) + " SET ");
		List<Object> whereVals = new ArrayList<Object>();
		String where = "";
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			if (isDBField(fields[i])) {
				if (isPKField(fields[i])) {
					whereVals.add(fields[i].get(pojo));
					if (where.length() == 0) {
						where = " WHERE " + getColumn(fields[i]) + "=?";
					} else {
						where += " AND " + getColumn(fields[i]) + "=?";
					}
					continue;
				} else {
					builder.append(getColumn(fields[i])).append("=?,");
					values.add(fields[i].get(pojo));
				}
			}
		}
		for (Object val : whereVals) {
			values.add(val);
		}
		String start = builder.toString();
		start = start.substring(0, start.length() - 1);
		return start + where;
	}

	public static boolean isPKField(Field field) {
		PK_ID id = field.getAnnotation(PK_ID.class);
		if (id != null) {
			return true;
		}
		if (GenerateSQL.SUPPORT == SUPPORT_NO_ANNOTATION.DISABLE) {
			return false;
		}
		if (field.getName().equals("id")) {
			return true;
		}
		return false;
	}

	public static String getColumn(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			if (column.name().length() > 0) {
				return column.name();
			}else{
				return field.getName();
			}
		}
		return pojoAttrToDBColumn(field.getName());
	}

	/**
	 * 生成INSERT SQL: INSERT INTO TABLE(XX,XX) VALUES(?,?);
	 * 
	 * @param pojo
	 * @param values
	 * @return
	 * @throws IllegalAccessException
	 */
	public static String buildInsertSQL(Object pojo, List<Object> values) throws IllegalAccessException {
		Field[] fields = pojo.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder(100);
		builder.append("INSERT INTO " + getTableName(pojo) + "(");
		StringBuilder valueBuilder = new StringBuilder();
		valueBuilder.append(")VALUES(");
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			if (isDBField(fields[i])) {
				if (fields[i].isAnnotationPresent(AutoGenerated.class)) {
					continue;
				}
				builder.append(getColumn(fields[i])).append(",");
				valueBuilder.append("?,");
				values.add(fields[i].get(pojo));
			}
		}
		String start = builder.toString();
		start = start.substring(0, start.length() - 1);
		String end = valueBuilder.toString();
		end = end.substring(0, end.length() - 1);
		return start + end + ")";
	}

	public static boolean isDBField(Field field) {
		if (field.isAnnotationPresent(Column.class)) {
			return true;
		}
		if (field.isAnnotationPresent(PK_ID.class)) {
			return true;
		}
		if (GenerateSQL.SUPPORT == SUPPORT_NO_ANNOTATION.DISABLE) {
			return false;
		}
		int modifier = field.getModifiers();
		return !(Modifier.isStatic(modifier) | Modifier.isNative(modifier) | Modifier.isFinal(modifier) | Modifier.isTransient(modifier));
	}

	/**
	 * 生成DELETE SQL. 如果存在@PK_ID
	 * 
	 * @param pojo
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String buildDeleteSQL(Object pojo, List<Object> values) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = pojo.getClass().getDeclaredFields();
		StringBuilder builder = new StringBuilder(100);
		builder.append("DELETE FROM " + getTableName(pojo));
		StringBuilder whereBuild = new StringBuilder(20);
		whereBuild.append(" WHERE ");
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			if (isPKField(fields[i])) {
				if (values.size() > 0) {
					whereBuild.append(" AND " + getColumn(fields[i]) + "=?");
				} else {
					whereBuild.append(getColumn(fields[i]) + "=?");
				}
				values.add(fields[i].get(pojo));
			}
		}
		return builder.toString() + whereBuild.toString();
	}

	/**
	 * 生成SELECT COUNT(1) FROM TABLE
	 * 
	 * @param pojo
	 * @return
	 */
	public static SQL buildCountALLSQL(Object pojo) {
		return new SQL("SELECT COUNT(1) FROM " + getTableName(pojo));
	}

	public static class SQL {
		public StringBuilder sql = new StringBuilder(100);

		public SQL(String sql) {
			this.sql.append(sql);
		}

		public SQL appendSQL(String appendSQL) {
			sql.append(appendSQL);
			return this;
		}

		public SQL appendLimit(int start, int size) {
			this.sql.append(" LIMIT " + start + "," + size);
			return this;
		}
		public SQL appendOrderBy(String... fields){
			return appendOrderBy(Sort.ASC,fields);
		}
		public SQL appendOrderBy(Sort sort, String... fields) {
			this.sql.append(" ORDER BY ");
			for (int i = 0; i < fields.length; i++) {
				if (i == 0) {
					this.sql.append(fields[i]);
				} else {
					this.sql.append("," + fields[i]);
				}
			}
			this.sql.append(" " + sort);
			return this;
		}

		public String toSQL() {
			return sql.toString();
		}

		@Override
		public String toString() {
			return toSQL();
		}

		private List<Object> values = new ArrayList<Object>();

		/**
		 * 追加Where条件
		 * 
		 * @param condition
		 * @param name
		 * @param val
		 */
		public SQL appendWhereCondition(Condition condition, String name, Object val) {
			if (sql.toString().toUpperCase().indexOf(" WHERE ") > 0) {
				sql.append(" " + condition + " " + name + "=?");
			} else {
				sql.append(" WHERE " + name + "=?");
			}
			values.add(val);
			return this;
		}
		
		public SQL appendWhereCondition(String name, Object val) {
			if (sql.toString().toUpperCase().indexOf(" WHERE ") > 0) {
				sql.append(" " + Condition.AND + " " + name + "=?");
			} else {
				sql.append(" WHERE " + name + "=?");
			}
			values.add(val);
			return this;
		}

		public SQL appendLikeCondition(Condition condition, String name, Object val) {
			if (sql.toString().toUpperCase().indexOf(" WHERE ") > 0) {
				sql.append(" " + condition + " " + name + " like ?");
			} else {
				sql.append(" WHERE " + name + " like ?");
			}
			values.add(val);
			return this;
		}

		
		public Object[] getWhereConditionVals() {
			return values.toArray();
		}
	}

	public static enum Condition {
		AND, OR
	}

	public static enum Sort {
		ASC, DESC
	}
}
