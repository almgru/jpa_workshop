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
import se.lexicon.almgru.jpa_workshop.entity.Details;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DetailsDAORepositoryTest {
    @Autowired
    private DetailsDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should persist user details when not already present in DAO")
    void create_should_persistDetails_when_notPresent() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when details already present")
    void create_should_throwIllegalArgumentException_when_detailsPresent() {
        Details details = new Details("test2@test.com", "test2", LocalDate.now());
        em.persist(details);
        em.flush();

        assertNotNull(details.getDetailsId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(details));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should throw exception when email not unique")
    void create_should_throwException_when_emailNotUnique() {
        fail("Test not implemented");
    }
}
