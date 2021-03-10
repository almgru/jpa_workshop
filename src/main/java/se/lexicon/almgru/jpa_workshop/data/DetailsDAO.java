package se.lexicon.almgru.jpa_workshop.data;

import se.lexicon.almgru.jpa_workshop.entity.Details;

import java.util.Collection;

public interface DetailsDAO {
    /**
     * Retrieve a single user detail by id.
     * @param userDetailId id of user detail to find.
     * @return Found user detail if DAO contains a user detail with specified id, 'null' otherwise.
     */
    Details findById(int userDetailId);

    /**
     * Retrieve all user details from the DAO.
     * @return A collection containing all users details in the DAO.
     */
    Collection<Details> findAll();

    /**
     * Persist a new user detail into the DAO.
     * @param userDetail user detail to persist.
     * @return The persisted user detail if user detail was persisted, 'null' otherwise.
     */
    Details create(Details userDetail);

    /**
     * Update an existing user detail.
     * @param userDetail user detail to update.
     * @throws IllegalArgumentException if the user detail does not already exist.
     * @return The updated user detail if the user detail already exists, 'null' otherwise.
     */
    Details update(Details userDetail) throws IllegalArgumentException;

    /**
     * Remove a user detail from the DAO.
     * @param userDetailId id of user detail to remove.
     * @throws IllegalArgumentException if the user detail does not exist.
     */
    void delete(int userDetailId) throws IllegalArgumentException;
}
