package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public Book findById(Integer entityId) {
        return entityManager.find(Book.class, entityId);
    }

    @Override
    public Collection<Book> findAll() {
        return entityManager
                .createQuery("SELECT book FROM Book book", Book.class)
                .getResultList();
    }

    @Override
    public Book create(Book entity) {
        entityManager.persist(entity);

        if (entityManager.contains(entity)) {
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public Book update(Book entity) throws IllegalArgumentException {
        if (!entityManager.contains(entity)) {
            throw new IllegalArgumentException("Book doesn't exist, use 'create' instead.");
        }

        return entityManager.merge(entity);
    }

    @Override
    public void delete(Integer entityId) throws IllegalArgumentException {
        Book toRemove = findById(entityId);

        if (toRemove == null) {
            throw new IllegalArgumentException("Book does not exist.");
        }

        entityManager.remove(toRemove);
    }
}
