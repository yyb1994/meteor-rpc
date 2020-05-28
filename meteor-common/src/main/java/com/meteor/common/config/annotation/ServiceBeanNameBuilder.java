package com.meteor.common.config.annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;

public class ServiceBeanNameBuilder {
    private static final String SEPARATOR = ":";

    // Required
    private String interfaceClassName;

    // Optional
    private String version;

    private String group;

    /**
     * bean name构造
     */
    public static String generateServiceBeanName(AnnotationAttributes serviceAnnotationAttributes, Class<?> interfaceClass) {
        ServiceBeanNameBuilder builder = create(interfaceClass)
                .group(serviceAnnotationAttributes.getString("group"))
                .version(serviceAnnotationAttributes.getString("version"));
        return builder.build();
    }

    public static ServiceBeanNameBuilder create(Class<?> interfaceClass) {
        return new ServiceBeanNameBuilder(interfaceClass.getName());
    }

    private ServiceBeanNameBuilder(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
    }


    private static void append(StringBuilder builder, String value) {
        if (StringUtils.isNotBlank(value)) {
            builder.append(SEPARATOR).append(value);
        }
    }

    public ServiceBeanNameBuilder group(String group) {
        this.group = group;
        return this;
    }

    public ServiceBeanNameBuilder version(String version) {
        this.version = version;
        return this;
    }

    public String build() {
        StringBuilder beanNameBuilder = new StringBuilder("ServiceBean");
        // Required
        append(beanNameBuilder, interfaceClassName);
        // Optional
        append(beanNameBuilder, version);
        append(beanNameBuilder, group);
        // Build and remove last ":"
        String rawBeanName = beanNameBuilder.toString();
//        // Resolve placeholders
//        return environment.resolvePlaceholders(rawBeanName);
        return rawBeanName;
    }
}
