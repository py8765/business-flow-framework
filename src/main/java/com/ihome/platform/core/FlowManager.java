package com.ihome.platform.core;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;

import com.google.common.collect.Lists;
import com.ihome.platform.annotation.ActivityMethod;
import com.ihome.platform.dubbo.DubboRegistry;
import com.ihome.platform.enums.MethodType;
import com.ihome.platform.http.HttpRegistry;
import com.ihome.platform.local.LocalDefinition;
import com.ihome.platform.local.LocalRegistry;
import com.ihome.platform.local.SpringRegistry;
import com.ihome.platform.utils.Const;

public class FlowManager implements BeanDefinitionRegistryPostProcessor, PriorityOrdered, ApplicationContextAware {

	private String scanPackages;
	private ApplicationContext context;

	private DubboRegistry dubboRegistry;

	private LocalRegistry localRegistry;

	private SpringRegistry springRegistry;

	private HttpRegistry httpRegistry;

	public String getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub

	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub

	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// 扫描指定文件夹获取注解
		String[] scanPackageArrs = this.scanPackages.split(Const.SCAN_SPLIT_TOKEN);
		FilterBuilder filter = new FilterBuilder();
		List<URL> urlList = Lists.newArrayList();
		for (String scanPackage : scanPackageArrs) {
			filter.includePackage(scanPackage);
			urlList.addAll(ClasspathHelper.forPackage(scanPackage));
		}
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(urlList).filterInputsBy(filter).setScanners(new SubTypesScanner(),
						new TypeAnnotationsScanner(), new FieldAnnotationsScanner(), new MethodAnnotationsScanner(),
						new MethodParameterScanner(), new MethodParameterNamesScanner(), new MemberUsageScanner()));

		// 初始化注册中心
		this.dubboRegistry = DubboRegistry.createDubboRegistry();
		this.localRegistry = LocalRegistry.createLocalRegistry();
		this.springRegistry = SpringRegistry.createSpringRegistry();
		this.httpRegistry = HttpRegistry.createHttpRegistry();

		handleAnnotation(reflections);
	}

	private void handleAnnotation(Reflections reflections) {
		Set<Method> methods = reflections.getMethodsAnnotatedWith(ActivityMethod.class);
		for (Method method : methods) {
			ActivityMethod activityMethod = method.getAnnotation(ActivityMethod.class);
			MethodType type = activityMethod.type();
			switch (type) {
			case DUBBO:
				// todo handle dubbo method
				break;
			case LOCAL:
				LocalDefinition localDefinition = DefinitionFactory.createLocalDefinition(method);
				this.localRegistry.registerLocalDefinition(localDefinition);
				break;
			case SPRINGLOCAL:
				// todo handle spring local method
				break;
			case HTTP:
				// todo handle http method
				break;
			default:
				break;
			}
		}
	}

	public DubboRegistry getDubboRegistry() {
		return dubboRegistry;
	}

	public void setDubboRegistry(DubboRegistry dubboRegistry) {
		this.dubboRegistry = dubboRegistry;
	}

	public LocalRegistry getLocalRegistry() {
		return localRegistry;
	}

	public void setLocalRegistry(LocalRegistry localRegistry) {
		this.localRegistry = localRegistry;
	}

	public SpringRegistry getSpringRegistry() {
		return springRegistry;
	}

	public void setSpringRegistry(SpringRegistry springRegistry) {
		this.springRegistry = springRegistry;
	}

	public HttpRegistry getHttpRegistry() {
		return httpRegistry;
	}

	public void setHttpRegistry(HttpRegistry httpRegistry) {
		this.httpRegistry = httpRegistry;
	}

}
