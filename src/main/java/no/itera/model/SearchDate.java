package no.itera.model;

import java.time.LocalDate;

public class SearchDate {
    private SearchDateType type;
    private LocalDate dateOne;
    private LocalDate dateTwo;

    private SearchDate(){}

    public SearchDate(LocalDate dateOne){
        this.type= SearchDateType.SINGLE;
        this.dateOne = dateOne;
    }

    public SearchDate(LocalDate dateOne,LocalDate dateTwo){
        this.type= SearchDateType.DOUBLE;
        this.dateOne = dateOne;
        this.dateTwo = dateTwo;
    }

    public SearchDateType getType() {
        return type;
    }

    public void setType(SearchDateType type) {
        this.type = type;
    }

    public LocalDate getDateOne() {
        return dateOne;
    }

    public void setDateOne(LocalDate dateOne) {
        this.dateOne = dateOne;
    }

    public LocalDate getDateTwo() {
        return dateTwo;
    }

    public void setDateTwo(LocalDate dateTwo) {
        this.dateTwo = dateTwo;
    }
}
