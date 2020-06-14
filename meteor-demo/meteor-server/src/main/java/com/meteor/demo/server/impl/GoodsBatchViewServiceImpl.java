package com.meteor.demo.server.impl;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.rpc.DataResult;
import com.meteor.demo.service.goods.GoodsBatchViewService;
import com.meteor.demo.service.goods.dto.GoodsCommonDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@MtProvider(version = "1.0.0", timeout = 300, group = "mt")
public class GoodsBatchViewServiceImpl implements GoodsBatchViewService {

    public DataResult<List<GoodsCommonDTO>> goodsBatchQuery() {
        System.out.println(9999);
        return new DataResult<List<GoodsCommonDTO>>();
    }

    @Override
    public DataResult<List<GoodsCommonDTO>> goodsBatchQuery(String nameLike) {
        List<GoodsCommonDTO> goodsCommonDTOList = Arrays.asList(
                new GoodsCommonDTO("苹果", "河南"),
                new GoodsCommonDTO("橘子", "山东")
        );
        return new DataResult<>(goodsCommonDTOList);
    }
}
