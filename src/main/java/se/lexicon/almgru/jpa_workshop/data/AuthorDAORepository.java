package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.lexicon.almgru.jpa_workshop.entity.Author;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class AuthorDAORepository extends GenericEntityDAORepository<Author, Integer> {

    @Autowired
    public AuthorDAORepository(EntityManager entityManager) {
        super(Author.class, entityManager);
    }

    @Override
    public Collection<Author> findAll() {
        return entityManager
                .createQuery("SELECT author FROM Author author", Author.class)
                .getResultList();
    }
}
