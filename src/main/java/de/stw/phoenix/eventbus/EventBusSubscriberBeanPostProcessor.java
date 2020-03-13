package de.stw.phoenix.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

import java.lang.reflect.Method;
import java.util.Objects;

public class EventBusSubscriberBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    private final EventBus eventBus;

    @Autowired
    public EventBusSubscriberBeanPostProcessor(final EventBus eventBus) {
        this.eventBus = Objects.requireNonNull(eventBus);
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        for (Method m : bean.getClass().getMethods()) {
            if (m.isAnnotationPresent(Subscribe.class)) {
                this.eventBus.register(bean);
            }
        }
        return bean;
    }

    // TODO MVR unregistering of beans does not work properly. An exception is raised in the logs. Should be investigated
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        for (Method m : bean.getClass().getMethods()) {
            if (m.isAnnotationPresent(Subscribe.class)) {
                this.eventBus.unregister(bean);
            }
        }
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }
}
