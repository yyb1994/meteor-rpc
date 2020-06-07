package com.meteor.common.util;


import com.meteor.common.rpc.ResultEnum;
import com.meteor.common.rpc.exception.CommonException;
import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * bean copy工具类
 *
 * @author SuperMu
 * @time 2019-06-14
 */
public class BeanUtil {
    private static final Logger log = LogUtils.getLogger(BeanUtil.class);

    /**
     * 批量复制List
     *
     * @param source    源数据
     * @param classType 目标数据class
     */
    public static <S, T> List<T> copyListProperties(List<S> source, Class<T> classType) {
        return copyListProperties(source, classType, null);
    }

    /**
     * 批量复制List
     *
     * @param source    源数据
     * @param classType 目标数据class
     * @param classMap  需要深拷贝的字段
     */
    public static <S, T> List<T> copyListProperties(List<S> source, Class<T> classType, Map<String, Class> classMap) {
        if (Objects.isNull(source) || Objects.isNull(classType)) return null;
        try {
            List<T> tList = new ArrayList<>(source.size());
            for (S s : source) {
                T target = classType.getConstructor().newInstance();
                copyProperties(s, target, classMap);
                tList.add(target);
            }
            return tList;
        } catch (Exception e) {
            throw new CommonException(ResultEnum.BEAN_COPY_ERROR.getCode(), ResultEnum.BEAN_COPY_ERROR.getMsg(), e);
        }
    }

    public static <S, T> T createFromProperties(S source, Class<T> classType) {
        return createFromProperties(source, classType, null);
    }

    public static <S, T> T createFromProperties(S source, Class<T> classType, Map<String, Class> classMap) {
        if (Objects.isNull(source) || Objects.isNull(classType)) return null;
        try {
            T target = classType.getConstructor().newInstance();
            copyProperties(source, target, classMap);
            // BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error(e);
            throw new CommonException(ResultEnum.BEAN_COPY_ERROR.getCode(), ResultEnum.BEAN_COPY_ERROR.getMsg(), e);
        }
    }


    private static void copyProperties(Object source, Object target, Map<String, Class> classMap) throws Exception {
        if (Objects.isNull(source) || Objects.isNull(target)) return;
        copyProperties(source, target, classMap, null);
    }

    /**
     * @param source           原数据
     * @param target           目标数据
     * @param classMap         需要深拷贝对象，key为属性名称，value为class
     * @param ignoreProperties 需要忽略复制的属性
     * @throws Exception e
     */
    private static void copyProperties(Object source, Object target, Map<String, Class> classMap, String... ignoreProperties) throws Exception {
        //获取bean信息
        BeanInfo sourceBI = Introspector.getBeanInfo(source.getClass());
        BeanInfo targetBI = Introspector.getBeanInfo(target.getClass());
        //获取bean的所有属性列表
        PropertyDescriptor[] sourcePDs = sourceBI.getPropertyDescriptors();
        PropertyDescriptor[] targetPDs = targetBI.getPropertyDescriptors();
        //todo 原生方法无法拿到加了@Accessors(chain = true) 注解之后的set 方法
        //PropertyDescriptor[] targetPDs = BeanUtils.getPropertyDescriptors(target.getClass());
        //聚合原目标方法
        Map<String, PropertyDescriptor> sourceMap = Arrays.stream(sourcePDs).collect(Collectors.toMap(PropertyDescriptor::getName, o -> o, (o, o2) -> o));
        List<String> ignoreList = ignoreProperties == null ? null : Arrays.asList(ignoreProperties);
        for (PropertyDescriptor targetPd : targetPDs) {
            //获取需要写的对象
            Method targetM = targetPd.getWriteMethod();
            String pdName = targetPd.getName();
            //判断是否是忽略复制属性
            if (ObjectUtils.isNotEmpty(targetM) && (Objects.isNull(ignoreList) || !ignoreList.contains(pdName))) {
                PropertyDescriptor sourcePd = sourceMap.get(pdName);
                if (sourcePd == null) {
                    continue;
                }
                Method sourceM = sourcePd.getReadMethod();
                if (ObjectUtils.isNotEmpty(sourceM)) {
                    Object value = null;
                    if (ClassUtils.isAssignable(targetM.getParameterTypes()[0], sourceM.getReturnType())) {
                        value = copyUtil(source, sourcePd, sourceM, targetM, classMap);
                        // targetM.invoke(target, amount);
                    } else {
                        Class targetClass = targetPd.getPropertyType();
                        value = copyUtil(source, sourcePd, sourceM, targetM, classMap);
                        //如果是时间类型，需要进行转换
                        if (DateUtil.isTime(targetClass)) {
                            value = dateConvert(value, targetClass);
                        } else {
                            //不是时间类型，需要深拷贝的对象
                            value = copyObject(value, sourcePd, targetClass, classMap);
                        }
                    }
                    targetM.invoke(target, value);
                }
            }
        }
    }


