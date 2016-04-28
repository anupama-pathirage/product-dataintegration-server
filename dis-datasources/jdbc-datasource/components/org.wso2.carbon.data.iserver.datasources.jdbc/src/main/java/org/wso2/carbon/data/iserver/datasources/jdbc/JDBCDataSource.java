/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.data.iserver.datasources.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.gateway.core.config.ParameterHolder;
import org.wso2.carbon.gateway.core.outbound.AbstractOutboundEndpoint;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;

/**
 * JDBC Data Source
 */

public class JDBCDataSource extends AbstractOutboundEndpoint {

    private static final Logger log = LoggerFactory.getLogger(JDBCDataSource.class);

    public JDBCDataSource() {

    }


    @Override
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {
        log.info("JDBCDataSource:Recv");
        carbonCallback.done(carbonMessage);
        return false;
    }

    @Override
    public void setParameters(ParameterHolder parameterHolder) {

    }
}
