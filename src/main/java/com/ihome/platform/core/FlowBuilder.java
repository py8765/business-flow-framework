package com.ihome.platform.core;

import java.util.LinkedList;
import com.google.common.collect.Lists;
import com.ihome.platform.enums.FlowType;
import com.ihome.platform.enums.MethodType;
import com.ihome.platform.exception.FlowBuildException;
import com.ihome.platform.local.LocalInvoker;
import com.ihome.platform.local.LocalRegistry;

public class FlowBuilder implements IFlowBuilderService {

	private LinkedList<FlowInvoker> flowLinkedList = Lists.newLinkedList();

	private FlowManager flowManager;

	public FlowBuilder(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	public FlowBuilder invoke(String name, MethodType type, Object obj, Object... args) {
		AbstractDefinition definition = null;
		FlowInvoker invoker = null;
		switch (type) {
		case DUBBO:
			// todo dubbo invoke handle
			break;
		case LOCAL:
			LocalRegistry localRegistry = flowManager.getLocalRegistry();
			definition = localRegistry.getLocalDefinition(name);
			if (null == definition) {
				throw new FlowBuildException("cannot find method named " + name);
			}
			FlowRequest flowRequest = createFlowRequest(FlowType.INVOKE, name, type, definition, obj, args);
			FlowResponse flowResponse = FlowResponse.createResponse();
			invoker = new LocalInvoker(FlowType.INVOKE, flowRequest, flowResponse);
			break;
		case SPRINGLOCAL:
			// todo spring local handle
			break;
		case HTTP:
			// todo http handle
			break;
		default:
			break;
		}
		flowLinkedList.add(invoker);
		return this;
	}

	public FlowBuilder pipe() {
		FlowInvoker invoker = new LocalInvoker(FlowType.PIPE, null, null);
		flowLinkedList.add(invoker);
		return this;
	}

	public FlowBuilder pipe(int index) {
		FlowRequest flowRequest = createFlowRequest(index);
		FlowInvoker invoker = new LocalInvoker(FlowType.PIPE, flowRequest, null);
		flowLinkedList.add(invoker);
		return this;
	}

	public FlowResponse build() {
		FlowInvoker invoker = new LocalInvoker(FlowType.BUILD, null, null);
		flowLinkedList.add(invoker);
		return handleBuildFlow();
	}

	private FlowRequest createFlowRequest(FlowType flowType, String name, MethodType type,
			AbstractDefinition definition, Object obj, Object... args) {
		FlowRequest flowRequest = new FlowRequest();
		flowRequest.setFlowType(flowType);
		flowRequest.setMethodName(name);
		flowRequest.setDefinition(definition);
		flowRequest.setMethodType(type);
		flowRequest.setObj(obj);
		flowRequest.setArgs(args);
		return flowRequest;
	}

	private FlowRequest createFlowRequest(int pipeIndex) {
		FlowRequest flowRequest = new FlowRequest();
		flowRequest.setPipeIndex(pipeIndex);
		return flowRequest;
	}

	private FlowResponse handleBuildFlow() {
		FlowResponse flowResponse = null;
		if (!flowLinkedList.isEmpty()) {
			FlowInvoker preFlowInvoker = null;
			while (!flowLinkedList.isEmpty()) {
				FlowInvoker flowInvoker = flowLinkedList.poll();
				// 检查流程顺序
				checkFlowOrder(preFlowInvoker, flowInvoker);
				FlowType flowType = flowInvoker.getType();
				switch (flowType) {
				case INVOKE:
					flowResponse = flowInvoker.invoke(preFlowInvoker);
					break;
				case PIPE:
					flowInvoker.setFlowResponse(preFlowInvoker.getFlowResponse());
					break;
				case BUILD:
					break;
				default:
					break;
				}
				preFlowInvoker = flowInvoker;
			}
		} else {
			throw new FlowBuildException("cannot build flow without element");
		}
		return flowResponse;
	}

	private void checkFlowOrder(FlowInvoker preFlowInvoker, FlowInvoker currentFlowInvoker) {
		if (null == preFlowInvoker && currentFlowInvoker.getType() != FlowType.INVOKE) {
			throw new FlowBuildException("cannot build with error flow order");
		}
		if ((currentFlowInvoker.getType() == FlowType.PIPE || currentFlowInvoker.getType() == FlowType.BUILD)
				&& preFlowInvoker.getType() != FlowType.INVOKE) {
			throw new FlowBuildException("cannot build with error flow order");
		}
	}

}
