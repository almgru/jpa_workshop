package se.lexicon.almgru.jpa_workshop.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.entity.BookLoan;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookLoanDAORepositoryTest {

    @Autowired
    private BookLoanDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should persist loan when not already present in DAO")
    void create_should_persistLoan_when_notPresent() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should NOT persist unpersisted borrower")
    void create_should_notPersistBorrower() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should NOT persist unpersisted book")
    void create_should_notPersistBook() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when loan already present")
    void create_should_throwIllegalArgumentException_when_loanPresent() {
        BookLoan loan = new BookLoan(null, LocalDate.now(), LocalDate.now().plusDays(90), false,
                null, null);
        em.persist(loan);
        em.flush();

        assertNotNull(loan.getLoanId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(loan));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }
}
