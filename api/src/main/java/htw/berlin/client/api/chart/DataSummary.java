package htw.berlin.client.api.chart;


public class DataSummary {

    private final String dataId;

    private final String chartId;
    private final String studentId;
    private final Transcript transcript;

    public DataSummary() {
        dataId = null;
        chartId = null;
        studentId = null;
        transcript = null;
    }
    public DataSummary(String dataId, String chartId, String studentId, Transcript transcript) {
        this.dataId = dataId;
        this.chartId = chartId;
        this.studentId = studentId;
        this.transcript = transcript;

    }

    public String getDataId() {
        return dataId;
    }

    public String getChartId() {
        return chartId;
    }

    public String getStudentId() {
        return studentId;
    }

    public Transcript getTranscript() {
        return transcript;
    }

}
