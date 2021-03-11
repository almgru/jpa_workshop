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
    @DisplayName("create should return persisted detail when detail not present in DAO")
    void create_should_returnPersistedDetail_when_detailNotPresent() {
        Details expected = new Details("test@test.com", "test", LocalDate.now());
        Details actual = dao.create(expected);

        assertNotNull(actual.getDetailsId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
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
    @DisplayName("findById should return expected details when present in DAO")
    void findById_should_returnExpectedDetails_when_present() {
        Details expected = new Details("test3@test.com", "test3", LocalDate.now());
        em.persist(expected);
        em.flush();

        assertNotNull(expected.getDetailsId());

        Details actual = dao.findById(expected.getDetailsId());

        assertNotNull(actual.getDetailsId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }
}
