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
import se.lexicon.almgru.jpa_workshop.entity.Book;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDAORepositoryTest {
    private static final int RANDOM_SEED = 3;
    private static TestDataGenerator testDataGen;

    @Autowired
    private BookDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeAll
    static void beforeAll() {
        testDataGen = new TestDataGenerator(RANDOM_SEED);
    }

    @Test
    @DisplayName("create should persist book when not already present in DAO")
    void create_should_persistBook_when_notPresent() {
        Book expected = testDataGen.book();

        Book actual = dao.create(expected);

        assertNotNull(actual.getBookId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getMaxLoanDays(), actual.getMaxLoanDays());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when book already present")
    void create_should_throwIllegalArgumentException_when_bookPresent() {
        Book book = new Book("isbn3", "title3", 90);
        em.persistAndFlush(book);

        assertNotNull(book.getBookId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(book));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }
}
