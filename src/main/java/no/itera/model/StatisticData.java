package no.itera.model;

import java.util.Map;

public class StatisticData {

    private Integer totalNumberOfPersons;

    private Map<String,Integer> numberOfPersonsByYear;

    private Integer numberOfPersonsWithInternshipNow;

    private Integer numberOfPersonsWithInternship;

    private Integer numberOfPersonsWithPracticeNow;

    private Integer numberOfPersonsWithPractice;

    private Integer numberOfPersonsWithJobNow;

    private Integer numberOfPersonsWithJob;

    public Integer getTotalNumberOfPersons() {
        return totalNumberOfPersons;
    }

    public void setTotalNumberOfPersons(Integer totalNumberOfPersons) {
        this.totalNumberOfPersons = totalNumberOfPersons;
    }

    public Map<String, Integer> getNumberOfPersonsByYear() {
        return numberOfPersonsByYear;
    }

    public void setNumberOfPersonsByYear(Map<String, Integer> numberOfPersonsByYear) {
        this.numberOfPersonsByYear = numberOfPersonsByYear;
    }

    public Integer getNumberOfPersonsWithInternshipNow() {
        return numberOfPersonsWithInternshipNow;
    }

    public void setNumberOfPersonsWithInternshipNow(Integer numberOfPersonsWithInternshipNow) {
        this.numberOfPersonsWithInternshipNow = numberOfPersonsWithInternshipNow;
    }

    public Integer getNumberOfPersonsWithInternship() {
        return numberOfPersonsWithInternship;
    }

    public void setNumberOfPersonsWithInternship(Integer numberOfPersonsWithInternship) {
        this.numberOfPersonsWithInternship = numberOfPersonsWithInternship;
    }

    public Integer getNumberOfPersonsWithPracticeNow() {
        return numberOfPersonsWithPracticeNow;
    }

    public void setNumberOfPersonsWithPracticeNow(Integer numberOfPersonsWithPracticeNow) {
        this.numberOfPersonsWithPracticeNow = numberOfPersonsWithPracticeNow;
    }

    public Integer getNumberOfPersonsWithPractice() {
        return numberOfPersonsWithPractice;
    }

    public void setNumberOfPersonsWithPractice(Integer numberOfPersonsWithPractice) {
        this.numberOfPersonsWithPractice = numberOfPersonsWithPractice;
    }

    public Integer getNumberOfPersonsWithJobNow() {
        return numberOfPersonsWithJobNow;
    }

    public void setNumberOfPersonsWithJobNow(Integer numberOfPersonsWithJobNow) {
        this.numberOfPersonsWithJobNow = numberOfPersonsWithJobNow;
    }

    public Integer getNumberOfPersonsWithJob() {
        return numberOfPersonsWithJob;
    }

    public void setNumberOfPersonsWithJob(Integer numberOfPersonsWithJob) {
        this.numberOfPersonsWithJob = numberOfPersonsWithJob;
    }
}
