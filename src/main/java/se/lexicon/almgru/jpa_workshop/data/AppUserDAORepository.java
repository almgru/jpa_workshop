package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.entity.AppUser;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class AppUserDAORepository implements AppUserDAO {
    private final EntityManager entityManager;

    @Autowired
    public AppUserDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findById(int userId) {
        return entityManager.find(AppUser.class, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUser> findAll() {
        return entityManager
                .createQuery("SELECT appUser FROM AppUser appUser", AppUser.class)
                .getResultList();
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {
        entityManager.persist(user);

        if (entityManager.contains(user)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public AppUser update(AppUser user) {
        if (!entityManager.contains(user)) {
            throw new IllegalArgumentException("User doesn't exist, use 'create' instead.");
        }

        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(int userId) {
        AppUser toRemove = entityManager.find(AppUser.class, userId);

        if (toRemove == null) {
            throw new IllegalArgumentException("User does not exist.");
        }

        entityManager.remove(toRemove);
    }
}
