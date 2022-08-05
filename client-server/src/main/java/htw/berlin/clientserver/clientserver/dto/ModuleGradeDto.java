package htw.berlin.clientserver.clientserver.dto;

public class ModuleGradeDto {
    private double grade;
    private String module;

    public ModuleGradeDto() {
    }

    public ModuleGradeDto(double grade, String module) {
        this.grade = grade;
        this.module = module;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
