/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meteor.common.log;

import java.io.Serializable;

public class Slf4jLogger extends AbstractLog implements Logger, Serializable {

    private static final long serialVersionUID = 1L;


    private final org.slf4j.Logger logger;


    public Slf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void trace(String format, Object... arguments) {
        logger.trace(appendMessage(format, arguments));

    }

    @Override
    public void trace(Throwable e) {
        logger.trace(e.getMessage(), e);
    }

    @Override
    public void trace(Throwable e, String format, Object... arguments) {
        logger.trace(appendMessage(format,arguments), e);
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(appendMessage(format, arguments));
    }

    @Override
    public void debug(Throwable e) {
        logger.debug(appendMessage(e.getMessage()), e);
    }

    @Override
    public void debug(Throwable e, String format, Object... arguments) {
        logger.debug(appendMessage(format,arguments), e);
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(appendMessage(format, arguments));
    }

    @Override
    public void info(Throwable e) {
        logger.info(appendMessage(e.getMessage()), e);
    }

    @Override
    public void info(Throwable e, String format, Object... arguments) {
        logger.info(appendMessage(format,arguments), e);
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(appendMessage(format, arguments));
    }

    @Override
    public void warn(Throwable e) {
        logger.warn(appendMessage(e.getMessage()), e);
    }

    @Override
    public void warn(Throwable e, String format, Object... arguments) {
        logger.warn(appendMessage(format,arguments), e);
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.error(appendMessage(format, arguments));
    }

    @Override
    public void error(Throwable e) {
        logger.error(appendMessage(e.getMessage()), e);
    }

    @Override
    public void error(Throwable e, String format, Object... arguments) {
        logger.error(appendMessage(format,arguments), e);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

}
