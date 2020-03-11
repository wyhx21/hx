package org.layz.hx.core.pojo.info;

import org.layz.hx.base.annotation.HxTable;

import java.io.Serializable;
import java.util.List;

public class TableClassInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7583005990125797807L;
	private Class<?> clazz;
	private HxTable table;
	private String id;
	private String tableName;
	private List<FieldColumnInfo> fieldList;
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public HxTable getTable() {
		return table;
	}
	public void setTable(HxTable table) {
		this.table = table;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<FieldColumnInfo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldColumnInfo> fieldList) {
		this.fieldList = fieldList;
	}
}
