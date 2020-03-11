package org.layz.hx.base.info;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8304306633400926502L;
	private Field field;
	private String fieldName;
	private Class<?> fieldType;
	private Method methodGet;
	private Method methodSet;
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	public Method getMethodGet() {
		return methodGet;
	}
	public void setMethodGet(Method methodGet) {
		this.methodGet = methodGet;
	}
	public Method getMethodSet() {
		return methodSet;
	}
	public void setMethodSet(Method methodSet) {
		this.methodSet = methodSet;
	}
}
