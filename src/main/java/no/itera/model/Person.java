package no.itera.model;


public class Person {

    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(int id){
        this.id = id;
        this.name = "default";
        this.age = 0;
    }

    public Person() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public String getInfo() {
        return String.format("ID: %d,Name: %s, Age %d.", this.id, this.name, this.age);
    }
}
