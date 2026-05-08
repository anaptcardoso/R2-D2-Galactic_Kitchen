package org.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.model.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    @PersistenceContext
    private EntityManager em;

    // Returns all ingredients
    public List<Ingredient> findAll() {
        return em.createQuery("SELECT i FROM Ingredient i", Ingredient.class)
                .getResultList();
    }

    // Returns an ingredient by ID
    public Optional<Ingredient> findById(int id) {
        return Optional.ofNullable(em.find(Ingredient.class, id));
    }

    // Saves or updates an ingredient
    public Ingredient save(Ingredient ingredient) {
        if (ingredient.getId() == 0) {
            em.persist(ingredient);
            return ingredient;
        }
        return em.merge(ingredient);
    }

    // Deletes an ingredient by ID
    public void deleteById(int id) {
        Ingredient ingredient = em.find(Ingredient.class, id);
        if (ingredient != null) em.remove(ingredient);
    }

    // Returns true if an ingredient with the given ID exists
    public boolean existsById(int id) {
        return em.find(Ingredient.class, id) != null;
    }

    // Returns ingredients whose name contains the given string (case-insensitive)
    public List<Ingredient> findByNameContainingIgnoreCase(String name) {
        return em.createQuery(
                        "SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))",
                        Ingredient.class)
                .setParameter("name", name)
                .getResultList();
    }
}
