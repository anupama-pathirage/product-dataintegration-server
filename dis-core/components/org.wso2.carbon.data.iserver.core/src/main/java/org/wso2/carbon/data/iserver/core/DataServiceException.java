/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.data.iserver.core;

/**
 * This class represents exceptions that occur in data services.
 */
public class DataServiceException extends Exception {

    /**
     * The type of data service fault, this can be used to identify what kind
     * of an error condition occurred.
     */
    private String code;

    /**
     * The detailed explanation of the data service fault.
     */
    private String dsFaultMessage;

    public DataServiceException(Exception nestedException, String code, String dsFaultMessage) {
        super(nestedException);
        this.code = code;
        this.dsFaultMessage = dsFaultMessage;
        if (this.code == null) {
            this.code = extractFaultCode(nestedException);
        }
    }

    public DataServiceException(Exception nestedException) {
        this(nestedException, null, null);
    }

    public DataServiceException(Exception nestedException, String dsFaultMessage) {
        this(nestedException, null, dsFaultMessage);
    }

    public DataServiceException(String code, String dsFaultMessage) {
        this(null, code, dsFaultMessage);
    }

    public DataServiceException(String dsFaultMessage) {
        this(null, null, dsFaultMessage);
    }

    public String getCode() {
        return code;
    }

    public String getDsFaultMessage() {
        return dsFaultMessage;
    }

    public static String extractFaultCode(Throwable throwable) {
        if (throwable instanceof DataServiceException) {
            return ((DataServiceException) throwable).getCode();
        } else if (throwable != null) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                return extractFaultCode(cause);
            } else {
                return Constants.FaultCodes.UNKNOWN_ERROR;
            }
        } else {
            return Constants.FaultCodes.UNKNOWN_ERROR;
        }
    }
}
