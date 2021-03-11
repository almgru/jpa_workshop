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
import se.lexicon.almgru.jpa_workshop.entity.Author;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDAORepositoryTest {

    @Autowired
    private AuthorDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should persist author when not already present in DAO")
    void create_should_persistAuthor_when_notPresent() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when author already present")
    void create_should_throwIllegalArgumentException_when_present() {
        Author author = new Author("Test II", "Testsson");
        em.persist(author);
        em.flush();

        assertNotNull(author.getAuthorId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(author));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should persist unpersisted books written by the author")
    void create_should_persistUnpersistedBooks() {
        fail("Test not implemented");
    }
}
