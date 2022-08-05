package htw.berlin.client.api.chart;

import java.util.Collection;

public class Transcript {
    private String semester;

    private int semesterYear;
    private Collection<ModuleGrade> grades;
    private long modulesCount;
    private int creditsTotal;
    private double average;


    public Transcript() {
        semester = null;
        semesterYear = 0;
        grades = null;
        modulesCount = 0;
        creditsTotal = 0;
        average = 0.0;
    }

    public Transcript(String semester, int semesterYear, Collection<ModuleGrade> grades, long modulesCount, int creditsTotal, double average) {
        this.semester = semester;
        this.semesterYear = semesterYear;
        this.grades = grades;
        this.modulesCount = modulesCount;
        this.creditsTotal = creditsTotal;
        this.average = average;
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

    public Collection<ModuleGrade> getGrades() {
        return grades;
    }

    public void setGrades(Collection<ModuleGrade> grades) {
        this.grades = grades;
    }

    public long getModulesCount() {
        return modulesCount;
    }

    public void setModulesCount(long modulesCount) {
        this.modulesCount = modulesCount;
    }

    public int getCreditsTotal() {
        return creditsTotal;
    }

    public void setCreditsTotal(int creditsTotal) {
        this.creditsTotal = creditsTotal;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
