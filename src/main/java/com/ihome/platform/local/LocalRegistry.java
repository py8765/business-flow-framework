package com.ihome.platform.local;

import java.util.Map;
import com.google.common.collect.Maps;
import com.ihome.platform.exception.RegisterException;

public class LocalRegistry {

	private Map<String, LocalDefinition> localRegistry = Maps.newConcurrentMap();

	public Map<String, LocalDefinition> getLocalRegistry() {
		return localRegistry;
	}

	public void setLocalRegistry(Map<String, LocalDefinition> localRegistry) {
		this.localRegistry = localRegistry;
	}

	public static LocalRegistry createLocalRegistry() {
		return new LocalRegistry();
	}

	public boolean registerLocalDefinition(LocalDefinition localDefinition) {
		String name = localDefinition.getName();
		if (localRegistry.containsKey(name)) {
			throw new RegisterException("Already registered local method named " + name);
		}
		localRegistry.put(name, localDefinition);
		return true;
	}

	public LocalDefinition getLocalDefinition(String name) {
		return localRegistry.get(name);
	}

}
