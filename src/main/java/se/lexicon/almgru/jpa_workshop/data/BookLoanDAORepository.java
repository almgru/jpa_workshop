package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.lexicon.almgru.jpa_workshop.entity.BookLoan;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BookLoanDAORepository extends GenericEntityDAORepository<BookLoan, Integer> {

    @Autowired
    public BookLoanDAORepository(EntityManager entityManager) {
        super(BookLoan.class, entityManager);
    }

    @Override
    public Collection<BookLoan> findAll() {
        return entityManager
                .createQuery("SELECT loan FROM BookLoan loan", BookLoan.class)
                .getResultList();
    }
}
