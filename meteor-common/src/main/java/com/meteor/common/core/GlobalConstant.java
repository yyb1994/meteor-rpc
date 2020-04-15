package com.meteor.common.core;

/**
 * 公共字典/常量
 *
 * @author SuperMu
 * @time 2019-07-06
 */
public class GlobalConstant {

    /**
     * Redis 共有key
     */
    public static class RedisKey {
        /**
         * 存储合约Map key
         */
        public static final String REDIS_CONTRACT_MAP_KEY = "CONTRACT_MAP";
        /**
         * 存储QuoteMap key
         */
        public static final String REDIS_QUOTE_MAP_KEY = "QUOTE_MAP";
        /**
         * 用户详情info Map key
         */
        public static final String REDIS_USER_INFO_MAP_KEY = "USER:USER_INFO_MAP";
        /**
         * 合约定时任务 key
         */
        public static final String CONTRACT_TIME_TASK = "CONTRACT:TIME_TASK";
        /**
         * 强制平仓定时任务 key
         */
        public static final String FORCED_LIQUIDATION_TIME_TASK = "CONTRACT:FORCED_LIQUIDATION:_TIME_TASK";
    }

}
