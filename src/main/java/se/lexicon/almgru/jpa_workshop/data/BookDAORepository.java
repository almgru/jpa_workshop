package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.stereotype.Repository;
import se.lexicon.almgru.jpa_workshop.entity.Book;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BookDAORepository implements BookDAO {

    private final EntityManager entityManager;

    public BookDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book findById(Integer entityId) {
        return null;
    }

    @Override
    public Collection<Book> findAll() {
        return null;
    }

    @Override
    public Book create(Book entity) {
        return null;
    }

    @Override
    public Book update(Book entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws IllegalArgumentException {

    }
}
