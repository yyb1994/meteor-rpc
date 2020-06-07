package com.meteor.common.util.page;

import com.meteor.common.rpc.BaseBean;
import lombok.Getter;
import lombok.Setter;


/**
 * 分页参数
 *
 * @author SuperMu
 * @time 2019-08-10
 */
@Getter
@Setter
public class CommonPage extends BaseBean {

    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页显示条数
     */
    private Integer pageSize;


    public CommonPage() {
    }

    public CommonPage(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static CommonPage getDefault() {
        return new CommonPage(0, 10);
    }

}
