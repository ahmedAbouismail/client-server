package htw.berlin.client.api.chart;

public class ChartAggregate {

    private final String chartId;
    private final String studentId;
    private final ChartLabel chartLabel;
    private final ChartType chartType;
    private final DataSummary dataSummary;

    private final ServiceAddresses serviceAddresses;


    public ChartAggregate() {
        chartId = null;
        studentId = null;
        chartLabel = null;
        chartType = null;
        dataSummary = null;
        serviceAddresses = null;

    }

    public ChartAggregate(String chartId, String studentId, ChartLabel chartLabel, ChartType chartType, DataSummary dataSummary, ServiceAddresses serviceAddresses) {
        this.chartId = chartId;
        this.studentId = studentId;
        this.chartLabel = chartLabel;
        this.chartType = chartType;
        this.dataSummary = dataSummary;
        this.serviceAddresses = serviceAddresses;
    }

    public String getChartId() {
        return chartId;
    }

    public String getStudentId() {
        return studentId;
    }

    public ChartLabel getChartLabel() {
        return chartLabel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public DataSummary getDataSummary() {
        return dataSummary;
    }

    public ServiceAddresses getServiceAddresses() {
        return serviceAddresses;
    }
}
