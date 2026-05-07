
package org.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.model.entity.UserProfile;
import org.model.entity.WeeklyPlan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for WeeklyPlan entity using EntityManager directly.
 * Replaces Spring Data JPA JpaRepository to avoid spring-hateoas dependency conflicts.
 */
@Repository
public class WeeklyPlanRepository {

    @PersistenceContext
    private EntityManager em;

    // Returns all weekly plans
    @Transactional
    public List<WeeklyPlan> findAll() {
        return em.createQuery("SELECT p FROM WeeklyPlan p", WeeklyPlan.class)
                .getResultList();
    }

    // Returns a weekly plan by ID
    @Transactional
    public Optional<WeeklyPlan> findById(int id) {
        return Optional.ofNullable(em.find(WeeklyPlan.class, id));
    }

    // Saves or updates a weekly plan
    @Transactional
    public WeeklyPlan save(WeeklyPlan plan) {
        if (plan.getId() == 0) {
            em.persist(plan);
            return plan;
        }
        return em.merge(plan);
    }

    // Deletes a weekly plan by ID
    @Transactional
    public void deleteById(int id) {
        WeeklyPlan plan = em.find(WeeklyPlan.class, id);
        if (plan != null) em.remove(plan);
    }

    // Returns true if a plan with the given ID exists
    @Transactional
    public boolean existsById(int id) {
        return em.find(WeeklyPlan.class, id) != null;
    }

    // Returns the total number of weekly plans
    @Transactional
    public long count() {
        return em.createQuery("SELECT COUNT(p) FROM WeeklyPlan p", Long.class)
                .getSingleResult();
    }

    // Returns all plans for a given user
    @Transactional
    public List<WeeklyPlan> findByUser(UserProfile user) {
        return em.createQuery(
                        "SELECT p FROM WeeklyPlan p WHERE p.user = :user", WeeklyPlan.class)
                .setParameter("user", user)
                .getResultList();
    }

    // Returns the plan for a given user and week start date
    @Transactional
    public Optional<WeeklyPlan> findByUserAndWeekStart(UserProfile user, LocalDate weekStart) {
        return em.createQuery(
                        "SELECT p FROM WeeklyPlan p WHERE p.user = :user AND p.weekStart = :weekStart",
                        WeeklyPlan.class)
                .setParameter("user", user)
                .setParameter("weekStart", weekStart)
                .getResultStream()
                .findFirst();
    }
}

