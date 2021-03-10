package se.lexicon.almgru.jpa_workshop.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
public class DetailsDAORepositoryTest {
    @Autowired
    private DetailsDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void teardown() {

    }

    @Test
    @DisplayName("create should return persisted detail when detail not present in DAO")
    void create_should_returnPersistedDetail_when_detailNotPresent() {
        Details expected = new Details(null, "test@test.com", "test", LocalDate.now());
        Details actual = dao.create(expected);

        assertNotNull(actual.getDetailsId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }
}
