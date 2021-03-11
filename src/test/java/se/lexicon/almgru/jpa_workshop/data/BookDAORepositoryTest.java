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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDAORepositoryTest {

    @Autowired
    private BookDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should persist book when not already present in DAO")
    void create_should_persistBook_when_notPresent() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when book already present")
    void create_should_throwIllegalArgumentException_when_bookPresent() {
        Book book = new Book("isbn3", "title3", 90);
        em.persist(book);
        em.flush();

        assertNotNull(book.getBookId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(book));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }
}
