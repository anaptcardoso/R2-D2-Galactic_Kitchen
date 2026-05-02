package org.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PlanDTO {
    private int id;
    private int userId;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    //Key day of the week
    // Value list of recipes per day
    private Map<String, List<RecipeDTO>> meals;

    // Shopping List render by plan
    private List<String> shoppingList;

    public PlanDTO(){}
    public PlanDTO(int id, int userId, LocalDate weekStart, LocalDate weekEnd,
                   Map<String, List<RecipeDTO>> meals, List<String> shoppingList) {
        this.id = id;
        this.userId = userId;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.meals = meals;
        this.shoppingList = shoppingList;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public Map<String, List<RecipeDTO>> getMeals() {
        return meals;
    }

    public List<String> getShoppingList() {
        return shoppingList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }

    public void setMeals(Map<String, List<RecipeDTO>> meals) {
        this.meals = meals;
    }

    public void setShoppingList(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PlanDTO other)) return false;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "PlanDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", weekStart=" + weekStart +
                ", weekEnd=" + weekEnd +
                '}';
    }
}
