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

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.data.iserver.core.Constants;
import org.wso2.carbon.data.iserver.core.DataServiceException;
import org.wso2.carbon.gateway.core.config.Parameter;
import org.wso2.carbon.gateway.core.config.ParameterHolder;
import org.wso2.carbon.gateway.core.outbound.AbstractOutboundEndpoint;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC Data Source
 */

public class JDBCDataSource extends AbstractOutboundEndpoint {

    private static final Logger log = LoggerFactory.getLogger(JDBCDataSource.class);

    private static Map<String, String> jdbcURLMap = new HashMap<String, String>();
    private static Map<String, String> jdbcDriverMap = new HashMap<String, String>();

    static {
        jdbcURLMap.put("MYSQL", "jdbc:mysql://[HOST]:[PORT]/[DB]");
        jdbcURLMap.put("DERBY", "jdbc:derby:[PATH]");
        jdbcURLMap.put("MSSQL", "jdbc:microsoft:sqlserver://[HOST]:[PORT];DatabaseName=[DB]");
        jdbcURLMap.put("ORACLE", "jdbc:oracle:[DRIVER]:[USER]/[PASSWORD]@[DB]");
        jdbcURLMap.put("DB2", "jdbc:db2:[DB]");
        jdbcURLMap.put("HSQLDB", "jdbc:hsqldb:[PATH]");
        jdbcURLMap.put("POSTGRESQL", "jdbc:postgresql://[HOST]:[PORT]/[DB]");
        jdbcURLMap.put("SYBASE", "jdbc:sybase:Tds:[HOST]:[PORT]/[DB]");
        jdbcURLMap.put("H2", "jdbc:h2:tcp:[HOST]:[PORT]/[DB]");
        jdbcURLMap.put("INFORMIX", "jdbc:informix-sqli://[HOST]:[PORT]/[DB]:INFORMIXSERVER=[SERVER]");

        jdbcDriverMap.put("MYSQL", "com.mysql.jdbc.Driver");
        jdbcDriverMap.put("DERBY", "org.apache.derby.jdbc.EmbeddedDriver");
        jdbcDriverMap.put("MSSQL", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        jdbcDriverMap.put("ORACLE", "oracle.jdbc.driver.OracleDriver");
        jdbcDriverMap.put("DB2", "com.ibm.db2.jcc.DB2Driver");
        jdbcDriverMap.put("HSQLDB", "org.hsqldb.jdbcDriver");
        jdbcDriverMap.put("POSTGRESQL", "org.postgresql.Driver");
        jdbcDriverMap.put("SYBASE", "com.sybase.jdbc3.jdbc.SybDriver");
        jdbcDriverMap.put("H2", "org.h2.Driver");
        jdbcDriverMap.put("INFORMIX", "com.informix.jdbc.IfxDriver");
    }

    PoolProperties properties;
    DataSource dataSource;

    public JDBCDataSource() {

    }

    @Override public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {
        log.info("JDBCDataSource:Recv");
        executeQuery();
        carbonCallback.done(carbonMessage);
        return false;
    }

    @Override public void setParameters(ParameterHolder parameterHolder) {
        setJDBCProperties(parameterHolder);
        initJDBCDataSource();
    }

    /**
     * JDBC Pool properties are initialized
     *
     * @param parameterHolder contains the parameters given in the config
     */
    private void setJDBCProperties(ParameterHolder parameterHolder) {
        try {
            properties = new PoolProperties();

            //Mandatory Properties
            String databaseType = null;
            Parameter paramDBType = parameterHolder.getParameter(Constants.JDBCCustomProperties.DATABASE_TYPE);
            if (paramDBType != null) {
                databaseType = paramDBType.getValue();
            }

            String jdbcURL = null;
            Parameter paramUrl = parameterHolder.getParameter(Constants.JDBCPoolProperties.URL);
            if (paramUrl != null) {
                jdbcURL = paramUrl.getValue();
            } else {
                jdbcURL = generateJDBCUrl(databaseType, parameterHolder);
            }
            if (jdbcURL != null && !jdbcURL.equals("")) {
                properties.setUrl(jdbcURL);
            }

            String jdbcDriverClass = null;
            Parameter paramDriverClass = parameterHolder.getParameter(Constants.JDBCPoolProperties.DRIVER_CLASSNAME);
            if (paramDriverClass != null) {
                jdbcDriverClass = paramDriverClass.getValue();
            } else {
                jdbcDriverClass = jdbcDriverMap.get(databaseType);
            }
            if (jdbcDriverClass != null && !jdbcDriverClass.equals("")) {
                properties.setDriverClassName(jdbcDriverClass);
            }

            Parameter param = parameterHolder.getParameter(Constants.JDBCPoolProperties.USERNAME);
            if (param != null) {
                String paramValue = param.getValue();
                if (paramValue != null) {
                    properties.setUsername(param.getValue());
                }
            }

            param = parameterHolder.getParameter(Constants.JDBCPoolProperties.PASSWORD);
            if (param != null) {
                String paramValue = param.getValue();
                if (paramValue != null) {
                    properties.setPassword(param.getValue());
                }
            }
        } catch (DataServiceException e) {
            log.error("Error in Initializing Data Source : " + e.getDsFaultMessage());
        }
        // Set Optional Properties
    }

    /**
     * Initialize the JDBC data source with the given JDBC properties in the config
     */
    private void initJDBCDataSource() {
        dataSource = new DataSource();
        dataSource.setPoolProperties(this.properties);
    }

    /**
     * Execute Database Query
     */
    private void executeQuery() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from Person");
            while (rs.next()) {
                log.info("DONE:" + rs.getString("PersonID"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            log.info("ERROR:", e.getErrorCode());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.info("ERROR:", e.getErrorCode());
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    log.info("ERROR:", e.getErrorCode());
                }
            }
        }
    }

