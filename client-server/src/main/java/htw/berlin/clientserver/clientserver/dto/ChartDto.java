package htw.berlin.clientserver.clientserver.dto;

import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.ChartType;

public class ChartDto {
    private String chartId;
    private String studentId;
    private ChartLabel chartLabel;
    private ChartType chartType;

    public ChartDto() {
        chartId = null;
        studentId = null;
        chartLabel = null;
        chartType = null;
    }

    public ChartDto(String chartId, String studentId, ChartLabel chartLabel, ChartType chartType) {
        this.chartId = chartId;
        this.studentId = studentId;
        this.chartLabel = chartLabel;
        this.chartType = chartType;
    }

    public String getChartId() {
        return chartId;
    }

    public void setChartId(String chartId) {
        this.chartId = chartId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ChartLabel getChartLabel() {
        return chartLabel;
    }

    public void setChartLabel(ChartLabel chartLabel) {
        this.chartLabel = chartLabel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }
}
