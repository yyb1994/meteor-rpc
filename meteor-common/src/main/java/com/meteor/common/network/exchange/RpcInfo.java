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
package com.meteor.common.network.exchange;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class RpcInfo implements Serializable {

    private static final long serialVersionUID = -4355285085441097045L;

    private String targetServiceUniqueName;

    private String methodName;
    private String serviceName;

    private transient Class<?>[] parameterTypes;

    private String parameterTypesDesc;
    private String[] compatibleParamSignatures;

    private Object[] arguments;

    /**
     * Passed to the remote server during RPC call
     */
    private Map<String, Object> attachments;

    public RpcInfo() {
    }


    @Override
    public String toString() {
        return "RpcInfo [serviceName=" + serviceName + ", methodName=" + methodName + ", parameterTypes="
                + Arrays.toString(parameterTypes) + ", arguments=" + Arrays.toString(arguments)
                + ", attachments=" + attachments + "]";
    }

}
