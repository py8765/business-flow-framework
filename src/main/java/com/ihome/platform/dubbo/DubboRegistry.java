package com.ihome.platform.dubbo;

import java.util.Map;
import com.google.common.collect.Maps;
import com.ihome.platform.exception.RegisterException;

public class DubboRegistry {

	private Map<String, DubboDefinition> dubboRegistry = Maps.newConcurrentMap();

	public static DubboRegistry createDubboRegistry() {
		return new DubboRegistry();
	}

	public Map<String, DubboDefinition> getDubboRegistry() {
		return dubboRegistry;
	}

	public void setDubboRegistry(Map<String, DubboDefinition> dubboRegistry) {
		this.dubboRegistry = dubboRegistry;
	}

	public boolean registerDubboDefinition(DubboDefinition dubboDefinition) {
		String name = dubboDefinition.getName();
		if (dubboRegistry.containsKey(name)) {
			throw new RegisterException("Already registered dubbo method named " + name);
		}
		dubboRegistry.put(name, dubboDefinition);
		return true;
	}

}
