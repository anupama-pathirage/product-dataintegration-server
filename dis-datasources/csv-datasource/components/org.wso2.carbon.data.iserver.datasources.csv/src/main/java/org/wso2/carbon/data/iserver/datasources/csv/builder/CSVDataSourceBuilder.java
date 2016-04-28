package org.wso2.carbon.data.iserver.datasources.csv.builder;

import org.wso2.carbon.data.iserver.datasources.csv.CSVDataSource;

/**
 * CSV DataSource Builder
 */
public class CSVDataSourceBuilder {

    public static CSVDataSource csvDataSource() {
        return  new CSVDataSource();
    }
}
