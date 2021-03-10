package se.lexicon.almgru.jpa_workshop.data;

import se.lexicon.almgru.jpa_workshop.entity.AppUser;

import java.util.Collection;

public interface AppUserDAO {
    /**
     * Retrieve a single user by id.
     * @param userId id of user to find.
     * @return Found user if DAO contains a user with specified id, 'null' otherwise.
     */
    AppUser findById(int userId);

    /**
     * Retrieve all users from the DAO.
     * @return A collection containing all users in the DAO.
     */
    Collection<AppUser> findAll();

    /**
     * Persist a new user into the DAO.
     * @param user user to persist.
     * @return The persisted user if user was persisted, 'null' otherwise.
     */
    AppUser create(AppUser user);

    /**
     * Update an existing user.
     * @param user user to update.
     * @throws IllegalArgumentException if user does not already exist.
     * @return The updated user
     */
    AppUser update(AppUser user) throws IllegalArgumentException;

    /**
     * Remove a user from the DAO.
     * @param userId id of user to remove.
     * @throws IllegalArgumentException if user does not exist.
     */
    void delete(int userId) throws IllegalArgumentException;
}
