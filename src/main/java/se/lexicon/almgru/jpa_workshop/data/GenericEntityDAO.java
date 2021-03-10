package se.lexicon.almgru.jpa_workshop.data;

import java.util.Collection;

public interface GenericEntityDAO<T, ID> {
    /**
     * Retrieve a single entity of type T by id.
     * @param entityId id of entity to find.
     * @return Found entity if DAO contains an entity of type T with specified id, 'null' otherwise.
     */
    T findById(ID entityId);

    /**
     * Retrieve all entities of type T from the DAO.
     * @return A collection containing all entities of type T in the DAO.
     */
    Collection<T> findAll();

    /**
     * Persist a new entity into the DAO.
     * @param entity entity to persist.
     * @return The persisted entity if entity was persisted, 'null' otherwise.
     */
    T create(T entity);

    /**
     * Update an existing entity.
     * @param entity entity to update.
     * @throws IllegalArgumentException if entity does not already exist.
     * @return The updated entity
     */
    T update(T entity) throws IllegalArgumentException;

    /**
     * Remove an entity from the DAO.
     * @param id id of entity to remove.
     * @throws IllegalArgumentException if entity does not exist.
     */
    void delete(ID id) throws IllegalArgumentException;
}
