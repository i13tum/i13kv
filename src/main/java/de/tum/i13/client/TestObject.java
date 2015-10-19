package de.tum.i13.client;

import java.util.DoubleSummaryStatistics;

/**
 * Created by chris on 19.10.15.
 */
public class TestObject {

    private Double grade;
    private Integer finalTestPoints;
    private Integer firstExcercisePoints;
    private Integer secondExcercisePoints;

    public TestObject(Double grade, Integer finalTestPoints, Integer firstExcercisePoints, Integer secondExcercisePoints) {
        this.grade = grade;
        this.finalTestPoints = finalTestPoints;
        this.firstExcercisePoints = firstExcercisePoints;
        this.secondExcercisePoints = secondExcercisePoints;
    }

    public Integer getSecondExcercisePoints() {
        return secondExcercisePoints;
    }

    public void setSecondExcercisePoints(Integer secondExcercisePoints) {
        this.secondExcercisePoints = secondExcercisePoints;
    }

    public Integer getFirstExcercisePoints() {
        return firstExcercisePoints;
    }

    public void setFirstExcercisePoints(Integer firstExcercisePoints) {
        this.firstExcercisePoints = firstExcercisePoints;
    }

    public Integer getFinalTestPoints() {
        return finalTestPoints;
    }

    public void setFinalTestPoints(Integer finalTestPoints) {
        this.finalTestPoints = finalTestPoints;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestObject that = (TestObject) o;

        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (finalTestPoints != null ? !finalTestPoints.equals(that.finalTestPoints) : that.finalTestPoints != null)
            return false;
        if (firstExcercisePoints != null ? !firstExcercisePoints.equals(that.firstExcercisePoints) : that.firstExcercisePoints != null)
            return false;
        return !(secondExcercisePoints != null ? !secondExcercisePoints.equals(that.secondExcercisePoints) : that.secondExcercisePoints != null);

    }

    @Override
    public int hashCode() {
        int result = grade != null ? grade.hashCode() : 0;
        result = 31 * result + (finalTestPoints != null ? finalTestPoints.hashCode() : 0);
        result = 31 * result + (firstExcercisePoints != null ? firstExcercisePoints.hashCode() : 0);
        result = 31 * result + (secondExcercisePoints != null ? secondExcercisePoints.hashCode() : 0);
        return result;
    }
}
