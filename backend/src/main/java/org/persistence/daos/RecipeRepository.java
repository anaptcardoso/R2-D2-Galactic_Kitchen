package org.persistence.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Recipe entity using EntityManager directly.
 * Replaces Spring Data JPA JpaRepository to avoid spring-hateoas dependency conflicts.
 */
@Repository
public class RecipeRepository {

 @PersistenceContext
 private EntityManager em;

 // Returns all recipes
 @Transactional
 public List<Recipe> findAll() {
  return em.createQuery("SELECT r FROM Recipe r", Recipe.class)
          .getResultList();
 }

 // Returns a recipe by ID
 @Transactional
 public Optional<Recipe> findById(int id) {
  return Optional.ofNullable(em.find(Recipe.class, id));
 }

 // Saves or updates a recipe
 @Transactional
 public Recipe save(Recipe recipe) {
  if (recipe.getId() == 0) {
   em.persist(recipe);
   em.flush();
   return recipe;
  }
  return em.merge(recipe);
 }

 // Saves a list of recipes
 @Transactional
 public List<Recipe> saveAll(List<Recipe> recipes) {
  recipes.forEach(this::save);
  return recipes;
 }

 // Deletes a recipe by ID
 @Transactional
 public void deleteById(int id) {
  Recipe recipe = em.find(Recipe.class, id);
  if (recipe != null) em.remove(recipe);
 }

 // Returns true if a recipe with the given ID exists
 @Transactional
 public boolean existsById(int id) {
  return em.find(Recipe.class, id) != null;
 }

 // Returns the total number of recipes
 @Transactional
 public long count() {
  return em.createQuery("SELECT COUNT(r) FROM Recipe r", Long.class)
          .getSingleResult();
 }

 // Returns recipes filtered by diet type
 @Transactional
 public List<Recipe> findByDietTypesContaining(DietType dietType) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE :dietType MEMBER OF r.dietTypes", Recipe.class)
          .setParameter("dietType", dietType)
          .getResultList();
 }

 // Returns recipes filtered by difficulty level
 @Transactional
 public List<Recipe> findByDifficultyLevel(DifficultyLevel level) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE r.difficultyLevel = :level", Recipe.class)
          .setParameter("level", level)
          .getResultList();
 }

 // Returns recipes filtered by meal type
 @Transactional
 public List<Recipe> findByMealType(MealType mealType) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE r.mealType = :mealType", Recipe.class)
          .setParameter("mealType", mealType)
          .getResultList();
 }

 // Returns recipes whose name contains the given string (case-insensitive)
 @Transactional
 public List<Recipe> findByNameContainingIgnoreCase(String name) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))",
                  Recipe.class)
          .setParameter("name", name)
          .getResultList();
 }

 // Returns recipes below a calorie threshold
 @Transactional
 public List<Recipe> findByCaloriesLessThanEqual(int maxCalories) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE r.calories <= :maxCalories", Recipe.class)
          .setParameter("maxCalories", maxCalories)
          .getResultList();
 }

 // Returns recipes filtered by category
 @Transactional
 public List<Recipe> findByCategory(String category) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE r.category = :category", Recipe.class)
          .setParameter("category", category)
          .getResultList();
 }

 // Returns recipes by a list of IDs
 @Transactional
 public List<Recipe> findAllById(List<Integer> ids) {
  return em.createQuery(
                  "SELECT r FROM Recipe r WHERE r.id IN :ids", Recipe.class)
          .setParameter("ids", ids)
          .getResultList();
 }
}
