package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.almgru.jpa_workshop.entity.Details;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class DetailsDAORepository extends GenericEntityDAORepository<Details, Integer> {

    @Autowired
    public DetailsDAORepository(EntityManager entityManager) {
        super(Details.class, entityManager);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Details> findAll() {
        return entityManager
                .createQuery("SELECT detail FROM Details detail", Details.class)
                .getResultList();
    }
}
