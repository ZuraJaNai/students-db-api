package no.itera.model;

import javax.persistence.*;

@org.hibernate.annotations.GenericGenerator(
        name = "ID_GENERATOR",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "JPWH_SEQUENCE"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "1000"
                )
        }
)

@Entity
@Table(name = "PERSONS")
public class Person {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private int id;

    @Column(nullable = false,name = "LASTNAME")
    private String lastName;

    @Column(nullable = false,name = "FIRSTNAME")
    private String firstName;

    @Column(nullable = false,name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "EMAIL")
    private String email;

    @Column(nullable = false,name = "YEAR")
    private int yearOfStudy;

    @Column(name = "INTERNSHIP")
    private String internship;

    @Column(name = "PRACTICE")
    private String practice;

//    @Embedded // Not necessary...
//    @AttributeOverrides({
//            @AttributeOverride(name = "comment",
//                    column = @Column(name = "COMMENT"))
//    })
//    private Comment comment;
    private String comment;

    public Person(String lastName, String firstName, String patronymic,
                  String email, int yearOfStudy, String internship, String practice,
                  String comment){
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.internship = internship;
        this.practice = practice;
        this.comment = comment;
    }

    public Person(int id){
        this.id = id;
        this.lastName = "default";
        this.firstName = "default";
        this.patronymic = "default";
        this.email = "default@mail.com";
        this.yearOfStudy = 0;
        this.internship = "default";
        this.practice = "default";
        this.comment = null;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    @Transient
    public String personName() {
        return lastName + ' ' + firstName + ' ' + patronymic;
    }
//
//    public void setName(String name) {
//        StringTokenizer t = new StringTokenizer(name);
//        if(t.countTokens() == 3) {
//            this.lastName = t.nextToken();
//            this.firstName = t.nextToken();
//            this.patronymic = t.nextToken();
//        }
//        else {
//            //exception
//        }
//    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getInternship() {
        return internship;
    }

    public void setInternship(String internship) {
        this.internship = internship;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nName: %s\nEmail: %s\nYear of study: %d\n" +
                        "Internship: %s\nPractice: %s\n", this.id, this.personName(),
                this.email,this.yearOfStudy,this.internship,this.practice);
    }

}
