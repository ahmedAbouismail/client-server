package htw.berlin.clientserver.clientserver.dto;

import htw.berlin.client.api.chart.ModuleGrade;
import htw.berlin.client.api.chart.Transcript;

import java.util.ArrayList;
import java.util.List;

public class DataDto {
    private String dataId;
    private String chartId;
    private String studentId;
    private String semester;
    private int semesterYear;
    private List<ModuleGrade> grades;

    public DataDto() {
        dataId = null;
        chartId = null;
        studentId = null;
        semester = null;
        semesterYear = 0;
        grades = new ArrayList<>();
    }

    public DataDto(String dataId, String chartId, String studentId, String semester, int semesterYear, List<ModuleGrade> grades) {
        this.dataId = dataId;
        this.chartId = chartId;
        this.studentId = studentId;
        this.semester = semester;
        this.semesterYear = semesterYear;
        this.grades = grades;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(int semesterYear) {
        this.semesterYear = semesterYear;
    }

    public List<ModuleGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<ModuleGrade> grades) {
        this.grades = grades;
    }
}
