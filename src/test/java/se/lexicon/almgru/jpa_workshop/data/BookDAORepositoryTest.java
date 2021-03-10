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
import se.lexicon.almgru.jpa_workshop.entity.Book;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
public class BookDAORepositoryTest {

    @Autowired
    private BookDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should return persisted book when not present in DAO")
    void create_should_returnPersistedBook_when_BookNotPresent() {
        Book expected = new Book(null, "isbn", "title", 90);
        Book actual = dao.create(expected);

        assertNotNull(actual.getBookId());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getMaxLoanDays(), actual.getMaxLoanDays());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when book already present")
    void create_should_throwIllegalArgumentException_when_bookPresent() {
        Book book = new Book(null, "isbn3", "title3", 90);
        em.persist(book);
        em.flush();

        assertNotNull(book.getBookId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(book));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("findById should return expected book when present in DAO")
    void findById_should_returnExpectedBook_when_present() {
        Book expected = new Book(null, "isbn2", "title2", 90);
        em.persist(expected);
        em.flush();

        assertNotNull(expected.getBookId());

        Book actual = dao.findById(expected.getBookId());

        assertNotNull(actual.getBookId());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getMaxLoanDays(), actual.getMaxLoanDays());
    }
}
