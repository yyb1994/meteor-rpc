package com.meteor.common.util;


import com.meteor.common.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static com.meteor.common.core.ResultEnum.SELECT_LIST_ERROR;


/**
 * 获取集合指定下标的值
 *
 * @author SuperMu
 * @time 2019-08-04
 */
public class ListSelectUtil {

//    public static <T> T getOne(List<T> list) {
//        if (CollectionUtils.isNotEmpty(list)) {
//            return getValueByIndex(list, 0, true);
//        } else {
//            throw new CommonException("0000", "无法取到集合中的值");
//        }
//    }

    /**
     * 获取集合中的指定下标的值
     *
     * @param list    目标集合
     * @param index   指定下标
     * @param isThrow 是否要抛异常，如果不需要抛异常，则返回null
     * @param <T>     指定下标对象
     * @return 指定下标值
     */
    public static <T> T getValueByIndex(List<T> list, int index, boolean isThrow) {
        if (CollectionUtils.isNotEmpty(list) && list.size() + 1 >= index) {
            return list.get(index);
        } else {
            if (isThrow)
                throw new CommonException(SELECT_LIST_ERROR, index, list == null ? 0 : list.size());
            else return null;
        }
    }
}
