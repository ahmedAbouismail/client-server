package htw.berlin.clientserver.clientserver.dto.viewModel;

import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.ChartType;
import htw.berlin.client.api.chart.DataSummary;

import java.util.List;

public abstract class Chart {

    protected final String chartId;
    protected final ChartType chartType;
    protected final ChartLabel chartLabel;
    protected final List<DataSummary> dataSummaryList;
    protected List<List<Object>> data;

    public Chart(String chartId, ChartType chartType, ChartLabel chartLabel, List<DataSummary> dataSummaryList) {
        this.chartId = chartId;
        this.chartType = chartType;
        this.chartLabel = chartLabel;
        this.dataSummaryList = dataSummaryList;

        generateData(this.dataSummaryList);
    }

    abstract protected void generateData(List<DataSummary> dataSummaryList);

    public String getChartId() {
        return chartId;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public ChartLabel getChartLabel() {
        return chartLabel;
    }

    public List<DataSummary> getDataSummaryList() {
        return dataSummaryList;
    }

    public List<List<Object>> getData() {
        return data;
    }
}
