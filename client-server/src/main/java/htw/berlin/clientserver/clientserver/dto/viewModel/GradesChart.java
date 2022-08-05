package htw.berlin.clientserver.clientserver.dto.viewModel;

import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.ChartType;
import htw.berlin.client.api.chart.DataSummary;
import htw.berlin.client.api.chart.Transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradesChart extends Chart{
    public GradesChart(String chartId, ChartType chartType, ChartLabel chartLabel, List<DataSummary> dataSummaryList) {
        super(chartId, chartType, chartLabel, dataSummaryList);
    }

//    private final ChartType chartType;
//    private final ChartLabel chartLabel;
//    private final List<DataSummary> dataSummaryList;
//    private List<List<Object>> data;


//    public GradesChart(ChartType chartType, ChartLabel chartLabel, List<DataSummary> dataSummaryList) {
//        this.chartType = chartType;
//        this.chartLabel = chartLabel;
//        this.dataSummaryList = dataSummaryList;

//        generateData(this.dataSummaryList);
//    }

    @Override
    protected void generateData(List<DataSummary> dataSummaryList){
        data = new ArrayList<>();
        data.add(List.of("Semester", "Grade"));
        if (dataSummaryList != null){
            List<Transcript> transcripts = dataSummaryList.stream().map(dataSummary -> dataSummary.getTranscript()).collect(Collectors.toList());
            transcripts.forEach(transcript -> {
                data.add(List.of(transcript.getSemester(), transcript.getAverage()));
            });
        }
    }

//    public ChartType getChartType() {
//        return chartType;
//    }
//
//    public ChartLabel getChartLabel() {
//        return chartLabel;
//    }
//
//    public List<DataSummary> getDataSummaryList() {
//        return dataSummaryList;
//    }
//
//    public List<List<Object>> getData() {
//        return data;
//    }
//
//    public void setData(List<List<Object>> data) {
//        this.data = data;
//    }
}
