package com.meteor.common.config.annotation;

import java.lang.annotation.*;

/**
 * 服务消费者接口注解
 * @author SuperMu
 * @time 2020-04-16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface MtCustomer {
    /**
     * Interface class, default value is void.class
     */
    Class<?> interfaceClass() default void.class;

    /**
     * Interface class name, default value is empty string
     */
    String interfaceName() default "";

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";

    /**
     * Timeout value for service invocation, default value is 0
     */
    int timeout() default 0;

}