    /**
     * Generates the JDBC
     *
     * @param databaseType Database type given in the config
     * @throws DataServiceException
     */
    private String generateJDBCUrl(String databaseType, ParameterHolder parameterHolder) throws DataServiceException {
        String url = jdbcURLMap.get(databaseType);
        switch (databaseType) {
        case Constants.Databases.MYSQL:
        case Constants.Databases.MSSQL:
        case Constants.Databases.POSTGRESQL:
        case Constants.Databases.SYBASE:
        case Constants.Databases.H2:
        case Constants.Databases.INFORMIX: {
            String host = null;
            String port = null;
            String db = null;

            Parameter param = parameterHolder.getParameter(Constants.JDBCCustomProperties.PORT);
            if (param != null) {
                port = param.getValue();
            }

            param = parameterHolder.getParameter(Constants.JDBCCustomProperties.HOST);
            if (param != null) {
                host = param.getValue();
            }

            param = parameterHolder.getParameter(Constants.JDBCCustomProperties.DATABASE);
            if (param != null) {
                db = param.getValue();
            }

            if (isAnyStringNullOrEmpty(host, port, db)) {
                throw new DataServiceException("Required Parameters are missing for URL:" + databaseType);
            }

            url = url.replace(Constants.JDBCURLParameters.HOST, host);
            url = url.replace(Constants.JDBCURLParameters.PORT, port);
            url = url.replace(Constants.JDBCURLParameters.DB, db);

            if (databaseType.equals(Constants.Databases.INFORMIX)) {
                param = parameterHolder.getParameter(Constants.JDBCCustomProperties.SERVER);
                if (param != null) {
                    String server = param.getValue();
                    url = url.replace(Constants.JDBCURLParameters.SERVER, server);
                } else {
                    throw new DataServiceException("Required Parameters are missing for URL:" + databaseType);
                }
            }
        }
        break;
        case Constants.Databases.DERBY:
        case Constants.Databases.HSQLDB: {
            Parameter param = parameterHolder.getParameter(Constants.JDBCCustomProperties.PATH);
            if (param != null) {
                String path = param.getValue();
                url = url.replace(Constants.JDBCURLParameters.PATH, path);
            } else {
                throw new DataServiceException("Required Parameters are missing for URL:" + databaseType);
            }
        }
        break;
        case Constants.Databases.ORACLE: {
            String driver = null;
            String db = null;
            String jdbcUser = null;
            String jdbcPassword = null;

            Parameter param = parameterHolder.getParameter(Constants.JDBCCustomProperties.DRIVER);
            if (param != null) {
                driver = param.getValue();
            }

            param = parameterHolder.getParameter(Constants.JDBCCustomProperties.DATABASE);
            if (param != null) {
                db = param.getValue();
            }

            param = parameterHolder.getParameter(Constants.JDBCPoolProperties.USERNAME);
            if (param != null) {
                jdbcUser = param.getValue();
            }

            param = parameterHolder.getParameter(Constants.JDBCPoolProperties.PASSWORD);
            if (param != null) {
                jdbcPassword = param.getValue();
            }

            if (isAnyStringNullOrEmpty(driver, db, jdbcUser, jdbcPassword)) {
                throw new DataServiceException("Required Parameters are missing for URL:" + databaseType);
            }
            url = url.replace(Constants.JDBCURLParameters.DRIVER, driver);
            url = url.replace(Constants.JDBCURLParameters.DB, db);
            url = url.replace(Constants.JDBCURLParameters.PASSWORD, jdbcUser);
            url = url.replace(Constants.JDBCURLParameters.USER, jdbcPassword);
        }
        break;
        case Constants.Databases.DB2: {
            Parameter param = parameterHolder.getParameter(Constants.JDBCCustomProperties.DATABASE);
            if (param != null) {
                String db = param.getValue();
                url = url.replace(Constants.JDBCURLParameters.DB, db);
            } else {
                throw new DataServiceException("Required Parameters are missing for URL:" + databaseType);
            }
        }
        break;
        default:
            throw new IllegalArgumentException("Invalid JDBC Datasource Type : " + databaseType);
        }
        return url;
    }

    /**
     * Check the given set of strings for null and empty
     *
     * @param strings Set of String to check for null and empty
     */
    public static boolean isAnyStringNullOrEmpty(String... strings) {
        for (String s : strings) {
            if (s == null || s.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
