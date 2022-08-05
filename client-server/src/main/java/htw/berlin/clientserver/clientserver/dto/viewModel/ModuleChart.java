package htw.berlin.clientserver.clientserver.dto.viewModel;

import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.ChartType;
import htw.berlin.client.api.chart.DataSummary;
import htw.berlin.client.api.chart.Transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleChart extends Chart{

    public ModuleChart(String chartId, ChartType chartType, ChartLabel chartLabel, List<DataSummary> dataSummaryList) {
        super(chartId, chartType, chartLabel, dataSummaryList);
    }

    @Override
    protected void generateData(List<DataSummary> dataSummaryList) {
        data = new ArrayList<>();
        data.add(List.of("Semester", "Module"));
        if (dataSummaryList != null){
            if (dataSummaryList != null){
                List<Transcript> transcripts = dataSummaryList.stream().map(dataSummary -> dataSummary.getTranscript()).collect(Collectors.toList());
                transcripts.forEach(transcript -> {
                    data.add(List.of(transcript.getSemester(), transcript.getModulesCount()));
                });
            }
        }
    }
}
