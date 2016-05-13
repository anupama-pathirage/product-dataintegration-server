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
 * Constants for Data Integration Server core
 */
public class Constants {

    /**
     * JDBC Databases
     */
    public static final class Databases {

        public static final String MYSQL = "MYSQL";
        public static final String DERBY = "DERBY";
        public static final String MSSQL = "MSSQL";
        public static final String ORACLE = "ORACLE";
        public static final String DB2 = "DB2";
        public static final String HSQLDB = "HSQLDB";
        public static final String POSTGRESQL = "POSTGRESQL";
        public static final String SYBASE = "SYBASE";
        public static final String H2 = "H2";
        public static final String INFORMIX = "INFORMIX";
    }

    /**
     * JDBC Pool Properties
     */
    public static final class JDBCPoolProperties {
        public static final String DEFAULT_AUTOCOMMIT = "defaultAutoCommit";
        public static final String DEFAULT_READONLY = "defaultReadOnly";
        public static final String DEFAULT_TX_ISOLATION = "defaultTransactionIsolation";
        public static final String DEFAULT_CATALOG = "defaultCatalog";
        public static final String DRIVER_CLASSNAME = "driverClassName";
        public static final String URL = "url";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String MAX_ACTIVE = "maxActive";
        public static final String MAX_IDLE = "maxIdle";
        public static final String MIN_IDLE = "minIdle";
        public static final String INITIAL_SIZE = "initialSize";
        public static final String MAX_WAIT = "maxWait";
        public static final String TEST_ON_BORROW = "testOnBorrow";
        public static final String TEST_ON_RETURN = "testOnReturn";
        public static final String TEST_WHILE_IDLE = "testWhileIdle";
        public static final String VALIDATION_QUERY = "validationQuery";
        public static final String VALIDATOR_CLASSNAME = "validatorClassName";
        public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "timeBetweenEvictionRunsMillis";
        public static final String NUM_TESTS_PER_EVICTION_RUN = "numTestsPerEvictionRun";
        public static final String MIN_EVICTABLE_IDLE_TIME_MILLIS = "minEvictableIdleTimeMillis";
        public static final String REMOVE_ABANDONED = "removeAbandoned";
        public static final String REMOVE_ABANDONED_TIMEOUT = "removeAbandonedTimeout";
        public static final String LOG_ABANDONED = "logAbandoned";
        public static final String CONNECTION_PROPERTIES = "connectionProperties";
        public static final String INIT_SQL = "initSQL";
        public static final String JDBC_INTERCEPTORS = "jdbcInterceptors";
        public static final String VALIDATION_INTERVAL = "validationInterval";
        public static final String JMX_ENABLED = "jmxEnabled";
        public static final String FAIR_QUEUE = "fairQueue";
        public static final String ABANDON_WHEN_PERCENTAGE_FULL = "abandonWhenPercentageFull";
        public static final String MAX_AGE = "maxAge";
        public static final String USE_EQUALS = "useEquals";
        public static final String SUSPECT_TIMEOUT = "suspectTimeout";
        public static final String VALIDATION_QUERY_TIMEOUT = "validationQueryTimeout";
        public static final String ALTERNATE_USERNAME_ALLOWED = "alternateUsernameAllowed";
        public static final String DATASOURCE_CLASSNAME = "dataSourceClassName";
        public static final String DATASOURCE_PROPS = "dataSourceProps";
        public static final String FORCE_STORED_PROC = "forceStoredProc";
        public static final String FORCE_JDBC_BATCH_REQUESTS = "forceJDBCBatchRequests";
        public static final String QUERY_TIMEOUT = "queryTimeout";
        public static final String AUTO_COMMIT = "autoCommit";
        public static final String FETCH_DIRECTION = "fetchDirection";
        public static final String FETCH_SIZE = "fetchSize";
        public static final String MAX_FIELD_SIZE = "maxFieldSize";
        public static final String MAX_ROWS = "maxRows";
        public static final String DYNAMIC_USER_AUTH_CLASS = "dynamicUserAuthClass";
        public static final String DYNAMIC_USER_AUTH_MAPPING = "dynamicUserAuthMapping";
        public static final String USERNAME_WILDCARD = "*";
    }

    /**
     * JDBC Custom DIS Properties
     */
    public static final class JDBCCustomProperties {
        public static final String DATABASE_TYPE = "dbtype";
        public static final String DATABASE = "db";
        public static final String HOST = "host";
        public static final String PORT = "port";
        public static final String DRIVER = "driver";
        public static final String SERVER = "server";
        public static final String PATH = "path";
    }

    /**
     * JDBC URL Parameters
     */
    public static final class JDBCURLParameters {
        public static final String HOST = "[HOST]";
        public static final String PORT = "[PORT]";
        public static final String DB = "[DB]";
        public static final String SERVER = "[SERVER]";
        public static final String PATH = "[PATH]";
        public static final String DRIVER = "[DRIVER]";
        public static final String USER = "[USER]";
        public static final String PASSWORD = "[PASSWORD]";
    }

    /**
     * Codes to be used as fault codes.
     */
    public static final class FaultCodes {
        public static final String DATABASE_ERROR = "DATABASE_ERROR";
        public static final String CONNECTION_UNAVAILABLE_ERROR = "CONNECTION_UNAVAILABLE_ERROR";
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String INCOMPATIBLE_PARAMETERS_ERROR = "INCOMPATIBLE_PARAMETERS_ERROR";
        public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    }
}

