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
import se.lexicon.almgru.jpa_workshop.entity.AppUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
public class AppUserDAORepositoryTest {

    @Autowired
    private AppUserDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should return persisted student when student not present in DAO")
    void create_should_returnPersistedStudent_when_studentNotPresent() {
        AppUser expected = new AppUser(null, "test", "test", LocalDate.now(), null);
        AppUser actual = dao.create(expected);

        assertNotNull(actual.getAppUserId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getRegDate(), actual.getRegDate());
    }

    @Test
    @DisplayName("findById should return expected user when present in DAO")
    void findById_should_returnExpectedUser_when_present() {
        AppUser expected = new AppUser(null, "test2", "test2", LocalDate.now(), null);
        em.persist(expected);
        em.flush();

        assertNotNull(expected.getAppUserId());

        AppUser actual = dao.findById(expected.getAppUserId());

        assertEquals(expected.getAppUserId(), actual.getAppUserId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getRegDate(), actual.getRegDate());
    }
}
