package org.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.model.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for UserProfile entity using EntityManager directly.
 * Replaces Spring Data JPA JpaRepository to avoid spring-hateoas dependency conflicts.
 */
@Repository
public class UserProfileRepository {

    @PersistenceContext
    private EntityManager em;

    // Returns all users
    public List<UserProfile> findAll() {
        return em.createQuery("SELECT u FROM UserProfile u", UserProfile.class)
                .getResultList();
    }

    // Returns a user by ID
    public Optional<UserProfile> findById(int id) {
        return Optional.ofNullable(em.find(UserProfile.class, id));
    }

    // Saves or updates a user
    public UserProfile save(UserProfile user) {
        if (user.getId() == 0) {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }

    // Saves a list of users
    public List<UserProfile> saveAll(List<UserProfile> users) {
        users.forEach(this::save);
        return users;
    }

    // Deletes a user by ID
    public void deleteById(int id) {
        UserProfile user = em.find(UserProfile.class, id);
        if (user != null) em.remove(user);
    }

    // Returns true if a user with the given ID exists
    public boolean existsById(int id) {
        return em.find(UserProfile.class, id) != null;
    }

    // Returns the total number of users
    public long count() {
        return em.createQuery("SELECT COUNT(u) FROM UserProfile u", Long.class)
                .getSingleResult();
    }

    // Returns a user by email
    public Optional<UserProfile> findByEmail(String email) {
        try {
            UserProfile user = em.createQuery(
                            "SELECT u FROM UserProfile u WHERE u.email = :email", UserProfile.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // Returns users whose first or last name contains the given string (case-insensitive)
    public List<UserProfile> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName) {
        return em.createQuery(
                        "SELECT u FROM UserProfile u WHERE " +
                                "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) OR " +
                                "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))",
                        UserProfile.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }
}

