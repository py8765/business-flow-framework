package com.ihome.platform.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.ihome.platform.annotation.ActivityMethod;
import com.ihome.platform.exception.RegisterException;
import com.ihome.platform.local.LocalDefinition;

public class DefinitionFactory {
	private static final Logger logger = LoggerFactory.getLogger(DefinitionFactory.class);

	public static LocalDefinition createLocalDefinition(Method method) {
		logger.debug("local definition : " + method.getDeclaringClass().getName() + " --> " + method.getName());

		ActivityMethod activityMethod = method.getAnnotation(ActivityMethod.class);

		Class<?> returnType = method.getReturnType();
		Type genericType = method.getGenericReturnType();

		logger.debug("local definition return type : " + returnType.getName() + " generic type : " + genericType);

		String name = activityMethod.value();
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("local method annotation value should not be empty");
		}

		LocalDefinition localDefinition = createLocalDefinition(name, method, activityMethod);
		return localDefinition;
	}

	private static LocalDefinition createLocalDefinition(String name, Method method, ActivityMethod activityMethod) {
		LocalDefinition localDefinition = LocalDefinition.createInstance();
		String describe = activityMethod.description();
		fillAbstractDefinition(localDefinition, method, name, describe);
		return localDefinition;
	}

	private static void fillAbstractDefinition(AbstractDefinition definition, Method method, String name,
			String description) {
		if (!method.isAccessible()) {
			if (Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			} else {
				throw new RegisterException("method " + method.getName() + " cannot access");
			}
		}
		Class<?> declareingClass = method.getDeclaringClass();
		definition.setMethod(method);
		definition.setIsStaticMethod(Modifier.isStatic(method.getModifiers()));
		definition.setClazz(declareingClass);
		definition.setName(name);
		definition.setDescription(description);
		definition.setReturnType(method.getReturnType());
	}

}
