package com.ihome.platform.core;

import com.ihome.platform.enums.FlowType;
import com.ihome.platform.enums.MethodType;

public class FlowRequest {

	// pipe参数所在位置
	private int pipeIndex;

	private FlowType flowType;

	private String methodName;

	private MethodType methodType;

	private AbstractDefinition definition;

	// local method instance
	private Object obj;

	private Object[] args;

	public int getPipeIndex() {
		return pipeIndex;
	}

	public void setPipeIndex(int pipeIndex) {
		this.pipeIndex = pipeIndex;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public AbstractDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(AbstractDefinition definition) {
		this.definition = definition;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public FlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	public MethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
