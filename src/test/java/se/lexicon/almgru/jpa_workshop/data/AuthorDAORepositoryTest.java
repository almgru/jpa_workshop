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
import se.lexicon.almgru.jpa_workshop.entity.Author;
import se.lexicon.almgru.jpa_workshop.entity.Book;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDAORepositoryTest {
    private static final int RANDOM_SEED = 2;
    private static TestDataGenerator testDataGen;

    @Autowired
    private AuthorDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeAll
    static void beforeAll() {
        testDataGen = new TestDataGenerator(RANDOM_SEED);
    }

    @Test
    @DisplayName("create should persist author when not already present in DAO")
    void create_should_persistAuthor_when_notPresent() {
        Author expected = testDataGen.author();

        Author actual = dao.create(expected);

        assertNotNull(actual.getAuthorId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when author already present")
    void create_should_throwIllegalArgumentException_when_present() {
        Author author = testDataGen.author();
        em.persistAndFlush(author);

        assertNotNull(author.getAuthorId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(author));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should persist unpersisted books written by the author")
    void create_should_persistUnpersistedBooks() {
        List<Book> books = testDataGen.books(3);
        Author author = testDataGen.author();
        author.setWrittenBooks(new HashSet<>(books));

        dao.create(author);

        books.forEach(book -> assertNotNull(book.getBookId()));
    }
}
