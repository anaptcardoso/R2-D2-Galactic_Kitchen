package org.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class WeeklyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToMany
    private List<Recipe> recipes;

     // GETTERS / SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }

    public UserProfile getUser() { return user; }
    public void setUser(UserProfile user) { this.user = user; }

    public List<Recipe> getRecipes() { return recipes; }
    public void setRecipes(List<Recipe> recipes) { this.recipes = recipes; }

}
