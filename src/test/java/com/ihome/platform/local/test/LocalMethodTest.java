package com.ihome.platform.local.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.ihome.platform.core.FlowBuilder;
import com.ihome.platform.core.FlowManager;
import com.ihome.platform.core.FlowResponse;
import com.ihome.platform.enums.MethodType;
import com.ihome.platform.local.LocalDefinition;
import com.ihome.platform.local.LocalRegistry;

public class LocalMethodTest {

	@Test
	public void testAnnotation() {
		FlowManager flowManager = new FlowManager();
		flowManager.setScanPackages("com.ihome.platform.local.test");
		flowManager.postProcessBeanDefinitionRegistry(null);

		LocalRegistry localRegistry = flowManager.getLocalRegistry();
		LocalDefinition localDefinition = localRegistry.getLocalDefinition("method1");

		assertTrue(localDefinition.getReturnType().equals(String.class));

		FlowBuilder flowBuilder = new FlowBuilder(flowManager);
		// 测试静态方法
		flowBuilder.invoke("method4", MethodType.LOCAL, null, null).build();

		LocalMethodData data = new LocalMethodData();
		data.setName("123");
		// 测试实例方法
		flowBuilder.invoke("method2", MethodType.LOCAL, data, "123").build();

		// 测试返回值方法
		FlowResponse flowResponse = flowBuilder.invoke("method1", MethodType.LOCAL, data, null).build();
		assertEquals("123", flowResponse.getReturnInstance().toString());

		String[] args = new String[] { "1", "2", "3" };

		flowBuilder.invoke("method1", MethodType.LOCAL, data, null).pipe(0)
				.invoke("method5", MethodType.LOCAL, data, args).build();

	}

}
