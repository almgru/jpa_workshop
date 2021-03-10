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
@DirtiesContext
public class AuthorDAORepositoryTest {

    @Autowired
    private AuthorDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should return persisted author when not present in DAO")
    void create_should_returnPersistedAuthor_when_NotPresent() {
        Author expected = new Author(null, "Test", "Testsson", null);
        Author actual = dao.create(expected);

        assertNotNull(actual.getAuthorId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when author already present")
    void create_should_throwIllegalArgumentException_when_present() {
        Author author = new Author(null, "Test II", "Testsson", null);
        em.persist(author);
        em.flush();

        assertNotNull(author.getAuthorId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(author));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("findById should return expected author when present in DAO")
    void findById_should_returnExpectedAuthor_when_present() {
        Author expected = new Author(null, "Test III", "Testsson", null);
        em.persist(expected);
        em.flush();

        assertNotNull(expected.getAuthorId());

        Author actual = dao.findById(expected.getAuthorId());

        assertNotNull(actual.getAuthorId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }
}
