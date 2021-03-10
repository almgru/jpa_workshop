package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.entity.Details;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class DetailsDAORepository implements DetailsDAO {
    private final EntityManager entityManager;

    @Autowired
    public DetailsDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Details findById(Integer userDetailId) {
        return entityManager.find(Details.class, userDetailId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Details> findAll() {
        return entityManager
                .createQuery("SELECT detail FROM Details detail", Details.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Details create(Details userDetail) {
        entityManager.persist(userDetail);

        if (entityManager.contains(userDetail)) {
            return userDetail;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Details update(Details userDetail) {
        if (entityManager.find(Details.class, userDetail.getDetailsId()) == null) {
            throw new IllegalArgumentException("Detail doesn't exist, use 'create' instead.");
        }

        return entityManager.merge(userDetail);
    }

    @Override
    @Transactional
    public void delete(Integer userDetailId) {
        Details toRemove = findById(userDetailId);

        if (toRemove == null) {
            throw new IllegalArgumentException("Detail does not exist.");
        }

        entityManager.remove(toRemove);
    }
}
