package com.meteor.common.core;

/**
 * 公共字典/常量
 *
 * @author SuperMu
 * @time 2019-07-06
 */
public class GlobalConstant {

  public static int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);
}
