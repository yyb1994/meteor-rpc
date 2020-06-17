package com.meteor.common.rpc.exception;

import com.meteor.common.rpc.ResultEnum;
import com.meteor.common.util.StrFormatter;
import lombok.Getter;

import java.io.Serializable;

/**
 * 通用rpc异常
 *
 * @author SuperMu
 * @time 2019-05-16
 */
@Getter
public class RpcException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3751421635600411804L;

    public String code;

    public RpcException() {
    }

    public RpcException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public RpcException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public RpcException(String code, Throwable e) {
        super(e.getMessage());
        this.code = code;
    }


    public RpcException(ResultEnum eum, Object... params) {
        super(StrFormatter.format(eum.getMsg(), params));
        this.code = eum.getCode();
    }


}
