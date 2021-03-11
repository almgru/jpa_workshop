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
import se.lexicon.almgru.jpa_workshop.entity.AppUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AppUserDAORepositoryTest {

    @Autowired
    private AppUserDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("create should persist user when not already present in DAO")
    void create_should_persistUser_when_notPresent() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when user already present in DAO")
    void create_should_throwIllegalArgumentException_when_userPresent() {
        AppUser user = new AppUser("test3", "test3", LocalDate.now());
        em.persist(user);
        em.flush();

        assertNotNull(user.getAppUserId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(user));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should persist unpersisted book loans")
    void create_should_persistUnpersistedBookLoans() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should persist unpersisted user details")
    void create_should_persistUnpersistedUserDetails() {
        fail("Test not implemented");
    }

    @Test
    @DisplayName("create should throw exception when username not unique")
    void create_should_throwException_when_usernameNotUnique() {
        fail("Test not implemented");
    }
}
