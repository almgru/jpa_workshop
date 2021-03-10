package se.lexicon.almgru.jpa_workshop.data;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * Abstract base implementation of a DAO repository of entities.
 * @param <T> The type of the entities which this repo holds.
 * @param <ID> The type of the identifiers for the entities.
 */
public abstract class GenericEntityDAORepository <T, ID> implements GenericEntityDAO<T, ID> {

    // Because of type erasure, we need to store the types of the entities for use when casting with EntityManager.
    protected final Class<T> _class;

    protected final EntityManager entityManager;

    /**
     * Inheriting classes should call this constructor to set the class of the types of the entities that this repo
     * holds.
     * @param _class The class of the type of entities that this repo holds.
     * @param entityManager Entity manager
     */
    protected GenericEntityDAORepository(Class<T> _class, EntityManager entityManager) {
        this._class = _class;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID entityId) {
        return entityManager.find(_class, entityId);
    }

    /* This method is abstract because I do not know how to implement it in a generic way, so inheriting classes will
     * need to do it individually. */
    @Override
    @Transactional(readOnly = true)
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
