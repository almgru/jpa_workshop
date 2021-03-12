package se.lexicon.almgru.jpa_workshop;

import com.devskiller.jfairy.Fairy;
import com.github.javafaker.Faker;
import se.lexicon.almgru.jpa_workshop.entity.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataGenerator {
    private final Fairy fairy;
    private final Faker faker;

    public TestDataGenerator(int seed) {
        this.fairy = Fairy.builder()
                .withRandomSeed(seed)
                .build();
        this.faker = new Faker(new Random(seed));
    }

    public AppUser appUser() {
        return new AppUser(
                fairy.person().getUsername(),
                fairy.person().getPassword(),
                fairy.dateProducer().randomDateInThePast(1).toLocalDate()
        );
    }

    public AppUser appUserWithUsername(String username) {
        AppUser appUser = this.appUser();
        appUser.setUsername(username);
        return appUser;
    }

    public Details details() {
        return new Details(
                fairy.person().getEmail(),
                fairy.person().getFullName(),
                fairy.person().getDateOfBirth()
        );
    }

    public Details detailsWithEmail(String email) {
        Details details = this.details();
        details.setEmail(email);
        return details;
    }

    public AppUser appUserWithDetails() {
        AppUser appUser = this.appUser();
        appUser.setUserDetails(this.details());
        return appUser;
    }

    public Book book() {
        return new Book(
                faker.code().isbn10(),
                faker.book().title(),
                faker.number().numberBetween(14, 90)
        );
    }

    public List<Book> books(int count) {
        return Stream
                .generate(this::book)
                .limit(count)
                .collect(Collectors.toList());
    }

    public BookLoan bookLoanWithBookAndBorrower(Book book, AppUser borrower) {
        return new BookLoan(borrower, book, fairy.dateProducer().randomDateInTheFuture(1).toLocalDate());
    }

    public BookLoan bookLoanWithBookAndBorrower(AppUser borrower) {
        return bookLoanWithBookAndBorrower(this.book(), borrower);
    }

    public BookLoan bookLoanWithBook() {
        return bookLoanWithBookAndBorrower(this.book(), null);
    }

    public BookLoan bookLoanWithBook(Book book) {
        return bookLoanWithBookAndBorrower(book, null);
    }

    public String email() {
        return fairy.person().getEmail();
    }

    public String username() {
        return fairy.person().getUsername();
    }

    public Author author() {
        return new Author(
                fairy.person().getFirstName(),
                fairy.person().getLastName()
        );
    }
}
