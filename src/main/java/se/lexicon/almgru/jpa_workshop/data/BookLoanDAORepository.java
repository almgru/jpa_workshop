package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.lexicon.almgru.jpa_workshop.entity.BookLoan;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BookLoanDAORepository implements BookLoanDAO {

    private final EntityManager entityManager;

    @Autowired
    public BookLoanDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BookLoan findById(Integer entityId) {
        return entityManager.find(BookLoan.class, entityId);
    }

    @Override
    public Collection<BookLoan> findAll() {
        return entityManager
                .createQuery("SELECT loan FROM BookLoan loan", BookLoan.class)
                .getResultList();
    }

    @Override
    public BookLoan create(BookLoan entity) {
        entityManager.persist(entity);

        if (entityManager.contains(entity)) {
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public BookLoan update(BookLoan entity) throws IllegalArgumentException {
        if (!entityManager.contains(entity)) {
            throw new IllegalArgumentException("Loan doesn't exist, use 'create' instead.");
        }

        return entityManager.merge(entity);
    }

    @Override
    public void delete(Integer entityId) throws IllegalArgumentException {
        BookLoan toRemove = findById(entityId);

        if (toRemove == null) {
            throw new IllegalArgumentException("Loan does not exist.");
        }

        entityManager.remove(toRemove);
    }
}
