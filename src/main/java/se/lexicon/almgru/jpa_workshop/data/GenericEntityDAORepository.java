package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class GenericEntityDAORepository <T, ID> implements GenericEntityDAO<T, ID> {

    protected final Class<T> _class;
    protected final EntityManager entityManager;

    protected GenericEntityDAORepository(Class<T> _class, EntityManager entityManager) {
        this._class = _class;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID entityId) {
        return entityManager.find(_class, entityId);
    }

    @Override
    public abstract Collection<T> findAll();

    @Override
    @Transactional
    public T create(T entity) {
        if (entityManager.contains(entity)) {
            throw new IllegalArgumentException("Entity already present in repository. To update, use 'update' instead.");
        }

        entityManager.persist(entity);

        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) throws IllegalArgumentException {
        if (!entityManager.contains(entity)) {
            throw new IllegalArgumentException("Entity not present in repository, use 'create' instead.");
        }

        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(ID entityId) throws IllegalArgumentException {
        T toRemove = findById(entityId);

        if (toRemove == null) {
            throw new IllegalArgumentException("Entity to remove not present in repository.");
        }

        entityManager.remove(toRemove);
    }
}
