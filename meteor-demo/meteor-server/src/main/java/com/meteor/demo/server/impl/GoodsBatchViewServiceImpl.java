package com.meteor.demo.server.impl;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.DataResult;
import com.meteor.demo.service.goods.GoodsBatchViewService;
import com.meteor.demo.service.goods.dto.GoodsCommonDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@MtProvider(version = "1.0.0", timeout = 300, group = "mt")
public class GoodsBatchViewServiceImpl implements GoodsBatchViewService {

    public DataResult<List<GoodsCommonDTO>> goodsBatchQuery() {

        return new DataResult<List<GoodsCommonDTO>>();
    }
}
