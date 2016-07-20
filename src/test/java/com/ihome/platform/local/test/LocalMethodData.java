package com.ihome.platform.local.test;

import com.ihome.platform.annotation.ActivityMethod;
import com.ihome.platform.enums.MethodType;

public class LocalMethodData {
	private String name;

	@ActivityMethod(value = "method1", type = MethodType.LOCAL)
	public String getName() {
		System.out.println("execute method1");
		return name;
	}

	@ActivityMethod(value = "method2", type = MethodType.LOCAL)
	public void setName(String name) {
		System.out.println("execute method2");
		System.out.println("method2 param: " + name);
		this.name = name;
	}

	@ActivityMethod(value = "method3", type = MethodType.LOCAL)
	public static LocalMethodData create() {
		System.out.println("execute method3");
		return new LocalMethodData();
	}

	@ActivityMethod(value = "method4", type = MethodType.LOCAL)
	public static void doThings() {
		System.out.println("execute method4");
	}

	@ActivityMethod(value = "method5", type = MethodType.LOCAL)
	public void setParams(String arg1, String arg2, String arg3) {
		System.out.println("arg1: " + arg1 + ", arg2: " + arg2 + ", arg3: " + arg3);
	}

}
