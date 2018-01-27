package de.elsivas.basic.ioc;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import de.elsivas.basic.ioc.register.DawnRegisterdServiceUtils;

public class DawnContextInitializer  {

	private static final Log LOG = LogFactory.getLog(DawnContextInitializer.class);

	private static DawnContextInitializer instance;

	private ConfigurableListableBeanFactory beanFactory;

	private ApplicationContext appCtx;

	private StaticApplicationContext staticCtx;

	private AnnotationConfigApplicationContext annotatedCtx;

	private DawnContextInitializer() {

	}

	public static BeanFactory getBeanFactory() {
		return instance.beanFactory;
	}

	public static void close() {
		((ConfigurableApplicationContext) instance.appCtx).close();
		((ConfigurableApplicationContext) instance.staticCtx).close();
		((ConfigurableApplicationContext) instance.annotatedCtx).close();
	}

	public static void init(final String contextXml) {
		instance = new DawnContextInitializer();
		instance.init0(contextXml);
	}

	private void init0(final String contextXml) {
		initContexts(contextXml);

		initBeanFactory();

		registerBeans();

		annotatedCtx.refresh();
	}

	private void registerBeans() {
		final Map<String, BeanDefinition> registeredServices = DawnRegisterdServiceUtils.findServices();
		
		for (final String beanName : registeredServices.keySet()) {
			final BeanDefinition beanDefinition = registeredServices.get(beanName);
			staticCtx.getDefaultListableBeanFactory().registerBeanDefinition(beanName, beanDefinition);
			LOG.info("Register Bean: " + beanDefinition.getBeanClassName());
		}
	}

	private void initBeanFactory() {
		final DefaultListableBeanFactory defaultListableBeanFactory = staticCtx.getDefaultListableBeanFactory();
		beanFactory = defaultListableBeanFactory;
		defaultListableBeanFactory.registerSingleton("beanFactory", beanFactory);
		
	}

	private void initContexts(final String contextXml) {
		appCtx = new ClassPathXmlApplicationContext(contextXml);
		staticCtx = new StaticApplicationContext(appCtx);
		annotatedCtx = new AnnotationConfigApplicationContext(staticCtx.getDefaultListableBeanFactory());
	}
}
