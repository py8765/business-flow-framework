package com.ihome.platform.core;

import com.ihome.platform.enums.MethodType;

public interface IFlowBuilderService {

	FlowBuilder invoke(String name, MethodType type, Object obj, Object... args);

	FlowBuilder pipe();

	FlowResponse build();

}
