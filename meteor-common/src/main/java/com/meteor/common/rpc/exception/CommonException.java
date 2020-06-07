package com.meteor.common.rpc.exception;

import com.meteor.common.rpc.ResultEnum;
import com.meteor.common.util.StrFormatter;
import lombok.Getter;

import java.io.Serializable;

/**
 * 通用异常
 *
 * @author SuperMu
 * @time 2019-05-16
 */
@Getter
public class CommonException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3751421635600411804L;

    public String code;

    public CommonException() {
    }

    public CommonException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public CommonException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public CommonException(String code, Throwable e) {
        //super(e);
        super(e.getMessage());
        this.code = code;
    }


    public CommonException(ResultEnum eum, Object... params) {
//        super(eum.name() + " | " + StrUtil.format(eum.getMsg(), params));
        //super(StrUtil.format(eum.getMsg(), params));
        //super(MessageFormat.format(eum.getMsg(), params));
        super(StrFormatter.format(eum.getMsg(), params));
        this.code = eum.getCode();
    }


}
