package se.lexicon.almgru.jpa_workshop.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.TestDataGenerator;
import se.lexicon.almgru.jpa_workshop.entity.AppUser;
import se.lexicon.almgru.jpa_workshop.entity.Book;
import se.lexicon.almgru.jpa_workshop.entity.BookLoan;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookLoanDAORepositoryTest {
    private static final int RANDOM_SEED = 4;
    private static TestDataGenerator testDataGen;

    @Autowired
    private BookLoanDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeAll
    static void beforeAll() {
        testDataGen = new TestDataGenerator(RANDOM_SEED);
    }

    @Test
    @DisplayName("create should persist loan when not already present in DAO")
    void create_should_persistLoan_when_notPresent() {
        BookLoan expected = testDataGen.bookLoanWithBook();

        BookLoan actual = dao.create(expected);

        assertNotNull(expected.getLoanId());
        assertEquals(expected.getLoanDate(), actual.getLoanDate());
        assertEquals(expected.getDueDate(), actual.getDueDate());
    }

    @Test
    @DisplayName("create should NOT persist unpersisted borrower")
    void create_should_notPersistBorrower() {
        AppUser borrower = testDataGen.appUser();
        BookLoan bookLoan = testDataGen.bookLoanWithBookAndBorrower(borrower);

        dao.create(bookLoan);

        assertNull(borrower.getAppUserId());
    }

    @Test
    @DisplayName("create should NOT persist unpersisted book")
    void create_should_notPersistBook() {
        Book book = testDataGen.book();
        BookLoan bookLoan = testDataGen.bookLoanWithBook(book);

        dao.create(bookLoan);

        assertNull(book.getBookId());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when loan already present")
    void create_should_throwIllegalArgumentException_when_loanPresent() {
        Book book = testDataGen.book();
        BookLoan loan = testDataGen.bookLoanWithBook(book);
        em.persist(book);
        em.persistAndFlush(loan);

        assertNotNull(loan.getLoanId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(loan));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }
}
