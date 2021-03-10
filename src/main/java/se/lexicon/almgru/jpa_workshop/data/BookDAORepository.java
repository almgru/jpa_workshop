package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.lexicon.almgru.jpa_workshop.entity.Book;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BookDAORepository extends GenericEntityDAORepository<Book, Integer> {

    @Autowired
    public BookDAORepository(EntityManager entityManager) {
        super(Book.class, entityManager);
    }

    @Override
    public Collection<Book> findAll() {
        return entityManager
                .createQuery("SELECT book FROM Book book", Book.class)
                .getResultList();
    }
}