    private static Object copyUtil(Object source, PropertyDescriptor sourcePd, Method sourceM, Method targetM, Map<String, Class> classMap) throws Exception {
        if (Modifier.isPrivate(targetM.getModifiers())) {
            targetM.setAccessible(true);
        }
        //通过get 方法，获取原目标值
        Object value = sourceM.invoke(source);

        //如果是需要深copy的对象
        if (value instanceof Collection && ObjectUtils.isNotEmpty(classMap) && classMap.containsKey(sourcePd.getName())) {
            Class targetClass = classMap.get(sourcePd.getName());
            value = copyList((List) value, targetClass, classMap);
        }

        return value;
    }


    /**
     * 深拷贝 集合
     *
     * @param sourceList  原对象集合
     * @param targetClass 目标对象class
     * @param classMap    深拷贝map
     * @return 深拷贝之后的对象
     * @throws Exception e
     */
    private static Object copyList(List<Object> sourceList, Class targetClass, Map<String, Class> classMap) throws Exception {
        List<Object> targetList = new ArrayList<>(sourceList.size());
        //获取对象的所有构造器
        Constructor[] constructors = targetClass.getConstructors();
        if (constructors.length != 0) {
            //判断第一个方法是否是无参构造器，无参构造器入参数为0
            if (constructors[0].getParameterTypes().length == 0) {
                for (Object object : sourceList) {
                    Object value = constructors[0].newInstance();
                    copyProperties(object, value, classMap);
                    targetList.add(value);
                }
            }
        }
        return targetList;
    }

    /**
     * 深拷贝一个对象
     *
     * @param source      原数据
     * @param sourcePd    原数据get方法属性PropertyDescriptor
     * @param targetClass 目标数据class,用来实例化一个新的目标对象
     * @param classMap    需要深拷贝的map集合
     * @return 深拷贝之后的对象
     * @throws Exception e
     */
    private static Object copyObject(Object source, PropertyDescriptor sourcePd, Class targetClass, Map<String, Class> classMap) throws Exception {
        if (ObjectUtils.isEmpty(source)) return null;
        if (ObjectUtils.isNotEmpty(classMap) && classMap.containsKey(sourcePd.getName())) {
            Constructor[] constructors = targetClass.getConstructors();
            if (constructors.length != 0) {
                //判断第一个方法是否是无参构造器，无参构造器入参数为0
                if (constructors[0].getParameterTypes().length == 0) {
                    Object value = constructors[0].newInstance();
                    copyProperties(source, value, classMap);
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 时间转换
     *
     * @param value  未深拷贝的对象
     * @param TClass 目标对象时间class
     * @return 转换之后的时间
     */
    private static Object dateConvert(Object value, Class TClass) {
        if (ObjectUtils.isEmpty(value)) return null;
        Class SClass = value.getClass();
        if (SClass == TClass) return value;
        if (DateUtil.isTime(TClass) && DateUtil.isTime(SClass)) {
            if (SClass == Date.class && TClass == LocalDate.class) {
                return DateUtil.date2LocalDate((Date) value);
            }
            if (SClass == Date.class && TClass == LocalDateTime.class) {
                return DateUtil.date2LocalDateTime((Date) value);
            }
            if (SClass == LocalDate.class && TClass == Date.class) {
                return DateUtil.localDate2Date((LocalDate) value);
            }
            if (SClass == LocalDateTime.class && TClass == Date.class) {
                return DateUtil.localDateTimeDate2Date((LocalDateTime) value);
            }
        }
        return value;
    }
}
