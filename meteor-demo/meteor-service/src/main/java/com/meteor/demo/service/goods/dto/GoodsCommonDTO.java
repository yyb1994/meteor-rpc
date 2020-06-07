package com.meteor.demo.service.goods.dto;

import com.meteor.common.rpc.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsCommonDTO extends BaseBean {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品产地
     */
    private String path;

}
