package se.lexicon.almgru.jpa_workshop.data;

import com.devskiller.jfairy.Fairy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.TestDataGenerator;
import se.lexicon.almgru.jpa_workshop.entity.Details;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DetailsDAORepositoryTest {
    private static final int RANDOM_SEED = 5;
    private static TestDataGenerator testDataGen;

    @Autowired
    private DetailsDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeAll
    static void beforeAll() {
        testDataGen = new TestDataGenerator(RANDOM_SEED);
    }

    @Test
    @DisplayName("create should persist user details when not already present in DAO")
    void create_should_persistDetails_when_notPresent() {
        Details expected = testDataGen.details();

        Details created = dao.create(expected);

        assertNotNull(created.getDetailsId());
        assertEquals(expected.getEmail(), created.getEmail());
        assertEquals(expected.getName(), created.getName());
        assertEquals(expected.getBirthDate(), created.getBirthDate());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when details already present")
    void create_should_throwIllegalArgumentException_when_detailsPresent() {
        Details details = testDataGen.details();
        em.persistAndFlush(details);

        assertNotNull(details.getDetailsId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(details));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should throw DataIntegrityViolationException when email not unique")
    void create_should_throwDataIntegrityViolationException_when_emailNotUnique() {
        String duplicateEmail = testDataGen.email();
        Details first = testDataGen.detailsWithEmail(duplicateEmail);
        Details second = testDataGen.detailsWithEmail(duplicateEmail);

        em.persistAndFlush(first);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> dao.create(second)
        );
    }
}
