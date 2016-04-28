package org.wso2.carbon.data.iserver.datasources.csv;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.gateway.core.outbound.OutboundEPProvider;
import org.wso2.carbon.gateway.core.outbound.OutboundEndpoint;

/**
 * CSV Data Source Provider
 *
 * This is responsible for providing a csv datasource instance to engine
 *
 */
@Component(
        name = "org.wso2.carbon.data.iserver.datasources.csv.CSVDataSourceProvider",
        immediate = true,
        service = OutboundEPProvider.class
)
public class CSVDataSourceProvider implements OutboundEPProvider {
    @Activate
    protected void start(BundleContext bundleContext) {
    }

    @Override
    public String getProtocol() {
        return "DATASOURCE_CSV";
    }

    @Override
    public OutboundEndpoint getEndpoint() {
        return new CSVDataSource();
    }
}
