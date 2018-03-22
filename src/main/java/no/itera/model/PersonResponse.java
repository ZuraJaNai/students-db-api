package no.itera.model;

import java.util.List;

public class PersonResponse {
    private List persons;
    private int currentPage;
    private int totalPages;
    private int count;


    public PersonResponse(List persons, int currentPage, int totalPages, int count) {
        this.persons = persons;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.count = count;
    }

    public List getPersons() {
        return persons;
    }

    public void setPersons(List persons) {
        this.persons = persons;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
