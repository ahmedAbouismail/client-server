package htw.berlin.clientserver.clientserver.dto;

import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.ChartType;
import htw.berlin.client.api.chart.ModuleGrade;

import java.util.ArrayList;
import java.util.List;


public class ChartAggregationDto {

    private  String studentId;
    private  ChartLabel chartLabel;
    private  ChartType chartType;
    private  String semester;
    private List<ModuleGrade> grades;
    private List<List<ModuleGradeDto>> chartList;

    public ChartAggregationDto() {
        studentId = null;
        chartLabel = null;
        chartType = null;
        semester = null;
        grades = new ArrayList<>();
        chartList = new ArrayList<>();
    }

    public ChartAggregationDto(String studentId, ChartLabel chartLabel, ChartType chartType, String semester, List<ModuleGrade> grades, List<List<ModuleGradeDto>> chartList) {
        this.studentId = studentId;
        this.chartLabel = chartLabel;
        this.chartType = chartType;
        this.semester = semester;
        this.grades = grades;
        this.chartList = chartList;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setChartLabel(ChartLabel chartLabel) {
        this.chartLabel = chartLabel;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setGrades(List<ModuleGrade> grades) {
        this.grades = grades;
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

    public String getSemester() {
        return semester;
    }

    public List<ModuleGrade> getGrades() {
        return grades;
    }

    public List<List<ModuleGradeDto>> getChartList() {
        return chartList;
    }

    public void setChartList(List<List<ModuleGradeDto>> chartList) {
        this.chartList = chartList;
    }
}
