package com.ihome.platform.local;

import java.util.Map;
import com.google.common.collect.Maps;
import com.ihome.platform.exception.RegisterException;

public class SpringRegistry {
	private Map<String, SpringDefinition> springRegistry = Maps.newConcurrentMap();

	public Map<String, SpringDefinition> getSpringRegistry() {
		return springRegistry;
	}

	public void setSpringRegistry(Map<String, SpringDefinition> springRegistry) {
		this.springRegistry = springRegistry;
	}

	public static SpringRegistry createSpringRegistry() {
		return new SpringRegistry();
	}

	public boolean registerSpringDefinition(SpringDefinition springDefinition) {
		String name = springDefinition.getName();
		if (springRegistry.containsKey(name)) {
			throw new RegisterException("");
		}
		springRegistry.put(name, springDefinition);
		return true;
	}

}
