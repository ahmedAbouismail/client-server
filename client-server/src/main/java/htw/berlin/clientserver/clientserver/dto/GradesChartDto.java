package htw.berlin.clientserver.clientserver.dto;

import java.util.List;

public class GradesChartDto {
    private ChartDto chartDto;
    private List<List<Object>> data;

    public GradesChartDto() {
        chartDto = null;
        data = null;
    }

    public GradesChartDto(ChartDto chartDto, List<List<Object>> data) {
        this.chartDto = chartDto;
        this.data = data;
    }

    public ChartDto getChartDto() {
        return chartDto;
    }

    public void setChartDto(ChartDto chartDto) {
        this.chartDto = chartDto;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }
}
