package htw.berlin.client.api.chart;

import java.util.List;



public class GetChartAggregate {
    private final List<ChartAggregate> chartAggregates;

    public GetChartAggregate() {
        chartAggregates = null;
    }

    public GetChartAggregate(List<ChartAggregate> chartAggregates) {
        this.chartAggregates = chartAggregates;
    }

    public List<ChartAggregate> getChartAggregates() {
        return chartAggregates;
    }
}
