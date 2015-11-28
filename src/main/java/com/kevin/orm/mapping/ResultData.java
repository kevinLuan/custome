package com.kevin.orm.mapping;

import java.lang.reflect.Field;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class ResultData<T> {
	private List<Object[]> data;
	private static final Logger LOGGER = Logger.getLogger(ResultData.class);
	private int columnCount = 0;
	private String[] columnNames;

	public List<Object[]> getData() {
		return data;
	}

	public static ResultData<?> newResultData(List<Object[]> list, ResultSetMetaData metaData) throws SQLException {
		ResultData<?> resultData = new ResultData();
		resultData.data = list;
		resultData.columnCount = metaData.getColumnCount();
		resultData.columnNames = new String[resultData.columnCount];
		for (int i = 0; i < resultData.columnNames.length; i++) {
			resultData.columnNames[i] = metaData.getColumnName(i + 1);
		}
		return resultData;
	}

	/**
	 * 支持基本数据类型
	 * 
	 * @return
	 */
	public T getOneResult() {
		if (data.size() > 0) {
			return (T) data.get(0)[0];
		}
		return null;
	}

	public Object[] getOneObject() {
		if (data.size() > 0) {
			return data.get(0);
		}
		return null;
	}

	public List<T> getResult(T t) throws Exception {
		if (data != null && data.size() > 0) {
			List<T> list = new ArrayList<T>();
			for (int i = 0; i < data.size(); i++) {
				list.add(setAttr(newInstance(t), data.get(i)));
			}
			return list;
		}
		return Collections.EMPTY_LIST;
	}

	private T newInstance(T t) {
		try {
			t = (T) t.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private T setAttr(T t, Object[] objects) throws Exception {
		if (objects == null || t == null) {
			return null;
		}
		Class<?> clazz = t.getClass();
		for (int i = 0; i < objects.length; i++) {
			String name = columnNames[i];
			Field field = getField(clazz, name);
			if (field != null) {
				setFieldVal(field, t, objects[i]);
			}
		}
		return t;
	}

	private static void setFieldVal(Field field, Object pojo, Object val) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, ParseException {
		if (field.isAccessible() == false) {
			field.setAccessible(true);
		}
		if (field.getType().isAssignableFrom(Integer.class)) {
			field.set(pojo, Integer.parseInt(val.toString()));
		} else {
			field.set(pojo, val);
		}
	}

	public T getOneResult(T t) throws Exception {
		return setAttr(t, getOneObject());
	}

	private Field getField(Class<?> clazz, String name) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			Column column = f.getAnnotation(Column.class);
			if (column != null) {
				if (column.name().length() == 0) {
					if (matches(f, name)) {
						return f;
					}
				} else {
					if (column.name().equals(name)) {
						return f;
					}
				}
			}
		}
		for (Field f : fields) {
			if (matches(f, name)) {
				return f;
			}
		}
		return null;
	}

	private boolean matches(Field field, String name) {
		if (field.getName().equals(name)) {
			return true;
		} else {
			if (GenerateSQL.pojoAttrToDBColumn(field.getName()).equals(name)) {
				return true;
			}
		}
		return false;
	}
}
