package kz.wojt.LibraryProjectBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kz.wojt.LibraryProjectBoot.validator.annotation.YearValidator;
import java.util.List;

@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name field shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name size should be between 2 and 30 symbols")
    @Column(name = "name")
    private String name;

    @YearValidator(min = 1900)
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
