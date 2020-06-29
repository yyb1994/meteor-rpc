package com.meteor.common.rpc;

import java.io.Serializable;


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

    /*2001开头 类方法异常*/
    SERVICE_NOT_FOUNT("2001", "无法找到类：{}"),
    SERVICE_METHOD_NOT_FOUNT("2001", "无法找到类：{} 对应的方法：{}"),


    /*3001开头 XXX异常*/

    /*4001开头 XXX异常*/;


    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
