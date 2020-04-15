package com.meteor.common.core;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum ResultEnum implements Serializable {
    /**
     * code 定义规则，
     * 一位的只有0，1，2
     * 异常用 六位数表示
     * 5001开头 不需要人为干预异常（一般是指前端数据传输错误，不需要开发处理）
     * 6001开头 需要人为干预异常，需要联系开发同学处理
     */
    SUCCESS("0", "成功"),
    ERROR("1", "失败"),
    UNKNOWN_EXCEPTION("2", "未知异常"),
    RPC_CALL_FILE("3", "接口调用异常 "),
    DB_ERROR("4", "数据库异常"),
    PARAM_ERROR("5", "参数异常"),
    NOT_SUBMIT_AGAIN("6", "请勿重复提交"),

    /*5001开头 不需要人为干预异常（一般是指前端数据传输错误，不需要开发处理）*/



    /*6001开头 需要人为干预异常，需要联系开发同学处理*/


    /*7001开头 非业务异常*/
    BEAN_COPY_ERROR("7001.BEAN_COPY_ERROR", "BeanCopy失败"),
    SELECT_LIST_ERROR("", "无法取到集合下标{}的值,集合长度{}"),


    ;


    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
