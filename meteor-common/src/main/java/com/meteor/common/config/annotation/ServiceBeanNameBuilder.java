package com.meteor.common.config.annotation;

import com.meteor.common.core.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.Optional;

public class ServiceBeanNameBuilder {
    // Required
    private String interfaceClassName;

    // Optional
    private String version;

    private String group;

    /**
     * bean name构造
     */
    public static String generateServiceBeanName(AnnotationAttributes attributes, Class<?> interfaceClass) {
        ServiceBeanNameBuilder builder = create(interfaceClass)
                .group(Optional.ofNullable(attributes.getString(CommonConstants.GROUP_KEY)).orElse(CommonConstants.DEFAULT_GROUP))
                .version(Optional.ofNullable(attributes.getString(CommonConstants.VERSION_KEY)).orElse(CommonConstants.DEFAULT_VERSION));
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
            builder.append(value);
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
        StringBuilder beanNameBuilder = new StringBuilder();
        // Required
        append(beanNameBuilder, interfaceClassName);
        append(beanNameBuilder, CommonConstants.GROUP_SEPERATOR);
        // Optional
        append(beanNameBuilder, version);
        append(beanNameBuilder, CommonConstants.GROUP_SEPERATOR);
        append(beanNameBuilder, group);
        // Build and remove last ":"
        String rawBeanName = beanNameBuilder.toString();
        return rawBeanName;
    }
}
