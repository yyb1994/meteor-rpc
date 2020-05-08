package com.meteor.service.goods;

import com.meteor.common.core.DataResult;
import com.meteor.service.goods.dto.GoodsCommonDTO;

import java.util.List;

/**
 * 商品批量查询服务
 *
 * @author SuperMu
 * @time 2020-04-16
 */
public interface GoodsBatchViewService {
    DataResult<List<GoodsCommonDTO>> goodsBatchQuery();

}
