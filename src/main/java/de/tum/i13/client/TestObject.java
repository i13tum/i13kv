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
}
