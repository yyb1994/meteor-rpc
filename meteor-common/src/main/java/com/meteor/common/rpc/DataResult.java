package com.meteor.common.rpc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


/**
 * 统一返回结构体
 *
 * @author SuperMu
 * @time 2019-05-15
 */
@Getter
@Setter
public class DataResult<T> implements Serializable {
    private static final long serialVersionUID = -817780767118878736L;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回结果
     */
    private T data;
    /**
     * 消息码
     */
    private String code;
    /**
     * 数据条数，批量查询时使用
     */
    private int total;
    /**
     * 本次请求唯一ID，链路追踪使用
     */
    private String traceId;

    public DataResult() {
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.code = ResultEnum.SUCCESS.getCode();
    }

    public DataResult(String msg, String code) {
        this.msg = msg;
        this.code = code;
        // this.traceId = MDC.get("traceId");
    }

    public DataResult(String msg, String code, String traceId) {
        this.msg = msg;
        this.code = code;
        this.traceId = traceId;
    }


    public DataResult(T data) {
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.code = ResultEnum.SUCCESS.getCode();
        this.data = data;
        // this.traceId = MDC.get("traceId");
    }

    public DataResult(T data, int total) {
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.code = ResultEnum.SUCCESS.getCode();
        this.data = data;
        this.total = total;
        //this.traceId = MDC.get("traceId");
    }


    //是否成功
    public boolean success() {
        return Objects.equals(this.code, ResultEnum.SUCCESS.getCode());
    }


}
