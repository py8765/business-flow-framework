package com.ihome.platform.core;

import java.lang.reflect.Method;

public class AbstractDefinition {

	private String name;
	/**
	 * 别名，默认不填等于name，用于显示，所以可以设置中文名称
	 */
	private String beanName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 所在的方法
	 */
	private Method method;
	/**
	 * 是否是静态方法
	 */
	private Boolean isStaticMethod = Boolean.FALSE;
	/**
	 * 所在的类
	 */
	private Class<?> clazz;
	/**
	 * 返回值类型， 不能为泛型
	 */
	private Class<?> returnType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Boolean getIsStaticMethod() {
		return isStaticMethod;
	}

	public void setIsStaticMethod(Boolean isStaticMethod) {
		this.isStaticMethod = isStaticMethod;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

}
