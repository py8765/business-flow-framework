package com.ihome.platform.core;

public class FlowResponse {

	private Class<?> returnType;

	private Object returnInstance;

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public Object getReturnInstance() {
		return returnInstance;
	}

	public void setReturnInstance(Object returnInstance) {
		this.returnInstance = returnInstance;
	}

	public static FlowResponse createResponse() {
		return new FlowResponse();
	}

}
