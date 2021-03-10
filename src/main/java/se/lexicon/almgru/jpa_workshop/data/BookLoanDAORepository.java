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
        return null;
    }

    @Override
    public Collection<BookLoan> findAll() {
        return null;
    }

    @Override
    public BookLoan create(BookLoan entity) {
        return null;
    }

    @Override
    public BookLoan update(BookLoan entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws IllegalArgumentException {

    }
}
