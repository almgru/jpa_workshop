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
@DirtiesContext
public class BookLoanDAORepositoryTest {

    @Autowired
    private BookLoanDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should return persisted loan when not present in DAO")
    void create_should_returnPersistedLoan_when_notPresent() {
        BookLoan expected = new BookLoan(null, LocalDate.now(), LocalDate.now().plusDays(90), false, null, null);
        BookLoan actual = dao.create(expected);

        assertNotNull(actual.getLoanId());
        assertEquals(expected.getLoanDate(), actual.getLoanDate());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.isReturned(), actual.isReturned());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when loan already present")
    void create_should_throwIllegalArgumentException_when_loanPresent() {
        BookLoan loan = new BookLoan(null, LocalDate.now(), LocalDate.now().plusDays(90), false, null, null);
        em.persist(loan);
        em.flush();

        assertNotNull(loan.getLoanId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(loan));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("findById should return expected loan when present in DAO")
    void findById_should_returnExpectedLoan_when_present() {
        BookLoan expected = new BookLoan(null, LocalDate.now(), LocalDate.now().plusDays(90), false, null, null);
        em.persist(expected);
        em.flush();

        assertNotNull(expected.getLoanId());

        BookLoan actual = dao.findById(expected.getLoanId());

        assertNotNull(actual.getLoanId());
        assertEquals(expected.getLoanDate(), actual.getLoanDate());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.isReturned(), actual.isReturned());
    }
}
