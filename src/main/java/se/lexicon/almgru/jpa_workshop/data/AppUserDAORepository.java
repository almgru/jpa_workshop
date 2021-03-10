package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.entity.AppUser;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class AppUserDAORepository extends GenericEntityDAORepository<AppUser, Integer> {

    @Autowired
    public AppUserDAORepository(EntityManager entityManager) {
        super(AppUser.class, entityManager);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUser> findAll() {
        return entityManager
                .createQuery("SELECT appUser FROM AppUser appUser", AppUser.class)
                .getResultList();
    }
}
