package com.ihome.platform.core;

import com.ihome.platform.enums.FlowType;

public abstract class FlowInvoker {

	private FlowType type;
	private FlowRequest flowRequest;
	private FlowResponse flowResponse;

	public FlowInvoker(FlowType type, FlowRequest flowRequest, FlowResponse flowResponse) {
		this.type = type;
		this.flowRequest = flowRequest;
		this.flowResponse = flowResponse;
	}

	public abstract FlowResponse invoke(FlowInvoker invoker);

	public FlowType getType() {
		return type;
	}

	public void setType(FlowType type) {
		this.type = type;
	}

	public FlowRequest getFlowRequest() {
		return flowRequest;
	}

	public void setFlowRequest(FlowRequest flowRequest) {
		this.flowRequest = flowRequest;
	}

	public FlowResponse getFlowResponse() {
		return flowResponse;
	}

	public void setFlowResponse(FlowResponse flowResponse) {
		this.flowResponse = flowResponse;
	}

}
