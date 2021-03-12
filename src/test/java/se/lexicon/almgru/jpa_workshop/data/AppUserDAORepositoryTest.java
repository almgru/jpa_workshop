package se.lexicon.almgru.jpa_workshop.data;

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
import se.lexicon.almgru.jpa_workshop.entity.AppUser;
import se.lexicon.almgru.jpa_workshop.entity.BookLoan;
import se.lexicon.almgru.jpa_workshop.entity.Details;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AppUserDAORepositoryTest {
    private static final int RANDOM_SEED = 1;
    private static TestDataGenerator testDataGen;

    @Autowired
    private AppUserDAORepository dao;

    @Autowired
    private TestEntityManager em;

    @BeforeAll
    static void beforeAll() {
        testDataGen = new TestDataGenerator(RANDOM_SEED);
    }

    @Test
    @DisplayName("create should persist user when not already present in DAO")
    void create_should_persistUser_when_notPresent() {
        AppUser expected = testDataGen.appUser();

        AppUser created = dao.create(expected);

        assertNotNull(created.getAppUserId());
        assertEquals(expected.getUsername(), created.getUsername());
        assertEquals(expected.getPassword(), created.getPassword());
        assertEquals(expected.getRegDate(), created.getRegDate());
    }

    @Test
    @DisplayName("create should throw IllegalArgumentException when user already present in DAO")
    void create_should_throwIllegalArgumentException_when_userPresent() {
        AppUser user = testDataGen.appUser();
        em.persistAndFlush(user);

        assertNotNull(user.getAppUserId());

        Exception e = assertThrows(RuntimeException.class, () -> dao.create(user));
        assertEquals(IllegalArgumentException.class, e.getCause().getClass());
    }

    @Test
    @DisplayName("create should persist unpersisted book loans")
    void create_should_persistUnpersistedBookLoans() {
        BookLoan bookLoan = testDataGen.bookLoanWithBook();
        AppUser user = testDataGen.appUser();
        user.addLoan(bookLoan);

        dao.create(user);

        assertNotNull(bookLoan.getLoanId());
    }

    @Test
    @DisplayName("create should persist unpersisted user details")
    void create_should_persistUnpersistedUserDetails() {
        Details details = testDataGen.details();
        AppUser user = testDataGen.appUser();
        user.setUserDetails(details);

        dao.create(user);

        assertNotNull(details.getDetailsId());
    }

    @Test
    @DisplayName("create should throw exception when username not unique")
    void create_should_throwException_when_usernameNotUnique() {
        String duplicateUsername = testDataGen.username();
        AppUser first = testDataGen.appUserWithUsername(duplicateUsername);
        AppUser second = testDataGen.appUserWithUsername(duplicateUsername);

        em.persistAndFlush(first);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> dao.create(second)
        );
    }
}
