package com.meteor.common.core;

import lombok.Data;

/**
 * 分组key
 * 多用于当做map key使用，通过多个key来确定一个唯一key
 * @author SuperMu
 * @time 2019-07-06
 */
@Data
public class GroupKey {
    private Object key1;

    private Object key2;

    private Object key3;

    private Object key4;

    private Object key5;

    public GroupKey(Object key1) {
        this.key1 = key1;
    }

    public GroupKey(Object key1, Object key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public GroupKey(Object key1, Object key2, Object key3) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
    }

    public GroupKey(Object key1, Object key2, Object key3, Object key4, Object key5) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
    }
}
