package com.meteor.demo.service.goods;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.DataResult;
import com.meteor.demo.service.goods.dto.GoodsCommonDTO;

import java.util.List;

/**
 * 商品批量查询服务
 *
 * @author SuperMu
 * @time 2020-04-16
 */
@MtProvider
public interface GoodsBatchViewService {

    DataResult<List<GoodsCommonDTO>> goodsBatchQuery();

}
