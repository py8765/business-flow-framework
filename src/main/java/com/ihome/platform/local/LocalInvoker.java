package com.ihome.platform.local;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ihome.platform.core.FlowInvoker;
import com.ihome.platform.core.FlowRequest;
import com.ihome.platform.core.FlowResponse;
import com.ihome.platform.enums.FlowType;

public class LocalInvoker extends FlowInvoker {

	public LocalInvoker(FlowType type, FlowRequest flowRequest, FlowResponse flowResponse) {
		super(type, flowRequest, flowResponse);
	}

	@Override
	public FlowResponse invoke(FlowInvoker invoker) {
		FlowRequest currentFlowRequest = super.getFlowRequest();
		FlowResponse currentFlowResponse = super.getFlowResponse();

		LocalDefinition localDefinition = (LocalDefinition) currentFlowRequest.getDefinition();
		Method invokerMethod = localDefinition.getMethod();
		Object result = null;
		// 调用之前没有pipe
		if (invoker == null) {
			if (localDefinition.getIsStaticMethod()) {
				try {
					result = invokerMethod.invoke(null, currentFlowRequest.getArgs());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				Object instance = currentFlowRequest.getObj();
				try {
					result = invokerMethod.invoke(instance, currentFlowRequest.getArgs());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		// 调用之前有pipe
		else {
			// 结果作为invoker的参数
			FlowResponse preFlowResponse = invoker.getFlowResponse();
			FlowRequest preFlowRequest = invoker.getFlowRequest();
			Object responseResult = preFlowResponse.getReturnInstance();
			Object instance = currentFlowRequest.getObj();
			Object[] args = currentFlowRequest.getArgs();
			int pipeIndex = preFlowRequest.getPipeIndex();
			Object[] argsResult = null;

			// 判定是否pipe到指定位置
			if (invoker.getFlowRequest() == null) {
				argsResult = new Object[] { responseResult };
			} else {
				argsResult = new Object[args.length];
				for (int i = 0; i < args.length; i++) {
					if (pipeIndex == i) {
						argsResult[i] = responseResult;
					} else {
						argsResult[i] = args[i];
					}
				}
			}

			if (localDefinition.getIsStaticMethod()) {
				try {
					result = invokerMethod.invoke(null, argsResult);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				try {
					result = invokerMethod.invoke(instance, argsResult);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		currentFlowResponse.setReturnInstance(result);
		currentFlowResponse.setReturnType(localDefinition.getReturnType());

		return currentFlowResponse;
	}

}
