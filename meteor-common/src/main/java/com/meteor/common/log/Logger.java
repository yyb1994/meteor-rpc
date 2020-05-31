package com.meteor.common.log;
/**
 * Logger interface
 * <p>
 * This interface is referred from commons-logging
 */
public interface Logger {

    /**
     * 打印 TRACE 等级的日志
     *
     * @param t 错误对象
     */
    void trace(Throwable t);

    /**
     * 打印 TRACE 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void trace(String format, Object... arguments);

    /**
     * 打印 TRACE 等级的日志
     *
     * @param t 错误对象
     * @param format 消息模板
     * @param arguments 参数
     */
    void trace(Throwable t, String format, Object... arguments);

    /**
     * 打印 DEBUG 等级的日志
     *
     * @param t 错误对象
     */
    void debug(Throwable t);

    /**
     * 打印 DEBUG 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void debug(String format, Object... arguments);

    /**
     * 打印 DEBUG 等级的日志
     *
     * @param t 错误对象
     * @param format 消息模板
     * @param arguments 参数
     */
    void debug(Throwable t, String format, Object... arguments);

    /**
     * 打印 INFO 等级的日志
     *
     * @param t 错误对象
     */
    void info(Throwable t);

    /**
     * 打印 INFO 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void info(String format, Object... arguments);

    /**
     * 打印 INFO 等级的日志
     *
     * @param t 错误对象
     * @param format 消息模板
     * @param arguments 参数
     */
    void info(Throwable t, String format, Object... arguments);

    /**
     * 打印 WARN 等级的日志
     *
     * @param t 错误对象
     */
    void warn(Throwable t);

    /**
     * 打印 WARN 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void warn(String format, Object... arguments);

    /**
     * 打印 WARN 等级的日志
     *
     * @param t 错误对象
     * @param format 消息模板
     * @param arguments 参数
     */
    void warn(Throwable t, String format, Object... arguments);

    /**
     * 打印 ERROR 等级的日志
     *
     * @param t 错误对象
     */
    void error(Throwable t);

    /**
     * 打印 ERROR 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void error(String format, Object... arguments);

    /**
     * 打印 ERROR 等级的日志
     *
     * @param t 错误对象
     * @param format 消息模板
     * @param arguments 参数
     */
    void error(Throwable t, String format, Object... arguments);

    /**
     * Is trace logging currently enabled?
     *
     * @return true if trace is enabled
     */
    boolean isTraceEnabled();

    /**
     * Is debug logging currently enabled?
     * 
     * @return true if debug is enabled
     */
    boolean isDebugEnabled();

    /**
     * Is info logging currently enabled?
     *
     * @return true if info is enabled
     */
    boolean isInfoEnabled();

    /**
     * Is warn logging currently enabled?
     *
     * @return true if warn is enabled
     */
    boolean isWarnEnabled();

    /**
     * Is error logging currently enabled?
     *
     * @return true if error is enabled
     */
    boolean isErrorEnabled();

}