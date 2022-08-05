package htw.berlin.clientserver.clientserver.dto;

import htw.berlin.client.api.chart.DataSummary;
import htw.berlin.client.api.chart.ServiceAddresses;

import java.util.ArrayList;
import java.util.List;

public class ChartAggregate {

    private final ChartDto chartSummary;
    private final List<DataSummary> dataSummaryList;
    private final ServiceAddresses serviceAddresses;

    public ChartAggregate() {
        chartSummary = null;
        dataSummaryList = new ArrayList<>();
        serviceAddresses = null;
    }

    public ChartAggregate(ChartDto chartSummary, List<DataSummary> dataSummaryList, ServiceAddresses serviceAddresses) {
        this.chartSummary = chartSummary;
        this.dataSummaryList = dataSummaryList;
        this.serviceAddresses = serviceAddresses;
    }

    public ChartDto getChartSummary() {
        return chartSummary;
    }

    public List<DataSummary> getDataSummaryList() {
        return dataSummaryList;
    }

    public ServiceAddresses getServiceAddresses() {
        return serviceAddresses;
    }
}
