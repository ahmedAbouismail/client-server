package htw.berlin.clientserver.clientserver.dto;

import java.util.ArrayList;
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
