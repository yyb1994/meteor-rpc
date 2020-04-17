package com.meteor.server.impl;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.DataResult;
import com.meteor.service.goods.GoodsBatchViewService;
import com.meteor.service.goods.dto.GoodsCommonDTO;

import java.util.List;

@MtProvider(version = "1.0.0", timeout = 300, group = "mt")
public class GoodsBatchViewServiceImpl implements GoodsBatchViewService {

    public DataResult<List<GoodsCommonDTO>> goodsBatchQuery() {

        return new DataResult<List<GoodsCommonDTO>>();
    }
}
